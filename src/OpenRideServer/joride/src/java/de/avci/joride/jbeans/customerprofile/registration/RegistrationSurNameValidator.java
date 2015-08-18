package de.avci.joride.jbeans.customerprofile.registration;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.avci.joride.utils.validators.JorideValidator;
import de.avci.openrideshare.errorhandling.ErrorCodes;

/** Validator to check wether user has added a valid surname
 * @author jochen
 *
 */

@FacesValidator("de.avci.joride.jbeans.customerprofile.registration.RegistrationSurNameValidator")
public class RegistrationSurNameValidator extends JorideValidator  {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object obj)
			throws ValidatorException {

		String surName = (String) obj;

		if (surName == null || surName.trim().equals("")) {
			FacesMessage message = new FacesMessage(
					this.getErrormessageByErrorCode(ErrorCodes.CUSTCREATION_SURNAME_SYNTAX_Error_Str));
			throw new ValidatorException(message);
		}
	}

}
