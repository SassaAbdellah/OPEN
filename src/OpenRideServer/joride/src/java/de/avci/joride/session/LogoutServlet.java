package de.avci.joride.session;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/** A simple servlet to manage the logout and other Session States from jORide 
 *
 *
 */
public class LogoutServlet extends HttpServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4907032788978484525L;


	@Override

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		this.processRequest(request, response);
	}
	
	@Override

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		this.processRequest(request, response);
	}
	
	
	
	
	protected String getLoggedOutURL(){
		return new HTTPUser().getLoggedOutURL();
	}
	
	
	/** Invalidate the Session, then forward to logout 
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * 
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
		

	
			HttpSession session=request.getSession(false);
			
			if(session!=null){
				session.invalidate(); 
			} 
			
		
			try { response.sendRedirect(getLoggedOutURL());
			} catch (IOException exc) {
				System.err.println("Error while sending redirect to logout URL ");
			}
			
	} // processRequest
	
	
} // class
