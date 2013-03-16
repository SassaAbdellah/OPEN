/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.auxiliary;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.customerprofile.JPublicCustomerProfile;
import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntity;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import javax.inject.Named;

/**
 * Make Driverratings/Riderratings availlable in form of a JSF-Bean
 *
 * @author jochen
 */
@Named("rating")
public class JRatingBean implements Serializable {

    /**
     * Public data of the person that rated me.
     */
    protected JPublicCustomerProfile rater;

    public JPublicCustomerProfile getRater() {
        return this.rater;
    }

    protected void setRater(JPublicCustomerProfile arg) {
        this.rater = arg;
    }
    /**
     * Rating (may be null if ride is not yet rated!)
     */
    protected Integer rating = null;

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer ratingArg) {
        this.rating = ratingArg;
    }
    /**
     * Ratings Comment
     */
    protected String comment = null;

    public String getComment() {
        return this.comment;
    }

    public void setComment(String commentArg) {
        this.comment = commentArg;
    }
    /**
     * Date of Rating, May be null, if there was no rating
     */
    protected Date ratingDate = null;

    public Date getRatingDate() {
        return ratingDate;
    }

   
    public void setRatingDate(Date arg) {
        this.ratingDate = arg;
    }

    
     /** Nicely formatted version of the rating date
     * 
     * @param arg 
     */
    public String getRatingDateFormatted(){
    
        DateFormat df=new JoRideConstants().createDateFormat();
    
        if(this.getRatingDate()!=null){
            return df.format(ratingDate);
        }
        
        return "";
    }
    
    
    
    public JRatingBean extractRiderRating(JRiderUndertakesRideEntity jrure) {


        JRatingBean res = new JRatingBean();

        CustomerEntity ce = jrure.getCustId();

        // Rater 
        res.setRater(new JPublicCustomerProfile()); 
        res.getRater().updateFromCustomerEntity(ce);
        
        // Rating
        res.setRating(jrure.getReceivedrating());
        
        // Comment
        res.setComment(jrure.getReceivedratingComment());
        
        // Rating Date
        res.setRatingDate(jrure.getReceivedratingDate());

        return res;
    }
    
    /** Create a rating bean from ride's driver rating (=receivedRating)
     * 
     * @param rue
     * @return 
     */
    public static JRatingBean createRatingFromDriverRating(RiderUndertakesRideEntity ride){
    
        JRatingBean res=new JRatingBean();
        
        // rater
        JPublicCustomerProfile jpcp=new JPublicCustomerProfile();
        jpcp.updateFromCustomerEntity(ride.getRideId().getCustId());
        res.setRater(jpcp);
        
        // rating
        res.setRating(ride.getReceivedrating());
        // date
        res.setRatingDate(ride.getReceivedratingDate());
        // comment
        res.setComment(ride.getReceivedratingComment());
    
        return res;
    }
    
    
    
    
    /** Create a rating bean from ride's driver rating
     * 
     * @param rue
     * @return 
     */
    public static JRatingBean createRatingFromRiderRating(RiderUndertakesRideEntity ride){
    
        JRatingBean res=new JRatingBean();
        
        // rater
        JPublicCustomerProfile jpcp=new JPublicCustomerProfile();
        jpcp.updateFromCustomerEntity(ride.getCustId());
        res.setRater(jpcp);
        
        // rating
        res.setRating(ride.getGivenrating());
        // date
        res.setRatingDate(ride.getGivenratingDate());
        // comment
        res.setComment(ride.getGivenratingComment());
    
        return res;
    }
    
    
  
    
    
} // class
