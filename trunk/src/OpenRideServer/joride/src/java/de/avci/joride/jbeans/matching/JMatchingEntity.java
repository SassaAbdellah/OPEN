/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.matching;

import de.avci.joride.jbeans.customerprofile.JPublicCustomerProfile;
import de.avci.joride.jbeans.driverundertakesride.JDriverUndertakesRideEntity;
import java.io.Serializable;

import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntity;
import de.avci.joride.utils.HTTPRequestUtil;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.fhg.fokus.openride.matching.MatchEntity;
import java.awt.event.ActionEvent;
import javax.servlet.http.HttpUtils;

/**
 * Wrapper making MatchingEntity available as a CDI Bean for use in JSF
 * frontend.
 *
 * @author jochen
 *
 */
@Named
@SessionScoped
public class JMatchingEntity implements Serializable {

    /**
     * HTTPRequest Parameter to tell JMatchingEntity the rider Id (id of offer)
     * of this request.
     */
    protected static String PARAM_NAME_rideId = "rideid";

    /**
     * Accessor for the PARAM_NAME_riderId parameter
     *
     * @return
     */
    public String getParamRideID() {
        return this.PARAM_NAME_rideId;
    }
    /**
     * HTTPRequest Parameter to tell JMatchingEntity the ridererrouteId (id of
     * request) of this request.
     *
     */
    protected static String PARAM_NAME_riderrouteId = "riderrouteid";

    /**
     * Accessor for the PARAM_NAME_riderrouteId parameter
     *
     * @return
     */
    public String getParamRiderrouteId() {
        return this.PARAM_NAME_riderrouteId;
    }
    /**
     * The Match Entity that this Object is build around.
     */
    private MatchEntity matchEntity = null;

    public MatchEntity getMatchEntity() {
        return this.matchEntity;
    }

    /**
     * Non trivial setter! -- In addition to setting the MatchEntity property,
     * it does also blank out the Drive and Ride properties, so that lazy
     * instantiation will renew them.
     *
     * @param arg
     */
    public void setMatchEntitiy(MatchEntity arg) {

        this.matchEntity = arg;
        this.drive = null;
        this.ride = null;

    }
    /**
     * Representation of the matchEntities riderUndertakesRideEntity prop. This
     * is created via lazy instantiation.
     *
     */
    private JRiderUndertakesRideEntity ride = null;

    /**
     * Accessor with lazy instantiation
     *
     * @return
     */
    public JRiderUndertakesRideEntity getRide() {

        if (ride == null) {
            ride = new JRiderUndertakesRideEntity();
            ride.updateFromRiderUndertakesRideEntity(matchEntity.getRiderUndertakesRideEntity());
        }

        return ride;
    }
    /**
     * Representation of the matchEntities driverUndertakesRideEntity prop. This
     * is created via lazy instantiation.
     *
     */
    private JDriverUndertakesRideEntity drive = null;

    /**
     * Accessor with lazy instantiation
     *
     * @return
     */
    public JDriverUndertakesRideEntity getDrive() {

        if (drive == null) {
            drive = new JDriverUndertakesRideEntity();
            drive.updateFromDriverUndertakesRideEntity(matchEntity.getDriverUndertakesRideEntity());
        }

        return drive;
    }

    /**
     * Get Driver State in it's integer representation.
     *
     * @return the driver state
     *
     */
    public Integer getDriverState() {
        return this.getMatchEntity().getDriverState();
    }

    /**
     * Get Rider State in it's integer representation.
     *
     * @return the rider state
     *
     */
    public Integer getRiderState() {
        return this.getMatchEntity().getRiderState();
    }

    /**
     * Accept Driver for this match. This methods attempts to be save, i.e
     * checks if the caller is in role to accept match.
     */
    public void acceptDriver(ActionEvent evt) {
        new JMatchingEntityService().acceptDriverSafely(this);
    }
    
    /**
     * Reject Driver for this match. This methods attempts to be save, i.e
     * checks if the caller is in role to accept match.
     */
    public void rejectDriver(ActionEvent evt) {
        new JMatchingEntityService().rejectDriverSafely(this);
    }

    
    
    

    /**
     * Accept Rider for this match. This methods attempts to be save, i.e checks
     * if the caller is in role to accept match.
     */
    public void acceptRider(ActionEvent evt) {
        new JMatchingEntityService().acceptRiderSafely(this);
    }
    
    
    
    /**
     * Reject Rider for this match. This methods attempts to be save, i.e checks
     * if the caller is in role to accept match.
     */
    public void rejectRider(ActionEvent evt) {
        new JMatchingEntityService().rejectRiderSafely(this);
    }
    
    
    
    
    
    

    /**
     * Create a new JMatchingEntity from a real matchingEntity
     *
     * @param arg
     */
    JMatchingEntity(MatchEntity arg) {
        this.matchEntity = arg;
    }

    /**
     * Update from parameters given in HTTPRequest, i.e evaluate riderId and
     * riderrouteId parameter, get match (if possible) and update data from
     * match.
     *
     */
    public void smartUpdate() {

        HTTPRequestUtil hru = new HTTPRequestUtil();
        String rideIdStr = hru.getParameterSingleValue(PARAM_NAME_rideId);
        Integer rideIdArg = new Integer(rideIdStr);

        String riderrouteIdStr = hru.getParameterSingleValue(PARAM_NAME_riderrouteId);
        Integer riderrouteIdArg = new Integer(riderrouteIdStr);


        MatchEntity me = new JMatchingEntityService().getMatchSafely(rideIdArg, riderrouteIdArg);

        this.setMatchEntitiy(me);

    }

    /**
     * Provides rider's data visible before a ride had been accepted upon
     */
    public JPublicCustomerProfile getPublicRiderData() {

        JPublicCustomerProfile res = new JPublicCustomerProfile();
        res.updateFromCustomerEntity(this.getRide().getCustId());
        return res;
    }

    /**
     * Provides driver's data visible before a ride had been accepted upon
     */
    public JPublicCustomerProfile getPublicDriverData() {

        JPublicCustomerProfile res = new JPublicCustomerProfile();
        res.updateFromCustomerEntity(this.getDrive().getCustId());
        return res;
    }

    /**
     * Bean constructor
     */
    public JMatchingEntity() {
    }
} // class 
