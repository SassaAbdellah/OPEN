package de.avci.joride.restful.dto.basic;


/** DTO for transporting Locations to/from  Server via JSON.
 *  
 *  Location adds human defined name and description to PointDTO
 *  
 * 
 * 
 * 
 * 
 * 
 * @author jochen
 *
 */
public class LocationDTO extends PointDTO {
	
	
	/** Mnemonic Name of the Location.
	 *  
	 *  Example: Tower 
	 */
	private String name;
	
	/** Human Readable description of the location
	 * 
	 *  Example: "The Tower of London"
	 * 
	 */
	private String description;
	
	
	
	
	
	/** Bean Constructor
	 */
	public LocationDTO(){
		super();
	}





	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	} 

}
