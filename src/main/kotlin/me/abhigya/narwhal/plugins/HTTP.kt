package me.ave.inventory.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureHTTP() {
    if (developmentMode) {
        routing {
            openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
        }
    }

    install(CORS) {
        allowMethod(HttpMethod.Put)
        anyHost()
        allowHeader("*")
        allowCredentials = true
        allowOrigins { true }
    }

    install(Resources)

    install(Compression) {
        gzip {
            priority = 0.9
        }
        deflate {
            priority = 1.0
        }
    }
}
