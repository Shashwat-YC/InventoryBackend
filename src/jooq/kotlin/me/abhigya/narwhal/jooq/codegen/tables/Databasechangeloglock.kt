/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.tables


import java.time.LocalDateTime

import javax.annotation.processing.Generated

import kotlin.collections.Collection

import me.ave.inventory.jooq.codegen.Public
import me.ave.inventory.jooq.codegen.keys.DATABASECHANGELOGLOCK_PKEY
import me.ave.inventory.jooq.codegen.tables.records.DatabasechangeloglockRecord

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
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
open class Databasechangeloglock(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DatabasechangeloglockRecord>?,
    parentPath: InverseForeignKey<out Record, DatabasechangeloglockRecord>?,
    aliased: Table<DatabasechangeloglockRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<DatabasechangeloglockRecord>(
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
         * The reference instance of <code>public.databasechangeloglock</code>
         */
        val DATABASECHANGELOGLOCK: Databasechangeloglock = Databasechangeloglock()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DatabasechangeloglockRecord> = DatabasechangeloglockRecord::class.java

    /**
     * The column <code>public.databasechangeloglock.id</code>.
     */
    val ID: TableField<DatabasechangeloglockRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>public.databasechangeloglock.locked</code>.
     */
    val LOCKED: TableField<DatabasechangeloglockRecord, Boolean?> = createField(DSL.name("locked"), SQLDataType.BOOLEAN.nullable(false), this, "")

    /**
     * The column <code>public.databasechangeloglock.lockgranted</code>.
     */
    val LOCKGRANTED: TableField<DatabasechangeloglockRecord, LocalDateTime?> = createField(DSL.name("lockgranted"), SQLDataType.LOCALDATETIME(6), this, "")

    /**
     * The column <code>public.databasechangeloglock.lockedby</code>.
     */
    val LOCKEDBY: TableField<DatabasechangeloglockRecord, String?> = createField(DSL.name("lockedby"), SQLDataType.VARCHAR(255), this, "")

    private constructor(alias: Name, aliased: Table<DatabasechangeloglockRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<DatabasechangeloglockRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<DatabasechangeloglockRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.databasechangeloglock</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.databasechangeloglock</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.databasechangeloglock</code> table reference
     */
    constructor(): this(DSL.name("databasechangeloglock"), null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<DatabasechangeloglockRecord> = DATABASECHANGELOGLOCK_PKEY
    override fun `as`(alias: String): Databasechangeloglock = Databasechangeloglock(DSL.name(alias), this)
    override fun `as`(alias: Name): Databasechangeloglock = Databasechangeloglock(alias, this)
    override fun `as`(alias: Table<*>): Databasechangeloglock = Databasechangeloglock(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Databasechangeloglock = Databasechangeloglock(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Databasechangeloglock = Databasechangeloglock(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Databasechangeloglock = Databasechangeloglock(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Databasechangeloglock = Databasechangeloglock(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Databasechangeloglock = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Databasechangeloglock = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Databasechangeloglock = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Databasechangeloglock = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Databasechangeloglock = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Databasechangeloglock = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Databasechangeloglock = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Databasechangeloglock = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Databasechangeloglock = where(DSL.notExists(select))
}
