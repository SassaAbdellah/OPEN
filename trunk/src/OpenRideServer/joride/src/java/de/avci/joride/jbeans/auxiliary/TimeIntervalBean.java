/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.auxiliary;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.utils.HTTPRequestUtil;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Defines a time interval in terms of length in days and enddate.
 *
 * @author jochen
 */
@Named("timeinterval")
@SessionScoped
public class TimeIntervalBean implements Serializable {

    Logger log = Logger.getLogger("" + this.getClass());
    /**
     * Maximal timespan in days a user can search.
     */
    protected long MAX_INTERVAL_DAYS = Long.MAX_VALUE;

    /**
     * Make MAX_INTERVAL_DAYS availlable via JSF property
     *
     */
    public long getMaxIntervalDays() {
        return MAX_INTERVAL_DAYS;
    }
    /**
     * TimeFormat used for retrieving dates in http requests
     *
     */
    protected String DATE_FORMAT = JoRideConstants.JORIDE_DATE_FORMAT_STR;

    /**
     * Make Format Strings available in JSF Fashion
     */
    public String getDateformat() {
        return DATE_FORMAT;
    }
    protected DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    /**
     * Date starting the period for which rides/drive should be displayed
     *
     */
    protected Date startDate = new Date();

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Date getStartDate() {
        return this.startDate;
    }
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
     * http parameter to transmit the startDate
     */
    protected static String PARAM_NAME_START_DATE = "TIMEINTERVAL_START_DATE";

    /**
     * Make StartDate parameter name available in JSF Fashion
     */
    public String getParamStartDate() {
        return this.PARAM_NAME_START_DATE;
    }
    /**
     * http parameter to transmit the endDate
     */
    protected static String PARAM_NAME_END_DATE = "TIMEINTERVAL_END_DATE";

    /**
     * Make StartDate parameter name available in JSF Fashion
     */
    public String getParamEndDate() {
        return this.PARAM_NAME_END_DATE;
    }

    /**
     * Update this Object from HTTP Parameters (if HTTP Parameters are present)
     */
    public void smartUpdate() {


        HTTPRequestUtil utils = new HTTPRequestUtil();
        String startDateStr = utils.getParameterSingleValue(getParamStartDate());
        String endDateStr = utils.getParameterSingleValue(getParamEndDate());



        System.err.println("this.getClass smartUpdate start " + startDateStr + " end " + endDateStr);



        /* Update EndDate if parameter present
         */
        if (startDateStr == null) {
            this.setStartDate(new Date());
        } else {

            try {
                Date dateStartPar = dateFormat.parse(startDateStr);
                this.setStartDate(dateStartPar);
            } catch (ParseException exc) {
                log.log(Level.SEVERE, "Error while parsing start date", exc);
            }
        }

        /* Update EndDate if parameter present
         */
        if (endDateStr == null) {
            this.setEndDate(new Date());
        } else {

            try {
                Date dateEndPar = dateFormat.parse(endDateStr);
                this.setEndDate(dateEndPar);
            } catch (ParseException exc) {
                log.log(Level.SEVERE, "Error while parsing end date", exc);
            }
        }

    } // smartUpdate  

    /**
     * Programmatocally retrieve TimeInterval from Faces Context please note,
     * this is a workaround in case neither injection nor actionListening is an
     * option.
     *
     * @param beanName name of the Bean (e.g: time Interval)
     * @return
     */
    public TimeIntervalBean retrieveCurrentTimeInterval(String beanName) {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Object o = context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", this.getClass());
            TimeIntervalBean res = (TimeIntervalBean) o;
            return res;
        } catch (Exception exc) {
            throw new Error("Unexpected Error while retrieving time interval named " + beanName, exc);
        }
    } // 
} // class

