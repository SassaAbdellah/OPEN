package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.SQLException;



/** Implementation of the CombiSearchAlgorithmORS providing 
 *  access to the polar-coordinate version of the
 *  ORS preselection subsystem. 
 *  
 *  I.e: 
 *  
 *  calls orsPolSFD(ride_id) respectively orsPolSFR(drive_id) 
 * 
 * 
 * 
 * @author jochen
 *
 */
public class CombiSearchAlgorithmORSPolar extends CombiSearchAlgorithmORS {

	
	
	/** String used to create prepared statement for SearchForDriver Algorithm.
	 *  This simply calls the orsPolSFD stored procedure.
	 */
	private static final String preparedStatementSFDString="select * from orsPolSFD( ? )";
	
	@Override
	protected String getPreparedStatementSFDString() {
		return preparedStatementSFDString;
	}	
	

	/** String used to create prepared statement for SearchForRider Algorithm.
	 *  This simply calls the orsPolSFR stored procedure.
	 */
	private static final String preparedStatementSFRString="select * from orsPolSFR( ? )";
	

	@Override
	protected String getPreparedStatementSFRString() {
		return preparedStatementSFRString;
	} 
	
	
	
	public CombiSearchAlgorithmORSPolar(Connection con) throws SQLException  {
		super(con);
	}



	
}
