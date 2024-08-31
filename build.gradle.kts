@file:Suppress("PropertyName")

import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.codegen.KotlinGenerator
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.postgres.PostgresDatabase

plugins {
    kotlin("jvm") version Versions.Kotlin.VERSION
    kotlin("kapt") version Versions.Kotlin.VERSION
    kotlin("plugin.serialization") version Versions.Kotlin.VERSION
    id("io.ktor.plugin") version Versions.Ktor.VERSION
    id("me.ave.inventory.task.PostgresRun")
    id("org.liquibase.gradle") version Versions.Plugins.LIQUIBASE
    id("nu.studer.jooq") version Versions.Plugins.JOOQ_GEN
}

group = "me.ave.inventory"
version = "0.0.1"

application {
    mainClass.set("me.ave.inventory.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:${Versions.KotlinX.DATETIME}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-compression")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-jetty-jvm")
    implementation("ch.qos.logback:logback-classic:${Versions.Deps.LOGBACK}")
    implementation("io.github.cdimascio:dotenv-kotlin:${Versions.Deps.DOTENV}")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:${Versions.Deps.REACTOR_EXT}")

    // Database
    implementation("org.postgresql:postgresql:${Versions.Deps.POSTGRES}")
    implementation("com.zaxxer:HikariCP:${Versions.Deps.HIKARI_CP}")
    implementation("org.postgresql:r2dbc-postgresql:${Versions.Deps.POSTGRES_R2DBC}")
    implementation("org.jooq:jooq:${Versions.Deps.JOOQ}")
    implementation("org.jooq:jooq-kotlin:${Versions.Deps.JOOQ}")
    implementation("org.jooq:jooq-kotlin-coroutines:${Versions.Deps.JOOQ}")
    jooqGenerator("org.postgresql:postgresql:${Versions.Deps.POSTGRES}")

    // Liquibase
    liquibaseRuntime("org.postgresql:postgresql:${Versions.Deps.POSTGRES}")
    liquibaseRuntime("org.liquibase:liquibase-core:${Versions.Deps.LIQUIBASE}")
    liquibaseRuntime("info.picocli:picocli:${Versions.Deps.PICO_CLI}")
    liquibaseRuntime("javax.xml.bind:jaxb-api:${Versions.Deps.JAXB_API}")
    liquibaseRuntime("ch.qos.logback:logback-core:${Versions.Deps.LOGBACK}")
    liquibaseRuntime("ch.qos.logback:logback-classic:${Versions.Deps.LOGBACK}")
    implementation("org.liquibase:liquibase-core:${Versions.Deps.LIQUIBASE}")

    // DI
    implementation("com.github.stephanenicolas.toothpick:ktp:${Versions.Deps.TOOTHPICK}")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:${Versions.Deps.TOOTHPICK}")

    // Testing
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Versions.Kotlin.VERSION}")
}

val database_driver: String by project
val database_url: String by project
val database_user: String by project
val database_password: String by project

postgres {
    database = database_url.substringAfterLast("/")
    user = database_user
    password = database_password
}

liquibase {
    activities {
        create("main") {
            arguments = mapOf(
                "changeLogFile" to "local_changelog.xml",
                "url" to database_url,
                "username" to database_user,
                "password" to database_password,
                "driver" to database_driver,
                "logLevel" to "info"
            )
        }
    }
}

jooq {
    version.set(Versions.Deps.JOOQ)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = database_driver
                    url = database_url
                    user = database_user
                    password = database_password
                }

                generator.apply {
                    name = KotlinGenerator::class.java.canonicalName
                    database.apply {
                        name = PostgresDatabase::class.java.canonicalName
                        inputSchema = "public"
                        includes = ".*"
                        excludes = "(?i:information_schema\\\\..*)|(?i:system_lobs\\\\..*)"
                        schemaVersionProvider = "SELECT :schema_name || '_' || MAX(\"orderexecuted\") FROM \"databasechangelog\";"
                        forcedTypes = listOf(
                            ForcedType()
                                .withName("TIMESTAMP")
                                .withIncludeTypes("timestamp")
                                .withUserType("kotlinx.datetime.Instant")
                                .withConverter("me.ave.inventory.database.converter.KotlinInstantConverter")
                                .withExcludeExpression("lockgranted|dateexecuted")
                        )
                        generate.withJavadoc(true)
                            .withComments(true)
                            .withDaos(true)
                            .withPojos(true)
                        withUnsignedTypes(true)
                        target.withPackageName("me.ave.inventory.jooq.codegen")
                            .withDirectory("src/jooq/kotlin")
                    }
                }
            }
        }
    }
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }

    named<JooqGenerate>("generateJooq") {
        allInputsDeclared.set(true)
        outputs.upToDateWhen { true }
        mustRunAfter(postgresStart)
        mustRunAfter(update)
    }

    register("migrateAndGenerate") {
        group = "build"
        dependsOn(
            postgresStart,
            update,
            "generateJooq"
        )
        finalizedBy(postgresStop)
    }

    postgresStop {
        mustRunAfter(postgresStart)
    }

    update {
        mustRunAfter(postgresStart)
    }
}

