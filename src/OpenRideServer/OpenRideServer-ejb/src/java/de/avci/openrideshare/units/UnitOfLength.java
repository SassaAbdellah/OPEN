package de.avci.openrideshare.units;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/** Units for measuring length, i.e meter, kilometers, miles and support 
 *  for converting to and from meters.
 *  Note that all internal calculations are all done in meters. 
 *   
 * 
 * @author jochen
 *
 */
public class UnitOfLength {

	/** Numerical key, one for each supported unit
	 * 
	 */
	private Integer key;
	
	/** Quotient. Gives the number of meters in that Unit.
	 *  E.g: One mile is 1609.344m, thus factor is 1609.344.
	 * 
	 */
	private Double factorToMeters;
	
	/** Key String is used to find the local name of the respective
	 *  Unit in the messages_xx.properties files from 118n.
	 * 
	 */
	private String keyString;


	/** Local name of the unit of length. (Singular)
	 * 
	 */
	private String localNameSingular;
	
	/** Local name of the unit of length. (Singular)
	 * 
	 */
	private String localNamePlural;
	
	
	/** Decimal format in which displaying this unit of length makes sense
	 */
	private String decimalFormatString;
	
	
	/** Number Format created from decimal format String
	 * 
	 * @return Decimal Format created from decimal format String
	 */
	public DecimalFormat createNumberFormat(){
		return new DecimalFormat(this.getDecimalFormatString());
	}
	
	
	
	
	public String getLocalNameSingular() {
		return localNameSingular;
	}

	public void setLocalNameSingular(String localName) {
		this.localNameSingular = localName;
	}

	
	
	public String getLocalNamePlural() {
		return localNamePlural;
	}

	public void setLocalNamePlural(String localName) {
		this.localNamePlural = localName;
	}

	
	
	
	
	

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	
	
	/** Key String to be used for getting labels in plural.
	 *  To do so, a capital "S" is concatenated to the keyString.
	 */
	public String getKeyStringPlural() {
		return getKeyString()+"S";
	}


	/**
	 * 
	 */
	private UnitOfLength(Integer key, Double factorToMeters, String keyString, String decimalFormat){
		
		this.setKey(key);
		this.setFactorToMeters(factorToMeters);
		this.setKeyString(keyString);
		this.setDecimalFormatString(decimalFormat);
	}

	public Integer getKey() {
		return key;
	}


	public void setKey(Integer key) {
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
	private static Hashtable<Integer,UnitOfLength> unitTable; 
	

	
	/** Mnemonic key for METER
	 */
	public static final Integer KEY_METER=1;
	
	/** Mnemonic key for METER
	 */
	public static final String KEYSTR_METER="METER";
	
	
	/** Mnemonic constant for meter. One meter is 1m :-)
	 */
	public static final UnitOfLength METER=new UnitOfLength(KEY_METER, 1d, KEYSTR_METER,"#0");
	
	
	
	/** Mnemonic key for KILOMETER
	 */
	public static final Integer KEY_KILOMETER=2;
	
	/** Mnemonic key for KILOMETER
	 */
	public static final String KEYSTR_KILOMETER="KILOMETER";
	
	
	/** Mnemonic constant for kilometer. One kilometer is 1000m
	 */
	public static final UnitOfLength KILOMETER=new UnitOfLength(KEY_KILOMETER ,1000d, KEYSTR_KILOMETER, "#0.0");
	
	
	/** Mnemonic key for MILE
	 */
	public static final Integer KEY_MILE=3;
	
	/** Mnemonic key for MILE
	 */
	public static final String KEYSTR_MILE="MILE";
	
	
	/** Mnemonic constant for mile ( more precisely: statute mile). One statute mile is 1609.34 meters.
	 */
	public static final UnitOfLength MILE=new UnitOfLength(KEY_MILE, 1609.34d, KEYSTR_MILE,"#0.0");
	
	
	
	static { // build the unit table 
		unitTable=new Hashtable<Integer,UnitOfLength>();
		
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
	public static final UnitOfLength getLengthUnitByKey (Integer key){
		return unitTable.get(key);
	}
	
	
	/** Set containing all (numerical) Keys for supported Units
	 */
	
	public static final Set <Integer> supportedKeys(){
		return unitTable.keySet();
	}
	
	/** Set containing all string valued Keys for supported Units
	 */
	
	public static final List <UnitOfLength> supportedUnitsOfLength(){
	
		LinkedList <UnitOfLength> res=new LinkedList<UnitOfLength>();
		
		for(Integer key : supportedKeys()){
			res.add(unitTable.get(key));
		}
		
		return res;
	}
	
		
	
	/** Raisin d'etre for this class is that distances measured
	 *  in meters in the backend get converted into 
	 *  userfriendly individual units.
	 *  
	 *  @param meters: length in meters to be converted
	 */
	public double meters2This(long meters){
		
		return this.getFactorToMeters()*meters;
	}

	public String getDecimalFormatString() {
		return decimalFormatString;
	}

	public void setDecimalFormatString(String arg) {
		this.decimalFormatString = arg;
	}


}
