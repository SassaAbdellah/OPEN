-- ---------------------------------------------------------------------------------------------------
-- Conditional Drop                                                                                ---
---                                                                                                ---
-- Note: the CASACADE will also kill related triggers, which are not restored from  this file      ---
-- 												   ---
-- ---------------------------------------------------------------------------------------------------

DROP FUNCTION if exists   orsOnDeleteMatch() CASCADE;

-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
--   DESCRIPTION
--
--   Decrements offer's and request's match count when a match is deleted 
--
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


CREATE FUNCTION orsOnDeleteMatch() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE

	-- no declarations needed
	
  BEGIN

   	update driverundertakesride set  match_count =  match_count-1 where ride_id = OLD.ride_id;   
    	update riderundertakesride  set  match_count =  match_count-1 where riderroute_id = OLD.riderroute_id; 
	
	RETURN OLD;

  END;
$$;


ALTER FUNCTION public.orsOnDeleteMatch() OWNER TO openride;


CREATE TRIGGER match_Delete_trigger BEFORE DELETE ON match FOR EACH ROW EXECUTE PROCEDURE   orsOnDeleteMatch();





