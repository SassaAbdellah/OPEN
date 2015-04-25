package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.SQLException;

import de.avci.openrideshare.utils.PropertiesLoader;


/** A class to switch between different ride  Search Algorithm Implementations
 * 
 * @author jochen
 *
 */
public class SearchAlgorithmSwitch {
	
	/** Name of operational property to evaluate for switching between sfr algorithm...
	 */
	private static final String sfrPreselectionNAME="sfrPreselection";
	
	/** Property value signifying the new ORS algorithm should be called
	 */
	private static final String sfrPreselectionValueORS="ORS";
	
	
	/** Evaluate operational properties to decide wether or not  to use new
	 *  preselection algorty
	 */
	private static String getSFRAlgorithmName(){
		return PropertiesLoader.getOperationalProperties().getProperty(sfrPreselectionNAME);

	}
	
	
	
	/** Name of operational property to evaluate for switching between sfd algorithm...
	 */
	private static final String sfdPreselectionNAME="sfdPreselection";
	
	/** Property value signifying the new ORS algorithm should be called
	 */
	private static final String sfdPreselectionValueORS="ORS";
	
	
	/** Evaluate operational properties to decide wether or not  to use new
	 *  preselection algorithm
	 */
	private static String getSFDAlgorithmName(){
		return PropertiesLoader.getOperationalProperties().getProperty(sfdPreselectionNAME);	
	}
	
	
	
	
	/** Return a Drive Search prefetch algorithm to call
	 * 
	 * 
	 * @param conn
	 * @return
	 */
	public static IDriverSearchAlgorithm getDriverSearchAlgorithm(Connection conn){
		

		
		try { 
	
		if( sfdPreselectionValueORS.equals(getSFDAlgorithmName())){				
			return new CombiSearchAlgorithmORS(conn);
		}
	
		// by default, return old implementation
		return new DriverSearchAlgorithm(conn);
			
		} catch (SQLException exc) {
			throw new Error(exc);
		}
		
	}
	
	
	/** Return a Ride Search prefetch algorithm  to call 
	 * 
	 * 
	 * @param conn
	 * @return
	 */
	public static IRiderSearchAlgorithm getRiderSearchAlgorithm(Connection conn){
		
		try { 
			
		if( sfrPreselectionValueORS.equals(getSFRAlgorithmName())){				
			return new CombiSearchAlgorithmORS(conn);
		}
		
		//by default, return old implementation
		return new RiderSearchAlgorithm(conn);
		
		} catch (SQLException exc) {
			throw new Error(exc);
		}
		
	}
	
	
}
