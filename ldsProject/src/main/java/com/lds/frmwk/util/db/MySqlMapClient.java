package com.lds.frmwk.util.db;

import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class MySqlMapClient {

	private static volatile SqlMapClient sqlMap;
	private static final Logger logger = LoggerFactory.getLogger(MySqlMapClient.class);
	private static int no;
	private final static String resource = "com/lds/frmwk/db/xml/sqlmap-config.xml";
	

	public static SqlMapClient getSqlMapInstance() {
		return getSqlMapInstance(resource);
	}

	public static SqlMapClient getSqlMapInstance(String resource) {
		Reader reader = null;
		try {
			if (sqlMap == null) {
				synchronized (SqlMapClient.class) {
					if (sqlMap == null) {

						reader = Resources.getResourceAsReader(resource);
						/*
						int readData;
						String change=""; 
						while((readData = reader.read()) != -1) 
						    change+= Character.toString((char) readData);
						logger.debug("SqlMapClientBuilder # : " + change);
						*/
						sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
						reader.close();
						no++;
						logger.debug("SqlMapClientBuilder New Instance Count[" + no + "]");
					}
				}
			} else {
				logger.debug("SqlMapClientBuilder Old Instance Count[" + no + "]");
			}
		} catch (Exception e) {
			logger.error("MySqlMapClient.getSqlMapInstance Exception : " + e.getMessage());
			e.printStackTrace();
			try {
				if(reader != null)
					reader.close();
			}catch(Exception re) {
				re.printStackTrace();
			}
		}finally {
			try {
				if(reader != null) 
					reader.close();
			}catch(Exception re) {
				re.printStackTrace();
			}
		}
		return sqlMap;
	}
}
