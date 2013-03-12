package de.avci.joride.jbeans.customerprofile;

import de.avci.joride.constants.JoRideConstants;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
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
@Named("publicProfile")
@SessionScoped
public class JPublicCustomerProfile implements Serializable {

    protected static String PUBLIC_PROFILE_DISPLAY_PAGE = "displayPublicProfile";
    
    
    transient Logger log = Logger.getLogger("" + this.getClass());
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

    public void setCustNickname(String arg) {
        this.custNickname = arg;
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
    public void updateFromCustId() {

        // erase all properties, this may be reusing a session scoped bean
        this.blankProperties();

        JPublicCustomerProfileService jpcps = new JPublicCustomerProfileService();

        if (this.getCustId() == null) {
            throw new Error("Cannot update profile,  custId is null");
        }


        JCustomerEntityService jces = new JCustomerEntityService();
        CustomerEntity ce = jces.getCustomerEntityByCustId(custId);
        JPublicCustomerProfile jpcp = new JPublicCustomerProfile();
        jpcp.updateFromCustomerEntity(ce);

    }

    /**
     *
     * /**
     * Update *all* data for a profile of which only the nickname is set. This
     * is used to retrieve user information given the nickname
     */
    public void updateFromCustNickname() {

        String nick = this.getCustNickname();
        // erase all properties, this may be reusing a session scoped bean
        this.blankProperties();
        this.setCustNickname(nick);

        if (nick == null) {
            throw new Error("Cannot update from nickname, nickname is null");
        }

        JCustomerEntityService jces = new JCustomerEntityService();
        CustomerEntity ce = jces.getCustomerEntityByNickname(this.getCustNickname());

        JPublicCustomerProfile jpcp = new JPublicCustomerProfile();
        jpcp.updateFromCustomerEntity(ce);
    }

    /**
     * Coarse method to determine if a customerprofile really exists.
     *
     * @return Return true, if both customerId and nickname of this user are not
     * null, else false
     *
     */
    public boolean seemsToExists() {

        if (this.getCustId() == null) {
            return false;
        }
        if (this.getCustNickname() == null) {
            return false;
        }

        return true;
    }

    /**
     * @return Total number of Ratings for this customer, or null, if there was
     * an Error
     */
    public Integer getDriverRatingsTotal() {
        return new JPublicCustomerProfileService().getRatingsTotalAsDriver(this.getCustId());
    }

    /**
     * @return Average of Ratings for this customer, or null, if there was an
     * Error
     */
    public Integer getDriverRatingsCount() {
        return new JPublicCustomerProfileService().getRatingsCountAsDriver(this.getCustId());
    }

    /**
     * @return Total number of Ratings for this customer, or null, if there was
     * an Error
     */
    public Integer getRiderRatingsTotal() {
        return new JPublicCustomerProfileService().getRatingsTotalAsRider(this.getCustId());
    }

    /**
     * @return Average of Ratings for this customer, or null, if there was an
     * Error
     */
    public Integer getRiderRatingsCount() {
        return new JPublicCustomerProfileService().getRatingsCountAsRider(this.getCustId());
    }

    /**
     * @return a formatted version of getRiderRatingsTotalA/getRiderRatingsCount
     *
     */
    public String getRiderRatingsRatioFormatted() {

        Integer riderRatingsCountI = getRiderRatingsCount();
        Integer riderRatingsTotalI = getRiderRatingsTotal();

        if (riderRatingsTotalI == null) {
            return "--";
        }
        if (riderRatingsCountI == null) {
            return "--";
        }
        // do not divide by zero!
        if (riderRatingsTotalI == 0) {
            return "--";
        }


        float totalF = riderRatingsTotalI.floatValue();
        float countF = riderRatingsCountI.floatValue();

        return new JoRideConstants().createRatingAverageFormat().format(totalF / countF);
    }

    /**
     * @return a formatted version of
     * getDriverRatingsTotalA/getDriverRatingsCount
     *
     */
    public String getDriverRatingsRatioFormatted() {

        Integer driverRatingsCountI = getDriverRatingsCount();
        Integer driverRatingsTotalI = getDriverRatingsTotal();

        if (driverRatingsTotalI == null) {
            return "--";
        }
        if (driverRatingsCountI == null) {
            return "--";
        }
        // do not divide by zero!
        if (driverRatingsTotalI == 0) {
            return "--";
        }


        float totalF = driverRatingsTotalI.floatValue();
        float countF = driverRatingsCountI.floatValue();

        return new JoRideConstants().createRatingAverageFormat().format(totalF / countF);
    }

    /**
     * Driver Rating ration rounded to Integer, so it can be displayed with a
     * Star Rating widget
     *
     * @return Math.round(getDriverRatingsTotal/getDriverRatingsCount), or null
     * if the ratio cannot be computed
     *
     */
    public Integer getDriverStarRating() {

        Integer driverRatingsCountI = getDriverRatingsCount();
        Integer driverRatingsTotalI = getDriverRatingsTotal();

        if (driverRatingsTotalI == null) {
            return null;
        }
        if (driverRatingsCountI == null) {
            return null;
        }
        // do not divide by zero!
        if (driverRatingsTotalI == 0) {
            return null;
        }

        float totalF = driverRatingsTotalI.floatValue();
        float countF = driverRatingsCountI.floatValue();

        return new Integer(Math.round(totalF / countF));
    }

    /**
     * Rider Rating ration rounded to Integer, so it can be displayed with a
     * Star Rating widget
     *
     * @return Math.round(getRiderRatingsTotal/getRiderRatingsCount), or null if
     * the ratio cannot be computed
     *
     */
    public Integer getRiderStarRating() {

        Integer riderRatingsCountI = getRiderRatingsCount();
        Integer riderRatingsTotalI = getRiderRatingsTotal();

        if (riderRatingsTotalI == null) {
            return null;
        }
        if (riderRatingsCountI == null) {
            return null;
        }
        // do not divide by zero!
        if (riderRatingsTotalI == 0) {
            return null;
        }

        float totalF = riderRatingsTotalI.floatValue();
        float countF = riderRatingsCountI.floatValue();

        return new Integer(Math.round(totalF / countF));
    }

    /**
     * @return true, if this customer is driver rated, else false
     */
    public boolean getDriverRated() {

        Integer count = this.getDriverRatingsCount();
        if (count == null) {
            return false;
        }
        if (count < 1) {
            return false;
        }
        return true;
    }

    /**
     * @return true, if this customer is rider rated, else false
     */
    public boolean getRiderRated() {

        Integer count = this.getRiderRatingsCount();
        if (count == null) {
            return false;
        }
        if (count <= 1) {
            return false;
        }
        return true;
    }

    /**
     * Load caller's profile, then move to displayPublicProfile page
     *
     * @return
     */
    public String displayMyPublicProfile() {
        this.blankProperties();
        this.updateFromCallerPublicProfile();
        return PUBLIC_PROFILE_DISPLAY_PAGE;
    }
    
    /**
     * Load profile given by nickname, then move to displayPublicProfile page
     *
     * @return
     */
    public String displayProfileForNickname() {
        String nick=this.getCustNickname();
        this.blankProperties();
        this.setCustNickname(nick);
        this.updateFromCustNickname();
        return PUBLIC_PROFILE_DISPLAY_PAGE;
    }
    
    
    
    
    
    

    /**
     * Blank out public profile, usually before updating
     *
     */
    protected void blankProperties() {

        this.custGender = (char) 0;
        this.custId = null;
        this.custIssmoker = null;
        this.custLicensedate = null;
        this.custNickname = null;
    }
} // class