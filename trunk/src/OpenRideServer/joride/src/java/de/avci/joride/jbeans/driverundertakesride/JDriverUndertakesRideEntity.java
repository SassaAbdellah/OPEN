/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.utils.CRUDConstants;
import de.avci.joride.utils.HTTPRequestUtil;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;

import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import org.postgis.Point;

/**
 * Small Wrapper class making Entity Bean CustomerEntity availlable as a CDI
 * Bean for Use in JSF Frontend.
 *
 * @author jochen
 *
 */
@Named
@RequestScoped
public class JDriverUndertakesRideEntity extends de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity {

    /**
     * Get a list of active drives for this driver.
     *
     * @return
     */
    public List<DriverUndertakesRideEntity> getActiveDrivesforDriver() {
        return (new JDriverUndertakesRideEntityService()).getActiveDrivesForDriver();
    }

    /**
     * Get a list with all Open Drives for this driver.
     *
     * TODO: what actually is an "open" Drive.
     *
     * @return
     */
    public List<DriverUndertakesRideEntity> getOpenDrivesForDriver() {
        return (new JDriverUndertakesRideEntityService()).getOpenDrivesForDriver();
    }

    /**
     * Get a list with all Drives for this driver.
     *
     * TODO: what actually is an "open" Drive.
     *
     * @return
     */
    public List<JDriverUndertakesRideEntity> getDrivesForDriver() {


        List<DriverUndertakesRideEntity> inlist = (new JDriverUndertakesRideEntityService()).getDrivesForDriver();

        return this.castList(inlist);
    }

    /**
     * Cast a list of DriverUndertakesRideEntity Objects into a list of
     * JDriverUndertakesRideEntity Objects, for easy display.
     *
     *
     * @param inlist
     * @return
     */
    protected List<JDriverUndertakesRideEntity> castList(List<DriverUndertakesRideEntity> inlist) {


        List<JDriverUndertakesRideEntity> res = new LinkedList<JDriverUndertakesRideEntity>();

        Iterator<DriverUndertakesRideEntity> it = inlist.iterator();

        while (it.hasNext()) {

            JDriverUndertakesRideEntity jdure = new JDriverUndertakesRideEntity();
            jdure.updateFromDB(it.next());

            res.add(jdure);
        }

        return res;
    }
    /**
     * A date format for formatting start and end date. Created via lazy
     * instantiation.
     */
    protected DateFormat dateFormat;

    /**
     * Accessor with lazy instantiation
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
        return getDateFormat().format(this.getRideStarttime());
    }

    /**
     * Update this Object's data from the Database.
     *
     * The id of the Object to be updated is read in from http-request
     *
     */
    public void updateFromDB() {

        String idStr = (new HTTPRequestUtil()).getParameterSingleValue(new CRUDConstants().getParamNameCrudId());

        int id = 0;

        try {
            id = new Integer(idStr).intValue();
        } catch (java.lang.NumberFormatException exc) {
            throw new Error("ID Parameter does not contain Numeric Value " + idStr);
        }


        DriverUndertakesRideEntity drue = new JDriverUndertakesRideEntityService().getDriveByIdSavely(id);

        JDriverUndertakesRideEntity jdrue = new JDriverUndertakesRideEntity();
        this.updateFromDB(drue);

    }

    /**
     * Update from a given DriverUndertakesRideEntity object.
     *
     * @param dure
     */
    public void updateFromDB(DriverUndertakesRideEntity dure) {

        this.setCustId(dure.getCustId());
        this.setEndptAddress(dure.getEndptAddress());
        this.setRideAcceptableDetourInKm(dure.getRideAcceptableDetourInKm());
        this.setRideAcceptableDetourInMin(dure.getRideAcceptableDetourInMin());
        this.setRideAcceptableDetourInPercent(dure.getRideAcceptableDetourInPercent());
        this.setRideComment(dure.getRideComment());
        this.setRideCurrpos(dure.getRideCurrpos());
        this.setRideEndpt(dure.getRideEndpt());
        this.setRideId(dure.getRideId());
        this.setRideOfferedseatsNo(dure.getRideOfferedseatsNo());
        this.setRideRoutePointDistanceMeters(dure.getRideRoutePointDistanceMeters());
        this.setRideSeriesId(dure.getRideSeriesId());
        this.setRideStartpt(dure.getRideStartpt());
        this.setRideStarttime(dure.getRideStarttime());
        this.setRideWeekdays(dure.getRideWeekdays());
        this.setRiderUndertakesRideEntityCollection(dure.getRiderUndertakesRideEntityCollection());
        this.setStartptAddress(dure.getStartptAddress());
    }

    /**
     * Get the Route Points for this Drive wrapped in a JRoutPointsEntity Object
     *
     * @return
     */
    public JRoutePointsEntity getRoutePoints() {


        int rideID = this.getRideId();
        return new JDriverUndertakesRideEntityService().getRoutePointsForDrive(rideID);

    }

    /**
     * Get the RoutePoints for this Drive encoded in a JSONString
     *
     * @return
     */
    public String getRoutePointsAsJSON() {
        return this.getRoutePoints().getRoutePointsAsJSON();
    }
    /**
     * Value for point.target parameters. If "Startpoint" ist set, then
     * smartUpdate will set the startpoint
     */
    private static final String paramValueStartpoint = "STARTPOINT";

    /**
     * Trivial Accessor making paramValueStartpoint accessible with JSF Methods
     *
     * @return
     */
    public String getParamValueStartpoint() {
        return paramValueStartpoint;
    }
    /**
     * Value for point.target parameters. If "Endpoint" ist set, then
     * smartUpdate will set the startpoint
     */
    private static final String paramValueEndpoint = "ENDPOINT";

    /**
     * Trivial Accessor making paramValueStartpoint accessible with JSF Methods
     *
     * @return
     */
    public String getParamValueEndpoint() {
        return paramValueEndpoint;
    }
    /**
     * Flag to signify that this object has not been initialized.
     *
     */
    private boolean initializedFlag = false;

    /**
     * Initialize a newly created JDriverUndertakesRideEntity
     *
     */
    public void initialize() {

        if (initializedFlag) {
            return;
        }

        // set starttime to current Time
        super.setRideStarttime(new Date(System.currentTimeMillis()));
        super.setStartptAddress("TODO: init StartpointAddress");
        super.setEndptAddress("TODO: init EndpointAddress");
        super.setRideStartpt(new Point());
        super.setRideEndpt(new Point());

        // mark this as initialized
        initializedFlag = true;

    }
} // class 
