package de.avci.joride.utils.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import de.avci.openrideshare.boundaries.BoundariesBean;
import de.avci.openrideshare.errorhandling.ErrorCodes;

/** A simple validator checking that Points are in valid Longitude/Latitude
 * 
 *  Object passed to validate method is a simple double describing a longitude.
 *  this is tested for beeing contained within valid bounds for latitude and longitude.
 *
 *  If point is not within valid range, it throws a generic errormessage of type "point not in bounds".
 *
 * @author jochen
 *
 */


@FacesValidator("de.avci.joride.utils.validators.JorideLatitudeValidator")

public class JorideLatitudeValidator extends JorideValidator{

	
	/** @return generic "Point exceeds spatial bounds" Errorcode
	 */
	protected String getErrorCode(){return ErrorCodes.SPATIAL_BOUNDS_EXCEEDED;}
	

	
	@Override
	public void validate(FacesContext context, UIComponent component, Object latitudeO)
			throws ValidatorException {
		
			Double latitude=(Double) latitudeO;
			
			if(latitudeO==null || (! new BoundariesBean().isWithinLatitudeBounds(latitude))){
			
				FacesMessage message=new FacesMessage(this.getErrormessageByErrorCode(getErrorCode()));
				throw new ValidatorException(message);
			}
	}

}
