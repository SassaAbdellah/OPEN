package de.avci.joride.jbeans.customerprofile;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.avci.joride.session.HTTPUser;
import de.avci.joride.utils.HTTPUtil;
import de.avci.joride.utils.PropertiesLoader;

/**
 * Mnemonics for different searchtypes decfined in messages.properties:
 * 
 * RIDEREPORT_ALL_RIDES_FOR_DRIVER=Fahrer: Alle Fahrten
 * RIDEREPORT_UNRATED_RIDES_FOR_DRIVER=Fahrer: Unbewertete Fahrten
 * RIDEREPORT_ALL_RIDES_FOR_RIDER=Mitfahrer: Alle Fahrten
 * RIDEREPORT_REALIZED_RIDES_FOR_RIDER=Mitfahrer: Realisierte Fahrten
 * RIDEREPORT_UNRATED_RIDES_FOR_RIDER=Mitfahrer: Unbewertete Fahrten
 * 
 * 
 * 
 * @author jochen
 *
 */

@Named("searchType")
@RequestScoped
public class SearchType implements Serializable {

	/**
	 * Mnemonic for searching all rides for given driver
	 */
	public static final String RIDEREPORT_ALL_RIDES_FOR_DRIVER = "RIDEREPORT_ALL_RIDES_FOR_DRIVER";

	/**
	 * Mnemonic for searching all unrated rides for given driver
	 */
	public static final String RIDEREPORT_UNRATED_RIDES_FOR_DRIVER = "RIDEREPORT_UNRATED_RIDES_FOR_DRIVER";

	/**
	 * Mnemonic for searching all rides for given passenger.
	 */
	public static final String RIDEREPORT_ALL_RIDES_FOR_RIDER = "RIDEREPORT_ALL_RIDES_FOR_RIDER";

	/**
	 * Mnemonic for searching all realized rides for given passenger.
	 */
	public static final String RIDEREPORT_REALIZED_RIDES_FOR_RIDER = "RIDEREPORT_REALIZED_RIDES_FOR_RIDER";

	/**
	 * Mnemonic for searching all unrated rides for given passenger.
	 */
	public static final String RIDEREPORT_UNRATED_RIDES_FOR_RIDER = "RIDEREPORT_UNRATED_RIDES_FOR_RIDER";

	/**
	 * Key for triggering search
	 */
	private String searchKey;

	public String getSearchKey() {
		return this.searchKey;
	}

	/**
	 * Localized String describing what to search for.
	 */
	public String getDisplayString() {

		Locale locale = new HTTPUtil().detectBestLocale();
		Properties msgs = PropertiesLoader.getMessageProperties(locale);
		return (String) msgs.get(this.getSearchKey());
	}

	/** Return list of possible search types depending on customer and system's
	 * capabilities.
	 */
	public static List<SearchType> getAvaillableSearchtypes(JCustomerEntity customer) {

		List<SearchType> res = new LinkedList<SearchType>();

		// driver search depends on driver capability
		if (customer.getDriverCapability()) {
			res.add(new SearchType(RIDEREPORT_ALL_RIDES_FOR_DRIVER));
		}
		if (customer.getDriverCapability() && customer.getRatingCapability()) {
			res.add(new SearchType(RIDEREPORT_UNRATED_RIDES_FOR_DRIVER));
		}

		if (customer.getPassengerCapability()) {
			res.add(new SearchType(RIDEREPORT_ALL_RIDES_FOR_RIDER));
		}

		if (customer.getPassengerCapability()) {
			res.add(new SearchType(RIDEREPORT_REALIZED_RIDES_FOR_RIDER));
		}

		if (customer.getPassengerCapability() && customer.getRatingCapability()) {
			res.add(new SearchType(RIDEREPORT_UNRATED_RIDES_FOR_RIDER));
		}

		return res;
	}

	
	
	
	/**
	 * Create new SearchTypes with specified searchKey
	 * 
	 */
	public SearchType(String searchKey) {
		super();
		this.searchKey = searchKey;
	}

}
