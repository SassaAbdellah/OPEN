package de.fhg.fokus.openride.matching;

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
	
	/** LogLevel for all matching related things
	 */
	private static Level matchingLoggerLevel=Level.INFO;
	
	
	/** initialization 
	 */
	static {
		matchingLogger=Logger.getLogger(matchingLogStr);	
	}
	

	/** Log message using default logger and level
	 * 
	 *  @param message
	 */
	public static void log(String message){
		MatchingLogger.matchingLogger.log(matchingLoggerLevel,message);
	};
	
}
