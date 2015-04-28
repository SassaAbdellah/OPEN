--
-- Conditional Drop
--

DROP TABLE if exists route_point

--
-- Name: route_point; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE route_point (
    ride_id integer NOT NULL,
    route_idx integer NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    riderroute_id integer,
    is_required boolean NOT NULL
);


ALTER TABLE public.route_point OWNER TO openride;




--
-- Name: route_point_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY route_point
    ADD CONSTRAINT route_point_pkey PRIMARY KEY (ride_id, route_idx);
