/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.jbeans.driverundertakesride.JDriverUndertakesRideEntityService;
import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntityService;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Simple Update Service providing information about updated requests and offers
 * to the Webclient.
 *
 *
 * @deprecated -- note, that this is currently unused, but may be reactivated
 * when RESTFUL interface will be enabled
 *
 *
 *
 *
 * @author jochen
 */
@Path("protected/update")
@Produces("text/json")
public class UpdateService {

    @GET
    @Path("text")
    /**
     * Produces a JSON Object describing the updated requests for the user given
     * as remote user.
     *
     * Example for JSON comes here:
     *
     * {"UpdateResponse":{"updatedoffers":0,"updatedsearches":0}} TODO: get
     * better Documentation for JSON Object (WADL?)
     *
     *
     * Service is to be found at an URL like that:
     * http://host:port/joride/jax-rs/update/
     *
     *
     */
    public String getUpdateText(@Context HttpServletRequest request) {

        if (request.getRemoteUser() == null) {
            throw new Error("Cannot determine update, request's remoteuser is null");
        }

        CustomerEntity ce = (new JCustomerEntityService()).getCustomerEntityFromRequest(request);

        int updatedoffersCount = 0;
        int updatedsearchesCount = 0;


        JDriverUndertakesRideEntityService driverUndertakesRideEntityService = new JDriverUndertakesRideEntityService();

        List<DriverUndertakesRideEntity> openoffers =
                driverUndertakesRideEntityService.getActiveDrivesForDriver(request);

        // Updated offers for (DriverUndertakesRideEntity drive : openoffers)

        for (DriverUndertakesRideEntity drive : openoffers) {
            if (driverUndertakesRideEntityService.isDriveUpdated(drive.getRideId())) {
                updatedoffersCount++;
            }
        }


        JRiderUndertakesRideEntityService jRiderUndertakesRideEntityService =
                new JRiderUndertakesRideEntityService();


        // Open searches FIXME: maybe Opensearches should be theses, which
        // have not been started or where no partner has yet been found?
        List<RiderUndertakesRideEntity> opensearches =
                jRiderUndertakesRideEntityService.getActiveOpenRides(request);

        // Updated searches for (RiderUndertakesRideEntity r : opensearches)


        for (RiderUndertakesRideEntity ride : opensearches) {
            if (jRiderUndertakesRideEntityService.isRideUpdated(ride.getRiderrouteId())) {
                updatedsearchesCount++;
            }
        }


        String UpdateResponse = JoRideConstants.PARAM_NAME_UPDATE_RESPONSE;
        String NoUpdateOffers = JoRideConstants.PARAM_NAME_NO_UPDATED_OFFERS;
        String NoUpdateRequests = JoRideConstants.PARAM_NAME_NO_UPDATED_REQUESTS;



        // For simplicity, we build the JSON Response "byHand" here //
        String result = "{\"" + UpdateResponse + "\":{\"" + NoUpdateOffers + "\":" + updatedoffersCount + ",\"" + NoUpdateRequests + "\":" + updatedsearchesCount + "}}";

        return result;

    }

    @GET
    @Path("gson")
    /**
     * GSON test harness
     * 
     * Returns a list of all **offers** for this user, that have received an
     * update "List" here means list in JSON format.
     */
    public String getUpdatedOffersAsGSON(@Context HttpServletRequest request) {

        if (request.getRemoteUser() == null) {
            throw new Error("Cannot determine update, request's remoteuser is null");
        }

        CustomerEntity ce = (new JCustomerEntityService()).getCustomerEntityFromRequest(request);

        JDriverUndertakesRideEntityService driverUndertakesRideEntityService = new JDriverUndertakesRideEntityService();

        List<DriverUndertakesRideEntity> openoffers = driverUndertakesRideEntityService.getActiveDrivesForDriver(request);

        // Updated offers for (DriverUndertakesRideEntity drive : openoffers)

        List<DriverUndertakesRideEntity> resultLists = new ArrayList<DriverUndertakesRideEntity>();


        for (DriverUndertakesRideEntity drive : openoffers) {
            if (driverUndertakesRideEntityService.isDriveUpdated(drive.getRideId())) {
                resultLists.add(drive);
            }
        }

        return new Gson().toJson(resultLists);
    }

    @GET
    @Path("jackson")
    /**
     * GSON test harness
     * 
     * Returns a list of all **offers** for this user, that have received an
     * update "List" here means list in JSON format.
     */
    public String getUpdatedOffersAsJACKSON(@Context HttpServletRequest request)  {

        if (request.getRemoteUser() == null) {
            throw new Error("Cannot determine update, request's remoteuser is null");
        }

        CustomerEntity ce = (new JCustomerEntityService()).getCustomerEntityFromRequest(request);

        JDriverUndertakesRideEntityService driverUndertakesRideEntityService = new JDriverUndertakesRideEntityService();

        List<DriverUndertakesRideEntity> openoffers = driverUndertakesRideEntityService.getActiveDrivesForDriver(request);

        // Updated offers for (DriverUndertakesRideEntity drive : openoffers)

        List<DriverUndertakesRideEntity> resultLists = new ArrayList<DriverUndertakesRideEntity>();


        for (DriverUndertakesRideEntity drive : openoffers) {
            if (driverUndertakesRideEntityService.isDriveUpdated(drive.getRideId())) {
                resultLists.add(drive);
            }
        }
        try {
            return new ObjectMapper().writeValueAsString(resultLists);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error(ex);
        
        }
    }

   


    /**
     * Root webservices must have a default constructor!
     *
     */
    public UpdateService() {
    }
}
