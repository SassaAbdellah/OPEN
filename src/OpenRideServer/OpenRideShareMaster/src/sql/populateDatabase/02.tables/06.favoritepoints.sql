--
-- Conditional drop
--

DROP TABLE if exists favoritepoint;


--
-- Name: favoritepoint; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE favoritepoint (
    favpt_point character varying(255) NOT NULL,
    cust_id integer NOT NULL,
    favpt_frequency integer NOT NULL,
    favpt_displayname character varying(255),
    favpt_address character varying(255),
    favpt_id integer NOT NULL
);


ALTER TABLE public.favoritepoint OWNER TO openride;

--
-- Name: favoritepoints_favpt_id_seq; Type: SEQUENCE; Schema: public; Owner: openride
--

CREATE SEQUENCE favoritepoints_favpt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.favoritepoints_favpt_id_seq OWNER TO openride;

--
-- Name: favoritepoints_favpt_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: openride
--

ALTER SEQUENCE favoritepoints_favpt_id_seq OWNED BY favoritepoint.favpt_id;





--
-- Name: favpt_id; Type: DEFAULT; Schema: public; Owner: openride
--

ALTER TABLE ONLY favoritepoint ALTER COLUMN favpt_id SET DEFAULT nextval('favoritepoints_favpt_id_seq'::regclass);





--
-- Name: favoritepoints_favpt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: openride
--

SELECT pg_catalog.setval('favoritepoints_favpt_id_seq', 1, false);



--
-- Name: favoritepoints_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY favoritepoint
    ADD CONSTRAINT favoritepoints_pkey PRIMARY KEY (favpt_id);
    
    
--
-- Name: uc_favoritepoints_cust_id_displayname; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY favoritepoint
    ADD CONSTRAINT uc_favoritepoints_cust_id_displayname UNIQUE (cust_id, favpt_displayname);    
