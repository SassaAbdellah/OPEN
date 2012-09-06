package de.avci.openride.tools.imagecreation.imagewriting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Class for writing images to png
 * 
 * @author jochen
 *
 */
public class ImageWriter {
	
	
	/** Write the image to a file 
	 * 
	 * @param filename the name of the file where the image should be written to.
	 * @param image  the image to be written
	 * @throws IOException
	 */
	
	public void writePNG(String filename, BufferedImage image) throws IOException{
		//
		// set the content type, get the output stream and print image as PNG
		//
		ImageIO.write(image, "PNG", new File(filename));
	}
	
	
	

}
