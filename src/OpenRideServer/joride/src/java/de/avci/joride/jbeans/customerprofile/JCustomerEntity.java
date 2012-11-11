/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.jbeans.customerprofile;

import de.avci.joride.session.HTTPUser;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Small Wrapper class making Entity Bean CustomerEntity availlable as a CDI
 * Bean for Use in JSF Frontend.
 *
 * @author jochen
 *
 */
@Named
@SessionScoped
public class JCustomerEntity extends CustomerEntity {

    /**
     * Empty public bean constructor.
     */
    public JCustomerEntity() {
        super();
    }

    /**
     * Update Data in the JCustomerEntityBean from Database
     */
    public void updateFromDB() {


        CustomerEntity ce = (new JCustomerEntityService()).getCustomerEntitySavely();

        this.setCustAccountBalance(ce.getCustAccountBalance());
        this.setCustAddrCity(ce.getCustAddrCity());
        this.setCustAddrStreet(ce.getCustAddrStreet());
        this.setCustAddrZipcode(ce.getCustAddrZipcode());
        this.setCustBankAccount(ce.getCustBankAccount());
        this.setCustBankCode(ce.getCustBankCode());
        this.setCustDateofbirth(ce.getCustDateofbirth());
        this.setCustDriverprefAge(ce.getCustDriverprefAge());
        this.setCustDriverprefGender(ce.getCustRiderprefGender());
        this.setCustDriverprefMusictaste(ce.getCustDriverprefMusictaste());
        this.setCustDriverprefSmoker(ce.getCustDriverprefIssmoker());
        this.setCustEmail(ce.getCustEmail());
        this.setCustFirstname(ce.getCustFirstname());
        this.setCustFixedphoneno(ce.getCustFixedphoneno());
        this.setCustGender(ce.getCustGender());
        this.setCustGroup(ce.getCustGroup());
        this.setCustId(ce.getCustId());
        this.setCustIssmoker(ce.getCustIssmoker());
        this.setCustLastname(ce.getCustLastname());
        this.setCustLicensedate(ce.getCustLicensedate());
        this.setCustMobilephoneno(ce.getCustMobilephoneno());
        this.setCustNickname(ce.getCustNickname());
        this.setCustPasswd(ce.getCustPasswd());
        this.setCustPostident(ce.getCustPostident());
        this.setCustPresencemssg(ce.getCustPresencemssg());
        this.setCustProfilepic(ce.getCustProfilepic());
        this.setCustRegistrdate(ce.getCustRegistrdate());
        //
        // Rider Preferences
        //
        this.setCustRiderprefAge(ce.getCustRiderprefAge());
        this.setCustRiderprefGender(ce.getCustRiderprefGender());
        this.setCustRiderprefSmoker(ce.getCustRiderprefIssmoker());

        // Driver Preferences
        this.setCustDriverprefAge(ce.getCustDriverprefAge());
        this.setCustDriverprefGender(ce.getCustDriverprefGender());
        this.setCustDriverprefSmoker(ce.getCustDriverprefIssmoker());
        //
        // Car Details Collection
        //
        this.setCarDetailsEntityCollection(ce.getCarDetailsEntityCollection());
        
        // Session ID
        //
        this.setCustSessionId(ce.getCustSessionId());

    } // update from DB

    /**
     * Superclass CustomerEntity has a name mismatch with the
     * custDriverPrefSmoke Property. Getter is called getDriverPrefIssmoker,
     * while Setter is calld setDriverPrefSmoker.
     *
     * Since we adopted the policy not to change the OpenRide interfaces until
     * the jORideFrontend is fully operable, we fix this by adding a wrapper.
     *
     * FIXME: rename methods in EJB as soon as the frontend is production ready.
     */
    public char getCustDriverprefSmoker() {

        try {
            return this.getCustDriverprefIssmoker();
        } catch (java.lang.NullPointerException exc) {
            return '?';
        }
    }

    /**
     * Superclass CustomerEntity has a name mismatch with the
     * custDriverPrefSmoke Property. Getter is called getDriverPrefIssmoker,
     * while Setter is calld setDriverPrefSmoker.
     *
     * Since we adopted the policy not to change the OpenRide interfaces until
     * the jORideFrontend is fully operable, we fix this by adding a wrapper.
     *
     * FIXME: rename methods in EJB as soon as the frontend is production ready.
     */
    public void setCustDriverprefSmoker(char arg) {

        super.setCustDriverprefSmoker(arg);
    }

    /**
     * Superclass CustomerEntity has a name mismatch with the custRiderPrefSmoke
     * Property. Getter is called getRiderPrefIssmoker, while Setter is calld
     * setRiderPrefSmoker.
     *
     * Since we adopted the policy not to change the OpenRide interfaces until
     * the jORideFrontend is fully operable, we fix this by adding a wrapper.
     *
     * FIXME: rename methods in EJB as soon as the frontend is production ready.
     */
    public char getCustRiderprefSmoker() {

        try {
            return this.getCustRiderprefIssmoker();
        } catch (java.lang.NullPointerException exc) {
            return '?';
        }
    }

    /**
     * Superclass CustomerEntity has a name mismatch with the custRiderPrefSmoke
     * Property. Getter is called getRiderPrefIssmoker, while Setter is calld
     * setRiderPrefSmoker.
     *
     * Since we adopted the policy not to change the OpenRide interfaces until
     * the jORideFrontend is fully operable, we fix this by adding a wrapper.
     *
     * FIXME: rename methods in EJB as soon as the frontend is production ready.
     */
    public void setCustRiderprefSmoker(char arg) {

        super.setCustRiderprefSmoker(arg);
    }


    
    
    
     /**  Returns the CarDetails of this 
     */
    public Object[] getCarDetailsArray() {

        Collection con = this.getCarDetailsEntityCollection();       
        return con.toArray();

    }

    
    
    
    
    
    

    /**
     * Update Data in the JCustomerEntityBean from Database
     */
    public void updatePersonalDataToDB() {
        new JCustomerEntityService().setCustomerPersonalData(this);
    } // update to  DB

    /**
     * Update Data in the JCustomerEntityBean from Database
     */
    public void updateDriverPreferencesToDB() {
        new JCustomerEntityService().setCustDriverPrefs(this);
    } // update to  DB

    /**
     * Update Data in the JCustomerEntityBean from Database
     */
    public void updateRiderPreferencesToDB() {
        new JCustomerEntityService().setCustRiderPrefs(this);
    } // update to  DB

    public String debugPrintout() {


        String res = "\n  ";
        res += "\n  == CustomerPersonalData ============================= ";
        res += "\n  == FirstName              : " + this.getCustFirstname();
        res += "\n  == LastName               : " + this.getCustLastname();
        res += "\n  == UserNickName           : " + this.getCustNickname();
        res += "\n  == CustDateofbirth        : " + this.getCustDateofbirth();
        res += "\n  == CustGender             : " + this.getCustGender();
        res += "\n  == CustGroup              : " + this.getCustGroup();
        res += "\n  == CustId                 : " + this.getCustId();
        res += "\n  == CustIssmoker           : " + this.getCustIssmoker();
        res += "\n  == CustLicensedate        : " + this.getCustLicensedate();
        res += "\n  ";
        res += "\n  == CustomerContactData";
        res += "\n  CustEmail                 : " + this.getCustEmail();
        res += "\n  CustFixedphoneno          : " + this.getCustFixedphoneno();
        res += "\n  CustMobilephoneno         : " + this.getCustMobilephoneno();
        res += "\n ";
        res += "\n  == CustomerAdress      ============================= ";
        res += "\n  CustAddrCity              : " + this.getCustAddrCity();
        res += "\n  CustAddrStreet            : " + this.getCustAddrStreet();
        res += "\n  CustAddrZipcode           : " + this.getCustAddrZipcode();
        res += "\n  ";
        res += "\n  == OpenRideSpecificData ============================= ";
        res += "\n  CustId                    : " + this.getCustId();
        res += "\n  CustPasswd                : " + this.getCustPasswd();
        res += "\n  CustPostident             : " + this.getCustPostident();
        res += "\n  CustPresencemssg          : " + this.getCustPresencemssg();
        res += "\n  CustProfilepic            : " + this.getCustProfilepic();
        res += "\n  CustRegistrdate           : " + this.getCustRegistrdate();
        res += "\n  ";
        res += "\n  == Banking Data are not used!  ============== ";
        res += "\n  CustAccountBalance        : " + this.getCustAccountBalance();
        res += "\n  CustBankAccount           : " + this.getCustBankAccount();
        res += "\n  CustBankCode              : " + this.getCustBankCode();
        res += "\n  ";


        //
        // Driver Preferences
        //
        res += "\n  CustDriverprefAge         : " + this.getCustDriverprefAge();
        res += "\n  CustDriverprefGender      : " + this.getCustRiderprefGender();
        res += "\n  CustDriverprefMusictaste  : " + this.getCustDriverprefMusictaste();
        res += "\n  CustDriverprefSmoker      : " + this.getCustDriverprefIssmoker();
        //
        // Rider Preferences
        //
        res += "\n  CustRiderprefAge          : " + this.getCustRiderprefAge();
        res += "\n  CustRiderprefGender       : " + this.getCustRiderprefGender();
        res += "\n  CustRiderprefMusictaste   : " + this.getCustRiderprefMusictaste();
        res += "\n  CustRiderprefSmoker       : " + this.getCustRiderprefIssmoker();
        //
        // Session ID
        //
        res += "\n  CustSessionId             : " + this.getCustSessionId();

        return res;

    }
}// class

