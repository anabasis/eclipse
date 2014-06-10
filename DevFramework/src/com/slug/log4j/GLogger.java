package com.slug.log4j;

/*
 * @Project  거래처 통합관리 시스템 (Smile Power of Sale - POS)
 * @File     GLogger.java
 * @Version  1.0
 * @Date     2004. 5. 25.
 * @Author   권영우 R & D Team
 * Copyright (c) 2003-2004 by GM Solution.Ltd.
 * All rights reserved.
 */

import java.io.FileOutputStream;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.xml.XMLLayout;

//import com.gm.framework.util.CommUtils;

/** 
* DEBUG,WARN,INFO,ERROR,FATAL등의 로고를 Console,HTML,XML,Text File로 남기는 기능을 제공한다
* 
* <PRE> 
* </PRE> 
* 
* <PRE> 
* <B>History:</B> 
*       권영우, 1.0, 2004/05/25 - 초기 작성 
* </PRE> 
* 
* @author  권영우 R & D Team
* @version 1.0, 2004/05/25  
* @see     NONE 
*/
public class GLogger 
{
	static  Logger logger        = Logger.getLogger(com.slug.log4j.GLogger.class);
	private String logDirectory  = "";

	/**
	*	Log4j Layout              
	*   iLogLevel = 0 : DateLayout : 사용안함
	*   iLogLevel = 1 : HTMLLayout,WriterAppender
	*   iLogLevel = 2 : PatternLayout,ConsoleAppender
	*   iLogLevel = 3 : SimpleLayout,FileAppender
	*   iLogLevel = 4 : XMLLayout,FileAppender
	*   iLogLevel = 5 : PatternLayout,DailyRollingFileAppender(PatternLayout 의 확장으로 정해진 시간 format 에 의해 log 파일이 자동생성된다)
	*   iLogLevel = 6 : DB 로그
	*/
	private int    iLogLevel  = 0;

	/**  
	*   Log Type
	*   iLogType  = 0 : DEBUG
	*   iLogType  = 1 : INFO
	*   iLogType  = 2 : WARN
	*   iLogType  = 3 : ERROR
	*   iLogType  = 4 : FATAL
	*/
	private int    iLogType   = 0;
	
	/**
	* 출력할 로그 메시지
	*/
	private String strMsg   = null;	

    /**
     * 생성자
	 */
    public GLogger() 
    {
		super();
	}

    /**
     * Parameter값에 따라 로그 작업을 수행한다.
     *
     * @param     Log Layout
     * @param     Log Type
     * @param     로그 메시지
     * @return    none
     * @exception none
     * @see       none
     */
    public void logWrite(int p_iLogLevel, int p_iLogType, String p_strMsg) 
    {           
		//CommUtils commutils = new CommUtils();
		//로그 파일을 저장할 디렉토리를 읽어온다.
    	logDirectory = "/temp";//commutils.getProperties("LOGPATH");
    	
    	//layout
		iLogLevel = p_iLogLevel;
		//debug,error,info...
		iLogType  = p_iLogType;
		//log message
		strMsg    = p_strMsg;
    	switch(iLogLevel)
    	{            
    		//DateLayout : 사용안함
    		case 0 : break;    
		    //HTMLLayout,WriterAppender
    		case 1 : {
	    				 HTMLLayout layout   = new HTMLLayout();
	    	             WriterAppender appender = null;
	    	             try
	    	             {
	    		             FileOutputStream output = new FileOutputStream(logDirectory+"/log.html");
	    		             appender = new WriterAppender(layout,output);
					    	 logger.addAppender(appender);
	    	             }
	    	             catch(Exception e)
	    	             {
				             System.out.println("logHTMLLayout,WriterAppender Processing Error : "+e.getMessage());
	    	             }
	    	         }
                     break;
			//PatternLayout,ConsoleAppender
    		case 2 : {
	    				 String strLogPattern = "%r [%t] %-5p %c %x - %m %d{yyyy MMM dd HH:mm:ss, SSS} \n";
						 try
						 {
		    				 PatternLayout   layout   = new PatternLayout(strLogPattern);
		    				 ConsoleAppender appender = new ConsoleAppender(layout);
					    	 logger.addAppender(appender);
						 }
						 catch(Exception e)
						 {
				             System.out.println("PatternLayout,ConsoleAppender Processing Error : "+e.getMessage());
						 }
					 }
    		         break; 
            //SimpleLayout,FileAppender
    		case 3 : {
	    				 SimpleLayout layout   = new SimpleLayout();
						 FileAppender appender = null;
						 try
						 {
							 appender = new FileAppender(layout,logDirectory+"/log.txt",true);
					    	 logger.addAppender(appender);
						 }
						 catch(Exception e)
						 {
				             System.out.println("SimpleLayout,FileAppender Processing Error : "+e.getMessage());
						 }
					 }
    		         break; 
			//XMLLayout,FileAppender
    		case 4 : {
	    				 XMLLayout layout = new XMLLayout();
	    				 layout.setLocationInfo(true);
	    				 FileAppender appender = null;
	    				 try
	    				 {
	    				 	 appender = new FileAppender(layout,logDirectory+"/log.xml",true);
					    	 logger.addAppender(appender);
	    				 }
	    				 catch(Exception e)
	    				 {
				             System.out.println("XMLLayout,FileAppender Processing Error : "+e.getMessage());    				 	 
	    				 }
	    		     }
    		         break;
			//PatternLayout,DailyRollingFileAppender    		         
    		case 5 : {
	    				 DailyRollingFileAppender appender = null;
	                     try {
						     String logPattern = "%r [%t] %-5p %c %x - %m %d{yyyy MMM dd HH:mm:ss, SSS} \n";
				             appender        = new DailyRollingFileAppender( new PatternLayout(logPattern), logDirectory+"/bingpos.log", "'.'yyyy-MM-dd" );
					    	 logger.addAppender(appender);
				         }
				         catch(Exception e){
				             System.out.println("PatternLayout,DailyRollingFileAppender Processing Error : "+e.getMessage());    				 	 
				         }
	    		         break;
	    		     }
    		case 6:  break;
    	}

		switch(iLogType)
		{
			case 0 : logger.setLevel(Level.DEBUG);
			         logger.debug(strMsg);
			         break;
			case 1 : logger.setLevel(Level.INFO);
			         logger.info(strMsg);
			         break;
			case 2 : logger.setLevel(Level.WARN);
			         logger.warn(strMsg);
			         break;
			case 3 : logger.setLevel(Level.ERROR);
			         logger.error(strMsg);
			         break;
			case 4 : logger.setLevel(Level.FATAL);
			         logger.fatal(strMsg);
			         break;
		}
	}
}
