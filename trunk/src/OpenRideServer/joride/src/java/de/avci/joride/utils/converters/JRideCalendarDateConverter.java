package de.avci.joride.utils.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.calendar.Calendar;

/**
 * Generic converter converting the input of a primefaces calendar 
 * with maxdate attribute in the way that we use in ORS.
 * 
 * This can generically be used with the startDate widget for offers,
 * as well as with the 
 * 
 * 
 *  
 * 
 * This ensures that ridestartTime is between current date and maxdate defined
 * in component.
 * 
 * 
 * @author jochen
 *
 */

@FacesConverter("rideDateCalendarConverter")
public class JRideCalendarDateConverter implements Converter {
	
	/** Create a java.util.Date object from string representation using the calendars pattern.
	 * 
	 * @param cal       calendar from which value and pattern are received
	 * @param dateStr   value string
	 * @return 			value string converted according to pattern, or null if that doesn't work.
	 */
	protected Date convertDateFromString(Calendar cal, String dateStr){
		
		String datePattern=cal.getPattern();
		DateFormat df=new SimpleDateFormat(datePattern);
		
		try { return df.parse(dateStr);
		} catch (ParseException exc) {
			return null;
		}
	}
	
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String valueStr) {
		
		Calendar cal=(Calendar) component;
		String maxDateStr=""+cal.getMaxdate();
		
		Date value=this.convertDateFromString(cal, valueStr);
		Date maxdate=this.convertDateFromString(cal, maxDateStr);
		// since adding an offer costs may take some time, we add one minute here
		Date now=new Date(System.currentTimeMillis()+(60l*1000l));
		
		// this may happen if input string is not well formatted
		if(value==null){
			return now;
		}
		// align to current date
		if(value.getTime()<now.getTime()){
			return now;
		}
		// align to potential max date, return maxdate - one minute
		if(value.getTime()>maxdate.getTime()){
			return new Date(maxdate.getTime()-(60l*1000l));
		}
		// happy case...
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		Calendar cal=(Calendar) component;
		
		Date date=null;
		if(value == null){ // if there is no date, the new date is now+1Minute
			date=new Date(System.currentTimeMillis()+(60l*1000l));
		} else {
			date = (Date) value;
		}
	
		String pattern=cal.getPattern();
		DateFormat df=new SimpleDateFormat(pattern);	
		return df.format(date);
	}

}
