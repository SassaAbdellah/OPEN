
--
-- Conditional DROP
--
DROP TABLE if exists drive_route_point


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
-- Name: drive_route_point_pk; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY drive_route_point
    ADD CONSTRAINT drive_route_point_pk PRIMARY KEY (drive_id, route_idx);
    
    


