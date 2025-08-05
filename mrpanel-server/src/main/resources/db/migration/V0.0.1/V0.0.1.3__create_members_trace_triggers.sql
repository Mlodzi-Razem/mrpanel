CREATE OR REPLACE FUNCTION members_insert_trigger_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
    INSERT INTO members_trace (id, preferred_name, email)
    VALUES (NEW.id, NEW.preferred_name, NEW.email);

    RETURN NEW;
END;
$$;

CREATE TRIGGER members_insert_trigger
    AFTER INSERT
    ON members
    FOR EACH ROW
EXECUTE FUNCTION members_insert_trigger_function();

------------------------------------------------------------

CREATE OR REPLACE FUNCTION members_update_trigger_function()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
    UPDATE members_trace
    SET preferred_name = NEW.preferred_name,
        email          = NEW.email
    WHERE id = NEW.id;
END;
$$;

CREATE TRIGGER members_update_trigger
    AFTER UPDATE
    ON members
    FOR EACH ROW
EXECUTE FUNCTION members_update_trigger_function();