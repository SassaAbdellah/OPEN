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




/**
 * 
 */
public class WebflowPoint implements Serializable {

  
    
    
     

    /**
     * Make PARAM_NAME_TARGETURL available as a bean method.
     *
     * @return
     */
    public String getParamTargetURL() {
        return WebflowPointConstants.PARAM_NAME_TARGETURL;
    }
    
    
    /** String containing the targeturl 
     */
    protected String targeturl=null;
    
    public String getTargetURL(){
        return targeturl;
    }
    
    
  

    /**
     * Make PARAM_NAME_TARGET available as a bean method.
     *
     * @return
     */
    public String getParamTarget() {
        return WebflowPointConstants.PARAM_NAME_TARGET;
    }
    
    
    /** String containing the target
     */
    protected String target=null;
    
    public String getTarget(){
        return target;
    }
    
    
 

    /** Make PARAM_NAME_LON availlable as a bean method.
     *
     * @return
     */
    public String getParamLon() {
        return WebflowPointConstants.PARAM_NAME_LON;
    }
    
    /** Double containing the current value for the longitude.
     */
    protected Double lon;

    public Double getLon() {
        return lon;
    }
    
  

    /**
     * Make PARAM_NAME_LAT available as a bean method.
     *
     * @return
     */
    public String getParamLat() {
        return WebflowPointConstants.PARAM_NAME_LAT;
    }
    
    
    
    /**  Double containing the current value for the latitude.
     *
     */
    protected Double lat;

    public Double getLat() {
        return lat;
    }
    
    
 

    /** Make PARAM_NAME_DISPLAYSTRING available as a bean method.
     *
     * @return
     */
    public String getParamDisplaystring() {
        return WebflowPointConstants.PARAM_NAME_DISPLAYSTRING;
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
     * Make PARAM_NAME_ADDRESS available as a bean method.
     *
     * @return
     */
    public String getParamAddress() {
        return WebflowBeanConstants.PARAM_NAME_ADDRESS;
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
        
        
          
        String vTargetUrl = hru.getParameterSingleValue(getParamTargetURL());
        if (vTargetUrl != null) {
            this.targeturl = vTargetUrl;
        }
        
     
        
        String vTarget = hru.getParameterSingleValue(getParamTarget());
        if (vTarget != null) {
            this.target = vTarget;
        }
        

        String vLon = hru.getParameterSingleValue(getParamLon());
        if (vLon != null) {
            
            try{ this.lon = new Double(vLon);
            } catch(java.lang.NumberFormatException exc){
                System.err.println("Error while converting longitude "+exc);
            }
            
            
        }

        String vLat = hru.getParameterSingleValue(getParamLat());
        if (vLat != null) {
             try{ this.lat = new Double(vLat);
            } catch(java.lang.NumberFormatException exc){
                System.err.println("Error while converting latitude "+exc);
            }
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

    
    
    /** Return a get request targeting the target URL and
     *  transporting the Displaystring/Address/target/lon/lat values
     * 
     * 
     * @return 
     */
    
    public String getResultURL(){
    
        String res=this.getTargetURL();
        res+="?";
        res+=this.getParamDisplaystring()+"="+this.getDisplaystring();
        res+="&";
        res+=this.getParamAddress()+"="+this.getAddress();
        res+="&";
        res+=this.getParamTarget()+"="+this.getTarget();
        res+="&";
        res+=this.getParamLat()+"="+this.getLat();
        res+="&";
        res+=this.getParamLon()+"="+this.getLon();
        
        return res;
    
    }
    
    
    /**
     * clear all values
     */
    public void clear() {

        this.lat = null;
        this.lon = null;
        this.displaystring = null;
        this.address = null;
        this.targeturl=null;
    }
    
    
    
    /** Print out a summary of properties
     * 
     * @return 
     */
   public  String getDebugPrintout(){ 
        
        String res="\n";
        res+="\n Displaystring : "+getDisplaystring();
        res+="\n Adress        : "+getAddress();
        res+="\n TargetUrl     : "+getTargetURL();
        res+="\n Target        : "+getTarget();
        res+="\n Lat           : "+getLat();
        res+="\n Lon           : "+getLon();
       
        return res;
    }
    

    /**
     * Returns a postgis point
     *
     */
    public Point getPoint() {


        Double latitude=null;

        try {
            latitude = new Double(this.getLat());
        } catch (java.lang.NumberFormatException exc) {
            System.err.println("Cannot determine numerical latitude from " + this.getLat());
            return null;
        } catch (java.lang.NullPointerException exc) {
            System.err.println("Latitude was null, cannot create point");
            return null;
        }
        
             

        Double longitude=null;

        try {
            longitude = new Double(this.getLon());
        } catch (java.lang.NumberFormatException exc) {
            System.err.println("Cannot determine numerical longitude from " + this.getLat());
            return null;
        } catch (java.lang.NullPointerException exc) {
            System.err.println("Longitude was null, cannot create point");
            return null;
        }

        return new Point(longitude, latitude);
    }
    
    
  /* Serialize PostGis Point coordinates to be stored in  
     *  the database. That is:
     *  return ""+this.getLat()+","+this.getLon();
     *  or null, if any of the coordinates is null.
     * 
     * @returns a serialized version of the String, that can be 
     * stored in the database
     */
    public String getDatabaseString(){
    
        PostGISPointUtil pu=new PostGISPointUtil();
        return pu.getDatabaseString(this.getPoint());
    }
   
} // class