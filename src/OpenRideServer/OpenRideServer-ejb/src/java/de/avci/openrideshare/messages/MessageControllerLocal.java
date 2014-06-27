/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.openrideshare.messages;

import java.util.List;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.matching.MatchEntity;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jochen
 */
@Local
public interface MessageControllerLocal {
	


	/** Create a message from Rider to driver of a given match
	 * 
	 * @param match
	 * @param subject
	 * @param message
	 * @return
	 */
    public boolean createRiderMessageFromMatch(
            MatchEntity match,
            String subject,
            String message);
    
	/** Create a message from Driver to Rider of a given match
	 * 
	 * @param match
	 * @param subject
	 * @param message
	 * @return
	 */
    public boolean createDriverMessageFromMatch(
            MatchEntity match,
            String subject,
            String message);

    
    
    /** Send a message from the system
     * 
     * @param recipient 
     * @param subject
     * @param message
     * @return  true if message had been sucessfully sent, else false
     */
    public boolean createSystemMessage(
            CustomerEntity recipient,
            String subject,
            String message);


    /** All messages. This should only be used when debugging.
     * 
     * @return List of all messages.
     */
	public List<Message> findAllMessages();


	/** All unread messages for given customer
	 * 
	 * @param ce
	 * @return
	 */
	public List<Message> findUnreadMessages(CustomerEntity ce);


	/** Return the number of unread messages 
	 * (which, for performance reasons, should have a ejb query of it's own instead 
	 * of just counting the number of elements in a list...
	 * 
	 * 
	 * @param ce
	 * @return
	 */
	public long getNumberOfUnreadMessages(CustomerEntity ce);



	/** True, if there are unread messages for this user, else false.
	 */
	boolean hasUnreadMessages(CustomerEntity ce);
}
