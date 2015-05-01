

-- ------------------------------
-- DROP before recreate 
-- ------------------------------
DROP FUNCTION IF EXISTS orsPolSFD(integer);



CREATE FUNCTION orsPolSFD(riderrouteId integer) 
	RETURNS TABLE(	drive_id 		 integer		       	, 
			riderroute_id            integer                        ,
			onrouteliftpointidx 	 integer	               	, 
			onrouteliftpoint 	 point		 	 	, 
			timeatonrouteliftpoint   timestamp without time zone	, 
			onroutedroppointidx      integer			,
                        onroutedroppoint         point                        	,
			sharedDistance           double precision
			)
    LANGUAGE plpgsql
    AS $$

-- -------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------
--
--
-- "Search for Drivers" part of the preselection algorithm. 
--  Given an request's  riderroute_id as  initial parameter, returns
-- 
-- Data for constructing the PotentialMatch Objects, i.e:
-- 
--     drive_id                : drive id  (offer's id) for the match
--     riderroute_id           : riderroute_id (requests'id) for the match 
--     onRouteLiftPointIDX     : index (in drive_route_point) of the route lift point 
--     onRouteLiftPoint        : Lon/Lat polar coordinates of the route lift point
--     timeAtOnRouteLiftPoint  : time when driver reaches route lift point
--     onRouteDropPointIDX     : index (in drive_route_point) of the route drop point 
--     onRouteDropPoint        : Lon/Lat polar coordinates of the route drop point
--     sharedDistance          : common distance covered
--
--
-- 
--    note that functions orsSFD (Search for Driver Preselection) and orsSFR (Search for Rider Preselection)
--    should return compatible resultsets, so the code creating the matchlist can be compatible
--
-- -------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------

DECLARE


    --	row from riderundertakes ride
    rideRow  riderundertakesride%ROWTYPE;
    -- rides startPoint point
    startPoint Point;	
    -- polar coordinates of ride's startpoint and endpoint	
    startpt        Geography;
    endpt          Geography;
    no_passengers    integer=-1;	    
    	

BEGIN

    select * into rideRow from riderundertakesride  where riderundertakesride.riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine startpts/endpts polar coordinates, no of passengers
    -- ------------------------------------------------------------------	
    startpt = ST_MakePoint(rideRow.startpt[0],rideRow.startpt[1])::geography;
    endpt   = ST_MakePoint(rideRow.endpt[0],rideRow.endpt[1])::geography  ;
    no_passengers = rideRow.no_passengers;

	

    RETURN QUERY SELECT 

		drpStart.drive_id             	as  drive_id                  , 
                riderrouteId                    as  riderroute_id             ,
		drpStart.route_idx            	as  onRouteLiftPointIDX       ,
		drpStart.coordinate           	as  onRouteLiftPoint          ,
		drpStart.expected_arrival     	as  timeAtOnRouteLiftPoint    ,
		drpEnd.route_idx              	as  onRouteDropPointIDX       ,
                drpEnd.coordinate             	as  onRouteDropPoint          ,
		drpEnd.distance_to_source - drpStart.distance_to_source   as  sharedDistance
 				
		from drive_route_point drpStart,drive_route_point drpEnd
   	
	
	-- JOIN criterion
	where drpStart.drive_id=drpEnd.drive_id
	-- only select those drive_id  that already pass first filter near startPoint, and second filter near endpoint
	AND drpStart.drive_id in (select  orsPolSFDFilter02DrivesNearEndPt(riderrouteId))
	-- select those, that also realize minimal distance to startPoint
	AND st_distance(ST_MakePoint(drpStart.coordinate[0],drpStart.coordinate[1])::geography, startpt) = orsPolDriveMinimalDistance( drpStart.drive_id , startpt )
	-- select those, that where endpoints also realize minimal distance to endpoint
	AND st_distance(ST_MakePoint(drpEnd.coordinate[0],drpEnd.coordinate[1])::geography,     endpt) = orsPolDriveMinimalDistance( drpEnd.drive_id   , endpt   )
	-- select only those combinations that provide empty seats on the route 
        AND orsPolEmptySeatsCount(drpStart.drive_id , drpStart.route_idx, drpEnd.route_idx) <= no_passengers
	; 

	
END;

$$;


