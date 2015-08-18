package de.avci.joride.jbeans.customerprofile.registration;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import de.avci.joride.jbeans.customerprofile.CustomerDataNormalizer;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.utils.validators.JorideValidator;
import de.avci.openrideshare.errorhandling.ErrorCodes;
import de.fhg.fokus.openride.customerprofile.CustomerUtils;

/**
 * Validator to check wether user has added a valid nickname
 * 
 * @author jochen
 *
 */

@FacesValidator("de.avci.joride.jbeans.customerprofile.registration.RegistrationNickNameValidator")
public class RegistrationNickNameValidator extends JorideValidator {

	/**
	 * Object is the potential nickname
	 * 
	 */

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object obj)
			throws ValidatorException {

		String nickName = (String) obj;

		
		  if( nickName==null || ( !(CustomerUtils.isValidNickname(nickName)))){
		  FacesMessage message=new
		  FacesMessage(this.getErrormessageByErrorCode(
		  ErrorCodes.CUSTCREATION_NICKNAME_SYNTAX_Error_Str)); throw new
		  ValidatorException(message); }
		  
		  // check, if email already exists, or not 
		  nickName = new CustomerDataNormalizer().normalizeNickname(nickName); if (new
		  JCustomerEntityService().nicknameExists(nickName)) { FacesMessage
		  message=new FacesMessage(this.getErrormessageByErrorCode(ErrorCodes.
		  CUSTCREATION_NICKNAME_EXISTS_Error_Str)); throw new
		  ValidatorException(message); }
		 

	

	}

}
