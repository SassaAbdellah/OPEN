package de.avci.joride.jbeans.auxiliary;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import de.avci.joride.utils.PropertiesLoader;
import de.avci.joride.utils.WebflowBean;

@Named("mainMenu")
@SessionScoped

public class MainMenuBean implements Serializable, MenuModel {
	

	

	/**
	 * generated id
	 */
	private static final long serialVersionUID = 1L;

	private MenuModel model;

	public MainMenuBean() {
		model = new DefaultMenuModel();
		
		// we are going to load a lot of properties...
		PropertiesLoader proploader=new PropertiesLoader();
		
		

		// Menu HOME
		// <p:menuitem action="home" value="#{msgs.nav1_home_label}"
		// icon="ui-icon-home" />
		String homeMsg=proploader.getMessagesProps().getProperty("nav1_home_label");	
		DefaultMenuItem homeItem = new DefaultMenuItem(homeMsg);
		homeItem.setIcon("ui-icon-home");
		homeItem.setCommand("home");
		homeItem.setAjax(false);
		model.addElement(homeItem);

		//
		// RIDER SUBMENU
		//
		// Rider's submenu
		// <p:submenu label="#{msgs.nav1_rider_label}" icon="ui-icon-suitcase">
		String riderMSG=proploader.getMessagesProps().getProperty("nav1_rider_label");
		DefaultSubMenu riderSubmenu = new DefaultSubMenu(riderMSG);
		riderSubmenu.setIcon("ui-icon-suitcase");

		// rider:: home
		String riderMsg=proploader.getMessagesProps().getProperty("nav1_rider_active");	
		DefaultMenuItem riderHomeItem = new DefaultMenuItem(riderMSG);
		riderHomeItem.setCommand("rider");
		riderHomeItem.setAjax(false);
		riderSubmenu.addElement(riderHomeItem);

		// rider::newRide
		String newRideMsg=proploader.getMessagesProps().getProperty("rideNewRequest");	
		DefaultMenuItem riderRideCreateItem = new DefaultMenuItem(newRideMsg);
		riderRideCreateItem.setCommand("#{mainMenu.rideCreateFlow}");
		riderRideCreateItem.setAjax(false);
		riderSubmenu.addElement(riderRideCreateItem);

		// rider::Search
		String rideSearchMsg=proploader.getMessagesProps().getProperty("rideSearchRequests");	
		DefaultMenuItem riderSearchItem = new DefaultMenuItem(rideSearchMsg);
		riderSearchItem.setCommand("search.requests");
		riderSearchItem.setAjax(false);
		riderSubmenu.addElement(riderSearchItem);

		model.addElement(riderSubmenu);
		
		//
		// DRIVERs SUBMENU
		//
		//
		
		String driverMSG=proploader.getMessagesProps().getProperty("nav1_driver_label");
		DefaultSubMenu driverSubmenu = new DefaultSubMenu(driverMSG);
		driverSubmenu.setIcon("ui-icon-person");
		// rider:: home
		//  <p:menuitem outcome="driver" value="#{msgs.nav1_driver_active}"  />  
		String driverMsg=proploader.getMessagesProps().getProperty("nav1_driver_active");
		DefaultMenuItem driverHomeItem = new DefaultMenuItem(driverMsg);
		driverHomeItem.setCommand("driver");
		driverHomeItem.setAjax(false);
		driverSubmenu.addElement(driverHomeItem);

		// TODO: add or configure webflow parameters for drive create new ride
		String driverNewDriveMsg=proploader.getMessagesProps().getProperty("rideNewDrive");
		DefaultMenuItem driverRideCreateItem = new DefaultMenuItem(driverNewDriveMsg);
		driverRideCreateItem.setCommand("#{mainMenu.driveCreateFlow}");
		driverRideCreateItem.setAjax(false);
		driverSubmenu.addElement(driverRideCreateItem);

		String driveSearchDriveMsg=proploader.getMessagesProps().getProperty("rideSearchDrives");
		DefaultMenuItem driverSearchItem = new DefaultMenuItem(driveSearchDriveMsg);
		driverSearchItem.setCommand("search.requests");
		driverSearchItem.setAjax(false);
		driverSubmenu.addElement(driverSearchItem);
		model.addElement(driverSubmenu);
		
		
		//
		//
		// SEARCH SUBMENU
		// 
		// <p:submenu  label="#{msgs.searchSubMenu}" icon="ui-icon-search">
		//
		String searchMSG=proploader.getMessagesProps().getProperty("searchSubMenu");
		DefaultSubMenu searchSubmenu = new DefaultSubMenu(searchMSG);
		searchSubmenu.setIcon("ui-icon-search");
	
		// <p:menuitem outcome="search.requests"               value="#{msgs.rideSearchRequests}"  />    
		String searchRequestMSG=proploader.getMessagesProps().getProperty("rideSearchRequests");
		DefaultMenuItem rideSearchItemX = new DefaultMenuItem(searchRequestMSG);
		rideSearchItemX.setCommand("search.requests");
		rideSearchItemX.setAjax(false);
		searchSubmenu.addElement(rideSearchItemX);
	
		// <p:menuitem outcome="search.drives"                 value="#{msgs.rideSearchDrives}"  /> 
		String searchDriveMSG=proploader.getMessagesProps().getProperty("rideSearchDrives");
		DefaultMenuItem driveSearchItemX = new DefaultMenuItem(searchDriveMSG);
		driveSearchItemX.setCommand("search.drives");
		searchSubmenu.addElement(driveSearchItemX);
	
		
		// <p:menuitem outcome="searchPublicProfileByNickName" value="#{msgs.publicProfileSearchProfile}"  />
		String searchProfileMSG=proploader.getMessagesProps().getProperty("publicProfileSearchProfile");
		DefaultMenuItem searchProfileItem = new DefaultMenuItem(searchProfileMSG);
		searchProfileItem.setCommand("searchPublicProfileByNickName");
		searchSubmenu.addElement(searchProfileItem);
		
		
		model.addElement(searchSubmenu);
		//
		//
		
		
		// 
		// PREFERENCES SUBMENU
		//
		//
		
       // <p:submenu  label="#{msgs.nav1_preferences_label}" icon="ui-icon-wrench">
		String preferencesMSG=proploader.getMessagesProps().getProperty("nav1_preferences_label");
		DefaultSubMenu preferencesSubmenu=new DefaultSubMenu(preferencesMSG);
		preferencesSubmenu.setIcon("ui-icon-wrench");
		
       // <p:menuitem outcome="password_change"        value="#{msgs.changePassword}" 
		String changePasswordMSG=proploader.getMessagesProps().getProperty("changePassword");
		DefaultMenuItem passwordChangeItem=new DefaultMenuItem(changePasswordMSG);
		passwordChangeItem.setCommand("password_change" );
		passwordChangeItem.setAjax(false);
		preferencesSubmenu.addElement(passwordChangeItem);
		       
		// <p:menuitem outcome="preferences.personalData"         value="#{msgs.nav1_pref_personalData_label}"       /> 
		String personalDataMSG=proploader.getMessagesProps().getProperty("nav1_pref_personalData_label");
		DefaultMenuItem personalDataItem=new DefaultMenuItem(personalDataMSG);
		personalDataItem.setCommand("preferences.personalData");
		personalDataItem.setAjax(false);
		preferencesSubmenu.addElement(personalDataItem);
		
		// <p:menuitem outcome="preferences.riderPreferences"               value="#{msgs.nav1_pref_riderPreferences_label}"   /> 
		String riderprefDataMSG=proploader.getMessagesProps().getProperty("nav1_pref_riderPreferences_label");
		DefaultMenuItem riderprefDataItem=new DefaultMenuItem(riderprefDataMSG);
		riderprefDataItem.setCommand("preferences.riderPreferences");
		riderprefDataItem.setAjax(false);
		preferencesSubmenu.addElement(riderprefDataItem);
		
		
        // <p:menuitem outcome="preferences.driverPreferences"              value="#{msgs.nav1_pref_driverPreferences_label}"  /> 
		String driverprefDataMSG=proploader.getMessagesProps().getProperty("nav1_pref_driverPreferences_label");
		
		DefaultMenuItem driverprefDataItem=new DefaultMenuItem(driverprefDataMSG);
		driverprefDataItem.setCommand("preferences.driverPreferences");
		driverprefDataItem.setAjax(false);
		preferencesSubmenu.addElement(driverprefDataItem);
		
		// <p:menuitem outcome="preferences.favoritePlaces"                 value="#{msgs.nav1_pref_favoritePlaces_label}"     />

		String favoritePlacesMSG=proploader.getMessagesProps().getProperty("nav1_pref_favoritePlaces_label");
		
		DefaultMenuItem preffavplacesDataItem=new DefaultMenuItem(favoritePlacesMSG);
		preffavplacesDataItem.setCommand("preferences.favoritePlaces" );
		preffavplacesDataItem.setAjax(false);
		preferencesSubmenu.addElement(preffavplacesDataItem);
		
        model.addElement(preferencesSubmenu);
        
        
        //
        // UPDATE MenuItem
        //
        
        // TODO: add spacer
		// <!--  p:spacer/ -->
		// <p:menuitem outcome="updates"  value="#{msgs.updates}" icon="ui-icon-lightbulb" /> 
    	String updateMsg=proploader.getMessagesProps().getProperty("updates");
    	
        DefaultMenuItem updateMenuItem=new DefaultMenuItem(updateMsg);
        updateMenuItem.setCommand("updates");  
        updateMenuItem.setIcon("ui-icon-lightbulb"); 
		model.addElement(updateMenuItem);
        
        
		//
		// REMOVE ACCOUNT MenuItem
		//
		
        // TODO: add spacer
		//<!--  p:spacer/ -->
		//<!--  <p:menuitem outcome="preferences"  value="#{msgs.custRemoveAccountLabel}" icon="ui-icon-trash" /> 
		String removeAccMsg=proploader.getMessagesProps().getProperty("custRemoveAccountLabel");
		
		DefaultMenuItem removeAccountMenuItem=new DefaultMenuItem(removeAccMsg);
		removeAccountMenuItem.setCommand("preferences");     
		removeAccountMenuItem.setIcon("ui-icon-trash"); 
		model.addElement(removeAccountMenuItem);
		
		
		//
		// LOGOUT MenuItem
		//
		
        // TODO: add spacer
		//<!--  p:spacer/ -->
		// <p:menuitem url="#{HTTPUser.getLogoutURL()}"  value="#{msgs.nav1_logout}" icon="ui-icon-power" />          
		String logoutMsg=proploader.getMessagesProps().getProperty("nav1_logout");
		
		DefaultMenuItem logoutMenuItem=new DefaultMenuItem(logoutMsg);
		logoutMenuItem.setUrl(proploader.getNavigationProps().getProperty("urlLogout"));        		
		logoutMenuItem.setIcon("ui-icon-power"); 
		model.addElement(logoutMenuItem);
		
		
		
	}

	public MenuModel getModel() {
		return model;
	}

	@Override
	public void addElement(MenuElement arg0) {
		this.getModel().addElement(arg0);
	}

	@Override
	public void generateUniqueIds() {
		this.getModel().generateUniqueIds();

	}

	@Override
	public List<MenuElement> getElements() {
		return this.getModel().getElements();
	}
	
	
	/** Initialize webflow, then go to ride.create page
	 * 
	 * @return
	 */
	private String rideCreateFlow(ActionEvent evt){
		
		
		WebflowBean webflowBean=findBean(WebflowBean.getSessionBeanName());
		webflowBean.setNext("rider");
		webflowBean.setCancel("rider");
		webflowBean.setFinish("rider");
		webflowBean.setBack("rider");
		return "rider.rideCreate";
		
	}

	
	
	
	/** Initialize webflow, then go drive.create page
	 * 
	 * @return
	 */
	private String driveCreateFlow(ActionEvent evt){
		
		
		WebflowBean webflowBean=findBean(WebflowBean.getSessionBeanName());
		webflowBean.setNext("driver");
		webflowBean.setCancel("driver");
		webflowBean.setFinish("driver");
		webflowBean.setBack("driver");
		return "driver.driveCreate";
		
	}
	
	
	
	
	/** Find JSF-bean by name
	 * 
	 * @param   beanName
	 * @return  bean retrieved from JSF Context, or null if there is one
	 */
	
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}
	
	

}
