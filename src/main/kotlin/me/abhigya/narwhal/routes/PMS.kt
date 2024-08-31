package me.ave.inventory.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import me.ave.inventory.database.Database
import me.ave.inventory.database.transaction
import me.ave.inventory.jooq.codegen.keys.UQ_MAINTENANCEUPDATEDPACKET
import me.ave.inventory.jooq.codegen.tables.pojos.Maintenancerecord
import me.ave.inventory.jooq.codegen.tables.records.UsedpacketrecordRecord
import me.ave.inventory.jooq.codegen.tables.references.*
import me.ave.inventory.models.*
import org.jooq.Condition
import org.jooq.impl.DSL
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun Route.pmsRoute(database: Database) {
    route("/pms") {
        get<Resources.JobRoute.Get> {
            database.transaction { cfg ->
                Flux.from(cfg.dsl().select()
                    .from(MACHINE)
                    .innerJoin(MACHINEINFO).on(MACHINE.MODEL.eq(MACHINEINFO.MODEL))
                    .innerJoin(MAINTENANCERECORD).on(
                        MACHINE.MODEL.eq(MAINTENANCERECORD.MODEL)
                            .and(MACHINE.MACHINE_NO.eq(MAINTENANCERECORD.MACHINE_NO))
                    )
                    .innerJoin(JOB).on(MAINTENANCERECORD.PMS_CODE.eq(JOB.PMS_CODE))
                    .innerJoin(SCHEDULEINFO).on(JOB.SCHEDULE_KEY.eq(SCHEDULEINFO.ID))
                    .run {
                        var clause: Condition? = null
                        if (it.place != null)
                            clause = JOB.PLACE.eq(it.place)
                        if (it.status != null) {
                            val subClause = when (it.status) {
                                Job.Status.COMPLETED -> MAINTENANCERECORD.TIME_STARTED.isNotNull.and(
                                    MAINTENANCERECORD.TIME_COMPLETED.isNotNull
                                )

                                Job.Status.IN_PROGRESS -> MAINTENANCERECORD.TIME_STARTED.isNotNull.and(
                                    MAINTENANCERECORD.TIME_COMPLETED.isNull
                                )

                                Job.Status.PLANNING -> MAINTENANCERECORD.TIME_STARTED.isNull.and(MAINTENANCERECORD.TIME_COMPLETED.isNull)
                            }

                            clause = clause?.and(subClause) ?: subClause
                        }

                        if (clause != null) where(clause) else this
                    })
                    .run {
                        val now = Clock.System.now()
                        mapNotNull { row ->
                            val schedule = row[SCHEDULEINFO.TYPE]!!.calculateSchedule(
                                row[MACHINE.LAST_MAINTENANCE]!!,
                                row[SCHEDULEINFO.DELAY]!!,
                                row[MACHINE.AVG_RH]!!
                            )

                            if (it.dueWithin != null && it.dueWithin != Job.DueWithin.ALL) {
                                val next = when (it.dueWithin) {
                                    Job.DueWithin.DAILY -> now.plus(1, DateTimeUnit.DAY, TimeZone.UTC)
                                    Job.DueWithin.WEEKLY -> now.plus(1, DateTimeUnit.WEEK, TimeZone.UTC)
                                    Job.DueWithin.MONTHLY -> now.plus(1, DateTimeUnit.MONTH, TimeZone.UTC)
                                    else -> return@mapNotNull null
                                }

                                if (schedule > next) return@mapNotNull null
                            }

                            JobWithMachine(
                                Job(
                                    id = row[MAINTENANCERECORD.ID]!!,
                                    code = row[JOB.PMS_CODE]!!,
                                    description = row[JOB.DESCRIPTION]!!,
                                    place = row[JOB.PLACE]!!,
                                    kind = row[JOB.KIND]!!,
                                    scheduleInfo = ScheduleInfo.fromRecord(row),
                                    pic = row[JOB.PIC]!!,
                                    nextSchedule = schedule
                                ),
                                Machine.fromRecord(row)
                            )
                        }
                    }
                    .run {
                        if (it.limit > -1)
                            this.take(it.limit.toLong())
                        else
                            this
                    }
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.JobRoute.GetParts.Code> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(JOBTOSPAREPART)
                        .innerJoin(SPAREPARTINFO).on(JOBTOSPAREPART.SPARE_PART.eq(SPAREPARTINFO.CODE))
                        .where(JOBTOSPAREPART.PMS_CODE.eq(it.code))
                )
                    .map { row ->
                        SparePartWithJob(
                            rob = 0,
                            code = row[SPAREPARTINFO.CODE]!!,
                            name = row[SPAREPARTINFO.NAME]!!,
                            partNo = row[SPAREPARTINFO.PART_NO]!!,
                            machineModel = row[SPAREPARTINFO.MACHINE_MODEL]!!,
                            packets = emptyList(),
                            quantityNeeded = row[JOBTOSPAREPART.QUANTITY]!!,
                        )
                    }
                    .distinct()
                    .collectMap { it.code }
                    .map { map ->
                        if (map.isNotEmpty()) {
                            val col = DSL.sum(PACKETRECORD.QUANTITY)
                            cfg.dsl().select(col, PACKETRECORD.CODE)
                                .from(PACKETRECORD)
                                .where(PACKETRECORD.CODE.`in`(map.keys))
                                .groupBy(PACKETRECORD.CODE)
                                .forEach {
                                    val code = it[PACKETRECORD.CODE]!!
                                    map.computeIfPresent(code) { _, v -> v.copy(rob = it[col]!!.toInt()) }
                                }
                        }
                        map.values
                    }
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.JobRoute.GetPartsMaintenance.MaintenanceId> {
            database.transaction { cfg ->
                val updatedSparePart = Flux.from(
                    cfg.dsl().select()
                        .from(MAINTENANCEUPDATEDPACKET)
                        .where(MAINTENANCEUPDATEDPACKET.MAINTENANCE_ID.eq(it.id))
                )
                    .collectMap({ it[MAINTENANCEUPDATEDPACKET.SPARE_PART_CODE]!! }) {
                        it[MAINTENANCEUPDATEDPACKET.UPDATED_QUANTITY]!!
                    }
                    .awaitSingle()

                Flux.from(
                    cfg.dsl().select()
                        .from(MAINTENANCERECORD)
                        .innerJoin(JOBTOSPAREPART).on(MAINTENANCERECORD.PMS_CODE.eq(JOBTOSPAREPART.PMS_CODE))
                        .innerJoin(SPAREPARTINFO).on(JOBTOSPAREPART.SPARE_PART.eq(SPAREPARTINFO.CODE))
                        .where(MAINTENANCERECORD.ID.eq(it.id))
                )
                    .map { row ->
                        SparePartWithMaintenance(
                            rob = 0,
                            code = row[SPAREPARTINFO.CODE]!!,
                            name = row[SPAREPARTINFO.NAME]!!,
                            partNo = row[SPAREPARTINFO.PART_NO]!!,
                            machineModel = row[SPAREPARTINFO.MACHINE_MODEL]!!,
                            packets = emptyList(),
                            quantityNeeded = row[JOBTOSPAREPART.QUANTITY]!!,
                            quantityNeededUpdated = updatedSparePart.getOrDefault(row[SPAREPARTINFO.CODE]!!, null)
                        )
                    }
                    .distinct()
                    .collectMap { it.code }
                    .map { map ->
                        if (map.isNotEmpty()) {
                            val col = DSL.sum(PACKETRECORD.QUANTITY)
                            cfg.dsl().select(col, PACKETRECORD.CODE)
                                .from(PACKETRECORD)
                                .where(PACKETRECORD.CODE.`in`(map.keys))
                                .groupBy(PACKETRECORD.CODE)
                                .forEach {
                                    val code = it[PACKETRECORD.CODE]!!
                                    map.computeIfPresent(code) { _, v -> v.copy(rob = it[col]!!.toInt()) }
                                }
                        }
                        map.values
                    }
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        post<Resources.JobRoute.Start.Code> {
            val record = database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().select()
                        .from(MAINTENANCERECORD)
                        .where(MAINTENANCERECORD.ID.eq(it.code))
                )
                    .map { it.into(MAINTENANCERECORD) }
                    .awaitSingle()
            }.getOrNull() ?: return@post call.respondText(
                "No job with given id found.",
                status = HttpStatusCode.NotFound
            )

            if (record.timeStarted != null) {
                return@post call.respondText("Job Already Started", status = HttpStatusCode.NotAcceptable)
            }

            val packets = call.receive<List<Int>>()

            database.transaction { cfg ->
                launch {
                    Mono.from(
                        cfg.dsl().update(MAINTENANCERECORD)
                            .set(MAINTENANCERECORD.TIME_STARTED, Clock.System.now())
                            .where(MAINTENANCERECORD.ID.eq(record.id))
                    )
                        .awaitSingle()
                }

                if (packets.isEmpty()) return@transaction

                val quantities = Flux.from(
                    cfg.dsl().select(PACKETRECORD.ID, PACKETRECORD.QUANTITY)
                        .from(PACKETRECORD)
                        .where(PACKETRECORD.ID.`in`(packets))
                )
                    .collectMap({ it[PACKETRECORD.ID]!! }) { it[PACKETRECORD.QUANTITY]!! }
                    .awaitSingle()
                Flux.merge(
                    Mono.from(cfg.dsl().batchInsert(packets.map {
                        UsedpacketrecordRecord(
                            packetId = it,
                            maintenanceId = record.id,
                            quantity = quantities[it] ?: 0
                        ).apply {
                            changed(USEDPACKETRECORD.PACKET_ID, false)
                        }
                    })),
                    Mono.from(
                        cfg.dsl().update(PACKETRECORD)
                            .set(PACKETRECORD.QUANTITY, 0)
                            .where(PACKETRECORD.ID.`in`(packets))
                    )
                )
                    .awaitLast()
            }.onFailure {
                call.respondText("Failed to start job: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStack()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }
        }

        post<Resources.JobRoute.Complete.Code> {
            val record = database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().select()
                        .from(MAINTENANCERECORD)
                        .where(MAINTENANCERECORD.ID.eq(it.code))
                )
                    .map { it.into(Maintenancerecord::class.java) }
                    .awaitSingle()
            }.getOrNull() ?: return@post call.respondText(
                "No job with given id found.",
                status = HttpStatusCode.NotFound
            )

            val body = call.receive<Map<Int, Int>>()

            database.transaction { cfg ->
                Flux.merge(buildList {
                    add(
                        Mono.from(
                            cfg.dsl().insertInto(MAINTENANCERECORD)
                                .columns(
                                    MAINTENANCERECORD.MODEL,
                                    MAINTENANCERECORD.MACHINE_NO,
                                    MAINTENANCERECORD.PMS_CODE
                                )
                                .values(record.model, record.machineNo, record.pmsCode)
                        )
                    )

                    add(
                        Mono.from(
                            cfg.dsl().update(MAINTENANCERECORD)
                                .set(MAINTENANCERECORD.TIME_COMPLETED, Clock.System.now())
                                .where(MAINTENANCERECORD.ID.eq(record.id))
                        )
                    )

                    if (body.isEmpty()) return@buildList

                    val itr = body.iterator()
                    val first = itr.next()

                    add(
                        Mono.from(
                            cfg.dsl().update(USEDPACKETRECORD)
                                .set(USEDPACKETRECORD.QUANTITY, DSL.case_(USEDPACKETRECORD.ID)
                                    .`when`(first.key, USEDPACKETRECORD.QUANTITY.minus(first.value)).run {
                                        var curr = this
                                        itr.forEach {
                                            curr = curr.`when`(it.key, USEDPACKETRECORD.QUANTITY.minus(it.value))
                                        }
                                        curr.otherwise(USEDPACKETRECORD.QUANTITY)
                                    })
                                .where(USEDPACKETRECORD.PACKET_ID.`in`(body.keys))
                        )
                    )
                })
                    .awaitLast()
            }.onFailure {
                call.respondText("Failed to complete job: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStack()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }
        }

        post<Resources.JobRoute.Update.PIC> {
            val body = call.receive<RequestsBody.JobRoute.Update.PIC>()
            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().update(JOB)
                        .set(JOB.PIC, body.pic)
                        .where(JOB.PMS_CODE.eq(body.code))
                )
                    .awaitSingle()
            }.onFailure {
                call.respondText("Failed to update job: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStack()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }
        }

        post<Resources.JobRoute.Update.SparePartNeededQuantity> {
            val body = call.receive<RequestsBody.JobRoute.Update.SparePartNeededQuantity>()

            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().insertInto(MAINTENANCEUPDATEDPACKET)
                        .set(MAINTENANCEUPDATEDPACKET.MAINTENANCE_ID, body.maintenanceId)
                        .set(MAINTENANCEUPDATEDPACKET.SPARE_PART_CODE, body.sparePartCode)
                        .set(MAINTENANCEUPDATEDPACKET.UPDATED_QUANTITY, body.quantity)
                        .onConflictOnConstraint(UQ_MAINTENANCEUPDATEDPACKET)
                        .doUpdate()
                        .set(MAINTENANCEUPDATEDPACKET.UPDATED_QUANTITY, body.quantity)
                )
                    .awaitSingle()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }.onFailure {
                call.respondText(
                    "Failed to insert or update: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
                it.printStack()
            }
        }
    }
}