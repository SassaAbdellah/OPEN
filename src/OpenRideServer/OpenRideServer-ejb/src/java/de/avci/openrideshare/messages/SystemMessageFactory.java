package de.avci.openrideshare.messages;

import java.util.Date;

import de.fhg.fokus.openride.matching.MatchEntity;


/** A factory to create System messages
 * 
 * @author jochen
 *
 */
public class SystemMessageFactory {


	/** Message telling the passenger that there is a new Match matching his request
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static Message createSystemMessageRiderNewMatch(MatchEntity m) {
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject RiderNewMatch");
		res.setMessage("TODO : Dummy Subject RiderNewMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}


	/** Message telling the driver that there is a new Match matching his offer
	 * 
	 * @param m
	 * 
	 * @return
	 */

	public static Message createSystemMessageDriverNewMatch(MatchEntity m ){
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  DriverNewMatch");
		res.setMessage("TODO : Dummy Subject  DriverNewMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}



	/** Message telling the passenger that a driver accepted his request
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static Message createSystemMessageDriverAcceptedNotification(MatchEntity m) {
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  DriverAcceptedMatch");
		res.setMessage("TODO : Dummy Subject  DriverAcceptedMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}


	/** Message telling the driver that a potential passenger accepted his offer
	 * 
	 * @param m
	 * 
	 * @return
	 */
	
	public static Message createSystemMessageRiderAcceptedNotification(MatchEntity m) {
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  RiderAcceptedMatch");
		res.setMessage("TODO : Dummy Subject  RiderAcceptedMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}


/** Message telling the passenger that both parties accepted a request 
 * 
 * @param m
 * 
 * @return
 */
	public static Message createSystemMessageRiderBothAcceptedNotification(MatchEntity m) {

		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject BothAcceptedMatch");
		res.setMessage("TODO : Dummy Subject BothAcceptedMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}



/** Message telling the driver that both parties agreed on his offer
 * 
 * @param m
 * 
 * @return
 */
	public static Message createSystemMessageDriverBothAcceptedNotification(MatchEntity m) {
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject BothAcceptedMatch");
		res.setMessage("TODO : Dummy Subject BothAcceptedMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}



	/** Message telling the passenger driver countermanded an offer
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static Message createSystemMessageDriverCountermandedNotification(MatchEntity m) {
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  RiderNewMatch");
		res.setMessage("TODO : Dummy Subject  RiderNewMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}


	/** Message telling the driver that a rider countermanded a request
	 * 
	 * @param m
	 * 
	 * @return
	 */

	public static Message createSystemMessageRiderCountermandedNotification(MatchEntity m) {
		
		Message res=new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  RiderNewMatch");
		res.setMessage("TODO : Dummy Subject  RiderNewMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());
		
		return res;
	}
	
	

}
