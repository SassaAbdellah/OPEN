package de.avci.openrideshare.errorhandling;



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
	 *  String valued (not numerical!) Error code associated to this Exception.
	 *  These are well known keys and will also serve in retrieving 
	 *  localized error messages
	 */ 
	
	private String errorCode;
	
	
	public String getErrorCode(){
		return this.errorCode;
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
	public OpenRideShareException(String errorCode){
		
		super(errorCode);
		this.errorCode=errorCode;
		
	}
	

}

