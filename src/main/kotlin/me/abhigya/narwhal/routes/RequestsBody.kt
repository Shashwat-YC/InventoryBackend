package me.ave.inventory.routes

import kotlinx.serialization.Serializable
import me.ave.inventory.jooq.codegen.enums.PacketType
import me.ave.inventory.jooq.codegen.enums.Pic

class RequestsBody {
    class PacketRoute {
        @Serializable
        data class Add(
            val id: Int,
            val rfid: String,
            val partCode: String,
            val quantity: Int,
            val type: PacketType,
            val locationId: Int
        )

        @Serializable
        data class Assign(
            val id: Int,
            val partCode: String,
            val quantity: Int,
            val type: PacketType,
            val locationId: Int
        )

        @Serializable
        data class Update(
            val id: Int? = null,
            val rfid: String? = null,
            val quantity: Int? = null,
            val type: PacketType? = null,
            val locationId: Int? = null,
            val newId: Int? = null,
        )

        @Serializable
        data class AddUnassigned(
            val id: Int,
            val rfid: String
        )
    }

    class SparePartRoute {
        @Serializable
        data class Add(
            val code: String,
            val name: String,
            val partNo: String,
            val machineModel: String
        )

        @Serializable
        data class Update(
            val code: String,
            val name: String?,
            val partNo: String?,
            val machineModel: String?
        )
    }

    class LocationRoute {
        class Add {
            @Serializable
            data class Linked(
                val model: String,
                val room: Int,
                val rack: Int? = null,
                val shelf: Int? = null
            )

            @Serializable
            data class Unlinked(
                val room: Int,
                val rack: Int? = null,
                val shelf: Int? = null
            )

            @Serializable
            data class Floor(
                val id: Int,
                val name: String
            )

            @Serializable
            data class Room(
                val name: String,
                val floor: Int
            )

            @Serializable
            data class LinkMachine(
                val model: String,
                val roomId: Int,
                val rack: Int? = null,
                val shelf: Int? = null
            )
        }

        class Update {
            @Serializable
            data class All(
                val id: Int,
                val room: Int,
                val rack: Int? = null,
                val shelf: Int? = null
            )

            @Serializable
            data class LinkMachine(
                val model: String,
                val oldLocationId: Int,
                val roomId: Int,
                val rack: Int? = null,
                val shelf: Int? = null
            )
        }
    }

    class JobRoute {

        class Update {
            @Serializable
            data class PIC(
                val code: String,
                val pic: Pic
            )

            @Serializable
            data class SparePartNeededQuantity(
                val maintenanceId: Int,
                val sparePartCode: String,
                val quantity: Int
            )
        }
    }
}