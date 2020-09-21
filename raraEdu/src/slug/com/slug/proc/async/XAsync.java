package com.slug.proc.async; 


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class XAsync 
{ 
	private final List nodes = new ArrayList();

	private final String encode;

	public XAsync(String encode) {
		this.encode = encode;
	}

	public XAsync() {
		this.encode = "UTF-8";
	}


	public void add(XAsyncResponse handler) {
		nodes.add(handler);
	}

	public String toXmlString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding=\"").append(this.encode).append("\"?>\r\n");
		sb.append("<xsync>\r\n");

		Iterator i = nodes.iterator();
		while(i.hasNext()) {
			XAsyncResponse peer = (XAsyncResponse)i.next();
			sb.append(peer.createResponseXml());
		}

		sb.append("</xsync>");
		return sb.toString();

	}

	public void fire(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setContentType("application/xml; charset=" + this.encode);
		PrintWriter pw = null;
		try {
			pw = res.getWriter();
			pw.write(this.toXmlString());
		} catch (Exception e) {
			pw.write("<?xml version='1.0' encoding=\""+this.encode+"\"?>\r\n");
			pw.write("<xsync error=\"true\">\r\n");
			pw.write("<![CDATA[" + e + "]]>");
			pw.write("</xsync>");

		} finally {
			pw.flush();
			pw.close();
		}

	}

} 
