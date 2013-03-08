package de.avci.joride.jbeans.customerprofile;

import de.avci.joride.utils.CRUDConstants;
import de.avci.joride.utils.HTTPUtil;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.logging.Logger;


/**
 * **Public** Customer Profile.
 *
 * This is the subset of personal data from {@link JCustomerEntity} that may
 * safely be displayed to other customers.
 *
 * Consequently, this gets created from JCustomerProfile and has only Getter
 * methods.
 *
 *
 *
 *
 * @author jochen
 */
@Named("publicProfile")
@RequestScoped
public class JPublicCustomerProfile implements Serializable {
    
    
    transient Logger log= Logger.getLogger(""+this.getClass());
    
   
    /**
     * CustomerId
     */
    private Integer custId;

    public Integer getCustId() {
        return this.custId;
    }
    /**
     * Customer Nickname
     */
    private String custNickname;

    public String getCustNickname() {
        return this.custNickname;
    }
    /**
     * Gender should be known to Riders a priori, since it may effect accept
     * decline decision
     */
    private char custGender;

    public char getCustGender() {
        return custGender;
    }
    /**
     * Since when is this user allowed to drive. Another important information
     * before deciding on whether or not to acceppt a ride offer.
     *
     */
    Date custLicensedate;

    public Date getCustLicensedate() {
        return custLicensedate;
    }
    /**
     * Whether Customer is smoker, respectively whether or not smoking is
     * allowed during rides.
     *
     */
    Boolean custIssmoker;

    public Boolean getCustIssmoker() {
        return custIssmoker;
    }

    /**
     * Fill with Data from givenCustomerId
     */
    public void updateFromCustomerEntity(CustomerEntity ce) {

        if (ce == null) {
            throw new Error("Cannot retrieve public customer data, customer is null");
        }


        this.custId = ce.getCustId();
        this.custGender = ce.getCustGender();
        this.custLicensedate = ce.getCustLicensedate();
        this.custNickname = ce.getCustNickname();

        this.custIssmoker = ce.getCustIssmoker();

    }

    /**
     * Update this profile from the caller's CustomerEntity.
     *
     */
    public void updateFromCallerPublicProfile() {

        JPublicCustomerProfileService jpcps = new JPublicCustomerProfileService();
        jpcps.updateFromCallerPublicProfile(this);
    }

    /**
     * Update this profile from the CustomerEntity given by Id.
     *
     * @param custId
     */
    public void updateFromProfile(Integer custId) {

        JPublicCustomerProfileService jpcps = new JPublicCustomerProfileService();
        
        if(custId==null){throw new Error("Cannot update profile, numerical custId is null");}
        
        
        jpcps.updatePublicCustomerProfileFromID(this, custId);
    }

    
    
    /** Extract value for id from HTTPRequest,
     *  then retrieve CustomerId for id,
     *  then update data for this object from values thus retrieved.
     * 
     */
    
    public void updateFromIdByHTTPParam() {
        
        HTTPUtil hu=new HTTPUtil();
        String idStr=hu.getParameterSingleValue(new CRUDConstants().getParamNameCrudId());
        
        Integer id=null;
        
        try{  id=Integer.parseInt(idStr);
        } catch(Exception exc){
            // return prematurely
            log.log(Level.WARNING, "Error while retrieving Id, cannot update PublicProfile",exc);
            return;
        }
 
        this.updateFromProfile(id);       
    }
    
      
} // class