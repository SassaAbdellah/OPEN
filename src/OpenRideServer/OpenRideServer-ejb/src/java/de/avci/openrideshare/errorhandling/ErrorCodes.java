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
	public static final Integer OfferLimitExceededError_Code = errorStrings
			.indexOf(OfferLimitExceededError_Str);

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
	public static final Integer UserDoesNotExistError_Code = errorStrings
			.indexOf(UserDoesNotExistError_Str);

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
	public static final int CUSTCREATION_NICKNAME_SYNTAX_Error_Code = errorStrings
			.indexOf(CUSTCREATION_NICKNAME_SYNTAX_Error_Str);

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
	public static final int CUSTCREATION_EMAIL_SYNTAX_Error_Code = errorStrings
			.indexOf(CUSTCREATION_NICKNAME_SYNTAX_Error_Str);

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
	public static final int CUSTCREATION_NICKNAME_EXISTS_Error_Code = errorStrings
			.indexOf(CUSTCREATION_NICKNAME_EXISTS_Error_Str);

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
	public static final int CUSTCREATION_EMAIL_EXISTS_Error_Code = errorStrings
			.indexOf(CUSTCREATION_NICKNAME_EXISTS_Error_Str);

	// ////////////////////////////////////////////////////////
	//
	// Request creation errors
	//
	// ////////////////////////////////////////////////////////

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

	// ////////////////////////////////////////////////////////
	//
	// OFFER creation errors
	//
	// ////////////////////////////////////////////////////////

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
