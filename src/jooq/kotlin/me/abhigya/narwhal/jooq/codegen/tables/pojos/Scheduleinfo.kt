/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.pojos


import java.io.Serializable

import javax.annotation.processing.Generated

import me.ave.inventory.jooq.codegen.enums.ScheduleType


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = [
        "https://www.jooq.org",
        "jOOQ version:3.19.7",
        "schema version:public_2"
    ],
    comments = "This class is generated by jOOQ"
)
@Suppress("UNCHECKED_CAST")
data class Scheduleinfo(
    var id: Int? = null,
    var type: ScheduleType? = null,
    var delay: Int? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Scheduleinfo = other as Scheduleinfo
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.type == null) {
            if (o.type != null)
                return false
        }
        else if (this.type != o.type)
            return false
        if (this.delay == null) {
            if (o.delay != null)
                return false
        }
        else if (this.delay != o.delay)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.type == null) 0 else this.type.hashCode())
        result = prime * result + (if (this.delay == null) 0 else this.delay.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Scheduleinfo (")

        sb.append(id)
        sb.append(", ").append(type)
        sb.append(", ").append(delay)

        sb.append(")")
        return sb.toString()
    }
}