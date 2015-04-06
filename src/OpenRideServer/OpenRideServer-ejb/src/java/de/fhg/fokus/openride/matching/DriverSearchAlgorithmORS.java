package de.fhg.fokus.openride.matching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.postgis.Point;
import org.postgresql.geometric.PGpoint;

public class DriverSearchAlgorithmORS

/**
 * Jochen's Implementation of the Driver Search (Prefetch) algorithm
 * 
 * 
 * 
 * 
 */
implements IDriverSearchAlgorithm {
	
	
	/** Class used internally for prefetching results from findMatchesNearPoint
	 * 
	 * @author jochen
	 *
	 */
	class PrefetchResultDTO{
		
				/** ride_id 
				 */
				private Integer rideId;
	
				/** route index in drive_route_points used for liftIndex and dropIndex	
				 */
				private Integer routeIdx ;
			
				// coordinates in polar coordinates
				private PGpoint polarCoordinate;
				
				//" drp.coordinate    	  as polarCoordinate  ,\n"+ 
				// carthesian coordinates in local carthesian system
				//" drp.coordinate_c  	  as carthCoordinate  ,\n"+ 
				// when will the driver pass this point
				
				/** Expected Arrival
				 */
				private Timestamp expectedArrival;
		
				/** availlable seats at that point
				 */
				private Integer seatsAvaillable;
				
				/** Distance to the source
				 */
				private Double distanceToSource;

				public Double getDistanceToSource() {
					return distanceToSource;
				}

				public void setDistanceToSource(Double distanceToSource) {
					this.distanceToSource = distanceToSource;
				}

				public Integer getSeatsAvaillable() {
					return seatsAvaillable;
				}

				public void setSeatsAvaillable(Integer seatsAvaillable) {
					this.seatsAvaillable = seatsAvaillable;
				}

				public Timestamp getExpectedArrival() {
					return expectedArrival;
				}

				public void setExpectedArrival(Timestamp expectedArrival) {
					this.expectedArrival = expectedArrival;
				}

				public Integer getRouteIdx() {
					return routeIdx;
				}

				public void setRouteIdx(Integer routeIdx) {
					this.routeIdx = routeIdx;
				}

				public Integer getRideId() {
					return rideId;
				}

				public void setRideId(Integer rideId) {
					this.rideId = rideId;
				}
				
				public PGpoint getPolarCoordinate(){
					return this.polarCoordinate;
				}
		
				public void setPolarCoordinate(PGpoint coordinate){
					this.polarCoordinate=coordinate;
				}
				
				
				
				public String toString(){
					
					StringBuffer buf=new StringBuffer();
					
					buf.append("| RideId           : ");
					buf.append(getRideId());
					buf.append("| RouteIdx         : ");
					buf.append(this.getRouteIdx());
					buf.append("| PolarCoordinate  : ");
					buf.append(this.getPolarCoordinate());
					buf.append("| expectedArrival  : ");
					buf.append(this.getExpectedArrival());
					buf.append("| seatsAvaillable  : ");
					buf.append(this.getSeatsAvaillable());
					buf.append("| distanceToSource :");
					buf.append(this.getDistanceToSource());
					
					return buf.toString();
				}
		
	}
	
	
	/** String to create a prepared Statement for prefetching the potential matches from PostGIS database.
	 *  More precisely, for a given request, this will take as argument:
	 *  
	 *  timeIntervalEarliest 	: (Timestamp)
	 *  startIntervalLatest   	: (Timestamp), alternatively a starttime latest with a useful default  added
	 *  distance               	:  maximal distance in meters (parameter of the ride overlay algorithm)
	 *  availlableSeats	 	 	:  number of availlable seats. Points with less availlable seats will be taken out	 
	 *  
	 *  
	 */
	private static final String preparedStatementStringSelectPotentialMatchesNearPoint =
			
			" SELECT                 \n"+ // what to select
			// ride_id 
			" dur.ride_id       	  as ride_id          ,\n"+ 
			// route index in drive_route_points used for liftIndex and dropIndex	
			" drp.route_idx     	  as route_idx        ,\n"+ 	
			// coordinates in polar coordinates
			" drp.coordinate    	  as polarCoordinate  ,\n"+ 
			// carthesian coordinates in local carthesian system
			" drp.coordinate_c  	  as cathCoordinate   ,\n"+ 
			// when will the driver pass this point
			" drp.expected_arrival    as expectedArrival        ,\n"+ 
			// availlable seats at that point
			" drp.seats_available     as seatsAvaillable ,\n"+ 
			// distance to source for calculating shared route
			" drp.distance_to_source  as distanceToSource         \n"+ 
			// we are about to Select from inner join of drive route points and drives
			"from drive_route_point drp,driverundertakesride dur \n"+
			"WHERE drp.drive_id=dur.ride_id                      \n"+
			// where timestamp is between starttime_earlies and starttime_latest defined by rider
			"AND drp.expected_arrival  > ? \n"+ 
			"AND drp.expected_arrival  < ? \n"+                  
			// Where and distance between point and 
			// either ride start point or ride end point is small enough 
			// (i.e either start or endpoint of argument ride is passed as lon / lat in polar coordinates)
			 "AND st_distance( drp.coordinate_c, st_transform( st_setsrid(st_makepoint( ? , ? ), 4326),3068))  < ? \n"+
			// And number of availlable seats is large enough
			"AND drp.seats_available  >= ? ";
	
	
		/** prepared statement to search for potential matches.
		 */
		private final PreparedStatement preparedStatementSelectPotentialMatchesNearPoint;

		/** DatabaseConnection to be initialized when creating this
		 */
		private final Connection con;
	
	
	
		
		/**
		 * 
		 * 
		 * 
		 * 
		 * @param riderrouteId
		 * @param startPt
		 * @param endPt
		 * @param startTimeEarliest
		 * @param startTimeLatest
		 * @param d
		 * @return
		 * @throws SQLException
		 * @throws IllegalArgumentException
		 */
		private LinkedList<PrefetchResultDTO> findMatchesNearPoint(
			
				Point point,  
				Timestamp startTimeEarliest,
				Timestamp startTimeLatest, 
				double distance,
				int seatsAvaillable) 
				throws SQLException,
				IllegalArgumentException {
			
		
			System.err.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.err.println("prefetching result");
			System.err.flush();
			
			PreparedStatement ps=preparedStatementSelectPotentialMatchesNearPoint;
			
			
			ps.setTimestamp(1, startTimeEarliest);
			ps.setTimestamp(2, startTimeLatest);
			ps.setDouble(3,point.x);
			ps.setDouble(4,point.y);
			ps.setDouble(5,distance);
			ps.setDouble(6,seatsAvaillable);
			
			
			LinkedList<PrefetchResultDTO> res = new LinkedList<PrefetchResultDTO>();
			
			
			ResultSet rs=ps.executeQuery();
			
		
			PrefetchResultDTO pfr=new PrefetchResultDTO();
		
			// TODO: set Properties of potential matches
	
			while (rs.next()){
				
		
			// rideId
			pfr.setRideId(rs.getInt("ride_id"));
			// routeIdx
			pfr.setRouteIdx(rs.getInt("route_idx"));
			//polarcoordinate           
			pfr.setPolarCoordinate( (PGpoint) rs.getObject("polarCoordinate"));
			// carthcoordinate   
			// is ommitted               
			 
			//expectedArrival      
			pfr.setExpectedArrival(rs.getTimestamp("expectedArrival"));   
			
			//distance  
			// when will the driver pass this point
		
			// availlable seats at that point
			pfr.setSeatsAvaillable(rs.getInt("seatsAvaillable"));
			// distance to source for calculating shared route
			
			pfr.setDistanceToSource(rs.getDouble("distanceToSource"));
			
			
			res.add(pfr);
			
			
			} // while rs.next()
			
			rs.close();
			
			System.err.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			
			return res;
			
		}
		
		
		/** Debug printout of a result list
		 * 
		 * @return
		 */
		
		public String debugResultList(List <PrefetchResultDTO> inputList){
			
			String res="";
			
			for (PrefetchResultDTO prdto : inputList){
				res+="\n"+prdto.toString();
			} 
			
			return res;			
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
	public LinkedList<PotentialMatch> findDriver(
			int riderrouteId,
			Point startPt, 
			Point endPt, 
			Timestamp startTimeEarliest,
			Timestamp startTimeLatest, 
			double d) 
			throws SQLException,
			IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
     * @param openRideDbConnection connection to openride database.
     */
    public DriverSearchAlgorithmORS(Connection con) throws SQLException {
        this.con = con;
        this.preparedStatementSelectPotentialMatchesNearPoint = this.con.prepareStatement(preparedStatementStringSelectPotentialMatchesNearPoint);
    }

	
	
	
	
	/** TODO: remove this if no more needed!
	 * 
	 * @param arg
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main (String[] arg) throws ClassNotFoundException, SQLException{
		
		System.out.println();
		System.out.println();
		System.out.println("=========================================================");
		System.out.println("*****  DriverSearchAlgorithmORS preparedStatement  ***** ");
		System.out.println("=========================================================");
			
		System.out.print(preparedStatementStringSelectPotentialMatchesNearPoint );
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		// TODO: remove this, or make it configurable
		
		String  host="";
		String  database="openride";
		String  user="openride";
		Integer port=5432;
		String  password="openride";
		
		
		
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://"+host+":"+port+"/"+database;
		Connection con = DriverManager.getConnection(url, user, password);
		
		
		DriverSearchAlgorithmORS dsa=new DriverSearchAlgorithmORS(con);
		
		try{
		
		List myList=
		dsa.findMatchesNearPoint(
				new Point(11.497159828158159,53.40902136357549),
				
				new Timestamp(0), 
				new Timestamp(2347483647000000l), 
				1000,
				1);
		
		System.err.println(
				dsa.debugResultList(myList)
				);
		
		} catch(Exception exc){
			
			throw new Error(exc);
		}
	
	}
	
	

}
