package com.slug.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.slug.exception.PException;
import com.slug.exception.SysException;
import com.slug.logging.Logging;
import com.slug.vo.box.Box;
import com.slug.vo.box.VectorBox;

public class DAORtnObjBuilder {
 
	/**
	 * ����
	 */
	public DAORtnObjBuilder(){
        super();
    }
	/*
	private Object createBean(
	        ResultSet rs,
	        Class type,
	        PropertyDescriptor[] props,
	        int[] columnToProperty,
	        int cols)
	        throws SQLException {

	        Object bean = this.newInstance(type);        
	        Object value = null;
	        ResultSetMetaData meta = rs.getMetaData();
	        for (int i = 1; i <= cols; i++) {
	            if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
	                continue;
	            }
	            
	            PropertyDescriptor prop = props[columnToProperty[i]];
	            Class propType = prop.getPropertyType();
	            
	            if ("CLOB".equals(meta.getColumnTypeName(i))) {
	                value = readClob(rs, i);
	            }
	            else {
	                value = rs.getObject(i);
	            }
	            if (propType != null && value == null && propType.isPrimitive()) {
	                value = primitiveDefaults.get(propType);
	            }
	            
	            this.callSetter(bean, prop, value);
	        }
	        return bean;
	    }
	    
	    protected Object readClob(ResultSet rs, int idx) {
	        StringBuffer stringbuffer = new StringBuffer();
	        char[] charbuffer = new char[1024];
	        int read = 0;
	        
	        Reader reader = null;
	        String result = null;
	        try {
	            reader = rs.getCharacterStream(idx);
	            while ((read = reader.read(charbuffer, 0, 1024)) != -1)
	                stringbuffer.append(charbuffer, 0, read);
	            result = stringbuffer.toString();
	        } catch (Exception exception) {
	            System.out.println(exception);
	        } finally {
	            if (reader != null) try { reader.close(); } catch (Exception e){}
	        }
	        return result;
	    }
	    */
	
	/**
	 *  List BO �����͸� json Data �� ��ȯ
     * 
     * @param dataBox
     * @return JSONObject
     * @throws PException
	 */
	public JSONObject RmDataTojson( ResultSet rs) throws PException {
		
		boolean cflag = false;
		
		boolean rtnflag = true;
		String errMsg = "";
		JSONObject rtnMsg =  new JSONObject();//"Sucess";

       // Logging.dev.println("< DaoHandler >< RmDataTojson >  Execute Start   ");
    	JSONArray jsonArray = new JSONArray();
    	JSONObject jsonObj = new JSONObject();
        ResultSetMetaData rm = null;

    	try {
            rm = rs.getMetaData();
            int COL_COUNT = rm.getColumnCount();
            int TOTAL_COUNT = 0;
            int row_count = 0;
            String temp_data = "";
            boolean isTotCount = false;
            JSONObject cobj = new JSONObject();
             
            while(rs.next()){
                row_count++;
                JSONObject obj = new JSONObject();
                for(int i = 0; i < COL_COUNT; i++){
                    
                    if(row_count==1)
                    cobj.put("cname"+i, rm.getColumnName(i+1));
                        
                    temp_data = rs.getString(i+1);
                    if( temp_data == null) temp_data = "";
                    obj.put(rm.getColumnName(i+1), temp_data);
                    
                    if(rm.getColumnName(i+1).equals("TOTAL_COUNT")) {
                        isTotCount = true;
                    }
                }
                jsonArray.add(obj);
                if(isTotCount) {
                    TOTAL_COUNT = rs.getInt("TOTAL_COUNT");
                }else{
                    TOTAL_COUNT++;
                }

            }
            jsonObj.put("COL_NAME", cobj);
            jsonObj.put("DATA_LIST", jsonArray);
            jsonObj.put("TOTAL_COUNT", TOTAL_COUNT);
            rtnMsg.put("responseMsg", "Sucess");

        }catch(SQLException se){
        	errMsg = "<"+this.getClass().getName()+">< RmDataTojson() > Error: "+se.getMessage();
			Logging.err.println(errMsg);
			rtnMsg.put("responseMsg", errMsg);
			se.printStackTrace();

        }
        jsonObj.put("success", rtnflag);
        jsonObj.put("data", rtnMsg);

		return jsonObj;
    }

   	/**
   	 * 
   	 * List BO �����͸� Box Data �� ��ȯ
   	 * 
   	 * @param rs
   	 * @return Box
   	 * @throws PException
   	 */
	public Box RmDataToBox(ResultSet rs) throws PException{

   		ResultSetMetaData rm = null;

		Box box = new VectorBox();
		
		String errMsg = "";

		try {

			rm = rs.getMetaData();

			int count = rm.getColumnCount();

			Vector values[] = new Vector[count];
			Vector columns = new Vector();

			for (int i = 0; i < count; i++)
				values[i] = new Vector();

			String columnLabel = "";

			while (rs.next()) {
				for (int i = 0; i < count;) {
					values[i].add(rs.getString(++i));
				}

			}

			int dataCount = values[0].size();
			box.put("dataCount", dataCount + "");

			for (int i = 0; i < count; i++) {
				columnLabel = rm.getColumnLabel(i + 1);
				columns.add(columnLabel);
				box.putVector(columnLabel, values[i]);
			}

			box.putVector("columns", columns);

		} catch (SQLException se) {

			Logging.err.println(this.getClass().getName() + "." + "RmDataToBox()" + "=>\n" + se);
			errMsg = "\n" + this.getClass().getName() + "."+ "RmDataToBox()" + "\n" + se;
			throw new SysException(this.getClass().getName() + "\n" + errMsg);

		} catch (Exception e) {
			Logging.err.println(this.getClass().getName() + "." + "RmDataToBox()" + "=>\n" + e + "\n");
			errMsg = "\n" + this.getClass().getName() + "."	+ "RmDataToBox()" + "\n" + e;
			throw new SysException(this.getClass().getName() + "\n" + errMsg);
		} 
		return box;

	}	
	
	/**
	 * 
	 * @param rs
	 * @return Document
	 * @throws PException
	 */
	public Document RmDataToXml(ResultSet rs) throws PException{

        ResultSetMetaData rm = null;
		
        
        
        Element  xmllist   = new Element("XmlList");
        Element  data      = new Element("XmlData");
        Element  rtnData   = new Element("XmlRtn");
        Document listDoc   = new Document(xmllist);
        
        Element Info = new Element("DataInfo");
   
        int rowCount = 0;
        String errMsg = "";
        String trxResultCode = "Y";
        String trxResultMsg = "Success Select Data!!";
        

        try{
            
            rm = rs.getMetaData();
           
            
            int columnCount = rm.getColumnCount();
           
            
            for(int i = 0; i<columnCount;i++){
                Info.addContent(new Element("colname_"+(i+1)).addContent(rm.getColumnLabel(i+1)));
            }
            
            xmllist.addContent(Info);
            
            while(rs.next()){

                Element List = new Element("SelectList");
                for(int i = 0; i < columnCount; i++){
                    
                    List.addContent(new Element("col_"+(i+1)).addContent(rs.getString(i + 1)));

                }
                data.addContent(List);
                rowCount++;


            }

          
          
        }catch(Exception e){
            Logging.err.println(this.getClass().getName() + "." + "selectListSimpleXmlType()" + "=>\n" + e + "\n");
            errMsg = "\n" + this.getClass().getName() + "." + "selectListSimpleXmlType()" + "\n" + e;
            trxResultCode = "N";
            trxResultMsg = errMsg;
            //throw new SysException(this.getClass().getName() + "\n" + errMsg);
            
        }
      
        // XML Data Dubug

		rtnData.addContent(new Element("trxResultCode").addContent(trxResultCode));
		rtnData.addContent(new Element("trxResultMsg").addContent(trxResultMsg)); 
        xmllist.addContent(data );
        xmllist.addContent(rtnData );
        
        /*
        try {
            org.jdom.output.XMLOutputter outputter = new org.jdom.output.XMLOutputter();
            org.jdom.output.Format format = org.jdom.output.Format.getPrettyFormat();
            format = format.setEncoding("EUC_KR");
            outputter.setFormat(format);

            outputter.output(listDoc, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
      //  */
        
        return listDoc;
	}

	/**
	 * 
	 * @param rtn_result
	 * @return
	 * @throws PException
	 */
	public Document trxRtnXml( String rtn_result[] ) throws PException {

        Element  xmllist   = new Element("XmlList");
        Element  rtnData   = new Element("XmlRtn");
        Document listDoc   = new Document(xmllist);
        
        
        
		String trxResultCode = rtn_result[0];
		String trxResultMsg = rtn_result[1];
		rtnData.addContent(new Element("trxResultCode").addContent(trxResultCode));
		rtnData.addContent(new Element("trxResultMsg").addContent(trxResultMsg));
        xmllist.addContent(rtnData );

		return listDoc.addContent(xmllist);
		
	}
	
	/**
	 * 
	 * @param rtn_result
	 * @return
	 * @throws PException
	 */
	public Box trxRtnBox( String rtn_result[]) throws PException {
		Box box = new Box();
		
		box.put("trxResultCode",rtn_result[0]);
		box.put("trxResultMsg",rtn_result[1]);

		return box;
	}

	/**
	 * 
	 * @param rtn_result
	 * @return
	 * @throws PException
	 */
	public JSONObject trxRtnJson( String rtn_result[] ) throws PException {
		JSONObject jsonObj = new JSONObject();

        jsonObj.put("trxResultCode", rtn_result[0]);
        jsonObj.put("trxResultMsg", rtn_result[1]);

        return jsonObj;
	}
	//*/

}
