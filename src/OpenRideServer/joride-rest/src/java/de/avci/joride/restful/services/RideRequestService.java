package de.avci.joride.restful.services;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.avci.joride.restful.converters.RideRequestDTOConverter;
import de.avci.joride.restful.dto.requests.RideRequestDTO;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;
import de.fhg.fokus.openride.rides.rider.RiderUndertakesRideEntity;


/** Restful Service for manipulating ride offers
 * 
 * @author jochen
 *
 */


@Path("request")
@Produces("text/json")

public class RideRequestService extends AbstractRestService {
	
	
	
	private ObjectMapper jacksonMapper = new ObjectMapper();
	
	private RideRequestDTOConverter requestConverter=new RideRequestDTOConverter();
	
	
	
	/** Get a list of all ride Offers for respective user
	 * 
	 * @param request
	 * @return
	 */
	
	
	@GET
	@Path("allRideRequests")
	public String listRideOffers(@Context HttpServletRequest request){
		
		
		LinkedList<RiderUndertakesRideEntity> entities=this.lookupRiderUndertakesRideControllerBean().getAllRides();
		
		LinkedList<RideRequestDTO>  res =new LinkedList <RideRequestDTO> ();
		
		// convert list of entities to list of DTOs
		for(RiderUndertakesRideEntity entity:entities){
			res.add(requestConverter.rideRequestDTO(entity));
		}
		
		// return list as json
		try { return jacksonMapper.writeValueAsString(res);
		} catch (JsonProcessingException exc) {
			throw new Error(exc);
		}
	}
	

}
