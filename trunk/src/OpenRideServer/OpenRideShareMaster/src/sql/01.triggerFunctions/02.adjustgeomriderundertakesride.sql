-- --------------------------
-- Conditional Drop        --
-- --------------------------
DROP  FUNCTION  IF EXISTS adjustgeomriderundertakesride()


--
-- Name: adjustgeomriderundertakesride(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION adjustgeomriderundertakesride() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
  BEGIN
    NEW.startpt_c = st_transform(st_setSrid(st_makepoint(NEW.startpt[0], NEW.startpt[1]), 4326), 3068);   
    NEW.endpt_c = st_transform(st_setSrid(st_makepoint(NEW.endpt[0], NEW.endpt[1]), 4326), 3068);   
    RETURN NEW;
  END;
$$;


ALTER FUNCTION public.adjustgeomriderundertakesride() OWNER TO postgres;

