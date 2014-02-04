/*
 OpenRideShare -- Car Sharing 3.0
 Copyright (C) 2014  Jochen Laser

 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License Version 3 as
 published by the Free Software Foundation.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fhg.fokus.openride.matching;

import de.fhg.fokus.openride.rides.driver.DriveNegotiationConstants;
import java.io.Serializable;
import java.util.List;

/**
 * Portmanteau class to contain statistics concerning the matches for a given
 *
 * riderundetakesrideentity
 *
 * or
 *
 * driverundertakesrideentity
 *
 *
 *
 * @author jochen
 */
public class MatchingStatistics implements Serializable {

    /** Overall number of matches that have gone into this
     *  statistics
     */
    private int numberOfMatches=0;
    
    
    /**
     * Number of matches which have rider state "NOT_ADAPTED"
     */
    private int notAdaptedRider = 0;
    /**
     * Number of matches which have driver state "NOT_ADAPTED"
     */
    private int notAdaptedDriver = 0;
    /**
     * Number of matches which have rider state "ACCEPTED"
     */
    private int acceptedRider = 0;
    /**
     * Number of matches which have driver state "ACCEPTED"
     */
    private int acceptedDriver = 0;
    /**
     * Number of matches which have rider state "REJECTED"
     */
    private int rejectedRider = 0;
    /**
     * Number of matches which have driver state "REJECTED"
     */
    private int rejectedDriver = 0;
    /**
     * Number of matches which have rider state "NO_MORE_AVAILLABLE"
     */
    private int noMoreAvaillableRider = 0;
    /**
     * Number of matches which have driver state "NO_MORE_AVAILLABLE"
     */
    private int noMoreAvaillableDriver = 0;
    /**
     * Number of matches which have rider state "COUNTERMANDED"
     */
    private int countermandedRider = 0;
    /**
     * Number of matches which have driver state "COUNTERMANDED"
     */
    private int countermandedDriver = 0;
    /**
     * Number of matches which are accepted by both, driver and rider
     */
    private int acceptedBoth = 0;

    public int getNumberOfMatches(){
        return this.numberOfMatches;
    }
  
    private void setNumberOfMatches(int arg){
        this.numberOfMatches=arg;
    }

    
    public int getNotAdaptedRider() {
        return notAdaptedRider;
    }

    public void setNotAdaptedRider(int notAdaptedRider) {
        this.notAdaptedRider = notAdaptedRider;
    }

    public int getNotAdaptedDriver() {
        return notAdaptedDriver;
    }

    public void setNotAdaptedDriver(int notAdaptedDriver) {
        this.notAdaptedDriver = notAdaptedDriver;
    }

    public int getAcceptedRider() {
        return acceptedRider;
    }

    public void setAcceptedRider(int acceptedRider) {
        this.acceptedRider = acceptedRider;
    }

    public int getAcceptedDriver() {
        return acceptedDriver;
    }

    public void setAcceptedDriver(int acceptedDriver) {
        this.acceptedDriver = acceptedDriver;
    }

    public int getRejectedRider() {
        return rejectedRider;
    }

    public void setRejectedRider(int rejectedRider) {
        this.rejectedRider = rejectedRider;
    }

    public int getRejectedDriver() {
        return rejectedDriver;
    }

    public void setRejectedDriver(int rejectedDriver) {
        this.rejectedDriver = rejectedDriver;
    }

    public int getNoMoreAvaillableRider() {
        return noMoreAvaillableRider;
    }

    public void setNoMoreAvaillableRider(int noMoreAvaillableRider) {
        this.noMoreAvaillableRider = noMoreAvaillableRider;
    }

    public int getNoMoreAvaillableDriver() {
        return noMoreAvaillableDriver;
    }

    public void setNoMoreAvaillableDriver(int noMoreAvaillableDriver) {
        this.noMoreAvaillableDriver = noMoreAvaillableDriver;
    }

    public int getCountermandedRider() {
        return countermandedRider;
    }

    public void setCountermandedRider(int countermandedRider) {
        this.countermandedRider = countermandedRider;
    }

    public int getCountermandedDriver() {
        return countermandedDriver;
    }

    public void setCountermandedDriver(int countermandedDriver) {
        this.countermandedDriver = countermandedDriver;
    }

    public int getAcceptedBoth() {
        return acceptedBoth;
    }

    public void setAcceptedBoth(int acceptedBoth) {
        this.acceptedBoth = acceptedBoth;
    }

    /**
     * Add data from argument to statitstics
     *
     * @param ,
     */
    public void addMatchingToStatistics(MatchEntity m) {

        // count the number of matchings processed
        this.numberOfMatches++;
        

        /**
         * driver...
         */
        // TODO: better set 0 as a decent default value
        int d = MatchEntity.NOT_ADAPTED;


        d = m.getDriverState();


        if (MatchEntity.ACCEPTED.equals(d)) {
            this.acceptedDriver++;
        }



        if (MatchEntity.COUNTERMANDED.equals(d)) {
            this.countermandedDriver++;
        }


        if (MatchEntity.NOT_ADAPTED.equals(d)) {
            this.notAdaptedDriver++;
        }



        if (MatchEntity.NO_MORE_AVAILABLE.equals(d)) {
            this.noMoreAvaillableDriver++;
        }


        if (MatchEntity.REJECTED.equals(d)) {
            this.rejectedDriver++;
        }


        /**
         * rider
         */
        int r = MatchEntity.NOT_ADAPTED;

        r = m.getRiderState();






        if (MatchEntity.ACCEPTED.equals(r)) {
            this.acceptedRider++;
        }

        if (MatchEntity.COUNTERMANDED.equals(r)) {
            this.countermandedRider++;
        }

        if (MatchEntity.NOT_ADAPTED.equals(r)) {
            this.notAdaptedRider++;
        }

        if (MatchEntity.NO_MORE_AVAILABLE.equals(r)) {
            this.noMoreAvaillableRider++;
        }

        if (MatchEntity.REJECTED.equals(r)) {
            this.rejectedRider++;
        }

        /**
         * Both..
         */
        if (MatchEntity.REJECTED.equals(r) && MatchEntity.REJECTED.equals(d)) {
            this.acceptedBoth++;
        }
    }

    /**
     * Add statistics for all the MatchEntities in the list. Typically think of
     * the list as the list of matchings for a given offer or request.
     *
     *
     *
     * @param mList List of MatchEntity for which the statistics should be
     * updated
     */
    public void statisticsFromList(List<MatchEntity> mList) {

        for (MatchEntity m : mList) {
            this.addMatchingToStatistics(m);
        }
    }

    /**
     * @return true, if any rider has already accepted this match
     */
    boolean getIsRiderAccepted() {
        return this.getAcceptedRider() > 0;
    }

    /**
     * @return true, if any rider has already accepted this match
     */
    boolean getIsDriverAccepted() {
        return this.getAcceptedDriver() > 0;
    }

    /**
     * @return true, if for at least one match both rider and driver have
     * already accepted this match
     */
    boolean getIsBothAccepted() {
        return this.getAcceptedBoth() > 0;
    }

    /**
     * Human readable debug output for Matching statistics
     *
     * @return
     */
    public String toString() {

        StringBuilder buf = new StringBuilder();
        buf.append("\n===MatchingStatistics========================");
        buf.append("\nNumber of Matches  : "+this.getNumberOfMatches());
        buf.append("\nAccepted from both : " + this.getAcceptedBoth());
        buf.append("\nState              :  Driver , Rider ");
        buf.append("\nNOT_ADAPTED        : " + getNotAdaptedDriver() + " , " + this.getNotAdaptedDriver());
        buf.append("\nACCEPTED           : " + getAcceptedDriver() + " , " + this.getAcceptedDriver());
        buf.append("\nREJECTED           : " + getRejectedDriver() + " , " + this.getRejectedDriver());
        buf.append("\nCOUNTERMANDED      : " + getCountermandedDriver() + " , " + this.getCountermandedDriver());
        buf.append("\nNO_MORE_AVAILLABLE : " + getRejectedDriver() + " , " + this.getRejectedDriver());
        buf.append("\n=============================================");

        return buf.toString();
    }
    
    
    
    
    /**
     * Calculate the state of negotians for this drive. This is done by
     * evaluating the matches
     *
     * @return calculated State, see above
     *
     */
    public DriveNegotiationConstants getDriveMatchingState() {

        if (this.getNumberOfMatches() == 0) {
            return DriveNegotiationConstants.STATE_NEW;
        }

        if (this.getAcceptedBoth() > 0) {
            return DriveNegotiationConstants.STATE_CONFIRMED;
        }

        if (this.getAcceptedDriver() > 0) {
            return DriveNegotiationConstants.STATE_DRIVER_ACCEPTED;
        }

        if (this.getAcceptedRider() > 0) {
            return DriveNegotiationConstants.STATE_RIDER_REQUESTED;
        }

        return DriveNegotiationConstants.STATE_UNCLEAR;
    }
    
    
    
        
    /**
     * Determines wether route for a driverundertakesrideentity can be edited or not.
     * I.e: wether or not waypoints can be added or removed.
     * 
     * Waypoints can be added or removed as long as there are no confirmed requests.
     * 
     * 
     * 
     * @returns true, if state is one of STATE_NEW, 
     * STATE_RIDER_REQUESTED, else false
     *
     */
    public boolean getCanEditRoute() {

        if(this.getDriveMatchingState()==DriveNegotiationConstants.STATE_NEW) return true;
        if(this.getDriveMatchingState()==DriveNegotiationConstants.STATE_RIDER_REQUESTED) return true;
        
        return false;
    }
    
    
        
}
