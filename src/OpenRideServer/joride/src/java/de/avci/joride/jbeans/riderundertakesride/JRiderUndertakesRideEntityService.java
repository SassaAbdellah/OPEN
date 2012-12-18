/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.riderundertakesride;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import java.text.DateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/** Wrapper for RiderUndertakesRideEntityService
 *  in OpenRideServer-ejb.
 *
 * @author jochen
 */
public class JRiderUndertakesRideEntityService {
    
  
    
    
 
       /** Get a customerEntity from the current request
       * 
       * @return 
       */
      public CustomerEntity getCustomerEntity(){
          return (new JCustomerEntityService()).getCustomerEntitySavely();
      }
    
    
     /** Lookup RiderUndertakesRideControllerLocal Bean that
     *  controls my requests.
     * 
     * @return 
     */
     protected RiderUndertakesRideControllerLocal lookupRiderUndertakesRideControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RiderUndertakesRideControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RiderUndertakesRideControllerBean!de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
          
    }

  
         
      /** Get a list all Drives of this driver.
       *  Current user/customer is determined from HTTPRequest's AuthPrincipal.
       * 
       * @return 
       */
      public List <RiderUndertakesRideEntity> getRidesForRider(){
      
          
         CustomerEntity ce=this.getCustomerEntity();
         RiderUndertakesRideControllerLocal rurcl=this.lookupRiderUndertakesRideControllerBeanLocal();
         
     
         if(ce==null){ 
             throw new Error ("Cannot determine Rides, customerEntity is null");
         }
         
         if(ce.getCustNickname()==null){ 
             throw new Error ("Cannot determine Rides, customerNickname is null");
         } 
         
    
         // get all rides related to this customer
         return rurcl.getRidesForCustomer(ce);
                 
                 
                
      }
      
      
        /** Savely get the Ride with given riderRouteId.
       *  
       *  Current user/customer is determined from HTTPRequest's AuthPrincipal.
       * 
       * @return 
       */
      public RiderUndertakesRideEntity getRideByRiderRouteIdSavely(int myRiderRouteId){
      
          
         CustomerEntity ce=this.getCustomerEntity();
         RiderUndertakesRideControllerLocal rurcl=this.lookupRiderUndertakesRideControllerBeanLocal();
         
     
         if(ce==null){ 
             throw new Error ("Cannot determine Ride, customerEntity is null");
         }
         
         if(ce.getCustNickname()==null){ 
             throw new Error ("Cannot determine Ride, customerNickname is null");
         } 
         
         
        RiderUndertakesRideEntity rure=rurcl.getRideByRiderRouteId(myRiderRouteId);
     
         
         
         if(rure.getCustId().getCustId()  != ce.getCustId()){
             throw new Error("Cannot retrieve Drive with given ID, object does not belong to user");
         }
         
         return rure;
         
      } //  getDriveByIdSavely(int id)
    
      
      
       
     
      /** Savely update JRiderUndertakesRideEntity from database
       * 
       *
       * 
       * @param riderRouteId  ride Id from the DriverUndertakesRideEntity providing the data.
       *                      If this is null, simply no update will be done. 
       * 
       * @param jdure  JDriverUndertakesRideEntity to be updated with data from database 
       */
      public void updateJRiderUndertakesRideEntityByRiderRouteIDSavely(Integer riderRouteId, JRiderUndertakesRideEntity jrure){
      
      
          if(riderRouteId==null){
              // nothing to do in that case!
              return;
          }
          
          
           RiderUndertakesRideEntity rure=this.getRideByRiderRouteIdSavely(riderRouteId);
          // update
          jrure.updateFromRiderUndertakesRideEntity(rure);
      }
    
 
      
        
    
}
