package de.avci.openride.tools.imagecreation;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import de.avci.openride.tools.imagecreation.constants.OpenRideApplications;
import de.avci.openride.tools.imagecreation.imagewriting.ImageWriter;

/**
 * 
 * @author jochen
 * 
 */
public class OpenRideImageCreator {

	/**
	 * The Application for which we are currently creating Images. One of
	 * OpenRideServer-RS, OpenRideWeb,...etc as defined in {@link
	 * /ORImageDrawer/src/de.avci.openride.tools.imagecreation.constants.
	 * OpenRideApplications }
	 * 
	 */
	protected static String currentApplication;

	public static String getCurrentApplication() {
		return currentApplication;
	}

	/**
	 * Key Properties are distinguished by a property name ending with ".key"
	 * 
	 */
	public static String SUFFIX_KEY = "key";

	/**
	 * Property name having the suffix ".size" distinguish the encoded witdh and
	 * height properties. this will come in the form
	 * keyproperty.size=<width>x<height>
	 * 
	 */
	public static String SUFFIX_SIZE = "size";

	public static String SUFFIX_LABEL = "label";

	/**
	 * Property name having the suffix ".type" distinguish the "type" property
	 * of an image (aka: OpenRideImageClass)
	 */
	public static String SUFFIX_CLASS = "class";

	/**
	 * Name of the directory where the main properties live
	 */
	protected static String propertiesDir = "resources/properties/";

	/**
	 * Name of the directory where the languange specific properties live
	 */
	protected static String langPropertiesDir_ = propertiesDir + "/lang/";

	/**
	 * The file containing the "Main Properties" for all the OpenRideImages.
	 */
	protected static String getMainPropertiesFileName() {
		
		return propertiesDir+ "images."+getCurrentApplication()+".properties";
	}

	/**
	 * The directory where put the created images
	 */
	protected static String distDirName = "dist.images";

	/**
	 * Properties Object containing all the meta information, on images except
	 * the text (which is language dependent)
	 * 
	 */
	protected static Properties mainProperties;

	/**
	 * Initialize mainProperties
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 * 
	 */
	protected static void loadMainProperties() throws MalformedURLException,
			IOException {

		File propsFile = new File(getMainPropertiesFileName());

		mainProperties = new Properties();
		mainProperties.load(propsFile.toURL().openStream());
	}

	/**
	 * Map mapping keys to labels in the respective Language
	 * 
	 * @param lang
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	protected static Properties getLabelMap(String lang)
			throws MalformedURLException, IOException {

		String propsfileName = "resources/properties/lang/"
				+ (lang.toUpperCase())
				+ "/images."+getCurrentApplication()+".label.properties";
		System.out.println("propsfile name " + propsfileName);

		File propsFile = new File(propsfileName);
		Properties labels = new Properties();
		labels.load(propsFile.toURL().openStream());
		return labels;
	}

	protected static String getLocalizedLabel(String key, String lang)
			throws MalformedURLException, IOException {

		return "" + getLabelMap(lang).getProperty(key + "." + SUFFIX_LABEL);

	}

	protected static Set<String> getKeys() throws MalformedURLException,
			IOException {

		loadMainProperties();

		Set<Object> candidates = mainProperties.keySet();
		Iterator<Object> it = candidates.iterator();

		TreeSet<String> keys = new TreeSet<String>();

		while (it.hasNext()) {
			Object candidate = it.next();

			if (candidate instanceof String
					&& ((String) candidate).endsWith(SUFFIX_KEY)

			) {
				String cs = (String) candidate;
				keys.add(cs.toString().substring(0, (cs.lastIndexOf('.'))));
			}

		}

		return keys;
	}

	/**
	 * 
	 * @param key
	 * @param lang
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	protected static OpenRideImageData imageDataFromKey(String key, String lang)
			throws MalformedURLException, IOException {

		System.err.println("=== Loading Image Data from Key " + key + " ===");

		String basename = key;
		OpenRideImageClass oriClass = new OpenRideImageClass(
				mainProperties.getProperty(key + "." + SUFFIX_CLASS));
		String text = getLocalizedLabel(key, lang);
		Integer width = getImageWidthFromKey(key);
		Integer height = getImageHeightFromKey(key);

		return new OpenRideImageData(basename, text, oriClass, width, height);
	}

	/**
	 * Extract the width from Property
	 * 
	 * @return
	 */
	protected static Integer getImageWidthFromKey(String key) {
		String val = mainProperties.getProperty(key + "." + SUFFIX_SIZE);
		String widthStr = val.substring(0, val.indexOf('x'));
		System.err.println("widthStr=" + widthStr);

		return new Integer(widthStr);
	}

	/**
	 * Extract the width from Property
	 * 
	 * @return
	 */
	protected static Integer getImageHeightFromKey(String key) {
		String val = mainProperties.getProperty(key + "." + SUFFIX_SIZE);
		String heightStr = val.substring(val.indexOf('x') + 1);
		System.err.println("heightStr=" + heightStr);

		return new Integer(heightStr);
	}

	/**
	 * Print a listing to stdout
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FontFormatException
	 * 
	 */
	public static void printOutAllImages(String lang)
			throws MalformedURLException, IOException, FontFormatException {

		// create an image directory
		new File(distDirName).mkdir();
		String imageOutputDirApp = distDirName + "/"+getCurrentApplication()+"/";
		new File(imageOutputDirApp).mkdir();
		String imageOutputDir = imageOutputDirApp + (lang.toUpperCase());
		new File(imageOutputDir).mkdir();

		loadMainProperties();
		Set<String> keys = getKeys();

		Iterator<String> it = keys.iterator();

		while (it.hasNext()) {
			String key = it.next();
			OpenRideImageData oRIData = imageDataFromKey(key, lang);
			System.out.println(oRIData.info());
			BufferedImage bi = oRIData.getImage();

			new ImageWriter().writePNG(imageOutputDir + "/" + key + ".png", bi);

		}

	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FontFormatException
	 */
	public static void main(String args[]) throws MalformedURLException,
			IOException, FontFormatException {

	
		String[] locales=new File(langPropertiesDir_).list();

		Iterator<String> applications = 
			OpenRideApplications.knownApplications.iterator();

		
		while (applications.hasNext()) {

			currentApplication = applications.next();

			for (int i = 0; i < locales.length; i++) {

				if(!(locales[i].startsWith("."))){
					String language = locales[i];
					printOutAllImages(language);
				}
			}

		} // while applications.hasNext()

	}

}
