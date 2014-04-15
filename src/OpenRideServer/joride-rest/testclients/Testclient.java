import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.avci.joride.restful.dto.basic.LocationDTO;
import de.avci.joride.restful.dto.requests.RideRequestDTO;


public class Testclient {

	
	private static LocationDTO createStartLocation(){
		LocationDTO startLoc=new LocationDTO();
		startLoc.setName("Start Location");
		startLoc.setDescription("Description for StartLocation");
		startLoc.setLat(0.0001);
		startLoc.setLon(0.0001);
		return startLoc;
	}
	
	
	private static LocationDTO createEndLocation(){
		LocationDTO endLoc=new LocationDTO();
		endLoc.setName("End Location");
		endLoc.setDescription("Description for EndLocation");
		endLoc.setLat(0.0002);
		endLoc.setLon(0.0002);
		return endLoc;
	}
	
	
	
	
	public static  void main(String args[]) throws IOException{

	
		RideRequestDTO rideDTO=new de.avci.joride.restful.dto.requests.RideRequestDTO();
		
		rideDTO.setStartLocation(createStartLocation());
		rideDTO.setEndLocation(createEndLocation());
		
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		String serializedRequest=(mapper.writeValueAsString(rideDTO));
		System.out.println(serializedRequest);
		RideRequestDTO rideRequestDTO2=mapper.readValue(serializedRequest, RideRequestDTO.class);
		
		System.out.println(rideRequestDTO2.toString());
		
		
	}
	
	
}
