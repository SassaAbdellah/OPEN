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

