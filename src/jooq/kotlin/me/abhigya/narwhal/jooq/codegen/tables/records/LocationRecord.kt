/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.records


import javax.annotation.processing.Generated

import me.ave.inventory.jooq.codegen.tables.Location

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
open class LocationRecord() : UpdatableRecordImpl<LocationRecord>(Location.LOCATION) {

    open var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    open var roomId: Int?
        set(value): Unit = set(1, value)
        get(): Int? = get(1) as Int?

    open var rack: Int?
        set(value): Unit = set(2, value)
        get(): Int? = get(2) as Int?

    open var shelf: Int?
        set(value): Unit = set(3, value)
        get(): Int? = get(3) as Int?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    /**
     * Create a detached, initialised LocationRecord
     */
    constructor(id: Int? = null, roomId: Int? = null, rack: Int? = null, shelf: Int? = null): this() {
        this.id = id
        this.roomId = roomId
        this.rack = rack
        this.shelf = shelf
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised LocationRecord
     */
    constructor(value: me.ave.inventory.jooq.codegen.tables.pojos.Location?): this() {
        if (value != null) {
            this.id = value.id
            this.roomId = value.roomId
            this.rack = value.rack
            this.shelf = value.shelf
            resetChangedOnNotNull()
        }
    }
}
