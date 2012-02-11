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

import de.fhg.fokus.openride.customerprofile.CarDetailsControllerLocal;
import de.fhg.fokus.openride.customerprofile.CarDetailsEntity;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.webclient.UserBean;
import de.fhg.fokus.openride.webclient.util.JSFUtil;
import de.fhg.fokus.openride.webclient.util.UserUtil;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import org.apache.myfaces.custom.date.HtmlInputDate;
import org.apache.myfaces.custom.fileupload.HtmlInputFileUpload;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class Profile {

    /*
     * Form fields declaration
     */
    // General
    private HtmlInputText presenceMessage;
    // Personal data
    private HtmlInputFileUpload picture;
    private String firstName;
    private String lastName;
    private String gender;
    private HtmlInputDate dateOfBirth;
    private HtmlInputText email;
    private HtmlInputText mobilePhoneNumber;
    private HtmlInputText fixedPhoneNumber;
    private HtmlInputText streetAddress;
    private HtmlInputText zipCode;
    private HtmlInputText city;
    private HtmlSelectOneRadio isSmoker;
    private HtmlInputText licenseDate;
    private HtmlInputText carColour;
    private HtmlInputText carBrand;
    private HtmlInputText carBuildYear;
    private HtmlInputText carPlateNo;
    // Account info
    private HtmlInputSecret passwordOld;
    private HtmlInputSecret password;
    private HtmlInputSecret passwordCheck;
    // General prefs
    private HtmlSelectOneRadio prefIsSmoker;
    private HtmlSelectOneRadio prefGender;
    // Driver prefs
    //private HtmlInputText driverPrefAge; /* may be resurrected later... */    
    // Rider prefs
    //private HtmlInputText riderPrefAge; /* may be resurrected later... */    
    boolean renderPersonalDataSuccessMessage = false;
    boolean renderPictureSuccessMessage = false;
    boolean renderPictureErrorMessage = false;
    boolean renderDriverRiderPrefsSuccessMessage = false;
    boolean renderPasswordSuccessMessage = false;
    String pictureBasename;
    @EJB
    private CustomerControllerLocal customerControllerBean;
    private CustomerEntity customer;
    @EJB
    private CarDetailsControllerLocal carDetailsControllerBean;
    private Logger logger = Logger.getLogger(this.getClass().toString());

    @PostConstruct
    public void initialize() {

        logger.info("Profile_Backing.initialize");

        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        customer = customerControllerBean.getCustomerByNickname(ec.getRemoteUser());

        // Copy current user data to managedUserBean
        UserUtil.copyUserProperties(customer, managedUserBean);

        pictureBasename = "pictures/profile/" + managedUserBean.getUsername() + "_" + managedUserBean.getId();

        firstName = managedUserBean.getFirstName();

        lastName = managedUserBean.getLastName();

        if (managedUserBean.getGender() == 'm') {
            gender = "männlich";
        } else {
            gender = "weiblich";
        }

        if (managedUserBean.getDateOfBirth() != null) {
            dateOfBirth = new HtmlInputDate();
            dateOfBirth.setValue(managedUserBean.getDateOfBirth());
        }

        email = new HtmlInputText();
        email.setValue(managedUserBean.getEmail());

        mobilePhoneNumber = new HtmlInputText();
        mobilePhoneNumber.setValue(managedUserBean.getMobilePhoneNumber());


        fixedPhoneNumber = new HtmlInputText();
        fixedPhoneNumber.setValue(managedUserBean.getFixedPhoneNumber());

        streetAddress = new HtmlInputText();
        streetAddress.setValue(managedUserBean.getStreetAddress());

        if (managedUserBean.getZipCode() != 0) {
            zipCode = new HtmlInputText();
            zipCode.setValue(managedUserBean.getZipCode());
        }

        city = new HtmlInputText();
        city.setValue(managedUserBean.getCity());

        isSmoker = new HtmlSelectOneRadio();
        if (managedUserBean.isIsSmoker() == null) {
            isSmoker.setValue("-");
        } else if (managedUserBean.isIsSmoker() == true) {
            isSmoker.setValue("y");
        } else if (managedUserBean.isIsSmoker() == false) {
            isSmoker.setValue("n");
        }

        if (managedUserBean.getLicenseDate() != null) {
            licenseDate = new HtmlInputText();
            Calendar cal = Calendar.getInstance();
            cal.setTime(managedUserBean.getLicenseDate());
            licenseDate.setValue(cal.get(Calendar.YEAR));
        }



        CarDetailsEntity cardetails = carDetailsControllerBean.getCarDetails(customer);
        if (cardetails != null) {
            if (cardetails.getCardetColour() != null) {
                carColour = new HtmlInputText();
                carColour.setValue(cardetails.getCardetColour());
            }

            if (cardetails.getCardetBrand() != null) {
                carBrand = new HtmlInputText();
                carBrand.setValue(cardetails.getCardetBrand());
            }

            if (cardetails.getCardetBuildyear() != null) {
                carBuildYear = new HtmlInputText();
                carBuildYear.setValue(cardetails.getCardetBuildyear());
            }

            if (cardetails.getCardetPlateno() != null) {
                carPlateNo = new HtmlInputText();
                carPlateNo.setValue(cardetails.getCardetPlateno());
            }
        }


        /*if (managedUserBean.getDriverPrefAge() != 0) {
        driverPrefAge = new HtmlInputText();
        driverPrefAge.setValue(managedUserBean.getDriverPrefAge());
        }*/

        prefGender = new HtmlSelectOneRadio();
        /*if (managedUserBean.getDriverPrefGender() == "m".charAt(0)) {
        driverPrefGender.setValue("m");
        } else */ if (managedUserBean.getDriverPrefGender() == "f".charAt(0)) {
            prefGender.setValue("f");
        } else {
            prefGender.setValue("-");
        }

        prefIsSmoker = new HtmlSelectOneRadio();
        prefIsSmoker.setValue("-");
        if (managedUserBean.isDriverPrefIsSmoker() == "n".charAt(0) || managedUserBean.isDriverPrefIsSmoker() == "y".charAt(0)) {
            prefIsSmoker.setValue(managedUserBean.isDriverPrefIsSmoker());
        }

        logger.info("prefIsSmoker: " + managedUserBean.isDriverPrefIsSmoker());

        /*if (managedUserBean.getRiderPrefAge() != 0) {
        riderPrefAge = new HtmlInputText();
        riderPrefAge.setValue(managedUserBean.getRiderPrefAge());
        }*/

    }

    public void editPicture() {

        logger.info("Profile_Backing.editPicture");

        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");

        String uploadedFileName = managedUserBean.getUsername() + "_" + managedUserBean.getId();

        // Filesystem path to the profile pictures folder
        //String profilePicturesPath = "C:\\OpenRide\\pictures\\profile";
        String profilePicturesPath = "/usr/lib/openride/pictures/profile";

        // FIXME: The following try/catch may be removed for production deployments:
        try {
            System.out.println("getHostName: "+java.net.InetAddress.getLocalHost().getHostName());
            if (java.net.InetAddress.getLocalHost().getHostName().equals("elan-tku-r2032.fokus.fraunhofer.de")) {
                profilePicturesPath = "/mnt/windows/OpenRide/pictures/profile";
            }
            else if (java.net.InetAddress.getLocalHost().getHostName().equals("robusta2.fokus.fraunhofer.de")) {
                profilePicturesPath = "/usr/lib/openride/pictures/profile";
            }
        } catch (UnknownHostException ex) {
        }

        int picSize = 125;
        int picThumbSize = 60;

        try {
            UploadedFile uploadedFile = ((UploadedFile) picture.getValue());

            BufferedImage uploadedPicture = ImageIO.read(uploadedFile.getInputStream());
            if (uploadedPicture == null) {
                setRenderPictureErrorMessage(true);
                return;
            }

            int newWidth, newHeight;
            int xPos, yPos;
            float ratio = (float) uploadedPicture.getHeight() / (float) uploadedPicture.getWidth();

            // Resize for "large" size
            if (uploadedPicture.getWidth() > uploadedPicture.getHeight()) {
                newWidth = picSize;
                newHeight = Math.round(newWidth * ratio);
            } else {
                newHeight = picSize;
                newWidth = Math.round(newHeight / ratio);
            }

            Image resizedPicture = uploadedPicture.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            xPos = Math.round((picSize - newWidth) / 2);
            yPos = Math.round((picSize - newHeight) / 2);
            BufferedImage bim = new BufferedImage(picSize, picSize, BufferedImage.TYPE_INT_RGB);
            bim.createGraphics().setColor(Color.white);
            bim.createGraphics().fillRect(0, 0, picSize, picSize);
            bim.createGraphics().drawImage(resizedPicture, xPos, yPos, null);

            File outputPicture = new File(profilePicturesPath, uploadedFileName + ".jpg");

            if (!ImageIO.write(bim, "jpg", outputPicture)) {
                setRenderPictureErrorMessage(true);
                return;
            }


            // Resize again for "thumb" size
            if (uploadedPicture.getWidth() > uploadedPicture.getHeight()) {
                newWidth = picThumbSize;
                newHeight = Math.round(newWidth * ratio);
            } else {
                newHeight = picThumbSize;
                newWidth = Math.round(newHeight / ratio);
            }

            resizedPicture = uploadedPicture.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            xPos = Math.round((picThumbSize - newWidth) / 2);
            yPos = Math.round((picThumbSize - newHeight) / 2);
            bim = new BufferedImage(picThumbSize, picThumbSize, BufferedImage.TYPE_INT_RGB);
            bim.createGraphics().setColor(Color.white);
            bim.createGraphics().fillRect(0, 0, picThumbSize, picThumbSize);
            bim.createGraphics().drawImage(resizedPicture, xPos, yPos, null);

            outputPicture = new File(profilePicturesPath, uploadedFileName + "_thumb.jpg");

            if (!ImageIO.write(bim, "jpg", outputPicture)) {
                setRenderPictureErrorMessage(true);
                return;
            }

            setRenderPictureSuccessMessage(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File upload / resize unsuccessful.");
            setRenderPictureErrorMessage(true);
        }

    }

    public void editPersonalData() {

        logger.info("Profile_Backing.editPersonalData");

        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");

        Date dateLicenseDate = new Date();
        //try {
        //    Calendar cal = Calendar.getInstance();
       //     cal.set(Calendar.YEAR, Integer.parseInt(licenseDate.getValue().toString()));
        //    dateLicenseDate.setTime(cal.getTimeInMillis());
        //} catch (NumberFormatException e) {
            dateLicenseDate = null;
        //}
        customerControllerBean.setPersonalData(managedUserBean.getId(), (Date) dateOfBirth.getValue(), email.getValue().toString(), mobilePhoneNumber.getValue().toString(), "", streetAddress.getValue().toString(), (!zipCode.getValue().toString().equals("")) ? Integer.parseInt(zipCode.getValue().toString()) : 0, city.getValue().toString(), (isSmoker.getValue() != null) ? isSmoker.getValue().toString().charAt(0) : "-".charAt(0), null);

        Short carBuildYearValue;
        //try {
        //    carBuildYearValue = Short.parseShort(carBuildYear.getValue().toString());
        //} catch (NumberFormatException e) {
            carBuildYearValue = null;
        //}
        carDetailsControllerBean.updateCarDetails(customer, carBrand.getValue().toString(), carBuildYearValue, carColour.getValue().toString(), carPlateNo.getValue().toString());

        CustomerEntity updatedUser = customerControllerBean.getCustomer(managedUserBean.getId());

        // copy updated user data to managedUserBean (current session)
        UserUtil.copyUserProperties(updatedUser, managedUserBean);

        setRenderPersonalDataSuccessMessage(true);

    }

    public void editPreferences() {

        logger.info("Profile_Backing.editPreferences");

        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");

        // For now, driver prefs = rider prefs (no distinction)
        customerControllerBean.setDriverPrefs(managedUserBean.getId(), /*(!driverPrefAge.getValue().toString().equals("")) ? Integer.parseInt(driverPrefAge.getValue().toString()) : */ 0, (prefGender.getValue() != null) ? prefGender.getValue().toString().charAt(0) : CustomerEntity.PREF_GENDER_DEFAULT, (prefIsSmoker.getValue() != null) ? prefIsSmoker.getValue().toString().charAt(0) : CustomerEntity.PREF_SMOKER_DEFAULT);
        customerControllerBean.setRiderPrefs(managedUserBean.getId(), /*(!riderPrefAge.getValue().toString().equals("")) ? Integer.parseInt(riderPrefAge.getValue().toString()) : */ 0, (prefGender.getValue() != null) ? prefGender.getValue().toString().charAt(0) : CustomerEntity.PREF_GENDER_DEFAULT, (prefIsSmoker.getValue() != null) ? prefIsSmoker.getValue().toString().charAt(0) : CustomerEntity.PREF_SMOKER_DEFAULT);

        CustomerEntity updatedUser = customerControllerBean.getCustomer(managedUserBean.getId());

        // copy updated user data to managedUserBean (current session)
        UserUtil.copyUserProperties(updatedUser, managedUserBean);

        setRenderDriverRiderPrefsSuccessMessage(true);

    }

    public void editPassword() {

        logger.info("Profile_Backing.editPassword");

        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");

        customerControllerBean.setPassword(managedUserBean.getId(), password.getValue().toString());

        setRenderPasswordSuccessMessage(true);

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

        // Allow existing email address if it is the user's current one!
        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");

        if (c != null && !c.getCustEmail().equals(managedUserBean.getEmail())) {
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
    public void validateYear(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        if (((String) value).equals("")) {
            return; // year should be optional, "" is okay.
        }
        Short yearValue;
        try {
            yearValue = Short.parseShort((String) value);
        } catch (NumberFormatException e) {
            yearValue = null;
        }

        Calendar cal = Calendar.getInstance();
        if (yearValue == null || yearValue < 1900 || yearValue > cal.get(Calendar.YEAR)) {
            FacesMessage message = new FacesMessage("Bitte ein gültiges Jahr im Format \"JJJJ\" angeben.");
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
    public void validatePasswordOld(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        String pw = (String) value;

        UserBean managedUserBean = (UserBean) JSFUtil.getManagedObject("UserBean");

        if (!customerControllerBean.isRegistered(managedUserBean.getUsername(), pw)) {
            FacesMessage message = new FacesMessage("Ungültiges Passwort.");
            throw new ValidatorException(message);
        }
    }

    /*
     * Getters / setters
     */
    public HtmlInputText getCity() {
        return city;
    }

    public void setCity(HtmlInputText city) {
        this.city = city;
    }

    public HtmlInputDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(HtmlInputDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /*public HtmlInputText getDriverPrefAge() {
    return driverPrefAge;
    }

    public void setDriverPrefAge(HtmlInputText driverPrefAge) {
    this.driverPrefAge = driverPrefAge;
    }*/
    public HtmlSelectOneRadio getPrefGender() {
        return prefGender;
    }

    public void setPrefGender(HtmlSelectOneRadio prefGender) {
        this.prefGender = prefGender;
    }

    public HtmlSelectOneRadio getPrefIsSmoker() {
        return prefIsSmoker;
    }

    public void setPrefIsSmoker(HtmlSelectOneRadio prefIsSmoker) {
        this.prefIsSmoker = prefIsSmoker;
    }

    public HtmlInputText getEmail() {
        return email;
    }

    public void setEmail(HtmlInputText email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public HtmlInputText getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(HtmlInputText fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HtmlSelectOneRadio getIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(HtmlSelectOneRadio isSmoker) {
        this.isSmoker = isSmoker;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HtmlInputText getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(HtmlInputText licenseDate) {
        this.licenseDate = licenseDate;
    }

    public HtmlInputText getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(HtmlInputText mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
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

    public HtmlInputFileUpload getPicture() {
        return picture;
    }

    public void setPicture(HtmlInputFileUpload picture) {
        this.picture = picture;
    }

    public HtmlInputText getPresenceMessage() {
        return presenceMessage;
    }

    public void setPresenceMessage(HtmlInputText presenceMessage) {
        this.presenceMessage = presenceMessage;
    }

    /*public HtmlInputText getRiderPrefAge() {
    return riderPrefAge;
    }

    public void setRiderPrefAge(HtmlInputText riderPrefAge) {
    this.riderPrefAge = riderPrefAge;
    }*/
    public HtmlInputText getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(HtmlInputText streetAddress) {
        this.streetAddress = streetAddress;
    }

    public HtmlInputText getZipCode() {
        return zipCode;
    }

    public void setZipCode(HtmlInputText zipCode) {
        this.zipCode = zipCode;
    }

    public HtmlInputSecret getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(HtmlInputSecret passwordOld) {
        this.passwordOld = passwordOld;
    }

    public boolean isRenderDriverRiderPrefsSuccessMessage() {
        return renderDriverRiderPrefsSuccessMessage;
    }

    public void setRenderDriverRiderPrefsSuccessMessage(boolean renderDriverRiderPrefsSuccessMessage) {
        this.renderDriverRiderPrefsSuccessMessage = renderDriverRiderPrefsSuccessMessage;
    }

    public boolean isRenderPersonalDataSuccessMessage() {
        return renderPersonalDataSuccessMessage;
    }

    public void setRenderPersonalDataSuccessMessage(boolean renderPersonalDataSuccessMessage) {
        this.renderPersonalDataSuccessMessage = renderPersonalDataSuccessMessage;
    }

    public String getPictureBasename() {
        return pictureBasename;
    }

    public void setPictureBasename(String pictureBasename) {
        this.pictureBasename = pictureBasename;
    }

    public boolean isRenderPasswordSuccessMessage() {
        return renderPasswordSuccessMessage;
    }

    public void setRenderPasswordSuccessMessage(boolean renderPasswordSuccessMessage) {
        this.renderPasswordSuccessMessage = renderPasswordSuccessMessage;
    }

    public boolean isRenderPictureSuccessMessage() {
        return renderPictureSuccessMessage;
    }

    public void setRenderPictureSuccessMessage(boolean renderPictureSuccessMessage) {
        this.renderPictureSuccessMessage = renderPictureSuccessMessage;
    }

    public boolean isRenderPictureErrorMessage() {
        return renderPictureErrorMessage;
    }

    public void setRenderPictureErrorMessage(boolean renderPictureErrorMessage) {
        this.renderPictureErrorMessage = renderPictureErrorMessage;
    }

    public HtmlInputText getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(HtmlInputText carBrand) {
        this.carBrand = carBrand;
    }

    public HtmlInputText getCarBuildYear() {
        return carBuildYear;
    }

    public void setCarBuildYear(HtmlInputText carBuildYear) {
        this.carBuildYear = carBuildYear;
    }

    public HtmlInputText getCarColour() {
        return carColour;
    }

    public void setCarColour(HtmlInputText carColour) {
        this.carColour = carColour;
    }

    public HtmlInputText getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(HtmlInputText carPlateNo) {
        this.carPlateNo = carPlateNo;
    }
}
