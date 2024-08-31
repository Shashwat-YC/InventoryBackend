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
import me.ave.inventory.jooq.codegen.tables.references.*
import me.ave.inventory.models.FloorInfo
import me.ave.inventory.models.Id
import me.ave.inventory.models.PacketInfo
import me.ave.inventory.models.RoomInfo
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun Route.locationRoute(database: Database) {
    route("/location") {
        put<Resources.LocationRoute.Add.Floor> {
            val body = call.receive<RequestsBody.LocationRoute.Add.Floor>()

            database.transaction { cfg ->
                val exists = Mono.from(
                    cfg.dsl().select()
                        .from(FLOOR)
                        .where(FLOOR.ID.eq(body.id))
                )
                    .awaitSingleOrNull()
                if (exists != null) {
                    call.respondText("Floor with given id already exists.", status = HttpStatusCode.Conflict)
                    return@transaction
                }

                Mono.from(
                    cfg.dsl().insertInto(FLOOR)
                        .columns(FLOOR.ID, FLOOR.NAME)
                        .values(body.id, body.name)
                )
                    .awaitSingle()
            }.onSuccess {
                call.respond(HttpStatusCode.Created)
            }.onFailure {
                call.respondText("Failed to add floor: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStack()
            }
        }

        put<Resources.LocationRoute.Add.Room> {
            val body = call.receive<RequestsBody.LocationRoute.Add.Room>()

            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().insertInto(ROOM)
                        .columns(ROOM.NAME, ROOM.FLOOR)
                        .values(body.name, body.floor)
                        .returning(ROOM.ID)
                )
                    .map { it.into(Int::class.java) }
                    .awaitSingle()
            }.onSuccess {
                call.respond(HttpStatusCode.Created, Id(it))
            }.onFailure {
                call.respondText("Failed to add room: ${it.message}", status = HttpStatusCode.InternalServerError)
                it.printStack()
            }
        }

        post<Resources.LocationRoute.Update.All> {
            val body = call.receive<RequestsBody.LocationRoute.Update.All>()

            database.transaction { cfg ->
                Mono.from(
                    cfg.dsl().update(LOCATION)
                        .set(LOCATION.ROOM_ID, body.room)
                        .set(LOCATION.RACK, body.rack ?: -1)
                        .set(LOCATION.SHELF, body.shelf ?: -1)
                        .where(LOCATION.ID.eq(body.id))
                        .returning(LOCATION.ID)
                )
                    .awaitSingleOrNull() ?: run {
                    call.respondText("No location with given id found.", status = HttpStatusCode.NotFound)
                    return@transaction
                }
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }.onFailure {
                call.respondText(
                    "Failed to update location: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
                it.printStack()
            }
        }

        post<Resources.LocationRoute.Add.LinkMachine> {
            val body = call.receive<RequestsBody.LocationRoute.Add.LinkMachine>()
            database.transaction { cfg ->
                val id = Mono.from(
                    cfg.dsl().insertInto(LOCATION)
                        .columns(LOCATION.ROOM_ID, LOCATION.RACK, LOCATION.SHELF)
                        .values(body.roomId, body.rack, body.shelf)
                        .onConflict(LOCATION.ROOM_ID, LOCATION.RACK, LOCATION.SHELF)
                        .doUpdate()
                        .set(LOCATION.ROOM_ID, body.roomId)
                        .returning(LOCATION.ID)
                )
                    .map { it.into(Int::class.java) }
                    .awaitSingle()

                Mono.from(
                    cfg.dsl().insertInto(MACHINETOLOCATION)
                        .set(MACHINETOLOCATION.MODEL, body.model)
                        .set(MACHINETOLOCATION.LOCATION_ID, id)
                )
                    .awaitSingle()

                id
            }.onSuccess {
                call.respond(HttpStatusCode.OK, Id(it))
            }.onFailure {
                call.respondText(
                    "Failed to link machine to location: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
                it.printStack()
            }
        }

        post<Resources.LocationRoute.Update.LinkMachine> {
            val body = call.receive<RequestsBody.LocationRoute.Update.LinkMachine>()

            database.transaction { cfg ->
                Flux.merge(buildList {
                    add(
                        Mono.from(
                            cfg.dsl().deleteFrom(MACHINETOLOCATION)
                                .where(
                                    MACHINETOLOCATION.MODEL.eq(body.model)
                                        .and(MACHINETOLOCATION.LOCATION_ID.eq(body.oldLocationId))
                                )
                        )
                    )

                    val id = Mono.from(
                        cfg.dsl().insertInto(LOCATION)
                            .columns(LOCATION.ROOM_ID, LOCATION.RACK, LOCATION.SHELF)
                            .values(body.roomId, body.rack, body.shelf)
                            .onConflict(LOCATION.ROOM_ID, LOCATION.RACK, LOCATION.SHELF)
                            .doUpdate()
                            .set(LOCATION.ROOM_ID, body.roomId)
                            .returning(LOCATION.ID)
                    )
                        .map { it.into(Int::class.java) }
                        .awaitSingle()

                    add(
                        cfg.dsl().insertInto(MACHINETOLOCATION)
                            .set(MACHINETOLOCATION.MODEL, body.model)
                            .set(MACHINETOLOCATION.LOCATION_ID, id)
                    )
                })
                    .awaitLast()
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }.onFailure {
                call.respondText(
                    "Failed to link machine to location: ${it.message}",
                    status = HttpStatusCode.InternalServerError
                )
                it.printStack()
            }
        }

        get<Resources.LocationRoute.GetRooms.ByFloor> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(ROOM)
                        .where(ROOM.FLOOR.eq(it.floor))
                )
                    .map(RoomInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.LocationRoute.GetRooms.All> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(ROOM)
                )
                    .map(RoomInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.LocationRoute.GetFloors> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select()
                        .from(FLOOR)
                )
                    .map(FloorInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }

        get<Resources.LocationRoute.GetPackets.ByLocationAndMachine> {
            database.transaction { cfg ->
                Flux.from(
                    cfg.dsl().select().from(PACKETRECORD)
                        .innerJoin(LOCATION).on(PACKETRECORD.LOCATION_ID.eq(LOCATION.ID))
                        .innerJoin(ROOM).on(LOCATION.ROOM_ID.eq(ROOM.ID))
                        .innerJoin(FLOOR).on(ROOM.FLOOR.eq(FLOOR.ID))
                        .innerJoin(SPAREPARTINFO).on(PACKETRECORD.CODE.eq(SPAREPARTINFO.CODE))
                        .innerJoin(MACHINEINFO).on(SPAREPARTINFO.MACHINE_MODEL.eq(MACHINEINFO.MODEL))
                        .where(LOCATION.ID.eq(it.location).and(MACHINEINFO.MODEL.eq(it.machine)))
                )
                    .map(PacketInfo.Companion::fromRecord)
                    .collectList()
                    .awaitSingle()
                    .run {
                        call.respond(this)
                    }
            }
        }
    }
}
