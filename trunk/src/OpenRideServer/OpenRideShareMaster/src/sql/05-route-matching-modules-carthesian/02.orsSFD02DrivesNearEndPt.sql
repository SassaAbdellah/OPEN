

-- -----------------------------------------------------------------
-- drop before recreate
-- -----------------------------------------------------------------
DROP FUNCTION IF EXISTS orsSFD02Filter02DrivesNearEndPt(integer);


CREATE FUNCTION   orsSFD02Filter02DrivesNearEndPt(riderrouteId integer) RETURNS TABLE(drive_id integer)
    LANGUAGE plpgsql
    AS $$



-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
--
--  given  riderroute_id paramater, which should be the id of  a request "r",
--   
--  return all the ride_ids that come in close enough in space and time to r's startpoint 
--  *and* close enough in space and time to r's endpoint to be in the preselection
--   
--  This is step 2 of the preselection filtering, which internally calls 
--  step 1 filer: (select  orsSFD01FilterDrivesnNearStartPt(riderrouteId)), see code below
--
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------




DECLARE


   
    --	row from riderundertakes ride
    rideRow  riderundertakesride%ROWTYPE;
    -- rides end point
    endPoint Point;	
    -- local carthesian coordinates of endpoint	
    endpt_c geometry;
    -- number of required seats 
    seatsRequired      integer;
	    


BEGIN

    select * into rideRow from riderundertakesride  where riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine endPoint's carthesian coordinate
    -- ------------------------------------------------------------------	
    endpt_c = rideRow.endpt_c;
    -- ------------------------------------------------------------------
    -- Determine number of required seats
    -- ------------------------------------------------------------------ 
    seatsRequired = rideRow.no_passengers;	

    RETURN QUERY SELECT distinct drp.drive_id  from drive_route_point drp
   	
	where  
	-- only select those that already passed first filter (drive route point near startPoint exists)
	drp.drive_id in (select  orsSFD01FilterDrivesnNearStartPt(riderrouteId))
        -- drive route point should be within testradius 
        AND st_dwithin( drp.coordinate_c, endpt_c , drp.test_radius) 
        --- there should be enough seats availlable 
        AND drp.seats_available >= seatsRequired;

END;

$$;

