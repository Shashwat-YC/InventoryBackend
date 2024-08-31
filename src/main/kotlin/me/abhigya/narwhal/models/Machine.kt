package me.ave.inventory.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.ave.inventory.jooq.codegen.tables.pojos.Machineinfo
import me.ave.inventory.jooq.codegen.tables.references.MACHINE
import me.ave.inventory.jooq.codegen.tables.references.MACHINEINFO
import org.jooq.Record

@Serializable
data class Machine(
    val model: String,
    val no: Int,
    val lastMaintenance: Instant,
    val avgRH: Int,
    val name: String,
    val maker: String
) {
    companion object : FromRecord<Machine> {
        override fun fromRecord(record: Record): Machine {
            return Machine(
                model = record[MACHINE.MODEL]!!,
                no = record[MACHINE.MACHINE_NO]!!,
                lastMaintenance = record[MACHINE.LAST_MAINTENANCE]!!,
                avgRH = record[MACHINE.AVG_RH]!!,
                name = record[MACHINEINFO.NAME]!!,
                maker = record[MACHINEINFO.MAKER]!!
            )
        }
    }
}

@Serializable
data class JobWithMachine(
    val job: Job,
    val machine: Machine
)

@Serializable
data class MachineInfo(
    val model: String,
    val name: String,
    val maker: String
) {
    companion object : FromRecord<MachineInfo>, FromPojo<Machineinfo, MachineInfo> {
        override fun fromRecord(record: Record): MachineInfo {
            return MachineInfo(
                model = record[MACHINEINFO.MODEL]!!,
                name = record[MACHINEINFO.NAME]!!,
                maker = record[MACHINEINFO.MAKER]!!
            )
        }

        override fun fromPojo(pojo: Machineinfo): MachineInfo {
            return MachineInfo(
                model = pojo.model!!,
                name = pojo.name!!,
                maker = pojo.maker!!
            )
        }
    }
}