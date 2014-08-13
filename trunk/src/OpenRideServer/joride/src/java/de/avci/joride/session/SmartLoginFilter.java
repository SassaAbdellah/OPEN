package de.avci.joride.session;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sun.jersey.api.core.HttpRequestContext;

import de.fhg.fokus.openride.customerprofile.CustomerControllerBean;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;

public class SmartLoginFilter implements Filter {

	@Override
	public void destroy() {
		// Nothing to do...
	}

	
	/** Classify String as email adress.
	 *  Currently, just check if name contains an '@'
	 * 
	 *  TODO: move function to server component, ensure 
	 *        nicknames which look like email adresses are forbidden
	 */
	private boolean isEmailAddress(String arg){
		
		if(arg==null) return false;
		return arg.contains("@");
	}
	
	
	
	
	
	/** Rewrite remote user if necessary, for example
	 *  map user logged in with emailaddress to his nickname.
	 * 
	 * @param request
	 */
	private void mangleRemoteUser(HttpServletRequest request){
		
		
		String remoteUserName=request.getRemoteUser();
		
		if(remoteUserName==null){
			// nothing to do. Typically not logged in.
			return;
		}
		
		
		/** User tries to login with email address.
		 *  Rewriting username to corresponding nickname
		 * 
		 */
		
		
		// TODO: remove when experimental phase is over...
		Enumeration<String> parEn=request.getParameterNames();
		String params=" --- ";
		while (parEn.hasMoreElements()){
			params=params+" --- "+parEn.nextElement();
		}
		
		// TODO: remove when experimental phase is over...
		Enumeration<String> attEn=request.getAttributeNames();
		String attributes=" --- ";
		while (attEn.hasMoreElements()){
			params=params+" --- "+attEn.nextElement();
		}
			
		if((request.getParameter("j_password") !=null) && isEmailAddress(remoteUserName)){
		
			// If user has already delivered a password, we do a relogin
			String password=request.getParameter("j_password");
			
			
			// TODO: rewrite to only get the user name
			CustomerEntity ce=new CustomerControllerBean().getCustomerByEmail(remoteUserName);
			// relogin
			try {
				request.logout();
			} catch (ServletException e) {
				throw new Error("Error while logging out programatically", e);
			}
			
			String nickname=ce.getCustNickname();
			
			
			try {
				request.login(nickname, password);
			} catch (ServletException exc) {
				throw new Error("Error while rewriting email Address", exc );
			}
			
		}
		
	}
	
	
	
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		// check principal, if necessary, rewrite principal

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		String remoteUser = httpServletRequest.getRemoteUser();
		
		// Mangle Remote User if necessary
		mangleRemoteUser(httpServletRequest);
		
		/*
		httpServletRequest.logout();
		httpServletRequest.login("user2", "user2");
		*/
		
		
		// delegate to upstream...
		filterChain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
