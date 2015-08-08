--
-- Conditional drop
--
DROP TABLE if exists waypoint;

--
-- Name: waypoint; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE waypoint (
    waypoint_id integer NOT NULL,
    ride_id integer NOT NULL,
    route_idx integer NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    description text
);


ALTER TABLE public.waypoint OWNER TO openride;




--
-- Name: waypoint_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY waypoint
    ADD CONSTRAINT waypoint_pkey PRIMARY KEY (waypoint_id);
