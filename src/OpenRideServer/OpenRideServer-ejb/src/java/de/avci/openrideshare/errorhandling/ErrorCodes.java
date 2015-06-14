package de.avci.openrideshare.errorhandling;

import java.util.Hashtable;
import java.util.Set;







/** Unified set of error codes and error strings to be used with OpenRideShareExceptions.
 *  
 * 
 * 
 * 
 * @author jochen
 *
 */
public class ErrorCodes{
	
	
	
	/** All Errorcodes are greater than this value
	 */
	public static Integer ERROR_CODE_MIN=1000;
	
	
	/** Map numerical error code to propertyStrings
	 */
	private static Hashtable <Integer,String> errorCodeToString = new Hashtable<Integer,String> ();
	
	/** Map property string to numerical Error code.
	 */
	private static Hashtable <String,Integer> errorStringToCode = new Hashtable<String,Integer> ();
	
	
	/** Mnemonic String for Unknown Error. 
	 *  See also corresponding Error Code UnknownErrorCode:
	 */
	
	public static final String UnknownError_Str="UnknownError";
	
	
	/** Mnemonic numerical Code for Unknown Error. 
	 *  See also corresponding Error String UnknownErrorStr:
	 */
	
	public static final Integer UnknownError_Code=ERROR_CODE_MIN+1;

	// add errorcode/errorstring pair
	static {
		errorCodeToString.put(UnknownError_Code, UnknownError_Str);
		errorStringToCode.put(UnknownError_Str, UnknownError_Code);
	}
	
	
	
	
	
	/**  Mnemonic StrCannot create Request: Maximum Number of open Requests for this user is exceeded.
	 *   See also corresponding Error Code RequestLimitExceededError_Code.
	 */
	public static final String RequestLimitExceededError_Str = "RequestLimitExceeded";
	

	/**  Mnemonic StrCannot create Request: Maximum Number of open Requests for this user is exceeded.
	 *   See also corresponding Error String RequestLimitExceededError_Code.
	 */
	public static final Integer RequestLimitExceededError_Code = UnknownError_Code+1;
	

	// add errorcode/errorstring pair
	static {
		errorCodeToString.put(RequestLimitExceededError_Code, RequestLimitExceededError_Str);
		errorStringToCode.put(RequestLimitExceededError_Str, RequestLimitExceededError_Code);
	}
	
	
	/**  Mnemonic StrCannot create Offer: Maximum Number of open Offers for this user is exceeded.
	 *   See also corresponding Error Code RequestLimitExceededError_Code.
	 */
	public static final String OfferLimitExceededError_Str = "OfferLimitExceeded";
	

	/**  Mnemonic StrCannot create Offer: Maximum Number of open Offers for this user is exceeded.
	 *   See also corresponding Error String OfferLimitExceededError_Code.
	 */
	public static final Integer OfferLimitExceededError_Code = RequestLimitExceededError_Code+1;

	
	
	// add errorcode/errorstring pair
	static {
		errorCodeToString.put(OfferLimitExceededError_Code, OfferLimitExceededError_Str);
		errorStringToCode.put(OfferLimitExceededError_Str, OfferLimitExceededError_Code);
	}
	
	
	/** Error Code signifying that the Customer for which an action is performed does not exist
	 */
	public static final String UserDoesNotExistError_Str = "UserDoesNotExist";
	

	/** Error Code signifying that the Customer for which an action is performed does not exist
	 */
	public static final Integer UserDoesNotExistError_Code = OfferLimitExceededError_Code+1;
	
	
	// add errorcode/errorstring pair
	static {
		errorCodeToString.put( UserDoesNotExistError_Code , UserDoesNotExistError_Str   );
		errorStringToCode.put( UserDoesNotExistError_Str  , UserDoesNotExistError_Code  );
	}
	

	/** "ErrorString" to be returned if customer creation failed.
	 *   Because of terms&conditions not acceppted. 
	 */
	public static final String CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str="Terms of use not accepted when creating customer";
	
	/** "ErrorCode" to be returned if customer creation failed.
	 *   Because of terms&conditions not acceppted. 
	 */
	public static final int CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Code= UserDoesNotExistError_Code+1;
	
	
	// add errorcode/errorstring pair
		static {
			errorCodeToString.put(  CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Code, CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str);
			errorStringToCode.put(  CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Str,  CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Code);
		}
	
	

	
	/** "ErrorString" to be returned if customer creation failed.
	 *   Because of nickname not compliant to sysntax rules
	 */
	public static final String CUSTCREATION_NICKNAME_SYNTAX_Error_Str="Nickname Syntax check failed when creating customer";
	
	
	/** "ErrorCode" to be returned if customer creation failed.
	 *   Because of nickname not compliant to sysntax rules
	 */
	public static final int CUSTCREATION_NICKNAME_SYNTAX_Error_Code=CUSTCREATION_TERMS_NOT_ACCEPTED_Error_Code+1;
	
	// add errorcode/errorstring pair
			static {
				errorCodeToString.put( CUSTCREATION_NICKNAME_SYNTAX_Error_Code , CUSTCREATION_NICKNAME_SYNTAX_Error_Str );
				errorStringToCode.put( CUSTCREATION_NICKNAME_SYNTAX_Error_Str  , CUSTCREATION_NICKNAME_SYNTAX_Error_Code );
			}
	
	
	
	
	/** "String" to be returned if customer creation failed.
	 *   Because of email not compliant to syntax rules
	 */
	public static final String CUSTCREATION_EMAIL_SYNTAX_Error_Str="Email Syntax check failed when creating customer";
	
	
	/** "ErrorCode" to be returned if customer creation failed.
	 *   Because of email not compliant to syntax rules
	 */
	public static final int CUSTCREATION_EMAIL_SYNTAX_Error_Code=CUSTCREATION_NICKNAME_SYNTAX_Error_Code+1;
	
	
	// add errorcode/errorstring pair
			static {
				errorCodeToString.put( CUSTCREATION_EMAIL_SYNTAX_Error_Code, CUSTCREATION_EMAIL_SYNTAX_Error_Str );
				errorStringToCode.put( CUSTCREATION_EMAIL_SYNTAX_Error_Str, CUSTCREATION_EMAIL_SYNTAX_Error_Code );
			}
	
	
	
	
	
	
	
	/** "ErrorString" to be returned if customer creation failed.
	 *   Because of nickname already exists
	 */
	public static final String CUSTCREATION_NICKNAME_EXISTS_Error_Str="Nickname already exists when creating customer";
	
	
	/** "ErrorCode" to be returned if customer creation failed.
	 *   Because of nickname already exists
	 */
	public static final int CUSTCREATION_NICKNAME_EXISTS_Error_Code=CUSTCREATION_EMAIL_SYNTAX_Error_Code+1;
	
	
	// add errorcode/errorstring pair
			static {
				errorCodeToString.put( CUSTCREATION_NICKNAME_EXISTS_Error_Code, CUSTCREATION_NICKNAME_EXISTS_Error_Str  );
				errorStringToCode.put( CUSTCREATION_NICKNAME_EXISTS_Error_Str, CUSTCREATION_NICKNAME_EXISTS_Error_Code );
			}
	
	
	/** "ErrorString" to be returned if customer creation failed.
	 *   Because of email already exists.
	 */
	public static final String CUSTCREATION_EMAIL_EXISTS_Error_Str="Email already exists when creating customer";
	
	
	/** "ErrorCode" to be returned if customer creation failed.
	 *   Because of email already exists.
	 */
	public static final int CUSTCREATION_EMAIL_EXISTS_Error_Code=CUSTCREATION_NICKNAME_EXISTS_Error_Code+1;
	
	
	// add errorcode/errorstring pair
			static {
				errorCodeToString.put( CUSTCREATION_EMAIL_EXISTS_Error_Code, CUSTCREATION_EMAIL_EXISTS_Error_Str );
				errorStringToCode.put( CUSTCREATION_EMAIL_EXISTS_Error_Str, CUSTCREATION_EMAIL_EXISTS_Error_Code );
			}
	
	
	
	
	
	
	
	
	
	
	/** get ErrorStr for numerical ErrorCode, or null if there is no such thing as an errorcode.
	 *
	 * @param errorCode 
	 * @return ErrorString for respective Code
	 */
	public static String getErrorStr(Integer errorCode){
		
		return errorCodeToString.get(errorCode);
	}
	
	
	/** get numerical ErrorCode for mnemonic Error String, or null if there is no such thing as an errorcode.
	 *
	 * @param errorCode 
	 * @return ErrorString for respective Code
	 */
	public static Integer getErrorCode(String errorStr){	
		return errorStringToCode.get(errorStr);
	}
	
	
	
	/** Get all known Error Strings
	 * 
	 * @return
	 */
	public static Set<String> getAllErrorStrings(){
		return errorStringToCode.keySet();
	}
	
	
	/** Get all known Numerical Error Codes
	 * 
	 * @return
	 */
	public static Set<Integer> getAllErrorCodes(){
		return errorCodeToString.keySet();
	}
	
	
	/** Get a a String representation of the errorcode->ErrorString mapping
	 */
	public static String errorCodeToErrorStringMapping(){
		
		Set <Integer> keys=getAllErrorCodes();
		
		String res="\n";
		
		for(Integer k:keys){ res+="\n"+k+" -> "+getErrorStr(k);}
		
		res+="\n";
		
		return res;
	}

	
	
}


