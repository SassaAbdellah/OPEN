package de.fhg.fokus.openride.customerprofile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/** Method to support login with technologies other than User/password
 * 
 * 
 *       
 * 
 * @author jochen
 *
 */
public class CustomerUtils {

	/** Check if argument is an email adress.
	 *  For the time beeing, an email address is everything that 
	 *  does contain an ''
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isValidEmailAdress(String arg){
		
		
		if(arg!=null && (arg.contains("@"))) {
			return true;
		}
		
		return false;
	}
	
	
	/** Nicknames must begin with a (non-national) 
	 *  character [a-z] and add 
	 *  at least 4 and at most 11 alphanumerical characters,
	 *  so that there may at least 5 and at most 12 characters.  
	 * 
	 */
	public static String NICKNAME_PATTERN="[a-z]+[a-z0-9]{3,11}";
	
	/** Precompiled pattern for nicknames
	 */
	private static Pattern nicknamePattern=Pattern.compile(NICKNAME_PATTERN);
	
	/** Check if nickname is valid according to the rule set up before.
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isValidNickname(String arg){
	
        Matcher matcher = nicknamePattern.matcher(arg);
        boolean found = nicknamePattern.matcher(arg).matches();
        return found;
	}
	
	
	

	
	

}
