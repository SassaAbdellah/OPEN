


DROP function IF EXISTS orsSFD01FilterDrivesnNearStartPt(integer);

--
-- Name: orsSFD01drivesnearstartpt(integer); Type: FUNCTION; Schema: public; Owner: openride
--






CREATE FUNCTION orsSFD01FilterDrivesnNearStartPt(riderrouteId integer) RETURNS TABLE(drive_id integer)
    LANGUAGE plpgsql
    AS $$

-- -------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------
--
--   For request with riderroute_id $riderrouteId,
--
--   returns list of drive_ids for all those drives, 
--   which come within radius max_detour (respectively testradius around drive_route_point)
--
--   This is the first "filter" to be used in SearchForRider Preselection step.
--
-- -------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------

DECLARE


   
    --	row from riderundertakes ride
    rideRow  riderundertakesride%ROWTYPE;
    -- fast local cartesian coordinates of startpoint	
    startpt_c geometry;
    -- startTimeEarliest startTimeLatest from ride	
    startTimeEarliest  timestamp;	
    startTimeLatest    timestamp;
    -- number of required seats 
    seatsRequired      integer;
	    


BEGIN

    select * into rideRow from riderundertakesride  where riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine startPoint's cartesian coordinates
    -- ------------------------------------------------------------------	
    startpt_c = rideRow.startpt_c;
    -- ------------------------------------------------------------------
    -- Determine startTimeEarliest, startTimeLatest 
    -- ------------------------------------------------------------------
    startTimeEarliest = rideRow.starttime_earliest; 	
    startTimeLatest   = rideRow.starttime_latest;
    -- ------------------------------------------------------------------
    -- Determine number of required seats
    -- ------------------------------------------------------------------ 
    seatsRequired = rideRow.no_passengers;	

    RETURN QUERY SELECT distinct drp.drive_id  from drive_route_point drp
   	
	where  
	-- expected arrival should be within bounds
            drp.expected_arrival  > startTimeEarliest 
	-- expected arrival should be within bounds	
        AND drp.expected_arrival  < startTimeLatest
        -- drive route point should be within testradius 
        AND st_dwithin( drp.coordinate_c, startpt_c, drp.test_radius) 
        --- there should be enough seats availlable 
        AND drp.seats_available >= seatsRequired;

END;

$$;


