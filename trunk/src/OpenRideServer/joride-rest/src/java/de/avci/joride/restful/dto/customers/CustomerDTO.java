package de.avci.joride.restful.dto.customers;


/** DTO to describe a CustomerEntity
 * 
 * @author jochen
 *
 */
public class CustomerDTO {
	
	/** Customer's id
	 */
	private Integer id;
	/** Customer's Nickname
	 */
	private String nickname;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	

}
