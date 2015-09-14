package de.avci.joride.session;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.render.RenderKitFactory;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.utils.DefaultLocale;
import de.avci.joride.utils.HTTPUtil;
import de.avci.joride.utils.PropertiesLoader;

/**
 * A simplistic bean allowing access to HTTPAuthData
 *
 *
 */
@Named("HTTPUser")
@SessionScoped
public class HTTPUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2141634023951952843L;

    public String getCurrentUser() {

        Principal principal = getUserPrincipal();
        if (principal == null) {
            return null;
        }

        return principal.getName();
    }

    public Principal getUserPrincipal() {

        if (getFacesContext() != null) {
            return getFacesContext().getExternalContext().getUserPrincipal();
        }

        return null;
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

   
    
    
    
    /** 
     * 
     * @return  true, if httprequest has a non-null user principal, else false
     */
    public boolean isLoggedIn(){
        return getUserPrincipal()!=null;
    }
    
    public String getLoginLabel() {
        if (getUserPrincipal() == null) {
        	java.util.Locale myLocale=new HTTPUtil().detectBestLocale();
            return (PropertiesLoader.getMessageProperties(myLocale).getProperty("login"));
        }
        return null;
    }
    
    
    /**  URLBase is the base of the webapplication,
     *   i,e "/joride" for joride webapp,
     *   "/joride-public/" for joride-public webapp, etc
     *   It is defined in navigation.properties file.
     * 
     * 
     * @return 
     */
    public String getURLBase(){
         
           return PropertiesLoader.getNavigationProperties().getProperty("urlBase");
    }
    
    
    /**  LoginURL is the URL where Users should be sent to log in.
     * 
     *   It is defined in navigation.properties file.
     * 
     * 
     * @return 
     */
    
    public String getLoginURL() {
             
            String urlLogin=PropertiesLoader.getNavigationProperties().getProperty("urlLogin");
            return getURLBase()+urlLogin;
            
    }
    

   
     public String getLogoutLabel() {
        if (getUserPrincipal() != null) {
        	java.util.Locale myLocale=new HTTPUtil().detectBestLocale();
            return PropertiesLoader.getMessageProperties(myLocale).getProperty("logout");
        }
        return null;
    }
    
    
    public String getLogoutURL() {
        
            String urlLogout=PropertiesLoader.getNavigationProperties().getProperty("urlLogout");        
            return urlLogout;
    } 
     
     
  
    public String getLoggedOutURL() {
            
            String urlLoggedOut=PropertiesLoader.getNavigationProperties().getProperty("urlLoggedOut");
            return urlLoggedOut;
    } 

    /** URL where the smartLoginServlet lives
     * 
     * @return
     */
    public String getSmartLoginURL() {
        
        String smartLoginUrl1=PropertiesLoader.getNavigationProperties().getProperty("urlSmartLogin");
        return getURLBase()+smartLoginUrl1;
} 
      
    
      /** Invalidate Session, then forward to "loggedOutURL"
       * 
       * @return 
       */
    public String logOut(){
    
        HTTPUtil hpu=new HTTPUtil();
        HttpSession session=hpu.getHTTPServletRequest().getSession();
        session.invalidate();
        try { hpu.getHTTPServletResponse().sendRedirect(getLoggedOutURL());
        } catch (IOException exc) {
            throw new Error("Unexpected Error", exc);
        }
        
        return "logout";
       
    }
    
    
        /** URL from which the OpenLayers library should be included
     *  this is now configurable via the urlOpenLayers property
     *  in navigation.properties
     *  
     */
    public String getOpenLayersURL() {


        PropertiesLoader pl = new PropertiesLoader();

        System.err.println("TODO: navigation props: " + PropertiesLoader.getNavigationProperties());

        String urlLoggedOut = PropertiesLoader.getNavigationProperties().getProperty("urlOpenLayers");
        return urlLoggedOut;
    }
    
     
    /** Flag signifying wether or not maps should be shown.
     *  I.e: Maps are cool and should always be shown, unless there
     *  is not enough bandwidth.
     *  
     *  Since such conditions can be expected to last for
     *  
     * 
     */
    private boolean showMap=false;

	public boolean isShowMap() {
		return showMap;
	}

	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}
    
	
	public void toggleMapVisibility(ActionEvent evt){
		this.setShowMap(!this.isShowMap());
	}
    
    /** return inversion of isShowMap
     */
    public boolean isHideMap(){
    	return !this.isShowMap();
    }
    
    
    
    
    
    /** Flag signifying wether or not CookieMessage should be shown.
     *  I.e: Cookie warning is shown before logging in,  or until 
     *  user disables CookieMessage.
     *  
     *  The value of the cookie message is per se not enough to 
     *  decide wether cookie message should be displayed or not,
     *  Cookie message must be enabled in general via localhost.properties,
     *  and Cookie message will dissapear as soon as the user logs in.
     *   
     *  
     *  
     * 
     */
    private boolean showCookieMessageFlag=true;

	public boolean isShowCookieMessageFlag() {
		return showCookieMessageFlag;
	}

	public void setShowCookieMessageFlag(boolean showCookieMessage) {
		this.showCookieMessageFlag = showCookieMessage;
	}
    
	
	public void toggleCookieMessageVisibility(ActionEvent evt){
		this.setShowCookieMessageFlag(!this.isShowCookieMessageFlag());
	}
    
	
	/** Cookie Message is shown under three conditions:
	 *  
	 *  1) Cookie message is enabled via local properties // TODO: not yet implemented
	 *  2) User has not clicked "Got it", i.e: cookieMessageFlag is true
	 *  3) User has not given consent to use of cookies by logging in. 
	 * 
	 * @return
	 */
	public boolean isShowCookieMessage() {
		
		
		String showCookiesStr=PropertiesLoader.getOperationalProperties().getProperty(JoRideConstants.PROPERTY_NAME_showCookieMessage);
		
		// if property is missing from local.properties, then do not show message
		Boolean showCookiesP=false;
		
		try{ showCookiesP=new Boolean(showCookiesStr);
		} catch(Exception exc){
			
			Logger.getLogger(this.getClass().getCanonicalName()).log(Level.WARNING, "Cannot load showCookie property : ", exc.getMessage());
		}
		
		// if user is logged in, close cookie message for the rest of the session
		if(this.isLoggedIn()){
			this.setShowCookieMessageFlag(false);
		}
		
		return showCookiesP && (!this.isLoggedIn()) && isShowCookieMessageFlag();
		
	}
    
    
    
    
    
    
    /** Return the timezone for this session
     * 
     */
    public TimeZone getTimeZone(){
    	return JoRideConstants.getTimeZone();
    }
    
    
    /** Return the list of supported locales
     */
    public Locale[] getSupportedLocales(){
    		
    	return new JCustomerEntityService().getSupportedLocales();
    }
    
    
    /**
     * 
     * @return list of supported languages as Select Items
     */
    public List <SelectItem> getSupportedLanguages(){
    	
    	
    	Locale[] supportedLocales=new JCustomerEntityService().getSupportedLocales();
    	
    	ArrayList <SelectItem> res = new ArrayList<SelectItem> ();
    	
    	for (Locale l: supportedLocales){
    		SelectItem s=new SelectItem(l, l.getDisplayLanguage(l));
    		res.add(s);    
    	}
    	
    	return res;
    } 
    
    
    /** Get Local from http request 
     * 
	 * 
	 * @param request
	 */

	public Locale getLocale() {
		
		
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletRequest httpServletRequest=null;
		
		
		if (request instanceof HttpServletRequest) {
			httpServletRequest= ((HttpServletRequest) request);
		} else {
			return DefaultLocale.getDefaultLocale() ;
		}
		
		return httpServletRequest.getLocale();
	}

	
	
	
	
	
	
	
	
	/** Flag to determine whether this application is mobile (true)
	 *  or desktop (false)
	 * 
	 */
	private Boolean mobileFlag;
	
	
  
	/** Accessor with lazy installation. Reads defaultMobile property.
	 * 
	 * @return true if application is mobile, else false.
	 */
	public Boolean getMobile(){
		
		if(mobileFlag==null){
			
			// see if we user has set the flag in another application?
			Object contextFlag=getFacesContext().getExternalContext().getApplicationMap().get(PARAM_NAME_MOBILE_FLAG);
			
			if(contextFlag instanceof Boolean){
				mobileFlag=(Boolean) contextFlag;
				return mobileFlag;
			}
			
			// if all else fails, deternube vakze from properties
			String mobileStr=PropertiesLoader.getOperationalProperties().getProperty(JoRideConstants.PROPERTY_NAME_defaultMobile);
			mobileFlag=new Boolean(mobileStr).booleanValue();
		}
		return mobileFlag;
	}
	
	
	public void setMobile(boolean arg){
		
		this.mobileFlag=arg;
		getFacesContext().getExternalContext().getApplicationMap().put(PARAM_NAME_MOBILE_FLAG, arg);
	}
	
	
	/**
	 * 
	 * @return true, if application is desktop (non-mobile), else false.
	 */
	public Boolean getDesktop(){
		return !getMobile();
	}
	
	
	/** switch between desktop and mobile version
	 */
	public void toggleMobile(){
		this.setMobile(! this.getMobile());
	}
	
	/**  Returns the render kit with which this users wants to be rendered 
	 *   (either mobile or desktop)
	 * 
	 * @return "PRIMEFACES_MOBILE" if "mobile" property is set, "HTML_BASIC" by Default.
	 */
	public String getRenderKit(){
	
		if(this.getMobile()){
			return "PRIMEFACES_MOBILE";	
			
		} else {
			return RenderKitFactory.HTML_BASIC_RENDER_KIT;
		}
	}
	
	
	/** Flag to determine wether we may change from Desktop to mobile mode
	 * 
	 */
	private Boolean enableMobileFlag;
	
	
	/** Determine if configuration allows to switch from desktop to mobile mode
	 */
	public boolean getEnableMobile(){
		
		if(enableMobileFlag==null){
			String enableMobileStr=PropertiesLoader.getOperationalProperties().getProperty(JoRideConstants.PROPERTY_NAME_enableMobile);
			enableMobileFlag=new Boolean(enableMobileStr).booleanValue();
		}
		return enableMobileFlag;	
	}
	
	
	/** Context param under which the mobile flag can be stored in request context
	 */
	private static final String PARAM_NAME_MOBILE_FLAG="joride_mobile_mode";
	
	
	/** Flag to determine wether we may change from mobile to desktop mode
	 */
	private Boolean enableDesktopFlag;
	
	
	/** Determine if configuration allows to switch from desktop to mobile mode
	 */
	public boolean getEnableDesktop(){
		
		if(enableDesktopFlag==null){		
			String enableDesktopStr=PropertiesLoader.getOperationalProperties().getProperty(JoRideConstants.PROPERTY_NAME_enableDesktop);
			enableDesktopFlag=new Boolean(enableDesktopStr).booleanValue();
		}
		return enableDesktopFlag;	
	}
	
	
	
	
 
    
} // class
