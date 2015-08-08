

-- -----------------------------------------------------------------------
--  Drop before recreate
-- -----------------------------------------------------------------------

DROP FUNCTION IF EXISTS orsPolDriveMinimalDistance(integer, geography); 

--
-- Name: orsPolDriveMinimalDistance(integer, geography); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION orsPolDriveMinimalDistance(driveid integer, coo geography) RETURNS double precision
    LANGUAGE plpgsql
    AS $$

-- -----------------------------------------------------------------------------------------------
-- Auxiliary function used within OpenRideShares SearchForRider and SearchForDriver algorithms
--
--
-- given:
--         param drive_id,  an integer referencing a drive_id in driverundertakesride
--         param coo,     a Postgis Geography object
--         computes the distance between the drive's route (represented by drive_route_point elements) 
--         and the geography (point) given by coo.
-- 
--         
--
-- ------------------------------------------------------------------------------------------------


DECLARE

	minimum double precision;
	
BEGIN
	SELECT into minimum min(ST_distance(ST_MakePoint(drp.coordinate[0],drp.coordinate[1])::geography,coo)) from drive_route_point drp where drp.drive_id=driveid ;
	return minimum;
END;

$$;
