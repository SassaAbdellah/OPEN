/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

import org.postgis.Point;

/**
 * Defines Parameters and Values for setting Points and FavoritePoints in JoRide
 * Frontend, such as "lon"/"lat"/"displaystring"/"address".
 *
 * Use as follows
 *
 *
 * To initialize this, set any of the HTTPParameters back/next/finish/cancel to
 * the desired outcome (i.e: the navigation key for the desired
 * next/back/cancel/finish page)
 *
 *
 * You can then use the getNext()/getBack()/getCancel()/getFinish() methods to
 * navigate dynamically through any webflow.
 *
 * use clear() to blank out all parameters
 *
 *
 * Note that this has session view to survive several pages. The backdraw is,
 * that there can only be one webflow at a time.
 *
 *
 *
 * @author jochen
 *
 *
 *
 */
@Named
@SessionScoped
public class WebflowPoint implements Serializable {

    /**
     * Name of the http request parameter that contains the target. The target
     * is used by the target page to determine which point should be updated.
     * (For ex: a RideRequest contains start and endpoint, and the target
     * parameter can be used to determin wether start or endpoint should be set)
     *
     */
    protected static final String PARAM_NAME_TARGET = "target";

    /**
     * Make PARAM_NAME_TARGET available as a bean method.
     *
     * @return
     */
    public String getParamTarget() {
        return PARAM_NAME_TARGET;
    }
    
    
    /** String containing the target
     */
    protected String target=null;
    
    public String getTarget(){
        return target;
    }
    
    
    /** Name of the http request parameter that contains the longitude
     */
    protected static final String PARAM_NAME_LON = "lon";

    /** Make PARAM_NAME_LON availlable as a bean method.
     *
     * @return
     */
    public String getParamLon() {
        return PARAM_NAME_LON;
    }
    
    /** String containing the current value for the longitude.
     */
    protected String lon;

    public String getLon() {
        return lon;
    }
    
    
    /** Name of the http request parameter that contains the latitude
     */
    protected static final String PARAM_NAME_LAT = "lat";

    /**
     * Make PARAM_NAME_LAT available as a bean method.
     *
     * @return
     */
    public String getParamLat() {
        return PARAM_NAME_LAT;
    }
    /**
     * String containing the current value for the latitude.
     *
     */
    protected String lat;

    public String getLat() {
        return lat;
    }
    /**
     * Name of the http request parameter that contains the Displaystring
     */
    protected static final String PARAM_NAME_DISPLAYSTRING = "displaystring";

    /**
     * Make PARAM_NAME_DISPLAYSTRING available as a bean method.
     *
     * @return
     */
    public String getParamDisplaystring() {
        return PARAM_NAME_DISPLAYSTRING;
    }
    /**
     * String containing the current value for the displaystring.
     *
     */
    protected String displaystring;

    public String getDisplaystring() {
        return displaystring;
    }
    /**
     * Name of the http request parameter that contains the Address
     */
    protected static final String PARAM_NAME_ADDRESS = "address";

    /**
     * Make PARAM_NAME_ADDRESS available as a bean method.
     *
     * @return
     */
    public String getParamAddress() {
        return PARAM_NAME_ADDRESS;
    }
    /**
     * String containing the current value for the address.
     *
     */
    protected String address;

    public String getAddress() {
        return address;
    }

    /**
     * Do a smart update, i.e: Check out http request, and overwrite any of the
     * lon/lat/displaystring/address values with a corresponding http request
     * parameter, *provided* the request parameter is !=null.
     *
     */
    public void smartUpdate() {

        HTTPRequestUtil hru = new HTTPRequestUtil();
        
        
        String vTarget = hru.getParameterSingleValue(getParamTarget());
        if (vTarget != null) {
            this.target = vTarget;
        }
        

        String vLon = hru.getParameterSingleValue(getParamLon());
        if (vLon != null) {
            this.lon = vLon;
        }

        String vLat = hru.getParameterSingleValue(getParamLat());
        if (vLat != null) {
            this.lat = vLat;
        }

        String vDisplaystring = hru.getParameterSingleValue(getParamDisplaystring());
        if (vDisplaystring != null) {
            this.displaystring = vDisplaystring;
        }

        String vAddress = hru.getParameterSingleValue(getParamAddress());
        if (vAddress != null) {
            this.address = vAddress;
        }

    } // smartUpdate

    
    
    
    /**
     * clear all values
     */
    public void clear() {

        this.lat = null;
        this.lon = null;
        this.displaystring = null;
        this.address = null;
    }

    /**
     * Returns a postgis point
     *
     */
    Point getPoint() {


        Double latitude;

        try {
            latitude = new Double(this.getLat());
        } catch (java.lang.NumberFormatException exc) {
            System.err.println("Cannot determine numerical latitude from " + this.getLat());
            return null;
        }

        Double longitude;

        try {
            longitude = new Double(this.getLon());
        } catch (java.lang.NumberFormatException exc) {
            System.err.println("Cannot determine numerical longitude from " + this.getLat());
            return null;
        }

        return new Point(longitude, latitude);
    }
} // class