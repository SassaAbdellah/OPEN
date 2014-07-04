package de.avci.joride.jbeans.messages;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import de.avci.joride.constants.JoRideConstants;
import de.avci.openrideshare.messages.Message;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;


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
	
	/**
	 * A date format for formatting "created" and "received" date. 
	 * Created via lazy instantiation.
	 * 
	 * @deprecated should be done centrally in utils* class
	 * 
	 */
	protected DateFormat dateFormat;

	/**
	 * Accessor with lazy instantiation
	 * 
	 *
	 * 
	 * @return
	 */
	protected DateFormat getDateFormat() {

		if (this.dateFormat == null) {
			dateFormat = (new JoRideConstants()).createDateTimeFormat();
		}

		return dateFormat;
	}
	
	/** returns a nicely formatted version of the "created" date property
	 * 
	 * @return
	 */
	public String getCreatedFormatted(){
		return getDateFormat().format(this.getTimeStampCreated());
	}
	
	/** returns a nicely formatted version of the "received" date property
	 * 
	 * @return
	 */
	public String getReceivedFormatted(){
		return getDateFormat().format(this.getTimeStampReceived());
	}
	
	/** True, if caller is receiver, else false
	 * 
	 */
	public boolean isIncoming(){
		return new JMessageService().isIncomingMessage(this);
	}
	
	/** True, if caller is receiver, else false
	 * 
	 */
	public boolean isOutgoing(){
		return new JMessageService().isOutgoingMessage(this);
	}
	
	
	/** True, if message references offer, and caller is driver.
	 *
	 * @return True, if caller is driver else false.
	 */
	public boolean getCallerIsDriver() {
		return new JMessageService().isCallerDriver(this);
	}
	
	/** True, if message references request, and caller is rider.
	 *
	 * @return True, if caller is driver else false.
	 */
	public boolean getCallerIsRider() {
		return new JMessageService().isCallerRider(this);
	}
	

	
	
	
	/** Change status from unread to read only, no way back!
	 * 
	 * @param arg
	 */
	public void setRead(boolean arg){
		if(arg=true){
			new JMessageService().setMessageRead(this);
		}
	}

	// TODO: dummy, think of it...
	public boolean getRead(){
		return this.getTimeStampReceived()!=null;
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
		this.setOffer(m.getOffer());
		this.setRequest(m.getRequest());
	}
	
	
}
