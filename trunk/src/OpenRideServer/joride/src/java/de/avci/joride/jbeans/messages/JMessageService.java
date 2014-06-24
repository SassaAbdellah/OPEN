package de.avci.joride.jbeans.messages;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.jbeans.matching.JMatchingEntity;
import de.avci.openrideshare.messages.MessageControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;


/** Service for doing stuff with internal messaging
 * 
 * @author jochen
 *
 */
public class JMessageService {
	
	
	  /** JNDI Address where the Message controller lives
		 */
		private static final String MessageControllerAdress="java:global/OpenRideServer/OpenRideServer-ejb/MessageControllerBean!de.avci.openrideshare.messages.MessageControllerLocal";
	
		
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
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
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

	    
	    /** Primitive Method for creating a message
	     * 
	     * TODO: set more parameters...
	     * 
	     * @param arg
	     */
	   private void createMessage(JMessage arg){
		   
		   this.lookupMessageBeanLocal().createMessage(
				   arg.getSender(),
				   arg.getRecipient(), 
				   arg.getSubject(), 
				   arg.getMessage()
				   );
	   }
	   
	   
	   
	   /** Create a "Driver Message" send from Driver to Rider
	    *  of a given agreement.
	    * 
	    * @param match
	    */
	   public void createDriverMessageFromMatch(JMatchingEntity match){
		   
		   // ensure that caller is driver 
		   CustomerEntity caller=this.getCustomerEntity();
		   
		   if(! (caller.getCustId()==match.getDrive().getCustId().getCustId())){
			   throw new Error("Caller "+caller.getCustNickname()+" is not driver when sending driver message");
		   }
		   
		   JMessage m=new JMessage();
		   m.setSender(match.getDrive().getCustId());
		   m.setRecipient(match.getRide().getCustId());
		   m.setSubject("DUMMY Subject for Driver Message");
		   m.setMessage(match.getDriverMessage());
		   
		   this.lookupMessageBeanLocal().createMessage(
				   m.getSender(),
				   m.getRecipient(), 
				   m.getSubject(), 
				   m.getMessage());
		   
	   }
	   
	   
	   /** Create a "Rider Message" send from Rider to Driver
	    *  of a given agreement.
	    */ 
	   
	   public void createRiderMessageFromMatch(JMatchingEntity match){
		
		   // ensure that caller is rider
		   CustomerEntity caller=this.getCustomerEntity();
		   
		   if(! (caller.getCustId()==match.getRide().getCustId().getCustId())){
			   throw new Error("Caller "+caller.getCustNickname()+" is not rider when sending rider message");
		   }
		   JMessage m=new JMessage();
		   m.setSender(match.getRide().getCustId());
		   m.setRecipient(match.getDrive().getCustId());
		   m.setSubject("DUMMY Subject for Rider Message");
		   m.setMessage(match.getRiderMessage());
		   
		   this.lookupMessageBeanLocal().createMessage(
				   m.getSender(),
				   m.getRecipient(), 
				   m.getSubject(), 
				   m.getMessage());
	   }
	   

}
