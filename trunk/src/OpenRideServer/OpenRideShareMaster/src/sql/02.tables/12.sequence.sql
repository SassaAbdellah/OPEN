--
-- Conditional DROP
-- 
DROP TABLE if exists sequence 

--
-- Name: sequence; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO openride;


--
-- Name: sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);

