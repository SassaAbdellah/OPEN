

-- -----------------------------------------------------------------
-- drop before recreate
-- -----------------------------------------------------------------
DROP FUNCTION IF EXISTS orsPolSFDFilter02DrivesNearEndPt(integer);

CREATE FUNCTION   orsPolSFDFilter02DrivesNearEndPt(riderrouteId integer) RETURNS TABLE(drive_id integer)
    LANGUAGE plpgsql
    AS $$



-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
--
--  given  riderroute_id paramater, which should be the id of  a request "r", *POL*ar coordinate version
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
    -- local geography (polar coordinates) of endpoint	
    endpt geography;
    -- number of required seats 
    seatsRequired      integer;
	    


BEGIN

    select * into rideRow from riderundertakesride  where riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine endPoint's polar coordinates
    -- ------------------------------------------------------------------	
    endpt = ST_MakePoint(rideRow.endpt[0],rideRow.endpt[1])::geography;
    -- ------------------------------------------------------------------
    -- Determine number of required seats
    -- ------------------------------------------------------------------ 
    seatsRequired = rideRow.no_passengers;	

    RETURN QUERY SELECT distinct drp.drive_id  from drive_route_point drp
   	
	where  
	-- only select those that already passed first filter (drive route point near startPoint exists)
	drp.drive_id in (select  orsPolSFDFilter01DrivesnNearStartPt(riderrouteId))
        -- drive route point should be within testradius 
        AND st_dwithin( ST_MakePoint(drp.coordinate[0],drp.coordinate[1])::geography, endpt , drp.test_radius) 
        --- there should be enough seats availlable 
        AND drp.seats_available >= seatsRequired;

END;

$$;

