package me.ave.inventory.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import me.ave.inventory.database.Database
import me.ave.inventory.database.transaction
import me.ave.inventory.jooq.codegen.tables.references.PACKETRECORD
import me.ave.inventory.models.Rob
import reactor.core.publisher.Mono

fun Route.forbiddenRoute(database: Database) {
    route("/forbidden") {
        put<Resources.PacketRoute.Add> {
            val body = call.receive<RequestsBody.PacketRoute.Add>()

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
                        .values(body.id, body.rfid, body.partCode, body.quantity, body.type, body.locationId)
                )
                    .awaitSingle()
            }.onFailure {
                call.respondText("Failed to create packet: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStackTrace()
            }.onSuccess {
                call.respondText("Packet created successfully.", status = HttpStatusCode.Created)
            }
        }

        get<Resources.SparePartRoute.GetRob.Code> {
            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().select(PACKETRECORD.CODE)
                        .from(PACKETRECORD)
                        .where(PACKETRECORD.CODE.eq(it.code))
                )
                    .map { it.into(Int::class.java) }
                    .awaitSingleOrNull()
                    .run {
                        call.respond(Rob(this ?: 0))
                    }
            }
        }

        /*route("/location") {
            get<Resources.LocationRoute.GetPackets.Full> {
                database.from(PacketRecordTable)
                    .innerJoin(LocationTable, PacketRecordTable.location_id eq LocationTable.id)
                    .innerJoin(RoomTable, LocationTable.room_id eq RoomTable.id)
                    .innerJoin(FloorTable, RoomTable.floor_id eq FloorTable.id)
                    .innerJoin(SparePartInfoTable, PacketRecordTable.code eq SparePartInfoTable.code)
                    .innerJoin(MachineInfoTable, SparePartInfoTable.machine_model eq MachineInfoTable.model)
                    .select(PacketRecordTable.columns + SparePartInfoTable.columns + LocationTable.columns + FloorTable.columns + RoomTable.columns + MachineInfoTable.columns)
                    .where {
                        (RoomTable.floor_id eq it.floor) and
                        (LocationTable.room_id eq it.room) and
                        (LocationTable.rack eq (it.rack ?: -1)) and
                        (LocationTable.shelf eq (it.shelf ?: -1))
                    }
                    .map(QueryRowSet::createPacketInfo)
                    .let {
                        call.respond(it)
                    }
            }
        }*/
    }
}