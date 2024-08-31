-- machinetolocation
TRUNCATE TABLE machinetolocation;

-- packetrecord
TRUNCATE TABLE packetrecord;

-- usedpacketrecord
TRUNCATE TABLE usedpacketrecord;

-- location
TRUNCATE TABLE location CASCADE;

-- maintenanceupdatedpacket
TRUNCATE TABLE maintenanceupdatedpacket;

-- unassigned packets
TRUNCATE TABLE unassignedpackets;

DO
$$
    DECLARE
        RFID VARCHAR := '000000000000000000000000';
    BEGIN
        FOR CNT IN 1..10000
            LOOP
                RFID = CONCAT('00000000000000000000000', CAST(CNT AS VARCHAR));
                RFID = RIGHT(RFID, 24);
                INSERT INTO unassignedpackets (id, rfid) VALUES (CNT, RFID);
            END LOOP;
    END;
$$;

-- maintenancerecord
DELETE
FROM maintenancerecord
WHERE time_started IS NOT NULL
  AND time_completed IS NOT NULL;

UPDATE maintenancerecord
SET time_started = NULL
WHERE time_started IS NOT NULL;