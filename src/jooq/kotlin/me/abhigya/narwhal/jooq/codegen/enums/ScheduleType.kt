/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen.enums


import javax.annotation.processing.Generated

import me.ave.inventory.jooq.codegen.Public

import org.jooq.Catalog
import org.jooq.EnumType
import org.jooq.Schema


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
enum class ScheduleType(@get:JvmName("literal") public val literal: String) : EnumType {
    CA("CA"),
    RH("RH");
    override fun getCatalog(): Catalog? = schema.catalog
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getName(): String = "schedule_type"
    override fun getLiteral(): String = literal
}
