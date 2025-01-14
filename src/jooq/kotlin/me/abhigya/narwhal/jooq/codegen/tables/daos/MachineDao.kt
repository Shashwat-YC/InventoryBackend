/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.daos


import javax.annotation.processing.Generated

import kotlin.collections.List

import kotlinx.datetime.Instant

import me.ave.inventory.jooq.codegen.tables.Machine
import me.ave.inventory.jooq.codegen.tables.records.MachineRecord

import org.jooq.Configuration
import org.jooq.Record2
import org.jooq.impl.DAOImpl


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
open class MachineDao(configuration: Configuration?) : DAOImpl<MachineRecord, me.ave.inventory.jooq.codegen.tables.pojos.Machine, Record2<String?, Int?>>(Machine.MACHINE, me.ave.inventory.jooq.codegen.tables.pojos.Machine::class.java, configuration) {

    /**
     * Create a new MachineDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: me.ave.inventory.jooq.codegen.tables.pojos.Machine): Record2<String?, Int?> = compositeKeyRecord(o.model, o.machineNo)

    /**
     * Fetch records that have <code>model BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfModel(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetchRange(Machine.MACHINE.MODEL, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>model IN (values)</code>
     */
    fun fetchByModel(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetch(Machine.MACHINE.MODEL, *values)

    /**
     * Fetch records that have <code>machine_no BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfMachineNo(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetchRange(Machine.MACHINE.MACHINE_NO, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>machine_no IN (values)</code>
     */
    fun fetchByMachineNo(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetch(Machine.MACHINE.MACHINE_NO, *values.toTypedArray())

    /**
     * Fetch records that have <code>last_maintenance BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfLastMaintenance(lowerInclusive: Instant?, upperInclusive: Instant?): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetchRange(Machine.MACHINE.LAST_MAINTENANCE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>last_maintenance IN (values)</code>
     */
    fun fetchByLastMaintenance(vararg values: Instant): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetch(Machine.MACHINE.LAST_MAINTENANCE, *values)

    /**
     * Fetch records that have <code>avg_rh BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfAvgRh(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetchRange(Machine.MACHINE.AVG_RH, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>avg_rh IN (values)</code>
     */
    fun fetchByAvgRh(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Machine> = fetch(Machine.MACHINE.AVG_RH, *values.toTypedArray())
}
