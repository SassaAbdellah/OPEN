package de.avci.joride.jbeans.customerprofile;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

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
@Named
@RequestScoped
public class JPublicCustomerProfile implements Serializable {

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
    boolean custIssmoker;

    public boolean getCustIssmoker() {
        return custIssmoker;
    }

    /**
     * Fill with Data from givenCustomerId
     */
    public void updateFromCustomerEntity(CustomerEntity ce) {

        this.custId = ce.getCustId();
        this.custGender = ce.getCustGender();
        this.custLicensedate = ce.getCustLicensedate();
        this.custNickname = ce.getCustNickname();
        this.custIssmoker = ce.getCustIssmoker();
    }
    
    
      
    
} // class