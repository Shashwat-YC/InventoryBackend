ALTER TABLE sparepartinfo
    ADD COLUMN rob INT NOT NULL DEFAULT 0;

-- Function
CREATE FUNCTION update_rob()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS '
BEGIN
    IF TG_OP = ''DELETE'' THEN
        UPDATE sparepartinfo
        SET rob = (SELECT COALESCE(SUM(quantity), 0) FROM packetrecord WHERE code = OLD.code)
        WHERE code = OLD.code;
        RETURN OLD;
    ELSE
        IF NEW.code = OLD.code THEN
            UPDATE sparepartinfo
            SET rob = (SELECT COALESCE(SUM(quantity), 0) FROM packetrecord WHERE code = NEW.code)
            WHERE code = NEW.code;
        ELSE
            UPDATE sparepartinfo
            SET rob = (SELECT COALESCE(SUM(quantity), 0) FROM packetrecord WHERE code = NEW.code)
            WHERE code = NEW.code;
            UPDATE sparepartinfo
            SET rob = (SELECT COALESCE(SUM(quantity), 0) FROM packetrecord WHERE code = OLD.code)
            WHERE code = OLD.code;
        END IF;
        RETURN NEW;
    END IF;
END;
';

-- Trigger
CREATE TRIGGER packetrecord_after_insert
    AFTER INSERT
    ON packetrecord
    FOR EACH ROW
EXECUTE FUNCTION update_rob();

CREATE TRIGGER packetrecord_after_update
    AFTER UPDATE
    ON packetrecord
    FOR EACH ROW
EXECUTE FUNCTION update_rob();

CREATE TRIGGER packetrecord_after_delete
    AFTER DELETE
    ON packetrecord
    FOR EACH ROW
EXECUTE FUNCTION update_rob();