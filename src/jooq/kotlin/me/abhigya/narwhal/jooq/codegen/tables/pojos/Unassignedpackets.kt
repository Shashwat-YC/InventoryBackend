/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.pojos


import java.io.Serializable

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
data class Unassignedpackets(
    var id: Int? = null,
    var rfid: String? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Unassignedpackets = other as Unassignedpackets
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.rfid == null) {
            if (o.rfid != null)
                return false
        }
        else if (this.rfid != o.rfid)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.rfid == null) 0 else this.rfid.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Unassignedpackets (")

        sb.append(id)
        sb.append(", ").append(rfid)

        sb.append(")")
        return sb.toString()
    }
}
