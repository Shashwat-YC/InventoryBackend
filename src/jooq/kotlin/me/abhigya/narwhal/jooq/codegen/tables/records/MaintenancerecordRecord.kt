/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.records


import javax.annotation.processing.Generated

import kotlinx.datetime.Instant

import me.ave.inventory.jooq.codegen.tables.Maintenancerecord

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
open class MaintenancerecordRecord() : UpdatableRecordImpl<MaintenancerecordRecord>(Maintenancerecord.MAINTENANCERECORD) {

    open var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    open var model: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    open var machineNo: Int?
        set(value): Unit = set(2, value)
        get(): Int? = get(2) as Int?

    open var pmsCode: String?
        set(value): Unit = set(3, value)
        get(): String? = get(3) as String?

    open var timeStarted: Instant?
        set(value): Unit = set(4, value)
        get(): Instant? = get(4) as Instant?

    open var timeCompleted: Instant?
        set(value): Unit = set(5, value)
        get(): Instant? = get(5) as Instant?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    /**
     * Create a detached, initialised MaintenancerecordRecord
     */
    constructor(id: Int? = null, model: String? = null, machineNo: Int? = null, pmsCode: String? = null, timeStarted: Instant? = null, timeCompleted: Instant? = null): this() {
        this.id = id
        this.model = model
        this.machineNo = machineNo
        this.pmsCode = pmsCode
        this.timeStarted = timeStarted
        this.timeCompleted = timeCompleted
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised MaintenancerecordRecord
     */
    constructor(value: me.ave.inventory.jooq.codegen.tables.pojos.Maintenancerecord?): this() {
        if (value != null) {
            this.id = value.id
            this.model = value.model
            this.machineNo = value.machineNo
            this.pmsCode = value.pmsCode
            this.timeStarted = value.timeStarted
            this.timeCompleted = value.timeCompleted
            resetChangedOnNotNull()
        }
    }
}