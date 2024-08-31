package me.ave.inventory.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.testcontainers.containers.PostgreSQLContainer
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

open class PostgresStartTask @Inject constructor(private val container: AtomicReference<PostgreSQLContainer<*>>) : DefaultTask() {

    init {
        group = "postgres"
        description = "Starts the Postgres container"
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun exec() {
        container.get()?.start() ?: throw IllegalStateException("Postgres container not initialized")
    }

}

open class PostgresStopTask @Inject constructor(private val container: AtomicReference<PostgreSQLContainer<*>>) : DefaultTask() {

    init {
        group = "postgres"
        description = "Stops the Postgres container"
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun exec() {
        container.get()?.stop() ?: throw IllegalStateException("Postgres container not initialized")
    }

}