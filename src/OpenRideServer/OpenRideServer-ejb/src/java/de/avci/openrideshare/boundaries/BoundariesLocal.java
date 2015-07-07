package de.avci.openrideshare.boundaries;

import javax.ejb.Local;

import org.postgis.Point;



/** Bean describing the boundaries and providing methods for checking
 *  that a point is located inside boundaries
 * 
 * @author jochen
 *
 */


@Local
public interface BoundariesLocal {
	
	
	/** @return Latitude in decimal degrees providing northern bound
	 */
	public Double getNorthernBound();
	/**  @return Longitude in decimal degrees providing eastern bound
	 */
	public Double getEasternBound();
	/** @return Latitude in decimal degrees providing southern bound
	 */
	public Double getSouthernBound();
	/** @return Longitude in decimal degrees  providing western bound
	 */
	public Double getWesternBound();
	
	
	/** Check, wether point is in area described by bounds.
	 */
	public boolean isWithinBounds(Point point);

}
