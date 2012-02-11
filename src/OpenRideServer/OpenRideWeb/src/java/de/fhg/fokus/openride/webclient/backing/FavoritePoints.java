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
import de.fhg.fokus.openride.customerprofile.FavoritePointControllerLocal;
import de.fhg.fokus.openride.customerprofile.FavoritePointEntity;
import de.fhg.fokus.openride.webclient.UserBean;
import de.fhg.fokus.openride.webclient.util.JSFUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Backing class for view, tab "favorites", subtab "places" ("Orte").
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class FavoritePoints {

    HtmlInputHidden address;
    HtmlInputText displayName;
    HtmlInputHidden geoCoords;
    boolean renderSuccessMessage = false;
    private List<FavoritePointEntity> favPoints;
    @EJB
    private FavoritePointControllerLocal favoritePointControllerBean;
    @EJB
    private CustomerControllerLocal customerControllerBean;
    private CustomerEntity customer;

    @PostConstruct
    public void initialize() {
        setFavPoints();
    }

    /**
     * Retrieves the user's current set of favorite points from the database.
     * 
     * (List "favPoints" accessed from profile view.)
     */
    private void setFavPoints() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        customer = customerControllerBean.getCustomerByNickname(ec.getRemoteUser());

        favPoints = (List<FavoritePointEntity>) favoritePointControllerBean.getFavoritePointsByCustomer(customer);
    }

    /**
     * Extracts form values and adds a new favorite point to the database.
     */
    public void add() {

        int favptId = favoritePointControllerBean.addFavoritePoint(address.getValue().toString(), geoCoords.getValue().toString(), displayName.getValue().toString(), customer);

        // Clear form fields to allow new favpts to be added
        address.resetValue();
        geoCoords.resetValue();
        displayName.resetValue();

        // Update list of currently saved points
        setFavPoints();

        // Done
        setRenderSuccessMessage(true);

    }

    /**
     * Extracts parameter from RequestParameterMap and deletes the selected favorite point.
     */
    public void remove() {

        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String favptId = (String) params.get("favptId").toString();

        favoritePointControllerBean.removeFavoritePoint(Integer.parseInt(favptId));

        // Update list of currently saved points
        setFavPoints();

        // Done
        setRenderSuccessMessage(true);

    }

    /**
     *
     * @param context
     * @param toValidate
     * @param value
     * @throws ValidatorException
     */
    public void validateDisplayName(FacesContext context, UIComponent toValidate,
            Object value) throws ValidatorException {

        if (favoritePointControllerBean.getFavoritePointByDisplayName((String) value, customer) != null) {
            FacesMessage message = new FacesMessage("Ein Ort mit diesem Namen existiert bereits.");
            throw new ValidatorException(message);
        }

    }

    public HtmlInputHidden getAddress() {
        return address;
    }

    public void setAddress(HtmlInputHidden address) {
        this.address = address;
    }

    public HtmlInputHidden getGeoCoords() {
        return geoCoords;
    }

    public void setGeoCoords(HtmlInputHidden geoCoords) {
        this.geoCoords = geoCoords;
    }

    public boolean isRenderSuccessMessage() {
        return renderSuccessMessage;
    }

    public void setRenderSuccessMessage(boolean renderSuccessMessage) {
        this.renderSuccessMessage = renderSuccessMessage;
    }

    public HtmlInputText getDisplayName() {
        return displayName;
    }

    public void setDisplayName(HtmlInputText shortName) {
        this.displayName = shortName;
    }

    public List<FavoritePointEntity> getFavPoints() {
        return favPoints;
    }

    public void setFavPoints(List<FavoritePointEntity> favPoints) {
        this.favPoints = favPoints;
    }
}
