package de.avci.joride.restful.testclient;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import de.avci.joride.client.restful.RestClient;

/**
 * Testclient for restclient. Used to automate actions which are then to be
 * executed by the testclient.
 * 
 * @author jochen
 * 
 */

public class Testclient {

	private static RestClient restClient = new RestClient();

	/**
	 * TODO: remove this
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * 
	 */

	public static void main(String arg[]) throws ClientProtocolException,
			URISyntaxException, IOException {

		RestClient restClient = new RestClient();
		restClient.setHostname("localhost");
		restClient.setPort(8080);
		restClient.setBasepath("/joride-rest/jax-rs/");

		/*
		 * // ping System.out.println("===================================");
		 * System.out.println(getJSON("ping/", null));
		 * System.out.println("===================================");
		 */

		/*
		 * // list of all ride offers
		 * System.out.println("===================================");
		 * System.out.println(getJSON("offer/allRideOffers", null));
		 * System.out.println("===================================");
		 */

		/*
		 * // ride offer by id
		 * System.out.println("===================================");
		 * System.out.println(getJSONObjectById("offer", 53951));
		 * System.out.println("===================================");
		 */

		/*
		 * // ride request by id
		 * System.out.println("===================================");
		 * System.out.println(getJSONObjectById("request",53965));
		 * System.out.println("===================================");
		 */

		/*
		 * String offerJSON =
		 * "{\"id\":null,\"customerId\":24852,\"acceptableDetourKM\":10,\"startTime\":1498858780000,\"startLocation\":{\"lon\":11.3666579,\"lat\":53.2764929,\"name\":null,\"address\":\"Forsthof Glaisin, Lindenstraße, Ludwigslust, Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland, Europäische Union\"},\"endLocation\":{\"lon\":11.4969985748317,\"lat\":53.4097714,\"name\":null,\"address\":\"UFAT Bildungswerk, Wöbbelin, Ludwigslust-Land, Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland, Europäische Union\"},\"routePoints\":null,\"drivePoints\":null,\"wayPoints\":[],\"offeredSeatsNo\":1,\"comment\":\"Ein Kommentar zum Angebot\"}"
		 * ;
		 * 
		 * System.out.println("=== posting offer ================================"
		 * ); restClient.postJSON("offer", offerJSON);
		 * System.out.println("=== done with posting offer ======================"
		 * );
		 */

		// list of all users
		System.out.println("===================================");
		System.out.println(restClient.getJSON("customer/findAll", null));
		System.out.println("===================================");

	}

}
