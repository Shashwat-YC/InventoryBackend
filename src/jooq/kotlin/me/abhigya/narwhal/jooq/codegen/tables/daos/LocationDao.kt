/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.daos


import javax.annotation.processing.Generated

import kotlin.collections.List

import me.ave.inventory.jooq.codegen.tables.Location
import me.ave.inventory.jooq.codegen.tables.records.LocationRecord

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
open class LocationDao(configuration: Configuration?) : DAOImpl<LocationRecord, me.ave.inventory.jooq.codegen.tables.pojos.Location, Int>(Location.LOCATION, me.ave.inventory.jooq.codegen.tables.pojos.Location::class.java, configuration) {

    /**
     * Create a new LocationDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: me.ave.inventory.jooq.codegen.tables.pojos.Location): Int? = o.id

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfId(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetchRange(Location.LOCATION.ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    fun fetchById(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetch(Location.LOCATION.ID, *values.toTypedArray())

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    fun fetchOneById(value: Int): me.ave.inventory.jooq.codegen.tables.pojos.Location? = fetchOne(Location.LOCATION.ID, value)

    /**
     * Fetch records that have <code>room_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfRoomId(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetchRange(Location.LOCATION.ROOM_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>room_id IN (values)</code>
     */
    fun fetchByRoomId(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetch(Location.LOCATION.ROOM_ID, *values.toTypedArray())

    /**
     * Fetch records that have <code>rack BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfRack(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetchRange(Location.LOCATION.RACK, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>rack IN (values)</code>
     */
    fun fetchByRack(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetch(Location.LOCATION.RACK, *values.toTypedArray())

    /**
     * Fetch records that have <code>shelf BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfShelf(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetchRange(Location.LOCATION.SHELF, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>shelf IN (values)</code>
     */
    fun fetchByShelf(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Location> = fetch(Location.LOCATION.SHELF, *values.toTypedArray())
}
