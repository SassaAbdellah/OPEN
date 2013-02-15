/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.riderundertakesride;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.auxiliary.TimeIntervalBean;
import de.avci.joride.jbeans.customerprofile.JCustomerEntity;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.jbeans.matching.JMatchingEntity;
import de.avci.joride.jbeans.matching.JMatchingEntityService;
import de.avci.joride.utils.CRUDConstants;
import de.avci.joride.utils.HTTPRequestUtil;
import de.avci.joride.utils.WebflowPoint;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.postgis.Point;

/**
 * Wrapper to make RideUndertakesRideEntity availlable as a JSFBean
 *
 *
 * @author jochen
 */
@Named
@SessionScoped
public class JRiderUndertakesRideEntity extends RiderUndertakesRideEntity implements Serializable {

    Logger log = Logger.getLogger("" + this.getClass());
    /**
     * A date format for formatting start and end date. Created via lazy
     * instantiation.
     *
     * @deprecated should be done centrally in utils* class
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
    protected DateFormat getDateTimeFormat() {

        if (this.dateFormat == null) {
            dateFormat = (new JoRideConstants()).createDateTimeFormat();
        }

        return dateFormat;
    }

    /**
     * Return a nicely formatted version of the startDate
     *
     * @return
     */
    public String getStartDateFormatted() {
        return getDateTimeFormat().format(this.getStarttimeEarliest());
    }

    /**
     * if comment property is null, replace it with an empty string rather than
     * null to avoid Nullpointertrouble in backend. Also, remove leading and
     * trailing blanks
     */
    protected void cleanseComment() {

        if (this.getComment() == null) {
            this.setComment("");
        }

        this.setComment(this.getComment().trim());
    }

    /**
     * if price property is null, replace it with "Double.NaN" to avoid
     * Nullpointertrouble in backend. Also, remove leading and trailing blanks
     */
    protected void cleansePrice() {

        if (this.getPrice() == null) {
            this.setPrice(Double.NaN);
        }

        this.setComment(this.getComment().trim());
    }

    /**
     * Update from a given RiderUndertakesRideEntity object.
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

    /**
     * Lists *all* rides this customer has ever requested
     *
     * @return
     */
    public List<JRiderUndertakesRideEntity> getRidesForRider() {

        return (new JRiderUndertakesRideEntityService()).getRidesForRider();
    }

    /**
     *
     *
     * @return All rides the rider undertakes in the specified interval
     */
    public List<JRiderUndertakesRideEntity> getRidesForRiderInInterval() {

        return (new JRiderUndertakesRideEntityService()).getRidesForRiderInInterval();
    }

    /**
     * Lists *all* **active** **open** rides for this customer. I.e: Rides which
     * have lastStartTime still in the future, and are not booked.
     *
     * @return list of all Active OpenRides
     */
    public List<JRiderUndertakesRideEntity> getActiveOpenRidesForRider() {

        List<RiderUndertakesRideEntity> activeOpenRides = (new JRiderUndertakesRideEntityService()).getActiveOpenRides();

        List<JRiderUndertakesRideEntity> res = new LinkedList<JRiderUndertakesRideEntity>();

        Iterator<RiderUndertakesRideEntity> it = activeOpenRides.iterator();

        while (it.hasNext()) {
            JRiderUndertakesRideEntity jrue = new JRiderUndertakesRideEntity();
            jrue.updateFromRiderUndertakesRideEntity(it.next());
            res.add(jrue);
        }


        return res;
    }

    /**
     * Update *this* with the Data read in from database for given id, or just
     * do nothing if ID is null.
     *
     * @param id rideId of the DriverUndertakeRide Entity to update from.
     */
    public void updateFromRiderRouteId(Integer myRiderRouteId) {

        JRiderUndertakesRideEntityService service = new JRiderUndertakesRideEntityService();
        service.updateJRiderUndertakesRideEntityByRiderRouteIDSavely(myRiderRouteId, this);

    }

    /**
     * See, if the CRUDConstants().getParamNameCrudId() parameter is present in
     * HTTPRequest. If the ID parameter is != null, then update data from
     * RriverUndertakesRideEntity in database with riderRouteId given by id
     * parameter. If parameter's value is not null, then leave **this**
     * untouched
     *
     */
    public void update() {

        String idStr = (new HTTPRequestUtil()).getParameterSingleValue(new CRUDConstants().getParamNameCrudId());

        int id = 0;

        try {
            id = new Integer(idStr).intValue();
            this.updateFromRiderRouteId(id);
        } catch (java.lang.NumberFormatException exc) {
            log.log(Level.WARNING, "ID Parameter does not contain Numeric Value, value was : " + idStr);
        }





    }

    /**
     * Initialize the RideStarttime property if it is not yet initialized.
     *
     */
    public void initialize() {


        // naturally, we cannot start earlier then now
        if (this.getStarttimeEarliest() == null) {
            this.setStarttimeEarliest(new Date(System.currentTimeMillis()));
        }

        // two hours from now seems to be a good default
        if (this.getStarttimeLatest() == null) {
            this.setStarttimeLatest(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 2)));
        }


        this.setNoPassengers(1);
    }
    /**
     * Value for point.target parameters. If "Startpoint" ist set, then
     * smartUpdate will set the startpoint
     */
    private static final String paramValueTargetStartpoint = "STARTPOINT";

    /**
     * Trivial Accessor making paramValueStartpoint accessible with JSF Methods
     *
     * @return
     */
    public String getParamValueTargetStartpoint() {
        return paramValueTargetStartpoint;
    }
    /**
     * Value for point.target parameters. If "Endpoint" ist set, then
     * smartUpdate will set the startpoint
     */
    private static final String paramValueTargetEndpoint = "ENDPOINT";

    /**
     * Trivial Accessor making paramValueStartpoint accessible with JSF Methods
     *
     * @return
     */
    public String getParamValueTargetEndpoint() {
        return paramValueTargetEndpoint;
    }

    /**
     * Return the Longitude of the rideStartpt , or null if the rideStartpt is
     * null;
     */
    public double getLongitudeStart() {

        if (this.getStartpt() == null) {
            return new Double(0);
        }
        return new Double(getStartpt().getX());
    }

    /**
     * Return the Latitude of the rideStartpt , or null if the rideStartpt is
     * null;
     */
    public double getLatitudeStart() {

        if (this.getStartpt() == null) {
            return new Double(0);
        }
        return new Double(getStartpt().getY());
    }

    /**
     * Return the Longitude of the rideEndpt , or null if the rideEndpt is null;
     */
    public double getLongitudeEnd() {

        if (this.getEndpt() == null) {
            return new Double(0);
        }
        return new Double(getEndpt().getX());
    }

    /**
     * Return the Latitude of the rideStartpt , or null if the rideStartpt is
     * null;
     */
    public double getLatitudeEnd() {

        if (this.getEndpt() == null) {
            return new Double(0);
        }
        return new Double(getEndpt().getY());
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLongitudeStart(double arg) {

        if (this.getStartpt() == null) {
            this.setStartpt(new Point(arg, 0));
        }

        this.getStartpt().setX(arg);
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLatitudeStart(double arg) {

        if (this.getStartpt() == null) {
            this.setStartpt(new Point(0, arg));
        }

        this.getStartpt().setY(arg);
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLongitudeEnd(double arg) {

        if (this.getEndpt() == null) {
            this.setEndpt(new Point(arg, 0));
        }

        this.getEndpt().setX(arg);
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLatitudeEnd(double arg) {

        if (this.getEndpt() == null) {
            this.setEndpt(new Point(0, arg));
        }

        this.getEndpt().setY(arg);
    }

    /**
     * Update bean, thereby evaluating the HTTPRequest and update startpoint or
     * endpoint data depending on params present in HTTPRequest
     */
    public void smartUpdate() {

        WebflowPoint webflowPoint = new WebflowPoint();
        webflowPoint.smartUpdate();


        //   
        // see, if we should update the startpoints
        // 

        if (paramValueTargetStartpoint.equals(webflowPoint.getTarget())) {

            if (webflowPoint.getParamAddress() != null) {
                this.setStartptAddress(webflowPoint.getAddress());
            }

            // Set Start/End Latitude depending on target param
            if (webflowPoint.getLat() != null) {
                this.setLatitudeStart(webflowPoint.getLat());
            }

            // Set Start/End Longitude depending on target param
            if (webflowPoint.getLon() != null) {
                this.setLongitudeStart(webflowPoint.getLon());
            }

        } //   if(paramValueTargetStartpoint.equals(webflowPoint.getTarget()))



        //   
        // see, if we should update the endpoints
        // 

        if (paramValueTargetEndpoint.equals(webflowPoint.getTarget())) {

            if (webflowPoint.getParamAddress() != null) {
                this.setEndptAddress(webflowPoint.getAddress());
            }

            // Set Start/End Latitude depending on target param
            if (webflowPoint.getLat() != null) {
                this.setLatitudeEnd(webflowPoint.getLat());
            }

            // Set Start/End Longitude depending on target param
            if (webflowPoint.getLon() != null) {
                this.setLongitudeEnd(webflowPoint.getLon());
            }

        } //   if(paramValueTargetEndpoint.equals(webflowPoint.getTarget()))

    } // smartUpdate

    /**
     * Create a new RiderUndertakesRideEntity and save it to the Database.
     * Return the Id of the newly created database.
     *
     * @return id of the newly create DriverUndertakesRideEntity
     */
    public int addToDB() {


        if (this.getRiderrouteId() != null) {
            throw new Error("Cannot add Ride to Database, riderrouteid already exists");
        }

        JRiderUndertakesRideEntityService jrures = new JRiderUndertakesRideEntityService();

        int my_id = jrures.addRideRequest(this);

        this.setRiderrouteId(new Integer(my_id));

        return this.getRiderrouteId();

    } // addToDB

    public void doCrudAction(ActionEvent evt) {

        HTTPRequestUtil hru = new HTTPRequestUtil();

        System.out.println("doCrudAction Event : " + evt.toString());

        String action = hru.getParameterSingleValue((new CRUDConstants()).getParamNameCrudAction());
        System.out.println("Param Action : " + action);

        String id = hru.getParameterSingleValue((new CRUDConstants()).getParamNameCrudId());
        System.out.println("Param ID     : " + id);


        // Deleting is not yet implemented,  
        //
        // if (CRUDConstants.PARAM_VALUE_CRUD_DELETE.equals(action)) {
        //    this.delete(new Integer(id).intValue());
        // }


        if (CRUDConstants.PARAM_VALUE_CRUD_CREATE.equals(action)) {
            this.addToDB();
        }

    } // doCrudAction()

    /**
     * Returns a list of Matching Drive Offers for this ride
     *
     * @return Returns a list of Matching Drive Offers for this ride
     */
    public List<JMatchingEntity> getMatches() {

        if (this.getRiderrouteId() == null) {
            System.err.println("riderRouteId is null, returning empty list");
            return new LinkedList<JMatchingEntity>();
        }

        return (new JMatchingEntityService()).getMatchesForRide(this.getRiderrouteId());
    }

    /**
     * Returns true, if this ride has been updated
     *
     * @return Returns the Number of OpenMatches for this RideRequest
     */
    public boolean getRideUpdated() {
        return (new JRiderUndertakesRideEntityService()).isRideUpdated(this.getRiderrouteId());
    }

    /**
     * Returns the Number of OpenMatches for this RideRequest
     *
     * @return Returns the Number of OpenMatches for this RideRequest
     */
    public int getNoMatches() {
        return this.getMatches().size();
    }

    /**
     * @return Returns true, if the number of Matches is > 0, else false
     *
     */
    public boolean getHasMatches() {
        return this.getMatches().size() > 0;
    }

    /**
     * Invalidate/countermand rideRequest with given riderroute id.
     *
     *
     * Returns "rider" to move back to "rider" page if removal was successful,
     * else returns null. May frequently fail, if there are open Matches for
     * this ride.
     *
     */
    public String invalidate() {

        boolean result = false;

        try {
            result = new JRiderUndertakesRideEntityService().removeRideSafely(this);
        } catch (Exception exc) {
            // TODO: add a message why this failed
            log.log(Level.SEVERE, "removing user " + this.getCustId() + " failed with unknown exception", exc);
            return null;
        }


        if (result) {
            return "rider";
        } else {
            // TODO: add a JSF message why this failed
            log.log(Level.SEVERE, "removing user " + this.getCustId() + " failed");
            return null;
        }

    } // remove ride

    /**
     * Determines the caller from http-request, and if caller is identical to
     * rider, then allow for rider rating
     *
     *
     * @return true, if caller is identical to rider, else false
     */
    public boolean isRiderRateable() {

        CustomerEntity caller = (new JCustomerEntityService()).getCustomerEntitySafely();


        if (this.getCustId() == null) {
            return false;
        }
        if (caller == null) {
            return false;
        }

        if (caller.getCustId() == this.getCustId().getCustId()) {
            return true;
        }

        return false;
    }

    /**
     * Determines the caller from http-request, and if caller is identical to
     * rider, then allow for rider cancel
     *
     * @return true, if caller is identical to rider, else false
     */
    public boolean isRiderCancellable() {

        CustomerEntity caller = (new JCustomerEntityService()).getCustomerEntitySafely();

        if (this.getCustId() == null) {
            return false;
        }
        if (caller == null) {
            return false;
        }



        if (caller.getCustId() == this.getCustId().getCustId()) {
            return true;
        }

        return false;
    }

    /**
     * Determines if this ride has already been rated by the rider
     * (receivedrating != null)
     *
     * @return true, if receivedrating is >= 0 , else false
     */
    public boolean isRiderRated() {

        if (this.getGivenrating() == null) {return false;}
        if (this.getGivenrating() >= 0) {return true;}
        return false;
    }

    /**
     * Determines if this ride has already been rated by the driver
     * (receivedrating != null)
     *
     * @return true, if receivedrating is >=0 , else false
     */
    public boolean isDriverRated() {

        if (this.getReceivedrating() == null) {return false;}
        if (this.getReceivedrating() >= 0) { return true;}
        return false;
    }

    /**
     * Update Rider's rating and comment for this ride
     *
     * @param rating
     * @param comment
     */
    public void doSetGivenRating(ActionEvent evt) {
        new JRiderUndertakesRideEntityService().setGivenRatingSavely(this);
    }

    /**
     * Overwrite RiderUndertakesRideEntity() to start out with negative ratings,
     * signifying that this ride has not been rated yet
     *
     */
    public JRiderUndertakesRideEntity() {

        super();

        this.setGivenrating(-1);
        this.setReceivedrating(-1);

    }
} // class
