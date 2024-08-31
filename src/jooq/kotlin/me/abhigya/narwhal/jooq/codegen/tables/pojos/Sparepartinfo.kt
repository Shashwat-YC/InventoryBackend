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
data class Sparepartinfo(
    var code: String? = null,
    var name: String? = null,
    var partNo: String? = null,
    var machineModel: String? = null,
    var rob: Int? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Sparepartinfo = other as Sparepartinfo
        if (this.code == null) {
            if (o.code != null)
                return false
        }
        else if (this.code != o.code)
            return false
        if (this.name == null) {
            if (o.name != null)
                return false
        }
        else if (this.name != o.name)
            return false
        if (this.partNo == null) {
            if (o.partNo != null)
                return false
        }
        else if (this.partNo != o.partNo)
            return false
        if (this.machineModel == null) {
            if (o.machineModel != null)
                return false
        }
        else if (this.machineModel != o.machineModel)
            return false
        if (this.rob == null) {
            if (o.rob != null)
                return false
        }
        else if (this.rob != o.rob)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.code == null) 0 else this.code.hashCode())
        result = prime * result + (if (this.name == null) 0 else this.name.hashCode())
        result = prime * result + (if (this.partNo == null) 0 else this.partNo.hashCode())
        result = prime * result + (if (this.machineModel == null) 0 else this.machineModel.hashCode())
        result = prime * result + (if (this.rob == null) 0 else this.rob.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Sparepartinfo (")

        sb.append(code)
        sb.append(", ").append(name)
        sb.append(", ").append(partNo)
        sb.append(", ").append(machineModel)
        sb.append(", ").append(rob)

        sb.append(")")
        return sb.toString()
    }
}