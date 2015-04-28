
--
-- Conditional DROP
--
DROP TABLE if exists  match 

--
-- Name: match; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE match (
    riderroute_id integer NOT NULL,
    ride_id integer NOT NULL,
    match_shared_distance_meters double precision,
    match_detour_meters double precision,
    match_expected_start_time timestamp without time zone,
    match_drive_remaining_distance_meters double precision,
    match_price_cents integer,
    driver_change timestamp without time zone,
    rider_change timestamp without time zone,
    driver_access timestamp without time zone,
    rider_access timestamp without time zone,
    driver_state integer,
    rider_state integer,
    rider_message character varying(255),
    driver_message character varying(255)
);


ALTER TABLE public.match OWNER TO openride;


--
-- Name: match_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY match
    ADD CONSTRAINT match_pkey PRIMARY KEY (riderroute_id, ride_id);
