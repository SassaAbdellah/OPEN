package de.avci.joride.jbeans.messages;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.avci.openrideshare.messages.Message;


@Named("message")
@RequestScoped

/** Adding Faces-Bean Capabilities to OpenRideServer-ejb Message Bean
 * 
 * @author jochen
 *
 */
public class JMessage extends Message implements Serializable  {

	/** Default serial id
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	/** All unread messages
	 * 
	 * @return
	 */
	public List <JMessage> getUnreadMessages() { return new JMessageService().getUnreadMessages();}
	
	
	
	/** Number of unread messages.
	 *  Since this method will be polled, it should be backed up
	 *  by an ejb method
	 *  
	 */
	public long getNumberOfUnreadMessages(){
		return new JMessageService().getNumberOfUnreadMessages();
	}
	
	
	/** True, if there are unread messages for calling customer,
	 *  else false.
	 * 
	 * @return
	 */
    public boolean hasUnreadMessages(){
		return new JMessageService().hasUnreadMessages();
	}
	
	
	
	/** Dumb default bean style constructor
	 * 
	 */
	public JMessage(){
		super();
	}
	
	/** Create message copying data from
	 * 
	 * @param m
	 */
	public JMessage(Message m){
		this();
		this.setDeliveryType(m.getDeliveryType());
		this.setMessage(m.getMessage());
		this.setMessageId(m.getMessageId());
		this.setRecipient(m.getRecipient());
		this.setSender(m.getSender());
		this.setSubject(m.getSubject());
		this.setTimeStampCreated(m.getTimeStampCreated());
		this.setTimeStampReceived(m.getTimeStampReceived());
	}
	
	
}
