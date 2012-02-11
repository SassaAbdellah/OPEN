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

package de.fhg.fokus.openride.services.configuration;

import com.thoughtworks.xstream.XStream;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.Utils;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import de.fhg.fokus.openride.services.configuration.helperclasses.InitResponse;
import de.fhg.fokus.openride.services.configuration.helperclasses.UpdateResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author pab, tku
 */
@Path("configuration")
public class Configuration {

    RiderUndertakesRideControllerLocal riderUndertakesRideControllerBean = lookupRiderUndertakesRideControllerBeanLocal();
    DriverUndertakesRideControllerLocal driverUndertakesRideControllerBean = lookupDriverUndertakesRideControllerBeanLocal();
    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();
    private String imgPath = "/OpenRideWeb/pictures/profile/";

    @GET
    @Produces("text/json")
    @Path("init")
    public Response getConfiguration(@Context HttpServletRequest request) {
        System.out.println("getinit");
        String nickname = request.getRemoteUser();
        
        if (nickname != null) {


            CustomerEntity customer = customerControllerBean.getCustomerByNickname(nickname);


            int updatedoffers = 0;
            int updatedsearches = 0;
            int openratings = 0;
            int newratings = 0;

            
            // Open offers  FIXME: maybe Opensearches should be theses, which have not been started or where no partner has yet been found?
            List<DriverUndertakesRideEntity> openoffers = driverUndertakesRideControllerBean.getActiveDrives(nickname);

            // Updated offers
            //List<DriverUndertakesRideEntity> drives = driverUndertakesRideControllerBean.getActiveOpenDrives(nickname);
            for (DriverUndertakesRideEntity drive : openoffers) {
                if (driverUndertakesRideControllerBean.isDriveUpdated(drive.getRideId())) {
                    updatedoffers++;
                }
            }

            // Open searches   FIXME: maybe Opensearches should be theses, which have not been started or where no partner has yet been found?
            List<RiderUndertakesRideEntity> opensearches = riderUndertakesRideControllerBean.getActiveRideRequests(nickname);

            // Updated searches
            //List<RiderUndertakesRideEntity> activeRides = riderUndertakesRideControllerBean.getActiveOpenRides(nickname);
            for (RiderUndertakesRideEntity r: opensearches) {
                if (riderUndertakesRideControllerBean.isRideUpdated(r.getRiderrouteId())) {
                    updatedsearches++;
                }
            }

            // Open ratings
            List<RiderUndertakesRideEntity> unratedRidesAsRider;
            List<RiderUndertakesRideEntity> unratedRidesAsDriver;
            unratedRidesAsRider = (List<RiderUndertakesRideEntity>) riderUndertakesRideControllerBean.getRidesWithoutGivenRatingByRider(customer);
            unratedRidesAsDriver = (List<RiderUndertakesRideEntity>) riderUndertakesRideControllerBean.getRidesWithoutReceivedRatingByDriver(customer);
            openratings = unratedRidesAsRider.size() + unratedRidesAsDriver.size();


            // Ratings (received)

            // TODO: Received since when? -> e.g., 7 days -> need bean method
            newratings = 0;
            
            InitResponse resp = new InitResponse(
                    nickname,                    
                    openoffers.size(),
                    opensearches.size(),
                    updatedoffers,
                    updatedsearches,
                    openratings,
                    newratings,
                    imgPath + nickname + "_" + customer.getCustId() + ".jpg");


            ArrayList list = new ArrayList();
            list.add(new InitResponse());            

            XStream x = Utils.getJasonXStreamer(list);
            Response response = Response.ok(x.toXML(resp)).build();
            
            return response;
            
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Produces("text/json")
    @Path("updates")
    public Response getUpdates(@Context HttpServletRequest request) {
        System.out.println("getUpdates");
        String nickname = request.getRemoteUser();

        if (nickname != null) {


            CustomerEntity customer = customerControllerBean.getCustomerByNickname(nickname);


            int updatedoffers = 0;
            int updatedsearches = 0;

            // Open offers  FIXME: maybe Opensearches should be theses, which have not been started or where no partner has yet been found?
            List<DriverUndertakesRideEntity> openoffers = driverUndertakesRideControllerBean.getActiveDrives(nickname);

            // Updated offers
            for (DriverUndertakesRideEntity drive : openoffers) {
                if (driverUndertakesRideControllerBean.isDriveUpdated(drive.getRideId())) {
                    updatedoffers++;
                }
            }

            // Open searches   FIXME: maybe Opensearches should be theses, which have not been started or where no partner has yet been found?
            List<RiderUndertakesRideEntity> opensearches = riderUndertakesRideControllerBean.getActiveRideRequests(nickname);

            // Updated searches
            for (RiderUndertakesRideEntity r: opensearches) {
                if (riderUndertakesRideControllerBean.isRideUpdated(r.getRiderrouteId())) {
                    updatedsearches++;
                }
            }

            UpdateResponse resp = new UpdateResponse(
                    updatedoffers,
                    updatedsearches);

            ArrayList list = new ArrayList();
            list.add(new UpdateResponse());

            XStream x = Utils.getJasonXStreamer(list);
            Response response = Response.ok(x.toXML(resp)).build();

            return response;

        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    private ArrayList<DriverUndertakesRideEntity> getOffers(String nickname) {
        return new ArrayList(driverUndertakesRideControllerBean.getDrives(nickname));
    }

    private ArrayList<RiderUndertakesRideEntity> getSearches(String nickname) {
        return new ArrayList(riderUndertakesRideControllerBean.getRides(nickname));
    }

    @PUT
    @Produces("text/json")
    @Path("/signoff/")
    public Response putConfiguration(@Context HttpServletRequest request) {
        System.out.println("Logout: " + request.getRemoteUser());
        
        request.getSession().invalidate();
        System.out.println("Logout: " + request.getRemoteUser());

        return Response.ok().build();
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
}
