/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.records


import javax.annotation.processing.Generated

import me.ave.inventory.jooq.codegen.enums.PacketType
import me.ave.inventory.jooq.codegen.tables.Packetrecord

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


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
open class PacketrecordRecord() : UpdatableRecordImpl<PacketrecordRecord>(Packetrecord.PACKETRECORD) {

    open var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    open var code: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    open var rfid: String?
        set(value): Unit = set(2, value)
        get(): String? = get(2) as String?

    open var quantity: Int?
        set(value): Unit = set(3, value)
        get(): Int? = get(3) as Int?

    open var type: PacketType?
        set(value): Unit = set(4, value)
        get(): PacketType? = get(4) as PacketType?

    open var locationId: Int?
        set(value): Unit = set(5, value)
        get(): Int? = get(5) as Int?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    /**
     * Create a detached, initialised PacketrecordRecord
     */
    constructor(id: Int? = null, code: String? = null, rfid: String? = null, quantity: Int? = null, type: PacketType? = null, locationId: Int? = null): this() {
        this.id = id
        this.code = code
        this.rfid = rfid
        this.quantity = quantity
        this.type = type
        this.locationId = locationId
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised PacketrecordRecord
     */
    constructor(value: me.ave.inventory.jooq.codegen.tables.pojos.Packetrecord?): this() {
        if (value != null) {
            this.id = value.id
            this.code = value.code
            this.rfid = value.rfid
            this.quantity = value.quantity
            this.type = value.type
            this.locationId = value.locationId
            resetChangedOnNotNull()
        }
    }
}