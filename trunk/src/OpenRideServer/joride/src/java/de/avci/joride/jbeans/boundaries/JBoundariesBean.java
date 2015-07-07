package de.avci.joride.jbeans.boundaries;

import javax.inject.Named;

import org.postgis.Point;



@Named("boundaries")

/** JSF Bean bringing boundaries checking to JSF.
 *  Probably not of much use except for developers.
 * 
 * @author jochen
 *
 */
public class JBoundariesBean  {
	
	JBoundariesService jBoundariesService=new JBoundariesService();
	
	
	public double getNorthernBound(){
		return jBoundariesService.getNorthernBound();
	}
	
	public double getEasternBound(){
		return jBoundariesService.getEasternBound();
	}
	
	public double getSouthernBound(){
		return jBoundariesService.getSouthernBound();
	}
	
	public double getWesternBound(){
		return jBoundariesService.getWesternBound();
	}
	
	
	public boolean isWithinPoint(Point p){
		
		return jBoundariesService.isWithinPoint(p);
		
	}
	
	
	
	
}
