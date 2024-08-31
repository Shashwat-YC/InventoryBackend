package me.ave.inventory.models

import kotlinx.serialization.Serializable
import me.ave.inventory.jooq.codegen.tables.pojos.Floor
import me.ave.inventory.jooq.codegen.tables.pojos.Room
import me.ave.inventory.jooq.codegen.tables.references.FLOOR
import me.ave.inventory.jooq.codegen.tables.references.LOCATION
import me.ave.inventory.jooq.codegen.tables.references.ROOM
import org.jooq.Record

@Serializable
data class LocationInfo(
    val id: Int,
    val floor: Int,
    val floorName: String,
    val room: Int,
    val roomName: String,
    val rack: Int?,
    val shelf: Int?
) {
    companion object : FromRecord<LocationInfo> {
        override fun fromRecord(record: Record): LocationInfo {
            return LocationInfo(
                id = record[LOCATION.ID]!!,
                floor = record[FLOOR.ID]!!,
                floorName = record[FLOOR.NAME]!!,
                room = record[ROOM.ID]!!,
                roomName = record[ROOM.NAME]!!,
                rack = record[LOCATION.RACK]!!.let { if (it == -1) null else it },
                shelf = record[LOCATION.SHELF]!!.let { if (it == -1) null else it }
            )
        }
    }
}

@Serializable
data class RoomInfo(
    val id: Int,
    val name: String,
    val floorId: Int
) {
    companion object : FromRecord<RoomInfo>, FromPojo<Room, RoomInfo> {
        override fun fromRecord(record: Record): RoomInfo {
            return RoomInfo(
                id = record[ROOM.ID]!!,
                name = record[ROOM.NAME]!!,
                floorId = record[ROOM.FLOOR]!!
            )
        }

        override fun fromPojo(pojo: Room): RoomInfo {
            return RoomInfo(
                id = pojo.id!!,
                name = pojo.name!!,
                floorId = pojo.floor!!
            )
        }
    }
}

@Serializable
data class FloorInfo(
    val id: Int,
    val name: String
) {
    companion object : FromRecord<FloorInfo>, FromPojo<Floor, FloorInfo> {
        override fun fromRecord(record: Record): FloorInfo {
            return FloorInfo(
                id = record[FLOOR.ID]!!,
                name = record[FLOOR.NAME]!!
            )
        }

        override fun fromPojo(pojo: Floor): FloorInfo {
            return FloorInfo(
                id = pojo.id!!,
                name = pojo.name!!
            )
        }
    }
}