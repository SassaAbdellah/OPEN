import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.avci.openride.tools.imagecreation.OpenRideImageClass;
import de.avci.openride.tools.imagecreation.imagewriting.ImageWriter;




/** Create a set of Testimages that , i.e:
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * @author jochen
 *
 */
public class TestimageCreator {
	

	
	
	
	/** Text to be printed
	 * 
	 */
	public static final String text="not initialized";
	
	
	/** The Directory to contain the Tab Images to be created
	 */
	public static final String TAB_DIR="testimages/tabs/";
	
	
	
	/** The Directory to contain the Tab Images to be created
	 */
	public static final String BUTTON_DIR="testimages/buttons/";
	
	
	
	
	
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public static void main(String args[]) throws FileNotFoundException, FontFormatException, IOException{

		
	
			
		String text;
		BufferedImage bi;
		
		
		// Tab 0, Problem with broken Image for Words with seven characters
		
		// multi line
		text="1234567";	
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB0_BLACK).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"account.png",bi);
		
		
		
		
		
		// Tab 0, Black single and multi line
	
		// multi line
		text="62x35 Black";	
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB0_BLACK).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"01.tab0black.png",bi);
		
		// single line
		text="Black62x35";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB0_BLACK).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"02.tab0blackSL.png",bi);
		
		
		// Tab 0, Green single and multi line
	
		// multi line
		text="Green 62x35";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB0_GREEN).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"03.tab0green.png",bi);
		
		// single line
		text="Green62x35";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB0_GREEN).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"04.tab0greenSL.png",bi);
		
		
		
		
		// Tab 1, White single and multi line
		
		// multi line
		text="White 74x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_WHITE).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"05.tab1white.png",bi);
		
		// single line
		text="W74x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_WHITE).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"06.tab1whiteSL.png",bi);
		
		
	
		// Tab 1, 74x41, Green single and multi line
		
		// multi line
		text="Green 74x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_GREEN).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"07.tab1green.png",bi);
		
		// single line
		text="Tab1Gr74x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_GREEN).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"08.tab1greenSL.png",bi);
		
		

		// Tab 1, WIDE. White single and multi line	
		
		
		// multi line
		text="White, Wide 104x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_WIDE_WHITE).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"09.tab1widewhite.png",bi);
		
		// single line
		
		text="White104x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_WIDE_WHITE).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"10.tab1widewhiteSL.png",bi);
			

		// Tab 1, WIDE. GREEN single and multi line	
	
		// multi line
		text="Green 104x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_WIDE_GREEN).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"11.tab1widegreen.png",bi);
		
		// single line
		text="GreenW104x41";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_TAB1_WIDE_WHITE).draw(text,null,null);
		(new ImageWriter()).writePNG(TAB_DIR+"12.tab1widegreenSL.png",bi);
		
		
		// Generic Button
		
		text="Generic Button, small";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_GENERIC_BUTTON).draw(text,60,20);
		(new ImageWriter()).writePNG(BUTTON_DIR+"genericButtonSmall.png",bi);
		
		
		text="Generic Button, medium";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_GENERIC_BUTTON).draw(text,100,30);
		(new ImageWriter()).writePNG(BUTTON_DIR+"genericButtonMedium.png",bi);
		
		

		text="Generic Button, large";
		bi=new OpenRideImageClass(OpenRideImageClass.IMG_CLASS_NAME_OR_GENERIC_BUTTON).draw(text,300,60);
		(new ImageWriter()).writePNG(BUTTON_DIR+"genericButtonLarge.png",bi);
		
	
		
	}
	
	
	

}
