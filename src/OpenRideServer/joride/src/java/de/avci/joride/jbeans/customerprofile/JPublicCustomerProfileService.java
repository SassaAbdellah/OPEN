/**
 * Service to get and Put CustomerEntityBeans the EJB Way.
 *
 */
package de.avci.joride.jbeans.customerprofile;

import de.avci.joride.jbeans.riderundertakesride.JRiderUndertakesRideEntityService;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerBean;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Service to get *public* CustomerProfiles from the database
 *
 *
 *
 * @author jochen
 *
 */
public class JPublicCustomerProfileService {

    Logger log = Logger.getLogger(this.getClass().getCanonicalName());
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

    /**
     * Update given PublicCustomerProfile from CustomerProfile given by custId
     *
     * @param jcpp PublicCustomerProfile to be updated
     * @param custId CustomerId from which data should be updated.
     */
    public void updatePublicCustomerProfileFromID(JPublicCustomerProfile jcpp, int custId) {

        CustomerControllerLocal ccl = this.lookupCustomerControllerBeanLocal();
        CustomerEntity ce = ccl.getCustomer(custId);

        if (ce == null) {
            return;
        }

        jcpp.updateFromCustomerEntity(ce);
    }

    /**
     * Update given PublicCustomerProfile from CustomerProfile given by custId
     *
     * @param jcpp PublicCustomerProfile to be updated
     * @param nickName of customer for which data should be updated.
     */
    public void updatePublicCustomerProfileFromNickname(JPublicCustomerProfile jcpp, String nickName) {

        CustomerControllerLocal ccl = this.lookupCustomerControllerBeanLocal();
        CustomerEntity ce = ccl.getCustomerByNickname(nickName);

        if (ce == null) {
            return;
        }

        jcpp.updateFromCustomerEntity(ce);
    }

    /**
     * Determine caller from http request, then update given public profile from
     * data so obtained
     *
     *
     * @param jcpp the public profile to be updated.
     *
     */
    public void updateFromCallerPublicProfile(JPublicCustomerProfile jcpp) {

        Integer custId = new JCustomerEntityService().getCustIDSafely();

        if (custId == null) {
            return;
        }

        this.updatePublicCustomerProfileFromID(jcpp, custId);
    }

    /**
     * Get Number of ratings for customer
     *
     * @param customerId customerId for customer to be rated
     * @return Average Rating, or null if something ugly happened
     */
    public Integer getRatingsCount(Integer customerId) {

        CustomerEntity ce = this.lookupCustomerControllerBeanLocal().getCustomer(customerId);

        if (ce == null) {
            log.log(Level.WARNING, "Tryed to get ratings for non existing customer " + customerId);
            return null;
        }

        try {
            RiderUndertakesRideControllerLocal rurcl = new JRiderUndertakesRideEntityService().lookupRiderUndertakesRideControllerBeanLocal();
            return rurcl.getRatingsCountByCustomer(ce);
        } catch (Exception exc) {
            log.log(Level.WARNING, "Unexpected Exception while retrieving Rating count for customer " + customerId, exc);
            return null;
        }

    } // getRatingRatio 

    /**
     * Get Total Sum of all Ratings for this customer
     *
     * @param customerId customerId for customer to be rated
     * @return Total number of Ratings, or null if something ugly happened
     */
    public Integer getRatingsTotal(Integer customerId) {

        CustomerEntity ce = this.lookupCustomerControllerBeanLocal().getCustomer(customerId);

        if (ce == null) {
            log.log(Level.WARNING, "Tryed to get ratings for non existing customer " + customerId);
            return null;
        }

        try {
            RiderUndertakesRideControllerLocal rurcl = new JRiderUndertakesRideEntityService().lookupRiderUndertakesRideControllerBeanLocal();
            return rurcl.getRatingsTotalByCustomer(ce);
        } catch (Exception exc) {
            log.log(Level.WARNING, "Unexpected Exception while retrieving Rating total for customer " + customerId, exc);
            return null;
        }

    } // getRatingTotal
} // class
