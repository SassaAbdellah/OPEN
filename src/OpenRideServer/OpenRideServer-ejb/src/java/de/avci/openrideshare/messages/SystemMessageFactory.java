package de.avci.openrideshare.messages;

import java.util.Date;

import de.fhg.fokus.openride.matching.MatchEntity;

/**
 * A factory to create System messages
 * 
 * @author jochen
 * 
 */
public class SystemMessageFactory {

	/**
	 * Create a list of matches reacting to either driver or rider accepting a
	 * reuest. That is: If driver has accepted a match, and rider has not yet
	 * accepted, then message is sent do rider If rider has accepted a match,
	 * and driver has not yet accepted, then message is sent to driver.
	 * 
	 * Finally, if both have accepted, then confirmation message is sent to
	 * both.
	 * 
	 * If none of the above cases is true (for ex, if match has already been
	 * rejected, no message is sent)
	 * 
	 * 
	 * 
	 * @return
	 */
	public static Message[] createMessagesOnAcceptance(MatchEntity m) {

		// send message to rider...
		if (m.getRiderState() == MatchEntity.NOT_ADAPTED
				&& m.getDriverState() == MatchEntity.ACCEPTED) {
			Message msgr = SystemMessageFactory
					.createSystemMessageDriverAcceptedNotification(m);
			Message res[] = new Message[1];
			res[0] = msgr;
			return res;
		}

		// send message to driver...
		if (m.getRiderState() == MatchEntity.ACCEPTED
				&& m.getDriverState() == MatchEntity.NOT_ADAPTED) {
			Message res[] = new Message[1];
			res[0] = SystemMessageFactory
					.createSystemMessageRiderAcceptedNotification(m);
			return res;
		}

		// send messages to both...
		if (m.getRiderState() == MatchEntity.ACCEPTED
				&& m.getDriverState() == MatchEntity.ACCEPTED) {
			Message res[] = new Message[2];
			res[0] = createSystemMessageRiderBothAcceptedNotification(m);
			res[1] = createSystemMessageDriverBothAcceptedNotification(m);
			return res;
		}

		// Default...
		return new Message[0];
	}

	/**
	 * Message telling the passenger that there is a new Match matching his
	 * request
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static Message createSystemMessageRiderNewMatch(MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject RiderNewMatch");
		res.setMessage("TODO : Dummy Subject RiderNewMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the driver that there is a new Match matching his offer
	 * 
	 * @param m
	 * 
	 * @return
	 */

	public static Message createSystemMessageDriverNewMatch(MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  DriverNewMatch");
		res.setMessage("TODO : Dummy Subject  DriverNewMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the passenger that a driver accepted his request
	 * 
	 * @param m
	 * 
	 * @return
	 */
	private static Message createSystemMessageDriverAcceptedNotification(
			MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  DriverAcceptedMatch");
		res.setMessage("TODO : Dummy Subject  DriverAcceptedMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the driver that a potential passenger accepted his offer
	 * 
	 * 
	 * 
	 * @param m
	 * 
	 * @return
	 */

	private static Message createSystemMessageRiderAcceptedNotification(
			MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  RiderAcceptedMatch");
		res.setMessage("TODO : Dummy Subject  RiderAcceptedMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the passenger that both parties accepted a request
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static Message createSystemMessageRiderBothAcceptedNotification(
			MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject BothAcceptedMatch");
		res.setMessage("TODO : Dummy Subject BothAcceptedMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the driver that both parties agreed on his offer
	 * 
	 * @param m
	 * 
	 * @return
	 */
	private static Message createSystemMessageDriverBothAcceptedNotification(
			MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject BothAcceptedMatch");
		res.setMessage("TODO : Dummy Subject BothAcceptedMatch");
		res.setRecipient(m.getDriverUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the passenger that driver countermanded an offer
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static Message createSystemMessageDriverCountermandedNotification(
			MatchEntity m) {

		Message res = new Message();
		res.setSender(null); // System message!
		res.setTimeStampCreated(new Date(System.currentTimeMillis()));
		res.setSubject("TODO : Dummy Subject  RiderNewMatch");
		res.setMessage("TODO : Dummy Subject  RiderNewMatch");
		res.setRecipient(m.getRiderUndertakesRideEntity().getCustId());
		res.setRequest(m.getRiderUndertakesRideEntity());
		res.setOffer(m.getDriverUndertakesRideEntity());

		return res;
	}

	/**
	 * Message telling the driver that a rider countermanded a request
	 * 
	 * @param m
	 * 
	 * @return
	 */

	public static Message createSystemMessageRiderCountermandedNotification(
			MatchEntity m) {

		Message res = new Message();
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
