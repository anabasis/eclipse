package com.slug.log4j;

/*
 * @Project  �ŷ�ó ���հ� �ý��� (Smile Power of Sale - POS)
 * @File     GLogger.java
 * @Version  1.0
 * @Date     2004. 5. 25.
 * @Author   �ǿ��� R & D Team
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
* DEBUG,WARN,INFO,ERROR,FATAL���� �ΰ? Console,HTML,XML,Text File�� ����� ����� �����Ѵ�
* 
* <PRE> 
* </PRE> 
* 
* <PRE> 
* <B>History:</B> 
*       �ǿ���, 1.0, 2004/05/25 - �ʱ� �ۼ� 
* </PRE> 
* 
* @author  �ǿ��� R & D Team
* @version 1.0, 2004/05/25  
* @see     NONE 
*/
public class GLogger 
{
	static  Logger logger        = Logger.getLogger(com.slug.log4j.GLogger.class);
	private String logDirectory  = "";

	/**
	*	Log4j Layout              
	*   iLogLevel = 0 : DateLayout : ������
	*   iLogLevel = 1 : HTMLLayout,WriterAppender
	*   iLogLevel = 2 : PatternLayout,ConsoleAppender
	*   iLogLevel = 3 : SimpleLayout,FileAppender
	*   iLogLevel = 4 : XMLLayout,FileAppender
	*   iLogLevel = 5 : PatternLayout,DailyRollingFileAppender(PatternLayout �� Ȯ������ ������ �ð� format �� ���� log ������ �ڵ���ȴ�)
	*   iLogLevel = 6 : DB �α�
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
	* ����� �α� �޽���
	*/
	private String strMsg   = null;	

    /**
     * ����
	 */
    public GLogger() 
    {
		super();
	}

    /**
     * Parameter���� ��� �α� �۾��� �����Ѵ�.
     *
     * @param     Log Layout
     * @param     Log Type
     * @param     �α� �޽���
     * @return    none
     * @exception none
     * @see       none
     */
    public void logWrite(int p_iLogLevel, int p_iLogType, String p_strMsg) 
    {           
		//CommUtils commutils = new CommUtils();
		//�α� ������ ������ ���丮�� �о�´�.
    	logDirectory = "/temp";//commutils.getProperties("LOGPATH");
    	
    	//layout
		iLogLevel = p_iLogLevel;
		//debug,error,info...
		iLogType  = p_iLogType;
		//log message
		strMsg    = p_strMsg;
    	switch(iLogLevel)
    	{            
    		//DateLayout : ������
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
