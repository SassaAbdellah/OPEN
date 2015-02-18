package de.avci.openrideshare.units;

import java.util.Hashtable;
import java.util.Set;


/** Units for measuring length, i.e meter, kilometers, miles and support 
 *  for converting to and from meters.
 *  Note that all internal calculations are all done in meters. 
 *   
 * 
 * @author jochen
 *
 */
public class LengthUnit {

	/** Short Name, e.g: KILOMETER for Kilometer, MILE for Mile, METER for Meter
	 * 
	 */
	private String key;
	
	/** Quotient. Gives the number of meters in that Unit.
	 *  E.g: One mile is 1609.344m, thus factor is 1609.344.
	 * 
	 */
	private Double factorToMeters;


	/**
	 * 
	 */
	private LengthUnit(String key, Double factorToMeters ){
		
		this.setKey(key);
		this.setFactorToMeters(factorToMeters);
		
	}

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}



	public Double getFactorToMeters() {
		return factorToMeters;
	}



	public void setFactorToMeters(Double factorToMeters) {
		this.factorToMeters = factorToMeters;
	}
	

	
	/** A hashtable mapping shortNames to LengthUnits, e.g:
	 *  
	 *      KILOMETER
	 *      METER
	 *    	MILE 
	 *    
	 */
	private static Hashtable<String,LengthUnit> unitTable; 
	

	
	/** Mnemonic key for METER
	 */
	public static final String KEY_METER="METER";
	
	/** Mnemonic constant for meter. One meter is 1m :-)
	 */
	public static final LengthUnit METER=new LengthUnit(KEY_METER, 1d);
	
	
	
	/** Mnemonic key for KILOMETER
	 */
	public static final String KEY_KILOMETER="KILOMETER";
	
	/** Mnemonic constant for kilometer. One kilometer is 1000m
	 */
	private static final LengthUnit KILOMETER=new LengthUnit(KEY_KILOMETER, 1000d);
	
	
	/** Mnemonic key for MILE
	 */
	public static final String KEY_MILE="MILE";
	
	/** Mnemonic constant for mile ( more precisely: statute mile). One statute mile is 1609.34 meters.
	 */
	private static final LengthUnit MILE=new LengthUnit(KEY_MILE, 1609.34d);
	
	static { // build the unit table 
		unitTable=new Hashtable<String,LengthUnit>();
		
		unitTable.put(METER.getKey()     , METER     );
		unitTable.put(MILE.getKey()      , MILE     );
		unitTable.put(KILOMETER.getKey() , KILOMETER);
	}
	
	
	/** Get supported lengthUnit by key. Returns null if there is no supported
	 *  unit for this key.
	 * 
	 * @param shortName
	 * @return
	 */
	public static final LengthUnit getLengthUnitByKEYString (String key){
		return unitTable.get(key);
	}
	
	
	/** Set containing all supported keys
	 * 
	 */
	
	public static final Set <String> supportedKeys(){
		return unitTable.keySet();
	}

}
