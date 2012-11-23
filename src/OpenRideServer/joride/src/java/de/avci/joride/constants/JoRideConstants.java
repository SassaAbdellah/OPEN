/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/** This class centrally defines a number of Constants for joride Frontend
 *  some of these may eventually be made configurable,
 *  so it's good to have them in one spot.
 * 
 *
 * @author jochen
 */
public class JoRideConstants {
    
    
    /** Format to be used for timestamps.
     * 
     *  FIXME: eventually this must be made confingurable as part 
     *         of internationalization. Currently this is eurocentric.
     */
    public static String JORIDE_DATE_FORMAT_STR= "dd.MM.yyyy HH:mm";
    
    
    /** Creates a new DateFormat 
     * 
     * @return  
     */
    public DateFormat createDateFormat(){
        return new SimpleDateFormat(JORIDE_DATE_FORMAT_STR);
    }
    
    
     
} // class
