

-- ------------------------------
-- DROP before recreate 
-- ------------------------------
DROP FUNCTION IF EXISTS orsPolSFR(integer);


CREATE FUNCTION orsPolSFR(rideId integer) 

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
-- "Search for Riders" part of the preselection algorithm. 
--  Given an offer's  ride_id as  initial parameter, returns
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
    driveRow  driverundertakesride%ROWTYPE;

	    
BEGIN
-- fetch drives complete data	
select * into driveRow from driverundertakesride  where driverundertakesride.ride_id=rideId;	
   

-- fetch

RETURN QUERY 
	
	select 
		
		rideId		             	as  drive_id                  , 
                rur.riderroute_id               as  riderroute_id             ,
		drpS.route_idx            	as  onRouteLiftPointIDX       ,
		drpS.coordinate           	as  onRouteLiftPoint          ,
		drpS.expected_arrival     	as  timeAtOnRouteLiftPoint    ,
		drpE.route_idx              	as  onRouteDropPointIDX       ,
                drpE.coordinate             	as  onRouteDropPoint          ,
		drpE.distance_to_source - drpS.distance_to_source   as  sharedDistance

	
	from  drive_route_point drpS, drive_route_point drpE, riderundertakesride rur 
	where
	-- request should not have already been matched,  
	rur.ride_id IS NULL
	-- drive_route_points should be about the offer given as parameter
	and drpS.drive_id=rideId
	and drpE.drive_id=rideId
	-- There must be enough seats availlable!
	and drpS.seats_available>=rur.no_passengers
	and drpE.seats_available>=rur.no_passengers
	-- start and endpoint of the request should be within reach
	and st_dwithin( ST_MakePoint(drpS.coordinate[0],drpS.coordinate[1])::geography, ST_MakePoint(rur.startpt[0],rur.startpt[1])::geography, drpS.test_radius) 
	and st_dwithin( ST_MakePoint(drpE.coordinate[0],drpE.coordinate[1])::geography, ST_MakePoint(rur.endpt[0],rur.endpt[1])::geography, drpE.test_radius)
	-- Start and endpoint should realize minimal distance	
	-- select those, that also realize minimal distance to startPoint
	and st_distance(ST_MakePoint(drpS.coordinate[0],drpS.coordinate[1])::geography, ST_MakePoint(rur.startpt[0],rur.startpt[1])::geography) = orsPolDriveMinimalDistance( drpS.drive_id , ST_MakePoint(rur.startpt[0],rur.startpt[1])::geography )
	-- select those, where endpoints also realize minimal distance to endpoint
	and st_distance(ST_MakePoint(drpE.coordinate[0],drpE.coordinate[1])::geography, ST_MakePoint(rur.endpt[0],rur.endpt[1])::geography) = orsPolDriveMinimalDistance( drpE.drive_id   , ST_MakePoint(rur.endpt[0],rur.endpt[1])::geography )
	-- check availlable seats
	and orsPolEmptySeatsCount(rideId , drpS.route_idx, drpE.route_idx) <= rur.no_passengers
	;	
	
 
END;

$$;


