/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.daos


import javax.annotation.processing.Generated

import kotlin.collections.List

import me.ave.inventory.jooq.codegen.tables.Usedpacketrecord
import me.ave.inventory.jooq.codegen.tables.records.UsedpacketrecordRecord

import org.jooq.Configuration
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
open class UsedpacketrecordDao(configuration: Configuration?) : DAOImpl<UsedpacketrecordRecord, me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord, Int>(Usedpacketrecord.USEDPACKETRECORD, me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord::class.java, configuration) {

    /**
     * Create a new UsedpacketrecordDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord): Int? = o.id

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfId(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetchRange(Usedpacketrecord.USEDPACKETRECORD.ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    fun fetchById(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetch(Usedpacketrecord.USEDPACKETRECORD.ID, *values.toTypedArray())

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    fun fetchOneById(value: Int): me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord? = fetchOne(Usedpacketrecord.USEDPACKETRECORD.ID, value)

    /**
     * Fetch records that have <code>packet_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPacketId(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetchRange(Usedpacketrecord.USEDPACKETRECORD.PACKET_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>packet_id IN (values)</code>
     */
    fun fetchByPacketId(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetch(Usedpacketrecord.USEDPACKETRECORD.PACKET_ID, *values.toTypedArray())

    /**
     * Fetch records that have <code>maintenance_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfMaintenanceId(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetchRange(Usedpacketrecord.USEDPACKETRECORD.MAINTENANCE_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>maintenance_id IN (values)</code>
     */
    fun fetchByMaintenanceId(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetch(Usedpacketrecord.USEDPACKETRECORD.MAINTENANCE_ID, *values.toTypedArray())

    /**
     * Fetch records that have <code>quantity BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfQuantity(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetchRange(Usedpacketrecord.USEDPACKETRECORD.QUANTITY, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>quantity IN (values)</code>
     */
    fun fetchByQuantity(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Usedpacketrecord> = fetch(Usedpacketrecord.USEDPACKETRECORD.QUANTITY, *values.toTypedArray())
}
