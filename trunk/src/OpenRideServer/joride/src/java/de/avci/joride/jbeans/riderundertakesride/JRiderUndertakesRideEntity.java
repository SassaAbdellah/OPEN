/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.riderundertakesride;

import de.avci.joride.constants.JoRideConstants;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.text.DateFormat;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Wrapper to make RideUndertakesRideEntity availlable as a JSFBean
 *
 *
 * @author jochen
 */
@Named
@SessionScoped


public class JRiderUndertakesRideEntity extends RiderUndertakesRideEntity {
    
    
    
          /**
     * A date format for formatting start and end date. Created via lazy
     * instantiation.
     * 
     * @deprecated  should be done centrally in utils* class
     * 
     */
    protected DateFormat dateFormat;

    /**
     * Accessor with lazy instantiation
     *
     * 
     * 
     * @return
     */
    protected DateFormat getDateFormat() {

        if (this.dateFormat == null) {
            dateFormat = (new JoRideConstants()).createDateFormat();
        }

        return dateFormat;
    }

    /**
     * Return a nicely formatted version of the startDate
     *
     * @return
     */
    public String getStartDateFormatted() {
        return getDateFormat().format(this.getStarttimeEarliest());
    }
    
    
    
    

    /** Update from a given RiderUndertakesRideEntity object.
     *
     * @param rure RiderUndertakesRideEntity to update from
     */
    public void updateFromRiderUndertakesRideEntity(RiderUndertakesRideEntity rure) {

        // private Integer riderrouteId;
        this.setRiderrouteId(rure.getRiderrouteId());

        // private Integer givenrating;
        this.setGivenrating(rure.getGivenrating());

        // private String givenratingComment;
        this.setGivenratingComment(rure.getGivenratingComment());
        
        // private Date givenratingDate;
        this.setGivenratingDate(rure.getGivenratingDate());

        // private Integer receivedrating;
        this.setReceivedrating(rure.getReceivedrating());


        // private String receivedratingComment;
        this.setReceivedratingComment(rure.getReceivedratingComment());
       
        // private Date receivedratingDate;
        this.setReceivedratingDate(rure.getReceivedratingDate());

        // private Date starttimeEarliest; 
        this.setStarttimeEarliest(rure.getStarttimeEarliest());

        // private Date starttimeLatest;
        this.setStarttimeLatest(rure.getStarttimeLatest());

        //private Date timestampbooked
        this.setTimestampbooked(rure.getTimestampbooked());

        // private Date timestamprealized;
        this.setTimestamprealized(rure.getTimestamprealized());

        // private Point startpt;
        this.setStartpt(rure.getStartpt());

        // private String startptAddress;
        this.setStartptAddress(rure.getStartptAddress());

        // private Point endpt;
        this.setEndpt(rure.getEndpt());

        // private String endptAddress;
        this.setEndptAddress(rure.getEndptAddress());

        // private Double price;
        this.setPrice(rure.getPrice());

        // private Integer noPassengers;
        this.setNoPassengers(rure.getNoPassengers());

        // private CustomerEntity custId;
        this.setCustId(rure.getCustId());

        // private DriverUndertakesRideEntity rideId;
        this.setRideId(rure.getRideId());

        // private String comment;
        this.setComment(rure.getComment());

    } //   public void updateFromRiderUndertakesRideEntit
    
    
    
    
    
    /** Lists *all* rides this customer has ever requested
     * 
     * @return 
     */
    public List <RiderUndertakesRideEntity> getRidesForRider(){
    
        return (new JRiderUndertakesRideEntityService()).getRidesForRider();

        
        
    }


} // class
