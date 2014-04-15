package de.avci.joride.restful.converters;

import java.util.LinkedList;
import java.util.List;

import de.avci.joride.restful.dto.offers.RoutepointDTO;
import de.avci.joride.restful.dto.offers.WaypointDTO;
import de.fhg.fokus.openride.rides.driver.RoutePointEntity;
import de.fhg.fokus.openride.rides.driver.WaypointEntity;

/** Converter to convert Waypoint entities to WaypointDTOs and vice versa.
 * 
 * @author jochen
 *
 */
public class WaypointDTOConverter extends RoutepointDTOConverter {

	
	/** Convert WaypointEntity to WaypointDTO and vice versa.
	 * 
	 * @param entity
	 * @return
	 */
	public WaypointDTO waypointDTO(WaypointEntity entity){
		
		WaypointDTO res=new WaypointDTO();
		
		// waypointDTOs do not have ids!
		res.setLat(entity.getLatitude());
		res.setLon(entity.getLongitude());
		res.setRideId(entity.getRideId());
		res.setRouteIdx(entity.getRouteIdx());
		res.setDescription(entity.getDescription());
		
		return res;
	}
	
	
	/** Create List of WaypointsDTO from List of Waypoints
	 */

	public List <WaypointDTO> waypointDTOList(List <WaypointEntity> entities){
		
		List <WaypointDTO> res=new LinkedList <WaypointDTO>();
		
		for(WaypointEntity entity: entities){
			res.add(waypointDTO(entity));
		}
		return res;
	}
	
	
	
	
	/** Convert WaypointDTO to WaypointEntity
	 * 
	 * @param dto
	 * @return
	 */
	public WaypointEntity waypointEntity(WaypointDTO dto){
		
		WaypointEntity res=new WaypointEntity();
		
		// waypointDTOs do not have ids!
		res.setLatitude(dto.getLat());
		res.setLongitude(dto.getLon());
		res.setRideId(dto.getRideId());
		res.setRouteIdx(dto.getRouteIdx());
		res.setDescription(dto.getDescription());
		
		return res;
	}
	
	
}
