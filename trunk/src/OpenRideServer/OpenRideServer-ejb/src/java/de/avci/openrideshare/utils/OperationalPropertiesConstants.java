package de.avci.openrideshare.utils;

import org.postgis.PGbox2d;


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
	 */
	public static final String PROPERTY_NAME_maxRequestsLimit = "maxRequestsLimit";
	
	/** Property Name for maxRequestLimit, the maximum number of requests that 
	 *  a customer can issue.
	 */
	public static final String PROPERTY_NAME_maxOffersLimit = "maxOffersLimit";
	
	/** Property Name for ="equiDistanceMinmum, the minimum distance of 
	 *  usable routepoints.
	 */
	public static final String PROPERTY_NAME_equiDistanceMinmum="equiDistanceMinmum";
	
	/** Property Name for ="planningHorizonForOffers", the default horizon for Offers. 
	 */
	public static final String PROPERTY_NAME_planningHorizonForOffers="planningHorizonForOffers";
	
	/** Property Name for ="planningHorizonForOffers", the default horizon for Requests. 
	 */
	public static final String PROPERTY_NAME_planningHorizonForRequests="planningHorizonForRequests";
	
	/** Property Name for "northernBound", latitude providing the northern bound of area */
	public static final String PROPERTY_NAME_northernBound="northernBound";
	
	/** Property Name for "easternBound", longitude providing the western bound of area */
	public static final String PROPERTY_NAME_easternBound="easternBound"; 
	
	/** Property Name for "southernBound", latitude providing the southern bound of area */
	public static final String PROPERTY_NAME_southernBound="southernBound";
	
	/** Property Name for "westernBound", longitude providing the western bound of area */
	public static final String PROPERTY_NAME_westernBound="westernBound";
	
	/** Property giving the default for the number of minutes for which a passenger is willing to wait for a ride. */
	public static final String PROPTERY_NAME_defaultWaitMinutes = "default_wait_minutes";
}
