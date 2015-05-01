package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.SQLException;



/** Implementation of the CombiSearchAlgorithmORS providing 
 *  access to the carthesian-coordinate version of the
 *  ORS preselection subsystem. 
 *  
 *  I.e: 
 *  
 *  calls orsSFD(ride_id) respectively orsSFR(drive_id) 
 * 
 * 
 * 
 * @author jochen
 *
 */
public class CombiSearchAlgorithmORSCarthesian extends CombiSearchAlgorithmORS {

	
	
	/** String used to create prepared statement for SearchForDriver Algorithm.
	 *  This simply calls the orsSFD stored procedure.
	 */
	private static final String preparedStatementSFDString="select * from orsSFD( ? )";
	
	@Override
	protected String getPreparedStatementSFDString() {
		return preparedStatementSFDString;
	}	
	

	/** String used to create prepared statement for SearchForRider Algorithm.
	 *  This simply calls the orsSFR stored procedure.
	 */
	private static final String preparedStatementSFRString="select * from orsSFR( ? )";
	

	@Override
	protected String getPreparedStatementSFRString() {
		
		return preparedStatementSFRString;
	} 
	
	
	
	public CombiSearchAlgorithmORSCarthesian(Connection con) throws SQLException  {
		
		
		super(con);
	}



	
}
