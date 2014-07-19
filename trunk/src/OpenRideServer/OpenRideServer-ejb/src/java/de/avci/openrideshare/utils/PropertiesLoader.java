package de.avci.openrideshare.utils;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;

/**
 * convenience methods to load properties from well-known properties files
 *
 * @author jochen
 */
public class PropertiesLoader {

	
	/** A locale to use (may or may not be used)
	 */
	Locale locale=null;
	
	
	/** Construct loader without locale
	 * 
	 */
	public PropertiesLoader(){
		super();
	}
	
	/** Construct loader with special locale.
	 * 
	 * @param locale
	 */
    private PropertiesLoader(Locale locale) {
		super();
		this.locale=locale;
	}



    /**
     * Load a ressourcebundle from the classpath
     *
     * @param bundlename name of the bundle to use
     * 
     * @return
     */ 
    private ResourceBundle loadResourceBundleByName(String bundlename) {
    	
    	if(locale!=null){
    		return ResourceBundle.getBundle(bundlename, locale );
    	}else{
    		return ResourceBundle.getBundle(bundlename);
    	}
    }

    
    
    
    
    
    /**
     * Create Properties from a given RessourceBundle
     *
     * @param ResourceBundle rb
     * @return a Properties Object generated from RessourceBundl
     */
    private Properties getPropertiesFromRessourceBundle(ResourceBundle rb) {

        Properties res = new Properties();
        Enumeration<String> keys = rb.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            res.put(key, rb.getObject(key));
        }
        return res;
    }
   
    
    


    
    
    
    /** Hashtable providing messages for all supported locales
     * 
     */
    private static Hashtable <Locale,Properties> messageCache = new Hashtable <Locale,Properties> ();
  
    
    /**
     * Place where the messages_xx.properties files are located in the code
     */
    private static final String MESSAGES_URL = "de.avci.openrideshare.properties.messages";
    	
    /** getPro
     * 
     * @param locale
     * @return
     */
    private static Properties loadMessageProperties(Locale locale){
    	
    	Properties props=messageCache.get(locale);
    	
    	if(props==null){
    		  PropertiesLoader loader=new PropertiesLoader();
    		  ResourceBundle rb =loader.loadResourceBundleByName(MESSAGES_URL);
    	      props = loader.getPropertiesFromRessourceBundle(rb);
    	      messageCache.put(locale, props);
    	      
    	      if (props==null) {
    	    	  throw new Error("Cannot load Properties to Cache for Locale "+locale);
    	      }
    	}
    	return props;
    }
    
 
   
    
    
    
    /**
     * Place where the messages_xx.properties files are located in the code
     */
    private static final String SUPPORTED_LANGUAGES_URL = "de.avci.openrideshare.properties.supported";
    	
    /** getPro
     * 
     * @param locale
     * @return
     */
    private static Properties loadSupportedLanguagesProperties(Locale locale){
    	
    	Properties props=messageCache.get(locale);
    	
    	if(props==null){
    		  PropertiesLoader loader=new PropertiesLoader();
    		  ResourceBundle rb =loader.loadResourceBundleByName(SUPPORTED_LANGUAGES_URL);
    	      props = loader.getPropertiesFromRessourceBundle(rb);
    	      messageCache.put(locale, props);
    	      
    	      if (props==null) {
    	    	  throw new Error("Cannot load Properties to Cache for Locale "+locale);
    	      }
    	}
    	return props;
    }
    
    /** Return Messagess from Cache. If no such Properties are in the cache,
     *  load Properties to cache first, then return them.
     * 
     * @param locale
     * @return
     */
    public static Properties getMessageProperties(Locale locale){
    	return loadMessageProperties(locale);
    }
    
   
    
 
 
  
    
    
    
}
