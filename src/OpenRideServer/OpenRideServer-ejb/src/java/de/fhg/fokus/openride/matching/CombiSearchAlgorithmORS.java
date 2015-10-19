package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.postgresql.geometric.PGpoint;

import de.fhg.fokus.openride.routing.Coordinate;

public abstract class CombiSearchAlgorithmORS

/**
 * Jochen's Implementation of the prefetch stages of both
 * 
 *  * Driver Search algorithm (SFD)
 *  * Rider Search Algorithm (SFR)
 * 
 * 
 */
implements IDriverSearchAlgorithm, IRiderSearchAlgorithm {
		
			
		/** prepared statement to search for potential matches for given request.
		 *  This gets initialized at construction time
		 */
		private final PreparedStatement preparedStatementSelectSFD;
		
				
		/** prepared statement to search for potential matches.
		 *  This gets initialized at construction time
		 */
		private final PreparedStatement preparedStatementSelectSFR;
		
		
		/** DatabaseConnection to be initialized when creating this
		 */
		private final Connection con;

		
		
		/** This should usually be overwritten to call different 
		 *  stored procedures. See known subclasses!
		 * 
		 * @return String describing a prepared statement to be called
		 */
	    protected abstract String getPreparedStatementSFDString();


	    /** This should usually be overwritten to call different 
		 *  stored procedures. See known subclasses!
		 * 
		 * @return String describing a prepared statement to be called
		 */
		protected abstract String getPreparedStatementSFRString();
	
	
		
		
		


	
	
	/** The real implementation of find driver, without parameter overhead
	 * 
	 * @param riderrouteId
	 * @return
	 * @throws SQLException 
	 */
		
	@Override
	
	public LinkedList<PotentialMatch> findDriver(int riderrouteId) {
		
		try{
			preparedStatementSelectSFD.setInt(1, riderrouteId);
			ResultSet rs=preparedStatementSelectSFD.executeQuery();
			return digestResultSet(rs);
		} catch(SQLException exc){
			
			throw new Error("Error while building Statement in DriverSearchAlgorithm ", exc);
		}		
	
	}
	
	

	
	

	
	

	/** Create Class, initialize the prepared statements (!)
	 * 
	 * 
     * @param openRideDbConnection connection to openride database.
     */
    public CombiSearchAlgorithmORS(Connection con) throws SQLException {
        this.con = con;
        this.preparedStatementSelectSFD = this.con.prepareStatement(getPreparedStatementSFDString());
        this.preparedStatementSelectSFR = this.con.prepareStatement(getPreparedStatementSFRString());
    }

    




	
	/** The real implementation of find driver, without parameter overhead
	 * 
	 * @param riderrouteId
	 * @return
	 * @throws SQLException 
	 */
    
    @Override
    
	public LinkedList<PotentialMatch> findRiders(int rideId) {
		
		try{
			preparedStatementSelectSFR.setInt(1, rideId);
			ResultSet rs=preparedStatementSelectSFR.executeQuery();
			return digestResultSet(rs);
		} catch(SQLException exc){
			
			throw new Error("Error while building Statement in DriverSearchAlgorithm ", exc);
		}		
	}
	
	
	
	
	
	
	/** digest result set as returned by the orsSFD / orsSFR stored procedure into
	 *  a list of PotentialMatches.
	 *  
	 *  Note, that this is possible because return types of both SFD and SFR 
	 *  are compatible by design
	 *  
	 */
	
private	LinkedList <PotentialMatch> digestResultSet (ResultSet rs){
		
		
		LinkedList <PotentialMatch> res=new LinkedList <PotentialMatch>();
		
		try {
			
			while(rs.next()){
				
				PotentialMatch pm=new PotentialMatch();
				
				pm.setRideId(rs.getInt("drive_id"));
				pm.setRidersRouteId(rs.getInt("riderroute_id"));
				pm.setLiftIndex(rs.getInt("onrouteliftpointidx"));
				// liftpoint must be casted from PGPoint
				PGpoint liftpg=(PGpoint)rs.getObject("onrouteliftpoint");
				Coordinate liftCoo=new Coordinate(liftpg.x,liftpg.y);				
				pm.setLiftPoint(liftCoo);
				//
				pm.setTimeAtOnRouteLiftPoint(rs.getTimestamp("timeatonrouteliftpoint"));
				pm.setDropIndex(rs.getInt("onroutedroppointidx"));
				// liftpoint must be casted from PGPoint
				PGpoint droppg=(PGpoint)rs.getObject("onroutedroppoint");
				Coordinate dropCoo=new Coordinate(liftpg.x,liftpg.y);				
				pm.setDropPoint(dropCoo);
				//
				pm.setSharedDistanceMeters(rs.getDouble("shareddistance"));
					
				res.add(pm);
			}
			
			
		} catch (SQLException exc) {
			
			throw new Error("DriverSearchAlgorithm: Digesting the Resultlist failed ", exc);
		}
		
		
		return res;
	}
		
	
	
	
	

}
