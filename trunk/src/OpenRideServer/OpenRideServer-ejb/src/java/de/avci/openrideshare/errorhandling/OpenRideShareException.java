package de.avci.openrideshare.errorhandling;

import java.util.Locale;

import de.avci.openrideshare.utils.PropertiesLoader;


/** Common base class for Exceptions to be thrown when accessing ORS functionality
 * 
 * 
 * 
 * @author jochen
 *
 */

public  class OpenRideShareException extends Exception {
	

	
	/** Default UID 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	/** Numerical Error Code to be set on instantiation.
	 * 
	 */
	private Integer errorCode;
	
	

			
	
	/** For each such message, a message property should be kept (somewhere).
	 *  The message Property is there to provide Access to the short and verbose
	 *  Error messages in the MessageProperties.
	 *  
	 * 
	 * @return
	 */
	public String getMessagePropertyName(){
		return ErrorCodes.getErrorStr(this.getErrorCode());
	}
	
	/** Override Standard getMessage() method to return the short english Message
	 *  from MessageProperties
	 *  
	 * @return
	 */
	public Integer getErrorCode(){
		return this.errorCode;
	}
	
	
	
	
	@Override
	public String getMessage(){
		
		return ""+PropertiesLoader.getMessageProperties(Locale.ENGLISH).get(getMessagePropertyName());	
	}
	
	
	/** Trivialized to return getMessage(), since there is no default locale that makes sense
	 */
	@Override
	public String getLocalizedMessage(){
		
		return getMessage();
	}
	
	/** Return error message for specialized locage
	 */
	
	public String getLocalizedMessage(Locale locale){
			
		return ""+PropertiesLoader.getMessageProperties(locale).get(getMessagePropertyName());	
	}
	
	
	/** Return a json representation of this error
	 */
	public String getJSONRepresentation(){
		
		String res="Error{ \n"
				+ "errorCode     : "+this.getErrorCode()+", \n"
				+ "errorProperty : "+this.getMessagePropertyName()+", }\n";
		
		return res;
		
	}
	
	
	
	
	
	
	/** Create an Exception with the specified ErrorCode.
	 *  The errorcode is must correspond to one of the ErrorCodes defined
	 *  in the ErrorCodes class.
	 *  
	 *  If the ErrorCode passed as parameter is not found in the ErrorCodes class,
	 *  then an Error is thrown and the Exception is not initialized.
	 *  
	 *
	 *  @param errorCode
	 */
	public OpenRideShareException(Integer errorCode){
		
		
		super();
		
		String messageProperty=ErrorCodes.getErrorStr(errorCode);
		
		if(messageProperty==null){
			throw new Error("Cannot create decent Exception, Eroor code "+errorCode+" not known");
		}
		
	}
	
	
	

}

