package de.avci.joride.jbeans.customerprofile.registration;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.avci.joride.jbeans.customerprofile.CustomerDataNormalizer;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.utils.validators.JorideValidator;
import de.avci.openrideshare.errorhandling.ErrorCodes;
import de.fhg.fokus.openride.customerprofile.CustomerUtils;

/**
 * Validator to check wether user has added a valid given name
 * 
 * @author jochen
 *
 */

@FacesValidator("de.avci.joride.jbeans.customerprofile.registration.RegistrationGivenNameValidator")
public class RegistrationGivenNameValidator extends JorideValidator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object obj)
			throws ValidatorException {

		String givenName = (String) obj;

		if (givenName == null || givenName.trim().equals("")) {
			FacesMessage message = new FacesMessage(
					this.getErrormessageByErrorCode(ErrorCodes.CUSTCREATION_GIVENNAME_SYNTAX_Error_Str));
			throw new ValidatorException(message);
		}

	}

}
