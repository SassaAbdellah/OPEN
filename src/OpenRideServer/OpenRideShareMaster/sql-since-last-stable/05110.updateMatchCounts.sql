
DROP FUNCTION if exists orsUpdateMatchCountsTMP();

-- -----------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------
-- 
-- Description:
--
--  decrement match count if matches are REJECTED, RIDER_COUNTERMANDED , DRIVER_COUNTERMANDED or NO_MORE_AVAILABLE
--  do nothing if matches are NOT_ADAPTED or ACCEPTED
--
-- 
-- -----------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------



CREATE FUNCTION orsUpdateMatchCountsTMP() returns integer 
    LANGUAGE plpgsql

AS $$

  DECLARE

	-- -----------------------------------------------------
	-- these are the states defined in  MatchEntity class
	-- ----------------------------------------------------- 	
	NOT_ADAPTED  		integer = 0;
   	REJECTED      		integer = 1;
   	ACCEPTED 		integer = 2;
        RIDER_COUNTERMANDED 	integer = 3;
    	DRIVER_COUNTERMANDED 	integer = 4;
	NO_MORE_AVAILABLE    	integer = 5;
	-- -----------------------------------------------------
	-- ----------------------------------------------------- 

	result           integer = 0;
	matchCount       integer = 0;
	driveId          integer = 0;
	riderrouteId     integer = 0;
  BEGIN

	-- note that we have to use loop here, since sql update will not work with aggregate function count
	for driveId in select ride_id from driverundertakesride 
	LOOP

		-- for each ride, count the number of matches which are in state NOT_ADAPTED or ACCEPTED
		select into matchCount count(*) from match 
		where ride_id=driveId 
		and ((rider_state in(0,2)) or (driver_state in(0,2)));

		update driverundertakesride  set match_count =  matchCount where driveId=ride_id;

	END LOOP;

	-- note that we have to use loop here, since sql update will not work with aggregate function count
	for riderrouteId in select riderroute_id from riderundertakesride 
	LOOP

		-- for each ride, count the number of matches which are in state NOT_ADAPTED or ACCEPTED
		select into matchCount count(*) from match 
		where riderroute_id=riderrouteId 
		and ((rider_state in(0,2)) or (driver_state in(0,2)));
		update riderundertakesride  set match_count =  matchCount where riderrouteId=riderroute_id;

	END LOOP;


	-- after all, this was expected to return an integer
	RETURN result;


  END;
$$;


ALTER FUNCTION public.orsUpdateMatchCountsTMP() OWNER TO openride;


--- run the temporary function, then drop it as not to litter the database 

SELECT orsUpdateMatchCountsTMP();

DROP FUNCTION if exists orsUpdateMatchCountsTMP();





