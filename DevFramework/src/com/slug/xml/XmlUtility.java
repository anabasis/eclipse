package com.slug.xml;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;

import com.slug.config.AppConfigManager;
import com.slug.exception.PException;
import com.slug.logging.*;
import com.slug.vo.box.Box;
import com.slug.vo.box.VectorBox;


/**
 * @(#) XmlUtility.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

public class XmlUtility{
	
	//public static XmlHandler xmlHandler;
	/**
	 * Web Application(WAR) 경로 안에 위치한 XML 파일 읽어 Document 생성하기
	 * @param path
	 * @return Document
	 * @throws JDOMException
	 * @throws IOException
	 */
    public static Document getXmlDoc(String path) throws PException{
        
    	String R_PATH = AppConfigManager.getAbsoluteRootPath();
    	R_PATH = "";
    	
    	SAXBuilder builder;
        Document doc;

        try {
	        builder = new SAXBuilder();
	        doc = builder.build(new File(R_PATH + path));
        
        }catch(JDOMException je) {
        	throw new PException(je);
        }catch(Exception e) {
        	throw new PException(e);
        }
        return doc;
    }
   
    public static String getQueryStr(String module , String id) throws PException{
        	
    	AppConfigManager conf = AppConfigManager.getInstance();
        String R_PATH = conf.getString("webApp.sql.default.dir")+module+".xml";
        
        SAXBuilder builder;
        Document   doc;
        try {
	        builder = new SAXBuilder();
	        doc = builder.build(new File(R_PATH));
        
        }catch(JDOMException je) {
        	throw new PException(je);
        }catch(Exception e) {
        	throw new PException(e);
        }
        
        Element root = doc.getRootElement();
        Element sqlElement;
        List sql = root.getChildren("sql");
        
        String sqlmodule = root.getAttribute("module").getValue();
        String sqlid     = "";
        String data_type     = "";
        String queryStr  = "";
        
        
        for (int i = 0; i < sql.size(); i++) 
        {
          sqlElement = (Element) sql.get(i);
          sqlid = sqlElement.getAttribute("id").getValue();
          data_type = sqlElement.getAttribute("type").getValue();
          
          if(sqlmodule.equals(module)&& sqlid.equals(id))
          {
        	  queryStr = sqlElement.getChild("query").getText();
        	  return queryStr;
          }
        }
        
        return "";
        
    }
    public static String[] getQueryInfo(String module , String id) throws PException{
    	
    	AppConfigManager conf = AppConfigManager.getInstance();
        String R_PATH = conf.getString("webApp.sql.default.dir")+module+".xml";
        
        String[] rtnArr = new String[2];
        
        SAXBuilder builder;
        Document   doc;
        try {
	        builder = new SAXBuilder();
	        doc = builder.build(new File(R_PATH));
        
        }catch(JDOMException je) {
        	throw new PException(je);
        }catch(Exception e) {
        	throw new PException(e);
        }
        
        Element root = doc.getRootElement();
        Element sqlElement;
        List sql = root.getChildren("sql");
        
        String sqlmodule = root.getAttribute("module").getValue();
        String sqlid     = "";
        String data_type     = "";
        String queryStr  = "";
        
        
        for (int i = 0; i < sql.size(); i++) 
        {
          sqlElement = (Element) sql.get(i);
          sqlid = sqlElement.getAttribute("id").getValue();
          data_type = sqlElement.getAttribute("type").getValue();
          
          if(sqlmodule.equals(module)&& sqlid.equals(id))
          {
        	  queryStr = sqlElement.getChild("query").getText();
        	  rtnArr[0]=queryStr;
        	  rtnArr[1]=data_type.toUpperCase();
        	  return rtnArr;
          }
        }
        
        return rtnArr;
        
    }
    public static String getQueryStr(String xml_path, String module , String id) throws PException{
    	
        //String R_PATH = AppConfig.getAbsoluteRootPath();
    	AppConfigManager conf = AppConfigManager.getInstance();
        String R_PATH = xml_path;//conf.getString("webApp.sql.default.dir")+module+".xml";
        
        SAXBuilder builder;
        Document   doc;
        try {
	        builder = new SAXBuilder();
	        doc = builder.build(new File(R_PATH));
        
        }catch(JDOMException je) {
        	throw new PException(je);
        }catch(Exception e) {
        	throw new PException(e);
        }
        
        Element root = doc.getRootElement();
        Element sqlElement;
        List sql = root.getChildren("sql");
        
        String sqlmodule = "";
        String sqlid     = "";
        String queryStr  = "";
        
        
        for (int i = 0; i < sql.size(); i++) 
        {
          sqlElement = (Element) sql.get(i);
          sqlmodule = sqlElement.getAttribute("module").getValue();
          sqlid = sqlElement.getAttribute("id").getValue();
          
          if(sqlmodule.equals(module)&& sqlid.equals(id))
          {
        	  
        	  queryStr = sqlElement.getChild("query").getText();
              return queryStr;
          }
        }
        
        return queryStr;
    }
	
}
