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
import de.fhg.fokus.openride.rating.helperclasses.OpenRatingInfo;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideControllerLocal;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;
import de.fhg.fokus.openride.webclient.UserBean;
import de.fhg.fokus.openride.webclient.util.JSFUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Backing class for view, tab "ratings".
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class Ratings {

    private List<RiderUndertakesRideEntity> unratedRidesAsRider;
    private List<RiderUndertakesRideEntity> unratedRidesAsDriver;

    private List<OpenRatingInfo> unratedRidesAsRiderInfo;
    private List<OpenRatingInfo> unratedRidesAsDriverInfo;
    
    private List<RiderUndertakesRideEntity> receivedRatingsAsRider;
    private List<RiderUndertakesRideEntity> receivedRatingsAsDriver;
    private HtmlInputText otherUsername;
    private List<RiderUndertakesRideEntity> othersRatingsAsRider;
    private List<RiderUndertakesRideEntity> othersRatingsAsDriver;
    // Page rendering logic
    private Boolean renderUnratedRidesAsDriver = false;
    private Boolean renderUnratedRidesAsRider = false;
    private Boolean renderReceivedRatingsAsDriver = false;
    private Boolean renderReceivedRatingsAsRider = false;
    private Boolean renderOthersRatingsAsDriver = false;
    private Boolean renderOthersRatingsAsRider = false;
    private Boolean renderSubmitSuccessMessage = false;
    private Boolean renderSubmitErrorMessage = false;
    private int ratingsTotal;
    private int ratingsRatioPercent;
    private int ratingsLatestPositive;
    private int ratingsLatestNeutral;
    private int ratingsLatestNegative;
    @EJB
    private RiderUndertakesRideControllerLocal riderundertakesrideControllerBean;
    @EJB
    private CustomerControllerLocal customerControllerBean;
    private CustomerEntity customer;

    @PostConstruct
    public void initialize() {        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        customer = customerControllerBean.getCustomerByNickname(ec.getRemoteUser());

        setRatingsTotal();
        setRatingsRatio();
        setLatestRatings();
        setReceivedRatings();

        setUnratedRides();
    }

    /**
     * Retrieves the user's current set of unrated rides from the database.
     *
     * (Lists "unratedRidesAsRiderInfo"/"unratedRidesAsDriverInfo" accessed from profile view.)
     */
    private void setUnratedRides() {

        unratedRidesAsRider = (List<RiderUndertakesRideEntity>) riderundertakesrideControllerBean.getRidesWithoutGivenRatingByRider(customer);
        unratedRidesAsDriver = (List<RiderUndertakesRideEntity>) riderundertakesrideControllerBean.getRidesWithoutReceivedRatingByDriver(customer);

        unratedRidesAsRiderInfo = new ArrayList<OpenRatingInfo>();
        unratedRidesAsDriverInfo = new ArrayList<OpenRatingInfo>();
        for (RiderUndertakesRideEntity ride : unratedRidesAsRider) {
            // TODO: Starttimeearliest needs to be replaced with Timestamprealized or -booked once those are set!!
            unratedRidesAsRiderInfo.add(new OpenRatingInfo(ride.getRiderrouteId(), ride.getRideId().getCustId().getCustId(), ride.getRideId().getCustId().getCustNickname(), ride.getStarttimeEarliest()));
            //unratedRidesAsRiderInfo.add(new OpenRatingInfo(ride.getRiderrouteId(), ride.getRideId().getCustId().getCustId(), ride.getRideId().getCustId().getCustNickname(), ride.getTimestamprealized()));
        }
        for (RiderUndertakesRideEntity ride : unratedRidesAsDriver) {            
            // TODO: Starttimeearliest needs to be replaced with Timestamprealized or -booked once those are set!!
            unratedRidesAsDriverInfo.add(new OpenRatingInfo(ride.getRiderrouteId(), ride.getCustId().getCustId(), ride.getCustId().getCustNickname(), ride.getStarttimeEarliest()));
            //unratedRidesAsDriverInfo.add(new OpenRatingInfo(ride.getRiderrouteId(), ride.getCustId().getCustId(), ride.getCustId().getCustNickname(), ride.getTimestamprealized()));
        }
        
        renderUnratedRidesAsRider = (unratedRidesAsRider.size() > 0);
        renderUnratedRidesAsDriver = (unratedRidesAsDriver.size() > 0);

    }

    private void setRatingsTotal() {
        ratingsTotal = riderundertakesrideControllerBean.getRatingsTotalByCustomer(customer);
    }

    private void setRatingsRatio() {
        ratingsRatioPercent = Math.round(riderundertakesrideControllerBean.getRatingsRatioByCustomer(customer) * 100);
    }

    private void setLatestRatings() {
        ratingsLatestPositive = riderundertakesrideControllerBean.getPositiveRatingsTotalByCustomer(customer);
        ratingsLatestNeutral = riderundertakesrideControllerBean.getNeutralRatingsTotalByCustomer(customer);
        ratingsLatestNegative = riderundertakesrideControllerBean.getNegativeRatingsTotalByCustomer(customer);
    }

    public void setReceivedRatings() {
        receivedRatingsAsRider = riderundertakesrideControllerBean.getRatedRidesByRider(customer);
        receivedRatingsAsDriver = riderundertakesrideControllerBean.getRatedRidesByDriver(customer);

        if (receivedRatingsAsRider.size() > 0) {
            renderReceivedRatingsAsRider = true;
        }
        if (receivedRatingsAsDriver.size() > 0) {
            renderReceivedRatingsAsDriver = true;
        }
    }

    public void submitRatings() {

        int rating;

        for (OpenRatingInfo ratingInfo : unratedRidesAsRiderInfo) {
            rating = ratingInfo.getRating();
            // For now, saving any rating in -1..1 range:
            if (rating >= -1 && rating <= 1) {
                // Do not accept negative ratings without a comment
                if (rating == -1 && ratingInfo.getRatingComment().equals("")) {
                    System.out.println("rideId " + ratingInfo.getRiderRoutId() + " rated " + ratingInfo.getRating() + " by rider without comment - rejected...");
                    renderSubmitErrorMessage = true;
                    continue;
                }
                // Save this rating                
                riderundertakesrideControllerBean.setGivenRating(ratingInfo.getRiderRoutId(), rating, ratingInfo.getRatingComment());
                System.out.println("rideId " + ratingInfo.getRiderRoutId() + " rated " + ratingInfo.getRating() + " by rider with comment '" + ratingInfo.getRatingComment() + "'");

                renderSubmitSuccessMessage = true;
            }
        }
        for (OpenRatingInfo ratingInfo : unratedRidesAsDriverInfo) {
            rating = ratingInfo.getRating();
            // For now, saving any rating in -1..1 range:
            if (rating >= -1 && rating <= 1) {
                // Do not accept negative ratings without a comment
                if (rating == -1 && ratingInfo.getRatingComment().equals("")) {
                    System.out.println("rideId " + ratingInfo.getRiderRoutId() + " rated " + ratingInfo.getRating() + " by driver without comment - rejected...");
                    renderSubmitErrorMessage = true;
                    continue;
                }
                // Save this rating                
                riderundertakesrideControllerBean.setReceivedRating(ratingInfo.getRiderRoutId(), rating, ratingInfo.getRatingComment());
                System.out.println("rideId " + ratingInfo.getRiderRoutId() + " rated " + ratingInfo.getRating() + " by driver with comment '" + ratingInfo.getRatingComment() + "'");

                renderSubmitSuccessMessage = true;
            }
        }

        // Update lists of unrated rides:
        setUnratedRides();

    }

    public void findOthersRatings() {

        // Get customer by username
        CustomerEntity otherUser = customerControllerBean.getCustomerByNickname(otherUsername.getValue().toString());
        // Get ratings for customer
        othersRatingsAsRider = riderundertakesrideControllerBean.getRatedRidesByRider(otherUser);
        othersRatingsAsDriver = riderundertakesrideControllerBean.getRatedRidesByDriver(otherUser);

        if (othersRatingsAsRider.size() > 0) {
            renderOthersRatingsAsRider = true;
        }
        if (othersRatingsAsDriver.size() > 0) {
            renderOthersRatingsAsDriver = true;
        }

    }

    /**
     *
     * @param context
     * @param toValidate
     * @param value
     * @throws ValidatorException
     */
    public void validateUsername(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        if (customerControllerBean.isNicknameAvailable((String) value)) {
            FacesMessage message = new FacesMessage("Ein Benutzer mit diesem Namen existiert nicht.");
            throw new ValidatorException(message);
        }

    }

    Integer validatedRating;
    public void validateRating(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        validatedRating = (Integer) value;        

    }
    public void validateRatingComment(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {
        
        // TODO: this gets called by JSF only if comment != "" - which means NOT when it would actually be needed...

        System.out.println("validatedRating "+validatedRating+", comment '"+value+"'");

        if (validatedRating == -1 && ((String) value).equals("")) {
            FacesMessage message = new FacesMessage("Angabe erforderlich bei negativer Bewertung.");
            throw new ValidatorException(message);
        }

    }

    public List<RiderUndertakesRideEntity> getUnratedRidesAsDriver() {
        return unratedRidesAsDriver;
    }

    public void setUnratedRidesAsDriver(List<RiderUndertakesRideEntity> unratedRidesAsDriver) {
        this.unratedRidesAsDriver = unratedRidesAsDriver;
    }

    public List<RiderUndertakesRideEntity> getUnratedRidesAsRider() {
        return unratedRidesAsRider;
    }

    public void setUnratedRidesAsRider(List<RiderUndertakesRideEntity> unratedRidesAsRider) {
        this.unratedRidesAsRider = unratedRidesAsRider;
    }

    public int getRatingsTotal() {
        return ratingsTotal;
    }

    public void setRatingsTotal(int ratingsTotal) {
        this.ratingsTotal = ratingsTotal;
    }

    public int getRatingsRatioPercent() {
        return ratingsRatioPercent;
    }

    public void setRatingsRatioPercent(int ratingsRatioPercent) {
        this.ratingsRatioPercent = ratingsRatioPercent;
    }

    public int getRatingsLatestNegative() {
        return ratingsLatestNegative;
    }

    public void setRatingsLatestNegative(int ratingsLatestNegative) {
        this.ratingsLatestNegative = ratingsLatestNegative;
    }

    public int getRatingsLatestNeutral() {
        return ratingsLatestNeutral;
    }

    public void setRatingsLatestNeutral(int ratingsLatestNeutral) {
        this.ratingsLatestNeutral = ratingsLatestNeutral;
    }

    public int getRatingsLatestPositive() {
        return ratingsLatestPositive;
    }

    public void setRatingsLatestPositive(int ratingsLatestPositive) {
        this.ratingsLatestPositive = ratingsLatestPositive;
    }

    public List<RiderUndertakesRideEntity> getReceivedRatingsAsDriver() {
        return receivedRatingsAsDriver;
    }

    public void setReceivedRatingsAsDriver(List<RiderUndertakesRideEntity> receivedRatingsAsDriver) {
        this.receivedRatingsAsDriver = receivedRatingsAsDriver;
    }

    public List<RiderUndertakesRideEntity> getReceivedRatingsAsRider() {
        return receivedRatingsAsRider;
    }

    public void setReceivedRatingsAsRider(List<RiderUndertakesRideEntity> receivedRatingsAsRider) {
        this.receivedRatingsAsRider = receivedRatingsAsRider;
    }

    public List<RiderUndertakesRideEntity> getOthersRatingsAsDriver() {
        return othersRatingsAsDriver;
    }

    public void setOthersRatingsAsDriver(List<RiderUndertakesRideEntity> othersRatingsAsDriver) {
        this.othersRatingsAsDriver = othersRatingsAsDriver;
    }

    public List<RiderUndertakesRideEntity> getOthersRatingsAsRider() {
        return othersRatingsAsRider;
    }

    public void setOthersRatingsAsRider(List<RiderUndertakesRideEntity> othersRatingsAsRider) {
        this.othersRatingsAsRider = othersRatingsAsRider;
    }

    public HtmlInputText getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(HtmlInputText otherUsername) {
        this.otherUsername = otherUsername;
    }

    public Boolean getRenderOthersRatingsAsDriver() {
        return renderOthersRatingsAsDriver;
    }

    public void setRenderOthersRatingsAsDriver(Boolean renderOthersRatingsAsDriver) {
        this.renderOthersRatingsAsDriver = renderOthersRatingsAsDriver;
    }

    public Boolean getRenderOthersRatingsAsRider() {
        return renderOthersRatingsAsRider;
    }

    public void setRenderOthersRatingsAsRider(Boolean renderOthersRatingsAsRider) {
        this.renderOthersRatingsAsRider = renderOthersRatingsAsRider;
    }

    public Boolean getRenderReceivedRatingsAsDriver() {
        return renderReceivedRatingsAsDriver;
    }

    public void setRenderReceivedRatingsAsDriver(Boolean renderReceivedRatingsAsDriver) {
        this.renderReceivedRatingsAsDriver = renderReceivedRatingsAsDriver;
    }

    public Boolean getRenderReceivedRatingsAsRider() {
        return renderReceivedRatingsAsRider;
    }

    public void setRenderReceivedRatingsAsRider(Boolean renderReceivedRatingsAsRider) {
        this.renderReceivedRatingsAsRider = renderReceivedRatingsAsRider;
    }

    public Boolean getRenderUnratedRidesAsDriver() {
        return renderUnratedRidesAsDriver;
    }

    public void setRenderUnratedRidesAsDriver(Boolean renderUnratedRidesAsDriver) {
        this.renderUnratedRidesAsDriver = renderUnratedRidesAsDriver;
    }

    public Boolean getRenderUnratedRidesAsRider() {
        return renderUnratedRidesAsRider;
    }

    public void setRenderUnratedRidesAsRider(Boolean renderUnratedRidesAsRider) {
        this.renderUnratedRidesAsRider = renderUnratedRidesAsRider;
    }

    public List<OpenRatingInfo> getUnratedRidesAsDriverInfo() {
        return unratedRidesAsDriverInfo;
    }

    public void setUnratedRidesAsDriverInfo(List<OpenRatingInfo> unratedRidesAsDriverInfo) {
        this.unratedRidesAsDriverInfo = unratedRidesAsDriverInfo;
    }

    public List<OpenRatingInfo> getUnratedRidesAsRiderInfo() {
        return unratedRidesAsRiderInfo;
    }

    public void setUnratedRidesAsRiderInfo(List<OpenRatingInfo> unratedRidesAsRiderInfo) {
        this.unratedRidesAsRiderInfo = unratedRidesAsRiderInfo;
    }

    public Boolean getRenderSubmitSuccessMessage() {
        return renderSubmitSuccessMessage;
    }

    public void setRenderSubmitSuccessMessage(Boolean renderSubmitSuccessMessage) {
        this.renderSubmitSuccessMessage = renderSubmitSuccessMessage;
    }

    public int getUnratedRidesAsRiderListSize() {
        return unratedRidesAsRider.size();
    }

    public int getUnratedRidesAsDriverListSize() {
        return unratedRidesAsDriver.size();
    }

    public Boolean getRenderSubmitErrorMessage() {
        return renderSubmitErrorMessage;
    }

    public void setRenderSubmitErrorMessage(Boolean renderSubmitErrorMessage) {
        this.renderSubmitErrorMessage = renderSubmitErrorMessage;
    }


}
