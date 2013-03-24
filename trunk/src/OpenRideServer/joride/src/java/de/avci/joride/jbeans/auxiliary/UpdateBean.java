package de.avci.joride.jbeans.auxiliary;

import de.avci.joride.utils.PropertiesLoader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jochen
 */
@Named("updatemsg")
@SessionScoped
/**
 * JSF Bean to deliver Information about updated rides; this is to be called
 * from a poll in the toolbar
 *
 */
public class UpdateBean {

    /**
     * Standard nonstatic log
     */
    Logger log = Logger.getLogger("" + this.getClass());
    /**
     * Parameter Name for the parameter describing the number of miliseconds
     *
     */
    protected static final String ParamNameUpdateInterval = "updateInterval";
    /**
     * By default, update Interval is 60*1000 Milliseconds = 1 minute.
     *
     */
    private static long updateIntervalDefault = 6 * 1000;
    /**
     * Number of milliseconds between
     */
    protected Long updateInterval = updateIntervalDefault;

    /**
     * Initialize update period from properties
     *
     */
    public UpdateBean() {

        super();


    } // static initialization

    public Long getUpdateInterval() {


        if (this.updateInterval == null) {

            this.updateInterval = new Long(updateIntervalDefault);

            try {
                PropertiesLoader loader = new PropertiesLoader();
                String updateStr = "" + loader.getUpdateProps().get(ParamNameUpdateInterval);
                this.updateInterval = new Long(updateStr);
                
            } catch (Exception exc) {
                log.log(
                        Level.SEVERE,
                        "Unable to load updateInterval from Properties, using default " + updateIntervalDefault,
                        exc);
            }
            
        } // if (this.updateInterval == null) 


        return updateInterval;
    }

    /**
     * Make updateInterval in seconds availlable as a JSF Prop
     */
    public long getUpdateIntervalSec() {

        double d = (getUpdateInterval());
        return Math.round((d / 1000d));
    }
    
    
    private UpdateService updateService=new UpdateService();

    /**
     * 
     * Get a String describing updates
     *
     * @return
     */
    public String getUpdateNotification() {
            
        return updateService.getUpdateMessage();
    }
    
    
    
    
    
    
    
    
} // class
