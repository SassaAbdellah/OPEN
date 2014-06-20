package de.avci.joride.restful.services;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.avci.joride.restful.dto.matches.MatchDTO;
import de.fhg.fokus.openride.matching.MatchEntity;


/** Restful Service for manipulating messages
 * 
 * @author jochen
 *
 */


@Path("messages")
@Produces("text/json")
public class MessageService extends AbstractRestService {

	
	@GET
	@Path("findAll")
	public String listRideOffers(@Context HttpServletRequest request){
		
		
		return ""+this.lookupMessageControllerBean().findAllMessages().size();
	}
	
	
	

}
