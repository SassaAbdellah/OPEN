package de.avci.openrideshare.translationutils;

import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import de.avci.openrideshare.utils.PropertiesLoader;
import de.avci.openrideshare.utils.SupportedLanguagesFactory;

/**
 * 
 * @author jochen
 *
 */
public class I18nUtil {

	private static Locale masterLocale = Locale.ENGLISH;

	private static Locale[] getSupportedLocales() {
		return SupportedLanguagesFactory.getSupportedLanguages();
	}

	private static Properties getServerPropertiesByLocale(Locale locale) {
		return PropertiesLoader.getMessageProperties(locale);
	}

	private static Properties getJoridePropertiesByLocale(Locale locale) {
		return de.avci.joride.utils.PropertiesLoader
				.getMessageProperties(locale);
	}

	/**
	 * Convert set of objects to set of strings by applying "toString" to each
	 * element.
	 * 
	 * @param objectSet
	 *            object sets to be converted to String
	 * 
	 * @return
	 * 
	 */
	private static TreeSet<String> objectSetToStringSet(Set objectSet) {

		TreeSet<String> res = new TreeSet<String>();

		for (Object o : objectSet) {
			res.add(o.toString());
		}

		return res;
	}

	/**
	 * Return the (asymmetric) difference, i.e elements contained in set a, but
	 * not in set b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private static TreeSet<String> difference(TreeSet<String> a,
			TreeSet<String> b) {

		TreeSet res = new TreeSet<String>();

		for (String ea : a) {
			if (!(b.contains(ea))) {
				res.add(ea);
			}
		}

		return res;
	}

	public static void main(String args[]) {

		
		//
		// Check subproject joride
		//
		
		// keys in messages_en.properties
		TreeSet<String> jorideMasterKeySet = objectSetToStringSet(getJoridePropertiesByLocale(masterLocale).keySet());
		//
		//
		// Iterate through all supported langs, and report was missing in each local properties file
		//
		System.out.println("==== checking for missing keys in joride subpoject ===");
		//
		for (Locale locale : getSupportedLocales()) {
			TreeSet<String> jorideLocalKeys = objectSetToStringSet(getJoridePropertiesByLocale(locale).keySet());
			TreeSet <String>  diff=difference(jorideMasterKeySet, jorideLocalKeys);
			
			String result=""+locale+" : ";
			
			if(diff.size()>0){
				result+=" missing keys :-( "+diff;
			} else {
				result+=" no missing keys :-)";
			}	
			System.out.println(result);
		}
		
		//
		//
		// Iterate through all supported langs, and report what is unused in each local properties file
		//
		System.out.println("==== checking for unused keys in joride subproject ===");
		//
		for (Locale locale : getSupportedLocales()) {
			TreeSet<String> jorideLocalKeys = objectSetToStringSet(getJoridePropertiesByLocale(locale).keySet());
			TreeSet <String>  diff=difference(jorideLocalKeys,jorideMasterKeySet);
			
			String result=""+locale+" : ";
			
			if(diff.size()>0){
				result+=" unused keys :-( "+diff;
			} else {
				result+=" no unused keys :-)";
			}	
			System.out.println(result);
		}
		
		
		// check what is missing in OpenRideServer-ejb properties
		TreeSet<String> serverMasterKeySet = objectSetToStringSet(getServerPropertiesByLocale(masterLocale).keySet());
		//
		//
		// Iterate through all supported langs, and report was missing in each local properties file
		//
		System.out.println("==== checking for missing keys in OpenRideServer-ejb subproject ===");
		//
		for (Locale locale : getSupportedLocales()) {
			TreeSet<String> serverLocalKeys = objectSetToStringSet(getServerPropertiesByLocale(locale).keySet());
			TreeSet <String>  diff=difference(serverMasterKeySet, serverLocalKeys);
			
			String result=""+locale+" : ";
			
			if(diff.size()>0){
				result+=" missing keys :-( "+diff;
			} else {
				result+=" no missing keys :-)";
			}	
			System.out.println(result);
		}
		
		//
		//
		// Iterate through all supported langs, and report what is unused in each local properties file
		//
		System.out.println("==== checking for unused keys in OpenRideServer-ejb subproject ===");
		//
		for (Locale locale : getSupportedLocales()) {
			TreeSet<String> serverLocalKeys = objectSetToStringSet(getServerPropertiesByLocale(locale).keySet());
			TreeSet <String>  diff=difference(serverLocalKeys,serverMasterKeySet);
			
			String result=""+locale+" : ";
			
			if(diff.size()>0){
				result+=" unused keys :-( "+diff;
			} else {
				result+=" no unused keys :-)";
			}	
			System.out.println(result);
		}
		
		
		
		
		
		
		
	}

}
