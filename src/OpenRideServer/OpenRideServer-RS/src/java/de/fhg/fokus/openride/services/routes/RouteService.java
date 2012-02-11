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

package de.fhg.fokus.openride.services.routes;

import de.fhg.fokus.openride.routing.Coordinate;
import de.fhg.fokus.openride.routing.Route;
import de.fhg.fokus.openride.routing.RouterBeanLocal;
import de.fhg.fokus.openride.routing.RouterWrapper;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.mapsforge.core.GeoCoordinate;

/**
 *
 * @author pab
 */
@Path("/users/{nickname}/routes/")
public class RouteService {
    RouterBeanLocal routerBean = lookupRouterBeanLocal();

    @GET
    @Path("/new,{startLat},{startLon},{endLat},{endLon}")
    @Produces("text/xml")
    public Response getRoute(
            @PathParam("startLat")String startLat,
            @PathParam("startLon")String startLon,
            @PathParam("endLat")String endLat,
            @PathParam("endLon")String endLon
            ){
        System.out.println("getRoute start");
        Coordinate source = null;
        Coordinate target = null;

        System.out.println(startLat+" "+startLon+" "+endLat+" "+endLon);

        source = new Coordinate(Double.parseDouble(startLat), Double.parseDouble(startLon));
        target = new Coordinate(Double.parseDouble(endLat), Double.parseDouble(endLon));

        Route route = null;
        if (source != null && target != null) {
            route = routerBean.findRoute(
                    source,
                    target,
                    new Timestamp(0),
                    de.fhg.fokus.openride.matching.Constants.ROUTE_FASTEST_PATH_DEFAULT,
                    de.fhg.fokus.openride.matching.Constants.MATCHING_MAX_ROUTE_POINT_DISTANCE,
                    true);
        }

        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();


        /* head */
        buffer.append("<?xml version=\"1.0\"?>");
        buffer.append("<document>");
        /* route */
        if (route != null) {
            route.appendAsXml(buffer, false);
        } else {
            buffer.append("<route><hasroute>false</hasroute></route>");
        }
        /* tail */
        buffer.append("<riders>");
        buffer.append("</riders>");
        buffer.append("</document>");
        System.out.println("buffer: "+buffer.toString());
        /* flush & close resources */
        return Response.ok(buffer.toString()).build();
    }

    @GET
    @Path("validate/{points}")
    public Response validate(
            @PathParam("points")String points,
            @PathParam("mode")int mode){
        System.out.println("test");
        GeoCoordinate[] coords = getRequestCoordinates(points);
        GeoCoordinate[] mapCoords = RouterWrapper.getInstance().getMapCoordinates(coords);
        String stringifiedCoords = stringifyCoords(mapCoords);
        return Response.ok(stringifiedCoords).build();
    }



    private static GeoCoordinate[] getRequestCoordinates(String s) {
        if( s == null) {
            return null;
        }
        String[] tmp = s.split(";");
        GeoCoordinate[] result = new GeoCoordinate[tmp.length];
        for(int i=0;i<tmp.length;i++) {
            String[] coord = tmp[i].split(",");
            if(coord.length != 2) {
                return null;
            }
            try {
                GeoCoordinate c = new GeoCoordinate(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
                result[i] = c;
            } catch(NumberFormatException e) {
                return null;
            }
        }
        return result;
    }

    private RouterBeanLocal lookupRouterBeanLocal() {
        try {
            Context c = new InitialContext();
            return (RouterBeanLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/RouterBean!de.fhg.fokus.openride.routing.RouterBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private String stringifyCoords(GeoCoordinate[] mapCoords) {
        String returnString = "";
        if(mapCoords == null){
            return returnString;
        }
        for(GeoCoordinate coord: mapCoords){
            returnString+=coord.getLatitude()+","+coord.getLongitude()+";";
        }
        return returnString;
    }
}
