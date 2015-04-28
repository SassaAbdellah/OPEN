-- ---------------------
-- Conditional Drop  ---
-- ---------------------
DROP FUNCTION if exadjustgeomdriveroutepoint()ists adjustgeomdriveroutepoint()


--
-- Name: adjustgeomdriveroutepoint(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION adjustgeomdriveroutepoint() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
  BEGIN
    NEW.coordinate_c = st_transform(st_setSrid(st_makepoint(NEW.coordinate[0], NEW.coordinate[1]), 4326), 3068);   
    RETURN NEW;
  END;
$$;


ALTER FUNCTION public.adjustgeomdriveroutepoint() OWNER TO postgres;


