package com.slug.proc.async; 

import java.util.HashMap;
import java.util.Map;
import java.util.Set;



/**
 * @(#) XAsyncInterface.java
 */
public abstract class XAsyncAbstractResponse {


	private final String name;
	private final Map attributes = new HashMap();

	protected abstract String getInnerTag();
	
	public XAsyncAbstractResponse(String name) {
		this.name = name;
	}

	protected void setAttribute(String name, String value) {
		attributes.put(name, value);
	}

	public void setOnBeforeAction(String callback){
		attributes.put("onBeforeAction",  escape(callback));
	}

	public void setOnAfterAction(String callback){
		attributes.put("onAfterAction",   escape(callback));
	}

	protected String getCdata(String cdata) {
		return "<![CDATA[" + cdata + "]]>";
	}

	protected String getTag(String tag, String data) {
		return "<" + tag + ">" + data + "</" + tag + ">";
	}

	protected String getTag(String tag, String aname, String avalue, String data) {
		StringBuffer sb = new StringBuffer();
		sb.append("<").append(tag).append(" ")  //LHtmlUtil.escape(avalue)
		  .append(aname).append("=\"").append(  escape(avalue) ).append("\">")
		  .append(data)
		  .append("</").append(tag).append(">\r\n");
		return sb.toString();
	}

	protected String getTag(String tag, Map attr, String data) {
		StringBuffer sb = new StringBuffer();
		sb.append("<").append(tag).append(" ");

		Set keySet = attr.keySet();
		String[] keyArray = (String [])keySet.toArray(new String[keySet.size()]);

		int size = keyArray.length;
		for (int idx = 0 ; idx < size ; idx++) {
			String  value = escape((String)attr.get(keyArray[idx]));
			sb.append(keyArray[idx]).append("=\"").append(value).append("\" ");
		}
		sb.append(">\n\r");
		sb.append(data).append("\n\r");
		sb.append("</").append(tag).append(">\r\n");
		return sb.toString();
	}

	public String toXmlString() {
		return getTag(this.name, this.attributes, getInnerTag());
	}


    
	public static String escape(String str)
    {
        if(str == null)
            return null;
        StringBuffer escapedStr = new StringBuffer();
        char ch[] = str.toCharArray();
        int charSize = ch.length;
        for(int i = 0; i < charSize; i++)
            if(ch[i] == '&')
                escapedStr.append("&amp;");
            else
            if(ch[i] == '<')
                escapedStr.append("&lt;");
            else
            if(ch[i] == '>')
                escapedStr.append("&gt;");
            else
            if(ch[i] == '"')
                escapedStr.append("&quot;");
            else
            if(ch[i] == '\'')
                escapedStr.append("&#039;");
            else
                escapedStr.append(ch[i]);

        return escapedStr.toString();
    }

}