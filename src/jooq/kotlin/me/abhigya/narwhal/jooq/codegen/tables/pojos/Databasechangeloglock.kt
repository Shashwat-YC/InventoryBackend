/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.pojos


import java.io.Serializable
import java.time.LocalDateTime

import javax.annotation.processing.Generated


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
data class Databasechangeloglock(
    var id: Int? = null,
    var locked: Boolean? = null,
    var lockgranted: LocalDateTime? = null,
    var lockedby: String? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Databasechangeloglock = other as Databasechangeloglock
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.locked == null) {
            if (o.locked != null)
                return false
        }
        else if (this.locked != o.locked)
            return false
        if (this.lockgranted == null) {
            if (o.lockgranted != null)
                return false
        }
        else if (this.lockgranted != o.lockgranted)
            return false
        if (this.lockedby == null) {
            if (o.lockedby != null)
                return false
        }
        else if (this.lockedby != o.lockedby)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.locked == null) 0 else this.locked.hashCode())
        result = prime * result + (if (this.lockgranted == null) 0 else this.lockgranted.hashCode())
        result = prime * result + (if (this.lockedby == null) 0 else this.lockedby.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Databasechangeloglock (")

        sb.append(id)
        sb.append(", ").append(locked)
        sb.append(", ").append(lockgranted)
        sb.append(", ").append(lockedby)

        sb.append(")")
        return sb.toString()
    }
}
