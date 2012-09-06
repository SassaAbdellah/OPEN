package de.avci.openride.tools.imagecreation.imagewriting;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.avci.openride.tools.imagecreation.constants.OpenRideColors;
import de.avci.openride.tools.imagecreation.constants.OpenRideFonts;

public class OpenRideGenericButtonDrawer {

	/**
	 * Calculate the Boundingbox that given Text may take in with a given Font.
	 * The Bounding box is returned as an Rectangle2D Object. The x and y
	 * coordinates can be happily ignored, while the width and height properties
	 * of the returned Rectangle give the dimension of the required bounding box
	 * 
	 * 
	 * @param text
	 *            the text to be displayed
	 * @param font
	 *            the font to be used
	 * 
	 * @return A Rectangle2D object, which width / heigtht properties can be
	 *         interpreted as the width and height required to display the given
	 *         text with the given font.
	 */

	protected Rectangle2D calculateBounds(String text, Font font) {

		// Calculate Bounds for Image

		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		FontRenderContext fc = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, fc);

		return bounds;
	}

	/**
	 * Create a new Generic OpenRide Button
	 * 
	 * 
	 * 
	 * 
	 * @param width
	 *            height of the button to be created
	 * 
	 * @param height
	 *            of the button to be created
	 * 
	 * 
	 * 
	 * @param textcolor
	 *            color in which the text is to be displayed
	 * 
	 * @param bgcolor
	 *            color in which the background is to be displayed
	 * 
	 * @param highlightcolor
	 *            color to be used as a gradient highlight in the background
	 * 
	 * 
	 * @param framecolor
	 *            color to be used for a frame around the button. If this is null, no frame will be drawn.
	 * 
	 * 
	 * @param text
	 *            text to be displayed
	 * 
	 * 
	 * @return The image in form of a buffered image object.
	 * 
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws FileNotFoundException
	 */
	public BufferedImage drawBufferedImage(int width, int height,
			Color textcolor, 
			Color backgroundcolor, 
			Color highlightcolor,
			Color framecolor,
			String text) throws FileNotFoundException, FontFormatException,
			IOException {

		//
		// create image either synthetically, or from backgroundimage
		//

		BufferedImage bi = null;

		bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = new OpenRideFonts().loadDefaultFont();

		// rescale the font to fit image bounds
		font = autoscaleFont(font, width, height, text);

		g2.setFont(font);

		this.drawBackgroundGradient(bi, backgroundcolor, highlightcolor,framecolor);

		//
		// calculate Offset so that text will be drawn
		// horizontally and vertically centered
		//

		Rectangle2D bounds = calculateBounds(text, font);
		int xoffset = new Double((width - bounds.getWidth()) / 2).intValue();
		int yoffset = new Double(height / 2 + bounds.getHeight() / 2)
				.intValue();

		System.err.println();
		System.err.println("width         : " + width);
		System.err.println("Bounds.witdh  : "
				+ new Double(bounds.getWidth()).intValue());
		System.err.println("xoffset       : " + xoffset);
		System.err.println("height        : " + height);
		System.err.println("Bounds.heigth : "
				+ new Double(bounds.getHeight()).intValue());
		System.err.println("yoffset       : " + yoffset);
		System.err.println();

		//
		// create the foreground
		//
		g2.setColor(textcolor);
		g2.drawString(text, xoffset, yoffset);

		//
		//

		return bi;

	}

	//  Draw a background Gradient as it is used in 
	// generic "green" Openride Buttons.
	 
	
	/**
	 * 
	 */
	protected void drawBackgroundGradient(
			
			BufferedImage bi,
			Color backgroundcolor, 
			Color highlightcolor,
			Color framecolor

	) {

		int width = bi.getWidth();
		int height = bi.getHeight();

		Graphics2D g = bi.createGraphics();

		// draw a solid background for a start
		g.draw(new Rectangle(0, 0, width, height));

		// now, add a gradient for highlighning

		GradientPaint gp;
		gp = new GradientPaint(0, 10, highlightcolor, 0, height,
				backgroundcolor, true);

		// Fill with the gradient.

		g.setPaint(gp);
		g.fill(new Rectangle(0, 0, width, height));

		// if framecolor is not null,
		// add a frame in framecolor
		if(framecolor!=null){
			g.setColor(framecolor);
	
			g.setBackground(null);
			g.draw(new Rectangle(0, 0, width -1, height -1 ));
		
			// blur the whole enchillada
			blur(bi);
			blur(bi);
		
		
			
		} //if framecolor!=null 
		
		
	} // method blur

	/**
	 * Shrink the given Font, until the text fits into the boundaries given by
	 * imageWidth and imageHeight parameters
	 * 
	 * @param defaultFont
	 * @param imageWidth
	 * @param imageHeight
	 * @param text
	 * @return
	 */
	protected Font autoscaleFont(Font inputFont, int imageWidth,
			int imageHeight, String text) {

		System.err.println("autoscaling font");

		Font font = inputFont;

		Rectangle2D bounds = calculateBounds(text, font);

		// shrink fonts until text fits into the bounds

		while ((bounds.getHeight() + 2 >= imageHeight)
				|| (bounds.getWidth() + 2 >= imageWidth)) {

			float fontsize = font.getSize2D() * (OpenRideFonts.AUTOSCALE_STEP);
			System.err.println("shrhinking fontsize : " + fontsize);

			font = font.deriveFont(fontsize);
			bounds = calculateBounds(text, font);
		}

		return font;

	} // autoscaleFont(...)

	/**
	 * Draw a generic Button for OpenRide with the standard values for
	 * Textcolor, backgroundColor and Gradient Highlight
	 * 
	 * 
	 * 
	 * @param width
	 * @param height
	 * @param text
	 * @return
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws FileNotFoundException
	 */
	public BufferedImage drawGenericOpenRideButton(int width, int height,
			String text) throws FileNotFoundException, FontFormatException,
			IOException {

		BufferedImage bi = this.drawBufferedImage(width, height, Color.WHITE,
				OpenRideColors.openrideBackgroundGreen,
				OpenRideColors.openrideGradientHighlightGreen,
				OpenRideColors.openrideFrameGreen,
				text);

		return bi;
	}

	/**
	 * Blurs the Image, using a 3x3 convolution image.
	 * 
	 * 
	 * @param biSrc
	 *            the image to be blurred
	 * 
	 * @return the blurred image
	 */
	protected void blur(BufferedImage biSrc) {

		// The convolution matrix
		//

		float data[] = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f,
				0.0625f, 0.125f, 0.0625f };

		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
				null);

		BufferedImage biCopy = deepCopy(biSrc);

		convolve.filter(biCopy, biSrc);

	}

	/**
	 * Make a deep copy of a buffered image
	 * 
	 * @param bi
	 *            Buffered Image to be copyied
	 * 
	 * 
	 * @return the cloned Image
	 */
	protected BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

} // class
