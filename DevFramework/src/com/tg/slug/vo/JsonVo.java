package com.tg.slug.vo;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tg.slug.logging.Logging;
import com.tg.slug.vo.box.VectorBox;

public class JsonVo {
	public JsonVo(){}
	public JSONObject vectorBoxToJsonVo(VectorBox box){
        // Logging.dev.println("< DaoHandler >< RmDataTojson >  Execute Start   ");
    	JSONArray jsonArray = new JSONArray();
    	JSONObject jsonObj = new JSONObject();
    	
    	String columnName = "";
    	
    	Vector colh = null;
    	

    		colh = box.getVector("columnHeader");
            int COL_COUNT = colh.size();//rm.getColumnCount();
            int TOTAL_COUNT = 0;
            int row_count = 0;
            String temp_data = "";
            boolean isTotCount = false;
            JSONObject cobj = new JSONObject();
            
            Vector[] vColumns = new Vector[COL_COUNT];
            JSONObject[] obj = new JSONObject[COL_COUNT];
             
            //while(rs.next()){
            for(int v = 0 ; v < COL_COUNT; v++){
            	columnName = box.getString("columnHeader");
            	vColumns[v] = box.getVector(columnName);
            	for(int i = 0; i < vColumns[v].size(); i++){
                    
                    //if(row_count==1)
                    //cobj.put("cname"+i, rm.getColumnName(i+1));
                        
                    temp_data = box.getString("columnHeader");//rs.getString(i+1);
                    if( temp_data == null) temp_data = "";
                    obj[v].put(columnName, temp_data);
                    
                    //if(rm.getColumnName(i+1).equals("TOTAL_COUNT")) {
                    //    isTotCount = true;
                    //}
                }
                jsonArray.add(obj[v]);
                //if(isTotCount) {
                //    TOTAL_COUNT = rs.getInt("TOTAL_COUNT");
                //}else{
                //    TOTAL_COUNT++;
                //}

            }
            jsonObj.put("COL_NAME", cobj);
            jsonObj.put("DATA_LIST", jsonArray);
            //jsonObj.put("TOTAL_COUNT", TOTAL_COUNT);

	
		return jsonObj;
	}
}
