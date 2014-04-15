package de.avci.joride.restful.dto.basic;

/** Abstract Class for all DTOs
 * 
 * @author jochen
 *
 */
public abstract class AbstractDTO {
	
	/** Every DTO should have a nonfunctional Id
	 */
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	/** Bean constructor
	 */
	
	public AbstractDTO(){}
}
