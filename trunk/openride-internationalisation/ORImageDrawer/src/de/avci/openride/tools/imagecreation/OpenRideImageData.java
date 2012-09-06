package de.avci.openride.tools.imagecreation;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;


/** Class that has all the Data necessary 
 *  to draw an Image Button for OpenRide:
 * 
 * 
 * 
 * 
 * @author jochen
 *
 */
public class OpenRideImageData {
	
	
	
	
	/** Every Image must have an ImageClass that knows how
	 *  to draw the Image.
	 * 
	 */
	protected OpenRideImageClass imageClass;
	
	
	/** Text to be displayed on the Image 
	 */
	protected String text;
	
	/** Witdh of this Image.
	 * (Only Images of class generic Button need width and height,
	 *  images of tabxy class know about this a priori )
	 */
	protected Integer width=null;
	
	
	/** Witdh of this Image.
	 * (Only Images of class generic Button need width and height,
	 *  images of tabxy class know about this a priori )
	 */
	protected Integer height=null;
	
	
	/** The basename of the file 
	 *  (basic filename without  language suffix and png suffix)
	 *  into which the Image will be drawn.
	 */
	protected String baseName;
	
	
	
	
	
	
	/** Draws the Button or Tab associated with this 
	 *  imageData Object and returns it to the caller
	 *  
	 *  
	 * @return
	 * @throws IOException 
	 * @throws FontFormatException 
	 * @throws FileNotFoundException 
	 */
	BufferedImage getImage() throws FileNotFoundException, FontFormatException, IOException{
		
		return this.getImageClass().draw(this.getText(), this.getWidth(), this.getHeight());
	}


	public OpenRideImageClass getImageClass() {
		return imageClass;
	}


	public String getText() {
		return text;
	}


	public String getBaseName() {
		return baseName;
	}


	public Integer getWidth() {
		return width;
	}


	public Integer getHeight() {
		return height;
	}
	
	
	public OpenRideImageData(
			String baseName,
			String text,
			OpenRideImageClass oriClass,
			Integer width,
			Integer height
			
	) {
		super();
		this.baseName=baseName;
		this.text=text;
		this.imageClass=oriClass;
		this.width=width;
		this.height=height;
	}
	
	
	/** Get an Info on this Object.
	 * 
	 * @return a String describing this Object's properties
	 */
	public String info(){
		
		return 
		"\n"+
		"\nbaseName   : "+this.getBaseName()+
		"\nimageClass : "+this.getImageClass().getImgClassName()+
		"\nwidth      : "+this.getWidth()+
		"\nheight     : "+this.getHeight()+
		"\ntext       : "+this.getText()+
		"\n"+
		"\n";
		
	
	}
	
	

} // class
