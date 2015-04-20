

-- -----------------------------------------------------------------
-- drop before recreate
-- -----------------------------------------------------------------
DROP FUNCTION IF EXISTS orsEmptySeatsCount(integer, integer, integer);


CREATE FUNCTION   orsEmptySeatsCount(
	rideId                integer,
	onRouteLiftPoint_idx  integer, 
	onRouteDropPoint_idx  integer) 
	RETURNS integer


    LANGUAGE plpgsql
    AS $$



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

$$;

