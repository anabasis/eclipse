package com.raraedu.adopter;

import javax.servlet.http.HttpSession;

import com.slug.logging.Logging;
import com.slug.proc.XAsyncStrProcessor;
import com.slug.web.XAsyncAbstractServlet;
import com.raraedu.proc.XAsyncWebRequestProcessor;

/***
 * 
 * @author kds
 *
 */
public class XAsyncWebAdopter extends XAsyncAbstractServlet {
    String resStr = "";
	public XAsyncWebAdopter(){super();}
	protected void procXAsyncService( javax.servlet.http.HttpServletRequest req,
								      javax.servlet.http.HttpServletResponse res){
		try
        {
			
			Logging.debug.println("");
			Logging.debug.println("< XAsyncWebAdapter > <procXAsyncService> Initilized by ["+req.getRemoteAddr()+"]");

			XAsyncStrProcessor proc = (XAsyncStrProcessor) new XAsyncWebRequestProcessor();
			resStr = proc.procXAsyncRequest(req, res);
			
			printResponseWrite(req,res,resStr);
			
			Logging.debug.println("< XAsyncWebAdapter > <procXAsyncService> Completed by ["+req.getRemoteAddr()+"]");

        } catch(Exception e) {
        	
			Logging.err.println("< XAsyncWebAdapter >< procXAsyncService > Occurred Exception While User IP ["+req.getRemoteAddr()+"]\n");
			printErrorWrite(req,res,"<Application Error>",e);

		}
        
        
	}

}


