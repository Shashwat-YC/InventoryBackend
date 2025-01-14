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
data class Location(
    var id: Int? = null,
    var roomId: Int? = null,
    var rack: Int? = null,
    var shelf: Int? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Location = other as Location
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.roomId == null) {
            if (o.roomId != null)
                return false
        }
        else if (this.roomId != o.roomId)
            return false
        if (this.rack == null) {
            if (o.rack != null)
                return false
        }
        else if (this.rack != o.rack)
            return false
        if (this.shelf == null) {
            if (o.shelf != null)
                return false
        }
        else if (this.shelf != o.shelf)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.roomId == null) 0 else this.roomId.hashCode())
        result = prime * result + (if (this.rack == null) 0 else this.rack.hashCode())
        result = prime * result + (if (this.shelf == null) 0 else this.shelf.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Location (")

        sb.append(id)
        sb.append(", ").append(roomId)
        sb.append(", ").append(rack)
        sb.append(", ").append(shelf)

        sb.append(")")
        return sb.toString()
    }
}
