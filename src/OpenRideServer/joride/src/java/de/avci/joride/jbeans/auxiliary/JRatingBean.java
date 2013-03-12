/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.auxiliary;

import de.avci.joride.jbeans.customerprofile.JCustomerEntity;
import de.avci.joride.jbeans.customerprofile.JPublicCustomerProfile;
import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntity;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.io.Serializable;
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
}
