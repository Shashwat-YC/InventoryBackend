package me.ave.inventory.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ave.inventory.database.Database
import me.ave.inventory.routes.inventoryRoute
import me.ave.inventory.routes.locationRoute
import me.ave.inventory.routes.pmsRoute
import toothpick.ktp.KTP
import toothpick.ktp.extension.getInstance

fun Application.configureRouting() {
    val database = KTP.openRootScope().getInstance<Database>()

    routing {
        post("/ack") {
            call.respond(HttpStatusCode.OK)
        }

        pmsRoute(database)
        inventoryRoute(database)
        locationRoute(database)

        allRoutes(this).filterIsInstance<HttpMethodRouteSelector>().forEach {
            application.log.debug("route: {}", it)
        }
    }
}

fun allRoutes(root: Route): List<Route> {
    return listOf(root) + root.children.flatMap { allRoutes(it) }
}
