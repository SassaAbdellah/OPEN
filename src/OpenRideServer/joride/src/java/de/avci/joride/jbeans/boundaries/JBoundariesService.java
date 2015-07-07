package de.avci.joride.jbeans.boundaries;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.postgis.Point;

import de.avci.openrideshare.boundaries.BoundariesLocal;


/** provides access to simple boundary checks
 * 
 * @author jochen
 *
 */
public class JBoundariesService {
	
	BoundariesLocal boundariesBean=this.lookupBoundariesBean();

	private BoundariesLocal lookupBoundariesBean() {
	
	        try {
		            javax.naming.Context c = new InitialContext();
		            return (BoundariesLocal) c.lookup("java:global/OpenRideServer/OpenRideServer-ejb/BoundariesBean!de.avci.openrideshare.boundaries.BoundariesLocal");
		        } catch (NamingException ne) {
		            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught when loading boundaries bean", ne);
		            throw new RuntimeException(ne);
		        }
		    }
	
	
	/** Latitude for northern bound in EPSG 3068, aka WGS 84
	 */
	public double getNorthernBound(){
		return boundariesBean.getNorthernBound();
	}
	
	/** Latitude for southern bound in EPSG 3068, aka WGS 84
	 */
	public double getSouthernBound(){
		return boundariesBean.getSouthernBound();
	}
	
	/** Longitude for easthern bound in EPSG 3068, aka WGS 84
	 */
	public double getEasternBound(){
		return boundariesBean.getEasternBound();
	}
	
	/** Longitude for southern bound in in EPSG 3068, aka WGS 84
	 */
	public double getWesternBound(){
		return boundariesBean.getWesternBound();
	}
	
	
	/** 
	 * 
	 * @param p point to be tested for being contained. Point is expected to be in in EPSG 3068, aka WGS 84
	 * @return
	 */
	
	public boolean isWithinPoint(Point p){
		
		return boundariesBean.isWithinBounds(p);
	}
	
}
