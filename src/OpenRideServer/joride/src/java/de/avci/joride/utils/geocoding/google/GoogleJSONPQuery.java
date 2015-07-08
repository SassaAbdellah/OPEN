/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils.geocoding.google;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.avci.openrideshare.boundaries.BoundariesBean;

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
@Named("googleq")
@SessionScoped
public class GoogleJSONPQuery  implements Serializable {

      
    
	/** Query (freetext string) that is sent to google.
	 */
	private String searchstring="";
	
	public String getSearchstring(){
			
		return this.searchstring;
	}
	
	
	public void setSearchstring(String arg){
		this.searchstring=arg;
	}
	
	
	
	
	
	
	
    
    /** Name of the limit http parameter.
     *
     */
    protected static final String PARAM_NAME_limit = "limit";
    
    
    /** Default for the limit parameter. 
     *  Determines how many of the results are to be shown.
     * 
     */
    public static final Integer LIMIT_DEFAULT=1;
    
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
    
    
    

	/** Northern bound to be send to google maps api
     */
    private static String north=(""+(new BoundariesBean()).getNorthernBound());
 
    /** eastern bound to be send to  google maps api
     */
    private static String east=(""+(new BoundariesBean()).getEasternBound());
    
    /** Northern bound to be send to google maps api
     */
    private static String south=(""+(new BoundariesBean()).getSouthernBound());
    
    /** Northern bound to be send to google maps api
     */
    private static String west=(""+(new BoundariesBean()).getWesternBound());

	public  String getNorth() {
		return north;
	}


	public  String getEast() {
		return east;
	}


	public  String getSouth() {
		return south;
	}


	public  String getWest() {
		return west; 
	}
    
    
     

    
} // class

