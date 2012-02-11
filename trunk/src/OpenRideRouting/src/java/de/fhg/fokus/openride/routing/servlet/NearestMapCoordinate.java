/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fhg.fokus.openride.routing.servlet;

import de.fhg.fokus.openride.routing.RouterWrapper;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name="NearestMapCoordinate", urlPatterns={"/NearestMapCoordinate"})
public class NearestMapCoordinate extends HttpServlet {

    private static final String SERVLET_INFO = "Gives a list of map coordinates nearest to the list of given coordinates.\n" +
            "Parameters : ?coords=lat1,lon1;lat2,lon2;...;latK,lonK";
   
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
            GeoCoordinate[] coords = getRequestCoordinates(request.getParameter("coords"));
            GeoCoordinate[] mapCoords = RouterWrapper.getInstance().getMapCoordinates(coords);
            if(mapCoords != null) {
                for(int i=0;i<mapCoords.length;i++) {
                    out.append(mapCoords[i].getLatitude() + "," + mapCoords[i].getLongitude() + "\n");
                }
            }
        } finally {
            out.close();
        }
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
        return SERVLET_INFO;
    }

}
