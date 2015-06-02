package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.SQLException;

import de.avci.openrideshare.utils.PropertiesLoader;



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

	
	
	/** Property Name of operational Property containing the SFD SP's name.
	 */
	
	private static String PROP_NAME_SFD="sfdPreselectionFunctionPolar";
			
	
	/** Name of SP to be used for SFD as default.		
	 */
	private static String SFD_DEFAULT="orspolsfd";
	
	
	/** Property Name of operational Property containing the SFR SP's name.
	 */
	
	private static String PROP_NAME_SFR="sfrPreselectionFunctionPolar";
			
	
	/** Name of SP to be used for SFR as default.		
	 */
	private static String SFR_DEFAULT="orspolsfr";
	
	
	/** String containing name of the sfdFunction, to be used in Prepared Statement
	 */
	private static String sfd=null;
	
	/** String containing name of the sfrFunction, to be used in Prepared Statement
	 */
	private static String sfr=null;
	
	
	/** Initialize SFD and SFR 
	 */
	static{
		
		sfd=PropertiesLoader.getOperationalProperties().getProperty(PROP_NAME_SFD);
		if(sfd==null){sfd=SFD_DEFAULT;}
		if(sfd.startsWith("$")){sfd=SFD_DEFAULT;}// may happen if property is not  properly initialized
		
		sfr=PropertiesLoader.getOperationalProperties().getProperty(PROP_NAME_SFR);
		if(sfr==null){sfr=SFR_DEFAULT;}
		if(sfr.startsWith("$")){sfr=SFR_DEFAULT;} // may happen if property is not  properly initialized
	}
	
	
	
	
	/** String used to create prepared statement for SearchForDriver Algorithm.
	 *  This simply calls the orsPolSFD stored procedure.
	 */
	private static final String preparedStatementSFDString="select * from "+sfd+"( ? )";
	
	@Override
	protected String getPreparedStatementSFDString() {
		return preparedStatementSFDString;
	}	
	

	/** String used to create prepared statement for SearchForRider Algorithm.
	 *  This simply calls the orsPolSFR stored procedure.
	 */
	private static final String preparedStatementSFRString="select * from "+sfr+"( ? )";
	
	@Override
	protected String getPreparedStatementSFRString() {
		return preparedStatementSFRString;
	} 
		
	
	public CombiSearchAlgorithmORSPolar(Connection con) throws SQLException  {
		super(con);
	}

	
}
