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
data class Room(
    var id: Int? = null,
    var name: String? = null,
    var floor: Int? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Room = other as Room
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.name == null) {
            if (o.name != null)
                return false
        }
        else if (this.name != o.name)
            return false
        if (this.floor == null) {
            if (o.floor != null)
                return false
        }
        else if (this.floor != o.floor)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.name == null) 0 else this.name.hashCode())
        result = prime * result + (if (this.floor == null) 0 else this.floor.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Room (")

        sb.append(id)
        sb.append(", ").append(name)
        sb.append(", ").append(floor)

        sb.append(")")
        return sb.toString()
    }
}
