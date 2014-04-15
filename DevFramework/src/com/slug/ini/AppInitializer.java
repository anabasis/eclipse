package com.slug.ini;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.slug.config.PropManager;
import com.slug.key.JFoundKey;
import com.slug.lifecycle.Lifecycle;
import com.slug.lifecycle.LifecycleException;
import com.slug.lifecycle.LifecycleManager;
import com.slug.logging.Logging;

public class AppInitializer implements ServletContextListener
{
    /**
     *
     * @param ServletContextEvent
     * @return void
     * @exception
     **/
    public void contextInitialized(ServletContextEvent evt)
    {
    	String prop_file = ""; 
    	
        LifecycleManager manager = LifecycleManager.getInstance();

        ServletContext sc = evt.getServletContext();
        JFoundKey.WebApplicationPath = sc.getRealPath("/");
        
        if(!"".equals(sc.getInitParameter("webApp.config.file"))) prop_file = sc.getInitParameter("webApp.config.file");
        
        if(manager instanceof Lifecycle) { 
            try {
                manager.start(prop_file);
            } catch(LifecycleException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
		Logging.sys.println("< AppInitializer >< contextInitialized >@ TG WEB FRAMEWORK Application Initializing");

    }
    public void contextDestroyed(ServletContextEvent evt)
    {
        Logging.sys.println("< AppInitializer >< contextDestroyed >@ TG WEB FRAMEWORK - Life Cycle destroying.");


        LifecycleManager manager = LifecycleManager.getInstance();
        if(manager instanceof Lifecycle) {
            try {
                manager.stop();
            } catch(LifecycleException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n\n");
    }

}
