
--
-- Name: _st_asgml(integer, geometry, integer, integer, text); Type: FUNCTION; Schema: public; Owner: openride
--

CREATE FUNCTION _st_asgml(integer, geometry, integer, integer, text) RETURNS text
    LANGUAGE c IMMUTABLE
    AS '$libdir/postgis-2.1', 'LWGEOM_asGML';


ALTER FUNCTION public._st_asgml(integer, geometry, integer, integer, text) OWNER TO openride;

