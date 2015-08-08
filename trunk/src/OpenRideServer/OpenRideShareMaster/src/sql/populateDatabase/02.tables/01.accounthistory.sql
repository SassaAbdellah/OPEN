
-- ---------------------
-- Conditional DROP   --
-- ---------------------

DROP TABLE if exists  accounthistory  ;

--
-- Name: accounthistory; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE accounthistory (
    account_amount double precision,
    account_action character varying(255),
    cust_id integer NOT NULL,
    account_timestamp timestamp without time zone NOT NULL
);




--
-- Name: accounthistory_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY accounthistory
    ADD CONSTRAINT accounthistory_pkey PRIMARY KEY (cust_id, account_timestamp);
