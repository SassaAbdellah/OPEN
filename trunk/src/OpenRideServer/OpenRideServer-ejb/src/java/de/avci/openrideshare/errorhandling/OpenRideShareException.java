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
	
	

			
	
	
	
	/** Override Standard getMessage() method to return the short english Message
	 *  from MessageProperties
	 *  
	 * @return
	 */
	public Integer getErrorCode(){
		return this.errorCode;
	}
	
	
	
	
	

	
	/** Return a json representation of this error
	 */
	public String getJSONRepresentation(){
		
		String res="Error{ \n"
				+ "errorCode     : "+this.getErrorCode()+", \n"
				+ "errorMessage  : "+this.getMessage()+", }\n";
		
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
		
		super(ErrorCodes.getErrorStr(errorCode));
		this.errorCode=errorCode;
		
	}
	

}

