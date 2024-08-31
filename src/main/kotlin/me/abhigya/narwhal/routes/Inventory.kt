package me.ave.inventory.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import me.ave.inventory.database.Database
import me.ave.inventory.database.transaction
import me.ave.inventory.jooq.codegen.tables.pojos.Machineinfo
import me.ave.inventory.jooq.codegen.tables.references.*
import me.ave.inventory.models.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun Route.inventoryRoute(database: Database) {
    route("/inventory") {
        get<Resources.PacketRoute.Get.Rfid> {
            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().select()
                        .from(PACKETRECORD)
                        .innerJoin(SPAREPARTINFO).on(PACKETRECORD.CODE.eq(SPAREPARTINFO.CODE))
                        .innerJoin(MACHINEINFO).on(SPAREPARTINFO.MACHINE_MODEL.eq(MACHINEINFO.MODEL))
                        .innerJoin(LOCATION).on(PACKETRECORD.LOCATION_ID.eq(LOCATION.ID))
                        .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                        .innerJoin(FLOOR).on(ROOM.FLOOR.eq(FLOOR.ID))
                        .where(PACKETRECORD.RFID.eq(it.rfid))
                )
                    .map(PacketInfo.Companion::fromRecord)
                    .awaitSingleOrNull()
                    ?.run {
                        call.respond(this)
                    }
                    ?: call.respondText("No packet with given rfid found.", status = HttpStatusCode.NotFound)
            }
        }

        post<Resources.PacketRoute.GetAll> {
            val rfidList = call.receive<List<String>>()

            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(PACKETRECORD)
                        .innerJoin(SPAREPARTINFO).on(PACKETRECORD.CODE.eq(SPAREPARTINFO.CODE))
                        .innerJoin(MACHINEINFO).on(SPAREPARTINFO.MACHINE_MODEL.eq(MACHINEINFO.MODEL))
                        .innerJoin(LOCATION).on(PACKETRECORD.LOCATION_ID.eq(LOCATION.ID))
                        .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                        .innerJoin(FLOOR).on(ROOM.FLOOR.eq(FLOOR.ID))
                        .where(PACKETRECORD.RFID.`in`(rfidList))
                )
                    .map(PacketInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        post<Resources.PacketRoute.Assign> {
            val body = call.receive<RequestsBody.PacketRoute.Assign>()

            val pkt = database.transaction {
                Mono.from(
                    it.dsl().deleteFrom(UNASSIGNEDPACKETS)
                        .where(UNASSIGNEDPACKETS.ID.eq(body.id))
                        .returning()
                )
                    .map { it.into(UNASSIGNEDPACKETS) }
                    .awaitSingleOrNull()
            }.getOrNull() ?: return@post call.respondText(
                "No unassigned packet with given id found.",
                status = HttpStatusCode.NotFound
            )

            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().insertInto(PACKETRECORD)
                        .columns(
                            PACKETRECORD.ID,
                            PACKETRECORD.RFID,
                            PACKETRECORD.CODE,
                            PACKETRECORD.QUANTITY,
                            PACKETRECORD.TYPE,
                            PACKETRECORD.LOCATION_ID
                        )
                        .values(body.id, pkt.rfid, body.partCode, body.quantity, body.type, body.locationId)
                )
                    .awaitSingle()
            }.onSuccess {
                call.respondText("Packet assigned successfully.", status = HttpStatusCode.Created)
            }.onFailure {
                // TODO: Verbose exception handling
                call.respondText("Failed to assign packet: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStack()
            }
        }

        post<Resources.PacketRoute.Update> {
            val body = call.receive<RequestsBody.PacketRoute.Update>()

            if (body.id == null && body.rfid == null) return@post call.respondText(
                "Either id or rfid must be provided.",
                status = HttpStatusCode.BadRequest
            )

            database.transaction { cfg ->
                if (body.newId != null && body.id != null) {
                    val swap = Mono.from(
                        cfg.dsl().select()
                            .from(UNASSIGNEDPACKETS)
                            .where(UNASSIGNEDPACKETS.ID.eq(body.newId))
                    )
                        .map { it.into(UNASSIGNEDPACKETS) }
                        .awaitSingleOrNull()
                        ?: return@transaction call.respondText("Invalid New Id", status = HttpStatusCode.NotFound)
                    val original = Mono.from(
                        cfg.dsl().select()
                            .from(PACKETRECORD)
                            .where(PACKETRECORD.ID.eq(body.id))
                    )
                        .map { it.into(PACKETRECORD) }
                        .awaitSingleOrNull()
                        ?: return@transaction call.respondText("Invalid Id", status = HttpStatusCode.NotFound)

                    Flux.merge(
                        Mono.from(
                            cfg.dsl().deleteFrom(PACKETRECORD)
                                .where(PACKETRECORD.ID.eq(body.id))
                        ),
                        Mono.from(
                            cfg.dsl().insertInto(UNASSIGNEDPACKETS)
                                .columns(UNASSIGNEDPACKETS.ID, UNASSIGNEDPACKETS.RFID)
                                .values(original.id, original.rfid)
                        ),
                        Mono.from(
                            cfg.dsl().update(PACKETRECORD)
                                .set(PACKETRECORD.ID, swap.id)
                                .set(PACKETRECORD.RFID, swap.rfid)
                                .where(PACKETRECORD.ID.eq(body.id))
                        )
                    ).awaitLast()
                }

                if (body.quantity == null && body.type == null && body.locationId == null) return@transaction
                Mono.from(
                    cfg.dsl().update(PACKETRECORD)
                        .set(buildMap {
                            if (body.quantity != null) put(PACKETRECORD.QUANTITY, body.quantity)
                            if (body.type != null) put(PACKETRECORD.TYPE, body.type)
                            if (body.locationId != null) put(PACKETRECORD.LOCATION_ID, body.locationId)
                        })
                        .where(
                            if (body.newId != null)
                                PACKETRECORD.ID.eq(body.newId)
                            else if (body.id != null)
                                PACKETRECORD.ID.eq(body.id)
                            else
                                PACKETRECORD.RFID.eq(body.rfid)
                        )
                )
                    .awaitSingle()
            }.onFailure {
                call.respondText(
                    "Failed to update packet: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }
        }

        put<Resources.PacketRoute.AddUnassigned> {
            val body = call.receive<RequestsBody.PacketRoute.AddUnassigned>()
            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().insertInto(UNASSIGNEDPACKETS)
                        .columns(UNASSIGNEDPACKETS.ID, UNASSIGNEDPACKETS.RFID)
                        .values(body.id, body.rfid)
                )
                    .awaitSingle()
            }.onSuccess {
                call.respondText("Unassigned packet created successfully.", status = HttpStatusCode.Created)
            }.onFailure {
                call.respondText(
                    "Failed to create unassigned packet: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
                it.printStack()
            }
        }

        post<Resources.PacketRoute.UpdateInUse.Tag> {
            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().update(USEDPACKETRECORD)
                        .set(USEDPACKETRECORD.QUANTITY, it.quantity)
                        .where(USEDPACKETRECORD.PACKET_ID.eq(it.tag))
                )
                    .awaitSingle()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }.onFailure {
                call.respondText(
                    "Failed to update: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
                it.printStack()
            }
        }

        get<Resources.SparePartRoute.Get.Code> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(PACKETRECORD)
                        .innerJoin(SPAREPARTINFO).on(PACKETRECORD.CODE.eq(SPAREPARTINFO.CODE))
                        .innerJoin(MACHINEINFO).on(SPAREPARTINFO.MACHINE_MODEL.eq(MACHINEINFO.MODEL))
                        .innerJoin(LOCATION).on(PACKETRECORD.LOCATION_ID.eq(LOCATION.ID))
                        .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                        .innerJoin(FLOOR).on(ROOM.FLOOR.eq(FLOOR.ID))
                        .where(PACKETRECORD.CODE.eq(it.code))
                )
                    .map(PacketInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        if (isEmpty())
                            SparePart(0, "", "", "", "", emptyList())
                        else {
                            val first = first()
                            SparePart(
                                rob = sumOf { it.quantity },
                                code = it.code,
                                name = first.partInfo.name,
                                partNo = first.partInfo.partNo,
                                machineModel = first.partInfo.machineInfo.model,
                                packets = this
                            )
                        }
                    }
                    .let { part ->
                        if (part.rob != 0)
                            call.respond(it)
                        else {
                            Mono.from(
                                cfg.dsl().select()
                                    .from(SPAREPARTINFO)
                                    .where(SPAREPARTINFO.CODE.eq(it.code))
                            )
                                .map { it.into(SPAREPARTINFO) }
                                .awaitSingleOrNull()
                                ?.run {
                                    call.respond(
                                        SparePart(
                                            0,
                                            code!!,
                                            name!!,
                                            partNo!!,
                                            machineModel!!,
                                            emptyList()
                                        )
                                    )
                                } ?: call.respondText(
                                "No spare part with given code found.",
                                status = HttpStatusCode.NotFound
                            )
                        }
                    }
            }
        }

        get<Resources.SparePartRoute.MaintenanceGet.RecordId> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(USEDPACKETRECORD)
                        .innerJoin(PACKETRECORD).on(USEDPACKETRECORD.PACKET_ID.eq(PACKETRECORD.ID))
                        .innerJoin(SPAREPARTINFO).on(PACKETRECORD.CODE.eq(SPAREPARTINFO.CODE))
                        .innerJoin(MACHINEINFO).on(SPAREPARTINFO.MACHINE_MODEL.eq(MACHINEINFO.MODEL))
                        .innerJoin(LOCATION).on(PACKETRECORD.LOCATION_ID.eq(LOCATION.ID))
                        .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                        .innerJoin(FLOOR).on(ROOM.FLOOR.eq(FLOOR.ID))
                        .where(
                            USEDPACKETRECORD.MAINTENANCE_ID.eq(it.maintenanceId)
                                .and(PACKETRECORD.CODE.eq(it.sparePartCode))
                        )
                )
                    .map {
                        PacketInfo(
                            it[PACKETRECORD.ID]!!,
                            it[PACKETRECORD.RFID]!!,
                            SparePartInfo.fromRecord(it),
                            it[USEDPACKETRECORD.QUANTITY]!!,
                            it[PACKETRECORD.TYPE]!!,
                            LocationInfo.fromRecord(it)
                        )
                    }
                    .collectList()
                    .awaitSingle()
                    .run {
                        if (isEmpty())
                            SparePart(0, "", "", "", "", emptyList())
                        else {
                            val first = first()
                            SparePart(
                                rob = sumOf { it.quantity },
                                code = it.sparePartCode,
                                name = first.partInfo.name,
                                partNo = first.partInfo.partNo,
                                machineModel = first.partInfo.machineInfo.model,
                                packets = this
                            )
                        }
                    }
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.Machine.Get.Floor> {
            database.transaction { cfg ->
                if (it.floor == -1)
                    Flux.from(cfg.dsl().select().from(MACHINEINFO))
                        .map(MachineInfo.Companion::fromRecord)
                        .collectList()
                        .awaitSingle()
                        .run {
                            call.respond(this)
                        }
                else
                    Flux.from(
                        cfg.dsl().select(
                            *(
                                    MACHINEINFO.fields() +
                                            MACHINETOLOCATION.fields() +
                                            LOCATION.ID +
                                            LOCATION.ROOM_ID +
                                            ROOM.ID +
                                            ROOM.FLOOR
                                    )
                        )
                            .from(MACHINEINFO)
                            .innerJoin(MACHINETOLOCATION).on(MACHINEINFO.MODEL.eq(MACHINETOLOCATION.MODEL))
                            .innerJoin(LOCATION).on(MACHINETOLOCATION.LOCATION_ID.eq(LOCATION.ID))
                            .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                            .where(ROOM.FLOOR.eq(it.floor))
                    )
                        .map(MachineInfo.Companion::fromRecord)
                        .collectList()
                        .awaitSingle()
                        .run {
                            call.respond(this)
                        }
            }
        }

        get<Resources.Machine.Info.MachineId> {
            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().select()
                        .from(MACHINEINFO)
                        .where(MACHINEINFO.MODEL.eq(it.machine))
                )
                    .map { it.into(Machineinfo::class.java) }
                    .map(MachineInfo.Companion::fromPojo)
                    .awaitSingleOrNull()
                    ?.run {
                        call.respond(this)
                    } ?: call.respondText(
                    "No machine with given model found.",
                    status = HttpStatusCode.NotFound
                )
            }
        }

        get<Resources.Machine.GetLocations.Model> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(MACHINETOLOCATION)
                        .innerJoin(LOCATION).on(MACHINETOLOCATION.LOCATION_ID.eq(LOCATION.ID))
                        .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                        .innerJoin(FLOOR).on(ROOM.FLOOR.eq(FLOOR.ID))
                        .where(MACHINETOLOCATION.MODEL.eq(it.model))
                )
                    .map(MachineInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.Machine.GetParts.MachineId> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(SPAREPARTINFO)
                        .innerJoin(MACHINEINFO).on(SPAREPARTINFO.MACHINE_MODEL.eq(MACHINEINFO.MODEL))
                        .where(SPAREPARTINFO.MACHINE_MODEL.eq(it.machine))
                )
                    .map(SparePartInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }
    }
}