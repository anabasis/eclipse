package com.raraedu.proc; 


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.proc.XAsyncStrProcessor;
import com.slug.proc.flow.FlowAccessController;
import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;
import com.slug.vo.box.CollectionUtility;

/**
 * 
 */
public class XAsyncWebRequestProcessor implements XAsyncStrProcessor
{ 
	private String encode = "UTF-8";
	public XAsyncWebRequestProcessor() {}

	public String procXAsyncRequest(HttpServletRequest req, HttpServletResponse res) throws PException {
		
        Box ibox = CollectionUtility.getBoxWithSession(req);
        String U_STATUS = ibox.getString("U_STATUS");
        String resStr = "";

        Logging.debug.println("< XAsyncWebRequestProcessor >< procXAsyncRequest >Execute Class & Method ["+ibox.getString("tflow")+"]["+ibox.getString("taction")+"]");
    	 
        ProcessMsg msg = new ProcessMsg();
       
        try{

			if((U_STATUS == null)||(U_STATUS.equals("N"))||(U_STATUS.equals(""))){
				resStr = "{\"success\":false,\"responseMsg\":\"Invalid Session\"}";
			}else{
	        	FlowAccessController FlowController = new FlowAccessController();
	            msg = FlowController.executeFlowComponent(ibox);

		        if(msg.getClassType().equals("JSON")) {
		            resStr = msg.getJsonObj().toString();
		        }else if(msg.getClassType().equals("STRING")) {
		            resStr = msg.getRtnStr();
		        }else{
		        	resStr = msg.getRtnXmlStr();
		        }
			}

        }catch(Exception e){
        	Logging.err.println("<XAsyncWebRequestProcessor> procXAsyncRequest: Component Name["+ibox.getString("tflow")+"]["+ibox.getString("taction")+"]");
            throw new PException("<XAsyncWebRequestProcessor> procXAsyncRequest: Component Name["+ibox.getString("tflow")+"]["+ibox.getString("taction")+"]",e);
        }
       	return resStr;

    }
   
	public String procXAsyncMultipartRequest(Box ibox) throws PException {
		
        String resStr = "";

        Logging.debug.println("< XAsyncWebRequestProcessor >< procXAsyncFileUpload >Execute Class & Method ["+ibox.getString("tflow")+"]["+ibox.getString("taction")+"]");
    	 
        ProcessMsg msg = new ProcessMsg();
       
        try{

	        	FlowAccessController FlowController = new FlowAccessController();
	            msg = FlowController.executeFlowComponent(ibox);

		        if(msg.getClassType().equals("JSON")) {
		            resStr = msg.getJsonObj().toString();
		        }
		        else if(msg.getClassType().equals("STRING")) {
		            resStr = msg.getRtnStr();
		        }else{
		        	resStr = msg.getRtnXmlStr();
		        }

        }catch(Exception e){
        	Logging.err.println("<XAsyncWebRequestProcessor> < procXAsyncFileUpload >procXAsyncRequest: Component Name["+ibox.getString("tflow")+"]["+ibox.getString("taction")+"]");
            throw new PException("<XAsyncWebRequestProcessor> < procXAsyncFileUpload >procXAsyncRequest: Component Name["+ibox.getString("tflow")+"]["+ibox.getString("taction")+"]",e);
        }

        return resStr;

    }
   
} 
