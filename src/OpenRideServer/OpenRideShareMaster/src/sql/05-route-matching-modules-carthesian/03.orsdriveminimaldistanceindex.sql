

-- -----------------------------------------------------------------------
--  Drop before recreate
-- -----------------------------------------------------------------------

DROP FUNCTION IF EXISTS orsDriveMinimalDistanceIndex(integer, geometry); 


--
-- Name: orsdriveminimaldistance(integer, geometry); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orsDriveMinimalDistanceIndex(driveid integer, coo_c geometry) RETURNS integer
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
--         and the point given by coo_c, and returns the route_idx of this point
-- 
--         Note, that coo_c is suspected to use the same reference system as the drive_route_point.coordinate_c field
--
-- ------------------------------------------------------------------------------------------------


DECLARE

	 routeIndex integer;
	
BEGIN
	
	
	
	select route_idx into routeIndex
	from (
		select row_number() over( ORDER BY  st_distance(coordinate_c, coo_c )) as row_number, 
		route_idx
		from drive_route_point 
		where drive_id=driveId
		) as subselect where row_number=1;

	return routeIndex;
	
END;

$$;


