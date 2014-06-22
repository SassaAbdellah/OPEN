/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.openrideshare.messages;

import java.util.List;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jochen
 */
@Local
public interface MessageControllerLocal {
	


    /**
     * Send a message with subject and messageBody from Customer sender to
     * Customer recipient
     *
     * @param sender
     * @param recipient
     * @param subject
     * @param message
     * @return
     */
    public boolean createMessage(
            CustomerEntity sender,
            CustomerEntity recipient,
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
}
