package de.avci.rest.application.config;


/** REST Application Registry, equivalent to javax.ws.rs.core.Application.
 * it registers all REST root resources created in the project.
 * 
 *  This is needed only if building OpenRideServer-RS outside 
 *  of the NetbeansIDE.*
 *
 *  When building with NetBeans, NB will provide a generated class
 *  or.netbeans.rest.application.config.Application config,
 *  which does the same job.
 *  
 *
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends javax.ws.rs.core.Application { 
}
