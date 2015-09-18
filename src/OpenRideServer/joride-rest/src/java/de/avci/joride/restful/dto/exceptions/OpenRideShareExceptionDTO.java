package de.avci.joride.restful.dto.exceptions;

import de.avci.openrideshare.errorhandling.OpenRideShareException;

/** DTO wrapping an OpenRideShareException
 * 
 * @author jochen
 *
 */
public class OpenRideShareExceptionDTO {
	
	
	/** OpenRideShareException's Error code
	 */
	private String errorCode;
	
	/**  OpenRdeShareException's ErrorMessage 
	 */
	private String message;
	
	
	/** OpenRideShareException's localized message
	 */
	private String localizedMessage;
	
	
	

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
	
	
	public OpenRideShareExceptionDTO(){
		super();
	}
	
	
	
	public OpenRideShareExceptionDTO(OpenRideShareException exc){
		
		this.setErrorCode(exc.getErrorCode());
		this.setMessage(exc.getMessage());
		this.setLocalizedMessage(exc.getLocalizedMessage());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocalizedMessage() {
		return localizedMessage;
	}

	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}
	

}
