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

package de.fhg.fokus.openride.services.profile;

import com.thoughtworks.xstream.XStream;
import de.fhg.fokus.openride.customerprofile.CarDetailsControllerLocal;
import de.fhg.fokus.openride.customerprofile.CarDetailsEntity;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.helperclasses.Utils;
import de.fhg.fokus.openride.services.profile.helperclasses.PasswordRequest;
import de.fhg.fokus.openride.services.profile.helperclasses.PreferencesRequest;
import de.fhg.fokus.openride.services.profile.helperclasses.PreferencesResponse;
import de.fhg.fokus.openride.services.profile.helperclasses.ProfileRequest;
import de.fhg.fokus.openride.services.profile.helperclasses.ProfileResponse;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author pab, tku
 */
@Path("/users/{username}/profile/")
public class ProfileService {

    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();
    CarDetailsControllerLocal carDetailsControllerBean = lookupCarDetailsControllerBeanLocal();

    @GET
    @Produces("text/json")
    public Response getProfile(@Context HttpServletRequest con, @PathParam("username") String username) {

        System.out.println("getProfile start");

        // check if remote user == {username} in path param
        if (!username.equals(con.getRemoteUser())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);
        CarDetailsEntity cd = carDetailsControllerBean.getCarDetails(c);

        // build a List of Objects that shall be available in the JSON context.
        ArrayList list = new ArrayList();
        list.add(new ProfileResponse());

        XStream x = Utils.getJasonXStreamer(list);


        Long dateOfBirth = null;
        if (c.getCustDateofbirth() != null) {
            dateOfBirth = c.getCustDateofbirth().getTime();
        }
        Short licenseDate = null;
        if (c.getCustLicensedate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(c.getCustLicensedate());
            licenseDate = (short) cal.get(Calendar.YEAR);
        }

        ProfileResponse profile = new ProfileResponse(
                StringEscapeUtils.escapeHtml(c.getCustFirstname()),
                StringEscapeUtils.escapeHtml(c.getCustLastname()),
                c.getCustGender(),
                dateOfBirth,
                StringEscapeUtils.escapeHtml(c.getCustEmail()),
                StringEscapeUtils.escapeHtml(c.getCustMobilephoneno()),
                StringEscapeUtils.escapeHtml(c.getCustFixedphoneno()),
                StringEscapeUtils.escapeHtml(c.getCustAddrStreet()),
                c.getCustAddrZipcode(),
                StringEscapeUtils.escapeHtml(c.getCustAddrCity()),
                getCustIssmokerChar(c.getCustIssmoker()),
                licenseDate,
                (cd != null) ? cd.getCardetColour() : null,
                (cd != null) ? cd.getCardetBrand() : null,
                (cd != null) ? cd.getCardetBuildyear() : null,
                (cd != null) ? cd.getCardetPlateno() : null);

        return Response.ok(x.toXML(profile)).build();

    }

    private char getCustIssmokerChar(Boolean custIssmoker) {
        if (custIssmoker == null) {
            return "-".charAt(0);
        }
        if (custIssmoker == true) {
            return "y".charAt(0);
        } else if (custIssmoker == false) {
            return "n".charAt(0);
        }
        return "-".charAt(0);
    }

    @PUT
    @Produces("text/json")
    public Response putProfile(@Context HttpServletRequest con, @PathParam("username") String username, String json) {

        System.out.println("putProfile start");

        if (json != null) {
            System.out.println("json: " + json);
            // to use this method client must send json content!

            // check if remote user == {username} in path param
            if (!username.equals(con.getRemoteUser())) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

            // build a List of Objects that shall be available in the JSON context.
            ArrayList list = new ArrayList();
            list.add(new ProfileRequest());

            XStream x = Utils.getJasonXStreamer(list);


            ProfileRequest r = (ProfileRequest) x.fromXML(json);

            //TODO: data validation!
            //resp.setStatus(resp.SC_BAD_REQUEST);

            Date dateOfBirth;
            if (r.getDateOfBirth() != null) {
                dateOfBirth = new Date(r.getDateOfBirth());
            } else {
                dateOfBirth = null;
            }

            Date licenseDate;            
            if (r.getLicenseDate() != null) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(r.getLicenseDate(), 0, 1);
                licenseDate = cal.getTime();
            } else {
                licenseDate = null;
            }            

            // Validate email address:
            CustomerEntity other_c = customerControllerBean.getCustomerByEmail(r.getEmail());

            // Allow existing email address if it is the user's current one!
            if (other_c != null && !other_c.getCustEmail().equals(c.getCustEmail())) {
                // "Ein Benutzer mit dieser E-Mail-Adresse ist bereits registriert."
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            // Pilotierung: nur ZU-Adressen zulässig
            /*if (!r.getEmail().endsWith("zeppelin-university.de") && !r.getEmail().endsWith("zeppelin-university.net") && !r.getEmail().endsWith("fokus.fraunhofer.de")) {
                // "E-Mail-Adresse muss auf \"zeppelin-university.de\" enden."
                return Response.status(Response.Status.BAD_REQUEST).build();
            }*/

            customerControllerBean.setPersonalData(c.getCustId(), dateOfBirth, r.getEmail(), r.getMobilePhoneNumber(), r.getFixedPhoneNumber(), r.getStreetAddress(), r.getZipCode(), r.getCity(), r.getIsSmoker(), licenseDate);
            carDetailsControllerBean.updateCarDetails(c, r.getCarBrand(), r.getCarBuildYear(), r.getCarColour(), r.getCarPlateNo());

            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET
    @Path("preferences/")
    @Produces("text/json")
    public Response getPreferences(@Context HttpServletRequest con, @PathParam("username") String username) {

        System.out.println("getPreferences start");

        // check if remote user == {username} in path param
        if (!username.equals(con.getRemoteUser())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

        // build a List of Objects that shall be available in the JSON context.
        ArrayList list = new ArrayList();
        list.add(new PreferencesResponse());

        XStream x = Utils.getJasonXStreamer(list);

        Character gender = c.getCustDriverprefGender();
        if (gender == null) {
            gender = "-".charAt(0);
        }
        Character issmoker = c.getCustDriverprefIssmoker();
        if (issmoker == null) {
            issmoker = "-".charAt(0);
        }

        PreferencesResponse profile = new PreferencesResponse(issmoker, gender);


        return Response.ok(x.toXML(profile)).build();

    }

    @PUT
    @Path("preferences/")
    @Produces("text/json")
    public Response putPreferences(@Context HttpServletRequest con, @PathParam("username") String username, String json) {

        System.out.println("putPreferences start");

        if (json != null) {
            System.out.println("json: " + json);
            // to use this method client must send json content!

            // check if remote user == {username} in path param
            if (!username.equals(con.getRemoteUser())) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

            // build a List of Objects that shall be available in the JSON context.
            ArrayList list = new ArrayList();
            list.add(new PreferencesRequest());

            XStream x = Utils.getJasonXStreamer(list);


            PreferencesRequest r = (PreferencesRequest) x.fromXML(json);

            //TODO: data validation!
            //resp.setStatus(resp.SC_BAD_REQUEST);

            // For now, driver prefs = rider prefs (no distinction)
            customerControllerBean.setDriverPrefs(c.getCustId(), 0, r.getPrefGender(), r.getPrefIsSmoker());
            customerControllerBean.setRiderPrefs(c.getCustId(), 0, r.getPrefGender(), r.getPrefIsSmoker());

            return Response.ok().build();

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("picture/")
    @Produces("text/json")
    public Response postPicture(@Context HttpServletRequest con, @PathParam("username") String username) {

        System.out.println("postPicture start");

        boolean success = false;

        //String profilePicturesPath = "C:\\OpenRide\\pictures\\profile";
        String profilePicturesPath = "/usr/lib/openride/pictures/profile";

        //TODO
        //String imagePath = getServletConfig().getInitParameter("imagePath");

        // FIXME: The following try/catch may be removed for production deployments:
        try {
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

        // check if remote user == {username} in path param
        if (!username.equals(con.getRemoteUser())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (ServletFileUpload.isMultipartContent(con)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(con);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            if (items != null) {
                Iterator<FileItem> iter = items.iterator();

                CustomerEntity c = customerControllerBean.getCustomerByNickname(username);
                String uploadedFileName = c.getCustNickname() + "_" + c.getCustId();

                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (!item.isFormField() && item.getSize() > 0) {

                        try {
                            BufferedImage uploadedPicture = ImageIO.read(item.getInputStream());

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

                            //System.out.println("new dimensions "+newWidth+"x"+newHeight);

                            Image resizedPicture = uploadedPicture.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                            xPos = Math.round((picSize - newWidth) / 2);
                            yPos = Math.round((picSize - newHeight) / 2);
                            BufferedImage bim = new BufferedImage(picSize, picSize, BufferedImage.TYPE_INT_RGB);
                            bim.createGraphics().setColor(Color.white);
                            bim.createGraphics().fillRect(0, 0, picSize, picSize);
                            bim.createGraphics().drawImage(resizedPicture, xPos, yPos, null);

                            File outputPicture = new File(profilePicturesPath, uploadedFileName + ".jpg");

                            ImageIO.write(bim, "jpg", outputPicture);


                            // Resize again for "thumb" size
                            if (uploadedPicture.getWidth() > uploadedPicture.getHeight()) {
                                newWidth = picThumbSize;
                                newHeight = Math.round(newWidth * ratio);
                            } else {
                                newHeight = picThumbSize;
                                newWidth = Math.round(newHeight / ratio);
                            }

                            //System.out.println("new dimensions "+newWidth+"x"+newHeight);

                            resizedPicture = uploadedPicture.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                            xPos = Math.round((picThumbSize - newWidth) / 2);
                            yPos = Math.round((picThumbSize - newHeight) / 2);
                            bim = new BufferedImage(picThumbSize, picThumbSize, BufferedImage.TYPE_INT_RGB);
                            bim.createGraphics().setColor(Color.white);
                            bim.createGraphics().fillRect(0, 0, picThumbSize, picThumbSize);
                            bim.createGraphics().drawImage(resizedPicture, xPos, yPos, null);

                            outputPicture = new File(profilePicturesPath, uploadedFileName + "_thumb.jpg");

                            ImageIO.write(bim, "jpg", outputPicture);

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("File upload / resize unsuccessful.");
                        }
                        success = true;
                    }
                }
            }
        }

        if (success) {

            // TODO: Perhaps introduce a redirection target as a parameter to the putProfile method and redirect to that URL (code 301/302) instead of just doing nothing.
            return null;

            /*
            try {
            String referer = con.getHeader("HTTP_REFERER");
            System.out.println("putPicture: Referer: " + referer);
            if (referer != null)
            return Response.status(Response.Status.SEE_OTHER).contentLocation(new URI(referer)).build();
            else
            return Response.ok().build();
            } catch (URISyntaxException ex) {
            Logger.getLogger(ProfileService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
            }
             */
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @PUT
    @Path("password/")
    @Produces("text/json")
    public Response putPassword(@Context HttpServletRequest con, @PathParam("username") String username, String json) {

        System.out.println("putPassword start");

        if (json != null) {
            System.out.println("json: " + json);
            // to use this method client must send json content!

            // check if remote user == {username} in path param
            if (!username.equals(con.getRemoteUser())) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

            // build a List of Objects that shall be available in the JSON context.
            ArrayList list = new ArrayList();
            list.add(new PasswordRequest());

            XStream x = Utils.getJasonXStreamer(list);


            PasswordRequest r = (PasswordRequest) x.fromXML(json);

            if (customerControllerBean.isRegistered(c.getCustNickname(), r.getPasswordOld())) {
                customerControllerBean.setPassword(c.getCustId(), r.getPassword());
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    private CustomerControllerLocal lookupCustomerControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CustomerControllerBean!de.fhg.fokus.openride.customerprofile.CustomerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CarDetailsControllerLocal lookupCarDetailsControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CarDetailsControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/CarDetailsControllerBean!de.fhg.fokus.openride.customerprofile.CarDetailsControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
