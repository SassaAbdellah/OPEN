package de.avci.joride.utils.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import de.avci.joride.constants.JoRideConstants;


@FacesConverter("rideDateTextConverter")


public class JRideTextDateConverter implements Converter {
	
	private DateFormat df=JoRideConstants.createDateTimeFormat();

	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String text) {
	
		try { return (Date) df.parseObject(text);
		} catch (ParseException exc) {
			// suppress Exception here, throw validator exception instead
			return new Date();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		
		return df.format((Date) obj);
	}

	
	
	
}
