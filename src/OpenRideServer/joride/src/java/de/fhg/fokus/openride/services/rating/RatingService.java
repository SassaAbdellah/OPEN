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

package de.fhg.fokus.openride.services.rating;

import com.thoughtworks.xstream.XStream;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.Utils;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import de.fhg.fokus.openride.services.rating.helperclasses.GivenRatingRequest;
import de.fhg.fokus.openride.services.rating.helperclasses.OpenRatingResponse;
import de.fhg.fokus.openride.services.rating.helperclasses.RatingsSummaryResponse;
import de.fhg.fokus.openride.services.rating.helperclasses.ReceivedRatingResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author tku
 */
@Path("/users/{username}/ratings/")
public class RatingService {

    RiderUndertakesRideControllerLocal riderUndertakesRideControllerBean = lookupRiderUndertakesRideControllerBeanLocal();
    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();

    @GET
    @Path("summary/")
    @Produces("text/json")
    public Response getRatingsSummary(@Context HttpServletRequest con, @PathParam("username") String username) {

        System.out.println("getRatingsSummary start");

        if (!username.equals(con.getRemoteUser())) {
            /*
             * RatingsSummary may be requested by any logged in user?
             *
            return Response.status(Response.Status.FORBIDDEN).build();
             */
        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

        // build a List of Objects that shall be available in the JSON context.
        ArrayList list = new ArrayList();
        list.add(new RatingsSummaryResponse());

        XStream x = Utils.getJasonXStreamer(list);


        RatingsSummaryResponse ratingsSummary = new RatingsSummaryResponse();


        ratingsSummary.setRatingsTotal(riderUndertakesRideControllerBean.getRatingsTotalByCustomer(c));

        ratingsSummary.setRatingsRatioPercent(Math.round(riderUndertakesRideControllerBean.getRatingsRatioByCustomer(c) * 100));

        ratingsSummary.setRatingsLatestPositive(riderUndertakesRideControllerBean.getPositiveRatingsTotalByCustomer(c));
        ratingsSummary.setRatingsLatestNeutral(riderUndertakesRideControllerBean.getNeutralRatingsTotalByCustomer(c));
        ratingsSummary.setRatingsLatestNegative(riderUndertakesRideControllerBean.getNegativeRatingsTotalByCustomer(c));

        return Response.ok(x.toXML(ratingsSummary)).build();

    }

    @GET
    @Produces("text/json")
    public Response getRatings(@Context HttpServletRequest con, @PathParam("username") String username) {

        System.out.println("getRatings start");

        if (!username.equals(con.getRemoteUser())) {
            /*
             * Ratings may be requested by any logged in user?
             *
            return Response.status(Response.Status.FORBIDDEN).build();
             */
        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

        // build a List of Objects that shall be available in the JSON context.
        ArrayList list = new ArrayList();
        list.add(new ReceivedRatingResponse());

        XStream x = Utils.getJasonXStreamer(list);

        List<RiderUndertakesRideEntity> receivedRatingsAsRider;
        List<RiderUndertakesRideEntity> receivedRatingsAsDriver;

        receivedRatingsAsRider = riderUndertakesRideControllerBean.getRatedRidesByRider(c);
        receivedRatingsAsDriver = riderUndertakesRideControllerBean.getRatedRidesByDriver(c);


        ArrayList receivedRatings = new ArrayList();       

        ReceivedRatingResponse response;
        for (RiderUndertakesRideEntity ride : receivedRatingsAsRider) {
            response = new ReceivedRatingResponse();
            response.setCustRole("d".charAt(0)); // this is a driver's rating
            response.setCustId(ride.getRideId().getCustId().getCustId());
            response.setCustNickname(ride.getRideId().getCustId().getCustNickname());
            response.setCustGender(ride.getRideId().getCustId().getCustGender());

            // TODO: This should be replaced with Timestamprealized once this is set!
            response.setTimestamprealized(ride.getStarttimeEarliest().getTime());
            //response.setTimestamprealized(ride.getTimestamprealized().getTime());

            response.setReceivedRating(ride.getReceivedrating());
            response.setReceivedRatingComment(StringEscapeUtils.escapeHtml(ride.getReceivedratingComment()));
            receivedRatings.add(response);
        }
        for (RiderUndertakesRideEntity ride : receivedRatingsAsDriver) {
            response = new ReceivedRatingResponse();
            response.setCustRole("r".charAt(0)); // this is a rider's rating
            response.setCustId(ride.getCustId().getCustId());
            response.setCustNickname(ride.getCustId().getCustNickname());
            response.setCustGender(ride.getCustId().getCustGender());

            // TODO: This should be replaced with Timestamprealized once this is set!
            response.setTimestamprealized(ride.getStarttimeEarliest().getTime());
            //response.setTimestamprealized(ride.getTimestamprealized().getTime());

            response.setReceivedRating(ride.getGivenrating());
            response.setReceivedRatingComment(ride.getGivenratingComment());
            receivedRatings.add(response);
        }

        // sort receivedRatings list by timestamprealized!
        Collections.sort(receivedRatings);

        return Response.ok(x.toXML(receivedRatings)).build();

    }

    @POST
    @Produces("text/json")
    public Response postRating(@Context HttpServletRequest con, @PathParam("username") String username, String json) {
        System.out.println("postRating start");

        if (json != null) {
            System.out.println("json: " + json);
            // to use this method client must send json content!

            if (!username.equals(con.getRemoteUser())) {
                /*
                 * Ratings my be posted by other users, too
                 *
                return Response.status(Response.Status.FORBIDDEN).build();
                 */
            }

            CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

            // build a List of Objects that shall be available in the JSON context.
            ArrayList list = new ArrayList();
            list.add(new GivenRatingRequest());

            XStream x = Utils.getJasonXStreamer(list);


            GivenRatingRequest r = (GivenRatingRequest) x.fromXML(json);

            // Check whether rating value is valid
            if (r.getGivenRating() >= -1 && r.getGivenRating() <= 1) {

                // Do not accept negative ratings without a comment
                if (r.getGivenRating() == -1 && r.getGivenRatingComment().equals("")) {
                    // return "bad request" status msg
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
                // Save this rating

                boolean rated = false;
                // Was the customer who submits this rating driver or rider?
                if (riderUndertakesRideControllerBean.getRideByRiderRouteId(r.getRiderRouteId()).getCustId().equals(c)) {
                    // Customer was the rider -> rates driver -> GivenRating
                    riderUndertakesRideControllerBean.setGivenRating(r.getRiderRouteId(), r.getGivenRating(), r.getGivenRatingComment());
                    rated = true;
                }
                if (riderUndertakesRideControllerBean.getRideByRiderRouteId(r.getRiderRouteId()).getRideId().getCustId().equals(c)) {
                    // Customer was the driver -> rates rider  -> ReceivedRating
                    riderUndertakesRideControllerBean.setReceivedRating(r.getRiderRouteId(), r.getGivenRating(), r.getGivenRatingComment());
                    rated = true;
                }
                if (!rated) {
                    // this Customer was neither rider or driver!... operation not allowed.
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }

            } else {
                // return "bad request" status msg
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces("text/json")
    @Path("open/")
    public Response getOpenRatings(@Context HttpServletRequest con, @PathParam("username") String username) {
        System.out.println("getUnratedRides start");

        if (!username.equals(con.getRemoteUser())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

        // build a List of Objects that shall be available in the JSON context.
        ArrayList list = new ArrayList();
        list.add(new OpenRatingResponse());

        XStream x = Utils.getJasonXStreamer(list);


        List<RiderUndertakesRideEntity> unratedRidesAsRider;
        List<RiderUndertakesRideEntity> unratedRidesAsDriver;

        unratedRidesAsRider = (List<RiderUndertakesRideEntity>) riderUndertakesRideControllerBean.getRidesWithoutGivenRatingByRider(c);
        unratedRidesAsDriver = (List<RiderUndertakesRideEntity>) riderUndertakesRideControllerBean.getRidesWithoutReceivedRatingByDriver(c);


        ArrayList openRatings = new ArrayList();

        OpenRatingResponse response;
        for (RiderUndertakesRideEntity ride : unratedRidesAsRider) {
            response = new OpenRatingResponse();
            response.setCustRole("d".charAt(0)); // this is a driver's rating
            response.setRiderRouteId(ride.getRiderrouteId());
            response.setCustId(ride.getRideId().getCustId().getCustId());
            response.setCustNickname(ride.getRideId().getCustId().getCustNickname());
            response.setCustGender(ride.getRideId().getCustId().getCustGender());
            
            // TODO: This should be replaced with Timestamprealized once this is set!
            response.setTimestamprealized(ride.getStarttimeEarliest().getTime());
            //response.setTimestamprealized(ride.getTimestamprealized().getTime());

            openRatings.add(response);
        }
        for (RiderUndertakesRideEntity ride : unratedRidesAsDriver) {
            response = new OpenRatingResponse();
            response.setCustRole("r".charAt(0)); // this is a rider's rating
            response.setRiderRouteId(ride.getRiderrouteId());
            response.setCustId(ride.getCustId().getCustId());
            response.setCustNickname(ride.getCustId().getCustNickname());
            response.setCustGender(ride.getCustId().getCustGender());

            // TODO: This should be replaced with Timestamprealized once this is set!
            response.setTimestamprealized(ride.getStarttimeEarliest().getTime());
            //response.setTimestamprealized(ride.getTimestamprealized().getTime());

            openRatings.add(response);
        }

        // sort openRatings list by timestamprealized!
        Collections.sort(openRatings);

        return Response.ok(x.toXML(openRatings)).build();

    }

    private CustomerControllerLocal lookupCustomerControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CustomerControllerBean!de.fhg.fokus.openride.customerprofile.CustomerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RiderUndertakesRideControllerLocal lookupRiderUndertakesRideControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RiderUndertakesRideControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RiderUndertakesRideControllerBean!de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
