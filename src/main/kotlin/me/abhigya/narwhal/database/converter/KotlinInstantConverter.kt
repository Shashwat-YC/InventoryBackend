package me.ave.inventory.database.converter

import kotlinx.datetime.*
import org.jooq.Converter
import java.time.LocalDateTime
import java.time.ZoneOffset

class KotlinInstantConverter : Converter<LocalDateTime, Instant> {

    override fun from(databaseObject: LocalDateTime): Instant {
        return databaseObject.toInstant(ZoneOffset.UTC).toKotlinInstant()
    }

    override fun to(userObject: Instant): LocalDateTime {
        return userObject.toLocalDateTime(TimeZone.UTC).toJavaLocalDateTime()
    }

    override fun fromType(): Class<LocalDateTime> = LocalDateTime::class.java

    override fun toType(): Class<Instant> = Instant::class.java

}