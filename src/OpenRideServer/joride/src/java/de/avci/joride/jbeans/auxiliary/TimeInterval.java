/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.auxiliary;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.utils.HTTPRequestUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;

/**
 * Defines a time interval in terms of length in days and enddate.
 *
 * @author jochen
 */
@Named("timeinterval")
@RequestScoped
public class TimeInterval {
    
    Logger log= Logger.getLogger(""+this.getClass());
    

    /**
     * TimeFormat used for retrieving dates in http requests
     *
     */
    protected String DATE_FORMAT = JoRideConstants.JORIDE_DATE_FORMAT_STR;
    
    
    /** Make Format Strings available in JSF Fashion
     */
    public String getDateformat(){
        return DATE_FORMAT;
    }
    
    protected DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    /**
     * Date ending the period for which rides/drive should be displayed
     *
     */
    protected Date endDate = new Date();

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    /**
     *
     * @return nicely formatted version of the endDate property.
     */
    public String getEndDateFormatted() {
        return dateFormat.format(this.getEndDate());
    }

    /**
     *
     * @return nicely formatted version of the startDate property.
     */
    public String getStartDateFormatted() {
        return dateFormat.format(this.getStartDate());
    }
    
    
    
    /**
     * Number of Days before endDate for which drives/ride should be listed.
     *
     */
    protected int days;

    public void setDays(int args) {
        this.days = args;
    }

    public int getDays() {
        return this.days;
    }
    // number of miliseconds in a day
    long millisInDay = (1000 * 60 * 60 * 24);

    /**
     *
     * @return endDate- number of days before endDate
     */
    public Date getStartDate() {

        long endTime = this.getEndDate().getTime();
        long startTime = endTime - (millisInDay * getDays());
        return new Date(startTime);
    }
    /**
     * http parameter to transmit the startDate
     *
     */
    protected static String PARAM_NAME_END_DATE = "END_DATE";

    /**
     * Make StartDate parameter name availlable in JSF Fashion
     */
    public String getParamEndDate() {
        return this.PARAM_NAME_END_DATE;
    }
    /**
     * http parameter to transmit the timeInterval
     *
     */
    protected static String PARAM_NAME_DAYS = "DAYS";

    /**
     * Make days parameter name availlable in JSF Fashion
     *
     * @return
     */
    public String getParamDays() {
        return this.PARAM_NAME_DAYS;
    }

    /**
     * Update this Object from HTTP Parameters (if HTTP Parameters are present)
     */
    public void smartUpdate()  {

        HTTPRequestUtil utils = new HTTPRequestUtil();

        /**
         * Update days value, if Parameter present
         */
        String daysStr = utils.getParameterSingleValue(PARAM_NAME_DAYS);


        if (daysStr == null) {
            this.setDays(1);
        } else {

            try {
                Integer daysInt = Integer.getInteger(daysStr);
                this.setDays(daysInt);
            } catch (Exception exc) { /* do nothing if things go wrong*/

            }
        }


        /* Update EndDate if parameter present
         */

        String dateStr = utils.getParameterSingleValue(PARAM_NAME_END_DATE);


        System.err.println("RECEIVED DATE PARAMETER : " + dateStr);

        if (dateStr == null) {
            this.setEndDate(new Date());
        } else {
    
            try {       
                Date dateEndPar = dateFormat.parse(dateStr);
                this.setEndDate(dateEndPar);
            } catch(ParseException exc){
                log.log(Level.SEVERE, "Error while parsing date", exc);
            }
        }





    }
} // class

