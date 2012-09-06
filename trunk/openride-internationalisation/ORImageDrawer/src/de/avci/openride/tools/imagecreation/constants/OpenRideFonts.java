package de.avci.openride.tools.imagecreation.constants;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;








/**
 * 
 * @author jochen
 *
 */
public class OpenRideFonts {

	
	
	
	/** File containing the TTF Font to use.
	 * 
	 */
	public static final String fontFileName = "resources/fonts/VeraBd.ttf";

	/** Size of the default font 
	 */
	public static final float DEFAULT_FONT_SIZE = 12;

	/** The scale parameter by which the font is schrinked in 
	 *  each step of the autoscale Process.
	 * 
	 * 
	 */
	public static final float AUTOSCALE_STEP = 0.98f;


	
	/**
	 * Load and return the default font to be used.
	 * 
	 * @return
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws FileNotFoundException
	 */
	public Font loadDefaultFont() throws FileNotFoundException,
			FontFormatException, IOException {

		Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(
				fontFileName));
		font = font.deriveFont(DEFAULT_FONT_SIZE);

		return font;
	}

}
