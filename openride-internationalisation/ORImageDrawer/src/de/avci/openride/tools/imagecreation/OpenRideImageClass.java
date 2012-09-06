package de.avci.openride.tools.imagecreation;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeSet;

import de.avci.openride.tools.imagecreation.imagewriting.OpenRideTabDrawer;
import de.avci.openride.tools.imagecreation.imagewriting.OpenRideGenericButtonDrawer;



/**
 * 
 * @author jochen
 *
 */
public class OpenRideImageClass {
	
	
	protected String imgClassName=null;

	
	
	public static final String IMG_CLASS_NAME_OR_GENERIC_BUTTON="GB";
	public static final String IMG_CLASS_NAME_OR_TAB0_BLACK="T0B";
	public static final String IMG_CLASS_NAME_OR_TAB0_GREEN="T0G";
	public static final String IMG_CLASS_NAME_OR_TAB1_GREEN="T1G";
	public static final String IMG_CLASS_NAME_OR_TAB1_WHITE="T1W";
	public static final String IMG_CLASS_NAME_OR_TAB1_WIDE_GREEN="T1GW";
	public static final String IMG_CLASS_NAME_OR_TAB1_WIDE_WHITE="T1WW";
	
	public static final TreeSet <String> KNOWN_IMAGE_CLASSES= new TreeSet <String> ();
 	
	/** Initialize the list of known Image Classes
	 * 
	 */
	{
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_GENERIC_BUTTON);
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_TAB0_BLACK);
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_TAB0_GREEN);
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_TAB1_GREEN);
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_TAB1_WHITE);
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_TAB1_WIDE_GREEN);
		KNOWN_IMAGE_CLASSES.add(IMG_CLASS_NAME_OR_TAB1_WIDE_WHITE);
		
		
		System.err.println("Initialized known Image Classes : "+KNOWN_IMAGE_CLASSES);	
	}

	
	
	/** Checks wether input parameter is in the list of known classes.
	 * 
	 * 
	 * @param imgclassname the name of the input class to be checked
	 * @return true, if 
	 */
	public boolean isKnownImageClass(String imgclassname){
		return KNOWN_IMAGE_CLASSES.contains(imgclassname);
	}
	
	
	
	
	public  BufferedImage draw( String text, Integer width , Integer height) throws FileNotFoundException, FontFormatException, IOException{
	
		
	
		
		
		if(!(this.isKnownImageClass(this.getImgClassName()))){
			throw new Error("No rule to draw image of class "+getImgClassName());
		}
		
		
		// 
		//
		//
		
		if(IMG_CLASS_NAME_OR_GENERIC_BUTTON.equals(imgClassName)){
			
		   OpenRideGenericButtonDrawer gd=new OpenRideGenericButtonDrawer();
			return gd.drawGenericOpenRideButton(width, height, text);
		}
		
		
		
		//  if the image is not a generic button, then it is a Tab
		//
		//
		
		OpenRideTabDrawer td=new OpenRideTabDrawer();
		
		// Black Tab0 
		if(IMG_CLASS_NAME_OR_TAB0_BLACK.equals(imgClassName)){
			return td.drawTab0Black(text);
		}
		
		// Green Tab0 
		if(IMG_CLASS_NAME_OR_TAB0_GREEN.equals(imgClassName)){
			return td.drawTab0Green(text);
		}
		
		
		// Green Tab1 
		if(IMG_CLASS_NAME_OR_TAB1_GREEN.equals(imgClassName)){
			return td.drawTab1Green(text);
		}
		
		// White Tab1 
		if(IMG_CLASS_NAME_OR_TAB1_WHITE.equals(imgClassName)){
			return td.drawTab1White(text);
		}
		
		// Green Tab1 WIDE 
		if(IMG_CLASS_NAME_OR_TAB1_WIDE_GREEN.equals(imgClassName)){
			return td.drawTab1GreenWide(text);
		}
		
		// White Tab1 WIDE
		if(IMG_CLASS_NAME_OR_TAB1_WIDE_WHITE.equals(imgClassName)){
			return td.drawTab1WhiteWide(text);
		}
		
	
		throw new Error("No method implemented to draw OpenRide Image of class "+imgClassName);
	}




	public String getImgClassName() {
		return imgClassName;
	}




	public void setImgClassName(String imgClassName) {
		this.imgClassName = imgClassName;
	}
	
	
	/** Create a new ImageClass Object with the specified imageClassName.
	 * 
	 * @param imageClassName
	 */
	public OpenRideImageClass(String imageClassName){
		
		super();
		
		if(!(isKnownImageClass(imageClassName))){
			throw new Error(
					"Cannot create OpenRideImageClass for class: "+imgClassName+"\n"+
					"argument not in "+KNOWN_IMAGE_CLASSES		
			);
		}
		this.setImgClassName(imageClassName);
	}
	
	
	
	
	
	
}