package me.ave.inventory.database

import io.ktor.server.application.*
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.util.LogLevel
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.Dispatchers
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.core.PostgresDatabase
import liquibase.resource.ClassLoaderResourceAccessor
import me.ave.inventory.APP_NAME
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DefaultConfiguration
import org.jooq.kotlin.coroutines.transactionCoroutine
import java.time.Duration
import java.util.*

class Database(
    private val settings: DatabaseSettings,
    private val isDevelopment: Boolean
) {

    private lateinit var context: DSLContext
    private val factory: ConnectionFactory by lazy {
        PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(settings.host)
                .port(settings.port)
                .database(settings.database)
                .username(settings.user)
                .password(settings.password)
                .applicationName(APP_NAME)
                .connectTimeout(Duration.ofSeconds(30))
                .noticeLogLevel(if (isDevelopment) LogLevel.DEBUG else LogLevel.WARN)
                .errorResponseLogLevel(if (isDevelopment) LogLevel.DEBUG else LogLevel.WARN)
                .fetchSize(1000)
                .preparedStatementCacheQueries(25)
                .timeZone(TimeZone.getTimeZone("UTC"))
                .build()
        )
    }

    fun migrate() {
        DatabaseFactory.getInstance().openConnection(
            "jdbc:postgresql://${settings.host}:${settings.port}/${settings.database}?socketTimeout=30000",
            settings.user,
            settings.password,
            "org.postgresql.Driver",
            PostgresDatabase::class.qualifiedName,
            null,
            null,
            ClassLoaderResourceAccessor()
        ).use { conn ->
            PostgresDatabase().use {
                it.connection = conn
                val liquibase = Liquibase(
                    "db/migration/changelog.xml",
                    ClassLoaderResourceAccessor(Application::class.java.classLoader),
                    it
                )
                liquibase.update(Contexts(), LabelExpression())
            }
        }
    }

    fun ctx(createNew: Boolean = false): DSLContext {
        if (!this::context.isInitialized || createNew) {
            context = configuration().dsl()
        }
        return context
    }

    fun configuration(): Configuration {
        return DefaultConfiguration()
            .set(factory)
            .set(SQLDialect.POSTGRES)
            .set(Settings().withRenderSchema(false))
    }

}

suspend inline fun <R> Database.transaction(crossinline block: suspend (Configuration) -> R): Result<R> {
    return ctx().transactionCoroutine(Dispatchers.IO) {
        runCatching {
            block(it)
        }
    }
}