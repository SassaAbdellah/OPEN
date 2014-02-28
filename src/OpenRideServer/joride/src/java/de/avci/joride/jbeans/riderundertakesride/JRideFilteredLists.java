/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.riderundertakesride;

import de.fhg.fokus.openride.matching.MatchingStatistics;
import de.fhg.fokus.openride.matching.RideNegotiationConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * A JBean class that can sort an unordered list of
 * JRideerundertakesRideEntities into a number of lists corresponding to the
 * respective rider states
 * (NEW,RIDER_REQUESTED,DRIVER_ACCEPTED,BOTH_ACCEPTED,COUNTERMANDED)
 *
 * Als
 *
 *
 *
 * @author jochen
 */
@Named("filteredList")
@SessionScoped
public class JRideFilteredLists implements Serializable {

    /**
     * One Comparator to sort whatever Lists of JRideUndertakesRideEntity we'll
     * have to handle.
     */
    StartTimeFirstSorter sorter = new StartTimeFirstSorter();
    /**
     * All Rides that this List manages, no filtering by state
     */
    private List<JRiderUndertakesRideEntity> allRides;

    public List<JRiderUndertakesRideEntity> getAllRides() {
        return this.allRides;
    }

    /**
     * Nontrivial setter. In addition to setting the list, it will also take
     * care to sort it by ride.startTimeFirst.
     *
     * @param argList
     */
    public void setAllRides(List<JRiderUndertakesRideEntity> argList) {
        this.allRides = argList;
        this.update();
    }

    /**
     * @return size of the "allRides" list.
     */
    public int getNumberOfAllRides() {
        return this.getAllRides().size();
    }

    /**
     * @return true, if there are rides at all
     */
    public boolean hasRides() {
        return this.getAllRides().size() > 0;
    }

    /**
     * Resort all the rides into respective Lists by Category this is called
     * after setAllRides(...) and may be called programmatically, if some states
     * are likely to have changed.
     *
     */
    private void update() {


       
        this.newRides                  = new ArrayList<JRiderUndertakesRideEntity>();
        this.riderRequestedRides       = new ArrayList<JRiderUndertakesRideEntity>();
        this.driverAcceptedRides       = new ArrayList<JRiderUndertakesRideEntity>();
        this.bothAcceptedRides         = new ArrayList<JRiderUndertakesRideEntity>();
      
        this.riderRejectedRides       = new ArrayList<JRiderUndertakesRideEntity>();
        this.driverRejectedRides       = new ArrayList<JRiderUndertakesRideEntity>();
        this.bothRejectedRides         = new ArrayList<JRiderUndertakesRideEntity>();
        
        this.riderCountermandedRides   = new ArrayList<JRiderUndertakesRideEntity>();
        this.driverCountermandedRides  = new ArrayList<JRiderUndertakesRideEntity>();
        this.bothCountermandedRides  = new ArrayList<JRiderUndertakesRideEntity>();
   
        
        
        this.unavaillableRides         = new ArrayList<JRiderUndertakesRideEntity>();
        this.unclearRides              = new ArrayList<JRiderUndertakesRideEntity>();
        this.updatedRides              = new ArrayList<JRiderUndertakesRideEntity>();

        
        
        
        //sorting list here means that all the sublists will be sorted too
        Collections.sort(this.getAllRides(), sorter);

        for (JRiderUndertakesRideEntity ride : this.getAllRides()) {

            MatchingStatistics ms = ride.getMatchingStatistics();

        
            RideNegotiationConstants state = ms.getRideMatchingState();


            if (state == RideNegotiationConstants.STATE_NEW) {
                newRides.add(ride);
            }

            // *********** Requested/Accepted/Confirmed
            
            if (state == RideNegotiationConstants.STATE_RIDER_REQUESTED) {
                riderRequestedRides.add(ride);
            }

            if (state == RideNegotiationConstants.STATE_DRIVER_ACCEPTED) {
                driverAcceptedRides.add(ride);
            }

            if (state == RideNegotiationConstants.STATE_CONFIRMED_BOTH) {
                bothAcceptedRides.add(ride);
            }
            
            
             // *********** Rejected rides *******
            
            if (state == RideNegotiationConstants.STATE_RIDER_REJECTED) {
                riderRequestedRides.add(ride);
            }

            if (state == RideNegotiationConstants.STATE_DRIVER_REJECTED) {
                driverRejectedRides.add(ride);
            }

            if (state == RideNegotiationConstants.STATE_REJECTED_BOTH) {
                bothRejectedRides.add(ride);
            }
            
            
            // ********* Countermanded Rides  ******
            
            if (state == RideNegotiationConstants.STATE_COUNTERMANDED_RIDER) {
                riderCountermandedRides.add(ride);
            }
            
            if (state == RideNegotiationConstants.STATE_COUNTERMANDED_DRIVER) {
                driverCountermandedRides.add(ride);
            }

            
            //********** ugly states ******************
            
            if (state == RideNegotiationConstants.STATE_UNAVAILLABLE) {
                unavaillableRides.add(ride);
            }

            if (state == RideNegotiationConstants.STATE_UNCLEAR) {
                unclearRides.add(ride);
            }

            if (ride.getRideUpdated()) {
                this.updatedRides.add(ride);
            }


        } // for 

    }
    /**
     * all new rides, i.e those that have both rider and DriverState nonAdapted
     */
    private List<JRiderUndertakesRideEntity>  newRides = null;

    public List<JRiderUndertakesRideEntity> getNewRides() {
        return this.newRides;
    }

    /**
     * @return size of the newRides list
     */
    public int getNumberOfNewRides() {
        return this.getNewRides().size();
    }

    /**
     * @return true, if there are rides of state "new", else false
     */
    public boolean hasNewRides() {
        return this.getNewRides().size() > 0;
    }
    /**
     * all rider-accepted rides, i.e those that have been accepted by rider, but
     * not by driver
     */
    private List<JRiderUndertakesRideEntity>  riderRequestedRides = null;

    public List<JRiderUndertakesRideEntity> getRiderRequestedRides() {
        return this.riderRequestedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfRiderRequestedRides() {
        return this.getRiderRequestedRides().size();
    }

    /**
     * @return true, if there are rides of state "rider requested" else false
     */
    public boolean hasRiderRequestedRides() {
        return this.getRiderRequestedRides().size() > 0;
    }
    /**
     * all driver-accepted rides, i.e those that have been accepted by driver,
     * but not by rider
     */
    private List<JRiderUndertakesRideEntity>  driverAcceptedRides = null;

    public List<JRiderUndertakesRideEntity> getDriverAcceptedRides() {
        return this.driverAcceptedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfDriverAcceptedRides() {
        return this.getDriverAcceptedRides().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasDriverAcceptedRides() {
        return this.getDriverAcceptedRides().size() > 0;
    }
    /**
     * all Rides accepted by both, rider and driver
     */
    private List <JRiderUndertakesRideEntity>  bothAcceptedRides = null;

    public List<JRiderUndertakesRideEntity> getBothAcceptedRides() {
        return this.bothAcceptedRides;
    }

    /**
     * @return size of the bothAcceptedRides list
     */
    public int getNumberOfBothAcceptedRides() {
        return this.getBothAcceptedRides().size();
    }

    /**
     * @return true, if there are rides of state "accepted both", else false
     */
    public boolean hasBothAcceptedRides() {
        return this.getBothAcceptedRides().size() > 0;
    }
    
    
        /**
     * all rider-accepted rides, i.e those that have been accepted by rider, but
     * not by driver
     */
    private List<JRiderUndertakesRideEntity> riderRejectedRides = null;

    public List<JRiderUndertakesRideEntity> getRiderRejectedRides() {
        return this.riderRejectedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfRiderRejectedRides() {
        return this.getRiderRejectedRides().size();
    }

    /**
     * @return true, if there are rides of state "rider requested" else false
     */
    public boolean hasRiderRejectedRides() {
        return this.getRiderRejectedRides().size() > 0;
    }
    /**
     * all driver-accepted rides, i.e those that have been accepted by driver,
     * but not by rider
     */
    private List<JRiderUndertakesRideEntity>  driverRejectedRides = null;

    public List<JRiderUndertakesRideEntity> getDriverRejectedRides() {
        return this.driverRejectedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfDriverRejectedRides() {
        return this.getDriverRejectedRides().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasDriverRejectedRides() {
        return this.getDriverRejectedRides().size() > 0;
    }
    /**
     * all Rides accepted by both, rider and driver
     */
    private List<JRiderUndertakesRideEntity> bothRejectedRides = null;

    public List<JRiderUndertakesRideEntity> getBothRejectedRides() {
        return this.bothRejectedRides;
    }

    /**
     * @return size of the bothAcceptedRides list
     */
    public int getNumberOfBothRejectedRides() {
        return this.getBothRejectedRides().size();
    }

    /**
     * @return true, if there are rides of state "accepted both", else false
     */
    public boolean hasBothRejectedRides() {
        return this.getBothRejectedRides().size() > 0;
    }
    
    
    
    
    
    
    
    /**
     * all Rides countermanded by rider.
     */
    private List<JRiderUndertakesRideEntity> riderCountermandedRides = null;

    public List<JRiderUndertakesRideEntity> getRiderCountermandedRides() {
        return this.riderCountermandedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfRiderCountermandedRides() {
        return this.getRiderCountermandedRides().size();
    }

    /**
     * @return true, if there are rides of state countermanded, else false
     */
    public boolean hasRiderCountermandedRides() {
        return this.getRiderCountermandedRides().size() > 0;
    }
    
    /**
     * all Rides countermanded by driver.
     */
    private List<JRiderUndertakesRideEntity>  driverCountermandedRides = null;

    public List<JRiderUndertakesRideEntity> getDriverCountermandedRides() {
        return this.driverCountermandedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfDriverCountermandedRides() {
        return this.getDriverCountermandedRides().size();
    }

    /**
     * @return true, if there are rides of state countermanded, else false
     */
    public boolean hasDriverCountermandedRides() {
        return this.getDriverCountermandedRides().size() > 0;
    }
    
     /**
     * all Rides countermanded by both, rider and driver.
     */
    private List<JRiderUndertakesRideEntity>  bothCountermandedRides = null;

    public List<JRiderUndertakesRideEntity> getBothCountermandedRides() {
        return this.bothCountermandedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfBothCountermandedRides() {
        return this.getBothCountermandedRides().size();
    }

    /**
     * @return true, if there are rides of state countermanded, else false
     */
    public boolean hasBothCountermandedRides() {
        return this.getBothCountermandedRides().size() > 0;
    }
    
    
    
    
    /**
     * all unavaillable rides, probably rarely displayed to outside, but maybe
     * useful for debugging.
     *
     */
    private List<JRiderUndertakesRideEntity> unavaillableRides;

    public List<JRiderUndertakesRideEntity> getUnavaillableRides() {
        return this.unavaillableRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfUnavaillableRides() {
        return this.getUnavaillableRides().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasUnavaillableRides() {
        return this.getUnavaillableRides().size() > 0;
    }
    /**
     * all rides with state unclear, probably seldom displayed to outside, but
     * maybe useful for debugging.
     *
     */
    private List<JRiderUndertakesRideEntity> unclearRides;

    public List<JRiderUndertakesRideEntity> getUnclearRides() {
        return this.unclearRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfUnclearRides() {
        return this.getUnclearRides().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasUnclearRides() {
        return this.getUnclearRides().size() > 0;
    }
    /**
     * List of all rides that got an update.
     *
     *
     */
    private List<JRiderUndertakesRideEntity> updatedRides;

    public List<JRiderUndertakesRideEntity> getUpdatedRides() {
        return this.updatedRides;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfUpdatedRides() {
        return this.getUpdatedRides().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasUpdatedRides() {
        return this.getUpdatedRides().size() > 0;
    }
    
    
    public String getDebugPrintout(){
        
        StringBuffer buf=new StringBuffer();
        
        buf.append("\nRideList Debug  Output : \n");
        
        buf.append("\n");
        buf.append("\nALL Rides : " );
        buf.append("\nNumber of : "+this.getNumberOfAllRides());
        buf.append("\nExist     : "+this.hasRides());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nNEW Rides : " );
        buf.append("\nNumber of : "+this.getNumberOfNewRides());
        buf.append("\nExist     : "+this.hasNewRides());
        buf.append("\n");
        
        // Requested/Accepted/Confirmed
        buf.append("\n");
        buf.append("\nRIDER REQUESTED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfRiderRequestedRides());
        buf.append("\nExist     : "+this.hasRiderRequestedRides());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nDRIVER ACCEPTED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfDriverAcceptedRides());
        buf.append("\nExist     : "+this.hasDriverAcceptedRides());
        buf.append("\n");
         buf.append("\n");
        buf.append("\nBOTH ACCEPTED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfBothAcceptedRides());
        buf.append("\nExist     : "+this.hasBothAcceptedRides());
        buf.append("\n");
        
        
        // Rejected
        buf.append("\n");
        buf.append("\nRIDER REJECTED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfRiderRejectedRides());
        buf.append("\nExist     : "+this.hasRiderRejectedRides());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nDRIVER REJECTED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfDriverRejectedRides());
        buf.append("\nExist     : "+this.hasDriverRejectedRides());
        buf.append("\n");
         buf.append("\n");
        buf.append("\nBOTH REJECTED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfBothRejectedRides());
        buf.append("\nExist     : "+this.hasBothRejectedRides());
        buf.append("\n");
           
        buf.append("\n");
        buf.append("\nRIDER COUNTERMANDED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfRiderCountermandedRides());
        buf.append("\nExist     : "+this.hasRiderCountermandedRides());
        buf.append("\n");  
        buf.append("\nDRIVER COUNTERMANDED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfDriverCountermandedRides());
        buf.append("\nExist     : "+this.hasDriverCountermandedRides());
        buf.append("\n");   buf.append("\n");  
        buf.append("\nBOTH COUNTERMANDED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfBothCountermandedRides());
        buf.append("\nExist     : "+this.hasBothCountermandedRides());
        buf.append("\n");  
        
        buf.append("\n");
        buf.append("\nUNAVAILLABLE Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfUnavaillableRides());
        buf.append("\nExist     : "+this.hasUnavaillableRides());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nUPDATED Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfUpdatedRides());
        buf.append("\nExist     : "+this.hasUpdatedRides());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nUNCLEAR Rides: " );
        buf.append("\nNumber of : "+this.getNumberOfUnclearRides());
        buf.append("\nExist     : "+this.hasUnclearRides());
        buf.append("\n");
        
        return buf.toString();
    }

  
    public void updateFutureRides(){
        new JRiderUndertakesRideEntityService().updateJFilteredRideList(this);
    }
    
    /**
     * Comparator to sort lists of JRiderUndertakesRideEntities by starttime
     * earliest.
     */
    class StartTimeFirstSorter implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {

            if (!(o1 instanceof JRiderUndertakesRideEntity)) {
                throw new Error("StartTimeFirstSorter cannot sort anything else but Rides!");
            }

            if (!(o2 instanceof JRiderUndertakesRideEntity)) {
                throw new Error("StartTimeFirstSorter cannot sort anything else but Rides!");
            }

            JRiderUndertakesRideEntity j1 = (JRiderUndertakesRideEntity) o1;
            JRiderUndertakesRideEntity j2 = (JRiderUndertakesRideEntity) o2;

            Long t1 = j1.getStarttimeEarliest().getTime();
            Long t2 = j2.getStarttimeEarliest().getTime();

            if (t1 > t2) {
                return 1;
            }
            if (t2 < t1) {
                return -1;
            }
            return 0;
        }
    } // Comparator
}
