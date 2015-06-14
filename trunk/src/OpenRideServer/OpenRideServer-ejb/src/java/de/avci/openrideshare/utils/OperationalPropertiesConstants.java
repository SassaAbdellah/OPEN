package de.avci.openrideshare.utils;


/** Defining mnemonic property names for operational properties
 * 
 * @author jochen
 *
 */
public class OperationalPropertiesConstants {
	
	
	
	
	/** jndi name of the mailservice 
	 */
	
	public static final String PROPERTY_NAME_mailServiceJNDI="mailServiceJNDI";
	
	 
	/** email of the person/entity that gets messages 
	 *  concerning operational issues
	 *  (mailfunction) 
	 */
	public static final String PROPERTY_NAME_webmasterEmailRecipient="webmasterEmailRecipient";
	
	 /** email of the person/entity that gets messages
	  * concerning business issues  (complaints etc...)
	  * also recipient for webfrontend
	  */
	
	public static final String PROPERTY_NAME_businessEmailRecipient="businessEmailRecipient";
	
	
	 /** noreply email that is used as sender/replyTo address
	  *  for messages that are not supposed to be replied
	  */
	public static final String PROPERTY_NAME_noreplyEmailRecipient="noreplyEmailRecipient";
	
	/**  max limit for matches to be displayed.
	 */
	public static final String PROPERTY_NAME_maxMatchLimit="maxMatchLimit";


	/** Property Name for maxRequestLimit, the maximum number of requests that 
	 *  a customer can issue.
	 * 
	 */
	public static final String PROPERTY_NAME_maxRequestsLimit = "maxRequestsLimit";
	
	
	/** Property Name for maxRequestLimit, the maximum number of requests that 
	 *  a customer can issue.
	 * 
	 */
	public static final String PROPERTY_NAME_maxOffersLimit = "maxOffersLimit";
	
	
	/** Property Name for ="equiDistanceMinmum, the minimum distance of 
	 *  usable routepoints.
	 * 
	 */
	
	public static final String PROPERTY_NAME_equiDistanceMinmum="equiDistanceMinmum";
	
	
	
}
