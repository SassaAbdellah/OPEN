package de.avci.openrideshare.errorhandling;




/**
 * Set of *String valued* error codes.. these are used as keys in messages.properties
 * to get localized messages.
 * 
 * They are also used as well known text constants to allow an automatic handling
 *  of OpenRideShare Exceptions.
 * 
 * @author jochen
 *
 */
public class ErrorCodes {



	/**
	 * Mnemonic String for Unknown Error. See also corresponding Error Code
	 * UnknownErrorCode:
	 */
	public static final String UnknownError_Str = "UnknownError";

	
		
	/**
	 * Error Code signifying that the Customer for which an action is performed
	 * does not exist
	 */
	public static final String UserDoesNotExistError_Str = "UserDoesNotExist";

	
	///////////////////////////////////////////////////////////////////////
	///
	/// Errors adding Customers
	///
	//////////////////////////////////////////////////////////////////////

	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * terms&conditions not acceppted.
	 */
	public static final String CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str = "TermsOfUseNotAccepted";


	
	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * nickname not compliant to sysntax rules
	 */
	public static final String CUSTCREATION_NICKNAME_SYNTAX_Error_Str = "NicknameSyntaxCheckFailed";


	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 *  givenName not compliant to sysntax rules
	 */
	public static final String CUSTCREATION_GIVENNAME_SYNTAX_Error_Str = "GivenNameCheckFailed";

	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 *  givenName not compliant to syntax rules
	 */
	public static final String CUSTCREATION_SURNAME_SYNTAX_Error_Str = "SurNameCheckFailed";

	
	
	
	
	
	
	/**
	 * "String" to be returned if customer creation failed. Because of email not
	 * compliant to syntax rules
	 */
	public static final String CUSTCREATION_EMAIL_SYNTAX_Error_Str = "EmailSyntaxCheckFailed";
	

	
	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * nickname already exists
	 */
	public static final String CUSTCREATION_NICKNAME_EXISTS_Error_Str = "NicknameAlreadyExists";



	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * email already exists.
	 */
	public static final String CUSTCREATION_EMAIL_EXISTS_Error_Str = "EmailAlreadyExists";


	
	
	

	
	
	/** **********************
	 *  **********************
	 *  
	 *  Adding Offers
	 * 
	 * ***********************
	 * ***********************
	 */
	
	/**
	 * "ErrorCode" to be returned if request creation fails because the planning
	 * horizon is exceeded
	 */
	public static final String OFFERCREATION_HORIZONEXCEEDED_Error_Str = "PlanningHorizonExceededWhenCreatingOffer";

	

	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * RideStartpoint is not set
	 */
	public static final String CreateOfferFailure_RideStartPointNull_Str="RideStartpointNullWhenCreatingOffer";

	
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * RideEndopoint is not set
	 */
	public static final String CreateOfferFailure_RideEndpointNull_Str="RideEndpointNullWhenCreatingOffer";
	
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * Intermediate point are null
	 * 
	 */
	public static final String CreateOfferFailure_IntermediatePointsNull_Str="IntermediatePointsNullWhenCreatingOffer";
	
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * rideStarttime is null
	 * 
	 */
	public static final String CreateOfferFailure_RideStartTimeNull_Str="RideStarttimeIsNullWhenCreatingOffer";
	 
			
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * Ride acceptable detour in Minutes is null.
	 * 
	 */
	public static final String CreateOfferFailure_RideAcceptableDetourMinNull_Str="RideAcceptableDetoutIsNullWhenCreatingOffer";
	


	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * Ride acceptable detour in Meters is null
	 */
	public static final String CreateOfferFailure_Ride_RideAcceptableDetourMeterNull_Str="RideAcceptableDetourInMeterIsNullWhenCreatingOffer";


	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * ride acceptable detour in percent is null.
	 * 
	 */
	public static final String CreateOfferFailure_RideAcceptableDetourPercentNull_Str="RideAcceptableDetourInPercentIsNullWhenCratingOffer";
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * ride startpoint address is null.
	 */
	public static final String CreateOfferFailure_StartPointAddressNull_Str="StartpointAddressIsNullWhenCreatingOffer";
	
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * ride endpoint address is null.
	 */
	public static final String CreateOfferFailure_EndpointAddressNull_Str="EndpointAddressIsNullWhenCreatingOffer";
	
	
	/**
	 * Mnemonic StrCannot create Offer: Maximum Number of open Offers for this
	 * user is exceeded. See also corresponding Error Code
	 * RequestLimitExceededError_Code.
	 */
	public static final String OfferLimitExceededError_Str = "OfferLimitExceeded";
	
	

	
	// ////////////////////////////////////////////////////////
	//
	// Request creation errors
	//
	// ////////////////////////////////////////////////////////

	/**
	 * Mnemonic StrCannot create Request: Maximum Number of open Requests for
	 * this user is exceeded. See also corresponding Error Code
	 * RequestLimitExceededError_Code.
	 */
	public static final String RequestLimitExceededError_Str = "RequestLimitExceeded";

	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * RideStartpoint is not set
	 */
	public static final String CreateRequestFailure_RideStartPointNull_Str="RideStartptIsNullWhenCratingRequest";
	
	
	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * RideEndopoint is not set
	 */
	public static final String CreateRequestFailure_RideEndpointNull_Str="RideEndptIsNullWhenCreatingRequest";
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * rideStarttime is null
	 * 
	 */
	public static final String CreateRequestFailure_RideStartTimeEarliestNull_Str="RideStarttimeEarliestIsNullWhenCreatingRequest";

	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * rideStarttime is null
	 * 
	 */
	public static final String CreateRequestFailure_RideStartTimeLatestNull_Str="RideStarttimeLatestIsNullWhenCreatingRequest";
	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * rideStarttimeEarliest is greater than rideStarttimeLatest
	 * 
	 */
	public static final String CreateRequestFailure_RideStartTimeLatestBeforeEarliest="RideStarttimeLatestBeforeEarliestWhenCreatingRequest";
	
	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * ride startpoint address is null.
	 */
	public static final String CreateRequestFailure_StartPointAddressNull_Str="StartpointAddressIsNullWhenCreatingRequest";
	
	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * ride endpoint address is null.
	 */
	public static final String CreateRequestFailure_EndpointAddressNull_Str="EndpointAddressIsNullWhenCreatingRequest";
	
	
	
	/**
	 * "ErrorCode" to be returned if request creation fails because the planning
	 * horizon is exceeded
	 */
	public static final String REQUESTCREATION_HORIZONEXCEEDED_Error_Str = "PlanningHorizonExceededWhenCreatingRequest";
	
	/**
	 * "ErrorCode" to be returned if spatial bounds are exceeded in a generic context
	 */
	public static final String SPATIAL_BOUNDS_EXCEEDED="SpatialBoundsExceededForPoint";
	
	/**
	 * "ErrorCode" to be returned if spatial bounds are exceeded for a startpoint of an offer or request
	 */
	public static final String SPATIAL_BOUNDS_EXCEEDED_FOR_STARTPOINT="SpatialBoundsExceededForStartPoint";
	
	
	/**
	 * "ErrorCode" to be returned if spatial bounds are exceeded for an endpoint of an offer or request
	 */
	public static final String SPATIAL_BOUNDS_EXCEEDED_FOR_ENDPOINT="SpatialBoundsExceededForStartPoint";
	
	
	
	
	
	
	
}
