
--
-- Conditional DROP
--
DROP TABLE if exists riderundertakesride;


--
-- Name: riderundertakesride; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE riderundertakesride (
    riderroute_id integer NOT NULL,
    ride_id integer,
    starttime_latest timestamp without time zone,
    timestamprealized timestamp without time zone,
    price double precision,
    cust_id integer,
    no_passengers integer,
    timestampbooked timestamp without time zone,
    account_timestamp timestamp without time zone,
    starttime_earliest timestamp without time zone,
    startpt point,
    endpt point,
    startpt_addr character varying(255),
    endpt_addr character varying(255),
    givenrating smallint,
    givenrating_comment character varying(80),
    givenrating_date timestamp without time zone,
    receivedrating smallint,
    receivedrating_comment character varying(80),
    receivedrating_date timestamp without time zone,
    startpt_c geometry,
    endpt_c geometry,
    comment character varying(255),
    last_matching_state integer,
    match_count int DEFAULT 0,
    is_countermanded boolean,
    CONSTRAINT enforce_dims_endpt_c CHECK ((st_ndims(endpt_c) = 2)),
    CONSTRAINT enforce_dims_startpt_c CHECK ((st_ndims(startpt_c) = 2)),
    CONSTRAINT enforce_geotype_endpt_c CHECK (((geometrytype(endpt_c) = 'POINT'::text) OR (endpt_c IS NULL))),
    CONSTRAINT enforce_geotype_startpt_c CHECK (((geometrytype(startpt_c) = 'POINT'::text) OR (startpt_c IS NULL)))
);


ALTER TABLE public.riderundertakesride OWNER TO openride;


--
-- Name: riderundertakesride_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY riderundertakesride
    ADD CONSTRAINT riderundertakesride_pkey PRIMARY KEY (riderroute_id);





