/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.daos


import javax.annotation.processing.Generated

import kotlin.collections.List

import me.ave.inventory.jooq.codegen.tables.Sparepartinfo
import me.ave.inventory.jooq.codegen.tables.records.SparepartinfoRecord

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
open class SparepartinfoDao(configuration: Configuration?) : DAOImpl<SparepartinfoRecord, me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo, String>(Sparepartinfo.SPAREPARTINFO, me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo::class.java, configuration) {

    /**
     * Create a new SparepartinfoDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo): String? = o.code

    /**
     * Fetch records that have <code>code BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfCode(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetchRange(Sparepartinfo.SPAREPARTINFO.CODE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    fun fetchByCode(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetch(Sparepartinfo.SPAREPARTINFO.CODE, *values)

    /**
     * Fetch a unique record that has <code>code = value</code>
     */
    fun fetchOneByCode(value: String): me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo? = fetchOne(Sparepartinfo.SPAREPARTINFO.CODE, value)

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfName(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetchRange(Sparepartinfo.SPAREPARTINFO.NAME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    fun fetchByName(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetch(Sparepartinfo.SPAREPARTINFO.NAME, *values)

    /**
     * Fetch records that have <code>part_no BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPartNo(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetchRange(Sparepartinfo.SPAREPARTINFO.PART_NO, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>part_no IN (values)</code>
     */
    fun fetchByPartNo(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetch(Sparepartinfo.SPAREPARTINFO.PART_NO, *values)

    /**
     * Fetch records that have <code>machine_model BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfMachineModel(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetchRange(Sparepartinfo.SPAREPARTINFO.MACHINE_MODEL, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>machine_model IN (values)</code>
     */
    fun fetchByMachineModel(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetch(Sparepartinfo.SPAREPARTINFO.MACHINE_MODEL, *values)

    /**
     * Fetch records that have <code>rob BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfRob(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetchRange(Sparepartinfo.SPAREPARTINFO.ROB, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>rob IN (values)</code>
     */
    fun fetchByRob(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Sparepartinfo> = fetch(Sparepartinfo.SPAREPARTINFO.ROB, *values.toTypedArray())
}
