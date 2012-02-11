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
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.customerprofile.RegistrationPassControllerLocal;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class Register {

    /*
     * Form fields declaration
     */
    private HtmlInputText username;
    private HtmlInputSecret password;
    private HtmlInputSecret passwordCheck;
    private HtmlInputText email;
    private HtmlInputText mobilePhoneNumber;
    private HtmlInputText firstName;
    private HtmlInputText lastName;
    private HtmlSelectOneRadio gender;
    private HtmlSelectBooleanCheckbox acceptedTerms;
    private HtmlInputText passCode;
    @EJB
    private CustomerControllerLocal customerControllerBean;
    @EJB
    private RegistrationPassControllerLocal registrationPassControllerBean;
    private Logger logger = Logger.getLogger(this.getClass().toString());

    @PostConstruct
    public void initialize() {

        logger.info("Register_Backing.initialize");

        // check for valid passcode in session (i.e., if the user got here through welcome.jsf)
        /*
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (!ec.getSessionMap().containsKey("passCode")) {
        // no passcode -> redirect to welcome page
        try {
        FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.jsf");
        } catch (IOException ex) {
        Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
         */

    }

    /**
     *
     * @return
     */
    public String register() {

        logger.info("Register_Backing.register");


        // Get random password from registrationPassControllerBean:
        String randomPass = registrationPassControllerBean.getNextValidPassCode();

        // Then use this as the initial password:
        int newUserId = customerControllerBean.addCustomer(username.getValue().toString(), randomPass, firstName.getValue().toString(), lastName.getValue().toString(), gender.getValue().toString().charAt(0), email.getValue().toString(), mobilePhoneNumber.getValue().toString());
        //int newUserId = customerControllerBean.addCustomer(username.getValue().toString(), password.getValue().toString(), firstName.getValue().toString(), lastName.getValue().toString(), gender.getValue().toString().charAt(0), email.getValue().toString(), mobilePhoneNumber.getValue().toString());

        if (newUserId == -1) { // Could not create entry - two customers trying to register same username in parallel...
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Ein neues Konto mit diesem Benutzernamen konnte nicht angelegt werden."));
            return null;
        }

        // Record which user this password was set for:
        registrationPassControllerBean.setInvalidByPassCode(randomPass);
        CustomerEntity newCust = customerControllerBean.getCustomer(newUserId);
        registrationPassControllerBean.setCustIdByPassCode(randomPass, newCust);


        // Invalidate this registration pass
        /*
        registrationPassControllerBean.setInvalidByPassCode(ec.getSessionMap().get("passCode").toString());
        registrationPassControllerBean.setCustIdByPassCode(ec.getSessionMap().get("passCode").toString(), newUserId);
        ec.getSessionMap().remove("passCode");
         */

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().put("registeredUsername", username.getValue());
        ec.getSessionMap().put("registeredPassword", randomPass);

        // Send notification email only if running on robusta2
        /*
        try {
            if (java.net.InetAddress.getLocalHost().getHostName().equals("robusta2.fokus.fraunhofer.de")) {
                sendEmailNotification(username.getValue().toString());
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
         */


        // And now, on to the profile page:
        return "registered";
    }

    private void sendEmailNotification(String username) {
        /*
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", 465);
            props.put("mail.smtp.socketFactory.port", 465);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("--email--", "--password--");
                        }
                    });
            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress("--email--");
            msg.setFrom(addressFrom);
            InternetAddress addressTo = new InternetAddress("anna.kress@fokus.fraunhofer.de");
            InternetAddress addressCC = new InternetAddress("tilo.kussatz@fokus.fraunhofer.de");
            msg.setRecipient(Message.RecipientType.TO, addressTo);
            msg.setRecipient(Message.RecipientType.CC, addressCC);
            msg.setSubject("Neuer OpenRide-Benutzer: " + username);
            msg.setContent("Es hat sich ein neuer Benutzer namens '" + username + "' registriert. \n\nhttps://www.open-ride.mobi/OpenRideAdmin/registration_passes_used.jsf", "text/plain");
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }

         */
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
    public void validateUsername(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        if (!customerControllerBean.isNicknameAvailable((String) value)) {
            FacesMessage message = new FacesMessage("Dieser Benutzername ist leider schon vergeben.");
            throw new ValidatorException(message);
        }

    }

    /**
     *
     * @param context
     * @param toValidate
     * @param value
     * @throws ValidatorException
     */
    public void validateEmail(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {
        String eMail = (String) value;

        CustomerEntity c = customerControllerBean.getCustomerByEmail(eMail);
        if (c != null) {
            FacesMessage message = new FacesMessage("Ein Benutzer mit dieser E-Mail-Adresse ist bereits registriert.");
            throw new ValidatorException(message);
        }

        // Pilotierung: nur ZU-Adressen zulässig
        /*if (!eMail.endsWith("zeppelin-university.de") && !eMail.endsWith("zeppelin-university.net") && !eMail.endsWith("fokus.fraunhofer.de")) {
            FacesMessage message = new FacesMessage("E-Mail-Adresse muss auf \"zeppelin-university.[net|de]\" enden.");
            throw new ValidatorException(message);
        }*/

    }

    /**
     *
     * @param context
     * @param toValidate
     * @param value
     * @throws ValidatorException
     */
    public void validatePassword(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        String password1 = (String) this.getPassword().getValue();
        String password2 = (String) value;

        if (!password1.equals(password2)) {
            FacesMessage message = new FacesMessage("Die Passwörter stimmen nicht überein.");
            throw new ValidatorException(message);
        }

    }

    public void validateAcceptedTerms(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        if (value.equals(Boolean.FALSE)) {
            FacesMessage message = new FacesMessage("Bestätigung erforderlich.");
            throw new ValidatorException(message);
        }

    }

    /*
     * Getters / setters
     */
    public HtmlSelectBooleanCheckbox getAcceptedTerms() {
        return acceptedTerms;
    }

    public void setAcceptedTerms(HtmlSelectBooleanCheckbox acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
    }

    public HtmlInputText getEmail() {
        return email;
    }

    public void setEmail(HtmlInputText email) {
        this.email = email;
    }

    public HtmlInputText getFirstName() {
        return firstName;
    }

    public void setFirstName(HtmlInputText firstName) {
        this.firstName = firstName;
    }

    public HtmlSelectOneRadio getGender() {
        return gender;
    }

    public void setGender(HtmlSelectOneRadio gender) {
        this.gender = gender;
    }

    public HtmlInputText getLastName() {
        return lastName;
    }

    public void setLastName(HtmlInputText lastName) {
        this.lastName = lastName;
    }

    public HtmlInputSecret getPassword() {
        return password;
    }

    public void setPassword(HtmlInputSecret password) {
        this.password = password;
    }

    public HtmlInputSecret getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(HtmlInputSecret passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public HtmlInputText getUsername() {
        return username;
    }

    public void setUsername(HtmlInputText username) {
        this.username = username;
    }

    public HtmlInputText getPassCode() {
        return passCode;
    }

    public void setPassCode(HtmlInputText passCode) {
        this.passCode = passCode;
    }

    public HtmlInputText getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(HtmlInputText mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
}
