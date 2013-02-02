/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.riderundertakesride;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.matching.MatchEntity;
import de.fhg.fokus.openride.matching.RouteMatchingBeanLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.postgis.Point;

/**
 * Wrapper for RiderUndertakesRideEntityService in OpenRideServer-ejb.
 *
 * @author jochen
 */
public class JRiderUndertakesRideEntityService {
    
    
    
    Logger logger=Logger.getLogger(""+this.getClass());

    /**
     * Get a customerEntity from the current request
     *
     * @return
     */
    public CustomerEntity getCustomerEntity() {
        return (new JCustomerEntityService()).getCustomerEntitySafely();
    }

    /**
     * Lookup RiderUndertakesRideControllerLocal Bean that controls my requests.
     *
     * @return
     */
    protected RiderUndertakesRideControllerLocal lookupRiderUndertakesRideControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RiderUndertakesRideControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RiderUndertakesRideControllerBean!de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }

    }

    /**
     * Get a list all Drives of this driver. Current user/customer is determined
     * from HTTPRequest's AuthPrincipal.
     *
     * @return
     */
    public List<RiderUndertakesRideEntity> getRidesForRider() {


        CustomerEntity ce = this.getCustomerEntity();
        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();


        if (ce == null) {
            throw new Error("Cannot determine Rides, customerEntity is null");
        }

        if (ce.getCustNickname() == null) {
            throw new Error("Cannot determine Rides, customerNickname is null");
        }


        // get all rides related to this customer
        return rurcl.getRidesForCustomer(ce);



    }

    /**
     * Savely get the Ride with given riderRouteId.
     *
     * Current user/customer is determined from HTTPRequest's AuthPrincipal.
     *
     * @return
     */
    public RiderUndertakesRideEntity getRideByRiderRouteIdSavely(int myRiderRouteId) {


        CustomerEntity ce = this.getCustomerEntity();
        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();


        if (ce == null) {
            throw new Error("Cannot determine Ride, customerEntity is null");
        }

        if (ce.getCustNickname() == null) {
            throw new Error("Cannot determine Ride, customerNickname is null");
        }


        RiderUndertakesRideEntity rure = rurcl.getRideByRiderRouteId(myRiderRouteId);



        if (rure.getCustId().getCustId() != ce.getCustId()) {
            throw new Error("Cannot retrieve Drive with given ID, object does not belong to user");
        }

        return rure;

    } //  getDriveByIdSavely(int id)

    /**
     * Savely update JRiderUndertakesRideEntity from database
     *
     *
     *
     * @param riderRouteId ride Id from the DriverUndertakesRideEntity providing
     * the data. If this is null, simply no update will be done.
     *
     * @param jdure JDriverUndertakesRideEntity to be updated with data from
     * database
     */
    public void updateJRiderUndertakesRideEntityByRiderRouteIDSavely(Integer riderRouteId, JRiderUndertakesRideEntity jrure) {


        if (riderRouteId == null) {
            // nothing to do in that case!
            return;
        }


        RiderUndertakesRideEntity rure = this.getRideByRiderRouteIdSavely(riderRouteId);
        // update
        jrure.updateFromRiderUndertakesRideEntity(rure);
    }

    /**
     * Add new RideRequest to the Database, generates and returns the ID of the
     * so created ride request.
     */
    public int addRideRequest(JRiderUndertakesRideEntity jrure) {


        CustomerEntity ce = this.getCustomerEntity();


        if (ce == null) {
            throw new Error("Cannot persist Rides, customerEntity is null");
        }

        if (ce.getCustId() == null) {
            throw new Error("Cannot determine Rides, customerId is null");
        }

        // null comments may cause nullpointer trouble, so clean it here
        jrure.cleanseComment();
        // null prices may cause nullpointer trouble, so clean it here
        jrure.cleansePrice();


        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();

        return rurcl.addRideRequest(
                // int cust_id, 
                ce.getCustId(),
                //Date starttime_earliest, 
                jrure.getStarttimeEarliest(),
                //Date starttimeLatest, 
                jrure.getStarttimeLatest(),
                // int noPassengers, 
                jrure.getNoPassengers(),
                //Point startpt, 
                jrure.getStartpt(),
                //Point endpt, 
                jrure.getEndpt(),
                //double price, 
                jrure.getPrice(),
                //String comment, 
                jrure.getComment(),
                //String startptAddress, 
                jrure.getStartptAddress(),
                //String endptAddress
                jrure.getEndptAddress());
    }

    /**
     * Return a list of *recent* rides, of this user i.e: Rides for which the
     * "lastStartTime" value is still in the future. and which are not booked.
     *
     * @return
     */
    public List<RiderUndertakesRideEntity> getActiveOpenRides() {


        CustomerEntity ce = this.getCustomerEntity();
        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();


        if (ce == null) {
            throw new Error("Cannot determine Rides, customerEntity is null");
        }


        if (ce.getCustNickname() == null) {
            throw new Error("Cannot determine Rides, customer's nickname is null");
        }


        return rurcl.getActiveOpenRides(ce.getCustNickname());

    } // getActiveOpenRides

    /**
     * Savely remove the Ride with given riderRouteId.
     *
     * Current user/customer is determined from HTTPRequest's AuthPrincipal.
     *
     * @return true, if the ride has been removed, else false.
     */
    public boolean removeRideSafely(JRiderUndertakesRideEntity jrure) {


     
        CustomerEntity ce = this.getCustomerEntity();

        if (ce == null) {
            throw new Error("Cannot determine Ride for removal, calling customerEntity is null");
        }


        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();

        if (rurcl == null) {
            throw new Error("Cannot determine RiderUndertakesRideControllerLocal");
        }

        
       if (jrure == null) {
            throw new Error("Cannot remove ride, argument is null");
        }

      
       if(jrure.getRiderrouteId()==null){
           throw new Error("Cannot remove ride, riderrouteId is null");
      }
    
       
       
        
        
        int riderrouteId=jrure.getRiderrouteId();
        
        RiderUndertakesRideEntity rure = rurcl.getRideByRiderRouteId(riderrouteId);

        
        
        if (rure == null) {
            throw new Error("Cannot remove ride with id " + jrure.getRiderrouteId() + ", ride is null!");
        }


        if (rure.getCustId().getCustId() != ce.getCustId()) {
            throw new Error("Cannot retrieve Ride with given ID for removal, object does not belong to user");
        }

        return rurcl.removeRide(rure.getRiderrouteId());

    } //  getDriveByIdSavely(int id)

    /**
     * Return a list of *recent* rides, of this user i.e: Rides for which the
     * "lastStartTime" value is still in the future. and which are not booked.
     * The user gets determined from the HttpServletRequest's remoteUser, thus
     * this method can be considered to be save.
     *
     * @return
     */
    public List<RiderUndertakesRideEntity> getActiveOpenRides(HttpServletRequest request) {

        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();
        return rurcl.getActiveOpenRides(request.getRemoteUser());

    } // getActiveOpenRides

    /**
     * Returns true, if the ride given by riderrouteId has been updated, else
     * false. This is just a small wrapper to
     * RiderUndertakesRideControllerBeanLocal.isRideUpdated(...)
     *
     * @param riderrouteId
     * @return
     */
    public boolean isRideUpdated(Integer riderrouteId) {
        return lookupRiderUndertakesRideControllerBeanLocal().isRideUpdated(riderrouteId);
    }
    
    
    
   

    /**
     * Invalidate/cancel/countermand the ride with given Rideid. The identity is
     * checked from http request. 
     *
     * @param rideId id of the ride to be invalidated.
     *
     * @return true if ride was invalidated, else false.
     */
    public boolean invalidateRequestSavely(int riderrouteId) {


        CustomerEntity ce = this.getCustomerEntity();


        RiderUndertakesRideControllerLocal rurcl = this.lookupRiderUndertakesRideControllerBeanLocal();
        RiderUndertakesRideEntity rue = rurcl.getRideByRiderRouteId(riderrouteId);

        // Sanity check, caller of this method must be owner of this offer

        if (ce.getCustId() != rue.getCustId().getCustId()) {
            throw new Error("Attempt to invalidate request that is not owned by User");
        }

        if (rurcl.isDeletable(riderrouteId)) {
            logger.info("Offer " + riderrouteId + " is deleteable, removing it");
            rurcl.removeRide(riderrouteId);
        } else {
            logger.info("Request " + riderrouteId + " is not deleteable, invalidating");
            rurcl.invalidateRide(riderrouteId);
        }

        return true;
    }
    
    
    
} // class
