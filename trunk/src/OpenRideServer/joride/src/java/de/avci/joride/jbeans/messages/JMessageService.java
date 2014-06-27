package de.avci.joride.jbeans.messages;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.jbeans.matching.JMatchingEntity;
import de.avci.openrideshare.messages.Message;
import de.avci.openrideshare.messages.MessageControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;

/**
 * Service for doing stuff with internal messaging
 * 
 * @author jochen
 * 
 */
public class JMessageService {

	/**
	 * JNDI Address where the Message controller lives
	 */
	private static final String MessageControllerAdress = "java:global/OpenRideServer/OpenRideServer-ejb/MessageControllerBean!de.avci.openrideshare.messages.MessageControllerLocal";

	/**
	 * Lookup MessageBeanLocal that controls my requests.
	 * 
	 * @return
	 */
	protected MessageControllerLocal lookupMessageBeanLocal() {
		try {
			javax.naming.Context c = new InitialContext();
			return (MessageControllerLocal) c.lookup(MessageControllerAdress);
		} catch (NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"exception caught", ne);
			throw new RuntimeException(ne);
		}
	}

	/**
	 * Get a customerEntity from the current request
	 * 
	 * @return
	 */
	public CustomerEntity getCustomerEntity() {
		return (new JCustomerEntityService()).getCustomerEntitySafely();
	}



	/**
	 * Create a "Driver Message" send from Driver to Rider of a given agreement.
	 * 
	 * @param match
	 */
	public void createDriverMessageFromMatch(JMatchingEntity match) {

		// ensure that caller is driver
		CustomerEntity caller = this.getCustomerEntity();

		if (!(caller.getCustId() == match.getDrive().getCustId().getCustId())) {
			throw new Error("Caller " + caller.getCustNickname()
					+ " is not driver when sending driver message");
		}

		this.lookupMessageBeanLocal().createDriverMessageFromMatch(match.getMatchEntity(), "DUMMY Subject for Driver Message", match.getDriverMessage());

	}

	/**
	 * Create a "Rider Message" send from Rider to Driver of a given agreement.
	 */

	public void createRiderMessageFromMatch(JMatchingEntity match) {

		// ensure that caller is rider
		CustomerEntity caller = this.getCustomerEntity();

		if (!(caller.getCustId() == match.getRide().getCustId().getCustId())) {
			throw new Error("Caller " + caller.getCustNickname()
					+ " is not rider when sending rider message");
		}
		

		this.lookupMessageBeanLocal().createRiderMessageFromMatch(
				match.getMatchEntity(),
				"DUMMY Subject for Rider Message", 
				match.getRiderMessage());
	}

	/**
	 * List of unread messages for calling customer
	 * 
	 * @return
	 */
	public List<JMessage> getUnreadMessages() {

		CustomerEntity ce = this.getCustomerEntity();

		List<Message> messagesL = lookupMessageBeanLocal().findUnreadMessages(
				ce);

		List<JMessage> res = new LinkedList<JMessage>();

		for (Message m : messagesL) {
			res.add(new JMessage(m));
		}

		return res;
	}

	/**
	 * Number of unread messages for caller
	 * 
	 * @return
	 */
	public long getNumberOfUnreadMessages() {
		CustomerEntity ce=this.getCustomerEntity();
		return lookupMessageBeanLocal().getNumberOfUnreadMessages(ce);
	}

	/** true if there are unread messages, else false
	 * 
	 * @return
	 */
	public boolean hasUnreadMessages() {	
		CustomerEntity ce=this.getCustomerEntity();
		return lookupMessageBeanLocal().hasUnreadMessages(ce);
	}

}
