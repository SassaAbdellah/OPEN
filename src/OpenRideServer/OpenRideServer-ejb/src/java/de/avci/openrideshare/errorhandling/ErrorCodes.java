package de.avci.openrideshare.errorhandling;

import java.util.Vector;



/**
 * Unified set of error codes and error strings to be used with
 * OpenRideShareExceptions.
 * 
 * Also, it is capable of mapping errorcodes to errorstrings
 * 
 * 
 * 
 * @author jochen
 *
 */
public class ErrorCodes {

	/**
	 * ErrorStrings are maintained in an index list, Error codes are the indexes
	 * of the respective list
	 */
	private static Vector<String> errorStrings = new Vector<String>();

	/**
	 * Mnemonic String for Unknown Error. See also corresponding Error Code
	 * UnknownErrorCode:
	 */
	public static final String UnknownError_Str = "UnknownError";

	static {
		errorStrings.add(UnknownError_Str);
	}

	/**
	 * Mnemonic numerical Code for Unknown Error. See also corresponding Error
	 * String UnknownErrorStr:
	 */

	public static final Integer UnknownError_Code = errorStrings
			.indexOf(UnknownError_Str);
	// add errorcode/errorstring pair
	
	
	
	
	/**
	 * Error Code signifying that the Customer for which an action is performed
	 * does not exist
	 */
	public static final String UserDoesNotExistError_Str = "UserDoesNotExist";
	static {
		errorStrings.add(UserDoesNotExistError_Str);
	}

	/**
	 * Error Code signifying that the Customer for which an action is performed
	 * does not exist
	 */
	public static final Integer UserDoesNotExistError_Code = errorStrings.indexOf(UserDoesNotExistError_Str);

	
	
	
	///////////////////////////////////////////////////////////////////////
	///
	/// Errors adding Customers
	///
	//////////////////////////////////////////////////////////////////////

	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * terms&conditions not acceppted.
	 */
	public static final String CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str = "Terms of use not accepted when creating customer";
	static {
		errorStrings.add(CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str);
	}

	/**
	 * "ErrorCode" to be returned if customer creation failed. Because of
	 * terms&conditions not acceppted.
	 */
	public static final int CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Code = UserDoesNotExistError_Code + 1;

	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * nickname not compliant to sysntax rules
	 */
	public static final String CUSTCREATION_NICKNAME_SYNTAX_Error_Str = "Nickname Syntax check failed when creating customer";
	static {
		errorStrings.add(CUSTCREATION_NICKNAME_SYNTAX_Error_Str);
	}

	/**
	 * "ErrorCode" to be returned if customer creation failed. Because of
	 * nickname not compliant to sysntax rules
	 */
	public static final int CUSTCREATION_NICKNAME_SYNTAX_Error_Code = errorStrings.indexOf(CUSTCREATION_NICKNAME_SYNTAX_Error_Str);

	/**
	 * "String" to be returned if customer creation failed. Because of email not
	 * compliant to syntax rules
	 */
	public static final String CUSTCREATION_EMAIL_SYNTAX_Error_Str = "Email Syntax check failed when creating customer";
	static {
		errorStrings.add(CUSTCREATION_EMAIL_SYNTAX_Error_Str);
	}

	/**
	 * "ErrorCode" to be returned if customer creation failed. Because of email
	 * not compliant to syntax rules
	 */
	public static final int CUSTCREATION_EMAIL_SYNTAX_Error_Code = errorStrings.indexOf(CUSTCREATION_NICKNAME_SYNTAX_Error_Str);

	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * nickname already exists
	 */
	public static final String CUSTCREATION_NICKNAME_EXISTS_Error_Str = "Nickname already exists when creating customer";
	static {
		errorStrings.add(CUSTCREATION_NICKNAME_EXISTS_Error_Str);
	}

	/**
	 * "ErrorCode" to be returned if customer creation failed. Because of
	 * nickname already exists
	 */
	public static final int CUSTCREATION_NICKNAME_EXISTS_Error_Code = errorStrings.indexOf(CUSTCREATION_NICKNAME_EXISTS_Error_Str);

	/**
	 * "ErrorString" to be returned if customer creation failed. Because of
	 * email already exists.
	 */
	public static final String CUSTCREATION_EMAIL_EXISTS_Error_Str = "Email already exists when creating customer";
	static {
		errorStrings.add(CUSTCREATION_EMAIL_EXISTS_Error_Str);
	}
	/**
	 * "ErrorCode" to be returned if customer creation failed. Because of email
	 * already exists.
	 */
	public static final int CUSTCREATION_EMAIL_EXISTS_Error_Code = errorStrings.indexOf(CUSTCREATION_NICKNAME_EXISTS_Error_Str);

	
	
	

	
	
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
	public static final String OFFERCREATION_HORIZONEXCEEDED_Error_Str = "Planning horizon exceeded when creating offer";
	static {
		errorStrings.add(OFFERCREATION_HORIZONEXCEEDED_Error_Str);
	}
	/**
	 * "ErrorCode" to be returned if request creation fails because the planning
	 * horizon is exceeded
	 */
	public static final int OFFERCREATION_HORIZONEXCEEDED_Error_Code = errorStrings
			.indexOf(OFFERCREATION_HORIZONEXCEEDED_Error_Str);
	

	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * RideStartpoint is not set
	 */
	public static final String CreateOfferFailure_RideStartPointNull_Str="Creating Offer failed, Reason : rideStartpt is null";
	static{errorStrings.add(CreateOfferFailure_RideStartPointNull_Str);}
	
	
	/**
	 * Mnemonic Code Cannot create Offer: Creating Offer failed, Reason : rideStartpt is null
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_RideStartPointNull_Code= errorStrings
			.indexOf(
	CreateOfferFailure_RideStartPointNull_Str);
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * RideEndopoint is not set
	 */
	public static final String CreateOfferFailure_RideEndpointNull_Str="Creating Offer failed, Reason : rideEndpt is null";
	static { errorStrings.add(CreateOfferFailure_RideEndpointNull_Str);}
	
	/**
	 * Mnemonic code Cannot create Offer:  "Creating Offer failed, Reason : rideEndpt is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_RideEndpointNull_Code= errorStrings
			.indexOf(
	CreateOfferFailure_RideEndpointNull_Str);
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * Intermediate point are null
	 * 
	 */
	public static final String CreateOfferFailure_IntermediatePointsNull_Str="Creating Offer failed, Reason : intermediate points are null";
	static {errorStrings.add(CreateOfferFailure_IntermediatePointsNull_Str);}
	
	
	/**
	 * Mnemonic error code cannot create Offer: "Creating Offer failed, Reason : intermediate points are null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_IntermediatePointsNull_Code = errorStrings.indexOf(CreateOfferFailure_IntermediatePointsNull_Str);
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * rideStarttime is null
	 * 
	 */
	public static final String CreateOfferFailure_RideStartTimeNull_Str="Creating Offer failed, Reason : ride starttime is null";
	static {errorStrings.add(CreateOfferFailure_RideStartTimeNull_Str);}
    
	/**
	 * Mnemonic StrCannot create Offer: "Creating Offer failed, Reason : ride starttime is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_RideStartTimeNull_Code= errorStrings.indexOf(CreateOfferFailure_RideStartTimeNull_Str);
			
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * Ride acceptable detour in Minutes is null.
	 * 
	 */
	public static final String CreateOfferFailure_RideAcceptableDetourMinNull_Str="Creating Offer failed, Reason : ride acceptable Detour in Min is null";
	
	static {errorStrings.add(CreateOfferFailure_RideAcceptableDetourMinNull_Str);}
	
	/**
	 * Mnemonic StrCannot create Offer: "Creating Offer failed, Reason : ride acceptable Detour in Min is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_RideAcceptableDetourMinNull_Code= errorStrings
			.indexOf(
	CreateOfferFailure_RideAcceptableDetourMinNull_Str);
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * Ride acceptable detour in Meters is null
	 */
	public static final String CreateOfferFailure_Ride_RideAcceptableDetourMeterNull_Str="Creating Offer failed, Reason : ride acceptable Detour in Meter is null";

	static {errorStrings.add(CreateOfferFailure_Ride_RideAcceptableDetourMeterNull_Str);}
	
	/**
	 * Mnemonic StrCannot create Offer: "Creating Offer failed, Reason : ride acceptable Detour in Meter is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_Ride_RideAcceptableDetourMeterNull_Code= errorStrings
			.indexOf(
	CreateOfferFailure_Ride_RideAcceptableDetourMeterNull_Str);
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * ride acceptable detour in percent is null.
	 * 
	 */
	public static final String CreateOfferFailure_RideAcceptableDetourPercentNull_Str="Creating Offer failed, Reason : ride acceptable Detour in Percent is null";
	
	static { errorStrings.add(CreateOfferFailure_RideAcceptableDetourPercentNull_Str);}
			
	/**
	 * Mnemonic StrCannot create Offer: Creating Offer failed, Reason : ride acceptable Detour in Percent is null
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_RideAcceptableDetourPercentNull_Code= errorStrings.indexOf(CreateOfferFailure_RideAcceptableDetourPercentNull_Str);
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * ride startpoint address is null.
	 */
	public static final String CreateOfferFailure_StartPointAddressNull_Str="Creating Offer failed, Reason : startpoint Address is null";
	
	static {errorStrings.add(CreateOfferFailure_StartPointAddressNull_Str);}

	/**
	 * Mnemonic StrCannot create Offer: Creating Offer failed, Reason : startpoint Address is null
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_StartPointAddressNull_Code= errorStrings.indexOf(CreateOfferFailure_StartPointAddressNull_Str);
	
	
	
	/**
	 * Mnemonic StrCannot create Offer: Error string to be thrown if offer creation fails because 
	 * ride endpoint address is null.
	 */
	public static final String CreateOfferFailure_EndpointAddressNull_Str="Creating Offer failed, Reason : endpoint Address is null";
	
	static {errorStrings.add(CreateOfferFailure_EndpointAddressNull_Str);}
	
	
	/**
	 * Mnemonic StrCannot create Offer: "Creating Offer failed, Reason : endpoint Address is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateOfferFailure_EndpointAddressNull_Code= errorStrings.indexOf(CreateOfferFailure_EndpointAddressNull_Str);

	/**
	 * Mnemonic StrCannot create Offer: Maximum Number of open Offers for this
	 * user is exceeded. See also corresponding Error Code
	 * RequestLimitExceededError_Code.
	 */
	public static final String OfferLimitExceededError_Str = "OfferLimitExceeded";
	static {
		errorStrings.add(OfferLimitExceededError_Str);
	}

	/**
	 * Mnemonic StrCannot create Offer: Maximum Number of open Offers for this
	 * user is exceeded. See also corresponding Error String
	 * OfferLimitExceededError_Code.
	 */
	public static final Integer OfferLimitExceededError_Code = errorStrings.indexOf(OfferLimitExceededError_Str);

	
	
	
	
	

	
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
	static {
		errorStrings.add(RequestLimitExceededError_Str);
	}

	/**
	 * Mnemonic StrCannot create Request: Maximum Number of open Requests for
	 * this user is exceeded. See also corresponding Error String
	 * RequestLimitExceededError_Code.
	 */
	public static final Integer RequestLimitExceededError_Code = errorStrings
			.indexOf(RequestLimitExceededError_Str);

	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * RideStartpoint is not set
	 */
	public static final String CreateRequestFailure_RideStartPointNull_Str="Creating Request failed, Reason : rideStartpt is null";
	static{errorStrings.add(CreateRequestFailure_RideStartPointNull_Str);}
	
	
	/**
	 * Mnemonic Code Cannot create Request: Creating Request failed, Reason : rideStartpt is null
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateRequestFailure_RideStartPointNull_Code= errorStrings
			.indexOf(
	CreateRequestFailure_RideStartPointNull_Str);
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * RideEndopoint is not set
	 */
	public static final String CreateRequestFailure_RideEndpointNull_Str="Creating Request failed, Reason : rideEndpt is null";
	static { errorStrings.add(CreateRequestFailure_RideEndpointNull_Str);}
	
	/**
	 * Mnemonic code Cannot create Request:  "Creating Request failed, Reason : rideEndpt is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateRequestFailure_RideEndpointNull_Code= errorStrings
			.indexOf(
	CreateRequestFailure_RideEndpointNull_Str);
	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * rideStarttime is null
	 * 
	 */
	public static final String CreateRequestFailure_RideStartTimeEarliestNull_Str="Creating Request failed, Reason : ride starttime earliest is null";
	static {errorStrings.add(CreateRequestFailure_RideStartTimeEarliestNull_Str);}
    
	/**
	 * Mnemonic StrCannot create Request: "Creating Request failed, Reason : ride starttime is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateRequestFailure_RideStartTimeEarliestNull_Code= errorStrings.indexOf(CreateRequestFailure_RideStartTimeEarliestNull_Str);

	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * rideStarttime is null
	 * 
	 */
	public static final String CreateRequestFailure_RideStartTimeLatestNull_Str="Creating Request failed, Reason : ride starttime latest is null";
	static {errorStrings.add(CreateRequestFailure_RideStartTimeLatestNull_Str);}
    
	/**
	 * Mnemonic StrCannot create Request: "Creating Request failed, Reason : ride starttime is null"
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateRequestFailure_RideStartTimeLatestNull_Code= errorStrings.indexOf(CreateRequestFailure_RideStartTimeLatestNull_Str);

	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * ride startpoint address is null.
	 */
	public static final String CreateRequestFailure_StartPointAddressNull_Str="Creating Request failed, Reason : startpoint Address is null";
	
	static {errorStrings.add(CreateRequestFailure_StartPointAddressNull_Str);}

	/**
	 * Mnemonic StrCannot create Request: Creating Request failed, Reason : startpoint Address is null
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateRequestFailure_StartPointAddressNull_Code= errorStrings.indexOf(CreateRequestFailure_StartPointAddressNull_Str);
	
	
	
	/**
	 * Mnemonic StrCannot create Request: Error string to be thrown if Request creation fails because 
	 * ride endpoint address is null.
	 */
	public static final String CreateRequestFailure_EndpointAddressNull_Str="Creating Request failed, Reason : endpoint Address is null";
	
	static {errorStrings.add(CreateOfferFailure_EndpointAddressNull_Str);}
	
	/**
	 * Mnemonic StrCannot create Request: Creating Request failed, Reason : endpoint Address is null
	 * See also corresponding Error String
	 * 
	 */
	public static final Integer CreateRequestFailure_EndPointAddressNull_Code= errorStrings.indexOf(CreateOfferFailure_EndpointAddressNull_Str);
	
	
	
	
	
	/**
	 * "ErrorCode" to be returned if request creation fails because the planning
	 * horizon is exceeded
	 */
	public static final String REQUESTCREATION_HORIZONEXCEEDED_Error_Str = "Planning horizon exceeded when creating Request";
	static {
		errorStrings.add(REQUESTCREATION_HORIZONEXCEEDED_Error_Str);
	}

	/**
	 * "ErrorCode" to be returned if request creation fails because the planning
	 * horizon is exceeded
	 */
	public static final int REQUESTCREATION_HORIZONEXCEEDED_Error_Code = errorStrings
			.indexOf(REQUESTCREATION_HORIZONEXCEEDED_Error_Str);


	
	// **********************************************************************
	// **********************************************************************
	// ***
	// *** Errors when creating Requests
	// ***
	// **********************************************************************
	// **********************************************************************
	

	/**
	 * get ErrorStr for numerical ErrorCode, or null if there is no such thing
	 * as an errorcode.
	 *
	 * @param errorCode
	 * @return ErrorString for respective Code
	 */
	public static String getErrorStr(Integer errorCode) {
		
		return errorStrings.elementAt(errorCode);
	}

	/**
	 * get numerical ErrorCode for mnemonic Error String, or null if there is no
	 * such thing as an errorcode.
	 *
	 * @param errorCode
	 * @return ErrorString for respective Code
	 */
	public static Integer getErrorCode(String errorStr) {
		return errorStrings.indexOf(errorStr);
	}

}
