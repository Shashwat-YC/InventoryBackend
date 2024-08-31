/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables


import javax.annotation.processing.Generated

import kotlin.collections.Collection
import kotlin.collections.List

import me.ave.inventory.jooq.codegen.Public
import me.ave.inventory.jooq.codegen.keys.LOCATION__FK_ROOMLOCATION
import me.ave.inventory.jooq.codegen.keys.ROOM_PKEY
import me.ave.inventory.jooq.codegen.keys.ROOM__FK_ROOMFLOOR
import me.ave.inventory.jooq.codegen.tables.Floor.FloorPath
import me.ave.inventory.jooq.codegen.tables.Location.LocationPath
import me.ave.inventory.jooq.codegen.tables.records.RoomRecord

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
open class Room(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, RoomRecord>?,
    parentPath: InverseForeignKey<out Record, RoomRecord>?,
    aliased: Table<RoomRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<RoomRecord>(
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
         * The reference instance of <code>public.room</code>
         */
        val ROOM: Room = Room()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<RoomRecord> = RoomRecord::class.java

    /**
     * The column <code>public.room.id</code>.
     */
    val ID: TableField<RoomRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "")

    /**
     * The column <code>public.room.name</code>.
     */
    val NAME: TableField<RoomRecord, String?> = createField(DSL.name("name"), SQLDataType.VARCHAR(50).nullable(false), this, "")

    /**
     * The column <code>public.room.floor</code>.
     */
    val FLOOR: TableField<RoomRecord, Int?> = createField(DSL.name("floor"), SQLDataType.INTEGER.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<RoomRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<RoomRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<RoomRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.room</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.room</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.room</code> table reference
     */
    constructor(): this(DSL.name("room"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, RoomRecord>?, parentPath: InverseForeignKey<out Record, RoomRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, ROOM, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class RoomPath : Room, Path<RoomRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, RoomRecord>?, parentPath: InverseForeignKey<out Record, RoomRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<RoomRecord>): super(alias, aliased)
        override fun `as`(alias: String): RoomPath = RoomPath(DSL.name(alias), this)
        override fun `as`(alias: Name): RoomPath = RoomPath(alias, this)
        override fun `as`(alias: Table<*>): RoomPath = RoomPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIdentity(): Identity<RoomRecord, Int?> = super.getIdentity() as Identity<RoomRecord, Int?>
    override fun getPrimaryKey(): UniqueKey<RoomRecord> = ROOM_PKEY
    override fun getReferences(): List<ForeignKey<RoomRecord, *>> = listOf(ROOM__FK_ROOMFLOOR)

    private lateinit var _floor: FloorPath

    /**
     * Get the implicit join path to the <code>public.floor</code> table.
     */
    fun floor(): FloorPath {
        if (!this::_floor.isInitialized)
            _floor = FloorPath(this, ROOM__FK_ROOMFLOOR, null)

        return _floor;
    }

    val floor: FloorPath
        get(): FloorPath = floor()

    private lateinit var _location: LocationPath

    /**
     * Get the implicit to-many join path to the <code>public.location</code>
     * table
     */
    fun location(): LocationPath {
        if (!this::_location.isInitialized)
            _location = LocationPath(this, null, LOCATION__FK_ROOMLOCATION.inverseKey)

        return _location;
    }

    val location: LocationPath
        get(): LocationPath = location()
    override fun `as`(alias: String): Room = Room(DSL.name(alias), this)
    override fun `as`(alias: Name): Room = Room(alias, this)
    override fun `as`(alias: Table<*>): Room = Room(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Room = Room(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Room = Room(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Room = Room(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Room = Room(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Room = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Room = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Room = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Room = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Room = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Room = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Room = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Room = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Room = where(DSL.notExists(select))
}
