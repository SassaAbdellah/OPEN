
DROP TABLE message;


CREATE TABLE message( 
--
--@Column(name = "message_id")
--
message_id     integer,
--
--@JoinColumn(name = "sender_id", referencedColumnName = "cust_id", insertable = true, updatable = true)
--
sender_id      integer references customer(cust_id) ON DELETE SET NULL,
--
--@JoinColumn(name = "recipient_id", referencedColumnName = "cust_id", insertable = true, updatable = true)
--
recipient_id   integer references customer(cust_id) ON DELETE SET NULL,
--
--@Column(name = "created")
--
created        timestamp,
--
--@Column(name = "received")
--
received       timestamp, 
--
--@Column(name = "message")
--
message character varying(255),
--@Column(name = "deliverytype")
deliverytype integer);
