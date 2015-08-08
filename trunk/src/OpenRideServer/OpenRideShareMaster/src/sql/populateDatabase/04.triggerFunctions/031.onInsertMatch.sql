-- ---------------------------------------------------------------------------------------------------
-- Conditional Drop                                                                                ---
---                                                                                                ---
-- Note: the CASACADE will also kill related triggers, which are not restored from  this file      ---
-- 												   ---
-- ---------------------------------------------------------------------------------------------------

DROP FUNCTION if exists   orsOnInsertMatch() CASCADE;

-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
--   DESCRIPTION
--
--   Increments offer's match count in riderundertakesride,driverundertakesride 
--   by one every time a new match is created 
--
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


CREATE FUNCTION orsOnInsertMatch() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE

	-- no declarations needed

		
  BEGIN

   	update driverundertakesride set  match_count =  1+match_count where ride_id = NEW.ride_id;   
        update riderundertakesride set   match_count =  1+match_count where riderroute_id = NEW.riderroute_id;   	

	RETURN NEW;

  END;
$$;


ALTER FUNCTION public.orsOnInsertMatch() OWNER TO openride;


CREATE TRIGGER match_count_Insert_trigger AFTER INSERT ON match FOR EACH ROW EXECUTE PROCEDURE   orsOnInsertMatch();





