package me.ave.inventory.routes

import io.ktor.resources.*
import me.ave.inventory.jooq.codegen.enums.JobPlace
import me.ave.inventory.models.Job

class Resources {
    // Inventory Route
    @Resource("/spare_part")
    class SparePartRoute {
        @Resource("get")
        class Get(val parent: SparePartRoute = SparePartRoute()) {
            @Resource("{code}")
            class Code(val parent: Get = Get(), val code: String)
        }

        @Resource("get_rob")
        class GetRob(val parent: SparePartRoute = SparePartRoute()) {
            @Resource("{code}")
            class Code(val parent: GetRob = GetRob(), val code: String)
        }

        @Resource("maintenance_get")
        class MaintenanceGet(val parent: SparePartRoute = SparePartRoute()) {
            @Resource("{maintenanceId}")
            class RecordId(
                val parent: MaintenanceGet = MaintenanceGet(),
                val maintenanceId: Int,
                val sparePartCode: String
            )
        }

        @Resource("add")
        class Add(val parent: SparePartRoute = SparePartRoute())

        @Resource("update")
        class Update(val parent: SparePartRoute = SparePartRoute())
    }

    @Resource("/packet")
    class PacketRoute {
        @Resource("get")
        class Get(val parent: PacketRoute = PacketRoute()) {
            @Resource("{rfid}")
            class Rfid(val parent: Get = Get(), val rfid: String)
        }

        @Resource("getall")
        class GetAll(val parent: PacketRoute = PacketRoute())

        @Resource("add")
        class Add(val parent: PacketRoute = PacketRoute())

        @Resource("assign")
        class Assign(val parent: PacketRoute = PacketRoute())

        @Resource("update")
        class Update(val parent: PacketRoute = PacketRoute())

        @Resource("add_unassigned")
        class AddUnassigned(val parent: PacketRoute = PacketRoute())

        @Resource("update_in_use")
        class UpdateInUse(val parent: PacketRoute = PacketRoute()) {
            @Resource("{tag}")
            class Tag(val parent: UpdateInUse = UpdateInUse(), val tag: Int, val quantity: Int)
        }
    }

    @Resource("/machine")
    class Machine {
        @Resource("get")
        class Get(val parent: Machine = Machine()) {
            @Resource("{floor}")
            class Floor(val parent: Get = Get(), val floor: Int)
        }

        @Resource("get_locations")
        class GetLocations(val parent: Machine = Machine()) {
            @Resource("{model}")
            class Model(val parent: GetLocations = GetLocations(), val model: String)
        }

        @Resource("get_parts")
        class GetParts(val parent: Machine = Machine()) {
            @Resource("{machine}")
            class MachineId(val parent: GetParts = GetParts(), val machine: String)
        }

        @Resource("info")
        class Info(val parent: Machine = Machine()) {
            @Resource("{machine}")
            class MachineId(val parent: Info = Info(), val machine: String)
        }
    }

    // Location Route
    class LocationRoute {
        @Resource("/add")
        class Add {
            @Resource("unlinked")
            class Unlinked(val parent: Add = Add())

            @Resource("linked")
            class Linked(val parent: Add = Add())

            @Resource("floor")
            class Floor(val parent: Add = Add())

            @Resource("room")
            class Room(val parent: Add = Add())

            @Resource("link_machine")
            class LinkMachine(val parent: Add = Add())
        }

        @Resource("/update")
        class Update {
            @Resource("all")
            class All(val parent: Update = Update())

            @Resource("link_machine")
            class LinkMachine(val parent: Update = Update())
        }

        @Resource("/get_rooms")
        class GetRooms {
            @Resource("{floor}")
            class ByFloor(val parent: GetRooms = GetRooms(), val floor: Int)

            @Resource("all")
            class All(val parent: GetRooms = GetRooms())
        }

        @Resource("/get_floors")
        class GetFloors

        @Resource("/get_packets")
        class GetPackets {
            @Resource("{location}/{machine}")
            class ByLocationAndMachine(val parent: GetPackets = GetPackets(), val location: Int, val machine: String)

            @Resource("full")
            class Full(
                val parent: GetPackets = GetPackets(),
                val floor: Int,
                val room: Int,
                val rack: Int? = null,
                val shelf: Int? = null
            )
        }
    }

    // Jobs Route
    @Resource("/jobs")
    class JobRoute {
        @Resource("get")
        class Get(
            val parent: JobRoute = JobRoute(),
            val status: Job.Status? = null,
            val dueWithin: Job.DueWithin? = null,
            val place: JobPlace? = null,
            val limit: Int = 30,
        )

        @Resource("get_parts")
        class GetParts(val parent: JobRoute = JobRoute()) {
            @Resource("{code}")
            class Code(val parent: GetParts = GetParts(), val code: String)
        }

        @Resource("get_parts_maintenance")
        class GetPartsMaintenance(val parent: JobRoute = JobRoute()) {
            @Resource("{id}")
            class MaintenanceId(val parent: GetPartsMaintenance = GetPartsMaintenance(), val id: Int)
        }

        @Resource("start")
        class Start(val parent: JobRoute = JobRoute()) {
            @Resource("{code}")
            class Code(val parent: Start = Start(), val code: Int)
        }

        @Resource("complete")
        class Complete(val parent: JobRoute = JobRoute()) {
            @Resource("{code}")
            class Code(val parent: Complete = Complete(), val code: Int)
        }

        @Resource("update")
        class Update(val parent: JobRoute = JobRoute()) {
            @Resource("pic")
            class PIC(val parent: Update = Update())

            @Resource("spare_part_needed_quantity")
            class SparePartNeededQuantity(val parent: Update = Update())
        }
    }
}