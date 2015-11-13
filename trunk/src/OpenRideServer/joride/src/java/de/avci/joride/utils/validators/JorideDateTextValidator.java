package de.avci.joride.utils.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import de.avci.joride.constants.JoRideConstants;
import de.avci.joride.jbeans.customerprofile.JCustomerEntityService;
import de.avci.joride.session.HTTPUser;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;



/** Validate text input against upper and lower bounds
 * 
 * @author jochen
 *
 */
public abstract class JorideDateTextValidator extends JorideValidator {

	
	/** If <> null, then the limit is enforced
	 *  as upper limit for the date.
	 * 
	 */
	Date upperLimit=null;
	
	
	/** Message to be sent if upper limit is  exceeded
	 */
	private String upperLimitMessage;
	
	
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object text)
			throws ValidatorException {
	
			DateFormat df=new SimpleDateFormat(JoRideConstants.getDateTimeFormatString());
		
		
			Date date=null;
			
			try {date= (Date) df.parseObject(""+text);
			} catch (ParseException exc) {
				
				// ToDo: find appropriate Errormessage
				FacesMessage fm=new FacesMessage(" TODO Date format error ");
				
				throw new ValidatorException(fm);
			}
			
			
			
			if( this.getUpperLimit()!=null && date.getTime()>this.getUpperLimit().getTime()){
				
				FacesMessage fm=new FacesMessage(this.getUpperLimitMessage());
				throw new ValidatorException(fm);
			}
				
			
		}

	
	
	
	
	
	
	public Date getUpperLimit(){
		return this.upperLimit;
	}
	
	public void setUpperLimit(Date d){
		this.upperLimit=d;
	}
	
	
	public String getUpperLimitMessage() {
		return upperLimitMessage;
	}

	public void setUpperLimitMessage(String upperLimitMessage) {
		this.upperLimitMessage = upperLimitMessage;
	}

		
		
		
		
		
		
	}
	
	
	


