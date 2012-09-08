package de.avci.openride.tools.imagecreation.constants;

import java.util.Set;
import java.util.TreeSet;



/** A list of all OpenRide Applications for which 
 *  image buttons and tabs will have to be created.
 *   
 *  (currently, this is only OpenRideServer-RS),
 *  but there will be more...
 *   
 * 
 * @author jochen
 *
 */
public class OpenRideApplications {
	
	/**  The OpenRideServer-RS Application
	 * 
	 */
	public final static String OPEN_RIDE_SERVER_RS="OpenRideServer-RS";
	
	
	
	/** A list of All known Applications
	 */

	public static Set <String> knownApplications;
	
	
	/** Initialze the List of known Applications
	 * 
	 */
	static {
		knownApplications=new TreeSet <String>();
		knownApplications.add(OPEN_RIDE_SERVER_RS);	
	}


	

}
