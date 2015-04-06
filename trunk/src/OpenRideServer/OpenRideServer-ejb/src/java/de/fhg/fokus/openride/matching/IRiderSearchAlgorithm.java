package de.fhg.fokus.openride.matching;

import java.sql.SQLException;
import java.util.LinkedList;

import de.fhg.fokus.openride.routing.RoutePoint;

interface IRiderSearchAlgorithm {

	/* spatial reference systems for postgis geometries */
	public static final int SRID_CARTHESIAN = 3068; // projection to the plane, most accurate within germany
	public static final int SRID_POLAR = 4326; // the wgs84 reference system (lon, lat)
	/* default circle radius */
	public static final double DEFAULT_D = 2000.d;

	/**
	 * Open Ride Route-Matching by geometric circle overlay and time.
	 * @param rideId only used for constructing the PotentialMatch class.
	 * @param decomposedRoute get this from RouterBean.
	 * @param d circle radius.
	 * @return list of potential matches, empty list if no match found.
	 * @throws IllegalArgumentException if supplied parameters are wrong.
	 * @throws SQLException on jdbc related problems.
	 */
	public abstract LinkedList<PotentialMatch> findRiders(int rideId,
			RoutePoint[] decomposedRoute, double d)
			throws IllegalArgumentException, SQLException;

}