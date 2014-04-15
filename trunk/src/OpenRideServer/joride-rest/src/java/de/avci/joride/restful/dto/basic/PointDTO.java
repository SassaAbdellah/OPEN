package de.avci.joride.restful.dto.basic;


/** Geographical Location having latitude and Longitude Coordinates.
 *  Anything else (Names, Descriptions ... are to be added by subclasses!)
 * 
 * @author jochen
 *
 */
public class PointDTO extends AbstractDTO {

	
	/** Longitude of Location
	 */
	private Double lon;
	
	/** Latitude of Location
	 * 
	 */
	private Double lat;
	
	
	/** Bean Constructor
	 */
	public PointDTO(){
	}


	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


	public Double getLon() {
		return lon;
	}


	public void setLon(Double lon) {
		this.lon = lon;
	}
	
}
