package de.avci.joride.restful.dto.basic;

/** Abstract Class for all DTOs wrapping entities (which have a unique Id)
 * 
 * @author jochen
 *
 */
public abstract class AbstractEntityDTO {
	
	/** Every DTO should have a nonfunctional Id
	 */
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	/** Bean constructor
	 */
	
	public AbstractEntityDTO(){}
}
