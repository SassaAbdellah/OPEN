package de.fhg.fokus.openride.matching;

import java.sql.SQLException;
import java.util.LinkedList;

interface IDriverSearchAlgorithm {

	/* spatial reference systems for postgis geometries */
	public static final int SRID_CARTHESIAN = IRiderSearchAlgorithm.SRID_CARTHESIAN;
	public static final int SRID_POLAR = IRiderSearchAlgorithm.SRID_POLAR;
	public static final double DEFAULT_D = 2000.d;

	/**
	 * Comppute all matches for a given ride based on geographical position and time.
	 * @param riderrouteId identifier of riders' offer.
	 * @param startPt lon/lat coordinate of riders' start point.
	 * @param endPt lon/lat coordinate of riders' end point.
	 * @param startTimeEarliest earliest possible time to  pick up the rider.
	 * @param startTimeLatest latest possible time to pick up the rider.
	 * @param d radius of the two circles around riders' start and end point in meters.
	 * @return List of matches with regard to geographical position and time constraints.
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public abstract LinkedList<PotentialMatch> findDriver(int riderrouteId) throws SQLException,
			IllegalArgumentException;

}