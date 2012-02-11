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

package de.fhg.fokus.openride.services.favoritepoint;

import com.thoughtworks.xstream.XStream;
import de.fhg.fokus.openride.customerprofile.CustomerControllerLocal;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.customerprofile.FavoritePointControllerLocal;
import de.fhg.fokus.openride.customerprofile.FavoritePointEntity;
import de.fhg.fokus.openride.helperclasses.Utils;
import de.fhg.fokus.openride.services.favoritepoint.helperclasses.FavoritePointRequest;
import de.fhg.fokus.openride.services.favoritepoint.helperclasses.FavoritePointResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author tku
 */
@Path("/users/{username}/favoritepoints/")
public class FavoritePointService {

    FavoritePointControllerLocal favoritePointControllerBean = lookupFavoritePointControllerBeanLocal();
    CustomerControllerLocal customerControllerBean = lookupCustomerControllerBeanLocal();

    @GET
    @Produces("text/json")
    public Response getFavoritePoints(@Context HttpServletRequest con, @PathParam("username") String username) {

        System.out.println("getFavoritePoints start");

        // check if remote user == {username} in path param
//        if (!username.equals(con.getRemoteUser())) {
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

        // build a List of Objects that shall be available in the JSON context.
        ArrayList list = new ArrayList();
        list.add(new FavoritePointResponse());

        XStream x = Utils.getJasonXStreamer(list);


        ArrayList favoritePoints = new ArrayList();

        List<FavoritePointEntity> favPoints = (List<FavoritePointEntity>) favoritePointControllerBean.getFavoritePointsByCustomer(c);

        FavoritePointResponse response;
        for (FavoritePointEntity point : favPoints) {
            response = new FavoritePointResponse();
            response.setFavptId(point.getFavptId());
            response.setFavptDisplayName(StringEscapeUtils.escapeHtml(point.getFavptDisplayname()));
            response.setFavptAddress(StringEscapeUtils.escapeHtml(point.getFavptAddress()));
            response.setFavptGeoCoords(StringEscapeUtils.escapeHtml(point.getFavptPoint()));
            favoritePoints.add(response);
        }

        System.out.println(favoritePoints.size() + " favoritepoints to return");

        return Response.ok(x.toXML(favoritePoints)).build();

    }

    @POST
    @Produces("text/json")
    public Response postFavoritePoint(@Context HttpServletRequest con, @PathParam("username") String username, String json) {

        System.out.println("postFavoritePoint start");

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
            list.add(new FavoritePointRequest());

            XStream x = Utils.getJasonXStreamer(list);


            FavoritePointRequest r = (FavoritePointRequest) x.fromXML(json);

            Integer favptId = favoritePointControllerBean.addFavoritePoint(r.getFavptAddress(), r.getFavptGeoCoords(), r.getFavptDisplayName(), c);

            if (favptId != -1) {
                // Favpt has been added successfully
                return Response.created(UriBuilder.fromPath("{displayName}").build(r.getFavptDisplayName())).build();
            } else {
                // Could not add favpt - displayName not unique?
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("{displayName}/")
    @Produces("text/json")
    public Response removeFavoritePoint(@Context HttpServletRequest con, @PathParam("username") String username, @PathParam("displayName") String displayName) {

        System.out.println("removeFavoritePoint start");

        // check if remote user == {username} in path param
        if (!username.equals(con.getRemoteUser())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        CustomerEntity c = customerControllerBean.getCustomerByNickname(username);

        FavoritePointEntity point;
        try {            
            point = favoritePointControllerBean.getFavoritePointByDisplayName(URLDecoder.decode(displayName, "UTF-8"), c);

            if (point != null) {
                favoritePointControllerBean.removeFavoritePoint(point.getFavptId());
            } else {
                // A favpt of the given displayName does not exist for this customer
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FavoritePointService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }

        return Response.ok().build();

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

    private FavoritePointControllerLocal lookupFavoritePointControllerBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (FavoritePointControllerLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/FavoritePointControllerBean!de.fhg.fokus.openride.customerprofile.FavoritePointControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
