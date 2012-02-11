/*
    OpenRide -- Car Sharing 2.0
    Copyright (C) 2010  Fraunhofer Institute for Open Communication Systems (FOKUS)

    Fraunhofer FOKUS
    Kaiserin-Augusta-Allee 31
    10589 Berlin
    Tel: +49 30 3463-7000
    info@fokus.fraunhofer.de

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License Version 3 as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.fhg.fokus.openride.customerprofile;

import de.fhg.fokus.openride.helperclasses.ControllerBean;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.UserTransaction;

/**
 *
 * @author pab
 */
@Stateless
public class CustomerControllerBean extends ControllerBean implements CustomerControllerLocal {

    @PersistenceContext
    EntityManager em;
    @Temporal(TemporalType.TIMESTAMP)

    UserTransaction u;
    private static String one = "ich";
    @EJB
    private FavoritePointControllerLocal favoritePointControllerBean;
    final String TEMPLATE_USER = "template_user";

    /*@Override
    public void init() {
    super.init();
    log(this.getClass(), "Init Testing");
    }*/
    /**
     * *********************Businessmethods start*************************
     */
    public CustomerEntity getCustomerByCredentials(String custNickname, String custPasswd) {
        logger.info("getCustomerByCredentials");
        init();
        CustomerEntity c = getCustomerByNickname(custNickname);
        if (c != null && c.getCustPasswd().equals(getMD5Hash(custPasswd))) {
            finish();
            return c;
        }
        finish();
        return null;
    }

    public boolean isNicknameAvailable(String custNickname) {
        init();
        logger.info("isNicknameAvailable");
        CustomerEntity c = getCustomerByNickname(custNickname);
        if (c == null) {
            finish();
            return true;
        }
        finish();
        return false;
    }

    public int addCustomer(String custNickname, String custPasswd, String custFirstname, String custLastname, char custGender, String custEmail, String custMobilephoneno) {
        init();
        logger.info("addCustomer");
        // Make sure no Customer exists for this same nickname
        List<CustomerEntity> customers = (List<CustomerEntity>) em.createNamedQuery("CustomerEntity.findByCustNickname").setParameter("custNickname", custNickname).getResultList();
        if (customers.size() > 0) {
            // No duplicates allowed
            return -1;
        }

        // OK - add them, and return their id
        CustomerEntity c = new CustomerEntity();
        c.setCustNickname(custNickname);
        c.setCustPasswd(getMD5Hash(custPasswd));
        c.setCustFirstname(custFirstname);
        c.setCustLastname(custLastname);
        c.setCustGender(custGender);
        c.setCustEmail(custEmail);
        c.setCustMobilephoneno(custMobilephoneno);
        c.setCustRegistrdate(new Date()); // the current date (timestamp)
        c.setCustGroup("customer");

        em.persist(c);
        finish();

        if (this.getCustomerByNickname(this.TEMPLATE_USER) != null) {
            for (FavoritePointEntity fav : favoritePointControllerBean.getFavoritePointsByCustomer(this.getCustomerByNickname(this.TEMPLATE_USER))) {
                favoritePointControllerBean.addFavoritePoint(fav.getFavptAddress(), fav.getFavptPoint(), fav.getFavptDisplayname(), c);
            }
        }

        return c.getCustId();

    }

    /**
     * This method adds a customer to the database
     */
    public int addCustomer(String custNickname, String custPasswd, String custFirstname, String custLastname, Date custDateofbirth, char custGender, String custMobilephoneno, String custEmail, boolean custIssmoker, boolean custPostident, String custAddrStreet, int custAddrZipcode, String custAddrCity) {
        logger.info("addCustomer");
        init();
        System.out.println("Username " + custNickname);
        boolean exists = false;
        List<CustomerEntity> entities = (List<CustomerEntity>) em.createNamedQuery("CustomerEntity.findByCustNickname").setParameter("custNickname", custNickname).getResultList();

        if (entities.size() > 0) {
            exists = true;
        } else {
            logger.log(Level.INFO, "Entity Customer " + custNickname + "does not exist");
        }

        if (!exists) {
            logger.log(Level.INFO, "So persist it!");
            //Query q = em.createNativeQuery("select * from \"customer\";");
            //System.out.println("[INFO Philipp]The Query: " + q.getResultList().size() + " " + dateFrom.toLocaleString());
            //int index = q.getResultList().size();
            //int index = 0;
            //while ((em.find(CustomerEntity.class, index)) != null) {
            //    index++;
            //}
            //int customer_Id = index;
            CustomerEntity e = new CustomerEntity(custNickname, getMD5Hash(custPasswd), custFirstname, custLastname, custDateofbirth, custGender, custMobilephoneno, custEmail, custIssmoker, custPostident, custAddrStreet, custAddrZipcode, custAddrCity);
            e.setCustGroup("customer");
            e.setCustDriverprefGender(CustomerEntity.PREF_GENDER_DEFAULT);
            e.setCustDriverprefSmoker(CustomerEntity.PREF_SMOKER_DEFAULT);
            e.setCustRiderprefGender(CustomerEntity.PREF_GENDER_DEFAULT);
            e.setCustRiderprefSmoker(CustomerEntity.PREF_SMOKER_DEFAULT);

            em.persist(e);
            finish();
            return e.getCustId();
        } else {
            finish();
            return -1;
        }
    }

    public static String getMD5Hash(String input) {
        StringBuffer stringBuffer = new StringBuffer(1000);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            Formatter f = new Formatter(stringBuffer);
            for (byte b : md5.digest()) {
                f.format("%02x", b);
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return stringBuffer.toString();
    }

    /**
     *
     * @param custId
     */
    public void removeCustomer(int custId) {
        logger.info("removeCustomer");
        init();
        //TODO: make shure that all related entities are deleted, too.
        CustomerEntity e = em.find(CustomerEntity.class, custId);
        logger.log(Level.INFO, "Die Entity: " + e.toString());
        em.remove(e);
        finish();
    }

    /**
     *
     * @param custId
     * @return
     */
    public CustomerEntity getCustomer(int custId) {
        logger.info("getCustomer with custId: " + custId);
        init();
        //CustomerEntity e = em.find(CustomerEntity.class, custId);
        List<CustomerEntity> e = (List<CustomerEntity>) em.createNamedQuery("CustomerEntity.findByCustId").setParameter("custId", custId).getResultList();
        finish();
        if (e != null && e.size() > 0) {
            return e.get(0);
        } else {
            return null;
        }
    }

    /**
     * Returns a <code>CustomerEntity</code> for a given <code>nickname</code>.
     * @param nickname The nickname of the requested Customer.
     * @return
     */
    public CustomerEntity getCustomerByNickname(String nickname) {
        logger.info("getCustomerByNickname");
        init();
        List<CustomerEntity> q = (List<CustomerEntity>) em.createNamedQuery("CustomerEntity.findByCustNickname").setParameter("custNickname", nickname).getResultList();
        if (q.size() > 0) {
            // should only be one result, since customernicknames are unique in DB
            CustomerEntity v = (CustomerEntity) q.get(0);
            if (v != null) {
                return v;
            }
        }
        finish();
        return null;
    }

    /**
     * Returns a <code>CustomerEntity</code> for a given <code>email</code>.
     * @param email The email of the requested Customer.
     * @return
     */
    public CustomerEntity getCustomerByEmail(String email) {
        logger.info("getCustomerByEmail");
        init();
        List<CustomerEntity> q = (List<CustomerEntity>) em.createNamedQuery("CustomerEntity.findByCustEmail").setParameter("custEmail", email).getResultList();
        if (q.size() > 0) {
            // should only be one result, since email addresses are unique in DB
            CustomerEntity v = (CustomerEntity) q.get(0);
            if (v != null) {
                return v;
            }
        }
        finish();
        return null;
    }

    public LinkedList<CustomerEntity> getAllCustomers() {
        init();

        List<CustomerEntity> l = em.createNamedQuery("CustomerEntity.findAll").getResultList();
        LinkedList<CustomerEntity> ll = new LinkedList<CustomerEntity>(l);

        finish();
        return ll;
    }

    /**
     * 
     */
    public void setCustomer() {
        init();
        //TODO: what shall this method do?
        finish();
    }

    /**
     * This method can be used to check whether a Customer with the <code>username</code> and
     * <code>password</code> exists.
     * @param username
     * @param password
     * @return true if a customer exists, false if not.
     */
    public boolean isRegistered(String username, String password) {
        logger.info("isRegistered");
        init();
        //Query q = em.createNativeQuery("SELECT * FROM customer c WHERE c.cust_nickname = '"+username+"';");
        Query q = em.createNamedQuery("CustomerEntity.findByCustNickname").setParameter("custNickname", username);
        if (q.getResultList().size() > 0) {

            CustomerEntity v = (CustomerEntity) q.getResultList().get(0);
            if (v.getCustPasswd().equals(getMD5Hash(password))) {
                return true;
            }
        }
        finish();

        return false;
    }

    /**
     * This method updates a sessionId for a User related to his nickname and password.
     * This is needed if Sessions shall be supported by the application.
     * @param nickname
     * @param password
     * @param id
     */
    /*public void updateSessionId(String nickname, String password, String id) {
    init();
    CustomerEntity e = (CustomerEntity)em.createNamedQuery("CustomerEntity.findByCustNickname").setParameter("cust_nickname", nickname).getSingleResult();
    if(e.getCustPasswd().equals(password)){
    e.setCustSessionId(id);
    em.merge(e);
    }
    //else throw an exception? Error Management.
    finish();
    }*/
    /**
     * This method can be called to check whether someone with <code>nickname</code> is currently logged in.
     * Therefore the field has to be set after the customer correctly logged in.
     * @param nickname
     * @return
     */
    /*
    FIXME: this seems not to be needed!
    public boolean isLoggedIn(String nickname) {

    boolean isLoggedIn = false;

    init();
    //Query q = em.createNativeQuery("SELECT * FROM customer c WHERE c.cust_nickname = '"+username+"';");
    Query q = em.createNamedQuery("CustomerEntity.findByCustNickname").setParameter("custNickname", "nickname2");
    if(q.getResultList().size()>0){

    CustomerEntity v = (CustomerEntity)q.getResultList().get(0);
    isLoggedIn = v.getIsLoggedIn();
    }
    finish();

    return isLoggedIn;
    }*/
    /**
     * *********************Businessmethods end**************************
     */
    public void persist(Object object) {
        init();
        em.persist(object);
        finish();
    }

    public void setPersonalData(int custId, Date custDateofbirth, String custEmail, String custMobilePhoneNo, String custFixedPhoneNo, String custAddrStreet, int custAddrZipcode, String custAddrCity, char custIssmoker, Date custLicenseDate) {
        init();
        logger.info("setPersonalData");
        CustomerEntity c = getCustomer(custId);

        c.setCustDateofbirth(custDateofbirth);

        c.setCustEmail(custEmail);

        c.setCustMobilephoneno(custMobilePhoneNo);

        c.setCustFixedphoneno(custFixedPhoneNo);

        c.setCustAddrStreet(custAddrStreet);

        c.setCustAddrZipcode(custAddrZipcode);

        c.setCustAddrCity(custAddrCity);

        if (custIssmoker == "y".charAt(0)) {
            c.setCustIssmoker(true);
        } else if (custIssmoker == "n".charAt(0)) {
            c.setCustIssmoker(false);
        } else {
            c.setCustIssmoker(null);
        }

        c.setCustLicensedate(custLicenseDate);

        em.persist(c);
        finish();
    }

    public void setBasePersonalData(int custId, String custFirstName, String custLastName, char custGender) {
        init();
        logger.info("setBasePersonalData");
        CustomerEntity c = getCustomer(custId);

        c.setCustFirstname(custFirstName);

        c.setCustLastname(custLastName);

        c.setCustGender(custGender);
        
        em.persist(c);
        finish();
    }

    public void setPassword(int custId, String custPasswd) {
        init();
        logger.info("setPassword");
        CustomerEntity c = getCustomer(custId);
        c.setCustPasswd(getMD5Hash(custPasswd));
        finish();
    }

    public void setDriverPrefs(int custId, int custDriverprefAge, char custDriverprefGender, char custDriverprefSmoker) {
        init();
        logger.info("setDriverPrefs");
        CustomerEntity c = getCustomer(custId);
        //age
        c.setCustDriverprefAge(custDriverprefAge);
        //gender
        if (custDriverprefGender == CustomerEntity.PREF_GENDER_GIRLS_ONLY
                || custDriverprefGender == CustomerEntity.PREF_GENDER_DONT_CARE) {
            c.setCustDriverprefGender(custDriverprefGender);
        } else {
            c.setCustDriverprefGender(CustomerEntity.PREF_GENDER_DEFAULT);
            logger.info("invalid gender pref - set to default (" + custDriverprefGender + ")");
        }
        //smoker
        if (custDriverprefSmoker == CustomerEntity.PREF_SMOKER_DESIRED
                || custDriverprefSmoker == CustomerEntity.PREF_SMOKER_DONT_CARE
                || custDriverprefSmoker == CustomerEntity.PREF_SMOKER_NOT_DESIRED) {
            c.setCustDriverprefSmoker(custDriverprefSmoker);
        } else {
            c.setCustDriverprefSmoker(CustomerEntity.PREF_SMOKER_DEFAULT);
            logger.info("invalid smoker pref - set to default (" + custDriverprefSmoker + ")");
        }
        finish();
    }

    public void setRiderPrefs(int custId, int custRiderprefAge, char custRiderprefGender, char custRiderprefSmoker) {
        logger.info("setRiderPrefs");
        init();
        CustomerEntity c = getCustomer(custId);
        //age
        c.setCustRiderprefAge(custRiderprefAge);
        //gender
        if (custRiderprefGender == CustomerEntity.PREF_GENDER_GIRLS_ONLY
                || custRiderprefGender == CustomerEntity.PREF_GENDER_DONT_CARE) {
            c.setCustRiderprefGender(custRiderprefGender);
        } else {
            c.setCustRiderprefGender(CustomerEntity.PREF_GENDER_DEFAULT);
            logger.info("invalid gender pref - set to default (" + custRiderprefGender + ")");
        }
        //smoker
        if (custRiderprefSmoker == CustomerEntity.PREF_SMOKER_DESIRED
                || custRiderprefSmoker == CustomerEntity.PREF_SMOKER_DONT_CARE
                || custRiderprefSmoker == CustomerEntity.PREF_SMOKER_NOT_DESIRED) {
            c.setCustRiderprefSmoker(custRiderprefSmoker);
        } else {
            c.setCustRiderprefSmoker(CustomerEntity.PREF_SMOKER_DEFAULT);
            logger.info("invalid smoker pref - set to default (" + custRiderprefSmoker + ")");
        }
        finish();
    }
}
