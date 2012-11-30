/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils;

import java.sql.SQLException;
import org.postgis.Point;

/**
 *
 * @author jochen
 */
public class PostGISPointUtil {

    /* Serialize PostGis Point coordinates to be stored in the database. That
     * is: return ""+point.getX()+","+point.getY(); or null, if any of the
     * coordinates is null.
     *
     * @returns a serialized version of the String, that can be stored in the
     * database
     */
    public String getDatabaseString(Point point) {

        return "" + point.getX() + "," + point.getY();
    }

    /** Create a point from longitude and latitude
     *
     * @param lon longitude
     * @param lat latitude
     * @return a new Postgis Point with X=longitude and Y=latitude
     */
    public Point createPoint(Double lon, Double lat) {

        return new Point(lon, lat);
    }

    
    /** Deserialize a Point from a database String
     * 
     * @param dbString
     * @return 
     */
    public Point pointFromDBString(String dbString) {

        try{
        
            int commapos=dbString.indexOf(',');
            
            String lonStr=dbString.substring(0, commapos);
            String latStr=dbString.substring(commapos+1);
            
            Double lonD=new Double(lonStr);
            Double latD=new Double(latStr);
            
            return new Point(lonD,latD);
        
        
        } catch (java.lang.Exception exc) {

            System.err.println("Error while deserializing point from String : " + dbString+" "+exc.getMessage());
            return null;
        }

    }
    
    
   
    /** Returns point.getX();
     * 
     * @param point
     * @return 
     */
    public Double getLon(Point point){
        return point.getX();
    }
    
    /** Returns point.getY();
     * 
     * @param point
     * @return 
     */
    public Double getLat(Point point){
        return point.getY();
    }
    
    
    
    
    
    
} // class
