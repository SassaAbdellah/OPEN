package de.avci.openrideshare.exceptions;

import java.util.Locale;

import de.avci.openrideshare.utils.PropertiesLoader;


/** Common base class for Exceptions to be thrown when accessing ORS functionality
 * 
 * 
 * 
 * @author jochen
 *
 */

public abstract class AbstractOpenRideShareException extends Exception {
	

	
	
	
	/** Error Code for Unknown Error
	 */
	
	public static final Integer UnknownError=0;
	
	
	/** Different Constants describing errorcodes for the type of exception:
	 *  Cannot create Request: Maximum Number of open Requests for this user is exceeded.
	 *  
	 */
	public static final Integer RequestLimitExceeded=UnknownError+1;
	

	
	/** Different Constants describing errorcodes for the type of exception:
	 *  Cannot create Offer: Maximum Number of open offers for this user is exceeded.
	 *  
	 */
	public static final Integer OfferLimitExceeded=RequestLimitExceeded+1;
			
	
	/** For each such message, a message property should be kept (somewhere).
	 *  The message Property is there to provide Access to the short and verbose
	 *  Error messages in the MessageProperties.
	 *  
	 * 
	 * @return
	 */
	public abstract String getMessagePropertyName();
	
	/** Override Standard getMessage() method to return the short english Message
	 *  from MessageProperties
	 *  
	 * @return
	 */
	public abstract Integer getErrorCode();
	
	
	
	
	@Override
	public String getMessage(){
		
		return ""+PropertiesLoader.getMessageProperties(Locale.ENGLISH).get(getMessagePropertyName());	
	}
	
	
	/** Trivialized to return getMessage(), since there is no default locale that makes sense
	 */
	@Override
	public String getLocalizedMessage(){
		
		return getMessage();
	}
	
	/** Return error message for specialized locage
	 */
	
	public String getLocalizedMessage(Locale locale){
			
		return ""+PropertiesLoader.getMessageProperties(locale).get(getMessagePropertyName());	
	}
	
	
	
	

}

