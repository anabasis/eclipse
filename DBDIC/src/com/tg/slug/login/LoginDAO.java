package com.tg.slug.login;

import com.tg.slug.dao.DaoHandler;
import com.tg.slug.exception.PException;
import com.tg.slug.logging.Logging;
import com.tg.slug.vo.box.Box;

/***
 * 
 * @author JSCHO
 *
 */
public class LoginDAO {

	/***
	 * 
	 * @param sbox
	 * @param input_value
	 * @return
	 */
	public static String updateTrx(Box sbox, String[] input_value){
		String result = "";
        try{
	        DaoHandler dao = new DaoHandler();
	        //Logging.dev.println("< TMRSDAO >< updateTrx() > call DaoHandler updateObjectLoggable Method");
	        result = dao.updateObjectLoggable(sbox,input_value);
	        result = "{success:true,data:{responseMsg:'SUCCESS'}}";

        }catch(PException p){
        	result = "{success:false,data:{responseMsg:'FAIL'}}";
        	
        }
        
        Logging.debug.println("< TMRSDAO >< updateTrx() > Trx From < "+sbox.getString("tflow")+" >< "+sbox.getString("taction")+" > Result: "+result);
        
        return result;
	}

	
	public static boolean updateTrxBoolean(Box sbox, String[] input_value){
		boolean result = true;
		String rtncode = "";
        try{
	        DaoHandler dao = new DaoHandler();
	        //Logging.dev.println("< TMRSDAO >< updateTrx() > call DaoHandler updateObjectLoggable Method");
	        rtncode = dao.updateObjectLoggable(sbox,input_value);
	        if(rtncode.equals("N"))
	        	result = false;

        }catch(PException p){
        	result = false;//"{success:false,data:{responseMsg:'FAIL'}}";
        	
        }
        
        Logging.debug.println("< TMRSDAO >< updateTrx() > Trx From < "+sbox.getString("tflow")+" >< "+sbox.getString("taction")+" > Result: "+result);
        
        return result;
	}

}
