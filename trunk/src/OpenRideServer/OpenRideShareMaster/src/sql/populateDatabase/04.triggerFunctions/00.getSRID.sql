-- ---------------------
-- Conditional Drop  ---
-- ---------------------
DROP FUNCTION if exists   orsGetSRID() ;



CREATE FUNCTION orsGetSRID() RETURNS integer
    LANGUAGE plpgsql
    AS $$
  DECLARE
  BEGIN
    RETURN 3068;
  END;
$$;


ALTER FUNCTION public.orsGetSrid() OWNER TO openride;


