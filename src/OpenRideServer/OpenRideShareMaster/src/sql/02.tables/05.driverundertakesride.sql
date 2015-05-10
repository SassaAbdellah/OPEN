
--
-- Conditional DROP
--
DROP TABLE if exists driverundertakesride;



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
-- Name: driverundertakesride_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY driverundertakesride
    ADD CONSTRAINT driverundertakesride_pkey PRIMARY KEY (ride_id);
