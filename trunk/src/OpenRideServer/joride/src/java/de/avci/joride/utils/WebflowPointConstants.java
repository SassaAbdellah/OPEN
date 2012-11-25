/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils;

/**
 *
 * @author jochen
 */
public class WebflowPointConstants {

    /** Name of the http request parameter that contains the target. The target
     * is used by the target page to determine which point should be updated.
     * (For ex: a RideRequest contains start and endpoint, and the target
     * parameter can be used to determin wether start or endpoint should be set)
     *
     */
    public static final String PARAM_NAME_TARGET = "target";
    
    
    /** Name of the http request parameter that contains the Displaystring
     */
    public static final String PARAM_NAME_DISPLAYSTRING = "displaystring";
    
    
    /** Name of the http request parameter that contains the latitude
     */
    public static final String PARAM_NAME_LAT = "lat";
    
    /** Name of the http request parameter that contains the longitude
     */
    public static final String PARAM_NAME_LON = "lon";
    
}
