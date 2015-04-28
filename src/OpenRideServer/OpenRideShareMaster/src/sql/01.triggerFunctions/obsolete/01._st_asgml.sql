--
-- Name: _st_asgml(integer, geography, integer, integer, text); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION _st_asgml(integer, geography, integer, integer, text) RETURNS text
    LANGUAGE c IMMUTABLE
    AS '$libdir/postgis-2.1', 'geography_as_gml';


ALTER FUNCTION public._st_asgml(integer, geography, integer, integer, text) OWNER TO openride;


