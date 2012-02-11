/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fhg.fokus.openride.routing.servlet;

import de.fhg.fokus.openride.routing.RouterWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mapsforge.core.GeoCoordinate;

/**
 *
 * @author fvi
 */
@WebServlet(name="ShortestPath", urlPatterns={"/ShortestPath"})
public class ShortestPath extends HttpServlet {

    private static final int MODE_COORDS = 0;
    private static final int MODE_TIME = 1;
    private static final int MODE_DISTANCE = 2;

    // sample query berlin
    //http://127.0.0.1:8080/OpenRideRouting/ShortestPath?coords=52.5075125,13.4502837;52.5256779,13.3061963

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // get parameter
            int mode = getMode(request.getParameter("mode"));
            GeoCoordinate[] coords = getRequestCoordinates(request.getParameter("coords"));

            switch(mode) {
                case MODE_COORDS : {
                    LinkedList<GeoCoordinate> route = RouterWrapper.getInstance().getShortestPath(coords);
                    if(route != null) {
                        for(GeoCoordinate c : route) {
                            out.print(c.getLatitude() + "," + c.getLongitude() + "\n");
                        }
                    }
                    break;
                }
                case MODE_TIME : {
                    LinkedList<GeoCoordinate> route = new LinkedList<GeoCoordinate>();
                    LinkedList<Integer> times = new LinkedList<Integer>();
                    RouterWrapper.getInstance().getShortestPathAndTravelTime(coords, route, times);

                    Iterator<GeoCoordinate> iterRoute = route.iterator();
                    Iterator<Integer> iterTime = times.iterator();
                    for(int i=0;i<route.size();i++) {
                        GeoCoordinate c = iterRoute.next();
                        int time = iterTime.next();
                        out.print(c.getLatitude() + "," + c.getLongitude() + "," + time + "\n");
                    }
                    break;
                }
                case MODE_DISTANCE : {
                    LinkedList<GeoCoordinate> route = RouterWrapper.getInstance().getShortestPath(coords);
                    if(route != null) {
                        double distance = 0;
                        GeoCoordinate[] arr = new GeoCoordinate[route.size()];
                        route.toArray(arr);
                        for(int i=1;i<arr.length;i++) {
                            distance += GeoCoordinate.sphericalDistance(
                                    arr[i-1].getLongitude(),
                                    arr[i-1].getLatitude(),
                                    arr[i].getLongitude(),
                                    arr[i].getLatitude()
                            );
                        }
                        out.print((int)distance);
                    } else {
                        out.print(Integer.MAX_VALUE);
                    }
                }
            }
        } finally {
            out.close();
        }
    }

    private static int getMode(String s) {
        if(s != null) {
            if(s.equals("coords")) {
                return MODE_COORDS;
            } else if(s.equals("time")) {
                return MODE_TIME;
            } else if(s.equals("distance")){
                return MODE_DISTANCE;
            }
        }
        //default mode
        return MODE_COORDS;
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

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
