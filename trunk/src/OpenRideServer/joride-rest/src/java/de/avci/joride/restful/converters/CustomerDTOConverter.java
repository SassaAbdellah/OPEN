package de.avci.joride.restful.converters;

import de.avci.joride.restful.dto.customers.CustomerDTO;
import de.fhg.fokus.openride.customerprofile.CustomerEntity;

public class CustomerDTOConverter {

	public CustomerDTO customerDTO(CustomerEntity entity) {
	
		CustomerDTO res=new CustomerDTO();
		res.setId(entity.getCustId());
		res.setNickname(entity.getCustNickname());
		
		return res;
	}

}
