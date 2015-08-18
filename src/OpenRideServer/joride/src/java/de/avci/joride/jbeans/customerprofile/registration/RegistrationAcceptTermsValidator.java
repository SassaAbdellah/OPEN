package de.avci.joride.jbeans.customerprofile.registration;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import de.avci.joride.utils.validators.JorideValidator;
import de.avci.openrideshare.errorhandling.ErrorCodes;

/** Validator to check wether user has checked "accept terms" checkbox
 * 
 * @author jochen
 *
 */

@FacesValidator("de.avci.joride.jbeans.customerprofile.registration.RegistrationAcceptTermsValidator")
public class RegistrationAcceptTermsValidator extends JorideValidator {

	
	/** Object plugged in it the boolean from checkbox.
	 *  Checkbox must be checked (terms accepted), else 
	 *  form will not be validated
	 */
	
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object obj)
			throws ValidatorException {
		
		Boolean checked = (Boolean) obj;
		
		if(!checked) {
			FacesMessage message=new FacesMessage(this.getErrormessageByErrorCode(ErrorCodes.CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str));
			throw new ValidatorException(message);
		}
	}
	
}
