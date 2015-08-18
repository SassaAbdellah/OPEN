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

/** Validator to check wether user has added a valid email
 * @author jochen
 *
 */

@FacesValidator("de.avci.joride.jbeans.customerprofile.registration.RegistrationEmailValidator")
public class RegistrationEmailValidator extends JorideValidator  {

	
	/** Check if object is valid email address!
	 * 
	 */
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object obj)
			throws ValidatorException {
		
		  String emailAddress=(String) obj;
		   
	       if(  emailAddress==null || ( !(CustomerUtils.isValidEmailAdress(emailAddress)))){
	    	   FacesMessage message=new FacesMessage(this.getErrormessageByErrorCode(ErrorCodes.CUSTCREATION_EMAIL_SYNTAX_Error_Str));
			   throw new ValidatorException(message);
	          
	       }
	      
	       // check, if email already exists, or not
	        String normalizedemail = new CustomerDataNormalizer().normalizeEmailAddress(emailAddress);
	        if (new JCustomerEntityService().emailExists(normalizedemail)) {
	        	 FacesMessage message=new FacesMessage(this.getErrormessageByErrorCode(ErrorCodes.CUSTCREATION_EMAIL_EXISTS_Error_Str));
				 throw new ValidatorException(message);
	        }
	        
	    }	

}
