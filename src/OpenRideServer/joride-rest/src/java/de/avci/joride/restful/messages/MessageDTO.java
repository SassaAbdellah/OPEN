package de.avci.joride.restful.messages;

import java.sql.Timestamp;

import de.avci.joride.restful.dto.basic.AbstractDTO;


/** 
 * 
 * @author jochen
 *
 */
public class MessageDTO extends AbstractDTO {
	
	/** unique id
	 */
	private Integer messageId;
	
	/** Customer id of the sender. If null, then 
	 *  message was sent by system
	 * 
	 */
	private Integer senderId=null;
	
	/** Customer id of the recipient. If null, then 
	 *  message will be received by system
	 * 
	 */
	private Integer recipientId=null;
	
	/**  Timestamp when message was created
	 */
	private Timestamp timeStampCreated=null;
	

	/**  Timestamp when message was received
	 */
	private Timestamp timeStampReceived=null;
	
	/** Message/Payload
	 */
	private String message;

}
