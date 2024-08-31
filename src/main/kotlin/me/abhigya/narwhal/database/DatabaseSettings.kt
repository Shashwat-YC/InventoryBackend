package me.ave.inventory.database

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

data class DatabaseSettings(
    val host: String,
    val port: Int,
    val database: String,
    val user: String,
    val password: String
) {
    companion object {
        fun fromEnv(dotenv: Dotenv = dotenv()): DatabaseSettings = DatabaseSettings(
            dotenv["DATABASE_HOSTNAME", "localhost"],
            dotenv["DATABASE_PORT"]?.toInt() ?: 5432,
            dotenv["DATABASE_DB", "postgres"],
            dotenv["DATABASE_USER", "postgres"],
            dotenv["DATABASE_PASSWORD", "postgres"]
        )
    }
}