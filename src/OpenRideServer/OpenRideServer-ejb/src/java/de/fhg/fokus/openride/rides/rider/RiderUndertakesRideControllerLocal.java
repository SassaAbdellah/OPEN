/*
    OpenRide -- Car Sharing 2.0
    Copyright (C) 2010  Fraunhofer Institute for Open Communication Systems (FOKUS)

    Fraunhofer FOKUS
    Kaiserin-Augusta-Allee 31
    10589 Berlin
    Tel: +49 30 3463-7000
    info@fokus.fraunhofer.de

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License Version 3 as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.fhg.fokus.openride.rides.rider;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.matching.MatchEntity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Local;
import org.postgis.Point;

/**
 *
 * @author pab
 */
@Local
public interface RiderUndertakesRideControllerLocal {

    Point getStartPoint(int rideId);

    Point getEndPoint(int rideId);

    Timestamp getStartTime(int rideId);

    Object getTolerance(int rideId);

    int addRiderToRide(int riderRouteId, int rideId);

    void setReceivedRating(int riderRouteId, int rating, String ratingComment);

    void setGivenRating(int riderRouteId, int rating, String ratingComment);

    void addPaymentReference(int rideId);

    @Deprecated
    int addRideRequest(int cust_id, Date starttime_earliest, Date starttimeLatest, int noPassengers, Point startpt, Point endpt, double price, String comment);

    List<RiderUndertakesRideEntity> getRides(String nickname);

    List<RiderUndertakesRideEntity> getActiveRideRequests(String nickname);

    RiderUndertakesRideEntity getRideByRiderRouteId(int riderRouteId);

    LinkedList<RiderUndertakesRideEntity> getAllRides();

    List<RiderUndertakesRideEntity> getActiveRideRequestsByCustId(String custId);

    List<RiderUndertakesRideEntity> getRidesWithoutRatingByRider(CustomerEntity rider);

    List<RiderUndertakesRideEntity> getRidesWithoutGivenRatingByRider(CustomerEntity rider);

    List<RiderUndertakesRideEntity> getRidesWithoutReceivedRatingByRider(CustomerEntity rider);

    List<RiderUndertakesRideEntity> getRidesWithoutRatingByDriver(CustomerEntity driver);

    List<RiderUndertakesRideEntity> getRidesWithoutGivenRatingByDriver(CustomerEntity driver);

    List<RiderUndertakesRideEntity> getRidesWithoutReceivedRatingByDriver(CustomerEntity driver);

    List<RiderUndertakesRideEntity> getRatedRidesByRider(CustomerEntity rider);

    List<RiderUndertakesRideEntity> getRatedRidesByDriver(CustomerEntity driver);

    int getRatingsTotalByCustomer(CustomerEntity customer);

    float getRatingsRatioByCustomerAndDate(CustomerEntity customer, Date fromDate);

    float getRatingsRatioByCustomer(CustomerEntity customer);

    int getPositiveRatingsTotalByCustomerAndDate(CustomerEntity customer, Date fromDate);

    int getPositiveRatingsTotalByCustomer(CustomerEntity customer);

    int getNeutralRatingsTotalByCustomerAndDate(CustomerEntity customer, Date fromDate);

    int getNeutralRatingsTotalByCustomer(CustomerEntity customer);

    int getNegativeRatingsTotalByCustomerAndDate(CustomerEntity customer, Date fromDate);

    int getNegativeRatingsTotalByCustomer(CustomerEntity customer);

    boolean removeRide(int rideId);

    int updateRide(int rideId,
            Date starttime_earliest,
            Date starttimeLatest,
            int noPassengers,
            Point startpt,
            Point endpt,
            double price,
            String comment,
            String startptAddress,
            String endptAddress);

    public int addRideRequest(int cust_id, Date starttime_earliest, Date starttimeLatest, int noPassengers, Point startpt, Point endpt, double price, String comment, String startptAddress, String endptAddress);

    void removeRiderFromRide(int riderrouteid, int rideid);

    public List<MatchEntity> getMatches(int riderrouteId, boolean setRiderAccess);

    boolean isRideUpdated(int riderrouteId);

    List<RiderUndertakesRideEntity> getInactiveRideRequests(String nickname);

    List<RiderUndertakesRideEntity> getActiveOpenRides(String nickname);

    void setMatchCountermand(Integer rideId, Integer riderrouteId);

    MatchEntity getMatch(Integer rideId, Integer riderrouteId);
}
