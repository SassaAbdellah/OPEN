/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils.geocoding.osm;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


import java.io.Serializable;

/**
 * Model for a JSONP Query to the nominatim geocoding service.
 *
 *
 *
 *
 *
 *
 * @author jochen
 */
@Named("nominatimq")
@SessionScoped
public class NominatimJSONPQuery  implements Serializable {

    
    
        /** Default value for the service_url property.
     *  This points to the original Nominatim service at:
     * "http://nominatim.openstreetmap.org/search"
     */
    protected static final String DEFAULT_SERVICE_URL = "http://nominatim.openstreetmap.org/search";
    /** Name of the "addressdetails" parameter.
     *  Wether to return verbose address details.
     *  We alway chose 0 here, because we cannot process adressdetaiks anyway.
     *  Currently, we are content with returning the displaystring,
     *  which is always there.
     *
     *  See a typical Nominatim query below:
     *  q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_NAME_addressdetails = "addressdetails";
    /** Nominatim parametername for the "format" parameter.
     *  Format parameter defines the format of the response.
     *  Nominatim allows 'html','xml', json,...
     *  We alway chose json here, because technically we do
     *  cross site scripting with jsonp (aka: padded JSON).
     *
     *
     *  See a typical Nominatim query below:
     *  q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_NAME_format = "format";
    /** Nominatim parametername for the "polygon" parameter.
     *  Wether to return a polygon for boundary or not
     *  We alway chose 0 here, because we cannot process polygons anyway.
     *
     *  See a typical Nominatim query below:
     *  q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_NAME_polygon = "polygon";
    /** Nominatim parametername for the "querystring".
     *  This is simply called 'q' by nominatim.
     *
     *  See a typical Nominatim query below:
     *  q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_NAME_q = "q";
    /** Value of the "addressdetails" parameter.
     *  Wether to return verbose address details.
     *  We alway chose 0 here, because we cannot process adressdetaiks anyway.
     *  Currently, we are content with returning the displaystring,
     *  which is always there.
     *
     *  See a typical Nominatim query below:
     *  q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_VALUE_addressdetails = "0";
    
    /** Value  for the "polygon" parameter.
     *  Wether to return a polygon for boundary or not
     *  We alway chose 0 here, because we cannot process polygons anyway.
     *
     *  See a typical Nominatim query below:
     *  q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_VALUE_polygon = "0";
    
    
    
    /** Name of the limit http parameter.
     *
     */
    protected static final String PARAM_NAME_limit = "limit";
    
    
    
    
    
    /** Property telling which counrty to search
     */
    protected String country=null;
    
    public String getCountry(){
    	return this.country;
    }
    
    public void setCountry(String arg){
    	this.country=arg;
    }
    
    
    
    /** Property telling which state (in a federal system) to search
     */
    protected String state=null;
    
    public String getState(){
    	return this.state;
    }
    
    public void setState(String arg){
    	this.state=arg;
    }
    
    
    
    /** Property telling which county to search
     */
    protected String county=null;
    
    public String getCounty(){
    	return this.county;
    }
    
    public void setCounty(String arg){
    	this.county=arg;
    }
    
    
    
    /** Property telling which City to search for.
     *  Note, that we can use either
     *  street and number,   or  poiName.
     *  If poiName is given, then this takes precedence over street/and number
     *
     */
    protected String city = null;
    
      /** Trivial Getter
     */
    public String getCity() {
        return this.city;
    }


    /** Trivial Setter
     */
    public  void setCity(String arg){ 
        this.city=arg;
    }
    
    
    /** Property telling which Neighborhood(s) to search for.
     *  Note, that we can use either
     *  street and number...,   or  freetext search.
     *  If freetextsearch is given, then this takes precedence over street/and number
     *
     */
    protected String neigborhoods = null;
       
    public String getNeigborhoods() {
		return neigborhoods;
	}

	public void setNeigborhoods(String neigborhoods) {
		this.neigborhoods = neigborhoods;
	}



	/** Property telling which place to search for in "freeSearch" style.
     *  Note, that we can use either
     *  city/street/number,   or  freeSearch.
     *  If poiName is given, then this takes precedence over city/street/and number
     *
     */
    protected String freeTextSearch = null;
    
    /** Trivial Getter
     */
    public String getFreeTextSearch(){ 
        return this.freeTextSearch; 
    }
    
     /** Trivial Setter
     */
    public  void setFreeTextSearch(String arg){ 
        this.freeTextSearch=arg;
    }
    
    
    /** Default for the limit parameter
     * 
     */
    public static final Integer LIMIT_DEFAULT=10;
    
    protected int limit=LIMIT_DEFAULT;
    
    public int getLimit(){
    	return this.limit;
    }
   
    /** Nontrivial setter
     * 	
     * Sets limit to maximum of 1 and arg.
     * 
     * @param arg
     */
    public void setLimit(int arg){
    	this.limit=Math.max(arg, 1);
    }
    
    
    
    
    /** Property telling which URL to use.
     *  Decault is {@link DEFAULT_SERVICE_URL }
     */
    protected String serviceURL = NominatimJSONPQuery.DEFAULT_SERVICE_URL;
    
    
    /** Trivial getter
     */
    public String getServiceURL(){return this.serviceURL;}
    
    /** Trivial Setter
     */
    public void setServiceURL(String arg) {this.serviceURL=arg; }
    
    
    /** Property telling which Street to search for.
     *  Note, that we can use either
     *  street and number or  poiName.
     *  If poiName is given, then this takes precedence over street/and number
     *
     */
    protected String street = null;
    
       /** Trivial Getter
     */
    public String getStreet(){ 
        return this.street; 
    }
    
     /** Trivial Setter
     */
    public  void setStreet(String arg){ 
        this.street=arg;
    }

    
    /** Property telling which Streetnumber to search for.
     *  Note, that we can use either
     *  street and number or  poiName.
     *  If poiName is given, then this takes precedence over street/and number
     *
     */
    protected String streetNumber = null;
    
       /** Trivial Getter
     */
    public String getStreetNumber(){ 
        return this.streetNumber; 
    }
    
     /** Trivial Setter
     */
    public  void setStreetNumber(String arg){ 
        this.streetNumber=arg;
    }

    
    /**
     * Standard Value for the "format" parameter. Format parameter defines the
     * format of the response. Nominatim allows 'html','xml', json,... We alway
     * chose json here, because technically we do cross site scripting with
     * jsonp (aka: padded JSON).
     *
     *
     * See a typical Nominatim query below:
     * q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_VALUE_json = "json";
    /**
     * Nominatim parametername for the "json_callback" parameter. json_callback
     * governs, how a json callback will be padded. With that, we can circumvent
     * the "same origin policy" and do cross site scripting with jsonp (aka:
     * padded JSON)
     *
     * We give "callback" here, because our callback function is simply called
     * "callback".
     *
     *
     * See a typical Nominatim query below:
     * q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM__NAME_json_callback = "json_callback";
    /**
     * Nominatim standard value for the "json_callback" parameter. json_callback
     * governs, how a json callback will be padded. With that, we can circumvent
     * the "same origin policy" and do cross site scripting with jsonp (aka:
     * padded JSON)
     *
     * We give "callback" here, because our callback function is simply called
     * "callback".
     *
     *
     * See a typical Nominatim query below:
     * q=fliederweg&format=json&polygon=1&addressdetails=2&json_callback=callback
     *
     */
    protected static final String PARAM_VALUE_json_callback = "callback";

    /**
     * Create the nominatim specific string to be given as a value for the 'q'
     * parameter.
     *
     * (Not th be confused with the full querystring!)
     *
     * @return
     */
    public String getQString() {


        String res = this.PARAM_NAME_q + "=";

        
        // if freeform search is used, then structured search is turned of
        if (this.getFreetextMode()) {
            String freeText = this.getFreeTextSearch();
            freeText.replace('\n', ',');
            return res+freeText;
        }
    
       
        
        // else, for structured search, use country/city/street/number...
        
        if (this.getCountry() != null && !("".equals(this.getCountry()))) {
            res += "," + this.getCountry();
        }
        
        // else, for structured search, use country/state/county/city/neighborhood/street/number...
        
        if (this.getState() != null && !("".equals(this.getState()))) {
            res += "," + this.getState();
        }
        
        
        if (this.getCounty() != null && !("".equals(this.getCounty()))) {
            res += "," + this.getCounty();
        }

     
        if (this.getCity() != null && !("".equals(this.getCity()))) {
            res += "," + this.getCity();
        }
        
        if (this.getNeigborhoods() != null && !("".equals(this.getNeigborhoods()))) {
            res += "," + this.getNeigborhoods();
        }


        if (this.getStreet() != null && !("".equals(this.getStreet()))) {
            res += "," + this.getStreet();
        }
        
        if (this.getStreetNumber() != null && !("".equals(this.getStreetNumber()))) {
            res += "," + this.getStreetNumber();
        }
        
        return res;
    }
    
    
    /** Active index determinig which panel of accordion should be shown in frontend
     */
    public int getActiveIndex(){
    	
    	// open up first panel if we are in freetext mode
    	if(this.getFreetextMode()) {
    		return 0;
    	}
    	
    	// open up second panel if we are in structured mode
    	if(this.getStructuredMode()) {
    		return 1;
    	}
    	
    	return -1;
    }
    
    /** Dummy, simulates a writable property for JSF, but doesn't do anything
     * 
     */
    public void setActiveIndex(int arg){}
    
    
    
    /** Flag signifying that user triggered structured search.
     *  This may govern visibility of the structured search fields in 
     *  the frontend.
     */
    private boolean structuredMode=false;
    
    
    public boolean getStructuredMode() {
		return structuredMode;
	}

	public void setStructuredMode(boolean structuredMode) {
		this.structuredMode = structuredMode;
	}
	
	/** Flag signifying that user triggered freetext search.
     *  This may govern visibility of the freetext search fields in 
     *  the frontend.
     */
    private boolean freetextMode=false;
    
   
    public boolean getFreetextMode() {
		return freetextMode;
	}

	public void setFreetextMode(boolean freetextMode) {
		this.freetextMode = freetextMode;
	}

	/** set Freetext value to null, so that it does not interfere with structured search.
     * 
     */
    public void switchStructuredMode(){
    	
    	this.setFreetextMode(false);
    	this.setStructuredMode(true);
    }
    
    /** Set values for structured search == null, so it does not interfere with freetext search.
     */
    public void switchFreetextMode(){
    
    	this.setFreetextMode(true);
    	this.setStructuredMode(false);
    }
    
    

    /**
     * Create the query string to be sent against nominatim service
     *
     */
    public String getQueryString() {

        String res = getServiceURL();

        // now, create the query part of the url,
        // something like:
        //
        //?#{nominatimq.QString}&format=json&polygon=0;addressdetails=0&json_callback=callback&serial=1"


        res += "?" + this.getQString();
        res += "&" + PARAM_NAME_limit          + "=" + getLimit(); 
        res += "&" + PARAM_NAME_format         + "=" + PARAM_VALUE_json;
        res += "&" + PARAM_NAME_polygon        + "=" + PARAM_VALUE_polygon;
        res += "&" + PARAM_NAME_addressdetails + "=" + PARAM_VALUE_addressdetails;
        res += "&" + PARAM__NAME_json_callback + "=" + PARAM_VALUE_json_callback;

        return res;

    }
} // class

