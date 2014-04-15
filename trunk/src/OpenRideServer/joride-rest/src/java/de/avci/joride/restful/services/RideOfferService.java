package de.avci.joride.restful.services;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.avci.joride.restful.converters.RideOfferDTOConverter;
import de.avci.joride.restful.dto.offers.RideOfferDTO;
import de.fhg.fokus.openride.rides.driver.DriverUndertakesRideEntity;


/** Restful Service for manipulating ride offers
 * 
 * @author jochen
 *
 */


@Path("offer")
@Produces("text/json")

public class RideOfferService extends AbstractRestService {
	
	
	
	private ObjectMapper jacksonMapper = new ObjectMapper();
	
	private RideOfferDTOConverter offerConverter=new RideOfferDTOConverter();
	
	
	
	/** Get a list of all ride Offers for respective user
	 * 
	 * @param request
	 * @return
	 */
	
	
	@GET
	@Path("allRideOffers")
	public String listRideOffers(@Context HttpServletRequest request){
		
		
		LinkedList<DriverUndertakesRideEntity> entities=this.lookupDriverUndertakesRideControllerBean().getAllDrives();
		
		LinkedList<RideOfferDTO>  res =new LinkedList <RideOfferDTO> ();
		
		// convert list of entities to list of DTOs
		for(DriverUndertakesRideEntity entity:entities){
			res.add(offerConverter.rideOfferDTO(entity));
		}
		
		// return list as json
		try { return jacksonMapper.writeValueAsString(res);
		} catch (JsonProcessingException exc) {
			throw new Error(exc);
		}
		
		
	}
	

}
