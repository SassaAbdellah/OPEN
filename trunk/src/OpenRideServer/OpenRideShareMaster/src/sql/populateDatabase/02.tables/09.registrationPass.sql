
--
-- Name: registration_pass; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE registration_pass (
    id integer NOT NULL,
    passcode character varying(32) NOT NULL,
    creation_date date DEFAULT ('now'::text)::date NOT NULL,
    usage_date date,
    cust_id integer
);


ALTER TABLE public.registration_pass OWNER TO openride;

--
-- Name: registration_pass_id_seq; Type: SEQUENCE; Schema: public; Owner: openride
--

CREATE SEQUENCE registration_pass_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.registration_pass_id_seq OWNER TO openride;

--
-- Name: registration_pass_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: openride
--

ALTER SEQUENCE registration_pass_id_seq OWNED BY registration_pass.id;




--
-- Name: id; Type: DEFAULT; Schema: public; Owner: openride
--

ALTER TABLE ONLY registration_pass ALTER COLUMN id SET DEFAULT nextval('registration_pass_id_seq'::regclass);





--
-- Name: registration_pass_id_seq; Type: SEQUENCE SET; Schema: public; Owner: openride
--

SELECT pg_catalog.setval('registration_pass_id_seq', 4, true);




--
-- Name: RegsitrationPass_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY registration_pass
    ADD CONSTRAINT "RegsitrationPass_pkey" PRIMARY KEY (id);
    
    




--
-- Name: registration_pass_passcode_key; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY registration_pass
    ADD CONSTRAINT registration_pass_passcode_key UNIQUE (passcode);
    

