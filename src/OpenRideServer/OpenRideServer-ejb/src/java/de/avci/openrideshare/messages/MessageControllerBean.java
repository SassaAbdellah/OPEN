/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.openrideshare.messages;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.ControllerBean;
import de.fhg.fokus.openride.matching.MatchEntity;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
	 * @return
	 */
	@Override
	public boolean createMessage(CustomerEntity sender, CustomerEntity recipient,
			String subject, String message) {

		Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

		String logstr = "Message:\n" + "\n" + "Recipient: " + recipient + "\n"
				+ "\n" + "Subject: " + subject + "\n" + "\n" + "Message: "
				+ message;

		Message msg = new Message();
		msg.setSender(sender);
		msg.setRecipient(recipient);
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

		return this.createMessage(null, recipient, subject, message);
	}

	@Override
	public List<Message> findAllMessages() {

		Query q = em.createNamedQuery("Message.findAll");
		List<Message> matches = q.getResultList();
		return matches;

	}

}
