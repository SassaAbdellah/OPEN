/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils.geocoding.osm;


import de.avci.joride.utils.PropertiesLoader;
import java.util.Properties;

/** Some constants for OpenStreetmap related stuff
 *
 * @author jochen
 */
public class OSMConstants {
    
    
    /** Parameter Name for Passing Longitude
     */
    public static final String PARAM_NAME_longitude="lon";
    
    
     /** Parameter Name for Passing Latitude
     */
    public static final String PARAM_NAME_latitude="lat";
    
    /** Parameter Name for Passing the DisplayString
     */
    public static final String PARAM_NAME_displayStr="displayStr";
    
       
    /** Parameter Name for Passing the CallerID
     */
    public static final String PARAM_NAME_callerID="callerID";
    
    
     /** Parameter Name for where to return
     */
    public static final String PARAM_NAME_returnAddress="returnAddr";
    
    
    
    /** Where the map for OpenStreetMap is displayed
     *  Note, that this might be evaluated by the paranoid 
     *  webkit javascript, so we rather escape the '.' here.
     */
    public static final String OSMMAP="osmmap\\.xhtml";
      
    
} 
