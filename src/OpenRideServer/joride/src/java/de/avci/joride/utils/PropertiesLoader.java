package de.avci.joride.utils;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;

/**
 * convenience methods to load properties from well-known properties files
 *
 * @author jochen
 */
public class PropertiesLoader {

    /**
     * Load a ressourcebundle from the classpath
     *
     * @param bundlename
     * @return
     */
    protected ResourceBundle getResourceBundleByName(String bundlename) {

        return ResourceBundle.getBundle(bundlename);
    }

    /**
     * Create Properties from a given RessourceBundle
     *
     * @param ResourceBundle rb
     * @return a Properties Object generated from RessourceBundl
     */
    Properties getPropertiesFromRessourceBundle(ResourceBundle rb) {

        Properties res = new Properties();
        Enumeration<String> keys = rb.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            res.put(key, rb.getObject(key));
        }
        return res;
    }
    /**
     * Where the navigation.properties file is located in the code
     */
    public static final String NAVIGATION_URL = "de.avci.joride.navigation";

    /**
     * Load the Navigation Properties
     *
     * @return the Navigation Properties as
     */
    public Properties getNavigationProps() {

        ResourceBundle rb = getResourceBundleByName(NAVIGATION_URL);
        Properties props = getPropertiesFromRessourceBundle(rb);
        return props;
    }
    /**
     * Where the messages.properties file is located in the code
     */
    public static final String MESSAGES_URL = "de.avci.joride.messages";

    /**
     * Load the Messages Properties
     *
     * @return the Messages Properties as
     */
    public Properties getMessagesProps() {

        ResourceBundle rb = getResourceBundleByName(MESSAGES_URL);
        Properties props = getPropertiesFromRessourceBundle(rb);
        return props;
    }
    
    
    /**
     * Where the navigation.properties file is located in the code
     */
    public static final String OPERATIONAL_URL = "de.avci.joride.operational";

    /**
     * Load the Operational Properties
     *
     */
    public Properties getOperationalProps() {

        ResourceBundle rb = getResourceBundleByName(OPERATIONAL_URL);
        Properties props = getPropertiesFromRessourceBundle(rb);
        return props;
    }
}
