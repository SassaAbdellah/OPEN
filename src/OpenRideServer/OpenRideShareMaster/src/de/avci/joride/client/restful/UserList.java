package de.avci.joride.client.restful;

import java.util.Hashtable;

import de.avci.joride.restful.converters.CustomerDTOConverter;


/** Create List of all Users from DTO
 * 
 * @author jochen
 *
 */


public class UserList {
	
	
	/** Table mapping id to nickname
	 * 
	 */
	private Hashtable<Integer,String> id2nick=new Hashtable<Integer,String>();
	
	/** Table mapping nickname to id
	 * 
	 */
	private Hashtable<String, Integer> nick2id=new Hashtable<String,Integer>();
	
	
	
	
	/** Initialize Maps from list of UserDTOs, as obtained from 
	 *  calling findall on users.
	 * 
	 * @param userDTOList
	 */
	public UserList (String userDTOList){
		
		
		CustomerDTOConverter customerConverter=new CustomerDTOConverter();
		
		
		
		
	}
	
	
}
