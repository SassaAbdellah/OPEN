

-- ------------------------------
-- DROP before recreate 
-- ------------------------------
DROP FUNCTION IF EXISTS orsSFD_ngr(integer);


-- -----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------
-- 
-- SFD implementation using windowing
-- Author: nikos
-- 
-- TODO: documentation?  
--
-- -----------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------

CREATE FUNCTION orsSFD_ngr(riderrouteId integer) 
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
    -- fast carthesian coordinates of ride's startpoint and endpoint	
    startpt_c        Geometry;
    endpt_c          Geometry;
    no_passengers    integer=-1;	    
    	

BEGIN

    select * into rideRow from riderundertakesride  where riderundertakesride.riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine startpts/endpts carthesian coordinates, no of passengers
    -- ------------------------------------------------------------------	
    startpt_c = rideRow.startpt_c;
    endpt_c   = rideRow.endpt_c  ;
    no_passengers = rideRow.no_passengers;
	--startTimeEarliest = rideRow.starttime_earliest; 	
    --startTimeLatest = rideRow.starttime_latest;

RETURN QUERY 
select 
	candidates2.drive_id				as  drive_id		      ,
	riderrouteId                            	as  riderroute_id             ,
	candidates1.st_route_idx            		as  onRouteLiftPointIDX       ,
	candidates1.start_coordinate           		as  onRouteLiftPoint          ,
	candidates1.expected_arrival     		as  timeAtOnRouteLiftPoint    ,
	candidates2.end_route_idx              		as  onRouteDropPointIDX       ,
	candidates2.end_coordinate             		as  onRouteDropPoint          ,
	candidates2.end_dst - candidates1.st_dst	as  sharedDistance
	FROM
	(select drp.drive_id,drp.route_idx 
		as end_route_idx,drp.coordinate 
		as end_coordinate,drp.distance_to_source 
		as end_dst,st_distance(drp.coordinate_c, endpt_c ) 
		as dist,rank() over( 
					PARTITION BY drp.drive_id ORDER BY st_distance(drp.coordinate_c, endpt_c )) as pos
		from drive_route_point drp 

	where
        st_dwithin( drp.coordinate_c, endpt_c, drp.test_radius) 
        --- there should be enough seats availlable 
        AND drp.seats_available >= no_passengers
	) 
	
	AS candidates2 INNER JOIN 
	(	select tmp.drive_id,tmp.st_route_idx,tmp.start_coordinate,tmp.expected_arrival,tmp.st_dst
	FROM
	(	select drp.drive_id,drp.route_idx 
		as st_route_idx,drp.coordinate 
		as start_coordinate,drp.expected_arrival,drp.distance_to_source 
		as st_dst,st_distance(drp.coordinate_c, startpt_c ) 
		as dist,rank() over( PARTITION BY drp.drive_id 
		ORDER BY st_distance(drp.coordinate_c, startpt_c )) as pos
		from drive_route_point drp
	where
	   drp.expected_arrival  >= rideRow.starttime_earliest
	-- expected arrival should be within bounds	
        AND drp.expected_arrival  <= rideRow.starttime_latest
        -- drive route point should be within testradius 
        AND st_dwithin( drp.coordinate_c, startpt_c, drp.test_radius) 
        --- there should be enough seats availlable 
        AND drp.seats_available >= no_passengers
	) AS tmp
		WHERE pos =1
	) AS candidates1
	USING(drive_id) 
	WHERE pos =1 and
	-- select only those combinations that provide empty seats on the route 
	orsEmptySeatsCount(candidates2.drive_id , candidates1.st_route_idx, candidates2.end_route_idx) >= no_passengers;

END;

$$;
