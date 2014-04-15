package com.tg.slug.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;



/**
 * @(#) XmlLibraryInterface.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

public class XmlHandler implements java.io.Serializable {
	
	final static long serialVersionUID = 2L;
	
	private static XmlHandler xmlHandler;
	private static Document docs = null;

    private XmlHandler(){
    	Element root = new Element("jfound_app");
    	this.docs = new Document(root);
    }
	private XmlHandler(String name, String rootElement){
		Element  root = new Element(rootElement);
			this.docs = new Document(root);
	}    

	public static synchronized XmlHandler getInstance(){
		xmlHandler = new XmlHandler();
		return xmlHandler;
	}
	public static synchronized XmlHandler getInstance(String name,String rootElement){
				
    	xmlHandler = new XmlHandler(name, rootElement);
		return xmlHandler;
	}
	public static synchronized XmlHandler getInstance(String path, String name ,String rootElement){
		
    	xmlHandler = new XmlHandler(name,rootElement);
		return xmlHandler;
	}
	
	
    
    private Document readXmlFile(String file) throws JDOMException ,IOException{
        
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(file));
        return doc;
    }


}
