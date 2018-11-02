package com.acme.anvil.listener;

import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import weblogic.common.T3ServicesDef;
import weblogic.i18n.logging.NonCatalogLogger;
import com.acme.anvil.management.AnvilInvokeBeanImpl;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

/***
 * Prior to Weblogic 7, the T3StartupDef was a way of implementing startup listeners.
 *  See: http://docs.oracle.com/cd/E13222_01/wls/docs100/javadocs/weblogic/common/T3StartupDef.html
 *  See: http://docs.oracle.com/cd/E13222_01/wls/docs81/config_xml/StartupClass.html
 * @author bradsdavis
 *
 */
public class AnvilWebStartupListener implements ServletContextListener {

	private static final String MBEAN_NAME = "com.acme:Name=anvil,Type=com.acme.anvil.management.AnvilInvokeBeanT3StartupDef"; 
	private NonCatalogLogger log;
	
	public AnvilWebStartupListener() {
		//yes, this should be static final, but just for demo sake..
		log = new NonCatalogLogger("AnvilWebStartupListener");
	}
	
	private T3ServicesDef services;
	
	public void setServices(T3ServicesDef services) {
		this.services = services;
	}

	public void contextInitialized(ServletContextEvent sce) {
		log.info("Starting Server Startup Class: "+name+" with properties: ");
		
		for(Object key : ht.keySet()) {
			log.info("Key["+key+"] = Value["+ht.get(key)+"]");
		}
		
		return "Completed Startup Class: "+name;
	}

	
	private MBeanServer getMBeanServer() throws NamingException {
		java.util.Hashtable<String, String> envHashtable = new java.util.Hashtable<String, String>();
		Context context = env.getContext();
		
		//get reference to the MBean Server...
		MBeanServer server = (MBeanServer) context.lookup("java:comp/jmx/runtime");
		return server;
	}
	
	private void registerMBean() {
		log.info("Registering MBeans.");
		
		MBeanServer server;
		try {
			server = getMBeanServer();
			server.registerMBean(new AnvilInvokeBeanImpl(), new ObjectName(MBEAN_NAME));
			log.info("Registered MBean["+MBEAN_NAME+"]");
		} catch (Exception e) {
			log.error("Exception while registering MBean["+MBEAN_NAME+"]");
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	
	
}
