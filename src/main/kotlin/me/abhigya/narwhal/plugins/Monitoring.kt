package me.ave.inventory.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = if (this@configureMonitoring.developmentMode) Level.TRACE else Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
