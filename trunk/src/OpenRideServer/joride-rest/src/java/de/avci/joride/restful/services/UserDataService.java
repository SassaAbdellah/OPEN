package de.avci.joride.restful.services;


import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.avci.joride.restful.converters.CustomerDTOConverter;
import de.avci.joride.restful.converters.MatchDTOConverter;
import de.avci.joride.restful.dto.customers.CustomerDTO;
import de.avci.joride.restful.dto.matches.MatchDTO;
import de.avci.joride.restful.services.AbstractRestService;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.matching.MatchEntity;

/** Restful service to query/add/delete CustomerEntity
 *  
 * @author jochen
 *
 */


@Path("customer")
@Produces("text/json")
public class UserDataService extends AbstractRestService {
	
	
	
private ObjectMapper jacksonMapper = new ObjectMapper();
	
	private CustomerDTOConverter customerConverter=new CustomerDTOConverter();
	
	
	
	/** Get a list of all customers
	 * 
	 * @param request
	 * @return
	 */
	
	
	@GET
	@Path("findAll")
	public String listCustomers(@Context HttpServletRequest request){
		
		
		List<CustomerEntity> entities=this.lookupCustomerControllerBean().getAllCustomers();
		
		LinkedList<CustomerDTO>  res =new LinkedList <CustomerDTO> ();
		
		// convert list of entities to list of DTOs
		for(CustomerEntity entity:entities){
			res.add(customerConverter.customerDTO(entity));
		}
		
		// return list as json
		try { return jacksonMapper.writeValueAsString(res);
		} catch (JsonProcessingException exc) {
			throw new Error(exc);
		}
	}
	
	

}
