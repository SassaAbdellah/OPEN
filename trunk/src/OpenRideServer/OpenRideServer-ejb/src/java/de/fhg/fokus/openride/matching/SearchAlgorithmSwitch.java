package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.SQLException;


/** A class to chose from different drive Search Algorithm Implementations
 * 
 * @author jochen
 *
 */
public class SearchAlgorithmSwitch {
	
	
	
	/** Return a Drive Search prefetch algorithm as implemented 
	 *  by 
	 * 
	 * 
	 * @param conn
	 * @return
	 */
	public static IDriverSearchAlgorithm getDriverSearchAlgorithm(Connection conn){
		
		try { return new DriverSearchAlgorithm(conn);
		} catch (SQLException exc) {
			throw new Error(exc);
		}
		
	}
	
	
	/** Return a Ride Search prefetch algorithm as implemented 
	 *  by 
	 * 
	 * 
	 * @param conn
	 * @return
	 */
	public static IRiderSearchAlgorithm getRiderSearchAlgorithm(Connection conn){
		
		try { return new RiderSearchAlgorithm(conn);
		} catch (SQLException exc) {
			throw new Error(exc);
		}
		
	}
	
	
}
