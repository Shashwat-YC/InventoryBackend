plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.testcontainers:postgresql:1.19.7")
}

gradlePlugin {
    plugins {
        create("postgresRun") {
            id = "me.ave.inventory.task.PostgresRun"
            implementationClass = "me.ave.inventory.task.PostgresRunPlugin"
        }
    }
}