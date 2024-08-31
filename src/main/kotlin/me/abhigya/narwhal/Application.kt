package me.ave.inventory

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import io.ktor.util.logging.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import me.ave.inventory.database.DatabaseSettings
import me.ave.inventory.plugins.*
import org.slf4j.LoggerFactory
import toothpick.configuration.Configuration
import toothpick.ktp.KTP

private val LOGGERS: Array<String> = arrayOf(
    "inventory.application",
    "inventory.database",
    "com.zaxxer.hikari.pool.HikariPool",
    "com.zaxxer.hikari.HikariConfig"
)

val dotEnv = dotenv()

const val APP_NAME = "inventory"

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val environment = applicationEngineEnvironment {
        developmentMode = dotEnv["ENVIRONMENT"] == "DEV"
        parentCoroutineContext = GlobalScope.coroutineContext
        log = KtorSimpleLogger("inventory.application")
        module {
            module()
        }
        connector {
            host = "0.0.0.0"
            port = dotEnv["PORT"]?.toInt() ?: 8080
        }
    }

    if (environment.developmentMode) {
        val context = LoggerFactory.getILoggerFactory() as LoggerContext
        LOGGERS.forEach { context.getLogger(it).level = Level.TRACE }
    }

    KTP.setConfiguration(
        if (environment.developmentMode)
            Configuration.forDevelopment()
        else
            Configuration.forProduction()
    )

    embeddedServer(Jetty, environment).start(wait = true)
}

fun Application.module() {
    configureSerialization()

    configureDatabases(DatabaseSettings.fromEnv(dotEnv))

    configureMonitoring()

    configureHTTP()

    configureRouting()
}
