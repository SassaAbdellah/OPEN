package de.avci.joride.jbeans.messages;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.avci.openrideshare.messages.Message;


@Named("message")
@RequestScoped

/** Adding Faces-Bean Capabilities to OpenRideServer-ejb Message Bean
 * 
 * @author jochen
 *
 */
public class JMessage extends Message implements Serializable  {

	/** Default serial id
	 */
	private static final long serialVersionUID = 1L;

	

}
