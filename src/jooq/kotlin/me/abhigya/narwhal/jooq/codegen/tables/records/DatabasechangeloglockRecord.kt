/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.records


import java.time.LocalDateTime

import javax.annotation.processing.Generated

import me.ave.inventory.jooq.codegen.tables.Databasechangeloglock

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
open class DatabasechangeloglockRecord() : UpdatableRecordImpl<DatabasechangeloglockRecord>(Databasechangeloglock.DATABASECHANGELOGLOCK) {

    open var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    open var locked: Boolean?
        set(value): Unit = set(1, value)
        get(): Boolean? = get(1) as Boolean?

    open var lockgranted: LocalDateTime?
        set(value): Unit = set(2, value)
        get(): LocalDateTime? = get(2) as LocalDateTime?

    open var lockedby: String?
        set(value): Unit = set(3, value)
        get(): String? = get(3) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    /**
     * Create a detached, initialised DatabasechangeloglockRecord
     */
    constructor(id: Int? = null, locked: Boolean? = null, lockgranted: LocalDateTime? = null, lockedby: String? = null): this() {
        this.id = id
        this.locked = locked
        this.lockgranted = lockgranted
        this.lockedby = lockedby
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised DatabasechangeloglockRecord
     */
    constructor(value: me.ave.inventory.jooq.codegen.tables.pojos.Databasechangeloglock?): this() {
        if (value != null) {
            this.id = value.id
            this.locked = value.locked
            this.lockgranted = value.lockgranted
            this.lockedby = value.lockedby
            resetChangedOnNotNull()
        }
    }
}
