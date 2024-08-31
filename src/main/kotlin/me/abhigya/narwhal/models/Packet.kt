package me.ave.inventory.models

import kotlinx.serialization.Serializable
import me.ave.inventory.jooq.codegen.enums.PacketType
import me.ave.inventory.jooq.codegen.tables.references.PACKETRECORD
import me.ave.inventory.jooq.codegen.tables.references.SPAREPARTINFO
import org.jooq.Record

@Serializable
data class PacketInfo(
    val id: Int,
    val rfid: String,
    val partInfo: SparePartInfo,
    val quantity: Int,
    val type: PacketType,
    val location: LocationInfo
) {
    companion object : FromRecord<PacketInfo> {
        override fun fromRecord(record: Record): PacketInfo {
            return PacketInfo(
                id = record[PACKETRECORD.ID]!!,
                rfid = record[PACKETRECORD.RFID]!!,
                partInfo = SparePartInfo.fromRecord(record),
                quantity = record[PACKETRECORD.QUANTITY]!!,
                type = record[PACKETRECORD.TYPE]!!,
                location = LocationInfo.fromRecord(record)
            )
        }
    }
}

@Serializable
data class SparePartInfo(
    val code: String,
    val name: String,
    val partNo: String,
    val machineInfo: MachineInfo,
    val rob: Int
) {
    companion object : FromRecord<SparePartInfo> {
        override fun fromRecord(record: Record): SparePartInfo {
            return SparePartInfo(
                code = record[SPAREPARTINFO.CODE]!!,
                name = record[SPAREPARTINFO.NAME]!!,
                partNo = record[SPAREPARTINFO.PART_NO]!!,
                machineInfo = MachineInfo.fromRecord(record),
                rob = record[SPAREPARTINFO.ROB]!!
            )
        }
    }

}

interface ISparePart {
    val rob: Int
    val code: String
    val name: String
    val partNo: String
    val machineModel: String
    val packets: List<PacketInfo>
}

@Serializable
data class SparePart(
    override val rob: Int,
    override val code: String,
    override val name: String,
    override val partNo: String,
    override val machineModel: String,
    override val packets: List<PacketInfo>
) : ISparePart

@Serializable
data class SparePartWithJob(
    override val rob: Int,
    override val code: String,
    override val name: String,
    override val partNo: String,
    override val machineModel: String,
    override val packets: List<PacketInfo>,
    val quantityNeeded: Int,
) : ISparePart

@Serializable
data class SparePartWithMaintenance(
    override val rob: Int,
    override val code: String,
    override val name: String,
    override val partNo: String,
    override val machineModel: String,
    override val packets: List<PacketInfo>,
    val quantityNeeded: Int,
    val quantityNeededUpdated: Int?,
) : ISparePart

@Serializable
class Rob(val rob: Int)

@Serializable
class Id(val id: Int)