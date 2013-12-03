/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import de.avci.joride.utils.HTTPUtil;
import de.avci.joride.utils.WebflowPoint;
import de.fhg.fokus.openride.rides.driver.WaypointEntity;
import de.fhg.fokus.openride.routing.RoutePoint;
import java.util.logging.Level;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/** Java Class making Waypoints accessible
 *  to frontend as a JSFBean
 * 
 *  This is session scoped, since it is supposed to model 
 *  a newly created waypoint, which live over several pages.
 *
 *  @author jochen
 */

@Named("waypoint")
@SessionScoped

/** Frontend to Waypoint Entity as a JSF Bean.
 * 
 */
public class JWaypointEntity extends WaypointEntity{
    
    /** Parametername for rideId. Used when doing a smart update.
     */
    public static final String PARAM_NAME_RIDEID="rideId";
    
    /**
     * @return  parameter name for rideId Parameter
     */
    public String getParamNameRideId(){return PARAM_NAME_RIDEID;}
    
    /** Parametername for position. Used when doing a smart update.
     */
    public static final String PARAM_NAME_POSITION="position";
    
    
    /**
     * @return parameter name position
     */
    public String getParamNamePosition(){return PARAM_NAME_POSITION;}
    
    
    /**
     *  Clear all fields, set parameters
     *  longitude, latitude, description,
     *  position and rideId from parameters.
     */
    
    public void smartUpdate(){
    
        //
        // retrieve point coordinates and descriptions
        // via webflow Point
        //
        WebflowPoint webflowPoint = new WebflowPoint();
        webflowPoint.smartUpdate();
        
        this.setLongitude(webflowPoint.getLon());
        this.setLatitude(webflowPoint.getLat());
        this.setDescription(webflowPoint.getDisplaystring());
        //
        // set position parameter and rideIDs 
        // 
        HTTPUtil hru = new HTTPUtil();
        String positionS = hru.getParameterSingleValue(getParamNamePosition());
        try{ this.setPosition(new Float(positionS));
        } catch(Exception exc){
            exc.printStackTrace(System.err);
        }
        String  rideIdS=hru.getParameterSingleValue(getParamNameRideId());
        try{ this.setRideId(new Integer(rideIdS));
        } catch(Exception exc){
            exc.printStackTrace(System.err);
        }
        
    } // smartUpdate
    
    
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
    private Float position=new Float(Integer.MAX_VALUE);
    
    public Float getPosition(){
        return this.getPosition();
    }
    
    public void setPosition(float position){
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
