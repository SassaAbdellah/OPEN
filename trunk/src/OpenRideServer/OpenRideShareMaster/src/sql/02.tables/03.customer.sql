-- -------------------
-- Conditional DROP --
-- -------------------

DROP TABLE if exists  customer;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE customer (
    cust_id integer NOT NULL,
    cust_addr_zipcode character varying(255),
    cust_addr_city character varying(255),
    cust_nickname character varying(255),
    cust_driverpref_age integer,
    cust_firstname character varying(255),
    cust_driverpref_gender character(1),
    cust_dateofbirth date,
    cust_driverpref_musictaste character varying(255),
    cust_mobilephoneno character varying(255),
    cust_riderpref_age integer,
    cust_email character varying(255),
    cust_riderpref_gender character(1),
    cust_issmoker boolean,
    cust_riderpref_musictaste character varying(255),
    cust_postident boolean,
    cust_bank_account integer,
    cust_bank_code integer,
    cust_lastname character varying(255),
    cust_presencemssg character varying(255),
    cust_fixedphoneno character varying(255),
    cust_registrdate time without time zone,
    cust_licensedate date,
    cust_account_balance double precision,
    cust_passwd character varying(255),
    cust_profilepic character varying(255),
    cust_gender character(1),
    cust_addr_street character varying(255),
    cust_driverpref_smoker character(1),
    cust_riderpref_smoker character(1),
    cust_session_id integer,
    is_logged_in boolean,
    cust_group character varying(255),
    last_customer_check  timestamp without time zone,
    last_matching_change timestamp without time zone,
    preferred_language character varying(10),
    show_email 		boolean,
    show_mobile 	boolean,
    matchlimitmax 		integer,
    matchlimitindividual 	integer,
    preferredunitoflength 	integer,
    requestLimit 		integer,
    offerLimit  		integer
	
);


ALTER TABLE public.customer OWNER TO openride;




--
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (cust_id);
    
    
--
-- Name: nickname_unique; Type: CONSTRAINT; Schema: public; Owner: openride; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT nickname_unique UNIQUE (cust_nickname);    
    
