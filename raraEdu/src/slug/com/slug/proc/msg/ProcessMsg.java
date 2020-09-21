package com.slug.proc.msg;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.slug.logging.Logging;

public class ProcessMsg implements java.io.Serializable{
 
	static final long serialVersionUID = 1L;
	
	private String     reqString;
	private String     resString;
	private String     resCode;
	private String     resMsg;
	private String     rtnUri;
	private String[]   reqArray;
	private String[][] rtnArray2;
	private Document   reqXml;
	private Document   rtnXml;
	private String     rtnXmlStr;
	/**
	 * JSON 추가
	 * 
	 */
	private String     classType; // return type 'STRING' , 'JSON'
    private String     rtnStr;   // String Type일경우의 Value
    private JSONObject jsonObj;  // JSON Type일경우의 value
    
	/**
	 * 생성자 Type1
	 */
	public ProcessMsg(){}

	public ProcessMsg(String srtTmp, String flag){
		if(flag.equals("req"))
			setReqString(srtTmp);
		else if(flag.equals("res"))
			setResString(srtTmp);
		else if(flag.equals("code"))
			setResCode(srtTmp);
		else if(flag.equals("msg"))
			setResMsg(srtTmp);
		else if(flag.equals("uri"))
			setRtnUri(srtTmp);
	}

	public ProcessMsg(String[] reqArray){
		setReqArray(reqArray);
	}
	public ProcessMsg(String[][] reqArray2){
		setReqArray2(reqArray2);
	}
	public ProcessMsg(Document xtmp, String flag){
		if(flag.equals("req"))
			setReqXml(xtmp);
		else
			setRtnXml(xtmp);
	}
	
	public String  getReqString(){return this.reqString;}
	public void   setReqString(String temp ){this.reqString = temp;}

	public String  getResString(){return this.resString;}
	public void   setResString(String temp ){this.resString = temp;}
	// 응답코드
	public void  setResCode(String temp){ this.resCode = temp; }
	public String getResCode(){return this.resCode;}
	
	// 응답 메세지
	public void  setResMsg(String temp){ this.resMsg = temp; }
	public String getResMsg(){return this.resMsg;}
	
	public void  setRtnUri(String temp){ this.rtnUri = temp; }
	public String getRtnUri(){return this.rtnUri;}
	
	// 응답 배열
	public void  setReqArray(String[] temp){ this.reqArray = temp; }
	public String[] getReqArrayCode(){return this.reqArray;}

	public void  setReqArray2(String[][] temp){ this.rtnArray2 = temp; }
	public String[][] getRtnArray2Code(){return this.rtnArray2;}
	
	public void setReqXml(Document temp){this.reqXml = temp;}
	public Document getReqXml(){return this.reqXml;}

	//public void setUserInfo(UserEnt temp){this.userInfo = temp;}
	//public UserEnt getUserInfo(){return this.userInfo;}
	
	
	public void setRtnXml(Document temp){
		this.rtnXml = temp;
		setClassType ("Dom");
    	
		XMLOutputter op = new XMLOutputter();
    	Format format = op.getFormat();
    	format = format.setLineSeparator("");
    	op.setFormat(format);
    	String rtn_xml = op.outputString(temp);
    	
    	Logging.dev.println("<ProcessMsg> Set Return Xml String...........");
       	Logging.dev.println(rtn_xml+"\n\n\n");
    	setRtnXmlStr(rtn_xml);
	}
	public Document getRtnXml(){return this.rtnXml;}

	public void setRtnXmlStr(String temp){this.rtnXmlStr = temp;}
	public String getRtnXmlStr(){return this.rtnXmlStr;}

	

    
	public ProcessMsg(JSONObject jsonObj){
		setJsonObj(jsonObj);
	}
	
	public ProcessMsg(String rtnStr){
			setRtnStr(rtnStr);
	}
	
    public void setRtnStr (String rtnStr) {
        this.rtnStr = rtnStr;
        setClassType("STRING");
    }
    
    public String getRtnStr() {
        return this.rtnStr;   
    }	
	
    public void setJsonObj (JSONObject jsonObj) {
        this.jsonObj = jsonObj;
        setClassType("JSON");
    }
    
    public JSONObject getJsonObj() {
        return this.jsonObj;   
    }
    public void setClassType (String classTyp) {
        this.classType = classTyp;
    }
    
    public String getClassType() {
        return this.classType;   
    }
}
