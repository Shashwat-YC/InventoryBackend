package me.ave.inventory.models

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.serialization.Serializable
import me.ave.inventory.jooq.codegen.enums.JobKind
import me.ave.inventory.jooq.codegen.enums.JobPlace
import me.ave.inventory.jooq.codegen.enums.Pic
import me.ave.inventory.jooq.codegen.enums.ScheduleType
import me.ave.inventory.jooq.codegen.tables.references.SCHEDULEINFO
import org.jooq.Record

@Serializable
data class Job(
    val id: Int,
    val code: String,
    val description: String,
    val place: JobPlace,
    val kind: JobKind,
    val scheduleInfo: ScheduleInfo,
    val pic: Pic,
    val nextSchedule: Instant
) {

    enum class Status {
        COMPLETED,
        IN_PROGRESS,
        PLANNING
    }

    enum class DueWithin {
        DAILY,
        WEEKLY,
        MONTHLY,
        ALL
    }
}

@Serializable
data class ScheduleInfo(
    val type: ScheduleType,
    val interval: Int
) {
    companion object : FromRecord<ScheduleInfo> {
        override fun fromRecord(record: Record): ScheduleInfo {
            return ScheduleInfo(
                type = ScheduleType.valueOf(record[SCHEDULEINFO.TYPE]!!.name),
                interval = record[SCHEDULEINFO.DELAY]!!
            )
        }
    }
}

fun ScheduleType.calculateSchedule(lastMaintenance: Instant, delay: Int, avgRH: Int): Instant = when (this) {
    ScheduleType.CA -> lastMaintenance.plus(delay.toLong(), DateTimeUnit.MONTH, TimeZone.UTC)
    ScheduleType.RH -> lastMaintenance.plus((delay / avgRH).toLong(), DateTimeUnit.DAY, TimeZone.UTC)
}