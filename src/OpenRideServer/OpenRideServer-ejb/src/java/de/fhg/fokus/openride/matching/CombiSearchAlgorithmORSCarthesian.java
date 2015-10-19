package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.SQLException;

import de.avci.openrideshare.utils.PropertiesLoader;



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


	
	/** Property Name of operational Property containing the SFD SP's name.
	 */
	
	private static String PROP_NAME_SFD="sfdPreselectionFunctionCarthesian";
			
	
	/** Name of SP to be used for SFD as default.		
	 */
	private static String SFD_DEFAULT="orssfd";
	
	
	/** Property Name of operational Property containing the SFR SP's name.
	 */
	
	private static String PROP_NAME_SFR="sfrPreselectionFunctionCarthesian";
			
	
	/** Name of SP to be used for SFR as default.		
	 */
	private static String SFR_DEFAULT="orssfr";
	
	
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
		if(sfr.startsWith("$")){sfr=SFR_DEFAULT;}// may happen if property is not  properly initialized
		
	}
	
	
	

	
	/** String used to create prepared statement for SearchForDriver Algorithm.
	 *  This simply calls the orsSFD stored procedure.
	 */
	private static final String preparedStatementSFDString="select * from "+sfd+"( ? )";
	
	@Override
	protected String getPreparedStatementSFDString() {
		
		MatchingLogger.log("returning SFD Statement for carthesian Coordinates : "+preparedStatementSFDString);
		return preparedStatementSFDString;
	}	
	

	/** String used to create prepared statement for SearchForRider Algorithm.
	 *  This simply calls the orsSFR stored procedure.
	 */
	private static final String preparedStatementSFRString="select * from "+sfr+"( ? )";
	

	@Override
	protected String getPreparedStatementSFRString() {
		
		MatchingLogger.log("returning SFR Statement for carthesian Coordinates : "+preparedStatementSFDString);
		return preparedStatementSFRString;
	} 
	
	
	
	public CombiSearchAlgorithmORSCarthesian(Connection con) throws SQLException  {		
		super(con);
	}



	
}
