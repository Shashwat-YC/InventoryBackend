package me.ave.inventory.plugins

import io.ktor.server.application.*
import me.ave.inventory.database.Database
import me.ave.inventory.database.DatabaseSettings
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

fun Application.configureDatabases(settings: DatabaseSettings) {
    val database = Database(settings, environment.developmentMode)

    database.migrate()

    KTP.openRootScope().installModules(module {
        bind<Database>().toInstance(database)
    })
}
