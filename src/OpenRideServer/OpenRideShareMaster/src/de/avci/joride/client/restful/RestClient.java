package de.avci.joride.client.restful;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient for restful Services provided by the joride-rest subproject.
 * Currently, this is used mainly for integration tests.
 * 
 * 
 * @author jochen
 * 
 */
public class RestClient {

	/**
	 * My client
	 */
	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	/**
	 * HOST to call for restful services
	 * 
	 * TODO: configure from local properties.
	 * 
	 */
	public static final String host = "localhost";

	/**
	 * Port to call for restful services
	 * 
	 * TODO: configure from local properties.
	 * 
	 */
	public static final Integer port = 8080;

	/**
	 * basic path on server where jersey servlet of joride-rest application is
	 * deployed.
	 * 
	 * TODO: this should be made configurable from local properties
	 */

	public static final String basepath = "/joride-rest/jax-rs/";

	/**
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getJSON(String path, HttpParams params)
			throws URISyntaxException, ClientProtocolException, IOException {

		try {
			URI uri = new URIBuilder().setScheme("http").setHost(host)
					.setPort(port).setPath(basepath + path).build();
			HttpGet httpget = new HttpGet(uri);

			if (params != null) {
				httpget.setParams(params);
			}

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}

			}; // ResponseHandler ends here

			return httpclient.execute(httpget, responseHandler);

		} finally {
			// do not close for repeated calls
			// httpclient.close();
		}

	}

	/**
	 * 
	 * @param path
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 */
	public static String getJSONObjectById(String path, Integer id)
			throws ClientProtocolException, URISyntaxException, IOException {

		String idPath = path + "/" + id;
		return getJSON(idPath, null);
	}

	/**
	 * Post a JSON String (to create a new Object)
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * 
	 */

	public static void postJSON(String path, String json)
			throws URISyntaxException, ClientProtocolException, IOException {

		CloseableHttpResponse response = null;

		try {
			URI uri = new URIBuilder().setScheme("http").setHost(host)
					.setPort(port).setPath(basepath + path).build();
			HttpPost httppost = new HttpPost(uri);
			StringEntity strEntity = new StringEntity(json);
			strEntity.setContentType("application/json");
			strEntity.setContentEncoding("utf8");
			httppost.setEntity(strEntity);
			response = httpclient.execute(httppost);
			
			System.out.println("status code : "+response.getStatusLine().getStatusCode());
			System.out.println("reason      : "+response.getStatusLine().getReasonPhrase());

		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

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

		String offerJSON = "{\"id\":null,\"customerId\":24852,\"acceptableDetourKM\":10,\"startTime\":1498858780000,\"startLocation\":{\"lon\":11.3666579,\"lat\":53.2764929,\"name\":null,\"address\":\"Forsthof Glaisin, Lindenstraße, Ludwigslust, Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland, Europäische Union\"},\"endLocation\":{\"lon\":11.4969985748317,\"lat\":53.4097714,\"name\":null,\"address\":\"UFAT Bildungswerk, Wöbbelin, Ludwigslust-Land, Ludwigslust-Parchim, Mecklenburg-Vorpommern, 19288, Deutschland, Europäische Union\"},\"routePoints\":null,\"drivePoints\":null,\"wayPoints\":[],\"offeredSeatsNo\":1,\"comment\":\"Ein Kommentar zum Angebot\"}";

		
		 System.out.println("=== posting offer ================================");
		 postJSON("offer", offerJSON);
		 System.out.println("=== done with posting offer ======================");
		
		
		
		
	}

}
