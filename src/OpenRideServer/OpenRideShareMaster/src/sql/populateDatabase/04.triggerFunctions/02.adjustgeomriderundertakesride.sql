-- --------------------------
-- Conditional Drop        --
-- --------------------------
DROP  FUNCTION  IF EXISTS orsAdjustGeomRiderundertakesride() CASCADE;



--
-- Name: adjustgeomriderundertakesride(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION orsAdjustGeomRiderundertakesride() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
    srid integer;
  BEGIN
    select into srid orsGetSrid();	
    NEW.startpt_c = st_transform(st_setSrid(st_makepoint(NEW.startpt[0], NEW.startpt[1]), 4326), srid);   
    NEW.endpt_c = st_transform(st_setSrid(st_makepoint(NEW.endpt[0], NEW.endpt[1]), 4326), srid);   
    RETURN NEW;
  END;
$$;


ALTER FUNCTION public.orsAdjustGeomRiderundertakesride() OWNER TO openride;



CREATE TRIGGER riderundertakesride_trigger BEFORE INSERT OR UPDATE ON riderundertakesride FOR EACH ROW EXECUTE PROCEDURE orsAdjustGeomRiderundertakesride();


