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

package de.fhg.fokus.openride.services.rider.search;

import com.thoughtworks.xstream.XStream;
import de.fhg.fokus.openride.customerprofile.CarDetailsControllerLocal;
import de.fhg.fokus.openride.customerprofile.CarDetailsEntity;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.Utils;
import de.fhg.fokus.openride.matching.MatchEntity;
import de.fhg.fokus.openride.matching.RouteMatchingBeanLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import de.fhg.fokus.openride.services.rider.search.helperclasses.MatchResponse;
import de.fhg.fokus.openride.services.rider.search.helperclasses.PostSearchResponse;
import de.fhg.fokus.openride.services.rider.search.helperclasses.Search;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.StringEscapeUtils;
import org.postgis.Point;

/**
 *
 * @author pab
 */
@Path("/users/{username}/rides/searches/")
public class SearchService {

    DriverUndertakesRideControllerLocal driverUndertakesRideControllerBean = lookupDriverUndertakesRideControllerBeanLocal();
    RouteMatchingBeanLocal routeMatchingBean = lookupRouteMatchingBeanLocal();
    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();
    CarDetailsControllerLocal carDetailsControllerBean = lookupCarDetailsControllerBeanLocal();
    RiderUndertakesRideControllerLocal riderUndertakesRideControllerBean = lookupRiderUndertakesRideControllerBeanLocal();

    @GET
    @Produces("text/json")
    @Path("{riderrouteid}/matches")
    public Response getMatches(@PathParam("riderrouteid") Integer riderrouteid) {

        List<MatchEntity> matches = (List<MatchEntity>) riderUndertakesRideControllerBean.getMatches(riderrouteid, true);
        MatchResponse m;
        List<MatchResponse> matchlist = new ArrayList<MatchResponse>();
        CustomerEntity driver;
        RiderUndertakesRideEntity rideEntity = riderUndertakesRideControllerBean.getRideByRiderRouteId(riderrouteid);
        for (MatchEntity match : matches) {
            int rideid = match.getMatchEntityPK().getRideId();
            DriverUndertakesRideEntity driveEntity = driverUndertakesRideControllerBean.getDriveByDriveId(rideid);

            driver = driveEntity.getCustId();

            m = new MatchResponse(match.getDriverState(),
                    match.getRiderState(),
                    match.getMatchExpectedStartTime().getTime(),
                    driveEntity.getRideId(),
                    rideEntity.getRiderrouteId(),
                    driver.getCustId(),
                    driver.getCustNickname(),
                    Math.round(riderUndertakesRideControllerBean.getRatingsRatioByCustomer(driver) * 100),
                    match.getMatchPriceCents(),
                    StringEscapeUtils.escapeHtml(rideEntity.getStartptAddress()),
                    StringEscapeUtils.escapeHtml(rideEntity.getEndptAddress()));

            if (match.getDriverState() != null && match.getDriverState().equals(MatchEntity.ACCEPTED) && match.getRiderState() != null && match.getRiderState().equals(MatchEntity.ACCEPTED)) {
                m.setDriverMobilePhoneNo(StringEscapeUtils.escapeHtml(driver.getCustMobilephoneno()));
                CarDetailsEntity cd = carDetailsControllerBean.getCarDetails(driver);
                m.setDriverCarColour(cd.getCardetColour());
                m.setDriverCarBrand(cd.getCardetBrand());
                m.setDriverCarBuildYear(cd.getCardetBuildyear());
                m.setDriverCarPlateNo(cd.getCardetPlateno());
            }

            matchlist.add(m);
        }

        XStream x = Utils.getJasonXStreamer(matchlist);
        Response response = Response.ok(x.toXML(matchlist)).build();
        return response;
    }

    @PUT
    @Produces("text/json")
    @Path("{riderrouteid}/matches/{rideid}/accept")
    public Response putAcceptDriver(
            @PathParam("username") String username,
            @PathParam("rideid") int rideId,
            @PathParam("riderrouteid") int riderrouteid,
            @Context HttpServletRequest request) {
        MatchEntity match = driverUndertakesRideControllerBean.acceptDriver(rideId, riderrouteid);
        if (match == null) {
            // invalid id.
            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        }else if (match.getDriverState() == null) {
            // the driver did not yet recognize this match
        } else if (match.getDriverState().equals(MatchEntity.ACCEPTED)) {
            // the driver has already been accepting the rider
            if (riderUndertakesRideControllerBean.addRiderToRide(riderrouteid, rideId) != -1) {
                System.out.println("successfully added rider");
                // rider was added

            } else {
                System.out.println("unsuccessfull adding rider");
                //rider could not be added
            }
        } else {
            //the driver has already rejected the rider
        }

        Response response = Response.ok().build();
        return response;
    }

    @PUT
    @Produces("text/json")
    @Path("{riderrouteid}/matches/{rideid}/reject")
    public Response putRejectDriver(
            @PathParam("username") String username,
            @PathParam("rideid") int rideId,
            @PathParam("riderrouteid") int riderrouteid,
            @Context HttpServletRequest request) {
        System.out.println("getOffer start");
        MatchEntity match = driverUndertakesRideControllerBean.rejectDriver(rideId, riderrouteid);
        if (match == null) {
            // invalid id.
            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        }else if (match.getDriverState() == null) {
            // the driver did not yet recognize this match
        } else if (match.getDriverState().equals(MatchEntity.ACCEPTED)) {
            // the driver has already been accepting the rider
        } else if (!match.getDriverState().equals(MatchEntity.REJECTED)) {
            // the driver has already been accepting the rider
            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        } else {
            //the rider has already rejected the rider
        }

        Response response = Response.ok().build();
        return response;
    }

    @GET
    @Produces("text/json")
    public Response getSearches(@Context HttpServletRequest request) {
        List<RiderUndertakesRideEntity> activeRides = riderUndertakesRideControllerBean.getActiveRideRequests(request.getRemoteUser());
        Search s;
        ArrayList<Search> searches = new ArrayList<Search>();
        //FIXME: either adapt the DB or the Search-Class
        for (RiderUndertakesRideEntity r : activeRides) {
            if (r != null) {
                s = new Search(
                        r.getRiderrouteId() != null ? r.getRiderrouteId() : -1,
                        r.getRideId() != null ? r.getRideId().getRideId() : -1,
                        r.getStartpt() != null ? r.getStartpt().getX() : null,
                        r.getStartpt() != null ? r.getStartpt().getY() : null,
                        r.getEndpt() != null ? r.getEndpt().getX() : null,
                        r.getEndpt() != null ? r.getEndpt().getY() : null,
                        r.getStarttimeLatest() != null ? r.getStarttimeLatest().getTime() : null,
                        StringEscapeUtils.escapeHtml(r.getComment()),
                        Math.round((r.getStarttimeLatest().getTime() - r.getStarttimeEarliest().getTime()) / 1000 / 60),
                        r.getNoPassengers(),
                        false,
                        r.getStarttimeEarliest() != null ? r.getStarttimeEarliest().getTime() : null,
                        r.getPrice(),
                        StringEscapeUtils.escapeHtml(r.getStartptAddress()),
                        StringEscapeUtils.escapeHtml(r.getEndptAddress()));
                s.setUpdated(riderUndertakesRideControllerBean.isRideUpdated(r.getRiderrouteId()));
                searches.add(s);
            }
        }

        ArrayList list = new ArrayList();
        list.add(new Search());

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(searches)).build();
        return response;
    }

    @GET
    @Produces("text/json")
    @Path("inactive")
    public Response getInactiveSearches(@Context HttpServletRequest request) {

        List<RiderUndertakesRideEntity> activeRides = riderUndertakesRideControllerBean.getInactiveRideRequests(request.getRemoteUser());
        Search s;
        ArrayList<Search> searches = new ArrayList<Search>();
        //FIXME: either adapt the DB or the Search-Class
        for (RiderUndertakesRideEntity r : activeRides) {
            if (r != null) {
                s = new Search(
                        r.getRiderrouteId() != null ? r.getRiderrouteId() : -1,
                        r.getRideId() != null ? r.getRideId().getRideId() : -1,
                        r.getStartpt() != null ? r.getStartpt().getX() : null,
                        r.getStartpt() != null ? r.getStartpt().getY() : null,
                        r.getEndpt() != null ? r.getEndpt().getX() : null,
                        r.getEndpt() != null ? r.getEndpt().getY() : null,
                        r.getStarttimeLatest() != null ? r.getStarttimeLatest().getTime() : null,
                        StringEscapeUtils.escapeHtml(r.getComment()),
                        Math.round((r.getStarttimeLatest().getTime() - r.getStarttimeEarliest().getTime()) / 1000 / 60),
                        r.getNoPassengers(),
                        false,
                        r.getStarttimeEarliest() != null ? r.getStarttimeEarliest().getTime() : null,
                        r.getPrice(),
                        StringEscapeUtils.escapeHtml(r.getStartptAddress()),
                        StringEscapeUtils.escapeHtml(r.getEndptAddress()));
                s.setUpdated(riderUndertakesRideControllerBean.isRideUpdated(r.getRiderrouteId()));
                searches.add(s);
            }
        }

        ArrayList list = new ArrayList();
        list.add(new Search());

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(searches)).build();
        return response;
    }

    @GET
    @Produces("text/json")
    @Path("{rideId}")
    public Response getSearch(@PathParam("username") String username, @PathParam("rideId") String rideId) {
        RiderUndertakesRideEntity r = riderUndertakesRideControllerBean.getRideByRiderRouteId(Integer.parseInt(rideId));
        Search s;
        if (r == null) {
            // search does not exist!
            Response response = Response.status(Response.Status.BAD_REQUEST).build();
            return response;
        }
        s = new Search(
                r.getRiderrouteId(),
                r.getRideId() != null ? r.getRideId().getRideId() : -1,
                r.getStartpt().getY(),
                r.getStartpt().getX(),
                r.getEndpt().getY(),
                r.getEndpt().getX(),
                r.getStarttimeLatest().getTime(),
                StringEscapeUtils.escapeHtml(r.getComment()),
                Math.round((r.getStarttimeLatest().getTime() - r.getStarttimeEarliest().getTime()) / 1000 / 60),
                r.getNoPassengers(),
                false,
                r.getStarttimeEarliest().getTime(),
                r.getPrice(),
                StringEscapeUtils.escapeHtml(r.getStartptAddress()),
                StringEscapeUtils.escapeHtml(r.getEndptAddress()));

        ArrayList list = new ArrayList();
        list.add(new Search());

        XStream x = Utils.getJasonXStreamer(list);
        Response response = Response.ok(x.toXML(s)).build();
        return response;
    }

    @PUT
    @Produces("text/json")
    @Path("{riderrouteId}")
    public Response putSearch(@PathParam("riderrouteId") int riderrouteId, String json) {

        ArrayList list = new ArrayList();
        list.add(new Search());
        list.add(new PostSearchResponse());

        XStream x = Utils.getJasonXStreamer(list);

        Search r = (Search) x.fromXML(json);
        Response response;
        if ((riderrouteId = riderUndertakesRideControllerBean.updateRide(
                riderrouteId,
                new Date(r.getRidestartTimeEarliest()),
                new Date(r.getRidestartTimeEarliest() + (1000 * 60 * r.getMaxwaitingtime())),
                r.getSearchedSeatsNo(),
                new Point(r.getRidestartPtLon(), r.getRidestartPtLat()),
                new Point(r.getRideendPtLon(), r.getRideendPtLat()),
                r.getPrice(),
                StringEscapeUtils.unescapeHtml(r.getRideComment()),
                StringEscapeUtils.unescapeHtml(r.getStartptAddress()),
                StringEscapeUtils.unescapeHtml(r.getEndptAddress()))) == -1) {
            response = Response.ok(x.toXML(new PostSearchResponse(riderrouteId))).build();
        } else {

            response = Response.ok(x.toXML(new PostSearchResponse(riderrouteId))).build();
        }
        System.out.println("MAXWAITINGTIME = " + r.getMaxwaitingtime());
        return response;
    }

    @DELETE
    @Path("{rideId}")
    public Response deleteSearch(@PathParam("rideId") String rideId) {
        riderUndertakesRideControllerBean.removeRide(Integer.parseInt(rideId));
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Produces("text/json")
    public Response postSearch(@Context HttpServletRequest request, @PathParam("username") String username, String json) {

        if (json != null) {
            // build a List of Objects that shall be available in the JSON context.
            ArrayList list = new ArrayList();
            list.add(new Search());
            list.add(new PostSearchResponse());

            XStream x = Utils.getJasonXStreamer(list);

            Search r = (Search) x.fromXML(json);
            // do something with tht information

            CustomerEntity customer = customerControllerBean.getCustomerByNickname(username);
            int rideId = -1;
            if ((rideId = riderUndertakesRideControllerBean.addRideRequest(
                    customer.getCustId(),
                    new Date(r.getRidestartTimeEarliest()),
                    new Date(r.getRidestartTimeEarliest() + r.getMaxwaitingtime() * 60 * 1000),
                    r.getSearchedSeatsNo(),
                    new Point(r.getRidestartPtLon(), r.getRidestartPtLat()),
                    new Point(r.getRideendPtLon(), r.getRideendPtLat()),
                    r.getPrice(),
                    StringEscapeUtils.unescapeHtml(r.getRideComment()),
                    StringEscapeUtils.unescapeHtml(r.getStartptAddress()),
                    StringEscapeUtils.unescapeHtml(r.getEndptAddress()))) == -1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                Response response = Response.ok(x.toXML(new PostSearchResponse(rideId))).build();

                return response;
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
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

    private CustomerControllerLocal lookupCustomerControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CustomerControllerBean!de.fhg.fokus.openride.customerprofile.CustomerControllerLocal");
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

    private DriverUndertakesRideControllerLocal lookupDriverUndertakesRideControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DriverUndertakesRideControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/DriverUndertakesRideControllerBean!de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CarDetailsControllerLocal lookupCarDetailsControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CarDetailsControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CarDetailsControllerBean!de.fhg.fokus.openride.customerprofile.CarDetailsControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
