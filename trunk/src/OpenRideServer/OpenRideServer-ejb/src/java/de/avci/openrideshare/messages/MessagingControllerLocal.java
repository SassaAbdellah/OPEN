/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.openrideshare.messages;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import javax.ejb.Local;

/**
 *
 * @author jochen
 */
@Local
public interface MessagingControllerLocal {
    
    
    /** Send a message with subject and messageBody
     *  from Customer sender 
     *  to   Customer recipient
     * 
     * @param sender
     * @param recipient
     * @param subject
     * @param message
     * @return 
     */
    
    public boolean sendMessage(
            CustomerEntity sender, 
            CustomerEntity recipient ,
            String subject, 
            String message);
    
    
    
}
