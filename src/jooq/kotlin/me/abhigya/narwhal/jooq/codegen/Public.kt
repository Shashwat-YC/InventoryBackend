/*
 * This file is generated by jOOQ.
 */
package me.ave.inventory.jooq.codegen


import javax.annotation.processing.Generated

import kotlin.collections.List

import me.ave.inventory.jooq.codegen.tables.Databasechangelog
import me.ave.inventory.jooq.codegen.tables.Databasechangeloglock
import me.ave.inventory.jooq.codegen.tables.Floor
import me.ave.inventory.jooq.codegen.tables.Job
import me.ave.inventory.jooq.codegen.tables.Jobtosparepart
import me.ave.inventory.jooq.codegen.tables.Location
import me.ave.inventory.jooq.codegen.tables.Machine
import me.ave.inventory.jooq.codegen.tables.Machineinfo
import me.ave.inventory.jooq.codegen.tables.Machinetolocation
import me.ave.inventory.jooq.codegen.tables.Maintenancerecord
import me.ave.inventory.jooq.codegen.tables.Maintenanceupdatedpacket
import me.ave.inventory.jooq.codegen.tables.Packetrecord
import me.ave.inventory.jooq.codegen.tables.Room
import me.ave.inventory.jooq.codegen.tables.Scheduleinfo
import me.ave.inventory.jooq.codegen.tables.Sparepartinfo
import me.ave.inventory.jooq.codegen.tables.Unassignedpackets
import me.ave.inventory.jooq.codegen.tables.Usedpacketrecord

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl


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
open class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC: Public = Public()
    }

    /**
     * The table <code>public.databasechangelog</code>.
     */
    val DATABASECHANGELOG: Databasechangelog get() = Databasechangelog.DATABASECHANGELOG

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    val DATABASECHANGELOGLOCK: Databasechangeloglock get() = Databasechangeloglock.DATABASECHANGELOGLOCK

    /**
     * The table <code>public.floor</code>.
     */
    val FLOOR: Floor get() = Floor.FLOOR

    /**
     * The table <code>public.job</code>.
     */
    val JOB: Job get() = Job.JOB

    /**
     * The table <code>public.jobtosparepart</code>.
     */
    val JOBTOSPAREPART: Jobtosparepart get() = Jobtosparepart.JOBTOSPAREPART

    /**
     * The table <code>public.location</code>.
     */
    val LOCATION: Location get() = Location.LOCATION

    /**
     * The table <code>public.machine</code>.
     */
    val MACHINE: Machine get() = Machine.MACHINE

    /**
     * The table <code>public.machineinfo</code>.
     */
    val MACHINEINFO: Machineinfo get() = Machineinfo.MACHINEINFO

    /**
     * The table <code>public.machinetolocation</code>.
     */
    val MACHINETOLOCATION: Machinetolocation get() = Machinetolocation.MACHINETOLOCATION

    /**
     * The table <code>public.maintenancerecord</code>.
     */
    val MAINTENANCERECORD: Maintenancerecord get() = Maintenancerecord.MAINTENANCERECORD

    /**
     * The table <code>public.maintenanceupdatedpacket</code>.
     */
    val MAINTENANCEUPDATEDPACKET: Maintenanceupdatedpacket get() = Maintenanceupdatedpacket.MAINTENANCEUPDATEDPACKET

    /**
     * The table <code>public.packetrecord</code>.
     */
    val PACKETRECORD: Packetrecord get() = Packetrecord.PACKETRECORD

    /**
     * The table <code>public.room</code>.
     */
    val ROOM: Room get() = Room.ROOM

    /**
     * The table <code>public.scheduleinfo</code>.
     */
    val SCHEDULEINFO: Scheduleinfo get() = Scheduleinfo.SCHEDULEINFO

    /**
     * The table <code>public.sparepartinfo</code>.
     */
    val SPAREPARTINFO: Sparepartinfo get() = Sparepartinfo.SPAREPARTINFO

    /**
     * The table <code>public.unassignedpackets</code>.
     */
    val UNASSIGNEDPACKETS: Unassignedpackets get() = Unassignedpackets.UNASSIGNEDPACKETS

    /**
     * The table <code>public.usedpacketrecord</code>.
     */
    val USEDPACKETRECORD: Usedpacketrecord get() = Usedpacketrecord.USEDPACKETRECORD

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Databasechangelog.DATABASECHANGELOG,
        Databasechangeloglock.DATABASECHANGELOGLOCK,
        Floor.FLOOR,
        Job.JOB,
        Jobtosparepart.JOBTOSPAREPART,
        Location.LOCATION,
        Machine.MACHINE,
        Machineinfo.MACHINEINFO,
        Machinetolocation.MACHINETOLOCATION,
        Maintenancerecord.MAINTENANCERECORD,
        Maintenanceupdatedpacket.MAINTENANCEUPDATEDPACKET,
        Packetrecord.PACKETRECORD,
        Room.ROOM,
        Scheduleinfo.SCHEDULEINFO,
        Sparepartinfo.SPAREPARTINFO,
        Unassignedpackets.UNASSIGNEDPACKETS,
        Usedpacketrecord.USEDPACKETRECORD
    )
}