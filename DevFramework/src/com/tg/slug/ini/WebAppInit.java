package com.tg.slug.ini;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import com.tg.jfound.config.AppConfig;

import com.tg.slug.config.AppConfigException;
import com.tg.slug.config.AppConfigManager;
import com.tg.slug.exception.PException;
import com.tg.slug.key.JFoundKey;
import com.tg.slug.key.JFoundKeyHandler;
import com.tg.slug.log4j.Logger;
import com.tg.slug.logging.Logging;

public class WebAppInit implements ServletContextListener{

    /**
     * 1. 기능     :  웹어플리케이션이 Deploy될 때 초기화 작업을 수행
     * 2. 처리 개요 : 웹어플리케이션이 디플로이될 때 ConfigManager를 기동하여
     *                초기화작업을 수행한다.
     * 3. 주의사항
     *
     * @param ServletContextEvent
     * @return void
     * @exception
     **/
    
    public void contextInitialized(ServletContextEvent evt)
    {

        System.out.println("*****************************  TimeGate jfound Frame-Work Initialized  **********************************************");

//        Logger logger = Logger.getLogger(	"");
//        logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        ServletContext sc = evt.getServletContext();
        
        String context_root_path = sc.getRealPath("/");
        String root_context = sc.getContextPath();
        String context_name = sc.getServletContextName();
//        Logger logger = Logger.getInstance(sc.getServletContextName());
        
//        logger.banner("TimeGate jfound Frame-Work Initialized");
        JFoundKeyHandler key = JFoundKeyHandler.getInstance();
        
		key.setKeys("ContextName", root_context);
		
         //JFoundKey.ContextName = root_context;

        System.out.println("@ context_root_path Path: ["+context_root_path+"]");
        System.out.println("@ Root Context Path: ["+root_context+"]");
        System.out.println("@ Servelet Context Name: ["+context_name+"]");
        
        try{
        	AppConfigManager conf = AppConfigManager.getInstance(context_root_path);
        }catch(AppConfigException ace){
            
        }

 	 
        System.out.println("@@@@ Web Framework - Application 초기화 종료!");

    }

    /**
     * 1. 기능     : 웹어플리케이션이 Undeploy될 때 초기화된 Manager들을 중지시킴
     * 2. 처리 개요 : 웹어플리케이션이 Undeploy될 때 LifecycleManager를 통해 Manager를 중지시킨다.
     *
     * 3. 주의사항
     *
     * @param ServletContextEvent
     * @return void
     * @exception
     **/
    public void contextDestroyed(ServletContextEvent evt)
    {

        Logging.sys.println("@@@@ Web Framework - Application destroying.");

        System.out.println("\n\n");
    }

    
}