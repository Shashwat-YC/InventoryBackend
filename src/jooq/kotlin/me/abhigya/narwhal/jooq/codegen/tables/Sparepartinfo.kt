/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables


import javax.annotation.processing.Generated

import kotlin.collections.Collection
import kotlin.collections.List

import me.ave.inventory.jooq.codegen.Public
import me.ave.inventory.jooq.codegen.keys.JOBTOSPAREPART__FK_SPAREPARTJOB
import me.ave.inventory.jooq.codegen.keys.MAINTENANCEUPDATEDPACKET__FK_UPDATEDPACKETSPAREPART
import me.ave.inventory.jooq.codegen.keys.PACKETRECORD__FK_PACKETCODE
import me.ave.inventory.jooq.codegen.keys.SPAREPARTINFO_PKEY
import me.ave.inventory.jooq.codegen.keys.SPAREPARTINFO__FK_CODEINFO
import me.ave.inventory.jooq.codegen.tables.Job.JobPath
import me.ave.inventory.jooq.codegen.tables.Jobtosparepart.JobtosparepartPath
import me.ave.inventory.jooq.codegen.tables.Machineinfo.MachineinfoPath
import me.ave.inventory.jooq.codegen.tables.Maintenancerecord.MaintenancerecordPath
import me.ave.inventory.jooq.codegen.tables.Maintenanceupdatedpacket.MaintenanceupdatedpacketPath
import me.ave.inventory.jooq.codegen.tables.Packetrecord.PacketrecordPath
import me.ave.inventory.jooq.codegen.tables.records.SparepartinfoRecord

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
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
open class Sparepartinfo(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, SparepartinfoRecord>?,
    parentPath: InverseForeignKey<out Record, SparepartinfoRecord>?,
    aliased: Table<SparepartinfoRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<SparepartinfoRecord>(
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
         * The reference instance of <code>public.sparepartinfo</code>
         */
        val SPAREPARTINFO: Sparepartinfo = Sparepartinfo()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<SparepartinfoRecord> = SparepartinfoRecord::class.java

    /**
     * The column <code>public.sparepartinfo.code</code>.
     */
    val CODE: TableField<SparepartinfoRecord, String?> = createField(DSL.name("code"), SQLDataType.VARCHAR(20).nullable(false), this, "")

    /**
     * The column <code>public.sparepartinfo.name</code>.
     */
    val NAME: TableField<SparepartinfoRecord, String?> = createField(DSL.name("name"), SQLDataType.VARCHAR(150).nullable(false), this, "")

    /**
     * The column <code>public.sparepartinfo.part_no</code>.
     */
    val PART_NO: TableField<SparepartinfoRecord, String?> = createField(DSL.name("part_no"), SQLDataType.VARCHAR(50).nullable(false), this, "")

    /**
     * The column <code>public.sparepartinfo.machine_model</code>.
     */
    val MACHINE_MODEL: TableField<SparepartinfoRecord, String?> = createField(DSL.name("machine_model"), SQLDataType.VARCHAR(3).nullable(false), this, "")

    /**
     * The column <code>public.sparepartinfo.rob</code>.
     */
    val ROB: TableField<SparepartinfoRecord, Int?> = createField(DSL.name("rob"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("0"), SQLDataType.INTEGER)), this, "")

    private constructor(alias: Name, aliased: Table<SparepartinfoRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<SparepartinfoRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<SparepartinfoRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.sparepartinfo</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.sparepartinfo</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.sparepartinfo</code> table reference
     */
    constructor(): this(DSL.name("sparepartinfo"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, SparepartinfoRecord>?, parentPath: InverseForeignKey<out Record, SparepartinfoRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, SPAREPARTINFO, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class SparepartinfoPath : Sparepartinfo, Path<SparepartinfoRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, SparepartinfoRecord>?, parentPath: InverseForeignKey<out Record, SparepartinfoRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<SparepartinfoRecord>): super(alias, aliased)
        override fun `as`(alias: String): SparepartinfoPath = SparepartinfoPath(DSL.name(alias), this)
        override fun `as`(alias: Name): SparepartinfoPath = SparepartinfoPath(alias, this)
        override fun `as`(alias: Table<*>): SparepartinfoPath = SparepartinfoPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<SparepartinfoRecord> = SPAREPARTINFO_PKEY
    override fun getReferences(): List<ForeignKey<SparepartinfoRecord, *>> = listOf(SPAREPARTINFO__FK_CODEINFO)

    private lateinit var _machineinfo: MachineinfoPath

    /**
     * Get the implicit join path to the <code>public.machineinfo</code> table.
     */
    fun machineinfo(): MachineinfoPath {
        if (!this::_machineinfo.isInitialized)
            _machineinfo = MachineinfoPath(this, SPAREPARTINFO__FK_CODEINFO, null)

        return _machineinfo;
    }

    val machineinfo: MachineinfoPath
        get(): MachineinfoPath = machineinfo()

    private lateinit var _jobtosparepart: JobtosparepartPath

    /**
     * Get the implicit to-many join path to the
     * <code>public.jobtosparepart</code> table
     */
    fun jobtosparepart(): JobtosparepartPath {
        if (!this::_jobtosparepart.isInitialized)
            _jobtosparepart = JobtosparepartPath(this, null, JOBTOSPAREPART__FK_SPAREPARTJOB.inverseKey)

        return _jobtosparepart;
    }

    val jobtosparepart: JobtosparepartPath
        get(): JobtosparepartPath = jobtosparepart()

    private lateinit var _maintenanceupdatedpacket: MaintenanceupdatedpacketPath

    /**
     * Get the implicit to-many join path to the
     * <code>public.maintenanceupdatedpacket</code> table
     */
    fun maintenanceupdatedpacket(): MaintenanceupdatedpacketPath {
        if (!this::_maintenanceupdatedpacket.isInitialized)
            _maintenanceupdatedpacket = MaintenanceupdatedpacketPath(this, null, MAINTENANCEUPDATEDPACKET__FK_UPDATEDPACKETSPAREPART.inverseKey)

        return _maintenanceupdatedpacket;
    }

    val maintenanceupdatedpacket: MaintenanceupdatedpacketPath
        get(): MaintenanceupdatedpacketPath = maintenanceupdatedpacket()

    private lateinit var _packetrecord: PacketrecordPath

    /**
     * Get the implicit to-many join path to the
     * <code>public.packetrecord</code> table
     */
    fun packetrecord(): PacketrecordPath {
        if (!this::_packetrecord.isInitialized)
            _packetrecord = PacketrecordPath(this, null, PACKETRECORD__FK_PACKETCODE.inverseKey)

        return _packetrecord;
    }

    val packetrecord: PacketrecordPath
        get(): PacketrecordPath = packetrecord()

    /**
     * Get the implicit many-to-many join path to the <code>public.job</code>
     * table
     */
    val job: JobPath
        get(): JobPath = jobtosparepart().job()

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.maintenancerecord</code> table
     */
    val maintenancerecord: MaintenancerecordPath
        get(): MaintenancerecordPath = maintenanceupdatedpacket().maintenancerecord()
    override fun `as`(alias: String): Sparepartinfo = Sparepartinfo(DSL.name(alias), this)
    override fun `as`(alias: Name): Sparepartinfo = Sparepartinfo(alias, this)
    override fun `as`(alias: Table<*>): Sparepartinfo = Sparepartinfo(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Sparepartinfo = Sparepartinfo(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Sparepartinfo = Sparepartinfo(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Sparepartinfo = Sparepartinfo(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Sparepartinfo = Sparepartinfo(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Sparepartinfo = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Sparepartinfo = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Sparepartinfo = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Sparepartinfo = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Sparepartinfo = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Sparepartinfo = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Sparepartinfo = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Sparepartinfo = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Sparepartinfo = where(DSL.notExists(select))
}
