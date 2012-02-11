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

package de.fhg.fokus.openride.webclient.backing;

import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.RegistrationPassControllerLocal;
import de.fhg.fokus.openride.customerprofile.RegistrationPassEntity;
import java.util.logging.Logger;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class Salute {

    /*
     * Login fields declaration
     */
    private HtmlInputText username;
    private HtmlInputSecret password;

    /*
     * Registration fields declaration
     */
    private HtmlInputText passCode;
    //@EJB
    //private CustomerControllerLocal customerControllerBean;
    @EJB
    private RegistrationPassControllerLocal registrationPassControllerBean;
    private Logger logger = Logger.getLogger(this.getClass().toString());
    private String nextPassCode;
    boolean renderRegistrationSuccessMessage = false;
    private String registeredUsername = "";
    private String registeredPassword = "";

    @PostConstruct
    public void initialize() {

        logger.info("Welcome_Backing.initialize");

        // Display success message if the user just finished their registration
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (ec.getSessionMap().containsKey("registeredUsername")) {
            registeredUsername = ec.getSessionMap().get("registeredUsername").toString();
            registeredPassword = ec.getSessionMap().get("registeredPassword").toString();
            renderRegistrationSuccessMessage = true;
        }

        // get valid passcode for "open" testing period
        /*
        nextPassCode = registrationPassControllerBean.getRandomValidPassCode();
        logger.fine(nextPassCode);
         */

    }

    /**
     *
     * @return
     */
    /*public String login() {

    CustomerEntity c = customerControllerBean.getCustomerByCredentials(username.getValue().toString(), password.getValue().toString());


    if (c == null) {
    // login failed
    FacesContext.getCurrentInstance().addMessage(null,
    new FacesMessage("Ungültige Zugangsdaten!"));
    return "notLoggedIn";
    } else {
    // login success
    UserBean managedUserBean =
    (UserBean) JSFUtil.getManagedObject("UserBean");
    UserUtil.copyUserProperties(c, managedUserBean);
    managedUserBean.setIsLoggedIn(true);

    // Place authorized user on session to disable security filter
    // todo JSFUtil.storeOnSession(FacesContext.getCurrentInstance(), AUTH_USER, "Authorized_User");
    return "loggedIn";
    }
    }*/
    /**
     *
     * @return
     * @throws IOException
     */
    public String logout() throws IOException {

        logger.info("Welcome_Backing.logout");

        // Destroy active session
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) ectx.getSession(false);
        session.invalidate();

        return "loggedOut";

    }

    /**
     *
     * @return
     */
    public String register() {

        logger.info("Welcome_Backing.register ");

        // Put passCode into session
        /*
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().put("passCode", passCode.getValue().toString());
         */

        return "register";
    }

    /*
     * Validators
     */
    /**
     *
     * @param context
     * @param toValidate
     * @param value
     * @throws ValidatorException
     */
    public void validatePassCode(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        logger.info("Welcome_Backing.validatePassCode");

        String pc = (String) value;

        RegistrationPassEntity registrationPass = registrationPassControllerBean.getRegistrationPassByPassCode(pc);
        logger.info("registrationPass: " + registrationPass);
        if (registrationPass == null || !registrationPassControllerBean.isValid(registrationPass.getId())) {
            logger.info("hallo?!");
            FacesMessage message = new FacesMessage("Ungültiger Registrierungscode.");
            throw new ValidatorException(message);
        }

    }

    /*
     * Getters / setters
     */
    public HtmlInputText getPassCode() {
        return passCode;
    }

    public void setPassCode(HtmlInputText passCode) {
        this.passCode = passCode;
    }

    public HtmlInputSecret getPassword() {
        return password;
    }

    public void setPassword(HtmlInputSecret password) {
        this.password = password;
    }

    public HtmlInputText getUsername() {
        return username;
    }

    public void setUsername(HtmlInputText username) {
        this.username = username;
    }

    public String getNextPassCode() {
        return nextPassCode;
    }

    public void setNextPassCode(String nextPassCode) {
        this.nextPassCode = nextPassCode;
    }

    public String getRegisteredUsername() {
        return registeredUsername;
    }

    public void setRegisteredUsername(String registeredUsername) {
        this.registeredUsername = registeredUsername;
    }

    public String getRegisteredPassword() {
        return registeredPassword;
    }

    public void setRegisteredPassword(String registeredPassword) {
        this.registeredPassword = registeredPassword;
    }

    public boolean isRenderRegistrationSuccessMessage() {
        return renderRegistrationSuccessMessage;
    }

    public void setRenderRegistrationSuccessMessage(boolean renderRegistrationSuccessMessage) {
        this.renderRegistrationSuccessMessage = renderRegistrationSuccessMessage;
    }
}
