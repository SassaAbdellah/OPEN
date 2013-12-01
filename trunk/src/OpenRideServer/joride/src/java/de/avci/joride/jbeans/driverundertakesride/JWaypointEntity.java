/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import de.fhg.fokus.openride.rides.driver.WaypointEntity;
import de.fhg.fokus.openride.routing.RoutePoint;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/** Java Class making Waypoints accessible
 *  to frontend as a JSFBean
 * 
 * 
 * 
 *  @author jochen
 */

@Named("waypoint")
@RequestScoped

/** Frontend to Waypoint Entity as a JSF Bean.
 * 
 */
public class JWaypointEntity extends WaypointEntity{
    
    
    /** JWaypoint Entity adds a volatile position parameter to WaypointEntity,
     *  
     *  This is used *only* for finding the routeIdx of a newly created waypoint.
     *  
     *  I.e a newly created waypoint "W_new" is added just before the first 
     *  existing waypoint having routeIdx larger than w.position.
     * 
     * 
     *  Position defaults to Integer.MAX_VALUE so that newly created 
     *  waypoints without other information are added to the 
     *  end of the ridepoints list.
     * 
     */
    private Double position=new Double(Integer.MAX_VALUE);
    
    public Double getPosition(){
        return this.getPosition();
    }
    
    public void setPosition(double position){
        this.position=position;
    }
    
    
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
