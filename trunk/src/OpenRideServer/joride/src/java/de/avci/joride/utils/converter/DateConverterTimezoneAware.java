package de.avci.joride.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/** Convert Date to and from String, honoring the *user defined*  timezone.
 * 
 * @author jochen
 *
 */
public class DateConverterTimezoneAware implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		// now, what kind of String do we expect in a date component?
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}
	
	

	



}
