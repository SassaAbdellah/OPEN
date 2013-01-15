/** Service to get and Put CustomerEntityBeans the EJB Way.
 *
 */
package de.avci.joride.jbeans.customerprofile;

import de.avci.joride.utils.HTTPRequestUtil;

import javax.servlet.http.HttpServletRequest;

import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/** Service to get and put JCustomerEntityBeans to the System.
 * 
 *
 * 
 * @author jochen
 * 
 */
public class JCustomerEntityService {
    
    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();
    
  
      private CustomerControllerLocal lookupCustomerControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CustomerControllerBean!de.fhg.fokus.openride.customerprofile.CustomerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    

    /** Initialize the data of an (possibly empty) JCustomerProfile.
     * 
     *  For safety, it retreives the "Customer.nickname" for updateing the
     *  data from the HTTPRequest.userPrincipal property.
     *  Thus, no dangerous passing of nicknames in parameters is necessary.
     * 
     */
    public CustomerEntity getCustomerEntitySafely() {
  
        // SecurityMeasure: ensure that the userName is equal to
        // the AuthPrincipal of the Request
      
       String userName=(new HTTPRequestUtil()).getUserPrincipalName();
        
        
        CustomerEntity customerEntity = customerControllerBean.getCustomerByNickname(userName);

        // TODO: do something more sane than just cast
        return  customerEntity;
      
    } // getCustomerEntity
    
    
    
    /** Determine a CustomerEntity from a HTTPServletRequest.
     *  The user is determined from the request's remoteUser property,
     *  and can thus be considered to be safe.
     * 
     * @param request request from which the remote user should be read
     * @return 
     */

    public CustomerEntity getCustomerEntityFromRequest(HttpServletRequest request) {
  
        // SecurityMeasure: ensure that the userName is equal to
        // the AuthPrincipal of the Request
      
       String userName=request.getRemoteUser();
        
        
        CustomerEntity customerEntity = customerControllerBean.getCustomerByNickname(userName);

        // TODO: do something more sane than just cast
        return  customerEntity;
      
    } // getCustomerEntity
    
    
    
        
    
    
    /** Read the customer's nickname from current HTTPRequest's Authentication Data,
     *  then determine and return the userID.
     *  Since it relies on HTTPAuth Information we call it "safe".
     * 
     * @return the customer id associated to the current HTTPRequest's Principal 
     * 
     */
    Integer getCustIDSafely(){
    
        CustomerEntity ce=getCustomerEntitySafely();
        
        if(ce==null){
            System.err.println("CustomerEntity was null, cannot return custID");
            return null;
        }
        
        return ce.getCustId();
    } // getCustIdSafely()
    
     
   
    

    
    /** Update the personal data persistent data for the given user.
     *  As a safety measure, the customer entity's user name is checked
     *  against the http request's user name
     * 
     */
    public void setCustomerPersonalData(JCustomerEntity jCustomerEntity){
        
           // check that nickname of argument bean is equal 
           // to http-request's user name
           String userName=(new HTTPRequestUtil()).getUserPrincipalName();
           
           if(userName==null){
              String errmsg="Refusing to update userdata, httpRequest is not authenticated";
              System.err.println(errmsg);
              throw new Error(errmsg);
           }
        
           if(jCustomerEntity==null){
               String errmsg="Refusing to update userdata, customerEntity is null";
               System.err.println(errmsg);
               throw new Error(errmsg);
           }
           
           
           String nickname=jCustomerEntity.getCustNickname();
           
           if(!(userName.equals(nickname))){
               String errmsg="Refusing to update userdata, nickname "+nickname+" is not able to http username "+userName;
               System.err.println(errmsg);
               throw new Error(errmsg);
           }
           
           // with security checks beeing done,
           // we can proceed to adding data to db        
           CustomerControllerLocal cc=lookupCustomerControllerBeanLocal();
           
           Integer customerID=this.getCustIDSafely();
               System.out.println("customerID is : "+customerID);
           
           if(customerID==null){
               throw new Error("CustomerID is null, cannot save");
           }
           
         
           cc.setPersonalData(
          //  int custId
           customerID,
           //Date custDateofbirth 
           jCustomerEntity.getCustDateofbirth(),
           //String custEmail 
           jCustomerEntity.getCustEmail(),
           //String custMobilePhoneNo 
           jCustomerEntity.getCustMobilephoneno(),
           //String custFixedPhoneNo 
           jCustomerEntity.getCustFixedphoneno(),
           //String custAddrStreet
           jCustomerEntity.getCustAddrStreet(),
           //int custAddrZipcode 
           jCustomerEntity.getCustAddrZipcode(),
           //String custAddrCity 
           jCustomerEntity.getCustAddrCity(),
           //char custIssmoker
           jCustomerEntity.getCustSmoker().charAt(0),  
           //Date custLicenseDate);
           jCustomerEntity.getCustLicensedate()
         ); // end of method call to setCustomerPersonalData
                                  
    }
    
    
    
 
    
    
        
    /** Update the driver preferences for the given user.
     *  As a safety measure, the customer entity's user name is checked
     *  against the http request's user name
     * 
     */
    public void setCustDriverPrefs(JCustomerEntity jCustomerEntity){
        
           // check that nickname of argument bean is equal 
           // to http-request's user name
           String userName=(new HTTPRequestUtil()).getUserPrincipalName();
           
           if(userName==null){
              String errmsg="Refusing to update userdata, httpRequest is not authenticated";
              System.err.println(errmsg);
              throw new Error(errmsg);
           }
        
           if(jCustomerEntity==null){
               String errmsg="Refusing to update userdata, customerEntity is null";
               System.err.println(errmsg);
               throw new Error(errmsg);
           }
           
           
         
           
           // with security checks beeing done,
           // we can proceed to adding data to db        
           CustomerControllerLocal cc=lookupCustomerControllerBeanLocal();
           
           Integer customerID=this.getCustIDSafely();
               System.out.println("customerID is : "+customerID);
           
           if(customerID==null){
               throw new Error("CustomerID is null, cannot save");
           }
           
         
           cc.setDriverPrefs(
                   
          //  int custId
           customerID,         
           // int custDriverprefAge is set to 0
           0,
       
           // char custDriverprefGender is set to '-'
           '-',
           
           // char custDriverprefSmoker) 
           jCustomerEntity.getCustDriverprefSmoker()
           
          
         ); // end of method call to setCustomerDriverPrefs
                                  
    }
    
    
    
           
    /** Update the rider preferences for the given user.
     *  As a safety measure, the customer entity's user name is checked
     *  against the http request's user name
     * 
     */
    public void setCustRiderPrefs(JCustomerEntity jCustomerEntity){
        
           // check that nickname of argument bean is equal 
           // to http-request's user name
           String userName=(new HTTPRequestUtil()).getUserPrincipalName();
           
           if(userName==null){
              String errmsg="Refusing to update userdata, httpRequest is not authenticated";
              System.err.println(errmsg);
              throw new Error(errmsg);
           }
        
           if(jCustomerEntity==null){
               String errmsg="Refusing to update userdata, customerEntity is null";
               System.err.println(errmsg);
               throw new Error(errmsg);
           }
           
           
         
           
           // with security checks beeing done,
           // we can proceed to adding data to db        
           CustomerControllerLocal cc=lookupCustomerControllerBeanLocal();
           
           Integer customerID=this.getCustIDSafely();
               System.out.println("customerID is : "+customerID);
           
           if(customerID==null){
               throw new Error("CustomerID is null, cannot save");
           }
           
         
           cc.setRiderPrefs(
                   
          //  int custId
           customerID,         
           // int custRiderprefAge is fixed to '0'
           0,
       
           // char custRiderprefGender is fixed to '-'
           '-',
           
           // char custRiderprefSmoker) 
           jCustomerEntity.getCustRiderprefIssmoker()
           
          
         ); // end of method call to setCustomerDriverPrefs
                                  
    }
    
    
    
    
    /** Returns true, if an account with that email address
     *  already exists in the db, else false.
     * 
     * @param email email adress to be checked
     * 
     * @return  true if account exists for given email address, else false
     */
    public boolean emailExists(String email){
    
          // with security checks beeing done,
           // we can proceed to adding data to db        
           CustomerControllerLocal cc=lookupCustomerControllerBeanLocal();
           CustomerEntity ce= cc.getCustomerByEmail(email);
    
           return ce!=null;
           
    } // email exists
    
    
    
        
    /** Returns true, if an account with that nickname address
     *  already exists in the db, else false.
     * 
     * @param nickname to be checked
     * 
     * @return  true if account exists for given email address, else false
     */
    public boolean nicknameExists(String nickname){
    
          // with security checks beeing done,
           // we can proceed to adding data to db        
           CustomerControllerLocal cc=lookupCustomerControllerBeanLocal();
           CustomerEntity ce= cc.getCustomerByNickname(nickname);
    
           return ce!=null;
           
    } // nickname exists
    
    
    
    
    
    
    
    
} // class
