package de.fhg.fokus.openride.matching.logging;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;



/** Class encapsulating log functions for matching algorithm.
 *  debug the matching algorithm in the field.
 * 
 * @author jochen
 *
 */
public class MatchingLogger {
	
	
	/** Name of the logger. Grep this to find it in the logs.
	 */
	public  static final String matchingLogStr="MATCHING_LOG" ;
	
	
	/** Logger for all the matching related things
	 */
	private static Logger matchingLogger;
		
	
	/** initialization 
	 */
	static {
		matchingLogger=Logger.getLogger(matchingLogStr);
	
	}
	

	/** Log message using default logger and default level for debugging matching algorithm
	 * 
	 *  @param message
	 */
	public static void info(String message){
		MatchingLogger.matchingLogger.log(Level.INFO,message);
	};
	
	
	/** Make matchingLogger log at Warning Level.
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		
		MatchingLogger.matchingLogger.log(Level.WARNING,message);
	}
	
	
	
	
	
	
	
	/** Boolean returning true if the logger configuration 
	 *  is configured for the matching logger to log,
	 *  or false else.
	 *  
	 *  This is here to avoid doing costly String operations,
	 *  notably constructing large logging Strings.
	 * 
	 * @return
	 */
	public static Boolean canLogInfo(){
		
		if(matchingLogger.isLoggable(Level.INFO)){
			return true;
		};
		
		return false;
	}
	
}
