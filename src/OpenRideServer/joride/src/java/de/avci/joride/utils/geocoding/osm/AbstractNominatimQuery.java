/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils.geocoding.osm;




/**
 *
 * @author jochen
 */
public abstract class AbstractNominatimQuery {

    public AbstractNominatimQuery() {
    }
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
    
    

    /** Property telling which POI to search for.
     *  Note, that we can use either
     *  street and number,   or  poiName.
     *  If poiName is given, then this takes precedence over street/and number
     *
     */
    protected String poi = null;
    
    /** Trivial Getter
     */
    public String getPoi(){ 
        return this.poi; 
    }
    
     /** Trivial Setter
     */
    public  void setPoi(String arg){ 
        this.poi=arg;
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

  
 
    
    /** Create a Q-String (this is the value associated with the "q" parameter
     *  and not to be confused with the http query string).
     *  
     * 
     * @return 
     */
    abstract public String getQString();
    
    
    /** Parameternames for passing Latitude and Longitude
     */
    public String getParamNameLatitude(){
        return OSMConstants.PARAM_NAME_latitude;
     }
    
    /** Parameternames for passing Latitude and Longitude
     */
    public String getParamNameLongitude(){
        return OSMConstants.PARAM_NAME_longitude;
     }
    
    
    /** Parameter name for passing display String
     */
    public String getParamNameDisplayStr(){
        return OSMConstants.PARAM_NAME_displayStr;
    }
    
    /** Parameter name for passing callerID
     */
    public String getParamNameCallerID(){
        return OSMConstants.PARAM_NAME_callerID;
    }
    
     /** Parameter name for passing returnAddress
     */
    public String getParamNameReturnAddress(){
        return OSMConstants.PARAM_NAME_returnAddress;
    }
    
    
    
    /** An ID set by the calling service to this request.
     *  This will be passed to the result, so that the 
     *  caller will finally know what to to with the result,
     *  when it comes back.
     * 
     */
    protected String callerID;
    
    
    /** Trivial Getter
     */
    public String getCallerID(){
        return this.callerID;
    }
    
    /** Trivial Setter
     */
    public void setCallerID(String arg){
       this.callerID=arg;
    }
    
    
    
    
    
    /** Return Addres. This consists of an actionString or a URL
     *  allowing the mapper mechanism to determine where to 
     *  finally send the results.
     */
    protected String returnAddress;
    
     /** Trivial Getter
     */
    public String getReturnAddress(){
        return this.returnAddress;
    }
    
    /** Trivial Setter
     */
    public void setReturnAddress(String arg){
       this.returnAddress=arg;
    }
    
    
    
    
    
    
    
    
    
    /** Base URL for calling the Mapper Servlet
     * 
     * @return 
     */
    public String getOSMMapperURL(){    
        return OSMConstants.OSMMAP;
    }
    
    
    
}
