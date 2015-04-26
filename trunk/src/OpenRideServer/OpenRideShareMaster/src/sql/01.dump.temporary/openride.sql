--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: openride; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE openride IS 'Database for openride ridesharing app';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


SET search_path = public, pg_catalog;

--
-- Name: _st_asgml(integer, geography, integer, integer, text); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION _st_asgml(integer, geography, integer, integer, text) RETURNS text
    LANGUAGE c IMMUTABLE
    AS '$libdir/postgis-2.1', 'geography_as_gml';


ALTER FUNCTION public._st_asgml(integer, geography, integer, integer, text) OWNER TO openride;

--
-- Name: _st_asgml(integer, geometry, integer, integer, text); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION _st_asgml(integer, geometry, integer, integer, text) RETURNS text
    LANGUAGE c IMMUTABLE
    AS '$libdir/postgis-2.1', 'LWGEOM_asGML';


ALTER FUNCTION public._st_asgml(integer, geometry, integer, integer, text) OWNER TO openride;

--
-- Name: adjustgeomdriveroutepoint(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION adjustgeomdriveroutepoint() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
  BEGIN
    NEW.coordinate_c = st_transform(st_setSrid(st_makepoint(NEW.coordinate[0], NEW.coordinate[1]), 4326), 3068);   
    RETURN NEW;
  END;
$$;


ALTER FUNCTION public.adjustgeomdriveroutepoint() OWNER TO postgres;

--
-- Name: adjustgeomriderundertakesride(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION adjustgeomriderundertakesride() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
  BEGIN
    NEW.startpt_c = st_transform(st_setSrid(st_makepoint(NEW.startpt[0], NEW.startpt[1]), 4326), 3068);   
    NEW.endpt_c = st_transform(st_setSrid(st_makepoint(NEW.endpt[0], NEW.endpt[1]), 4326), 3068);   
    RETURN NEW;
  END;
$$;


ALTER FUNCTION public.adjustgeomriderundertakesride() OWNER TO postgres;

--
-- Name: distancefrompoint(real, real); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION distancefrompoint(lon real, lat real) RETURNS SETOF integer
    LANGUAGE plpgsql
    AS $$


DECLARE

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------

    srid integer := 3068;

BEGIN

    RETURN QUERY SELECT distinct drive_id  from drive_route_point drp
   	
	where  
        
	   drp.expected_arrival  > to_timestamp('23-11-1986 09:30:00', 'DD-MM-YYYY hh24:mi:ss') 
	   AND drp.expected_arrival  <  to_timestamp('23-11-2019 09:30:00', 'DD-MM-YYYY hh24:mi:ss')
           AND st_distance( drp.coordinate_c, st_transform( st_setsrid(st_makepoint( lon , lat ), 4326),3068))  < drp.test_radius 
           AND drp.seats_available >= 1;

END;
$$;


ALTER FUNCTION public.distancefrompoint(lon real, lat real) OWNER TO openride;

--
-- Name: driverundertakesridewithinpoint(real, real); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION driverundertakesridewithinpoint(lon real, lat real) RETURNS SETOF integer
    LANGUAGE plpgsql
    AS $$


DECLARE

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------

    srid integer := 3068;
    
    rideIds integer[];
    	

BEGIN

   SELECT into rideIds  distinct drive_id  from drive_route_point drp
   	
	where  
	-- expected arrival should be within bounds
        drp.expected_arrival  > to_timestamp('23-11-1986 09:30:00', 'DD-MM-YYYY hh24:mi:ss') 
	-- expected arrival should be within bounds	
        AND drp.expected_arrival  <  to_timestamp('23-11-2019 09:30:00', 'DD-MM-YYYY hh24:mi:ss')
        -- drive route point should be within testradius 
        AND st_distance( drp.coordinate_c, st_transform( st_setsrid(st_makepoint( lon , lat ), 4326),3068))  < drp.test_radius 
        --- there should be enough seats availlable 
        AND drp.seats_available >= 1;

END;
$$;


ALTER FUNCTION public.driverundertakesridewithinpoint(lon real, lat real) OWNER TO openride;

--
-- Name: drivesnearstarpoint(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION drivesnearstarpoint(rideid integer) RETURNS TABLE(drive_id integer)
    LANGUAGE plpgsql
    AS $$


DECLARE

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------

    srid integer  := 3068;
   
    --	row from riderundertakes ride
    rideRow  riderundertakesride%ROWTYPE;
    -- rides start point
    startPoint Point;	
    -- longitude/latitude of startpoint	
    lonStart real;
    latStart real;
    -- startTimeEarliest startTimeLatest from ride	
    startTimeEarliest  timestamp;	
    startTimeLatest    timestamp;
    -- number of required seats 
    seatsRequired      integer;
	    


BEGIN

    select * into rideRow from riderundertakesride  where ride_id=rideId;	
    -- ------------------------------------------------------------------
    -- Determine startPoint's longitude and latitude 
    -- ------------------------------------------------------------------	
    lonStart = rideRow.startpt[0];
    latStart = rideRow.startpt[1]; 	
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
        AND st_distance( drp.coordinate_c, st_transform( st_setsrid(st_makepoint( lonStart , latStart ), 4326),3068))  < drp.test_radius 
        --- there should be enough seats availlable 
        AND drp.seats_available >= seatsRequired;


END;

$$;


ALTER FUNCTION public.drivesnearstarpoint(rideid integer) OWNER TO openride;

--
-- Name: kannweg(integer, geometry); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION kannweg(driveid integer, coordinate_c geometry) RETURNS real
    LANGUAGE plpgsql
    AS $$

DECLARE
BEGIN
	return 5;
END;

$$;


ALTER FUNCTION public.kannweg(driveid integer, coordinate_c geometry) OWNER TO openride;

--
-- Name: orsdriveminimaldistance(integer, geometry); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orsdriveminimaldistance(driveid integer, coo_c geometry) RETURNS double precision
    LANGUAGE plpgsql
    AS $$

-- -----------------------------------------------------------------------------------------------
-- Auxiliary function used within OpenRideShares SearchForRider and SearchForDriver algorithms
--
--
-- given:
--         param drive_id,  an integer referencing a drive_id in driverundertakesride
--         param coo_c,     a Postgis Point object
--         computes the distance between the drive's route (represented by drive_route_point elements) 
--         and the point given by coo_c.
-- 
--         Note, that coo_c is suspected to use the same reference system as the drive_route_point.coordinate_c field
--
-- ------------------------------------------------------------------------------------------------


DECLARE

	 minimum double precision;
	
BEGIN
	SELECT  into minimum min(st_distance(coo_c,drp.coordinate_c)) from drive_route_point drp where drp.drive_id=driveId ;
	return minimum;
END;

$$;


ALTER FUNCTION public.orsdriveminimaldistance(driveid integer, coo_c geometry) OWNER TO openride;

--
-- Name: orsemptyseatscount(integer, integer, integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orsemptyseatscount(rideid integer, onrouteliftpoint_idx integer, onroutedroppoint_idx integer) RETURNS integer
    LANGUAGE plpgsql
    AS $_$



-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------
--
--  given the offer with ride_id $rideId
--
--  calculate the minimal number of available seats between route point
--  with index onRouteLiftPoint_idx   
--  with index onRouteDropPoint_idx  
--
--  this is used in SFR and SFD preselection functions to check wheter a 
--  match can be made or not
--
-- ------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------


DECLARE

	result integer = 0;
	  

BEGIN

	select into result min(seats_available) from drive_route_point drp

	where drp.drive_id=rideId
	and   drp.route_idx >= onRouteLiftPoint_idx  
	and   drp.route_idx <= onRouteDropPoint_idx;  

	return result;

END;

$_$;


ALTER FUNCTION public.orsemptyseatscount(rideid integer, onrouteliftpoint_idx integer, onroutedroppoint_idx integer) OWNER TO openride;

--
-- Name: orsenddrpsforride(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orsenddrpsforride(riderrouteid integer) RETURNS TABLE(drive_id integer, onroutedroppointidx integer, onroutedroppoint point, timeatonroutedroppoint timestamp without time zone, distancetosource double precision)
    LANGUAGE plpgsql
    AS $$

-- -------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------
--
-- Part of the preselection. Given a request's riderroute_id as parameter, returns
-- 
-- Data for constructing the PotentialMatch Objects, i.e:
-- 
--     drive_id                : drive id for the match 
--     onRouteDropPointIDX     : Index (in drive_route_point) of the route Drop point 
--     onRouteDropPoint        : Lon/Lat polar coordinates of the route Drop point
--     timeAtOnRouteDropPoint  : time when driver reaches route Drop point
--     distanceToSource        : route Drop point's distance to start
--
-- -------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------

DECLARE

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------
    srid integer  := 3068;
   
    --	row from riderundertakes ride
    rideRow  riderundertakesride%ROWTYPE;
    -- rides startPoint point
    startPoint Point;	
    -- fast carthesian coordinates of startpoint	
    endpt_c Geometry;
    
	    


BEGIN

    select * into rideRow from riderundertakesride  where riderundertakesride.riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine endpts carthesian coordinates
    -- ------------------------------------------------------------------	
    endpt_c = rideRow.endpt_c;
   
    RETURN QUERY SELECT 

		drp.drive_id             as  drive_id               , 
		drp.route_idx            as onRouteDropPointIDX    ,
		drp.coordinate           as  onRouteDropPoint       ,
		drp.expected_arrival     as  timeAtOnRouteDropPoint ,
                drp.distance_to_source   as  distanceToSource 

		from drive_route_point drp
   	
	where  
	-- only select those that already pass first filter near startPoint, and second filter near endpoint
	drp.drive_id in (select  orsSFR02Filter02DrivesNearEndPt(riderrouteId))
	-- select those, that also realize minimal distance to endPoint
	AND st_distance(drp.coordinate_c, endpt_c) = orsDriveMinimalDistance( drp.drive_id , endpt_c ); 

	
END;

$$;


ALTER FUNCTION public.orsenddrpsforride(riderrouteid integer) OWNER TO openride;

--
-- Name: orssfd(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orssfd(riderrouteid integer) RETURNS TABLE(drive_id integer, riderroute_id integer, onrouteliftpointidx integer, onrouteliftpoint point, timeatonrouteliftpoint timestamp without time zone, onroutedroppointidx integer, onroutedroppoint point, shareddistance double precision)
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

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------
    srid integer  := 3068;
   
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
	AND drpStart.drive_id in (select  orsSFD02Filter02DrivesNearEndPt(riderrouteId))
	-- select those, that also realize minimal distance to startPoint
	AND st_distance(drpStart.coordinate_c, startpt_c) = orsDriveMinimalDistance( drpStart.drive_id , startpt_c )
	-- select those, that where endpoints also realize minimal distance to endpoint
	AND st_distance(drpEnd.coordinate_c,     endpt_c) = orsDriveMinimalDistance( drpEnd.drive_id   , endpt_c   )
	-- select only those combinations that provide empty seats on the route 
        AND orsEmptySeatsCount(drpStart.drive_id , drpStart.route_idx, drpEnd.route_idx) <= no_passengers
	; 

	
END;

$$;


ALTER FUNCTION public.orssfd(riderrouteid integer) OWNER TO openride;

--
-- Name: orssfd01filterdrivesnnearstartpt(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orssfd01filterdrivesnnearstartpt(riderrouteid integer) RETURNS TABLE(drive_id integer)
    LANGUAGE plpgsql
    AS $_$

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

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------

    srid integer  := 3068;
   
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

$_$;


ALTER FUNCTION public.orssfd01filterdrivesnnearstartpt(riderrouteid integer) OWNER TO openride;

--
-- Name: orssfd02filter02drivesnearendpt(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orssfd02filter02drivesnearendpt(riderrouteid integer) RETURNS TABLE(drive_id integer)
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

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------

    srid integer  := 3068;
   
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


ALTER FUNCTION public.orssfd02filter02drivesnearendpt(riderrouteid integer) OWNER TO openride;

--
-- Name: orssfr(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orssfr(rideid integer) RETURNS TABLE(drive_id integer, riderroute_id integer, onrouteliftpointidx integer, onrouteliftpoint point, timeatonrouteliftpoint timestamp without time zone, onroutedroppointidx integer, onroutedroppoint point, shareddistance double precision)
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
	and st_dwithin( drpS.coordinate_c, rur.startpt_c, drpS.test_radius) 
	and st_dwithin( drpE.coordinate_c, rur.endpt_c, drpE.test_radius)
	-- Start and endpoint should realize minimal distance	
	-- select those, that also realize minimal distance to startPoint
	and st_distance(drpS.coordinate_c, rur.startpt_c) = orsDriveMinimalDistance( drpS.drive_id , rur.startpt_c )
	-- select those, where endpoints also realize minimal distance to endpoint
	and st_distance(drpE.coordinate_c, rur.endpt_c) = orsDriveMinimalDistance( drpE.drive_id   , rur.endpt_c   )
	-- check availlable seats
	and orsEmptySeatsCount(rideId , drpS.route_idx, drpE.route_idx) <= rur.no_passengers
	;	
	
 
END;

$$;


ALTER FUNCTION public.orssfr(rideid integer) OWNER TO openride;

--
-- Name: orsstartdrpsforride(integer); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orsstartdrpsforride(riderrouteid integer) RETURNS TABLE(drive_id integer, onrouteliftpointidx integer, onrouteliftpoint point, timeatonrouteliftpoint timestamp without time zone, distancetosource double precision)
    LANGUAGE plpgsql
    AS $$

-- -------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------
--
-- Part of the preselection. Given a request's riderroute_id as parameter, returns
-- 
-- Data for constructing the PotentialMatch Objects, i.e:
-- 
--     drive_id                : drive id for the match 
--     onRouteLiftPointIDX     : Index (in drive_route_point) of the route lift point 
--     onRouteLiftPoint        : Lon/Lat polar coordinates of the route lift point
--     timeAtOnRouteLiftPoint  : time when driver reaches route lift point
--     distanceToSource        : route lift point's distance to start
--
-- -------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------

DECLARE

-- -------------------------------------------------------------
--  we use srid 3068, srid for "Germany / Soldner Berlin "
-- -------------------------------------------------------------
    srid integer  := 3068;
   
    --	row from riderundertakes ride
    rideRow  riderundertakesride%ROWTYPE;
    -- rides startPoint point
    startPoint Point;	
    -- fast carthesian coordinates of startpoint	
    startpt_c Geometry;
    
	    


BEGIN

    select * into rideRow from riderundertakesride  where riderundertakesride.riderroute_id=riderrouteId;	
    -- ------------------------------------------------------------------
    -- Determine startpts carthesian coordinates
    -- ------------------------------------------------------------------	
    startpt_c = rideRow.startpt_c;
   
    RETURN QUERY SELECT 

		drp.drive_id             as  drive_id               , 
		drp.route_idx            as onRouteLiftPointIDX    ,
		drp.coordinate           as  onRouteLiftPoint       ,
		drp.expected_arrival     as  timeAtOnRouteLiftPoint ,
                drp.distance_to_source   as  distanceToSource 

		from drive_route_point drp
   	
	where  
	-- only select those that already pass first filter near startPoint, and second filter near endpoint
	drp.drive_id in (select  orsSFR02Filter02DrivesNearEndPt(riderrouteId))
	-- select those, that also realize minimal distance to startPoint
	AND st_distance(drp.coordinate_c, startpt_c) = orsDriveMinimalDistance( drp.drive_id , startpt_c ); 

	
END;

$$;


ALTER FUNCTION public.orsstartdrpsforride(riderrouteid integer) OWNER TO openride;

--
-- Name: somefunc(); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION somefunc() RETURNS integer
    LANGUAGE plpgsql
    AS $$
<< outerblock >>
DECLARE
    quantity integer := 30;
BEGIN
    RAISE NOTICE 'Quantity here is %', quantity;  -- Prints 30
    quantity := 50;
    --
    -- Create a subblock
    --
    DECLARE
        quantity integer := 80;
    BEGIN
        RAISE NOTICE 'Quantity here is %', quantity;  -- Prints 80
        RAISE NOTICE 'Outer quantity here is %', outerblock.quantity;  -- Prints 50
    END;

    RAISE NOTICE 'Quantity here is %', quantity;  -- Prints 50

    RETURN quantity;
END;
$$;


ALTER FUNCTION public.somefunc() OWNER TO openride;

--
-- Name: st_asgml(integer, geography, integer, integer, text); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION st_asgml(version integer, geog geography, maxdecimaldigits integer DEFAULT 15, options integer DEFAULT 0, nprefix text DEFAULT NULL::text) RETURNS text
    LANGUAGE sql IMMUTABLE
    AS $_$ SELECT _ST_AsGML($1, $2, $3, $4, $5);$_$;


ALTER FUNCTION public.st_asgml(version integer, geog geography, maxdecimaldigits integer, options integer, nprefix text) OWNER TO openride;

--
-- Name: FUNCTION st_asgml(version integer, geog geography, maxdecimaldigits integer, options integer, nprefix text); Type: COMMENT; Schema: public; Owner: openride
--

COMMENT ON FUNCTION st_asgml(version integer, geog geography, maxdecimaldigits integer, options integer, nprefix text) IS 'args: version, geog, maxdecimaldigits=15, options=0, nprefix=null - Return the geometry as a GML version 2 or 3 element.';


--
-- Name: st_asgml(integer, geometry, integer, integer, text); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION st_asgml(version integer, geom geometry, maxdecimaldigits integer DEFAULT 15, options integer DEFAULT 0, nprefix text DEFAULT NULL::text) RETURNS text
    LANGUAGE sql IMMUTABLE
    AS $_$ SELECT _ST_AsGML($1, $2, $3, $4,$5); $_$;


ALTER FUNCTION public.st_asgml(version integer, geom geometry, maxdecimaldigits integer, options integer, nprefix text) OWNER TO openride;

--
-- Name: FUNCTION st_asgml(version integer, geom geometry, maxdecimaldigits integer, options integer, nprefix text); Type: COMMENT; Schema: public; Owner: openride
--

COMMENT ON FUNCTION st_asgml(version integer, geom geometry, maxdecimaldigits integer, options integer, nprefix text) IS 'args: version, geom, maxdecimaldigits=15, options=0, nprefix=null - Return the geometry as a GML version 2 or 3 element.';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: accounthistory; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE accounthistory (
    account_amount double precision,
    account_action character varying(255),
    cust_id integer NOT NULL,
    account_timestamp timestamp without time zone NOT NULL
);


ALTER TABLE public.accounthistory OWNER TO openride;

--
-- Name: cardetails; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE cardetails (
    cardet_id integer NOT NULL,
    cust_id integer NOT NULL,
    cardet_colour character varying,
    cardet_brand character varying,
    cardet_buildyear smallint,
    cardet_plateno character varying
);


ALTER TABLE public.cardetails OWNER TO openride;

--
-- Name: cardetails_cardet_id_seq; Type: SEQUENCE; Schema: public; Owner: openride
--

CREATE SEQUENCE cardetails_cardet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cardetails_cardet_id_seq OWNER TO openride;

--
-- Name: cardetails_cardet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: openride
--

ALTER SEQUENCE cardetails_cardet_id_seq OWNED BY cardetails.cardet_id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE customer (
    cust_id integer NOT NULL,
    cust_addr_zipcode character varying(255),
    cust_addr_city character varying(255),
    cust_nickname character varying(255),
    cust_driverpref_age integer,
    cust_firstname character varying(255),
    cust_driverpref_gender character(1),
    cust_dateofbirth date,
    cust_driverpref_musictaste character varying(255),
    cust_mobilephoneno character varying(255),
    cust_riderpref_age integer,
    cust_email character varying(255),
    cust_riderpref_gender character(1),
    cust_issmoker boolean,
    cust_riderpref_musictaste character varying(255),
    cust_postident boolean,
    cust_bank_account integer,
    cust_bank_code integer,
    cust_lastname character varying(255),
    cust_presencemssg character varying(255),
    cust_fixedphoneno character varying(255),
    cust_registrdate time without time zone,
    cust_licensedate date,
    cust_account_balance double precision,
    cust_passwd character varying(255),
    cust_profilepic character varying(255),
    cust_gender character(1),
    cust_addr_street character varying(255),
    cust_driverpref_smoker character(1),
    cust_riderpref_smoker character(1),
    cust_session_id integer,
    is_logged_in boolean,
    cust_group character varying(255),
    last_customer_check timestamp without time zone,
    last_matching_change timestamp without time zone,
    preferred_language character varying(10),
    show_email boolean,
    show_mobile boolean,
    matchlimitmax integer,
    matchlimitindividual integer,
    preferredunitoflength integer
);


ALTER TABLE public.customer OWNER TO openride;

--
-- Name: drive_route_point; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE drive_route_point (
    drive_id integer NOT NULL,
    route_idx integer NOT NULL,
    coordinate point NOT NULL,
    expected_arrival timestamp without time zone NOT NULL,
    seats_available integer NOT NULL,
    coordinate_c geometry,
    distance_to_source double precision,
    test_radius double precision,
    CONSTRAINT enforce_dims_coordinate_c CHECK ((st_ndims(coordinate_c) = 2)),
    CONSTRAINT enforce_geotype_coordinate_c CHECK (((geometrytype(coordinate_c) = 'POINT'::text) OR (coordinate_c IS NULL))),
    CONSTRAINT enforce_srid_coordinate_c CHECK ((st_srid(coordinate_c) = 3068))
);


ALTER TABLE public.drive_route_point OWNER TO openride;

--
-- Name: driverundertakesride; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE driverundertakesride (
    ride_id integer NOT NULL,
    ride_weekdays character varying(255),
    ride_series_id integer,
    ride_starttime timestamp without time zone,
    ride_comment character varying(255),
    ride_acceptable_detour_in_min integer,
    ride_offeredseats_no integer,
    cust_id integer,
    ride_startpt point,
    ride_endpt point,
    ride_currpos point,
    startpt_addr character varying(255),
    endpt_addr character varying(255),
    ride_acceptable_detour_in_km integer,
    ride_acceptable_detour_in_percent integer,
    ride_route_point_distance_meters double precision NOT NULL,
    last_matching_state integer,
    is_countermanded boolean
);


ALTER TABLE public.driverundertakesride OWNER TO openride;

--
-- Name: favoritepoint; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE favoritepoint (
    favpt_point character varying(255) NOT NULL,
    cust_id integer NOT NULL,
    favpt_frequency integer NOT NULL,
    favpt_displayname character varying(255),
    favpt_address character varying(255),
    favpt_id integer NOT NULL
);


ALTER TABLE public.favoritepoint OWNER TO openride;

--
-- Name: favoritepoints_favpt_id_seq; Type: SEQUENCE; Schema: public; Owner: openride
--

CREATE SEQUENCE favoritepoints_favpt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.favoritepoints_favpt_id_seq OWNER TO openride;

--
-- Name: favoritepoints_favpt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: openride
--

ALTER SEQUENCE favoritepoints_favpt_id_seq OWNED BY favoritepoint.favpt_id;


--
-- Name: match; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE match (
    riderroute_id integer NOT NULL,
    ride_id integer NOT NULL,
    match_shared_distance_meters double precision,
    match_detour_meters double precision,
    match_expected_start_time timestamp without time zone,
    match_drive_remaining_distance_meters double precision,
    match_price_cents integer,
    driver_change timestamp without time zone,
    rider_change timestamp without time zone,
    driver_access timestamp without time zone,
    rider_access timestamp without time zone,
    driver_state integer,
    rider_state integer,
    rider_message character varying(255),
    driver_message character varying(255)
);


ALTER TABLE public.match OWNER TO openride;

--
-- Name: message; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE message (
    message_id integer,
    subject character varying(128),
    sender_id integer,
    recipient_id integer,
    match_request integer,
    match_offer integer,
    created timestamp without time zone,
    received timestamp without time zone,
    message character varying(255),
    deliverytype integer
);


ALTER TABLE public.message OWNER TO openride;

--
-- Name: registration_pass; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE registration_pass (
    id integer NOT NULL,
    passcode character varying(32) NOT NULL,
    creation_date date DEFAULT ('now'::text)::date NOT NULL,
    usage_date date,
    cust_id integer
);


ALTER TABLE public.registration_pass OWNER TO openride;

--
-- Name: registration_pass_id_seq; Type: SEQUENCE; Schema: public; Owner: openride
--

CREATE SEQUENCE registration_pass_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.registration_pass_id_seq OWNER TO openride;

--
-- Name: registration_pass_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: openride
--

ALTER SEQUENCE registration_pass_id_seq OWNED BY registration_pass.id;


--
-- Name: riderundertakesride; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE riderundertakesride (
    riderroute_id integer NOT NULL,
    ride_id integer,
    starttime_latest timestamp without time zone,
    timestamprealized timestamp without time zone,
    price double precision,
    cust_id integer,
    no_passengers integer,
    timestampbooked timestamp without time zone,
    account_timestamp timestamp without time zone,
    starttime_earliest timestamp without time zone,
    startpt point,
    endpt point,
    startpt_addr character varying(255),
    endpt_addr character varying(255),
    givenrating smallint,
    givenrating_comment character varying(80),
    givenrating_date timestamp without time zone,
    receivedrating smallint,
    receivedrating_comment character varying(80),
    receivedrating_date timestamp without time zone,
    startpt_c geometry,
    endpt_c geometry,
    comment character varying(255),
    last_matching_state integer,
    is_countermanded boolean,
    CONSTRAINT enforce_dims_endpt_c CHECK ((st_ndims(endpt_c) = 2)),
    CONSTRAINT enforce_dims_startpt_c CHECK ((st_ndims(startpt_c) = 2)),
    CONSTRAINT enforce_geotype_endpt_c CHECK (((geometrytype(endpt_c) = 'POINT'::text) OR (endpt_c IS NULL))),
    CONSTRAINT enforce_geotype_startpt_c CHECK (((geometrytype(startpt_c) = 'POINT'::text) OR (startpt_c IS NULL))),
    CONSTRAINT enforce_srid_endpt_c CHECK ((st_srid(endpt_c) = 3068)),
    CONSTRAINT enforce_srid_startpt_c CHECK ((st_srid(startpt_c) = 3068))
);


ALTER TABLE public.riderundertakesride OWNER TO openride;

--
-- Name: route_point; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE route_point (
    ride_id integer NOT NULL,
    route_idx integer NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    riderroute_id integer,
    is_required boolean NOT NULL
);


ALTER TABLE public.route_point OWNER TO openride;

--
-- Name: sequence; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO openride;

--
-- Name: waypoint; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE waypoint (
    waypoint_id integer NOT NULL,
    ride_id integer NOT NULL,
    route_idx integer NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    description text
);


ALTER TABLE public.waypoint OWNER TO openride;

--
-- Name: cardet_id; Type: DEFAULT; Schema: public; Owner: openride
--

ALTER TABLE ONLY cardetails ALTER COLUMN cardet_id SET DEFAULT nextval('cardetails_cardet_id_seq'::regclass);


--
-- Name: favpt_id; Type: DEFAULT; Schema: public; Owner: openride
--

ALTER TABLE ONLY favoritepoint ALTER COLUMN favpt_id SET DEFAULT nextval('favoritepoints_favpt_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: openride
--

ALTER TABLE ONLY registration_pass ALTER COLUMN id SET DEFAULT nextval('registration_pass_id_seq'::regclass);


--
-- Data for Name: accounthistory; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY accounthistory (account_amount, account_action, cust_id, account_timestamp) FROM stdin;
\.


--
-- Data for Name: cardetails; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY cardetails (cardet_id, cust_id, cardet_colour, cardet_brand, cardet_buildyear, cardet_plateno) FROM stdin;
\.


--
-- Name: cardetails_cardet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: openride
--

SELECT pg_catalog.setval('cardetails_cardet_id_seq', 1, false);


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY customer (cust_id, cust_addr_zipcode, cust_addr_city, cust_nickname, cust_driverpref_age, cust_firstname, cust_driverpref_gender, cust_dateofbirth, cust_driverpref_musictaste, cust_mobilephoneno, cust_riderpref_age, cust_email, cust_riderpref_gender, cust_issmoker, cust_riderpref_musictaste, cust_postident, cust_bank_account, cust_bank_code, cust_lastname, cust_presencemssg, cust_fixedphoneno, cust_registrdate, cust_licensedate, cust_account_balance, cust_passwd, cust_profilepic, cust_gender, cust_addr_street, cust_driverpref_smoker, cust_riderpref_smoker, cust_session_id, is_logged_in, cust_group, last_customer_check, last_matching_change, preferred_language, show_email, show_mobile, matchlimitmax, matchlimitindividual, preferredunitoflength) FROM stdin;
58001	\N	\N	user5	\N	user	\N	\N	\N	\N	\N	user5@orangetraining.de	\N	\N	\N	\N	\N	\N	user	\N	\N	19:37:42	\N	\N	0a791842f52a0acfbb3a783378c066b8	\N	-	\N	\N	\N	0	\N	customer	\N	\N	en	\N	\N	51	32	\N
57853	\N	\N	user3	\N	user3	\N	\N	\N	\N	\N	user3@orangetraining.de	\N	\N	\N	\N	\N	\N	user3	\N	\N	12:39:24	\N	\N	92877af70a45fd6a2ed7fe81e1236b78	\N	-	\N	\N	\N	0	\N	customer	\N	\N	de	\N	\N	200	23	\N
57951	\N	\N	user4	\N	user4	\N	\N	\N	\N	\N	user4@localhost	\N	\N	\N	\N	\N	\N	user4	\N	\N	21:11:36	\N	\N	5f20cad560d10081d8c365be3c393af5	\N	-	\N	\N	\N	0	\N	customer	\N	\N	de	\N	\N	200	23	\N
58201	\N	\N	rita	\N	rita	\N	\N	\N	\N	\N	rita@rita	\N	\N	\N	\N	\N	\N	rita	\N	\N	16:32:12	\N	\N	b8c45283c858bff72d6ed9f659bfd0e8	\N	-	\N	\N	\N	0	\N	customer	\N	\N	de	\N	\N	\N	\N	\N
57851			user1	\N	horst	\N	\N	\N	123212312	0	jochen.laser@orangetraining.de	-	f	\N	\N	\N	\N	krawuppke	\N		12:38:01	2015-02-18	\N	24c9e15e52afc47c225b757e7bee1f9d	\N	-		\N	y	0	\N	customer	2015-04-21 20:06:49.892	2015-04-25 20:08:43.201	en	f	f	9999	9999	2
57852	\N	\N	user2	\N	user2	\N	\N	\N	\N	\N	user2@orangetraining.de	\N	\N	\N	\N	\N	\N	user2	\N	\N	12:39:04	\N	\N	7e58d63b60197ceb55a1c487989a3720	\N	-	\N	\N	\N	0	\N	customer	2015-04-25 15:59:10.162	2015-04-25 20:08:43.201	de	\N	\N	200	3	\N
\.


--
-- Data for Name: drive_route_point; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY drive_route_point (drive_id, route_idx, coordinate, expected_arrival, seats_available, coordinate_c, distance_to_source, test_radius) FROM stdin;
\.


--
-- Data for Name: driverundertakesride; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY driverundertakesride (ride_id, ride_weekdays, ride_series_id, ride_starttime, ride_comment, ride_acceptable_detour_in_min, ride_offeredseats_no, cust_id, ride_startpt, ride_endpt, ride_currpos, startpt_addr, endpt_addr, ride_acceptable_detour_in_km, ride_acceptable_detour_in_percent, ride_route_point_distance_meters, last_matching_state, is_countermanded) FROM stdin;
\.


--
-- Data for Name: favoritepoint; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY favoritepoint (favpt_point, cust_id, favpt_frequency, favpt_displayname, favpt_address, favpt_id) FROM stdin;
53.3995897,11.5041162	57851	0	Wöbbelin Fliederweg	Fliederweg, Wöbbelin, Ludwigslust-Land, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland	57854
53.2765605,11.3716255	57851	0	Glaisin, Dorfstrasse	Dorfstraße, Ludwigslust, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland	57855
53.3249131,11.4883878667331	57851	0	Schloß Ludwigslust	Schloss Ludwigslust, Schloßfreiheit, Ludwigslust, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland	57856
53.3300973,11.3877354	57851	0	Kummer	Kummer, Ludwigslust, Mecklenburg-Vorpommern, Deutschland	57857
53.3458745,11.4036132	57851	0	Warlow	Warlow, Ludwigslust-Land, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, Deutschland	57858
53.3568464,11.4563665	57851	0	Weselsdorf	Weselsdorf, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, Deutschland	57859
53.4145149,11.4538572	57851	0	Lüblow	Lüblow, Ludwigslust-Land, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, Deutschland	57860
53.4,11.5	57852	0	Wöbbelin	Wöbbelin, Ludwigslust-Land, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, Deutschland	57866
53.2804408,11.3670075	57852	0	Glaisin	Glaisin, Ludwigslust, Mecklenburg-Vorpommern, Deutschland	57867
53.4164737,11.4513794	57852	0	Lüblow	Lüblow, Dorfplatz, Forsthaus, Lüblow, Ludwigslust-Land, Landkreis Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Germany	58251
53.3300973,11.3877354	57852	0	Kummer	Kummer, Ludwigslust, Mecklenburg-Vorpommern, Germany	58252
\.


--
-- Name: favoritepoints_favpt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: openride
--

SELECT pg_catalog.setval('favoritepoints_favpt_id_seq', 1, false);


--
-- Data for Name: match; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY match (riderroute_id, ride_id, match_shared_distance_meters, match_detour_meters, match_expected_start_time, match_drive_remaining_distance_meters, match_price_cents, driver_change, rider_change, driver_access, rider_access, driver_state, rider_state, rider_message, driver_message) FROM stdin;
\.


--
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY message (message_id, subject, sender_id, recipient_id, match_request, match_offer, created, received, message, deliverytype) FROM stdin;
\.


--
-- Data for Name: registration_pass; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY registration_pass (id, passcode, creation_date, usage_date, cust_id) FROM stdin;
\.


--
-- Name: registration_pass_id_seq; Type: SEQUENCE SET; Schema: public; Owner: openride
--

SELECT pg_catalog.setval('registration_pass_id_seq', 4, true);


--
-- Data for Name: riderundertakesride; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY riderundertakesride (riderroute_id, ride_id, starttime_latest, timestamprealized, price, cust_id, no_passengers, timestampbooked, account_timestamp, starttime_earliest, startpt, endpt, startpt_addr, endpt_addr, givenrating, givenrating_comment, givenrating_date, receivedrating, receivedrating_comment, receivedrating_date, startpt_c, endpt_c, comment, last_matching_state, is_countermanded) FROM stdin;
\.


--
-- Data for Name: route_point; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY route_point (ride_id, route_idx, longitude, latitude, riderroute_id, is_required) FROM stdin;
\.


--
-- Data for Name: sequence; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY sequence (seq_name, seq_count) FROM stdin;
SEQ_GEN\n	1000
SEQ_GEN	84900
\.


--
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
\.


--
-- Data for Name: waypoint; Type: TABLE DATA; Schema: public; Owner: openride
--

COPY waypoint (waypoint_id, ride_id, route_idx, longitude, latitude, description) FROM stdin;
\.


--
-- Name: RegsitrationPass_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY registration_pass
    ADD CONSTRAINT "RegsitrationPass_pkey" PRIMARY KEY (id);


--
-- Name: accounthistory_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY accounthistory
    ADD CONSTRAINT accounthistory_pkey PRIMARY KEY (cust_id, account_timestamp);


--
-- Name: cardetails_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY cardetails
    ADD CONSTRAINT cardetails_pkey PRIMARY KEY (cardet_id);


--
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (cust_id);


--
-- Name: drive_route_point_pk; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY drive_route_point
    ADD CONSTRAINT drive_route_point_pk PRIMARY KEY (drive_id, route_idx);


--
-- Name: driverundertakesride_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY driverundertakesride
    ADD CONSTRAINT driverundertakesride_pkey PRIMARY KEY (ride_id);


--
-- Name: favoritepoints_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY favoritepoint
    ADD CONSTRAINT favoritepoints_pkey PRIMARY KEY (favpt_id);


--
-- Name: match_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY match
    ADD CONSTRAINT match_pkey PRIMARY KEY (riderroute_id, ride_id);


--
-- Name: nickname_unique; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT nickname_unique UNIQUE (cust_nickname);


--
-- Name: registration_pass_passcode_key; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY registration_pass
    ADD CONSTRAINT registration_pass_passcode_key UNIQUE (passcode);


--
-- Name: riderundertakesride_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY riderundertakesride
    ADD CONSTRAINT riderundertakesride_pkey PRIMARY KEY (riderroute_id);


--
-- Name: route_point_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY route_point
    ADD CONSTRAINT route_point_pkey PRIMARY KEY (ride_id, route_idx);


--
-- Name: sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- Name: uc_favoritepoints_cust_id_displayname; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY favoritepoint
    ADD CONSTRAINT uc_favoritepoints_cust_id_displayname UNIQUE (cust_id, favpt_displayname);


--
-- Name: waypoint_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY waypoint
    ADD CONSTRAINT waypoint_pkey PRIMARY KEY (waypoint_id);


--
-- Name: drive_route_point_trigger; Type: TRIGGER; Schema: public; Owner: openride
--

CREATE TRIGGER drive_route_point_trigger BEFORE INSERT OR UPDATE ON drive_route_point FOR EACH ROW EXECUTE PROCEDURE adjustgeomdriveroutepoint();


--
-- Name: riderundertakesride_trigger; Type: TRIGGER; Schema: public; Owner: openride
--

CREATE TRIGGER riderundertakesride_trigger BEFORE INSERT OR UPDATE ON riderundertakesride FOR EACH ROW EXECUTE PROCEDURE adjustgeomriderundertakesride();


--
-- Name: drive_route_point_fk; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY drive_route_point
    ADD CONSTRAINT drive_route_point_fk FOREIGN KEY (drive_id) REFERENCES driverundertakesride(ride_id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: fk_accounthistory_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY accounthistory
    ADD CONSTRAINT fk_accounthistory_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: fk_cardetails_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY cardetails
    ADD CONSTRAINT fk_cardetails_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id) ON DELETE RESTRICT;


--
-- Name: fk_driverundertakesride_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY driverundertakesride
    ADD CONSTRAINT fk_driverundertakesride_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: fk_favoritepoints_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY favoritepoint
    ADD CONSTRAINT fk_favoritepoints_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: fk_riderundertakesride_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY riderundertakesride
    ADD CONSTRAINT fk_riderundertakesride_cust_id FOREIGN KEY (cust_id, account_timestamp) REFERENCES accounthistory(cust_id, account_timestamp);


--
-- Name: fk_riderundertakesride_ride_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY riderundertakesride
    ADD CONSTRAINT fk_riderundertakesride_ride_id FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: message_recipient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_recipient_id_fkey FOREIGN KEY (recipient_id) REFERENCES customer(cust_id) ON DELETE SET NULL;


--
-- Name: message_sender_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_sender_id_fkey FOREIGN KEY (sender_id) REFERENCES customer(cust_id) ON DELETE SET NULL;


--
-- Name: registration_pass_custid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY registration_pass
    ADD CONSTRAINT registration_pass_custid_fkey FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: rideid; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY match
    ADD CONSTRAINT rideid FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: riderrouteid; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY match
    ADD CONSTRAINT riderrouteid FOREIGN KEY (riderroute_id) REFERENCES riderundertakesride(riderroute_id);


--
-- Name: route_point_ride_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY route_point
    ADD CONSTRAINT route_point_ride_id_fkey FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: waypoint_ride_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY waypoint
    ADD CONSTRAINT waypoint_ride_id_fkey FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO openride;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

