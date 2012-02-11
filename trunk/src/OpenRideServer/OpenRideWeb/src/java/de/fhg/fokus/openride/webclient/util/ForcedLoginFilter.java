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

package de.fhg.fokus.openride.webclient.util;

import de.fhg.fokus.openride.webclient.UserBean;
import java.io.IOException;
import java.util.Iterator;
import java.util.Arrays;

import javax.faces.context.FacesContext;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.Filter;

/**
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 * @author edburns
 */
public class ForcedLoginFilter implements Filter {

    private FilterConfig filterConfig = null;
    private String[] noForwardViewIds = null;
    private String[] authUsersProfileForwardViewIds = null;

    public ForcedLoginFilter() {
    }

    public static boolean checkLoginState(Object request)
            throws IOException, ServletException {
        boolean isLoggedIn = false;
        HttpSession session = ((HttpServletRequest) request).getSession(false);

        UserBean managedUserBean = null;
        // If there is a UserBean in the session, and it has the isLoggedIn property set to true.
        if (null != session &&
                (null != (managedUserBean = (UserBean) session.getAttribute("UserBean")))) {
            if (managedUserBean.isIsLoggedIn()) {
                isLoggedIn = true;
            }
        }
        return isLoggedIn;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        Throwable problem = null;
        boolean isLoggedIn = false;

        isLoggedIn = checkLoginState(request);

        // If this invocation of the filter is forwardable
        // and we're not already logged in.
        if (isLoginForwardable((HttpServletRequest) request) && !isLoggedIn) {
            String loginURI = getFilterConfig().getInitParameter("de.fhg.fokus.openride.webclient.LoginURI");
            loginURI = (null != loginURI) ? loginURI : DEFAULT_LOGIN_URI;
            //RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginURI);
            // Force the login (redirect to loginURI)
            System.out.println(((HttpServletRequest) request).getRequestURI() + ": forced redirect to login page");
            FacesContext.getCurrentInstance().getExternalContext().redirect(loginURI);
            //requestDispatcher.forward(request, response);
            return;
        }
        else if (isProfileForwardable((HttpServletRequest) request) && isLoggedIn) {
            String profileURI = getFilterConfig().getInitParameter("de.fhg.fokus.openride.webclient.ProfileURI");
            if (profileURI == null) return;
            //RequestDispatcher requestDispatcher = request.getRequestDispatcher(profileURI);
            // Force the login (redirect to profileURI)
            System.out.println(((HttpServletRequest) request).getRequestURI() + ": forced redirect to profile page");
            FacesContext.getCurrentInstance().getExternalContext().redirect(profileURI);
            //requestDispatcher.forward(request, response);
            return;

        } else {
            try {
                chain.doFilter(request, response);
            } catch (Throwable t) {
                
            }
        }
    }

    /**
     * <p>Returns true if this filter is not processing the login postback
     * and the filter is not already on the callstack for this request.</p>
     */
    private boolean isLoginForwardable(HttpServletRequest request) {
        boolean onCallstack = true,
                isNoForwardViewId = false;
        String noForwardViewId = null,
                requestURI = null;
        Iterator noForwardViewIdIter = null;
        if (null == request.getAttribute("de.fhg.fokus.openride.webclient.ForcedLoginFilter.OnStack")) {
            request.setAttribute("de.fhg.fokus.openride.webclient.ForcedLoginFilter.OnStack",
                    Boolean.TRUE);
            onCallstack = false;
        }

        requestURI = request.getRequestURI();
        noForwardViewIdIter = getNoForwardViewIds(request);
        // Iterate over the list of noForwardViewIds and bail out if
        // the current requestURI contains a match
        while (!isNoForwardViewId && noForwardViewIdIter.hasNext()) {
            noForwardViewId = (String) noForwardViewIdIter.next();
            isNoForwardViewId = (requestURI.endsWith(noForwardViewId));
        }

        if (isNoForwardViewId) {
            return false;
        }
        if (onCallstack) {
            return false;
        }
        return true;
    }

    private boolean isProfileForwardable(HttpServletRequest request) {
        boolean onCallstack = true,
                isAuthUsersProfileForwardViewId = false;
        String authUsersProfileForwardViewId = null,
                requestURI = null;
        Iterator authUsersProfileForwardViewIdIter = null;
        if (null == request.getAttribute("de.fhg.fokus.openride.webclient.ForcedLoginFilter.OnStack")) {
            request.setAttribute("de.fhg.fokus.openride.webclient.ForcedLoginFilter.OnStack",
                    Boolean.TRUE);
            onCallstack = false;
        }

        requestURI = request.getRequestURI();
        authUsersProfileForwardViewIdIter = getAuthUsersProfileForwardViewIds(request);
        // Iterate over the list of authUsersProfileForwardViewIds and bail out if
        // the current requestURI contains a match
        while (!isAuthUsersProfileForwardViewId && authUsersProfileForwardViewIdIter.hasNext()) {
            authUsersProfileForwardViewId = (String) authUsersProfileForwardViewIdIter.next();
            isAuthUsersProfileForwardViewId = (requestURI.endsWith(authUsersProfileForwardViewId));
        }        

        if (isAuthUsersProfileForwardViewId) {
            return true;
        }
        if (onCallstack) {
            return false;
        }
        return false;
    }

    /**
     * <p>Returns an Iterator over the contentsof the NoForwardViewIds init
     * parameter, or the empty iterator if that parameter is not specified.</p>
     */
    Iterator getNoForwardViewIds(HttpServletRequest request) {
        Iterator result = null;

        if (null == noForwardViewIds) {
            synchronized (this) {
                noForwardViewIds = new String[0];
                String viewIdList =
                        getFilterConfig().getInitParameter("de.fhg.fokus.openride.webclient.NoForwardViewIds");
                if (null != viewIdList) {
                    try {
                        noForwardViewIds = viewIdList.split(" ");
                    } catch (Exception e) {
                    }
                }
            }
        }
        //final String[] viewIds = noForwardViewIds;
        result = Arrays.asList(noForwardViewIds).iterator();

        return result;
    }

     /**
     * <p>Returns an Iterator over the contentsof the AuthUsersProfileForwardViewIds init
     * parameter, or the empty iterator if that parameter is not specified.</p>
     */
    Iterator getAuthUsersProfileForwardViewIds(HttpServletRequest request) {
        Iterator result = null;

        if (null == authUsersProfileForwardViewIds) {
            synchronized (this) {
                authUsersProfileForwardViewIds = new String[0];
                String viewIdList =
                        getFilterConfig().getInitParameter("de.fhg.fokus.openride.webclient.AuthUsersProfileForwardViewIds");
                if (null != viewIdList) {
                    try {
                        authUsersProfileForwardViewIds = viewIdList.split(" ");
                    } catch (Exception e) {
                    }
                }
            }
        }
        //final String[] viewIds = authUsersProfileForwardViewIds;
        result = Arrays.asList(authUsersProfileForwardViewIds).iterator();

        return result;
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {

        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     *
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     */
    public void init(FilterConfig filterConfig) {

        this.filterConfig = filterConfig;
    }
    private static final String DEFAULT_LOGIN_URI = "/faces/index.jsp";
}
