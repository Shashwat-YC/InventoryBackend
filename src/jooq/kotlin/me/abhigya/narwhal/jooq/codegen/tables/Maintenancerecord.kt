/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables


import javax.annotation.processing.Generated

import kotlin.collections.Collection
import kotlin.collections.List

import kotlinx.datetime.Instant

import me.ave.inventory.database.converter.KotlinInstantConverter
import me.ave.inventory.jooq.codegen.Public
import me.ave.inventory.jooq.codegen.keys.MAINTENANCERECORD_PKEY
import me.ave.inventory.jooq.codegen.keys.MAINTENANCERECORD__FK_MAINTAININFO
import me.ave.inventory.jooq.codegen.keys.MAINTENANCERECORD__FK_MAINTAINJOB
import me.ave.inventory.jooq.codegen.keys.MAINTENANCERECORD__FK_MAINTAINMACHINE
import me.ave.inventory.jooq.codegen.keys.MAINTENANCEUPDATEDPACKET__FK_MAINTENANCEUPDATEDPACKET
import me.ave.inventory.jooq.codegen.tables.Job.JobPath
import me.ave.inventory.jooq.codegen.tables.Machine.MachinePath
import me.ave.inventory.jooq.codegen.tables.Machineinfo.MachineinfoPath
import me.ave.inventory.jooq.codegen.tables.Maintenanceupdatedpacket.MaintenanceupdatedpacketPath
import me.ave.inventory.jooq.codegen.tables.Sparepartinfo.SparepartinfoPath
import me.ave.inventory.jooq.codegen.tables.records.MaintenancerecordRecord

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


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
open class Maintenancerecord(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, MaintenancerecordRecord>?,
    parentPath: InverseForeignKey<out Record, MaintenancerecordRecord>?,
    aliased: Table<MaintenancerecordRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<MaintenancerecordRecord>(
    alias,
    Public.PUBLIC,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table(),
    where,
) {
    companion object {

        /**
         * The reference instance of <code>public.maintenancerecord</code>
         */
        val MAINTENANCERECORD: Maintenancerecord = Maintenancerecord()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<MaintenancerecordRecord> = MaintenancerecordRecord::class.java

    /**
     * The column <code>public.maintenancerecord.id</code>.
     */
    val ID: TableField<MaintenancerecordRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "")

    /**
     * The column <code>public.maintenancerecord.model</code>.
     */
    val MODEL: TableField<MaintenancerecordRecord, String?> = createField(DSL.name("model"), SQLDataType.VARCHAR(3).nullable(false), this, "")

    /**
     * The column <code>public.maintenancerecord.machine_no</code>.
     */
    val MACHINE_NO: TableField<MaintenancerecordRecord, Int?> = createField(DSL.name("machine_no"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>public.maintenancerecord.pms_code</code>.
     */
    val PMS_CODE: TableField<MaintenancerecordRecord, String?> = createField(DSL.name("pms_code"), SQLDataType.VARCHAR(20).nullable(false), this, "")

    /**
     * The column <code>public.maintenancerecord.time_started</code>.
     */
    val TIME_STARTED: TableField<MaintenancerecordRecord, Instant?> = createField(DSL.name("time_started"), SQLDataType.LOCALDATETIME(0), this, "", KotlinInstantConverter())

    /**
     * The column <code>public.maintenancerecord.time_completed</code>.
     */
    val TIME_COMPLETED: TableField<MaintenancerecordRecord, Instant?> = createField(DSL.name("time_completed"), SQLDataType.LOCALDATETIME(0), this, "", KotlinInstantConverter())

    private constructor(alias: Name, aliased: Table<MaintenancerecordRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<MaintenancerecordRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<MaintenancerecordRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.maintenancerecord</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.maintenancerecord</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.maintenancerecord</code> table reference
     */
    constructor(): this(DSL.name("maintenancerecord"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, MaintenancerecordRecord>?, parentPath: InverseForeignKey<out Record, MaintenancerecordRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, MAINTENANCERECORD, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class MaintenancerecordPath : Maintenancerecord, Path<MaintenancerecordRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, MaintenancerecordRecord>?, parentPath: InverseForeignKey<out Record, MaintenancerecordRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<MaintenancerecordRecord>): super(alias, aliased)
        override fun `as`(alias: String): MaintenancerecordPath = MaintenancerecordPath(DSL.name(alias), this)
        override fun `as`(alias: Name): MaintenancerecordPath = MaintenancerecordPath(alias, this)
        override fun `as`(alias: Table<*>): MaintenancerecordPath = MaintenancerecordPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIdentity(): Identity<MaintenancerecordRecord, Int?> = super.getIdentity() as Identity<MaintenancerecordRecord, Int?>
    override fun getPrimaryKey(): UniqueKey<MaintenancerecordRecord> = MAINTENANCERECORD_PKEY
    override fun getReferences(): List<ForeignKey<MaintenancerecordRecord, *>> = listOf(MAINTENANCERECORD__FK_MAINTAININFO, MAINTENANCERECORD__FK_MAINTAINMACHINE, MAINTENANCERECORD__FK_MAINTAINJOB)

    private lateinit var _machineinfo: MachineinfoPath

    /**
     * Get the implicit join path to the <code>public.machineinfo</code> table.
     */
    fun machineinfo(): MachineinfoPath {
        if (!this::_machineinfo.isInitialized)
            _machineinfo = MachineinfoPath(this, MAINTENANCERECORD__FK_MAINTAININFO, null)

        return _machineinfo;
    }

    val machineinfo: MachineinfoPath
        get(): MachineinfoPath = machineinfo()

    private lateinit var _machine: MachinePath

    /**
     * Get the implicit join path to the <code>public.machine</code> table.
     */
    fun machine(): MachinePath {
        if (!this::_machine.isInitialized)
            _machine = MachinePath(this, MAINTENANCERECORD__FK_MAINTAINMACHINE, null)

        return _machine;
    }

    val machine: MachinePath
        get(): MachinePath = machine()

    private lateinit var _job: JobPath

    /**
     * Get the implicit join path to the <code>public.job</code> table.
     */
    fun job(): JobPath {
        if (!this::_job.isInitialized)
            _job = JobPath(this, MAINTENANCERECORD__FK_MAINTAINJOB, null)

        return _job;
    }

    val job: JobPath
        get(): JobPath = job()

    private lateinit var _maintenanceupdatedpacket: MaintenanceupdatedpacketPath

    /**
     * Get the implicit to-many join path to the
     * <code>public.maintenanceupdatedpacket</code> table
     */
    fun maintenanceupdatedpacket(): MaintenanceupdatedpacketPath {
        if (!this::_maintenanceupdatedpacket.isInitialized)
            _maintenanceupdatedpacket = MaintenanceupdatedpacketPath(this, null, MAINTENANCEUPDATEDPACKET__FK_MAINTENANCEUPDATEDPACKET.inverseKey)

        return _maintenanceupdatedpacket;
    }

    val maintenanceupdatedpacket: MaintenanceupdatedpacketPath
        get(): MaintenanceupdatedpacketPath = maintenanceupdatedpacket()

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.sparepartinfo</code> table
     */
    val sparepartinfo: SparepartinfoPath
        get(): SparepartinfoPath = maintenanceupdatedpacket().sparepartinfo()
    override fun `as`(alias: String): Maintenancerecord = Maintenancerecord(DSL.name(alias), this)
    override fun `as`(alias: Name): Maintenancerecord = Maintenancerecord(alias, this)
    override fun `as`(alias: Table<*>): Maintenancerecord = Maintenancerecord(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Maintenancerecord = Maintenancerecord(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Maintenancerecord = Maintenancerecord(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Maintenancerecord = Maintenancerecord(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Maintenancerecord = Maintenancerecord(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Maintenancerecord = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Maintenancerecord = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Maintenancerecord = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Maintenancerecord = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Maintenancerecord = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Maintenancerecord = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Maintenancerecord = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Maintenancerecord = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Maintenancerecord = where(DSL.notExists(select))
}
