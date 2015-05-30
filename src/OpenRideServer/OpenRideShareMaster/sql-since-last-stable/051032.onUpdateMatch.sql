-- ---------------------------------------------------------------------------------------------------
-- Conditional Drop                                                                                ---
---                                                                                                ---
-- Note: the CASACADE will also kill related triggers, which are not restored from  this file      ---
-- 												   ---
-- ---------------------------------------------------------------------------------------------------

DROP FUNCTION if exists orsOnUpdateMatch() CASCADE;

-- -----------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------
-- 
-- Description:
--
--  decrement match count if matches are REJECTED, RIDER_COUNTERMANDED , DRIVER_COUNTERMANDED or NO_MORE_AVAILABLE
--  do nothing if matches are NOT_ADAPTED or ACCEPTED
--
-- 
-- -----------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------



CREATE FUNCTION orsOnUpdateMatch() RETURNS trigger
    LANGUAGE plpgsql

AS $$

  DECLARE

	-- these are the states defined in  MatchEntity class

	
	NOT_ADAPTED  		integer = 0;
   	REJECTED      		integer = 1;
   	ACCEPTED 		integer = 2;
        RIDER_COUNTERMANDED 	integer = 3;
    	DRIVER_COUNTERMANDED 	integer = 4;
	NO_MORE_AVAILABLE    	integer = 5;

		

  BEGIN

	-- decrement match count if matches are REJECTED, RIDER_COUNTERMANDED , DRIVER_COUNTERMANDED or NO_MORE_AVAILABLE
	-- do nothing if matches are NOT_ADAPTED or ACCEPTED

	IF ( (NEW.rider_state in( 1, 3, 4, 5)) or (NEW.driver_state in( 1, 3, 4, 5)) ) THEN

		update driverundertakesride set  match_count = (match_count -1) where NEW.ride_id=ride_id;
		update riderundertakesride  set  match_count = (match_count -1) where NEW.riderroute_id=riderroute_id;
	END IF;

	RETURN NEW;
  END;
$$;


ALTER FUNCTION public.orsOnUpdateMatch() OWNER TO openride;

CREATE TRIGGER match_count_update_trigger AFTER  INSERT OR UPDATE ON match FOR EACH ROW EXECUTE PROCEDURE   orsOnUpdateMatch();




