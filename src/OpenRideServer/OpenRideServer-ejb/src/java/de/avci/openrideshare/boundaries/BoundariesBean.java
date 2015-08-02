package de.avci.openrideshare.boundaries;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import org.postgis.Point;

import de.avci.openrideshare.utils.OperationalPropertiesConstants;
import de.avci.openrideshare.utils.PropertiesLoader;

@Stateless

public class BoundariesBean implements BoundariesLocal {
	
	static Logger log=Logger.getLogger(BoundariesBean.class.getCanonicalName());
	
	
	
	private static Double northernBound;
	private static Double easternBound;
	private static Double southernBound;
	private static Double westernBound;
	
	
	
	
	
	
	
	// initialize data
	static {
	
		Properties op=PropertiesLoader.getOperationalProperties();
		
		String northStr=op.getProperty(OperationalPropertiesConstants.PROPERTY_NAME_northernBound);
		try{ northernBound=new Double(northStr);
		}catch(java.lang.NumberFormatException exc){
			northernBound=null;
			log.log(Level.SEVERE, "Cannot initialize northern bound from property value "+northStr);
		}
		
		String eastStr=op.getProperty(OperationalPropertiesConstants.PROPERTY_NAME_easternBound);
		try{ easternBound=new Double(eastStr);
		}catch(java.lang.NumberFormatException exc){
			easternBound=null;
			log.log(Level.SEVERE, "Cannot initialize eastern bound from property value "+eastStr);
		}
		
		String southStr=op.getProperty(OperationalPropertiesConstants.PROPERTY_NAME_southernBound);
		try{ southernBound=new Double(southStr);	
		}catch(java.lang.NumberFormatException exc){
			southernBound=null;
			log.log(Level.SEVERE, "Cannot initialize southern bound from property value "+southStr);
		}
		
		String westStr=op.getProperty(OperationalPropertiesConstants.PROPERTY_NAME_westernBound);
		try{ westernBound=new Double(westStr);
		}catch(java.lang.NumberFormatException exc){
			westernBound=null;
			log.log(Level.SEVERE, "Cannot initialize western bound from property value "+westStr);
		}
		
	}
	
	

	@Override
	public Double getNorthernBound() {
		return northernBound;
	}

	@Override
	public Double getEasternBound() {
		return easternBound;
	}

	@Override
	public Double getSouthernBound() {
		return southernBound;
	}

	@Override
	public Double getWesternBound() {
		return westernBound;
	}
	
	
	/** Auxiliary function. True, if argument  is between western and eastern bounds.
	 * 
	 * @param longitude
	 * @return
	 */
	public boolean isWithinLongitudeBounds(Double longitude){
		
		// if eastern bound is smaller then western bound, we cross the date border
		boolean crossesDateBorder=(this.getEasternBound()<this.getWesternBound());
		
		if(!crossesDateBorder){
			return longitude>=this.getWesternBound() && longitude<= this.getEasternBound();
		} else {
			return longitude<=this.getWesternBound() && longitude>= this.getEasternBound();
		}
	}
	
	
	
	/** Checks iff point's x-coordinate is within latitude bounds.
	 * 
	 * @param point
	 * @return
	 */
	public boolean isWithinLongitudeBounds(Point point){
				
		return isWithinLongitudeBounds(point.getX());
	}
	
	
	
	/** Auxiliary function. True, if argument interpreted as a latitude value is between western and eastern bounds.
	 * 
	 * @param latitude
	 * @return
	 */
	public boolean isWithinLatitudeBounds(double latitude){	
		
		return latitude<=this.getNorthernBound() && latitude >= this.getSouthernBound();
	}
	
	
	/** Auxiliary function. True, if point's X-component is between western and eastern bounds.
	 * 
	 * @param point
	 * @return
	 */
	public boolean isWithinLatitudeBounds(Point point){	
		
		return isWithinLatitudeBounds(point.getY());
	}
	
	@Override
	public boolean isWithinBounds(Point point) {
		return this.isWithinLongitudeBounds(point) && this.isWithinLatitudeBounds(point);
	}
	
}
