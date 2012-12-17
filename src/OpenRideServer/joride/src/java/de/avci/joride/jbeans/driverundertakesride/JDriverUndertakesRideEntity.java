/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import javax.inject.Named;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.utils.CRUDConstants;
import de.avci.joride.utils.HTTPRequestUtil;
import de.avci.joride.utils.WebflowPoint;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import java.text.DateFormat;
import java.util.Date;

import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import org.postgis.Point;

/**
 * Small Wrapper class making Entity Bean CustomerEntity availlable as a CDI
 * Bean for Use in JSF Frontend.
 *
 * @author jochen
 *
 */
@Named
@SessionScoped

public class JDriverUndertakesRideEntity extends de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity {
    
    
    
    /** Default Value for Acceptable Detour in Km.
     *  May be changed by the user in Frontends.
     */
    private Integer ACCEPTABLE_DETOUR_KM_DEFAULT=10;
    
     /** Default Value for Acceptable Detour in Min.
     *  May be changed by the user in Frontends.
     */
    private Integer ACCEPTABLE_DETOUR_MIN_DEFAULT=15;
    
    
     /** Default Value for Acceptable Detour in Percent.
     *  May be changed by the user in Frontends.
     */
    private Integer ACCEPTABLE_DETOUR_PERCENT_DEFAULT=20;
    

     /** Default Value for Number of offered seats.
     *  May be changed by the user in Frontends.
     */
    private Integer NUMBER_SEATS_OFFERED_DEFAULT=1;
    

    
    
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
            jdure.updateFromDriverUndertakesRideEntity(it.next());

            res.add(jdure);
        }

        return res;
    }
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
        return getDateFormat().format(this.getRideStarttime());
    }

 
    /** See, if the  CRUDConstants().getParamNameCrudId() parameter is  present in HTTPRequest.
     * If the ID parameter is != null, then update data from DriverUndertakesRideEntity
     * in database with rideId given by id parameter.
     * If parameter's value is not null, then leave **this** untouched
     * 
     */
    public void update() {

        String idStr = (new HTTPRequestUtil()).getParameterSingleValue(new CRUDConstants().getParamNameCrudId());

        int id = 0;

        try {
            id = new Integer(idStr).intValue();
        } catch (java.lang.NumberFormatException exc) {
            throw new Error("ID Parameter does not contain Numeric Value " + idStr);
        }


       
        this.updateFromId(id);

    }

    /** Update from a given DriverUndertakesRideEntity object.
     *
     *  @param dure DriverUndertakesRideEntityObject to update *this* object.
     */
    public void updateFromDriverUndertakesRideEntity(DriverUndertakesRideEntity dure) {

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
    
    
   /** Update *this* with the Data read in from database for given id,
    *  or just do nothing if ID is null.
    * 
    * @param id rideId of the DriverUndertakeRide Entity to update from.
    */
   public void updateFromId(Integer id){
       
       JDriverUndertakesRideEntityService service=new JDriverUndertakesRideEntityService();   
       service.updateJDriverUndertakesRideEntityByIDSavely(id, this);
  
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
     * Get the Route Points for this Drive wrapped in a JRoutPointsEntity Object
     *
     * @return
     */
    public JRoutePointsEntity findRoutePoints() {
 
        return new JDriverUndertakesRideEntityService().findRoute(this);

    }
    
    
    

    /** Get the RoutePoints for this Drive encoded in a JSONString
     *
     * 
     * @return
     * 
     */
    public String getRoutePointsAsJSON() {
        return this.getRoutePoints().getRoutePointsAsJSON();
    }
    
    
      /**
     * Get the RoutePoints for this Drive encoded in a JSONString
     *
     * @return
     */
    public String findRoutePointsAsJSON() {
        return this.findRoutePoints().getRoutePointsAsJSON();
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

        if (this.getRideStartpt() == null) {
            return new Double(0);
        }
        return new Double(getRideStartpt().getX());
    }

    /**
     * Return the Latitude of the rideStartpt , or null if the rideStartpt is
     * null;
     */
    public double getLatitudeStart() {

        if (this.getRideStartpt() == null) {
            return new Double(0);
        }
        return new Double(getRideStartpt().getY());
    }

    /**
     * Return the Longitude of the rideEndpt , or null if the rideEndpt is null;
     */
    public double getLongitudeEnd() {

        if (this.getRideEndpt() == null) {
            return new Double(0);
        }
        return new Double(getRideEndpt().getX());
    }

    /**
     * Return the Latitude of the rideStartpt , or null if the rideStartpt is
     * null;
     */
    public double getLatitudeEnd() {

        if (this.getRideEndpt() == null) {
            return new Double(0);
        }
        return new Double(getRideEndpt().getY());
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLongitudeStart(double arg) {

        if (this.getRideStartpt() == null) {
            this.setRideStartpt(new Point(arg, 0));
        }

        this.getRideStartpt().setX(arg);
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLatitudeStart(double arg) {

        if (this.getRideStartpt() == null) {
            this.setRideStartpt(new Point(0, arg));
        }

        this.getRideStartpt().setY(arg);
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLongitudeEnd(double arg) {

        if (this.getRideEndpt() == null) {
            this.setRideEndpt(new Point(arg, 0));
        }

        this.getRideEndpt().setX(arg);
    }

    /**
     * set the latitude of the rideStartpt
     */
    public void setLatitudeEnd(double arg) {

        if (this.getRideEndpt() == null) {
            this.setRideEndpt(new Point(0, arg));
        }

        this.getRideEndpt().setY(arg);
    }


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

        } //   if(paramValueTargetStartpoint.equals(webflowPoint.getTarget()))
    }

    
        /** Initialize the RideStarttime property if it is not yet initialized.
         * 
         */
      public  void initialize(){
        
            if(this.getRideStarttime()==null){
                this.setRideStarttime(new Date(System.currentTimeMillis()));
            }
              
            this.setRideAcceptableDetourInKm(ACCEPTABLE_DETOUR_KM_DEFAULT);
            this.setRideAcceptableDetourInMin(ACCEPTABLE_DETOUR_MIN_DEFAULT);
            this.setRideAcceptableDetourInPercent(ACCEPTABLE_DETOUR_PERCENT_DEFAULT);
            
            this.setRideOfferedseatsNo(NUMBER_SEATS_OFFERED_DEFAULT);
        }
    
      
      /** DriverUnderTakesRideController already provides support 
       *  for setting intermediate points on a route.
       * 
       *  This is currently not supported by the frontend and routing mechs,
       *  but to be added soon.
       * 
       *  Until then, this methods returns an empty Array, to satisfy 
       *  the DriverUnderTakesRideController interface.
       * 
       * @return 
       */
      public Point[] getIntermediatePoints(){
                   
          return new Point[0];
      }
      
      
    
      /** Create a new DriverUndertakesRideEntity and Save it to the Database.
       *  Note that the set of Routepoints will always be 
       *  created for this entity. Routepoints are not created
       *  programmatically by the user.
       * 
       * 
       * 
       * 
       *   @return id of the newly create DriverUndertakesRideEntity
       */
      public int addToDB(){
          
          
          if(this.getRideId()!=null){
              throw new Error("Cannot add Ride to Database, Id already exists");
          }
          
          JDriverUndertakesRideEntityService jdures=new JDriverUndertakesRideEntityService();
          
          int my_id=jdures.addDriveSavely(this);
          
          this.setRideId(new Integer(my_id));
          
          return this.getRideId();
          
      }
      
      
      
      
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

    }

      
      
      
      
  
} // class 
