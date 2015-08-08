-- ---------------------------------------------------------------------------------------------------
-- Conditional Drop                                                                                ---
---                                                                                                ---
-- Note: the CASACADE will also kill related triggers, which are not restored from  this file      ---
-- 												   ---
-- ---------------------------------------------------------------------------------------------------

DROP FUNCTION if exists   adjustgeomdriveroutepoint() CASCADE;
DROP FUNCTION if exists   orsAdjustGeomDriveroutepoint() CASCADE;



CREATE FUNCTION orsAdjustGeomDriveroutepoint() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
	
	srid integer;	
		
  BEGIN

    select into srid orsGetSrid(); 		

    NEW.coordinate_c = st_transform(st_setSrid(st_makepoint(NEW.coordinate[0], NEW.coordinate[1]), 4326), srid);   
    RETURN NEW;
  END;
$$;


ALTER FUNCTION public.orsAdjustGeomDriveroutepoint() OWNER TO openride;

CREATE TRIGGER drive_route_point_trigger BEFORE INSERT OR UPDATE ON drive_route_point FOR EACH ROW EXECUTE PROCEDURE orsAdjustGeomDriveroutepoint();






