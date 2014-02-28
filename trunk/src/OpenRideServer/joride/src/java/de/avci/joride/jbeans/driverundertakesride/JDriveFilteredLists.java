/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

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
@Named("filteredDriveLists")
@SessionScoped
public class JDriveFilteredLists implements Serializable {

    /**
     * One Comparator to sort whatever Lists of JRideUndertakesRideEntity we'll
     * have to handle.
     */
    StartTimeFirstSorter sorter = new StartTimeFirstSorter();
    /**
     * All Rides that this List manages, no filtering by state
     */
    private List<JDriverUndertakesRideEntity> allDrives;

    public List<JDriverUndertakesRideEntity> getAllDrives() {
        return this.allDrives;
    }

    /**
     * Nontrivial setter. In addition to setting the list, it will also take
     * care to sort it by ride.startTimeFirst.
     *
     * @param argList
     */
    public void setAllDrives(List<JDriverUndertakesRideEntity> argList) {
        this.allDrives = argList;
        this.update();
    }

    /**
     * @return size of the "allRides" list.
     */
    public int getNumberOfAllDrives() {
        return this.getAllDrives().size();
    }

    /**
     * @return true, if there are rides at all
     */
    public boolean hasDrives() {
        return this.getAllDrives().size() > 0;
    }

    /**
     * Resort all the rides into respective Lists by Category this is called
     * after setAllRides(...) and may be called programmatically, if some states
     * are likely to have changed.
     *
     */
    private void update() {



        this.newDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.riderRequestedDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.driverAcceptedDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.bothAcceptedDrives = new ArrayList<JDriverUndertakesRideEntity>();

        this.riderRejectedDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.driverRejectedDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.bothRejectedDrives = new ArrayList<JDriverUndertakesRideEntity>();

        this.riderCountermandedDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.driverCountermandedDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.bothCountermandedDrives = new ArrayList<JDriverUndertakesRideEntity>();



        this.unavaillableDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.unclearDrives = new ArrayList<JDriverUndertakesRideEntity>();
        this.updatedDrives = new ArrayList<JDriverUndertakesRideEntity>();




        //sorting list here means that all the sublists will be sorted too
        Collections.sort(this.getAllDrives(), sorter);

        for (JDriverUndertakesRideEntity drive : this.getAllDrives()) {

            MatchingStatistics ms = drive.getMatchingStatistics();


            RideNegotiationConstants state = ms.getRideMatchingState();


            if (state == RideNegotiationConstants.STATE_NEW) {
                newDrives.add(drive);
            }

            // *********** Requested/Accepted/Confirmed

            if (state == RideNegotiationConstants.STATE_RIDER_REQUESTED) {
                riderRequestedDrives.add(drive);
            }

            if (state == RideNegotiationConstants.STATE_DRIVER_ACCEPTED) {
                driverAcceptedDrives.add(drive);
            }

            if (state == RideNegotiationConstants.STATE_CONFIRMED_BOTH) {
                bothAcceptedDrives.add(drive);
            }


            // *********** Rejected rides *******

            if (state == RideNegotiationConstants.STATE_RIDER_REJECTED) {
                riderRequestedDrives.add(drive);
            }

            if (state == RideNegotiationConstants.STATE_DRIVER_REJECTED) {
                driverRejectedDrives.add(drive);
            }

            if (state == RideNegotiationConstants.STATE_REJECTED_BOTH) {
                bothRejectedDrives.add(drive);
            }


            // ********* Countermanded Rides  ******

            if (state == RideNegotiationConstants.STATE_COUNTERMANDED_RIDER) {
                riderCountermandedDrives.add(drive);
            }

            if (state == RideNegotiationConstants.STATE_COUNTERMANDED_DRIVER) {
                driverCountermandedDrives.add(drive);
            }


            //********** ugly states ******************

            if (state == RideNegotiationConstants.STATE_UNAVAILLABLE) {
                unavaillableDrives.add(drive);
            }

            if (state == RideNegotiationConstants.STATE_UNCLEAR) {
                unclearDrives.add(drive);
            }

            if (drive.getDriveUpdated()) {
                this.updatedDrives.add(drive);
            }


        } // for 

    }
    /**
     * all new rides, i.e those that have both rider and DriverState nonAdapted
     */
    private List<JDriverUndertakesRideEntity> newDrives = null;

    public List<JDriverUndertakesRideEntity> getNewDrives() {
        return this.newDrives;
    }

    /**
     * @return size of the newRides list
     */
    public int getNumberOfNewDrives() {
        return this.getNewDrives().size();
    }

    /**
     * @return true, if there are rides of state "new", else false
     */
    public boolean hasNewDrives() {
        return this.getNewDrives().size() > 0;
    }
    /**
     * all rider-accepted rides, i.e those that have been accepted by rider, but
     * not by driver
     */
    private List <JDriverUndertakesRideEntity>  riderRequestedDrives = null;

    public List<JDriverUndertakesRideEntity> getRiderRequestedDrives() {
        return this.riderRequestedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfRiderRequestedDrives() {
        return this.getRiderRequestedDrives().size();
    }

    /**
     * @return true, if there are rides of state "rider requested" else false
     */
    public boolean hasRiderRequestedRides() {
        return this.getRiderRequestedDrives().size() > 0;
    }
    /**
     * all driver-accepted rides, i.e those that have been accepted by driver,
     * but not by rider
     */
    private List <JDriverUndertakesRideEntity>  driverAcceptedDrives = null;

    public List<JDriverUndertakesRideEntity> getDriverAcceptedDrives() {
        return this.driverAcceptedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfDriverAcceptedDrives() {
        return this.getDriverAcceptedDrives().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasDriverAcceptedDrives() {
        return this.getDriverAcceptedDrives().size() > 0;
    }
    /**
     * all Rides accepted by both, rider and driver
     */
    private List <JDriverUndertakesRideEntity>  bothAcceptedDrives = null;

    public List<JDriverUndertakesRideEntity> getBothAcceptedDrives() {
        return this.bothAcceptedDrives;
    }

    /**
     * @return size of the bothAcceptedRides list
     */
    public int getNumberOfBothAcceptedDrives() {
        return this.getBothAcceptedDrives().size();
    }

    /**
     * @return true, if there are rides of state "accepted both", else false
     */
    public boolean hasBothAcceptedDrives() {
        return this.getBothAcceptedDrives().size() > 0;
    }
    /**
     * all rider-accepted rides, i.e those that have been accepted by rider, but
     * not by driver
     */
    private List <JDriverUndertakesRideEntity>  riderRejectedDrives = null;

    public List<JDriverUndertakesRideEntity> getRiderRejectedDrives() {
        return this.riderRejectedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfRiderRejectedDrives() {
        return this.getRiderRejectedDrives().size();
    }

    /**
     * @return true, if there are rides of state "rider requested" else false
     */
    public boolean hasRiderRejectedDrives() {
        return this.getRiderRejectedDrives().size() > 0;
    }
    /**
     * all driver-accepted rides, i.e those that have been accepted by driver,
     * but not by rider
     */
    private List <JDriverUndertakesRideEntity> driverRejectedDrives = null;

    public List<JDriverUndertakesRideEntity> getDriverRejectedDrives() {
        return this.driverRejectedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfDriverRejectedDrives() {
        return this.getDriverRejectedDrives().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasDriverRejectedDrives() {
        return this.getDriverRejectedDrives().size() > 0;
    }
    /**
     * all Rides accepted by both, rider and driver
     */
    private List <JDriverUndertakesRideEntity> bothRejectedDrives = null;

    public List<JDriverUndertakesRideEntity> getBothRejectedDrives() {
        return this.bothRejectedDrives;
    }

    /**
     * @return size of the bothAcceptedRides list
     */
    public int getNumberOfBothRejectedDrives() {
        return this.getBothRejectedDrives().size();
    }

    /**
     * @return true, if there are rides of state "accepted both", else false
     */
    public boolean hasBothRejectedDrives() {
        return this.getBothRejectedDrives().size() > 0;
    }
    /**
     * all Rides countermanded by rider.
     */
    private List <JDriverUndertakesRideEntity> riderCountermandedDrives = null;

    public List<JDriverUndertakesRideEntity> getRiderCountermandedDrives() {
        return this.riderCountermandedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfRiderCountermandedDrives() {
        return this.getRiderCountermandedDrives().size();
    }

    /**
     * @return true, if there are rides of state countermanded, else false
     */
    public boolean hasRiderCountermandedDrives() {
        return this.getRiderCountermandedDrives().size() > 0;
    }
    /**
     * all Rides countermanded by driver.
     */
    private List <JDriverUndertakesRideEntity> driverCountermandedDrives = null;

    public List<JDriverUndertakesRideEntity> getDriverCountermandedDrives() {
        return this.driverCountermandedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfDriverCountermandedDrives() {
        return this.getDriverCountermandedDrives().size();
    }

    /**
     * @return true, if there are rides of state countermanded, else false
     */
    public boolean hasDriverCountermandedDrives() {
        return this.getDriverCountermandedDrives().size() > 0;
    }
    /**
     * all Rides countermanded by both, rider and driver.
     */
    private List <JDriverUndertakesRideEntity>  bothCountermandedDrives = null;

    public List<JDriverUndertakesRideEntity> getBothCountermandedDrives() {
        return this.bothCountermandedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfBothCountermandedDrives() {
        return this.getBothCountermandedDrives().size();
    }

    /**
     * @return true, if there are rides of state countermanded, else false
     */
    public boolean hasBothCountermandedDrives() {
        return this.getBothCountermandedDrives().size() > 0;
    }
    /**
     * all unavaillable rides, probably rarely displayed to outside, but maybe
     * useful for debugging.
     *
     */
    private List<JDriverUndertakesRideEntity> unavaillableDrives;

    public List<JDriverUndertakesRideEntity> getUnavaillableDrives() {
        return this.unavaillableDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfUnavaillableDrives() {
        return this.getUnavaillableDrives().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasUnavaillableDrives() {
        return this.getUnavaillableDrives().size() > 0;
    }
    /**
     * all rides with state unclear, probably seldom displayed to outside, but
     * maybe useful for debugging.
     *
     */
    private List<JDriverUndertakesRideEntity> unclearDrives;

    public List<JDriverUndertakesRideEntity> getUnclearDrives() {
        return this.unclearDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfUnclearDrives() {
        return this.getUnclearDrives().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasUnclearDrives() {
        return this.getUnclearDrives().size() > 0;
    }
    /**
     * List of all rides that got an update.
     *
     *
     */
    private List <JDriverUndertakesRideEntity> updatedDrives;

    public List<JDriverUndertakesRideEntity> getUpdatedDrives() {
        return this.updatedDrives;
    }

    /**
     * @return size of the acceptedRides list
     */
    public int getNumberOfUpdatedDrives() {
        return this.getUpdatedDrives().size();
    }

    /**
     * @return true, if there are rides of state unclear, else false
     */
    public boolean hasUpdatedDrives() {
        return this.getUpdatedDrives().size() > 0;
    }

    public String getDebugPrintout() {

        StringBuffer buf = new StringBuffer();

        buf.append("\nRideList Debug  Output : \n");

        buf.append("\n");
        buf.append("\nALL Rides : ");
        buf.append("\nNumber of : " + this.getNumberOfAllDrives());
        buf.append("\nExist     : " + this.hasDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nNEW Rides : ");
        buf.append("\nNumber of : " + this.getNumberOfNewDrives());
        buf.append("\nExist     : " + this.hasNewDrives());
        buf.append("\n");

        // Requested/Accepted/Confirmed
        buf.append("\n");
        buf.append("\nRIDER REQUESTED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfRiderRequestedDrives());
        buf.append("\nExist     : " + this.hasRiderRequestedRides());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nDRIVER ACCEPTED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfDriverAcceptedDrives());
        buf.append("\nExist     : " + this.hasDriverAcceptedDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nBOTH ACCEPTED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfBothAcceptedDrives());
        buf.append("\nExist     : " + this.hasBothAcceptedDrives());
        buf.append("\n");


        // Rejected
        buf.append("\n");
        buf.append("\nRIDER REJECTED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfRiderRejectedDrives());
        buf.append("\nExist     : " + this.hasRiderRejectedDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nDRIVER REJECTED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfDriverRejectedDrives());
        buf.append("\nExist     : " + this.hasDriverRejectedDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nBOTH REJECTED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfBothRejectedDrives());
        buf.append("\nExist     : " + this.hasBothRejectedDrives());
        buf.append("\n");

        buf.append("\n");
        buf.append("\nRIDER COUNTERMANDED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfRiderCountermandedDrives());
        buf.append("\nExist     : " + this.hasRiderCountermandedDrives());
        buf.append("\n");
        buf.append("\nDRIVER COUNTERMANDED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfDriverCountermandedDrives());
        buf.append("\nExist     : " + this.hasDriverCountermandedDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nBOTH COUNTERMANDED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfBothCountermandedDrives());
        buf.append("\nExist     : " + this.hasBothCountermandedDrives());
        buf.append("\n");

        buf.append("\n");
        buf.append("\nUNAVAILLABLE Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfUnavaillableDrives());
        buf.append("\nExist     : " + this.hasUnavaillableDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nUPDATED Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfUpdatedDrives());
        buf.append("\nExist     : " + this.hasUpdatedDrives());
        buf.append("\n");
        buf.append("\n");
        buf.append("\nUNCLEAR Rides: ");
        buf.append("\nNumber of : " + this.getNumberOfUnclearDrives());
        buf.append("\nExist     : " + this.hasUnclearDrives());
        buf.append("\n");

        return buf.toString();
    }

    /**
     * TODO: implement!
     *
     *
     * public void updateFutureRide(){ new
     * JRiderUndertakesRideEntityService().updateJFilteredRideList(this); }
     */
    /**
     * Comparator to sort lists of JDriverUndertakesRideEntities by starttime
     *
     */
    class StartTimeFirstSorter implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {

            if (!(o1 instanceof JDriverUndertakesRideEntity)) {
                throw new Error("StartTimeFirstSorter cannot sort anything else but Rides!");
            }

            if (!(o2 instanceof JDriverUndertakesRideEntity)) {
                throw new Error("StartTimeFirstSorter cannot sort anything else but Rides!");
            }

            JDriverUndertakesRideEntity j1 = (JDriverUndertakesRideEntity) o1;
            JDriverUndertakesRideEntity j2 = (JDriverUndertakesRideEntity) o2;

            Long t1 = j1.getRideStarttime().getTime();
            Long t2 = j2.getRideStarttime().getTime();

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
