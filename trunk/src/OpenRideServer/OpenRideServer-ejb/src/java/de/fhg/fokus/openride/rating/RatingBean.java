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

package de.fhg.fokus.openride.rating;

import de.fhg.fokus.openride.customerprofile.CustomerControllerBean;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rating.helperclasses.OpenRatingInfo;
import de.fhg.fokus.openride.rating.helperclasses.Rating;
import de.fhg.fokus.openride.helperclasses.ControllerBean;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author pab
 */
@Stateless
@Deprecated
public class RatingBean extends ControllerBean implements RatingLocal {

    @EJB
    private CustomerControllerLocal customerControllerBean;
    @EJB
    private RiderUndertakesRideControllerLocal riderUndertakesRideControllerBean;
    @PersistenceContext
    private EntityManager em;
    private UserTransaction u;

    /*
     * Driver rates rider.
     */
    @Deprecated
    public void rateRider(int riderRouteId, Integer rating, String ratingComment) {
        init();
        RiderUndertakesRideEntity entity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderRouteId);
        entity.setReceivedrating(rating);
        if (ratingComment == null) {
            ratingComment = "";
        }
        entity.setReceivedratingComment(ratingComment);
        em.merge(entity);
        finish();
    }

    /*
     * Rider rates driver.
     */
    @Deprecated
    public void rateDriver(int riderRouteId, Integer rating, String ratingComment) {
        init();
        RiderUndertakesRideEntity entity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderRouteId);
        entity.setGivenrating(rating);
        if (ratingComment == null) {
            ratingComment = "";
        }
        entity.setGivenratingComment(ratingComment);
        em.merge(entity);
        finish();
    }

// TOFIX: a driver can have many ratings, because there are perhaps several riders!
    @Deprecated
    public Rating getRatingForDriver(int riderRouteId) {
        init();
        RiderUndertakesRideEntity entity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderRouteId);
        int r = entity.getGivenrating();
        String ratingComment = entity.getGivenratingComment();
        Rating rating = new Rating(r, ratingComment, entity.getGivenratingDate());
        finish();
        return rating;
    }

    @Deprecated
    public Rating getRatingForRider(int riderRouteId) {
        init();
        RiderUndertakesRideEntity entity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderRouteId);
        int ratingStarsNo = entity.getReceivedrating();
        String ratingComment = entity.getReceivedratingComment();
        Rating rating = new Rating(ratingStarsNo, ratingComment, entity.getReceivedratingDate());
        finish();
        return rating;
    }

    /**
     * This method scans the DB for rides that the customer has undertaken as Driver.
     * @param cust_id
     * @return List of rides that the customer has undertaken as driver.
     */
    @Deprecated
    public List<Rating> getRatingsAsDriver(int cust_id) {
        init();
        CustomerEntity e = customerControllerBean.getCustomer(cust_id);
        List<RiderUndertakesRideEntity> rides = riderUndertakesRideControllerBean.getRatedRidesByDriver(e);
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        Rating rating;
        for (RiderUndertakesRideEntity entity : rides) {
            rating = new Rating(entity.getReceivedrating(), entity.getReceivedratingComment(), entity.getReceivedratingDate());
            ratings.add(rating);
        }
        finish();
        return ratings;
    }

    /**
     * This method scans the DB for rides that the customer has undertaken as rider.
     * @param cust_id
     * @return List of rides that the customer has undertaken as rider.
     */
    @Deprecated
    public List<Rating> getRatingsAsRider(int cust_id) {
        init();
        CustomerEntity e = customerControllerBean.getCustomer(cust_id);
        List<RiderUndertakesRideEntity> rides = riderUndertakesRideControllerBean.getRatedRidesByRider(e);
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        Rating rating;
        for (RiderUndertakesRideEntity entity : rides) {
            rating = new Rating(entity.getReceivedrating(), entity.getReceivedratingComment(), entity.getReceivedratingDate());
            ratings.add(rating);
        }
        finish();
        return ratings;
    }

    @Deprecated
    public List<OpenRatingInfo> getOpenRatingsAsDriver(int cust_id) {
        init();
        CustomerEntity e = customerControllerBean.getCustomer(cust_id);
        List<RiderUndertakesRideEntity> rides = riderUndertakesRideControllerBean.getRidesWithoutGivenRatingByDriver(e);
        ArrayList<OpenRatingInfo> ratings = new ArrayList<OpenRatingInfo>();
        OpenRatingInfo rating;
        for (RiderUndertakesRideEntity entity : rides) {
            // TODO: (pab) What shall this Timestamp be?
            rating = new OpenRatingInfo(entity.getRiderrouteId(), cust_id, e.getCustNickname(), new Date());
            ratings.add(rating);
        }
        finish();
        return ratings;
    }

    @Deprecated
    public List<OpenRatingInfo> getOpenRatingsAsRider(int cust_id) {
        init();
        CustomerEntity e = customerControllerBean.getCustomer(cust_id);
        List<RiderUndertakesRideEntity> rides = riderUndertakesRideControllerBean.getRidesWithoutGivenRatingByRider(e);
        ArrayList<OpenRatingInfo> ratings = new ArrayList<OpenRatingInfo>();
        OpenRatingInfo rating;
        for (RiderUndertakesRideEntity entity : rides) {
            // TODO: (pab) What shall this Timestamp be?
            rating = new OpenRatingInfo(entity.getRiderrouteId(), cust_id, e.getCustNickname(), new Date());
            ratings.add(rating);
        }
        finish();
        return ratings;
    }

    public void persist(Object object) {
        init();
        em.persist(object);
        finish();
    }
}
