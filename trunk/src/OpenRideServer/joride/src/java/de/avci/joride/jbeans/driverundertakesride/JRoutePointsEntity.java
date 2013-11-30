/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import java.io.Serializable;

import de.fhg.fokus.openride.rides.driver.RoutePointEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

/**
 * Wrapper for list of Routpoints, making them available as a JSF Bean, and
 * offering some convenience methods to view them in OpenLayers and friends.
 *
 * @author jochen
 */
public class JRoutePointsEntity implements Serializable {

    /**
     * the point classified as the startPoint of the journey.
     */
    private RoutePointEntity startPoint;
    /**
     * the point classified as the endPoint of the journey.
     */
    private RoutePointEntity endPoint;
    /**
     * List of points where riders get picked up
     */
    private List<RoutePointEntity> pickupRiderPoints;
    /**
     * List of points where riders get dropped
     */
    private List<RoutePointEntity> dropRiderPoints;
    /**
     * List of Routepoints for given Ride or Drive
     *
     */
    private List<RoutePointEntity> routePoints;

    public List<RoutePointEntity> getRoutePoints() {
        return this.routePoints;
    }

    /**
     * Nontrivial (!) Setter -- triggers implicite call to {
     *
     * @see initializePoints}
     *
     * @param arg
     */
    public void setRoutePoints(List<RoutePointEntity> arg) {
        this.routePoints = arg;
        initializePoints();
    }

    /**
     * Get a string representation of this
     *
     * @param rpe routepoint to be encoded
     * @return
     */
    private StringBuffer getRoutePointAsJSON(RoutePointEntity rp) {

        StringBuffer buf = new StringBuffer();

        buf.append("[");
        buf.append(rp.getLongitude());
        buf.append(",");
        buf.append(rp.getLatitude());
        buf.append("]");

        return buf;
    }

    /**
     * Return reprensentation of the routepoints as a JSON String, i.e:
     *
     * @return
     */
    private StringBuilder getListOfRoutePointsAsJSON(List<RoutePointEntity> rpl) {

        Iterator<RoutePointEntity> it = rpl.iterator();

        StringBuilder bui = new StringBuilder();
        bui.append("\n[");

        while (it.hasNext()) {
            RoutePointEntity rp = it.next();
            bui.append("\n");
            bui.append(getRoutePointAsJSON(rp));
            if (it.hasNext()) {
                bui.append(",");
            }
        } // while it.hasNext()

        bui.append("\n]");

        return bui;
    }

    /**
     *
     * @return list of all routepoints encoded in a json string
     */
    public String getRoutePointsAsJSON() {
        return this.getListOfRoutePointsAsJSON(this.getRoutePoints()).toString();
    }

    /**
     *
     * @return {@link getDropRiderPoints} converted to json
     */
    public String getDropRiderPointsAsJSON() {
        return this.getListOfRoutePointsAsJSON(this.getDropRiderPoints()).toString();
    }

    /**
     *
     * @return {@link getPickupRiderPoints} converted to json
     */
    public String getPickupRiderPointsAsJSON() {
        return this.getListOfRoutePointsAsJSON(this.getPickupRiderPoints()).toString();
    }
    
    
    public String getStartPointAsJSON(){
        return this.getRoutePointAsJSON(this.getStartPoint()).toString();
    }
    
    public String getEndPointAsJSON(){
        return this.getRoutePointAsJSON(this.getEndPoint()).toString();
    }
    
    
    

    public RoutePointEntity getStartPoint() {
        return startPoint;
    }

    public RoutePointEntity getEndPoint() {
        return endPoint;
    }

    public List<RoutePointEntity> getDropRiderPoints() {
        return this.dropRiderPoints;
    }

    public List<RoutePointEntity> getPickupRiderPoints() {
        return this.pickupRiderPoints;
    }

    /**
     * Initialize/Classify the list of route points into startPoint, endPoint,
     * pickupPoints and dropPoints
     *
     */
    private void initializePoints() {

        List<RoutePointEntity> rpes = this.getRoutePoints();

        this.startPoint = rpes.get(0);
        this.endPoint = rpes.get(rpes.size() - 1);

        this.pickupRiderPoints = new ArrayList<RoutePointEntity>();
        this.dropRiderPoints = new ArrayList<RoutePointEntity>();


        HashSet<Integer> rides = new HashSet<Integer>();

        for (RoutePointEntity rpe : rpes) {

                boolean isRequired=rpe.isRequired();
                Integer riderRouteId = rpe.getRiderrouteId();
                             

                if (isRequired && riderRouteId!= null) {
                    if (!(rides.contains(riderRouteId))) {
                        this.pickupRiderPoints.add(rpe);
                    } else {
                        this.dropRiderPoints.add(rpe);
                    }

                    rides.add(riderRouteId);
                }
         
        }
    } // initializePoints
}
