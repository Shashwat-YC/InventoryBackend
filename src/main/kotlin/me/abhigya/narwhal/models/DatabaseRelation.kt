package me.ave.inventory.models

import org.jooq.Record

interface FromRecord<R> {

    fun fromRecord(record: Record): R

}

interface FromPojo<P, R> {

    fun fromPojo(pojo: P): R

}