/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.avci.joride.constants;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import de.avci.joride.utils.PropertiesLoader;

/**
 * This class centrally defines a number of Constants for joride Frontend some
 * of these may eventually be made configurable, so it's good to have them in
 * one spot.
 * 
 *
 * @author jochen
 */

@ApplicationScoped
@Named
public class JoRideConstants implements Serializable {

	/**
	 * default serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return timestamp format, defined as "tsformat" property in
	 *         datetime.properties
	 */
	public static String getDateTimeFormatString() {
		return PropertiesLoader.getDatetimeProperties().getProperty("tsformat");
	}

	// @return date format, defined as "dateformat" property in
	// datetime.properties
	public static String getDateFormatString() {
		return PropertiesLoader.getDatetimeProperties().getProperty(
				"dateformat");
	}

	/**
	 * Creates a new DateTimeFormat (Datetime=Date+Time of Day) using Timezone
	 * retrieved with "getTimezone"
	 * 
	 * @return
	 */
	public static DateFormat createDateFormat() {
		DateFormat res = new SimpleDateFormat(getDateFormatString());
		res.setTimeZone(getTimeZone());
		return res;
	}

	/**
	 * Creates a new DateTimeFormat (Datetime=Date+Time of Day)
	 * 
	 * @return
	 */
	public static DateFormat createDateTimeFormat() {
		DateFormat res = new SimpleDateFormat(getDateTimeFormatString());
		res.setTimeZone(getTimeZone());
		return res;
	}

	/**
	 * TODO: make Timezone configurable, currently it is default timezone.
	 * 
	 * @return
	 */
	public static TimeZone getTimeZone() {
		return TimeZone.getTimeZone(PropertiesLoader.getDatetimeProperties()
				.getProperty("defaulttimezone"));
	}

	/**
	 * Decimal Format for displaying ratings
	 */
	public DecimalFormat createRatingAverageFormat() {
		return new DecimalFormat("#0.00");
	}

	/**
	 * JSON Parameter describing the name of the JSON object describing the
	 * update count. This is used with JSON Update Service
	 */
	public static String PARAM_NAME_UPDATE_RESPONSE = "UpdateResponse";

	/**
	 * Make PARAM_NAME_UPDATE_RESPONSE availlable as a JSF Property
	 * 
	 * @return PARAM_NAME_UPDATE_RESPONSE
	 */
	public static String getParamNameUpdateResponse() {
		return PARAM_NAME_UPDATE_RESPONSE;
	}

	/**
	 * JSON Parameter describing the number of updated offers for this user.
	 * This is used with JSON Update Service.
	 */
	public static String PARAM_NAME_NO_UPDATED_OFFERS = "NoOfUpdatedOffers";

	/**
	 * Make PARAM_NAME_NO_UPDATED_OFFERS availlable as a JSF Property.
	 * 
	 * @return PARAM_NAME_NO_UPDATED_OFFERS
	 */
	public String getParamNameNoUpdatedOffers() {
		return PARAM_NAME_NO_UPDATED_OFFERS;
	}

	/**
	 * JSON Parameter describing the number of updated requests for this user.
	 * This is used with JSON Update Service
	 */
	public static String PARAM_NAME_NO_UPDATED_REQUESTS = "NoOfUpdatedRequests";

	/**
	 * Make PARAM_NAME_NO_UPDATED_OFFERS availlable as a JSF Property
	 * 
	 * @return PARAM_NAME_NO_UPDATED_OFFERS
	 */
	public String getParamNameNoUpdatedRequests() {
		return PARAM_NAME_NO_UPDATED_REQUESTS;
	}

	/**
	 * HTTP Parameter transporting Customer's Nickname
	 * 
	 */
	protected static final String PARAM_NAME_NICKNAME = "nickname";

	/**
	 * Make Nickname parameter available to JSF Beans
	 */
	public String getParamNameNickname() {
		return PARAM_NAME_NICKNAME;
	}
	
	
	/** Property signifying wether this application is mobile (true) or desktop (false) application
	 */
	public static final String PROPERTY_NAME_defaultMobile="mobileDefault";
	
	
	/** Property signifying wether this application is  allows switching to mobile (true) version.
	 */
	public static final String PROPERTY_NAME_enableMobile="enableMobile";
	
	/** Property signifying wether this application is  allows switching to desktop (true) version.
	 */
	public static final String PROPERTY_NAME_enableDesktop="enableDesktop";
	
	
	
	

	/**
	 * "showCookieMessage" is used to decide wether or not cookie messages
	 * should be displayed on top of joride pages.
	 * 
	 */
	public static final String PROPERTY_NAME_showCookieMessage = "showCookieMessage";

	/***************************************************************************************/
	/**** Frontend capability stuff comes here ! *******************************************/
	/***************************************************************************************/

	/**
	 * Property name for driver functionality, i.e turning on/off offering rides
	 * (true/false)
	 */
	public static final String PROPTERY_NAME_joride_capability_driver = "joride_capability_driver";

	/**
	 * Property name for passenger functionality, i.e turning on/off requesting
	 * hitches (true/false)
	 */

	public static final String PROPTERY_NAME_joride_capability_passenger = "joride_capability_passenger";

	/**
	 * Property name for rating functionality, i.e turning on/off rating others
	 * (true/false)
	 */

	public static final String PROPTERY_NAME_joride_capability_rating = "joride_capability_rating";

	/**
	 * Property which decides wether or not to show an menu item for addressing
	 * update pages programmatically if true, then there will be a menu item for
	 * updates
	 * 
	 */
	public static final String PROPTERY_NAME_joride_capability_updateMenuItem = "joride_capability_updateMenuItem";

	
	/**  Property which decides wether or not to show an menu item for searching
	 * 
	 */
	public static final String PROPTERY_NAME_joride_capability_searchMenuItem = "joride_capability_searchMenuItem";

	
	
	/**
	 * Property which decides wether or not drivers can add waypoints to change
	 * the route suggested by the routing engine
	 */
	public static final String PROPTERY_NAME_joride_capability_waypoints = "joride_capability_waypoints";

	/**
	 * Property which decides wether or not messages and update should be
	 * displayed in page headers set this to false, if joride should be used as
	 * a frontend for maintaining master data only
	 */
	public static final String PROPTERY_NAME_joride_capability_messageDisplay = "joride_capability_messageDisplay";

	/** Property which decides wether or not to use "favoritePlaces" to pick
	 *  start or endpoints from.
	 */
	public static final String PROPTERY_NAME_joride_capability_favoritePlaces = "joride_capability_favoritePlaces";

	
	/** Property which decides wether or not to use html5 geolocation to dtermine the
	 *  current position when picking start or endpoints.
	 */
	public static final String PROPTERY_NAME_joride_capability_currentPosition = "joride_capability_currentPosition";

	
	/** Property which decides wether or not to use geomapper services 
	 * like OSM or Google to dtermine the current position when picking start or endpoints.
	 */
	public static final String PROPTERY_NAME_joride_capability_geocodingServices = "joride_capability_geocodingServices";

	
	/** Property which decides wether or not to show numerical coordinates in the frontend
	 *  when picking starpoints, waypoints or endpoints.
	 */
	public static final String PROPTERY_NAME_joride_capability_showCoordinates = "joride_capability_showCoordinates";

	
	
} // class
