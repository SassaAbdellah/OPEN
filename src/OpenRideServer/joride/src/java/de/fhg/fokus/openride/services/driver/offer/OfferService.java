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

package de.fhg.fokus.openride.services.driver.offer;

import com.thoughtworks.xstream.XStream;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.Utils;
import de.fhg.fokus.openride.matching.MatchEntity;
import de.fhg.fokus.openride.matching.RouteMatchingBeanLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.driver.RoutePointEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import de.fhg.fokus.openride.routing.Coordinate;
import de.fhg.fokus.openride.routing.RouterBeanLocal;
import de.fhg.fokus.openride.services.driver.offer.helperclasses.Offer;
import de.fhg.fokus.openride.services.driver.offer.helperclasses.PostOfferResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.StringEscapeUtils;
import org.postgis.Point;

/**
 *
 * @author pab
 */
@Path("/users/{username}/rides/offers/")
public class OfferService {

    RouteMatchingBeanLocal routeMatchingBean = lookupRouteMatchingBeanLocal();
    RouterBeanLocal routerBean = lookupRouterBeanLocal();
    DriverUndertakesRideControllerLocal driverUndertakesRideControllerBean = lookupDriverUndertakesRideControllerBeanLocal();
    RiderUndertakesRideControllerLocal riderUndertakesRideControllerBean = lookupRiderUndertakesRideControllerBeanLocal();
    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();

    public HttpServletRequest getRequest(@Context HttpServletRequest context) {
        return context;
    }

    @GET
    @Produces("text/json")
    @Path("inactive")
    public Response getInactiveOffers(@PathParam("username") String username, @PathParam("rideId") String rideId, @Context ServletContext context) {
        System.out.println("getOffer start");

        List<DriverUndertakesRideEntity> drives = driverUndertakesRideControllerBean.getInactiveDrives(username);
        ArrayList<Offer> offers = new ArrayList<Offer>();
        Offer offer = null;
        for (DriverUndertakesRideEntity drive : drives) {
            //FIXME: check attributes!
            System.out.println("OfferService: Drive -> " + drive.toString());
            // only get the already inactive drives
            double startptLat = drive.getRideStartpt() != null ? drive.getRideStartpt().getY() : -1.0;
            double startptLon = drive.getRideStartpt() != null ? drive.getRideStartpt().getX() : -1.0;
            double endptLat = drive.getRideEndpt() != null ? drive.getRideEndpt().getY() : -1.0;
            double endptLon = drive.getRideEndpt() != null ? drive.getRideEndpt().getX() : -1.0;
            long starttime = drive.getRideStarttime() != null ? drive.getRideStarttime().getTime() : new Long("1").MIN_VALUE;

            //FIXME: was maxWaitTime, but should be rideprice??
            double rideprice = -1.0;

            String rideComment = drive.getRideComment();
            int acceptableDetourInMin = -1;//FIXME: (pab) what is different from the above Calling this: drive.getRideAcceptableDetourInMin();
            int acceptableDetourInKm = -1; //drive.getRideAcceptableDetourInKm();
            int acceptableDetourInPercent = -1; //drive.getRideAcceptableDetourInPercent();//drive.getRideAcceptableDetourInMin();
            int offeredseats = drive.getRideOfferedseatsNo();
            String startptAddress = drive.getStartptAddress();
            String endptAddress = drive.getEndptAddress();


            offer = new Offer(
                    drive.getRideId(),
                    startptLat,
                    startptLon,
                    endptLat,
                    endptLon,
                    starttime,
                    rideprice,
                    StringEscapeUtils.escapeHtml(rideComment),
                    acceptableDetourInMin,
                    acceptableDetourInKm,
                    acceptableDetourInPercent,
                    offeredseats,
                    StringEscapeUtils.escapeHtml(startptAddress),
                    StringEscapeUtils.escapeHtml(endptAddress),
                    null);
            offer.setUpdated(driverUndertakesRideControllerBean.isDriveUpdated(drive.getRideId()));
            offers.add(offer);
        }
        ArrayList list = new ArrayList();
        list.add(new Offer());

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(offers)).build();
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_ATOM_XML)
    @Path("atom")
    public Response getOffersAtom(@PathParam("username") String username, @PathParam("rideId") String rideId, @Context ServletContext context) {


        return null;
    }

    @GET
    @Produces("text/json")
    public Response getOffers(@PathParam("username") String username, @PathParam("rideId") String rideId, @Context ServletContext context) {
        System.out.println("getOffer start");

        List<DriverUndertakesRideEntity> drives = driverUndertakesRideControllerBean.getActiveDrives(username);
        ArrayList<Offer> offers = new ArrayList<Offer>();
        Offer offer = null;
        for (DriverUndertakesRideEntity drive : drives) {
            //FIXME: check attributes!
            System.out.println("OfferService: Drive -> " + drive.toString());
            if (drive != null) {

                double startptLat = drive.getRideStartpt() != null ? drive.getRideStartpt().getY() : -1.0;
                double startptLon = drive.getRideStartpt() != null ? drive.getRideStartpt().getX() : -1.0;
                double endptLat = drive.getRideEndpt() != null ? drive.getRideEndpt().getY() : -1.0;
                double endptLon = drive.getRideEndpt() != null ? drive.getRideEndpt().getX() : -1.0;
                long starttime = drive.getRideStarttime() != null ? drive.getRideStarttime().getTime() : new Long("1").MIN_VALUE;

                //FIXME: was maxWaitTime, but should be rideprice??
                double rideprice = -1.0;

                String rideComment = drive.getRideComment();
                int acceptableDetourInMin = -1;//FIXME: (pab) what is different from the above Calling this: drive.getRideAcceptableDetourInMin();
                int acceptableDetourInKm = -1; //drive.getRideAcceptableDetourInKm();
                int acceptableDetourInPercent = -1; //drive.getRideAcceptableDetourInPercent();//drive.getRideAcceptableDetourInMin();
                int offeredseats = drive.getRideOfferedseatsNo();
                String startptAddress = drive.getStartptAddress();
                String endptAddress = drive.getEndptAddress();


                offer = new Offer(
                        drive.getRideId(),
                        startptLat,
                        startptLon,
                        endptLat,
                        endptLon,
                        starttime,
                        rideprice,
                        StringEscapeUtils.escapeHtml(rideComment),
                        acceptableDetourInMin,
                        acceptableDetourInKm,
                        acceptableDetourInPercent,
                        offeredseats,
                        StringEscapeUtils.escapeHtml(startptAddress),
                        StringEscapeUtils.escapeHtml(endptAddress),
                        null);
                offer.setUpdated(driverUndertakesRideControllerBean.isDriveUpdated(drive.getRideId()));
                offers.add(offer);
            } else {
                System.out.println("OfferService: drive was null");
            }
        }
        ArrayList list = new ArrayList();
        list.add(new Offer());

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(offers)).build();
        return response;
    }

    @GET
    @Produces("text/json")
    @Path("{rideId}")
    public Response getOffer(@PathParam("username") String username, @PathParam("rideId") String rideId) {
        System.out.println("getOffer start");

        DriverUndertakesRideEntity drive = driverUndertakesRideControllerBean.getDriveByDriveId(Integer.parseInt(rideId));
        Offer offer = null;
        //FIXME: check attributes!
        System.out.println("OfferService: Drive -> " + drive.toString());

        double startptLat = drive.getRideStartpt() != null ? drive.getRideStartpt().getY() : -1.0;
        double startptLon = drive.getRideStartpt() != null ? drive.getRideStartpt().getX() : -1.0;
        double endptLat = drive.getRideEndpt() != null ? drive.getRideEndpt().getY() : -1.0;
        double endptLon = drive.getRideEndpt() != null ? drive.getRideEndpt().getX() : -1.0;
        long starttime = drive.getRideStarttime() != null ? drive.getRideStarttime().getTime() : new Long("1").MIN_VALUE;

        //FIXME: was maxWaitTime, but should be rideprice??
        double rideprice = -1.0;

        String rideComment = drive.getRideComment();
        int acceptableDetourInMin = drive.getRideAcceptableDetourInMin();
        int acceptableDetourInKm = drive.getRideAcceptableDetourInKm();
        int acceptableDetourInPercent = drive.getRideAcceptableDetourInPercent();
        int offeredseats = drive.getRideOfferedseatsNo();
        String startptAddress = drive.getStartptAddress();
        String endptAddress = drive.getEndptAddress();


        offer = new Offer(
                drive.getRideId(),
                startptLat,
                startptLon,
                endptLat,
                endptLon,
                starttime,
                rideprice,
                StringEscapeUtils.escapeHtml(rideComment),
                acceptableDetourInMin,
                acceptableDetourInKm,
                acceptableDetourInPercent,
                offeredseats,
                StringEscapeUtils.escapeHtml(startptAddress),
                StringEscapeUtils.escapeHtml(endptAddress),
                null);
        ArrayList list = new ArrayList();
        list.add(new Offer());

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(offer)).build();
        return response;
    }

    @PUT
    @Produces("text/json")
    @Path("{rideId}")
    public Response putOffer(@PathParam("rideId") int rideId, String json) {
        ArrayList list = new ArrayList();
        list.add(new Offer());
        list.add(new PostOfferResponse());
        XStream x = Utils.getJasonXStreamer(list);
        Offer r = (Offer) x.fromXML(json);
        //TODO: find evtl. states. If not existant do update and remove old and create new with new infos
        int custid = driverUndertakesRideControllerBean.getDriveByDriveId(rideId).getCustId().getCustId();
        if ((rideId = driverUndertakesRideControllerBean.updateRide(
                rideId,
                custid,
                new Point(r.getRidestartPtLon(), r.getRidestartPtLat()),
                new Point(r.getRideendPtLon(), r.getRideendPtLat()),
                r.getIntermediatePoints(), //Point[] intermediate route points
                new Date(r.getRidestartTime()),
                StringEscapeUtils.unescapeHtml(r.getRideComment()),
                r.getAcceptableDetourInMin(),
                r.getAcceptableDetourInKm(),
                r.getAcceptableDetourInPercent(),
                r.getOfferedSeatsNo(),
                StringEscapeUtils.unescapeHtml(r.getStartptAddress()),
                StringEscapeUtils.unescapeHtml(r.getEndptAddress()))) == -1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {

            /**

            // TODO: this should be asynchron!!! remove
            List<MatchEntity> matches = lookupRouteMatchingBeanLocal().searchForRiders(rideId);
            de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse  m ;
            List<de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse> list = new ArrayList<de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse>();
            if(matches != null){
            // Some Matches were found.
            for(MatchEntity match: matches){
            int rideid = match.getMatchEntityPK().getRideId();
            int riderrouteid = match.getMatchEntityPK().getRiderrouteId();
            RiderUndertakesRideEntity rideEntity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderrouteid);
            DriverUndertakesRideEntity driveEntity = driverUndertakesRideControllerBean.getDriveByDriveId(rideid);

            CustomerEntity rider = rideEntity.getCustId();
            //this results in a null pointer exception
            //don't know how the jpa translates this query
            //but maybe it misses the match entity in the db
            //it is not yet persistet.

            m = new de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse(
            match.getDriverState(),
            match.getRiderState(),
            match.getMatchSharedDistancEmeters(),
            match.getMatchDetourMeters(),
            match.getMatchExpectedStartTime().getTime(),
            driveEntity.getRideId(),
            rideEntity.getRiderrouteId(),
            rider.getCustId(),
            rider.getCustNickname(),
            riderUndertakesRideControllerBean.getRatingsRatioByCustomer(rider),
            match.getMatchPriceCents(),
            rideEntity.getStartptAddress(),
            rideEntity.getEndptAddress()
            );
            list.add(m);
            }
            }
            Response response = Response.ok(x.toXML(list)).build();
             */
            Response response = Response.ok(x.toXML(new PostOfferResponse(rideId))).build();

            //Response response = Response.ok().build();
            return response;
        }
    }

    @DELETE
    @Produces("text/json")
    @Path("{rideId}")
    public Response deleteOffer(@PathParam("rideId") String rideId) {
        System.out.println("deleteOffer start");
        // FIXME: some error handling...
        driverUndertakesRideControllerBean.removeRide(Integer.parseInt(rideId));
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Produces("text/json")
    @Consumes("*/*")
    public Response postOffer(@Context HttpServletRequest con, @PathParam("username") String username, String json) {
        System.out.println("postOffer start");
        // get the parameter containing the request-information.


        Enumeration ee = con.getParameterNames();
        System.out.println("Elements");
        while (ee.hasMoreElements()) {
            System.out.println("Element: " + ee.nextElement());
        }

        //String json = (String)con.getParameter("json");
        System.out.println("json: " + json);
        if (json != null) {
            // to use this method client must send json content!
            // build a List of Objects that shall be available in the JSON context.
            ArrayList startlist = new ArrayList();
            startlist.add(new Offer());
            startlist.add(new PostOfferResponse());

            XStream x = Utils.getJasonXStreamer(startlist);

            Offer r = (Offer) x.fromXML(json);
            // do something with tht information

            // Add the new ride to DB!
            CustomerEntity e = customerControllerBean.getCustomerByNickname(username);

            //RoutePoint[] routepoints = routerBean.getEquiDistantRoutePoints(new Coordinate[]{new Coordinate(r.getRidestartPtLat(), r.getRidestartPtLon()), new Coordinate(r.getRideendPtLat(), r.getRideendPtLon())}, new Timestamp(r.getRidestartTime()), Constants.ROUTE_FASTEST_PATH_DEFAULT, Constants.ROUTER_NEAREST_NEIGHBOR_THRESHOLD, Constants.MATCHING_MAX_ROUTE_POINT_DISTANCE);

            // FIXME: What shall these parameters contain? Extend the Object!

            //System.out.println("routpoints: " + routepoints);
            int rideId = -1;
            System.out.println("startaddr: " + r.getStartptAddress() + " endptAddr: " + r.getEndptAddress());
            if ((rideId = driverUndertakesRideControllerBean.addRide(
                    e.getCustId(),
                    new Point(r.getRidestartPtLon(), r.getRidestartPtLat()),
                    new Point(r.getRideendPtLon(), r.getRideendPtLat()),
                    r.getIntermediatePoints(), //Point[] intermediate route points
                    new Date(r.getRidestartTime()),
                    StringEscapeUtils.unescapeHtml(r.getRideComment()),
                    r.getAcceptableDetourInMin(),
                    r.getAcceptableDetourInKm(),
                    r.getAcceptableDetourInPercent(),
                    r.getOfferedSeatsNo(),
                    StringEscapeUtils.unescapeHtml(r.getStartptAddress()),
                    StringEscapeUtils.unescapeHtml(r.getEndptAddress()))) == -1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {

                Response response = Response.ok(x.toXML(new PostOfferResponse(rideId))).build();

                return response;
            }
            // in this case no special response-information is needed.
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Produces("text/json")
    @Path("{rideId}/matches/{riderrouteid}/accept")
    public Response putAcceptRider(
            @PathParam("username") String username,
            @PathParam("rideId") int rideId,
            @PathParam("riderrouteid") int riderrouteid,
            @Context HttpServletRequest request) {
        System.out.println("acceptRider start");
        MatchEntity match = driverUndertakesRideControllerBean.acceptRider(rideId, riderrouteid);
        if (match == null) {
            // invalid id.
            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        } else if (match.getRiderState() == null) {
            System.out.println("acceptRider start 1");
            // the rider did not yet recognize this match
            // TODO: inform the driver
        } else if (match.getRiderState().equals(MatchEntity.ACCEPTED)) {
            System.out.println("acceptRider start 2");
            // the rider has already been accepting the rider
            if (riderUndertakesRideControllerBean.addRiderToRide(riderrouteid, rideId) != -1) {
                System.out.println("successfully added rider");
                // rider was added

            } else {
                System.out.println("unsuccessfull adding rider");
                //rider could not be added
                // TODO: inform rider!
                // TODO: match has to be marked as not bookable
            }

            int custid = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderrouteid).getCustId().getCustId();
            //TODO: inform both parties about success of booking.
        } else {
            System.out.println("acceptRider start 3");
            //the driver has already rejected the rider
        }

        System.out.println("match" + match);
        Response response = Response.ok().build();
        return response;
    }

    @PUT
    @Produces("text/json")
    @Path("{rideId}/matches/{riderrouteid}/reject")
    public Response putRejectRider(
            @PathParam("username") String username,
            @PathParam("rideId") int rideId,
            @PathParam("riderrouteid") int riderrouteid,
            @Context HttpServletRequest request) {
        MatchEntity match = driverUndertakesRideControllerBean.rejectRider(rideId, riderrouteid);
        if (match == null) {
            // invalid id.
            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        } else if (match.getRiderState() == null) {
            // the rider did not yet recognize this match
            // TODO: inform the rider
        } else if (match.getRiderState().equals(MatchEntity.ACCEPTED)) {
            // the rider has already been accepting the rider
            //TODO: inform the rider the driver rejected
        } else if (!match.getDriverState().equals(MatchEntity.REJECTED)) {
            // the rider has already been accepting the rider

            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        } else {
            //the rider has already rejected the rider
        }

        /** putRejectRider returns nothing.
         *
        CustomerEntity rider = match.getRiderUndertakesRideEntity().getCustId();
        // This response is for a rider


        de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse m = new de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse(
        match.getDriverState(),
        match.getRiderState(),
        match.getMatchSharedDistancEmeters(),
        match.getMatchDetourMeters(),
        match.getMatchExpectedStartTime().getTime(),
        match.getDriverUndertakesRideEntity().getRideId(),
        match.getRiderUndertakesRideEntity().getRiderrouteId(),
        rider.getCustId(),
        rider.getCustNickname(),
        riderUndertakesRideControllerBean.getRatingsRatioByCustomer(rider),
        match.getMatchPriceCents(),
        match.getRiderUndertakesRideEntity().getStartptAddress(),
        match.getRiderUndertakesRideEntity().getEndptAddress()
        );

        List<de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse> list = new LinkedList<de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse>();
        list.add(m);
        XStream x = Utils.getJasonXStreamer(list);
         * */
        Response response = Response.ok().build();
        return response;
    }

    @GET
    @Produces("text/json")
    @Path("{rideId}/matches")
    public Response getMatches(@PathParam("username") String username, @PathParam("rideId") int rideId) {
        System.out.println("getOffer start");
        List<MatchEntity> matches = driverUndertakesRideControllerBean.getMatches(rideId, true);
        de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse m;
        List<de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse> list = new ArrayList<de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse>();
        for (MatchEntity match : matches) {
            // This response is for a rider
            int rideid = match.getMatchEntityPK().getRideId();
            int riderrouteid = match.getMatchEntityPK().getRiderrouteId();
            RiderUndertakesRideEntity rideEntity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderrouteid);
            DriverUndertakesRideEntity driveEntity = driverUndertakesRideControllerBean.getDriveByDriveId(rideid);

            double detourMeters = Math.abs(match.getMatchDetourMeters());
            if (detourMeters < 0) {
                // don't display negative detours in clients:
                detourMeters = 0;
            }

//            int seatsAvailable = driveEntity.getRideOfferedseatsNo();
//            for (RiderUndertakesRideEntity rider : driveEntity.getRiderUndertakesRideEntityCollection()) {
//                seatsAvailable -= rider.getNoPassengers();
//            }
//
//            Math.max(seatsAvailable, 0);

            m = new de.fhg.fokus.openride.services.driver.offer.helperclasses.MatchResponse(
                    match.getDriverState(),
                    match.getRiderState(),
                    match.getMatchSharedDistancEmeters(),
                    detourMeters,
                    match.getMatchExpectedStartTime().getTime(),
                    driveEntity.getRideId(),
                    rideEntity.getRiderrouteId(),
                    rideEntity.getCustId().getCustId(),
                    rideEntity.getCustId().getCustNickname(),
                    Math.round(riderUndertakesRideControllerBean.getRatingsRatioByCustomer(rideEntity.getCustId()) * 100),
                    match.getMatchPriceCents(),
                    StringEscapeUtils.escapeHtml(rideEntity.getStartptAddress()),
                    StringEscapeUtils.escapeHtml(rideEntity.getEndptAddress()),
                    rideEntity.getStartpt().getY(),
                    rideEntity.getStartpt().getX(),
                    rideEntity.getEndpt().getY(),
                    rideEntity.getEndpt().getX(),
                    rideEntity.getNoPassengers());
//                    seatsAvailable);

            if (match.getDriverState() != null && match.getDriverState().equals(MatchEntity.ACCEPTED) && match.getRiderState() != null && match.getRiderState().equals(MatchEntity.ACCEPTED)) {
                m.setRiderMobilePhoneNo(rideEntity.getCustId().getCustMobilephoneno());
            }

            list.add(m);
        }

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(list)).build();
        return response;
    }

    @GET
    @Produces("text/json")
    @Path("{rideId}/route")
    public Response getRoute(@PathParam("username") String username, @PathParam("rideId") int rideId) {
        List<RoutePointEntity> routePoints = driverUndertakesRideControllerBean.getRoutePoints(rideId);

        List<Coordinate> typeList = new LinkedList<Coordinate>();
        typeList.add(new Coordinate(new Point(-1.0, -1.0)));

        List<Coordinate> route = new ArrayList<Coordinate>();
        List<Coordinate> startPt = new ArrayList<Coordinate>();
        List<Coordinate> endPt = new ArrayList<Coordinate>();
        List<List<Coordinate>> list = new ArrayList<List<Coordinate>>();
        list.add(route);
        list.add(startPt);
        list.add(endPt);

        for (RoutePointEntity rp : routePoints) {
            route.add(new Coordinate(rp.getLatitude(), rp.getLongitude()));
        }
        List<RiderUndertakesRideEntity> rides = driverUndertakesRideControllerBean.getRidersForDrive(rideId);
        if (rides != null) {
            for (RiderUndertakesRideEntity r : rides) {
                startPt.add(new Coordinate(r.getStartpt()));
                endPt.add(new Coordinate(r.getEndpt()));
            }
        }

        XStream x = Utils.getJasonXStreamer(typeList);
        Response response = Response.ok(x.toXML(list)).build();

        return response;
    }

    @GET
    @Produces("text/json")
    @Path("{rideId}/tracker")
    public Response getTrack(@PathParam("username") String username, @PathParam("rideId") int rideId) {

        Response response = Response.status(Response.Status.NOT_FOUND).build();

        return response;
    }

    @PUT
    @Produces("text/json")
    @Path("{rideId}/tracker")
    public Response putTrack(@PathParam("username") String username, @PathParam("rideId") int rideId) {

        Response response = Response.status(Response.Status.NOT_FOUND).build();

        return response;
    }

    /* Not needed anymore
    @GET
    @Produces("text/json")
    @Path("{rideId}/passengers")
    public Response getPassengers(@PathParam("username") String username, @PathParam("rideId") int rideId){
    List<RiderUndertakesRideEntity> riders = driverUndertakesRideControllerBean.getRidersForDrive(rideId);
    List<Rider> list = new ArrayList<Rider>();
    for(RiderUndertakesRideEntity rider: riders){
    list.add(new Rider(rider));
    }


    XStream x = Utils.getJasonXStreamer(list);
    Response response = Response.ok(x.toXML(list)).build();
    return response;
    }

    @DELETE
    @Produces("text/json")
    @Path("{rideId}/passengers/{riderrouteid}")
    public Response deletePassenger(@PathParam("username") String username, 
    @PathParam("rideId") int rideId,
    @PathParam("riderrouteid") int riderrouteid){
    riderUndertakesRideControllerBean.removeRiderFromRide(riderrouteid,rideId);
    Response response = Response.ok().build();
    return response;
    }*/
    private CustomerControllerLocal lookupCustomerControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CustomerControllerBean!de.fhg.fokus.openride.customerprofile.CustomerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DriverUndertakesRideControllerLocal lookupDriverUndertakesRideControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DriverUndertakesRideControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/DriverUndertakesRideControllerBean!de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal");
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

    private RouterBeanLocal lookupRouterBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RouterBeanLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RouterBean!de.fhg.fokus.openride.routing.RouterBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RouteMatchingBeanLocal lookupRouteMatchingBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RouteMatchingBeanLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RouteMatchingBean!de.fhg.fokus.openride.matching.RouteMatchingBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
