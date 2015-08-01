package de.avci.joride.utils.validators;

import java.util.Locale;
import java.util.Properties;

import javax.faces.validator.Validator;

import de.avci.joride.utils.HTTPUtil;
import de.avci.openrideshare.errorhandling.ErrorCodes;
import de.avci.openrideshare.utils.PropertiesLoader;




/** Abstract base class for ORS specific Validators,
 *  providing basic support for localized errormessages.
 *  
 * 
 * @author jochen
 *
 */
public abstract class JorideValidator implements Validator {
	
	
	/** Locale to be used for errormessages.
	 * 
	 */
	private Locale locale;
	
	public Locale getLocale(){
		return this.locale;
	}
 
	
	/** Properties containing serverside errormessages.
	 *  This is loaded from OpenRideShare-ejb.PropertiesLoader.getErrorProperties()
	 * 
	 */
	private Properties errorProperties;
	
	
	public Properties getErrorProperties(){

		return this.errorProperties;
	}
	
	
	/**  Returns a localized errormessage obtained using an Errorcode
	 * 	 as defined in de.avci.openrideshare.errorhandling.ErrorCode	
	 * 
	 */
	public String getErrormessageByErrorCode(String errorCode){
		
		String result=this.getErrorProperties().getProperty(errorCode);
	
		if(result!=null){return result;}
		
		return this.getErrormessageByErrorCode(ErrorCodes.UnknownError_Str);
	
	}


	/** Initialize locale and Errormessages
	 */
	public JorideValidator(){
		
		locale=new HTTPUtil().detectBestLocale();
		errorProperties=PropertiesLoader.getErrorMessageProperties(locale);		
	}
	


}
