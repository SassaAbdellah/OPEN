package de.avci.openride.tools.imagecreation.imagewriting;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;

import de.avci.openride.tools.imagecreation.constants.OpenRideColors;
import de.avci.openride.tools.imagecreation.constants.OpenRideFonts;

public class OpenRideTabDrawer {
	
	
	/** Top Margin for Tab class images
	 */
	public static final int MARGIN_TOP=7;
	
	
	/** Margin left for Tab class images
	 */
	public static final int MARGIN_LEFT=5;
	
	
	/** Margin right for Tab class images
	 */
	public static final int MARGIN_RIGHT=5;
	
	
	/** Background image for Tab 0, black
	 */
	public static final String TEMPL_TAB_0_BLACK="resources/OriginalImages/OpenRideServerRS/templates/62x35/tab0blacktempl.png";
	
	
	
	/** Background image for Tab 0, green
	 */

	public static final  String TEMPL_TAB_0_GREEN="resources/OriginalImages/OpenRideServerRS/templates/62x35/tab0greentempl.png";


	
	/** Background image for Tab 1, white
	 */

	public static final  String TEMPL_TAB_1_WHITE="resources/OriginalImages/OpenRideServerRS/templates/78x41/tab1whitetempl.png";


	
	/** Background image for Tab 1, green
	 */

	public static final  String TEMPL_TAB_1_GREEN="resources/OriginalImages/OpenRideServerRS/templates/78x41/tab1greentempl.png";

	
	
	/** Background image for Tab 1, wide, white
	 */

	public static final  String TEMPL_TAB_1_WIDE_WHITE="resources/OriginalImages/OpenRideServerRS/templates/104x41/tab1whitetemplate_wide.png";


	
	/** Background image for Tab 1, wide, green
	 */

	public static final  String TEMPL_TAB_1_WIDE_GREEN="resources/OriginalImages/OpenRideServerRS/templates/104x41/tab1greentempl_wide.png";


	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param font    The Font to be used 
	 * @param text    The Text to be displayed
	 * @param width   The width of the viewport
	 * @param height  The height of the viewport (currently ignored, may become relevant with vertical directions of scripture)
	 * 
	 * 
	 * @return
	 */
	protected Rectangle calculateBounds(Font font, String text, int width, int height){
		 
			BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			g2.setFont(font);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			AttributedString aString=new AttributedString(text);
			aString.addAttribute( TextAttribute.FONT, font);
		    aString.addAttribute(TextAttribute.FONT,font,0,text.length());
			
			AttributedCharacterIterator styledText = aString.getIterator();
		   
			
		     FontRenderContext frc = g2.getFontRenderContext();
		     LineBreakMeasurer measurer = new LineBreakMeasurer(styledText, frc);
		 
		     
		     Point pen=new Point(MARGIN_LEFT,MARGIN_TOP);
		     
		     
		     float maxWidthFloat=0;
		     
		     while (measurer.getPosition() < styledText.getEndIndex()) {

		         TextLayout layout = measurer.nextLayout(width-MARGIN_LEFT-MARGIN_RIGHT);
		        
		         maxWidthFloat=Math.max(maxWidthFloat,layout.getAdvance());
		         
		         pen.y += (layout.getAscent());
		         
		         float dx = 0f;
		         
		         if(layout.isLeftToRight()) {
		        	 dx=0;
		          } else {
		        	 dx=(width - layout.getAdvance());
		          }
		         
		         layout.draw((Graphics2D) g2, pen.x + dx, pen.y);
		         pen.y += layout.getDescent() + layout.getLeading();
		     }
		     
		     
		     
		     int maxWidth=new Float(maxWidthFloat).intValue();
		     int maxHeight=new Float(pen.y).intValue();
		     
		     
		     System.err.println();
		     System.err.println("=======================");
		     System.err.println("maxWidth : "+maxWidth);
		     System.err.println("maxHeight : "+maxHeight);
		     System.err.println("=======================");
		    
		     return new Rectangle(0,0,maxWidth,maxHeight);
		 
	 }   
	    
	       


	
	
	
	/**  Create a new Image
	 *               
	 *            
	 * @param backgroundimage  background image 
	 *            
	 * @param textcolor color in which the text is to be displayed      
	 *            
	 * @param text   text to b e displayed
	 * 
	 * 
	 * @return The image in form of a buffered image object.
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws FileNotFoundException
	 */
	protected BufferedImage drawBufferedImage(
		
			String backgroundimage, 
			Color textcolor, 
			String text)
			throws FileNotFoundException, FontFormatException, IOException {

		//
		// Start with Background Image 
		//
		
		System.err.println("loading backgroundimage "+backgroundimage);
		
		BufferedImage	bi = ImageIO.read(new File(backgroundimage));
	

		
		int imgWidth=bi.getWidth();
		int imgHeight=bi.getHeight();
		
		
		
		Graphics2D g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Font font = new OpenRideFonts().loadDefaultFont();
		
		// rescale the font to fit image bounds

		font=autoscaleFont(font, text, imgWidth, imgHeight );
		g2.setFont(font);
		
		
		
	
		//
		// calculate Offset so that text will be drawn
		// horizontally and vertically centered
		//

		Rectangle2D bounds = calculateBounds(font, text, imgWidth, imgHeight);
		
		
		System.err.println();
		System.err.println("width         : " + imgWidth);
		System.err.println("Bounds.witdh  : "
				+ new Double(bounds.getWidth()).intValue());
		System.err.println("height        : " + imgHeight);
		System.err.println("Bounds.heigth : "
				+ new Double(bounds.getHeight()).intValue());
		System.err.println();
		
		
			//
			// create the foreground
			//
			g2.setColor(textcolor);
			drawText(g2,font, text, textcolor, MARGIN_LEFT, MARGIN_TOP, imgWidth);

			//
			//
			return bi;
		

	}
	
	
	/** Shrink the given Font, until the text fits into
	 *  the  boundaries given by imageWidth and imageHeight 
	 *  parameters
	 * 
	 * @param defaultFont
	 * @param imageWidth
	 * @param imageHeight
	 * @param text
	 * @return
	 */
	protected Font autoscaleFont(
			Font inputFont, 
			String text,
			int imageWidth, 
			int imageHeight		
	
	){
		
		System.err.println("autoscaling font");
	
		Font myfont=inputFont;
		float fontsizeOld=myfont.getSize2D();
	
		Rectangle2D bounds=calculateBounds(myfont, text, imageWidth, imageHeight);
		
		System.err.println("autoscale-width : "+bounds.getWidth()+" to "+imageWidth);
		System.err.println("autoscale-heigth : "+bounds.getHeight()+" to "+imageHeight);
		
		
		
		// shrink fonts until text fits into the bounds
		
		while(
				(bounds.getHeight() >= imageHeight-MARGIN_LEFT-MARGIN_RIGHT)
				||
				(bounds.getWidth() >= imageWidth-MARGIN_LEFT-MARGIN_RIGHT)
		){
			
			float fontsize=myfont.getSize2D()*(OpenRideFonts.AUTOSCALE_STEP);
			
			
			if(fontsize>=fontsizeOld){throw new Error("cannot shrink fontsize any more");}
						
			System.err.println("shrhinking fontsize : "+fontsize);
			
			
			myfont=myfont.deriveFont(fontsize);
			bounds=calculateBounds(myfont, text, imageWidth, imageHeight);
		}
		
		
		return myfont;
		
	} // autoscaleFont(...)
	
	
	
	/**
	 * 
	 * 
	 * 
	 * @param g2
	 * @param text
	 * @param xoffset
	 * @param offset
	 * @param width
	 */
	protected void drawText(
			Graphics2D g2,
			Font font,
			String text,
			Color textColor,
			int xoffset, 
			int yoffset, 
			int width){
		
		//
		// create the foreground
		//
		g2.setColor(textColor);
		
		
	
		 
		 AttributedString aString=new AttributedString(text);
		 
		 aString.addAttribute( TextAttribute.FONT, font);
		 aString.addAttribute(TextAttribute.FONT,font,0,text.length());
		 
		 AttributedCharacterIterator styledText= aString.getIterator();
		 
		 
		 
		 
		 FontRenderContext frc = g2.getFontRenderContext();
		 
		 
	     LineBreakMeasurer measurer = new LineBreakMeasurer(styledText, frc);
	     

	 	Point pen=new Point(MARGIN_LEFT,MARGIN_TOP);
	     int lineCount=0;
	     
	     while (measurer.getPosition() < styledText.getEndIndex()) {
	    	 
	    	 lineCount++;
	    	 System.err.println("Line : "+lineCount);

	         TextLayout layout = measurer.nextLayout(width-MARGIN_LEFT-MARGIN_RIGHT);
	     
	         System.err.println("Draw Text: Advance : "+layout.getAdvance());

	         pen.y += (layout.getAscent());
	         
	         float dx = layout.isLeftToRight() ? 0 : (width - layout.getAdvance());
	         
	         layout.draw((Graphics2D) g2, pen.x + dx, pen.y);
	         pen.y += layout.getDescent() + layout.getLeading();
	     }
	     
	}
	

	
	
	/** Draw an OpenRide Image of Class Tab0 (62x35), 
	 *  with black background and white text.
	 *   
	 * @param text - the text to be printed on the background
	 * 
	 * @return image in form of a buffered image
	 * 
	 * @throws FileNotFoundException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	
	public BufferedImage drawTab0Black(String text) throws FileNotFoundException, FontFormatException, IOException{
		
		return drawBufferedImage(
				TEMPL_TAB_0_BLACK, 
				Color.WHITE, 
				text
				);
		
	}
	
	
	/** Draw an OpenRide Image of Class Tab0 (62x35), 
	 *  with green background and white text.
	 * 
	 * 
	 *   
	 * @param text - the text to be printed on the background
	 * 
	 * @return image in form of a buffered image
	 * 
	 * @throws FileNotFoundException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	
public BufferedImage drawTab0Green(String text) throws FileNotFoundException, FontFormatException, IOException{
		
		return drawBufferedImage(
				TEMPL_TAB_0_GREEN, 
				Color.WHITE, 
				text
				);
	}
	
	


/** Draw an OpenRide Image of Class Tab1 (78x41), 
 *  with green background and white text.
 * 
 * 
 *   
 * @param text - the text to be printed on the background
 * 
 * @return image in form of a buffered image
 * 
 * @throws FileNotFoundException
 * @throws FontFormatException
 * @throws IOException
 */

public BufferedImage drawTab1Green(String text) throws FileNotFoundException, FontFormatException, IOException{
	
	return drawBufferedImage(
			TEMPL_TAB_1_GREEN, 
			Color.WHITE, 
			text
			);
}




/** Draw an OpenRide Image of Class Tab1 (78x41), 
 *  with white background and green text.
 * 
 * 
 *   
 * @param text - the text to be printed on the background
 * 
 * @return image in form of a buffered image
 * 
 * @throws FileNotFoundException
 * @throws FontFormatException
 * @throws IOException
 */


public BufferedImage drawTab1White(String text) throws FileNotFoundException, FontFormatException, IOException{
	
	return drawBufferedImage(
			TEMPL_TAB_1_WHITE, 
			OpenRideColors.openrideBackgroundGreen, 
			text
			);
}





/** Draw an OpenRide Image of Class Tab1_Wide (104x41), 
 *  with green background and white text.
 * 
 * 
 *   
 * @param text - the text to be printed on the background
 * 
 * @return image in form of a buffered image
 * 
 * @throws FileNotFoundException
 * @throws FontFormatException
 * @throws IOException
 */


public BufferedImage drawTab1GreenWide(String text) throws FileNotFoundException, FontFormatException, IOException{
	
	return drawBufferedImage(
			TEMPL_TAB_1_WIDE_GREEN, 
			Color.WHITE, 
			text
			);
}




/** Draw an OpenRide Image of Class Tab1_Wide (104x41), 
 *  with white background and green text.
 * 
 * 
 *   
 * @param text - the text to be printed on the background
 * 
 * @return image in form of a buffered image
 * 
 * @throws FileNotFoundException
 * @throws FontFormatException
 * @throws IOException
 */


public BufferedImage drawTab1WhiteWide(String text) throws FileNotFoundException, FontFormatException, IOException{
	
	return drawBufferedImage(
			TEMPL_TAB_1_WIDE_WHITE, 
			OpenRideColors.openrideBackgroundGreen, 
			text
			);
}


	

} // class 
