-- Enums
CREATE TYPE schedule_type as ENUM ('CA', 'RH');
CREATE TYPE job_place as ENUM ('SAILING', 'DOCK', 'PORT');
CREATE TYPE job_kind as ENUM ('PMS', 'PCM');
CREATE TYPE packet_type as ENUM ('NEW', 'RECONDITIONED', 'OLD');
CREATE TYPE pic as ENUM ('L1', 'L2', 'L3');

-- Tables
CREATE TABLE scheduleinfo
(
    id    SERIAL,
    type  schedule_type NOT NULL,
    delay INT           NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UQ_ScheduleType UNIQUE (type, delay)
);

CREATE TABLE machineinfo
(
    model VARCHAR(3)   NOT NULL,
    name  VARCHAR(50)  NOT NULL,
    maker VARCHAR(100) NOT NULL,
    PRIMARY KEY (model),
    CONSTRAINT UQ_MachineName UNIQUE (name)
);

CREATE TABLE floor
(
    id   INT         NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE room
(
    id    SERIAL,
    name  VARCHAR(50) NOT NULL,
    floor INT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_RoomFloor FOREIGN KEY (floor) REFERENCES floor (id)
);

CREATE TABLE machine
(
    model            VARCHAR(3) NOT NULL,
    machine_no       INT        NOT NULL,
    last_maintenance TIMESTAMP  NOT NULL,
    avg_rh           INT        NOT NULL,
    PRIMARY KEY (model, machine_no),
    CONSTRAINT FK_MachineInfo FOREIGN KEY (model) REFERENCES machineinfo (model)
);

CREATE TABLE sparepartinfo
(
    code          VARCHAR(20)  NOT NULL,
    name          VARCHAR(150) NOT NULL,
    part_no       VARCHAR(50)  NOT NULL,
    machine_model VARCHAR(3)   NOT NULL,
    PRIMARY KEY (code),
    CONSTRAINT FK_CodeInfo FOREIGN KEY (machine_model) REFERENCES machineinfo (model)
);

CREATE TABLE location
(
    id      SERIAL,
    room_id INT NOT NULL,
    rack    INT NOT NULL DEFAULT -1,
    shelf   INT NOT NULL DEFAULT -1,
    PRIMARY KEY (id),
    CONSTRAINT FK_RoomLocation FOREIGN KEY (room_id) REFERENCES room (id),
    CONSTRAINT UQ_Location UNIQUE (room_id, rack, shelf)
);

CREATE TABLE machinetolocation
(
    model      VARCHAR(3) NOT NULL,
    location_id INT        NOT NULL,
    CONSTRAINT FK_MachineModel FOREIGN KEY (model) REFERENCES machineinfo (model),
    CONSTRAINT FK_Location FOREIGN KEY (location_id) REFERENCES location (id),
    CONSTRAINT UQ_MachineLocation UNIQUE (model, location_id)
);

CREATE TABLE packetrecord
(
    id          INT         NOT NULL,
    code        VARCHAR(20) NOT NULL,
    rfid        VARCHAR(30) NOT NULL,
    quantity    INT         NOT NULL,
    type        packet_type NOT NULL,
    location_id INT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_PacketCode FOREIGN KEY (code) REFERENCES sparepartinfo (code),
    CONSTRAINT FK_Location FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE usedpacketrecord
(
    id             SERIAL,
    packet_id      INT     NOT NULL,
    maintenance_id INT     NOT NULL,
    quantity       INT     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_PacketRecord FOREIGN KEY (packet_id) REFERENCES packetrecord (id)
);

CREATE TABLE job
(
    pms_code     VARCHAR(20)  NOT NULL,
    description  VARCHAR(100) NOT NULL,
    place        job_place    NOT NULL,
    kind         job_kind     NOT NULL,
    schedule_key INT          NOT NULL,
    model        VARCHAR(3)   NOT NULL,
    pic          pic          NOT NULL,
    PRIMARY KEY (pms_code),
    CONSTRAINT FK_JobSchedule FOREIGN KEY (schedule_key) REFERENCES scheduleinfo (id)
);

CREATE TABLE maintenancerecord
(
    id             SERIAL,
    model          VARCHAR(3)  NOT NULL,
    machine_no     INT         NOT NULL,
    pms_code       VARCHAR(20) NOT NULL,
    time_started   TIMESTAMP,
    time_completed TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT FK_MaintainInfo FOREIGN KEY (model) REFERENCES machineinfo (model),
    CONSTRAINT FK_MaintainJob FOREIGN KEY (pms_code) REFERENCES job (pms_code),
    CONSTRAINT FK_MaintainMachine FOREIGN KEY (model, machine_no) REFERENCES machine (model, machine_no)
);

CREATE TABLE unassignedpackets
(
    id   INT         NOT NULL,
    rfid VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE jobtosparepart
(
    pms_code   VARCHAR(20) NOT NULL,
    spare_part VARCHAR(20) NOT NULL,
    quantity   INT         NOT NULL DEFAULT 1,
    CONSTRAINT UQ_PMSSparePart UNIQUE (pms_code, spare_part),
    CONSTRAINT FK_JobSparePart FOREIGN KEY (pms_code) REFERENCES job (pms_code),
    CONSTRAINT FK_SparePartJob FOREIGN KEY (spare_part) REFERENCES sparepartinfo (code)
);

CREATE TABLE maintenanceupdatedpacket
(
    maintenance_id   INT         NOT NULL,
    spare_part_code  VARCHAR(20) NOT NULL,
    updated_quantity INT         NOT NULL,
    CONSTRAINT FK_MaintenanceUpdatedPacket FOREIGN KEY (maintenance_id) REFERENCES maintenancerecord (id),
    CONSTRAINT FK_UpdatedPacketSparePart FOREIGN KEY (spare_part_code) REFERENCES sparepartinfo (code),
    CONSTRAINT UQ_MaintenanceUpdatedPacket UNIQUE (maintenance_id, spare_part_code)
);
