package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import org.postgis.Point;
import org.postgresql.geometric.PGpoint;

import de.fhg.fokus.openride.routing.Coordinate;
import de.fhg.fokus.openride.routing.RoutePoint;

public class CombiSearchAlgorithmORS

/**
 * Jochen's Implementation of the prefetch stages of both
 * 
 *  * Driver Search algorithm (SFD)
 *  * Rider Search Algorithm (SFR)
 * 
 * 
 * 
 * 
 */
implements IDriverSearchAlgorithm, IRiderSearchAlgorithm {
		
	
		/** String used to create prepared statement for SearchForDriver Algorithm.
		 *  This simply calls the orsSFD stored procedure.
		 */
		private static final String preparedStatementSFDString="select * from orsSFD( ? )";
		
	
			
		/** prepared statement to search for potential matches for given request.
		 *  This gets initialized at construction time
		 */
		private final PreparedStatement preparedStatementSelectSFD;
		
		
		
		/** String used to create prepared statement for SearchForRider Algorithm.
		 *  This simply calls the orsSFR stored procedure.
		 */
		private static final String preparedStatementSFRString="select * from orsSFR( ? )";
		
	
			
		/** prepared statement to search for potential matches.
		 *  This gets initialized at construction time
		 */
		private final PreparedStatement preparedStatementSelectSFR;

		
		
		/** DatabaseConnection to be initialized when creating this
		 */
		private final Connection con;
	
	
	
		
		
		

	/**
	 * This should return a List of preselected "PotentialMatches" with the
	 * following properties beeing filled in:
	 * 
	 * PotentialMatch.dropIndex 
	 * PotentialMatch.dropPoint 
	 * PotentialMatch.liftIndex 
	 * PotentialMatch.liftPoint 
	 * PotentialMatch.onRouteDropPoint
	 * PotentialMatch.onRouteLiftPoint 
	 * PotentialMatch.rideId 
	 * PotentialMatch.ridersRouteId 
	 * PotentialMatch.sharedDistanceMeters
	 * PotentialMatch.timeAtLiftPoint 
	 * PotentialMatch.timeAtOnRouteLiftPoint
	 * 
	 * Not filled in at this stage are:
	 * 
	 * PotentialMatch.detourMeters 
	 * PotentialMatch.detourSeconds 
	 * 
	 * 
	 * 
	 */

	@Override
	public LinkedList<PotentialMatch> findDriver(
			int riderrouteId,
			Point startPt, 
			Point endPt, 
			Timestamp startTimeEarliest,
			Timestamp startTimeLatest, 
			double d) 
			throws SQLException,
			IllegalArgumentException {
		// 
		// TODO:  Note that this implementation happily ignores all parameters, except the rideId.
		//        Hence, the IDriverSearchAlgorithm  can be simplifyed heavily once this runs OK
		//
		//
		
		return this.findDriver(riderrouteId);
	}
	
	
	
	/** The real implementation of find driver, without parameter overhead
	 * 
	 * @param riderrouteId
	 * @return
	 * @throws SQLException 
	 */
	private LinkedList<PotentialMatch> findDriver(int riderrouteId) {
		
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
        this.preparedStatementSelectSFD = this.con.prepareStatement(preparedStatementSFDString);
        this.preparedStatementSelectSFR = this.con.prepareStatement(preparedStatementSFRString);
    }

    
    /**
	 * This should return a List of preselected "PotentialMatches" with the
	 * following properties beeing filled in:
	 * 
	 * PotentialMatch.dropIndex 
	 * PotentialMatch.dropPoint 
	 * PotentialMatch.liftIndex 
	 * PotentialMatch.liftPoint 
	 * PotentialMatch.onRouteDropPoint
	 * PotentialMatch.onRouteLiftPoint 
	 * PotentialMatch.rideId 
	 * PotentialMatch.ridersRouteId 
	 * PotentialMatch.sharedDistanceMeters
	 * PotentialMatch.timeAtLiftPoint 
	 * PotentialMatch.timeAtOnRouteLiftPoint
	 * 
	 * Not filled in at this stage are:
	 * 
	 * PotentialMatch.detourMeters 
	 * PotentialMatch.detourSeconds 
	 * 
	 * 
	 * 
	 */

	@Override
	public LinkedList<PotentialMatch> findRiders(int rideId,
			RoutePoint[] decomposedRoute, double d)
			throws IllegalArgumentException, SQLException {
		
		// 
		// TODO:  Note that this implementation happily ignores all parameters, except the rider_Id.
		//        Hence, the IDriverSearchAlgorithm  can be simplifyed heavily once this runs OK
		//
		//
		
		return this.findRiders(rideId);	
	}


	
	/** The real implementation of find driver, without parameter overhead
	 * 
	 * @param riderrouteId
	 * @return
	 * @throws SQLException 
	 */
	private LinkedList<PotentialMatch> findRiders(int rideId) {
		
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
