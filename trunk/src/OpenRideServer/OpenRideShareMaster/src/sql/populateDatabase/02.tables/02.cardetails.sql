-- --------------------
-- Conditional DROP  --
-- --------------------
DROP TABLE if exists cardetails;



--
-- Name: cardetails; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE cardetails (
    cardet_id integer NOT NULL,
    cust_id integer NOT NULL,
    cardet_colour character varying,
    cardet_brand character varying,
    cardet_buildyear smallint,
    cardet_plateno character varying
);


ALTER TABLE public.cardetails OWNER TO openride;



--
-- Name: cardetails_cardet_id_seq; Type: SEQUENCE; Schema: public; Owner: openride
--

CREATE SEQUENCE cardetails_cardet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cardetails_cardet_id_seq OWNER TO openride;

--
-- Name: cardetails_cardet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: openride
--

ALTER SEQUENCE cardetails_cardet_id_seq OWNED BY cardetails.cardet_id;


--
-- Name: cardet_id; Type: DEFAULT; Schema: public; Owner: openride
--

ALTER TABLE ONLY cardetails ALTER COLUMN cardet_id SET DEFAULT nextval('cardetails_cardet_id_seq'::regclass);

--
-- Name: cardetails_cardet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: openride
--

SELECT pg_catalog.setval('cardetails_cardet_id_seq', 1, false);




--
-- Name: cardetails_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY cardetails
    ADD CONSTRAINT cardetails_pkey PRIMARY KEY (cardet_id);