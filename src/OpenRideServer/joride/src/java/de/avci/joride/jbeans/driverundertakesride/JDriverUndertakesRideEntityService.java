/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.utils.HTTPRequestUtil;
import de.avci.joride.jbeans.driverundertakesride.JRoutePointsEntity;

import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.driver.RoutePointEntity;
import de.fhg.fokus.openride.routing.RouterBeanLocal;
import de.fhg.fokus.openride.routing.Coordinate;
import de.fhg.fokus.openride.routing.Route;
import de.fhg.fokus.openride.routing.RoutePoint;



import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.LinkedList;
import org.postgis.Point;



/** Service 
 *
 * @author jochen
 */
public class JDriverUndertakesRideEntityService {
    
    
    
    /** Lookup DriverUndertakesRideControllerLocal Bean that
     *  controls my offers.
     * 
     * @return 
     */
     protected DriverUndertakesRideControllerLocal lookupDriverUndertakesRideControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DriverUndertakesRideControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/DriverUndertakesRideControllerBean!de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
          
    }

     
      /** Lookup RouterBean to find Route for new offer
     * 
     * @return 
     */
     protected RouterBeanLocal lookupRouterBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RouterBeanLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RouterBean!de.fhg.fokus.openride.routing.RouterBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
          
    }

    
    
    
    
    /** Get a customerEntity from the current request
       * 
       * @return 
       */
      public CustomerEntity getCustomerEntity(){
          return (new JCustomerEntityService()).getCustomerEntitySavely();
      }
      
      
      /** Get a list of Rides the current User has offered.
       *  Current user/customer is determined from HTTPRequest's AuthPrincipal.
       * 
       * @return 
       */
      public List <DriverUndertakesRideEntity> getActiveDrivesForDriver(){
      
         CustomerEntity ce=this.getCustomerEntity();
         DriverUndertakesRideControllerLocal durcl=this.lookupDriverUndertakesRideControllerBeanLocal();
         
         return durcl.getActiveDrives(ce.getCustNickname());
          
      }
      
            
      /** Get a list of open active Rides the current user has offered.
       *  Current user/customer is determined from HTTPRequest's AuthPrincipal.
       * 
       * @return 
       */
      public List <DriverUndertakesRideEntity> getOpenDrivesForDriver(){
      
         CustomerEntity ce=this.getCustomerEntity();
         DriverUndertakesRideControllerLocal durcl=this.lookupDriverUndertakesRideControllerBeanLocal();
         
         return durcl.getActiveOpenDrives(ce.getCustNickname());
      }
      
      
          
      /** Get a list all Drives of this driver.
       *  Current user/customer is determined from HTTPRequest's AuthPrincipal.
       * 
       * @return 
       */
      public List <DriverUndertakesRideEntity> getDrivesForDriver(){
      
          
         CustomerEntity ce=this.getCustomerEntity();
         DriverUndertakesRideControllerLocal durcl=this.lookupDriverUndertakesRideControllerBeanLocal();
         
     
         if(ce==null){ 
             throw new Error ("Cannot determine Drives, customerEntity is null");
         }
         
         if(ce.getCustNickname()==null){ 
             throw new Error ("Cannot determine Drives, customerNickname is null");
         } 
         
         
         return durcl.getDrivesForDriver(ce.getCustNickname());
          
      }
      
      
      
                
      /** Savely get the Drive with given ID.
       *  
       *  Current user/customer is determined from HTTPRequest's AuthPrincipal.
       * 
       * @return 
       */
      public DriverUndertakesRideEntity getDriveByIdSavely(int id){
      
          
         CustomerEntity ce=this.getCustomerEntity();
         DriverUndertakesRideControllerLocal durcl=this.lookupDriverUndertakesRideControllerBeanLocal();
         
     
         if(ce==null){ 
             throw new Error ("Cannot determine Drives, customerEntity is null");
         }
         
         if(ce.getCustNickname()==null){ 
             throw new Error ("Cannot determine Drives, customerNickname is null");
         } 
         
         
        DriverUndertakesRideEntity dure=durcl.getDriveByDriveId(id);
     
         
         
         if(dure.getCustId().getCustId()  != ce.getCustId()){
             throw new Error("Cannot retrieve Drive with given ID, object does not belong to user");
         }
         
         return dure;
         
      } //  getDriveByIdSavely(int id)
    
      
      
      /** Savely update JDriverUndertakesRideEntity from database
       * 
       *
       * 
       * @param rideId ride Id from the DriverUndertakesRideEntity providing the data.
       *               If this is null, simply no update will be done. 
       * 
       * @param jdure  JDriverUndertakesRideEntity to be updated with data from database 
       */
      public void updateJDriverUndertakesRideEntityByIDSavely(Integer rideId, JDriverUndertakesRideEntity jdure){
      
      
          if(rideId==null){
              // nothing to do in that case!
              return;
          }
          
          
          DriverUndertakesRideEntity dure=this.getDriveByIdSavely(rideId);
          // update
          jdure.updateFromDriverUndertakesRideEntity(dure);
      }
    
 
      
      /** Get RoutePoints for Drive with DriveId
       * 
       * @param driveId
       * @return 
       */
      public JRoutePointsEntity getRoutePointsForDrive(int driveId){
      
          //
          // Check, if drive does really belong to the calling user
          //
               
         CustomerEntity ce=this.getCustomerEntity();
         DriverUndertakesRideControllerLocal durcl=this.lookupDriverUndertakesRideControllerBeanLocal();
         
     
         if(ce==null){ 
             throw new Error ("Cannot determine Drives, customerEntity is null");
         }
         
         if(ce.getCustNickname()==null){ 
             throw new Error ("Cannot determine Drives, customerNickname is null");
         } 
         
         
        DriverUndertakesRideEntity dure=durcl.getDriveByDriveId(driveId);
     
         
         
         if(dure.getCustId().getCustId()  != ce.getCustId()){
             throw new Error("Cannot retrieve Drive with given ID, object does not belong to user");
         }
         
          
         // 
         // done with checking for user
         //
          
         List<RoutePointEntity> routePoints=durcl.getRoutePoints(driveId);
         
         JRoutePointsEntity res=new JRoutePointsEntity();
         res.setRoutePoints(routePoints);
         
         return res;
         
      }
     
 
      
      public JRoutePointsEntity findRoute(DriverUndertakesRideEntity dure){
      
               //
          // Check, if drive does really belong to the calling user
          //
               
         CustomerEntity ce=this.getCustomerEntity();
         RouterBeanLocal rbl=this.lookupRouterBeanLocal();
         
     
         if(ce==null){ 
             throw new Error ("Cannot find route, customerEntity is null");
         }
         
         
         Coordinate startC=new Coordinate(
                                            dure.getRideStartpt().getY(),
                                            dure.getRideStartpt().getX()    
         );
         
         Coordinate endC=new Coordinate(
                                            dure.getRideEndpt().getY(),
                                            dure.getRideEndpt().getX()    
         );
         
         
         Double threshold=1d;
         
         Route route=rbl.findRoute(
                                    startC, 
                                    endC, 
                                    new java.sql.Timestamp(dure.getRideStarttime().getTime()), 
                                    true, 
                                    threshold, 
                                    true);     
         
         
        
         
         RoutePoint[] routePoints=route.getRoutePoints();
         
         List<RoutePointEntity> routePointsEntities=new LinkedList <RoutePointEntity> ();
         
         for(int i=0; i<routePoints.length; i++){
        
             RoutePointEntity rpe=new RoutePointEntity();
             rpe.setLatitude(routePoints[i].getCoordinate().getLatititude());
             rpe.setLongitude(routePoints[i].getCoordinate().getLongitude());
             routePointsEntities.add(rpe);
         }
         
         
         JRoutePointsEntity res=new JRoutePointsEntity();       
         res.setRoutePoints(routePointsEntities);
         return res;
      }
      
    
      
      /** Check if JDriverUndertakesRideEntity is ready to be saved to DB.
       *  Brutally throws an error if not.
       * 
       * 
       * @param jdure 
       */
      protected void checkJDriverUndertakesRideEntity(JDriverUndertakesRideEntity jdure){
          
          
           String errPrefix="Checking JDriverUndertakeRideEntity failed, reason :\n";
           
            //Point ridestartPt
           if(jdure.getRideStartpt()==null){ 
               throw new Error(errPrefix+"rideStartpt is null");
           }
           
            // Point rideendPt
            if(jdure.getRideEndpt()==null){
                  throw new Error(errPrefix+"rideEndpt is null");
            }
            
            //  Point[] intermediatePoints
              if(jdure.getIntermediatePoints()==null){
                  throw new Error(errPrefix+"intermediate points are null");
            }
            
            
            //java.sql.Date ridestartTime
            if(jdure.getRideStarttime()==null){
                  throw new Error(errPrefix+"ride starttime is null");
            }
            //String rideComment
            // jdure.getRideComment(),No checks, rideComment may possibly be null
            
            //Integer acceptableDetourInMin
            if(jdure.getRideAcceptableDetourInMin()==null){
                 throw new Error(errPrefix+"ride acceptable Detour in Min is null");
            }
            // Integer acceptableDetourKm
            if(jdure.getRideAcceptableDetourInKm()==null){
                 throw new Error(errPrefix+"ride acceptable Detour in KM is null");
            }
                
                
            // Integer acceptableDetourPercent,
            if (jdure.getRideAcceptableDetourInPercent()==null){
                 throw new Error(errPrefix+"ride acceptable Detour in Percent is null");
            }
     
            //int offeredSeatsNo,
           
            
            
            //String startptAddress,
            if(jdure.getStartptAddress()==null){
                throw new Error(errPrefix+"startpoint Address is null");
            }
                
            //String endptAddress
            if(jdure.getEndptAddress()==null){
             throw new Error(errPrefix+"endpoint Address is null");
            }
      }
     
    
      /** Add a new Drive (rsp: driverUndertakesRideEntity) savely to the persistence layer.
       *  As usually, savely here means that the calling user is determined
       *  from http request and will get set as driver from serverside
       *    
       *  Note that the Route for this ride will not be 
       *  transferred as a parameter, but will be autocreated.
       *  TODO: dealing with the route as depicted above might    
       *  not be what we want in the long run, so this 
       *  should be revisited.
       * 
       * 
       * 
       * 
       * 
       * 
       * @param jdure
       * @return 
       */
      
      public int addDriveSavely(JDriverUndertakesRideEntity jdure){
   
          // check integrity of Offer
          this.checkJDriverUndertakesRideEntity(jdure);
    
           //
          // Check, if drive does really belong to the calling user
          //
               
         CustomerEntity ce=this.getCustomerEntity();
         DriverUndertakesRideControllerLocal durcl=this.lookupDriverUndertakesRideControllerBeanLocal();
         
          
         return durcl.addRide(
             // Customer ID
            ce.getCustId(),
            //Point ridestartPt
            jdure.getRideStartpt(),
            // Point rideendPt
            jdure.getRideEndpt(),
            //  Point[] intermediatePoints
            jdure.getIntermediatePoints(),
            //java.sql.Date ridestartTime
            new java.sql.Date(jdure.getRideStarttime().getTime()),
            //String rideComment
            jdure.getRideComment(),
            //Integer acceptableDetourInMin
            jdure.getRideAcceptableDetourInMin(),
            // Integer acceptableDetourKm
            jdure.getRideAcceptableDetourInKm(),
            // Integer acceptableDetourPercent,
            jdure.getRideAcceptableDetourInPercent(),
            //int offeredSeatsNo,
            jdure.getRideOfferedseatsNo(),
            //String startptAddress,
            jdure.getStartptAddress(),
            //String endptAddress
            jdure.getEndptAddress()
            );

      }
      
      
    
} // class
