package de.avci.joride.session;

import java.util.regex.Pattern;

/**
 * An Utility Class that tries to tell wether or not a given String is a valid
 * email Adress.
 *
 * @author jochen
 */
public class EmailCheck {

    /** Regexp Pattern for valid RFC 822 email-adresses
     */
    public static final String EmailPatternStr = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

    /** Compiled Regexp Pattern for valid RFC 822 email-adresses
     */
    public static final Pattern EmailPattern = Pattern.compile(EmailPatternStr);

    /** Checks if argument is a valid email adress
     *
     * @param arg String to be checked
     * 
     * @return true if arg is a valid email adress, else false
     */
    public static boolean emailCheck(String arg) {
        return EmailPattern.matcher(arg).matches();
    }
    
    
} // class
