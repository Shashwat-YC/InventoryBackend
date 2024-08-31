package me.ave.inventory.task

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.testcontainers.containers.PostgreSQLContainer
import java.util.concurrent.atomic.AtomicReference

class PostgresRunPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("postgres", Postgres::class.java)
        val reference = AtomicReference(PostgreSQLContainer("postgres:16.2").withReuse(true))
        target.tasks.register("postgresStart", PostgresStartTask::class.java, reference)
        target.tasks.register("postgresStop", PostgresStopTask::class.java, reference)

        target.afterEvaluate {
            val ext = target.extensions.getByType(Postgres::class.java)

            requireNotNull(ext.database)
            requireNotNull(ext.user)
            requireNotNull(ext.password)

            val container = reference.get()
                .withDatabaseName(ext.database)
                .withUsername(ext.user)
                .withPassword(ext.password)
            container.portBindings = listOf("2279:5432")
        }
    }
}

open class Postgres(
    var database: String? = null,
    var user: String? = null,
    var password: String? = null
)