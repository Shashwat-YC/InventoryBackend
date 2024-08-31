/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables


import javax.annotation.processing.Generated

import kotlin.collections.Collection
import kotlin.collections.List

import me.ave.inventory.jooq.codegen.Public
import me.ave.inventory.jooq.codegen.keys.MACHINETOLOCATION__FK_LOCATION
import me.ave.inventory.jooq.codegen.keys.MACHINETOLOCATION__FK_MACHINEMODEL
import me.ave.inventory.jooq.codegen.keys.UQ_MACHINELOCATION
import me.ave.inventory.jooq.codegen.tables.Location.LocationPath
import me.ave.inventory.jooq.codegen.tables.Machineinfo.MachineinfoPath
import me.ave.inventory.jooq.codegen.tables.records.MachinetolocationRecord

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
open class Machinetolocation(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, MachinetolocationRecord>?,
    parentPath: InverseForeignKey<out Record, MachinetolocationRecord>?,
    aliased: Table<MachinetolocationRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<MachinetolocationRecord>(
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
         * The reference instance of <code>public.machinetolocation</code>
         */
        val MACHINETOLOCATION: Machinetolocation = Machinetolocation()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<MachinetolocationRecord> = MachinetolocationRecord::class.java

    /**
     * The column <code>public.machinetolocation.model</code>.
     */
    val MODEL: TableField<MachinetolocationRecord, String?> = createField(DSL.name("model"), SQLDataType.VARCHAR(3).nullable(false), this, "")

    /**
     * The column <code>public.machinetolocation.location_id</code>.
     */
    val LOCATION_ID: TableField<MachinetolocationRecord, Int?> = createField(DSL.name("location_id"), SQLDataType.INTEGER.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<MachinetolocationRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<MachinetolocationRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<MachinetolocationRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.machinetolocation</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.machinetolocation</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.machinetolocation</code> table reference
     */
    constructor(): this(DSL.name("machinetolocation"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, MachinetolocationRecord>?, parentPath: InverseForeignKey<out Record, MachinetolocationRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, MACHINETOLOCATION, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class MachinetolocationPath : Machinetolocation, Path<MachinetolocationRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, MachinetolocationRecord>?, parentPath: InverseForeignKey<out Record, MachinetolocationRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<MachinetolocationRecord>): super(alias, aliased)
        override fun `as`(alias: String): MachinetolocationPath = MachinetolocationPath(DSL.name(alias), this)
        override fun `as`(alias: Name): MachinetolocationPath = MachinetolocationPath(alias, this)
        override fun `as`(alias: Table<*>): MachinetolocationPath = MachinetolocationPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getUniqueKeys(): List<UniqueKey<MachinetolocationRecord>> = listOf(UQ_MACHINELOCATION)
    override fun getReferences(): List<ForeignKey<MachinetolocationRecord, *>> = listOf(MACHINETOLOCATION__FK_MACHINEMODEL, MACHINETOLOCATION__FK_LOCATION)

    private lateinit var _machineinfo: MachineinfoPath

    /**
     * Get the implicit join path to the <code>public.machineinfo</code> table.
     */
    fun machineinfo(): MachineinfoPath {
        if (!this::_machineinfo.isInitialized)
            _machineinfo = MachineinfoPath(this, MACHINETOLOCATION__FK_MACHINEMODEL, null)

        return _machineinfo;
    }

    val machineinfo: MachineinfoPath
        get(): MachineinfoPath = machineinfo()

    private lateinit var _location: LocationPath

    /**
     * Get the implicit join path to the <code>public.location</code> table.
     */
    fun location(): LocationPath {
        if (!this::_location.isInitialized)
            _location = LocationPath(this, MACHINETOLOCATION__FK_LOCATION, null)

        return _location;
    }

    val location: LocationPath
        get(): LocationPath = location()
    override fun `as`(alias: String): Machinetolocation = Machinetolocation(DSL.name(alias), this)
    override fun `as`(alias: Name): Machinetolocation = Machinetolocation(alias, this)
    override fun `as`(alias: Table<*>): Machinetolocation = Machinetolocation(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Machinetolocation = Machinetolocation(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Machinetolocation = Machinetolocation(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Machinetolocation = Machinetolocation(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Machinetolocation = Machinetolocation(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Machinetolocation = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Machinetolocation = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Machinetolocation = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Machinetolocation = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Machinetolocation = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Machinetolocation = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Machinetolocation = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Machinetolocation = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Machinetolocation = where(DSL.notExists(select))
}
