--
-- Conditional DROP
--

DROP TABLE if exists message

--
-- Name: message; Type: TABLE; Schema: public; Owner: openride; Tablespace: 
--

CREATE TABLE message (
    message_id integer,
    subject character varying(128),
    sender_id integer,
    recipient_id integer,
    match_request integer,
    match_offer integer,
    created timestamp without time zone,
    received timestamp without time zone,
    message character varying(255),
    deliverytype integer
);


ALTER TABLE public.message OWNER TO openride;
