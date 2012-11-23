/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.driverundertakesride;

import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.utils.HTTPRequestUtil;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    
    
 
     
 
    
    
} // class
