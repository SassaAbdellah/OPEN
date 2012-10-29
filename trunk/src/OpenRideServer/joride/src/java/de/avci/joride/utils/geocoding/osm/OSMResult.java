/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils.geocoding.osm;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;

/** Models the result of an OSM Query
 *
 *
 * @author jochen
 */
@Named("osmmresult")
@SessionScoped


public class OSMResult implements Serializable {
    
    /** Property for the longitude 
     */
    protected String longitude=null;
    
    /** Trivial Setter
     */
    public void setLongitude(String arg){
        this.longitude=arg;
    }
    
    /** Trivial Getter
     */
    public String getLongitude(){
        return this.longitude;
    }
    
    
    
    
    
    /** Property for the latitude 
     */
    protected String latitude=null;
    
    /** Trivial Setter
     */
    public void setLatitude(String arg){
        this.latitude=arg;
    }
    
    /** Trivial Getter
     */
    public String getLatitude(){
        return this.latitude;
    }
    
    
    /** Display String returned by the geocoding service
     *  for *this* result.
     */
    protected String displayString=null;
    
    /** Trivial Getter
     */
    public String getDisplayString(){return this.displayString;}
    
    /** Trivial Getter
     */
    public void setDisplayString(String arg){this.displayString=arg;}
   
    
    /** callerID passed from the query to *this* result.
     */
    protected String callerID=null;
    
    /** Trivial Getter
     */
    public String getCallerID(){return this.callerID;}
    
    /** Trivial Getter
     */
    public void setCallerID(String arg){this.callerID=arg;}
   
    
    
    
    
    
    
    /** return address passed from the query to *this* result.
     */
    protected String returnAddress=null;
    
    /** Trivial Getter
     */
    public String getReturnAddress(){return this.returnAddress;}
    
    /** Trivial Getter
     */
    public void setReturnAddres(String arg){this.returnAddress=arg;}
   
    
    
    
   
    
    /** Initialize Properties from the HTTPRequest.
     *  This is useful, as for most of the time results will be created from
     *  sources outside of the JSF Mechanisms.
     *  (Ie: Javascript, Nominatim)
     */
    public void initFromHTTPRequest(){
        
        
    HttpServletRequest request= (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
   
    
         this.setDisplayString(request.getParameter(OSMConstants.PARAM_NAME_displayStr));
         this.setLatitude(request.getParameter(OSMConstants.PARAM_NAME_latitude));   
         this.setLongitude(request.getParameter(OSMConstants.PARAM_NAME_longitude));   
         this.setCallerID(request.getParameter(OSMConstants.PARAM_NAME_callerID));   
         this.setReturnAddres(request.getParameter(OSMConstants.PARAM_NAME_returnAddress));   
        
         
    }
    
    

} // class

