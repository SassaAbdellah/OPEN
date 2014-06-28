/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.openrideshare.messages;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.ControllerBean;
import de.fhg.fokus.openride.matching.MatchEntity;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Controller to finally implement a sending messages currently this is a dummy
 * which only writes the messages to the log.
 * 
 * 
 * @author jochen
 */
@Stateless
public class MessageControllerBean extends ControllerBean implements
		MessageControllerLocal {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Message to be sent from sender to recipient if a ride request is to be
	 * cancelled
	 * 
	 * Currently, this is just a dummy.
	 * 
	 * 
	 * @param sender
	 * @param recipient
	 * @param subject
	 * @param message
	 * @param riderroute_id  The RideRequest referenced in match
	 * @param ride_id        The RideOffer referenced in match 
	 * @return
	 */
	
	private boolean createMessage(
			CustomerEntity sender, 
			CustomerEntity recipient,
			RiderUndertakesRideEntity riderroute_id,
			DriverUndertakesRideEntity ride_id,
			String subject, 
			String message) {

		Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

		String logstr = "Message:\n" + "\n" + "Recipient: " + recipient + "\n"
				+ "\n" + "Subject: " + subject + "\n" + "\n" + "Message: "
				+ message;

		Message msg = new Message();
		msg.setSender(sender);
		msg.setRecipient(recipient);
		msg.setRequest(riderroute_id);
		msg.setOffer(ride_id);
		msg.setSubject(subject);
		msg.setMessage(message);
		msg.setTimeStampCreated(new Date());

		try {
			
			em.persist(msg);
			em.flush();
			
			logger.fine("Successfully created message : " + logstr);
			return true;
			
		} catch (Exception exc) {
			
			logger.log(Level.SEVERE,"Error creating message : " + logstr,exc);
			return true;
			
		}
	}

	

	@Override
	/** Call sendMessage with "null" sender
	 * 
	 */
	public boolean createSystemMessage(CustomerEntity recipient, String subject,
			String message) {

		return this.createMessage(null, recipient, null, null, subject, message);
	}

	@Override
	public List<Message> findAllMessages() {

		Query q = em.createNamedQuery("Message.findAll");
		List<Message> matches = q.getResultList();
		return matches;

	}

	
	
	
	@Override
	public List<Message> findUnreadMessages(CustomerEntity ce) {
		
		
		Query q = em.createNamedQuery("Message.findUnread");
		q.setParameter("ce", ce);
		List<Message> matches = q.getResultList();
		return matches;	
	}



	@Override
	public long getNumberOfUnreadMessages(CustomerEntity ce) {
		Query q = em.createNamedQuery("Message.numberOfUnread");
		q.setParameter("ce", ce);
		Long res = (Long) q.getSingleResult();
		return res;
	}
	
	
	@Override
	public boolean hasUnreadMessages(CustomerEntity ce) {
		return (0< this.getNumberOfUnreadMessages(ce));
	}



	@Override
	public boolean createRiderMessageFromMatch(MatchEntity match,
			String subject, String message) {
		
		return this.createMessage(
				match.getRiderUndertakesRideEntity().getCustId(),  // sender==rider
				match.getDriverUndertakesRideEntity().getCustId(), // recipient=driver
				match.getRiderUndertakesRideEntity(),
				match.getDriverUndertakesRideEntity(),
				subject, 
				message
				);
	}



	@Override
	public boolean createDriverMessageFromMatch(MatchEntity match,
			String subject, String message) {
	
	
		return this.createMessage(
				match.getDriverUndertakesRideEntity().getCustId(),  // sender==driver
				match.getRiderUndertakesRideEntity().getCustId(), // recipient==rider
				match.getRiderUndertakesRideEntity(),
				match.getDriverUndertakesRideEntity(),
				subject, 
				message);
	}
	
	

}
