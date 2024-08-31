/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables.daos


import javax.annotation.processing.Generated

import kotlin.collections.List

import me.ave.inventory.jooq.codegen.enums.JobKind
import me.ave.inventory.jooq.codegen.enums.JobPlace
import me.ave.inventory.jooq.codegen.enums.Pic
import me.ave.inventory.jooq.codegen.tables.Job
import me.ave.inventory.jooq.codegen.tables.records.JobRecord

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
open class JobDao(configuration: Configuration?) : DAOImpl<JobRecord, me.ave.inventory.jooq.codegen.tables.pojos.Job, String>(Job.JOB, me.ave.inventory.jooq.codegen.tables.pojos.Job::class.java, configuration) {

    /**
     * Create a new JobDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: me.ave.inventory.jooq.codegen.tables.pojos.Job): String? = o.pmsCode

    /**
     * Fetch records that have <code>pms_code BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPmsCode(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.PMS_CODE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>pms_code IN (values)</code>
     */
    fun fetchByPmsCode(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.PMS_CODE, *values)

    /**
     * Fetch a unique record that has <code>pms_code = value</code>
     */
    fun fetchOneByPmsCode(value: String): me.ave.inventory.jooq.codegen.tables.pojos.Job? = fetchOne(Job.JOB.PMS_CODE, value)

    /**
     * Fetch records that have <code>description BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfDescription(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.DESCRIPTION, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    fun fetchByDescription(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.DESCRIPTION, *values)

    /**
     * Fetch records that have <code>place BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPlace(lowerInclusive: JobPlace?, upperInclusive: JobPlace?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.PLACE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>place IN (values)</code>
     */
    fun fetchByPlace(vararg values: JobPlace): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.PLACE, *values)

    /**
     * Fetch records that have <code>kind BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfKind(lowerInclusive: JobKind?, upperInclusive: JobKind?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.KIND, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>kind IN (values)</code>
     */
    fun fetchByKind(vararg values: JobKind): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.KIND, *values)

    /**
     * Fetch records that have <code>schedule_key BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfScheduleKey(lowerInclusive: Int?, upperInclusive: Int?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.SCHEDULE_KEY, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>schedule_key IN (values)</code>
     */
    fun fetchByScheduleKey(vararg values: Int): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.SCHEDULE_KEY, *values.toTypedArray())

    /**
     * Fetch records that have <code>model BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfModel(lowerInclusive: String?, upperInclusive: String?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.MODEL, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>model IN (values)</code>
     */
    fun fetchByModel(vararg values: String): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.MODEL, *values)

    /**
     * Fetch records that have <code>pic BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPic(lowerInclusive: Pic?, upperInclusive: Pic?): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetchRange(Job.JOB.PIC, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>pic IN (values)</code>
     */
    fun fetchByPic(vararg values: Pic): List<me.ave.inventory.jooq.codegen.tables.pojos.Job> = fetch(Job.JOB.PIC, *values)
}