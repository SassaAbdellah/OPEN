/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.matching;

import de.avci.joride.jbeans.driverundertakesride.JDriverUndertakesRideEntity;
import java.io.Serializable;

import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntity;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.fhg.fokus.openride.matching.MatchEntity;

/**
 * Wrapper making MatchingEntity available as a CDI Bean for use in JSF
 * frontend.
 *
 * @author jochen
 *
 */
@Named
@SessionScoped
public class JMatchingEntity implements Serializable {

    /**
     * The Match Entity that this Object is build around. 
     */
    private MatchEntity matchEntity = null;

    public MatchEntity getMatchEntity() {
        return this.matchEntity;
    }
    
  
    
    
    /** Non trivial setter! -- In addition to setting the MatchEntity property,
     *  it does also blank out the Drive and Ride properties,
     *  so that lazy instantiation will renew them.
     * 
     * @param arg 
     */
    public void setMatchEntitiy(MatchEntity arg){
    
        this.matchEntity=arg;
        this.drive=null;
        this.ride=null;

    }
    
    
    
    
    
    /**
     * Representation of the matchEntities riderUndertakesRideEntity prop. This
     * is created via lazy instantiation.
     *
     */
    private JRiderUndertakesRideEntity ride = null;

    /**
     * Accessor with lazy instantiation
     *
     * @return
     */
    public JRiderUndertakesRideEntity getRide() {

        if (ride == null) {
            ride = new JRiderUndertakesRideEntity();
            ride.updateFromRiderUndertakesRideEntity(matchEntity.getRiderUndertakesRideEntity());
        }

        return ride;
    }
    /**
     * Representation of the matchEntities driverUndertakesRideEntity prop. This
     * is created via lazy instantiation.
     *
     */
    private JDriverUndertakesRideEntity drive = null;

    /**
     * Accessor with lazy instantiation
     *
     * @return
     */
    public JDriverUndertakesRideEntity getDrive() {

        if (drive == null) {
            drive = new JDriverUndertakesRideEntity();
            drive.updateFromDriverUndertakesRideEntity(matchEntity.getDriverUndertakesRideEntity());
        }

        return drive;
    }
    
    
    /** Get Driver State in it's integer representation.
     * 
     *  @return  the driver state
     * 
     */
    public Integer getDriverState(){
        return this.getMatchEntity().getDriverState();
    }
    
     /** Get Rider State in it's integer representation.
     * 
     *  @return the rider state
     * 
     */
    public Integer getRiderState(){
        return this.getMatchEntity().getRiderState();
    }
    
    
    
    
    
    /** Accept Driver for this match.
     *  This methods attempts to be save, i.e checks if the caller is in role to accept match
     * 
     * 
     * @return  true if accepting the driver worked out, else false<
     * 
     */
    public String getAcceptDriver(){
       
        return ""+new JMatchingEntityService().acceptDriverSavely(this);
    
    }
    
    
       
    /** Accept Rider for this match.
     *  This methods attempts to be save, i.e checks if the caller is in role to accept match
     * 
     * @return  true if accepting the rider worked out, else false<
     * 
     */
    public String getAcceptRider(){
        return ""+new JMatchingEntityService().acceptRiderSavely(this);
    }
    
    
    
    /** Create a new JMatchingEntity from a real matchingEntity
     *
     * @param arg
     */
    JMatchingEntity(MatchEntity arg) {
        this.matchEntity = arg;
    }

    /** Bean constructor
     */
    public JMatchingEntity() {
    }
} // class 
