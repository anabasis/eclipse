package com.slug.dao;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;


import org.jdom.Document;
import org.jdom.Element;


import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.util.cmnUtil;
import com.slug.vo.box.Box;
import com.slug.vo.box.VectorBox;

import net.sf.json.JSONObject;

public class DaoHandler extends DAOSqlHandler{

	String trxResultCode = "Y";
	String trxResultMsg  ="Success To Request!";

	/**
	 * Constructor
	 */
	public DaoHandler(){
        super();
    }
	
	/**
	 * selectList override method
	 * 
	 * @param ubox
	 * @param input_value
	 * @return
	 * @throws PException
	 */
    public Box selectListBox( Box    ubox, String input_value[]) throws PException{
			
		return selectListBox(null, ubox, input_value);
	}

	/**
	 * selectListObject 실 수행 Method
	 * Vector 형태의 Data Set을 얻어 오기 위한 메소드
	 * 
	 * @param conn
	 * @param ubox
	 * @param input_value
	 * @return
	 * @throws PException
	 */
    public Box selectListBox( Connection pconn,
    		                              Box    ubox,
							              String input_value[]) throws PException
	{

		Connection conn = null;
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		String errMsg = "";
		
		Box box = new VectorBox();
		DAORtnObjBuilder rob = new DAORtnObjBuilder();
		DAOStatementExecutor xe = new DAOStatementExecutor();
		try{
			
			if(pconn == null){
				conn = getConnection(ubox.getString("db"));
				rs = xe.doExecuteSelectLoggable( conn,  pstmt, rs, ubox,  input_value);			  
			}else{
				rs = xe.doExecuteSelectLoggable( pconn,  pstmt, rs, ubox,  input_value);
			}
			box = rob.RmDataToBox(rs);
		
		}catch (PException pe){
			Logging.err.println(this.getClass().getName()+"."+"selectListBox()"+"=>\n" + pe+"\n" );
		}finally{
			if(pconn == null)
				try{close(conn, rs,  pstmt);}catch(Exception e){}
			else
				try{close(   pstmt);}catch(Exception e){}
				try{close( rs);}catch(Exception e){}
		}
		return box;
	
	}
    
    /**
     * 
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public JSONObject selectListJson(Box ubox, String input_value[]) throws PException{
    	return selectListJson(null,ubox,input_value); 
    }
    /**
     * 
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public JSONObject selectListJson(Connection pconn,Box ubox, String input_value[]) throws PException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONObject jsonObj = new JSONObject();
        ResultSetMetaData rm = null;

		DAORtnObjBuilder rob = new DAORtnObjBuilder();
		DAOStatementExecutor xe = new DAOStatementExecutor();

		try{

        	
			if(pconn == null){
				conn = getConnection(ubox.getString("db"));
				rs = xe.doExecuteSelectLoggable( conn,  pstmt, rs, ubox,  input_value);			  
			}else{
				rs = xe.doExecuteSelectLoggable( pconn,  pstmt, rs, ubox,  input_value);
			}
			
			jsonObj = rob.RmDataTojson(rs);

        }catch (PException pe){
			Logging.err.println(this.getClass().getName()+"."+"selectListJson()"+"=>\n" + pe+"\n" );
		}finally{
			if(pconn == null){
				try{close(conn, rs,  pstmt);}catch(Exception e){}
			}else{
				try{close( pstmt);}catch(Exception e){}
				try{close( rs);}catch(Exception e){}
			}
        }
        
        jsonObj.put("trxResultCode", trxResultCode);
        jsonObj.put("trxResultMsg", trxResultMsg);

        return jsonObj;
    }
    
    /**
     * 
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public Document selectListXml(Box ubox, String input_value[]) throws PException{
    	return selectListXml(null , ubox,  input_value);
    }
    
    /**
     * 
     * @param pconn
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public Document selectListXml(Connection pconn,Box ubox, String input_value[]) throws PException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

		DAOStatementExecutor xe = new DAOStatementExecutor();
		DAORtnObjBuilder rob = new DAORtnObjBuilder();
		
        Document listDoc = new Document();
		Element  rtnData   = new Element("XmlRtn");

        
        try{

			if(pconn == null){
				conn = getConnection(ubox.getString("db"));
				rs = xe.doExecuteSelectLoggable( conn,  pstmt, rs, ubox,  input_value);			  
			}else{
				rs = xe.doExecuteSelectLoggable( pconn,  pstmt, rs, ubox,  input_value);
			}
			
			listDoc = rob.RmDataToXml(rs);

		}catch (PException pe){
			Logging.err.println(this.getClass().getName()+"."+"selectListXml()"+"=>\n" + pe+"\n" );
			rtnData.addContent(new Element("trxResultCode").addContent(trxResultCode));
			rtnData.addContent(new Element("trxResultMsg").addContent(trxResultMsg)); 
			
		}finally{
			if(pconn == null)
				try{close(pconn, rs,  pstmt);}catch(Exception e){}
			else
				try{close( pstmt);}catch(Exception e){}
				try{close( rs);}catch(Exception e){}
        }
        
        return listDoc;
    }

	public String selectOneData(Box ubox, String input_value[])
			throws PException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = ubox.getString("query");
		String rtnStr = "";


		try {

				conn = getConnection(ubox.getString("db"));
				pstmt = conn.prepareStatement(query);

				 for ( int input = 0 ; input < input_value.length ; input++ )
				  {
					 pstmt.setString(input +1,input_value[input]);
				  }

				  rs = pstmt.executeQuery();
				  while(rs.next()){
					  rtnStr = rs.getString(1);
				  }

		} catch (SQLException se) {
			Logging.err.println(this.getClass().getName() + "."+ "selectOneData()" + "=>\n" + se + "\n");
			throw(new PException(se));
		} finally {
			if (conn == null)
				try {
					close(conn, rs, pstmt);
				} catch (Exception e) {
				}
			else
				try {
					close(pstmt);
				} catch (Exception e) {
				}
			try {
				close(rs);
			} catch (Exception e) {
			}
		}
		return rtnStr;

	}
    
    /**
     * 
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public Document updateObjectDom(Box ubox, String input_value[]) throws PException{
    	return updateObjectDom(null, ubox,input_value);
    }
    /**
     * 
     * @param pconn
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public Document updateObjectDom(Connection pconn,Box ubox, String input_value[]) throws PException{

    	DAOStatementExecutor xe = new DAOStatementExecutor();
		DAORtnObjBuilder rob = new DAORtnObjBuilder();
        
		String rtn_result[] = new String[2];
		
		Document listDoc = new Document();

		try{
			rtn_result = xe.updateObject(pconn, ubox, input_value);
		}catch(PException pe){
			Logging.err.println(this.getClass().getName()+"."+"updateObjectDom()"+"=>\n" + pe+"\n" );
			rtn_result[0] = "N";
			rtn_result[1] = this.getClass().getName()+"."+"updateObjectDom()"+"=>\n" + pe+"\n" ;
		}finally{
			listDoc = rob.trxRtnXml(rtn_result);
		}
		return listDoc;
    }
    
    /**
     * 
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public Box updateObjectBox(Box ubox, String input_value[]) throws PException{
    	return updateObjectBox(null,ubox,input_value);
    }
    
    /**
     * 
     * @param pconn
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public Box updateObjectBox(Connection pconn,Box ubox, String input_value[]) throws PException{

    	DAOStatementExecutor xe = new DAOStatementExecutor();
		DAORtnObjBuilder rob = new DAORtnObjBuilder();
        
		String rtn_result[] = new String[2];
		
		Box rbox = new Box();

		try{
			rtn_result = xe.updateObject(pconn, ubox, input_value);
		}catch(PException pe){
			Logging.err.println(this.getClass().getName()+"."+"updateObjectBox()"+"=>\n" + pe+"\n" );
			rtn_result[0] = "N";
			rtn_result[1] = this.getClass().getName()+"."+"updateObjectBox()"+"=>\n" + pe+"\n" ;
		}finally{
			rbox = rob.trxRtnBox(rtn_result);
		}
		return rbox;
    }
    
    /**
     * 
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public JSONObject updateObjectJson(Box ubox, String input_value[]) throws PException{
    	return updateObjectJson(null,ubox,input_value);
    }
    /**
     * 
     * @param pconn
     * @param ubox
     * @param input_value
     * @return
     * @throws PException
     */
    public JSONObject updateObjectJson(Connection pconn,Box ubox, String input_value[]) throws PException{

    	DAOStatementExecutor xe = new DAOStatementExecutor();
		DAORtnObjBuilder rob = new DAORtnObjBuilder();
        
		String rtn_result[] = new String[2];
		JSONObject jobj = new JSONObject();

		try{
			rtn_result = xe.updateObject(pconn, ubox, input_value);
		}catch(PException pe){
			Logging.err.println(this.getClass().getName()+"."+"updateObjectJson()"+"=>\n" + pe+"\n" );
			pe.printStackTrace();

			rtn_result[0] = "N";
			rtn_result[1] = this.getClass().getName()+"."+"updateObjectJson()"+"=>\n" + pe+"\n" ;
		}finally{
			jobj = rob.trxRtnJson(rtn_result);
		}
		return jobj;
    }
    
	public String updateObjectLoggable(Box ubox , String input_value[]) throws PException
	{
		cmnUtil cmnUtil = new cmnUtil();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String db = ubox.getString("db");
		String cflag = cmnUtil.checkNull2Value(ubox.getString("cflag"),"N");
		
		String trxResultCode = "Y";
		String trxResultMsg  ="정상 처리 되었습니다.";
	       
		int txRs = 0;

		String query = ubox.getString("query");
		

		try
		{
		    conn = getConnection(db);
		    pstmt = conn.prepareStatement(query);

		    for ( int input = 0 ; input < input_value.length ; input++ )
		    {
		    	if(cflag.equals(input+"")){
		    		String contents = input_value[input];
		    		StringReader sr = new StringReader(contents); 
		    		pstmt.setCharacterStream(input+1, sr,contents.length() );
	                //Logging.info.println("===========>Query Parameter ["+input+"]>>\n"+input_value[input]);
		    	}else{
		    		pstmt.setString(input +1,input_value[input]);
	                //Logging.info.println("Query Parameter ["+input+"]>>"+input_value[input]);
		    	}
		    }

		    txRs = pstmt.executeUpdate();


		} catch (SQLException se) {

            Logging.err.println(this.getClass().getName()+"."+"updateObject() SQLException"+"=>" + se);
            Logging.err.println("Query: "+query);
            for ( int input = 0 ; input < input_value.length ; input++ )
            {
                Logging.err.println("Query Parameter ["+input+"]>>"+input_value[input]);
            }
			trxResultCode = "N";
            trxResultMsg = se.getErrorCode() + ", " + this.getClass().getName()+"."+"updateObject() SQLException"+"=>" + se;
            
            throw new PException(se);
            
		} catch (Exception e) {
		    
			Logging.err.println(this.getClass().getName()+"."+"updateObject() Exception"+"=>" + e);
		    trxResultCode = "N";
		    trxResultMsg = this.getClass().getName()+"."+"updateObject() Exception"+"=>" + e;
            throw new PException(e);
            
		} finally {
		   try{close(conn);}catch(Exception e){}
		   try{close(pstmt);}catch(Exception e){}
		}
        
		 return trxResultCode;
	}
	
	
    
    /**
     * 
     * @param ubox
     * @param input_value
     * @param conn
     * @return
     * @throws PException
     */
	public String updateObjectLoggable(Box ubox , String input_value[], Connection conn) throws PException
	{
		PreparedStatement pstmt = null;

		String trxResultCode = "SUCCESS";
		String trxResultMsg  = "정상 처리 되었습니다.";
	       
		int txRs = 0;

		String query = ubox.getString("query");
		

		try
		{
		    pstmt = new LoggableStatement(conn,query);//CLAB 처리를 위한 LaggableStatement 사용
		    //pstmt = conn.prepareStatement(query);

		    for ( int input = 0 ; input < input_value.length ; input++ )
		    {
			   pstmt.setString(input +1,input_value[input]);
			  // Logging.dev.println("input_value[" + input + "][" + input_value[input]+"]");
		    }

		    txRs = pstmt.executeUpdate();

		} catch (SQLException se) {

	            Logging.err.println(this.getClass().getName()+"."+"updateObject() SQLException"+"=>" + se);
	            Logging.err.println("Query: "+query);
	            for ( int input = 0 ; input < input_value.length ; input++ )
	            {
	                Logging.err.println("Query Parameter ["+input+"]>>"+input_value[input]);
	            }
			trxResultCode = "N";
            trxResultMsg = se.getErrorCode() + ", " + this.getClass().getName()+"."+"updateObject() SQLException"+"=>" + se;
            throw new PException(se);
		} catch (Exception e) {
		    Logging.err.println(this.getClass().getName()+"."+"updateObject() Exception"+"=>" + e);
		    trxResultCode = "N";
		    trxResultMsg = this.getClass().getName()+"."+"updateObject() Exception"+"=>" + e;
            throw new PException(e);
		} 
        
		 return trxResultCode;
	}
    
	public Box callStdProcedureObject(Box ubox , String input_value[], int outcnt)
	{

		Box box = new Box();

		Connection conn = null;
		CallableStatement cstmt = null;

		String query = ubox.getString("query");
		String db = ubox.getString("db");

		String trxResultCode = "Y";
		String trxErrMsg="";

		int input, output, k;
		int outnum = input_value.length + 1;

		try
		{
		   conn = getConnection(db);

		   cstmt = conn.prepareCall(query);

		   for ( input = 0 ; input < input_value.length ; input++ ){
			  cstmt.setString(input +1,input_value[input]);
		   }

		   for( output = input ; output < input_value.length + outcnt ; output++){
			  cstmt.registerOutParameter(output+1 , Types.VARCHAR );
		   }

		   cstmt.execute();

		   for( k = 0; k < outcnt ; k++){
			  box.put("result"+k, cstmt.getString(outnum+k));
		   }

		   box.put("outcnt", outcnt+"");

		} catch (SQLException se) {

		   Logging.err.println(this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + se);
		   trxErrMsg = this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + se;
		   trxResultCode = "N";

		} catch (Exception e) {

		   Logging.err.println(this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + e);
		   trxErrMsg = this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + e;
		   trxResultCode = "N";

		} finally {
		   try{close(conn);}catch(Exception e){}
		   try{close(cstmt);}catch(Exception e){} 
		   
		   box.put("trxResultCode", trxResultCode);
		   box.put("trxErrMsg", trxErrMsg);
		   
		   return box;

		}
	}


	//------------------------------------------------------------------------------
	//   Method명    : callStdProcedureObject)_
	//
	//   DESC        : stored procedure등의 data base object에 대한 실행 메소드이다
	//               input_value[]: object의 in 값을 정의한 배열
	//               outcnt      : out되는 요소수를 정의 한다.
	//   참조 Method  :
	//   참조 변수명  :
	//
	//
	//------------------------------------------------------------------------------
	public Box callStdProcedureObject(Box ubox , String input_value[], int outcnt, Connection conn )
	{

		Box box = new Box();
		CallableStatement cstmt = null;

		String query = ubox.getString("query");

		String trxResultCode = "Y";
		String trxErrMsg="";

		int input, output, k;
		int outnum = input_value.length + 1;

		try
		{
		   cstmt = conn.prepareCall(query);

		   for ( input = 0 ; input < input_value.length ; input++ ){
			  cstmt.setString(input +1,input_value[input]);
		   }

		   for( output = input ; output < input_value.length + outcnt ; output++){
			  cstmt.registerOutParameter(output+1 , Types.VARCHAR);
		   }

		   cstmt.execute();

		   for( k = 0; k < outcnt ; k++){
			  box.put("result"+k, cstmt.getString(outnum+k));
		   }

		   box.put("outcnt", outcnt+"");


		} catch (SQLException se) {

		   Logging.err.println(this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + se);
		   trxErrMsg = this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + se;
		   trxResultCode = "N";

		} catch (Exception e) {

		   Logging.err.println(this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + e);
		   trxErrMsg = this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + e;
		   trxResultCode = "N";

		} finally {
		   try{close(cstmt);}catch(Exception e){}
		   
		}
		return box;
	}

	

    //------------------------------------------------------------------------------
	//   Method명    : callStdProcedureObject)_
	//
	//   DESC        : stored procedure등의 data base object에 대한 실행 메소드이다
	//               input_value[]: object의 in 값을 정의한 배열
	//               argcnt      : total argument
	//               outcnt      : out되는 요소수를 정의 한다.
	//   참조 Method  :
	//   참조 변수명  :
	//
	//
	//------------------------------------------------------------------------------
	public Box callStdProcedureObject(Box ubox , String input_value[], int argcnt , int outcnt, Connection conn )
	{

		Box box = new Box();
		CallableStatement cstmt = null;

		String query = ubox.getString("query");

		String trxResultCode = "Y";
		String trxErrMsg="";

		int input, output, k;
		int outnum = input_value.length + 1;

		try
		{
		   cstmt = conn.prepareCall(query);

		   for ( input = 0 ; input < input_value.length ; input++ ){
			  cstmt.setString(input +1,input_value[input]);
		   }

		   for( output = argcnt-outcnt ; output < argcnt ; output++){
			  cstmt.registerOutParameter(output+1 , Types.VARCHAR);
		   }

		   cstmt.execute();

		   for( k = 0; k < outcnt ; k++){
			  box.put("result"+k, cstmt.getString(argcnt-outcnt+k+1));

		   }

		   box.put("outcnt", outcnt+"");

		} catch (SQLException se) {

		   Logging.err.println(this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + se);
		   trxErrMsg = this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + se;
		   trxResultCode = "N";

		} catch (Exception e) {

		   Logging.err.println(this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + e);
		   trxErrMsg = this.getClass().getName()+"."+"callStdProcedureObject()"+"=>" + e;
		   trxResultCode = "N";

		} finally {
		   try{close(cstmt);}catch(Exception e){}
		   
		}
		return box;
	}
}
