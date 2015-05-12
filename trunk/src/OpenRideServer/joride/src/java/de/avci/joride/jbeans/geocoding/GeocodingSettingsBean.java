package de.avci.joride.jbeans.geocoding;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import de.avci.openrideshare.utils.PropertiesLoader;


@ApplicationScoped
@Named("geocodingSettings")

public class GeocodingSettingsBean implements Serializable{

	/** Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	

	/** Name of operational Property governing which geocoding service to use.
	 */
	private static final String PropertyNameGeocoding="geocoding";
	
	/** Property value for geocoding signalling to use Nominatim service
	 */
	private static final String PropertyValueGeocodingNominatim="NOMINATIM";
	
	/** Property value for geocoding signalling to use Google v3 service
	 */
	private static final String PropertyValueGeocodingGoogle="GOOGLE";
	
	
	
	/** Name of operational Property governing which webmapping service to use.
	 */
	private static final String PropertyNameGeomapper="geomapper";
	
	/** Property value for geomapping signalling to use OSM webmapper service
	 */
	private static final String PropertyValueGeomapperOSM="OSM";
	
	/** Property value for geocoding signalling to use Google v3 service
	 */
	private static final String PropertyValueGeomapperGoogle="GOOGLE";
	
	
	/** Evaluate Operational Properties for PropertyNameGeomapper
	 * 
	 * @return
	 */
	public String getGeomapper(){
		return	PropertiesLoader.getOperationalProperties().getProperty(PropertyNameGeomapper);	
	};
	
	/** Evaluate Operational Properties for PropertyNameGeocoding
	 * 
	 * @return
	 */
	public String getGeocoding(){
		return	PropertiesLoader.getOperationalProperties().getProperty(PropertyNameGeocoding);	
	};
	
	
	
	/** True if geocoding parameter is equal to PropertyValueGeocodingNominatim
	 */
	public boolean getGeocodingIsNominatim(){
		return PropertyValueGeocodingNominatim.toUpperCase().equals(this.getGeocoding().toUpperCase());
	};
	
	/** True if geocoding parameter is equal to PropertyValueGeocodingGoogle
	 */
	public boolean getGeocodingIsGoogle(){
		return PropertyValueGeocodingGoogle.toUpperCase().equals(this.getGeocoding().toUpperCase());
	};
	
	/** True if geomapping parameter is equal to PropertyValueGeomappingOSM
	 */
	public boolean getGeomapperIsOSM(){
		return PropertyValueGeomapperOSM.toUpperCase().equals(this.getGeomapper().toUpperCase());
	};
	
	/** True if geomapping parameter is equal to PropertyValueGeomappingGoogle
	 */
	public boolean getGeomapperIsGoogle(){
		return PropertyValueGeomapperGoogle.toUpperCase().equals(this.getGeomapper().toUpperCase());
	};
	
}
