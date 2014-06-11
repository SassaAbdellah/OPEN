package de.avci.joride.utils.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.facelets.ConverterConfig;

import de.avci.joride.constants.JoRideConstants;


/** Convert Date to and from String, honoring the *user defined*  timezone.
 * 
 * @author jochen
 *
 */

@FacesConverter("de.avci.joride.utils.converter.DateTimeConverterTimezoneAware")

public class DateTimeConverterTimezoneAware implements Converter {
	
	
	DateFormat df=JoRideConstants.createDateTimeFormat();
	

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		try { return df.parseObject(arg2);
		} catch (ParseException exc) {
			throw new Error("Error while parsing date : ", exc);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		if(arg2 instanceof Date){
			return df.format((Date) arg2);
		} else {	
			throw new Error("Error while converting date  argument is no date : "+arg2);
		}
	}
		
}
