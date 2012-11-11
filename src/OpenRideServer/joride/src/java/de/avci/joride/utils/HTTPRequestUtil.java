/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.utils;

import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;


/** Access to the HTTPServletRequest and some functions therein
 * 
 *  Sometimes it's a good Idea to access the HTTPRequest 
 *  (for Example when it comes to retrieving sensitive User data based
 * on the AuthPrincipal)
 * 
 * 
 *
 * @author jochen
 */
public class HTTPRequestUtil {
    
    
    
    /** 
     * @return the HTTPServletRequest, or null if it can not be accessed 
     */
    public HttpServletRequest getHTTPServletRequest(){
    
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if(request instanceof HttpServletRequest) {
            return ((HttpServletRequest) request);
            }else {
                
                return null;
            }   
    }
    
    
    /** Get Access to the HTTPAuth Principal's name.
     *  This allows to savely restrict access to data to these belonging
     *  to the authenticated user.
     * 
     * @return the HTTPPrincipal's Name , or null if this cannot be retrieved.
     * 
     * 
     */
    public String getUserPrincipalName(){
    
        HttpServletRequest request=this.getHTTPServletRequest();
        if(request==null){
            System.err.println("HTTPRequest is null, cannot determine AuthUser");
            return null;
        }
        
        if(this.getHTTPServletRequest().getUserPrincipal()==null){
            System.err.println("UserPrincipal is null, cannot determine AuthUser");
            return null;
        }
 
        return this.getHTTPServletRequest().getUserPrincipal().getName();        
    }
    
   
} // class