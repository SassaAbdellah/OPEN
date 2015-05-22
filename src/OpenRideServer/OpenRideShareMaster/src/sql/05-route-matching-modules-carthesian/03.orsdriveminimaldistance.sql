

-- -----------------------------------------------------------------------
--  Drop before recreate
-- -----------------------------------------------------------------------

DROP FUNCTION IF EXISTS orsdriveminimaldistance(integer, geometry); 

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


