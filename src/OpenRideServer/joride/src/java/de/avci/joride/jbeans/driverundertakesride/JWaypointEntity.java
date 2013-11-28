/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import de.fhg.fokus.openride.rides.driver.WaypointEntity;

/** Java Class making Waypoints accessible
 *  to frontend as a JSFBean
 * 
 * @author jochen
 */
public class JWaypointEntity extends WaypointEntity{
    
    /** Encode this waypoint as a 
     *  JSON *Array* compatible with
     *  the representation of {@link JRoutePointEntity}
     *  that is: [longitude,latitude,id,'description']
     * 
     * @return  representation of this object as a json array
     * 
     */
    public StringBuffer getJSON(){
        StringBuffer buf=new StringBuffer();
        buf.append("[");
        buf.append(this.getLongitude());
        buf.append(",");
        buf.append(this.getLongitude());
        buf.append(",");
        buf.append(this.getId());
        buf.append(",");
        buf.append("'");
        buf.append(this.getDescription());
        buf.append("'");
        buf.append("]");
        return buf;
    }
    
    
    /** Create Waypoint Entity from it's superclass,
     *  copying longitude,latitude,rideId,routeIdx and description.
     * 
     */
    public JWaypointEntity(WaypointEntity  w){
    
        super();
        this.setLongitude(w.getLongitude());
        this.setLatitude(w.getLatitude());
        this.setRideId(w.getRideId());
        this.setRouteIdx(w.getRouteIdx());
        this.setDescription(w.getDescription());
    }
    
}
