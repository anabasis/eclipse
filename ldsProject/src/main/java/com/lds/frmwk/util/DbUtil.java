package com.lds.frmwk.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import oracle.sql.CLOB;

public class DbUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommUtil.class);
	
	private String sid ;
	private String curDate ;
	private String url ;
	
	// DB Write File
	public static final int nCHAR = 1;
	public static final int nINT = 2;
	public static final int nSTRING = 12;
	public static final int nFLOAT = 91;
	public static final int nBLOB = 2004;
	public static final int nROWID = -8;
	public static final int nLONGRAW = -4;
	public static final int nLONG = -1;
	
	public static final String newline = System.getProperty("line.separator");
	
	public DbUtil() {
	}
	
	public DbUtil(String sid, String url, String db_type, String proc_day,String db_option) {
		this.getDbType(sid, url, db_type, proc_day, db_option);
	}

	public String getSid() {
		return sid;
	}

	public String getCurDate() {
		return curDate;
	}

	public String getUrl() {
		return url;
	}
	
	public int update(SqlMapClient sqlMap, String query, HashMap<String, String> paramMap ) {
		int ret = 0;
		logger.debug("########### SIEM_EVENT_LOG UPDATE ################" + paramMap.toString());
		
		try {
			sqlMap.startTransaction();
			ret = sqlMap.update(query , paramMap);
			sqlMap.commitTransaction();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public int insert(SqlMapClient sqlMap, String query, HashMap<String, String> paramMap ) {
		int ret = 0;
		logger.debug("########### SIEM_EVENT_LOG INSERT ################" + paramMap.toString());
		
		try {
			sqlMap.startTransaction();
			sqlMap.insert(query , paramMap);
			sqlMap.commitTransaction();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	public void getDbType(String sid, String url, String db_type, String proc_day, String db_option) {

		if ("mssql".equals(db_type)) {
			if (sid.toUpperCase().contains("YYYYMMDD")) {
				curDate = CommUtil.getDay("yyyyMMdd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYYMMDD", curDate);
				logger.debug(" ExcuteDAO sid2 : " + sid);
			} else if (sid.toUpperCase().contains("YYYY-MM-DD")) {
				curDate = CommUtil.getDay("yyyy-MM-dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM-DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM")) {
				curDate = CommUtil.getDay("yyyy-MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM_DD")) {
				curDate = CommUtil.getDay("yyyy_MM_dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM_DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM")) {
				curDate = CommUtil.getDay("yyyy_MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM", curDate);
			}
			//logger.debug(" ExcuteDAO sid1 : " + sid + " curDate : " + curDate);
			url += ";databaseName=" + sid + (("".equals(db_option))?";":";"+db_option);;
		} else if ("mysql".equals(db_type)||"postgres".equals(db_type)) {

			if (sid.toUpperCase().contains("YYYYMMDD")) {
				curDate = CommUtil.getDay("yyyyMMdd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYYMMDD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM-DD")) {
				curDate = CommUtil.getDay("yyyy-MM-dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM-DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM")) {
				curDate = CommUtil.getDay("yyyy-MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM_DD")) {
				curDate = CommUtil.getDay("yyyy_MM_dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM_DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM")) {
				curDate = CommUtil.getDay("yyyy_MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM", curDate);
			}
			url += "/" + sid +(("".equals(db_option))?"":"?"+db_option);
		} else if ("oracle".equals(db_type)) {
			if (sid.toUpperCase().contains("YYYYMMDD")) {
				curDate = CommUtil.getDay("yyyyMMdd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYYMMDD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM-DD")) {
				curDate = CommUtil.getDay("yyyy-MM-dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM-DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM")) {
				curDate = CommUtil.getDay("yyyy-MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM_DD")) {
				curDate = CommUtil.getDay("yyyy_MM_dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM_DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM")) {
				curDate = CommUtil.getDay("yyyy_MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM", curDate);
			}
			url += ":" + sid;
		} else if ("sqlite".equals(db_type)) {
			if (sid.toUpperCase().contains("YYYYMMDD")) {
				curDate = CommUtil.getDay("yyyyMMdd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYYMMDD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM-DD")) {
				curDate = CommUtil.getDay("yyyy-MM-dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM-DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY-MM")) {
				curDate = CommUtil.getDay("yyyy-MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY-MM", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM_DD")) {
				curDate = CommUtil.getDay("yyyy_MM_dd", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM_DD", curDate);
			} else if (sid.toUpperCase().contains("YYYY_MM")) {
				curDate = CommUtil.getDay("yyyy_MM", Integer.parseInt(proc_day));
				sid = sid.replace("YYYY_MM", curDate);
			}
			url += ":" + sid;
		}
		this.sid = sid;
		this.url = url;
		this.curDate = curDate;
	}
	
	public static String getTableType(String query_table, String startDate) {
		
		String year = ("".contentEquals(startDate))?CommUtil.getCurrentTime().substring(0, 4):CommUtil.getDay(startDate,"yyyy");
		String month = ("".contentEquals(startDate))?CommUtil.getCurrentTime().substring(4, 6):CommUtil.getDay(startDate,"MM");
		String day = ("".contentEquals(startDate))?CommUtil.getCurrentTime().substring(6, 8):CommUtil.getDay(startDate,"dd");
		
		logger.debug("######## year : " + year);
		logger.debug("######## month : " + month);
		logger.debug("######## day : " + day);
		
		if (query_table.toUpperCase().contains("YYYY_MM_DD")) {
			query_table = query_table.replaceAll("YYYY_MM_DD", year + "_" + month + "_" + day);
		} else if (query_table.toUpperCase().contains("YYYY-MM-DD")) {
			query_table = query_table.replaceAll("YYYY-MM-DD", year + "-" + month + "-" + day);
		} else if (query_table.toUpperCase().contains("YYYYMMDD")) {
			query_table = query_table.replaceAll("YYYYMMDD", year + month + day);
		} else if (query_table.toUpperCase().contains("YYYY_MM")) {

			query_table = query_table.replaceAll("YYYY_MM", year + "_" + month);
		} else if (query_table.toUpperCase().contains("YYYY-MM")) {
			query_table = query_table.replaceAll("YYYY-MM", year + "-" + month);
		} else if (query_table.toUpperCase().contains("YYYYMM")) {
			query_table = query_table.replaceAll("YYYYMM", year + month);
		}
		return query_table;
	}
	
	public static String getColType(String db_type, String col_type, String col_name, String date_format, String startDate, String endDate ) {
		StringBuffer queryString = new StringBuffer();
		
		if (col_type.contains("date")) {
			if ("mssql".equals(db_type)) {
				queryString.append(" and " + col_name + "  > convert(datetime, '" + startDate
						+ "' , 120 ) and " + col_name + " <= convert(datetime ,'" + endDate + "' , 120)");
			} else if ("oracle".equals(db_type)) {
				queryString.append(" and " + col_name + "  > to_date('" + startDate + "','" + date_format
						+ "') and  " + col_name + " <= to_date('" + endDate + "' , '" + date_format + "')");
			} else if ("mysql".equals(db_type)) {
				queryString.append(" and " + col_name + "  > date_format('" + startDate + "','"
						+ date_format + "') and " + col_name + " <= date_format('" + endDate + "','"
						+ date_format + "')");
			} else if ("postgresql".equals(db_type)) {
				queryString.append(" and \"" + col_name + "\"  > to_timestamp('" + startDate + "','"
						+ date_format + "') and \"" + col_name + "\" <= to_timestamp('" + endDate + "','"
						+ date_format + "')");
			}
		} else if (col_type.contains("string")) {
			if ("mssql".equals(db_type)) {
				queryString.append(" and convert(datetime , " + col_name + ", 120)  > convert(datetime, '"
						+ startDate + "' , 120 ) and convert(datetime , " + col_name
						+ ", 120) <= convert(datetime ,'" + endDate + "' , 120)");
			} else if ("oracle".equals(db_type)) {
				queryString.append(" and to_date(" + col_name + ",'" + date_format + "')  > to_date('"
						+ startDate + "','" + date_format + "') and  to_date(" + col_name + ") <= to_date('"
						+ endDate + "' , '" + date_format + "')");
			} else if ("mysql".equals(db_type)) {
				queryString.append(" and date_format(" + col_name + ",'" + date_format
						+ "')  > date_format('" + startDate + "','" + date_format + "') and date_format("
						+ col_name + ",'" + date_format + "')  <= date_format('" + endDate + "','"
						+ date_format + "')");
			} else if ("postgresql".equals(db_type)) {
				queryString.append(" and to_timestamp(\"" + col_name + "\",'" + date_format
						+ "')  > to_timestamp('" + startDate + "','" + date_format + "') and to_timestamp(\""
						+ col_name + "\",'" + date_format + "')  <= to_timestamp('" + endDate + "','"
						+ date_format + "')");
			}
			
		}
		return queryString.toString();
	}
	
	public static int dbWriteFile(ResultSet rs, String fileName, String dataType, String dataSplt) throws Exception {
		
		int res = 1;
		
		FileUtil fileUtil = new FileUtil(fileName, true);
		// logger.debug("encoding : " + System.getProperty("file.encoding"));
		ResultSetMetaData rmd = rs.getMetaData();
		int columnCount = rmd.getColumnCount();
		String columnName = null;

		BufferedWriter out = null;
		StringBuffer resultBuffer = null;
		int rows = 0;
		
		try {
			logger.debug("### start_time : " + System.currentTimeMillis());
			out = new BufferedWriter(new FileWriter(fileName,true), 16384); // ?ŒŒ?¼ê°ì²´ ?“°ê¸? ?œ„?•œ BufferedWriter ?ƒ?„±
			
			
			if ("map".equals(dataType)) {
				// ?°?´?„° ë£¨í”„ ?Œë©´ì„œ ?ŒŒ?¼ ???ž¥ ///////////////////////
				
				while (rs.next()) {
					rows ++;

					resultBuffer = new StringBuffer(); // ?ƒˆë¡œìš´ ?¼?¸?„ ?ˆ˜ì§‘í•˜ê¸? ?œ„?•œ ë²„í¼ê°ì²´ë¥? ?ƒ?„±.
					
					for (int i = 1; i <= columnCount; i++) {
						columnName = rmd.getColumnName(i);
						if (i != columnCount) {
							switch (rmd.getColumnType(i)) {
							case nSTRING:
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(rs.getString(i)) + ",");
								break;
							case nCHAR:
								resultBuffer.append(
										columnName + "=" + (CommUtil.NullToStr(rs.getString(i))).trim() + ",");
								break;
							case nBLOB:
								resultBuffer.append(
										columnName + "=" + CommUtil.NullToObj(rs.getBinaryStream(i)) + ",");
								break;
							case nLONG:
								resultBuffer.append(columnName + "="
										+ CommUtil.characterStreamToString(rs.getCharacterStream(i)) + ",");
								break;
							case nLONGRAW:
								resultBuffer.append(columnName + "=" + rs.getBytes(i) + ",");
								break;
							case java.sql.Types.CLOB:
								oracle.sql.CLOB clob = null;
								Reader reader = null;
								int rcnt = 0;
								char[] char_array;
								clob = (CLOB) rs.getClob(i);
								reader = clob.getCharacterStream();
								char_array = new char[(int) (clob.length())];
								rcnt = reader.read(char_array);
								String contents = (new String(char_array));
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(contents) + ",");
								reader.close();
								break;
							default:
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(rs.getString(i)) + ",");
							}
						} else {
							switch (rmd.getColumnType(i)) {
							case nSTRING:
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(rs.getString(i)));
								break;
							case nCHAR:
								resultBuffer
										.append((columnName + "=" + CommUtil.NullToStr(rs.getString(i))).trim());
								break;
							case nBLOB:
								resultBuffer.append(columnName + "=" + CommUtil.NullToObj(rs.getBinaryStream(i)));
								break;
							case nLONG:
								resultBuffer.append(columnName + "="
										+ CommUtil.characterStreamToString(rs.getCharacterStream(i)));
								break;
							case nLONGRAW:
								resultBuffer.append(columnName + "=" + rs.getBytes(i));
								break;
							case java.sql.Types.CLOB:
								oracle.sql.CLOB clob = null;
								Reader reader = null;
								int rcnt = 0;
								char[] char_array;

								clob = (CLOB) rs.getClob(i);
								reader = clob.getCharacterStream();
								char_array = new char[(int) (clob.length())];
								rcnt = reader.read(char_array);
								String contents = (new String(char_array));
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(contents));
								reader.close();
								break;
							default:
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(rs.getString(i)));
							}
						}
					}

					if (rs.isLast()) {
						/*
						if ("Y".equals(adv.getDaemonProcedure())) {
							param.put("db_seq", Integer.parseInt(rs.getString(adv.getDaemonProcedureParamColumn())));
							try {
								sqlMap.startTransaction();
								sqlMap.update("config.updateAgentProcedureSeq", param);
								sqlMap.commitTransaction();
							} finally {
								sqlMap.endTransaction();
							}

							logger.debug("lastSeq : " + lastSeq);
							prcTotalcount = rs.getRow();
						}
						*/

						//resultBuffer.append("");
						resultBuffer.append(newline);
					} else {
						resultBuffer.append(newline);
					}

					// ?ˆ˜ì§‘ëœ ?•œì¤„ì„ ?ŒŒ?¼?— ?¨?„£?Š”?‹¤.
					if (fileUtil.writeFile(resultBuffer.toString(), out) == 0) {
						res = 0; // ?ŒŒ?¼?“°ê¸°ì¤‘ ?—?Ÿ¬ê°? ë°œìƒ?œ ê²½ìš° ?—?Ÿ¬ê°? ?„¸?Œ…
					}
					;

				}
			} else if ("splt".equals(dataType)) {
				String splt = "";
				if ("space".equals(dataSplt)) {
					splt = " ";
				} else if ("tab".equals(dataSplt)) {
					splt = "\t";
				} else {
					splt = ",";
				}
				while (rs.next()) {

					rows ++;
					
					resultBuffer = new StringBuffer(); // ?ƒˆë¡œìš´ ?¼?¸?„ ?ˆ˜ì§‘í•˜ê¸? ?œ„?•œ ë²„í¼ê°ì²´ë¥? ?ƒ?„±.
					
					for (int i = 1; i <= columnCount; i++) {
						if (i != columnCount) {
							switch (rmd.getColumnType(i)) {
							case nSTRING:
								resultBuffer.append(CommUtil.NullToStr(rs.getString(i)) + splt);
								break;
							case nCHAR:
								resultBuffer.append((CommUtil.NullToStr(rs.getString(i))).trim() + splt);
								break;
							case nBLOB:
								resultBuffer.append(CommUtil.NullToObj(rs.getBinaryStream(i)) + splt);
								break;
							case nLONG:
								resultBuffer.append(
										CommUtil.characterStreamToString(rs.getCharacterStream(i)) + splt);
								break;
							case nLONGRAW:
								resultBuffer.append(rs.getBytes(i) + splt);
								break;
							case java.sql.Types.CLOB:
								oracle.sql.CLOB clob = null;
								Reader reader = null;
								int rcnt = 0;
								char[] char_array;

								clob = (CLOB) rs.getClob(i);
								reader = clob.getCharacterStream();
								char_array = new char[(int) (clob.length())];
								rcnt = reader.read(char_array);
								String contents = (new String(char_array));
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(contents) + splt);
								reader.close();
								break;
							default:
								resultBuffer.append(CommUtil.NullToStr(rs.getString(i)) + splt);
							}
						} else {
							switch (rmd.getColumnType(i)) {
							case nSTRING:
								resultBuffer.append(CommUtil.NullToStr(rs.getString(i)));
								break;
							case nCHAR:
								resultBuffer.append((CommUtil.NullToStr(rs.getString(i))).trim());
								break;
							case nBLOB:
								resultBuffer.append(CommUtil.NullToObj(rs.getBinaryStream(i)));
								break;
							case nLONG:
								resultBuffer.append(CommUtil.characterStreamToString(rs.getCharacterStream(i)));
								break;
							case nLONGRAW:
								resultBuffer.append(rs.getBytes(i));
								break;
							case java.sql.Types.CLOB:
								oracle.sql.CLOB clob = null;
								Reader reader = null;
								int rcnt = 0;
								char[] char_array;
								clob = (CLOB) rs.getClob(i);
								reader = clob.getCharacterStream();
								char_array = new char[(int) (clob.length())];
								rcnt = reader.read(char_array);
								String contents = (new String(char_array));
								resultBuffer.append(columnName + "=" + CommUtil.NullToStr(contents));
								reader.close();
								break;
							default:
								resultBuffer.append(CommUtil.NullToStr(rs.getString(i)));
							}
						}
					}

					if (rs.isLast()) {
						/*
						if ("Y".equals(adv.getDaemonProcedure())) {
							int dbseq = rs.getInt(adv.getDaemonProcedureParamColumn());
							param.put("db_seq", dbseq);
							try {
								sqlMap.startTransaction();
								sqlMap.update("config.updateAgentProcedureSeq", param);
								sqlMap.commitTransaction();
							} finally {
								sqlMap.endTransaction();
							}

							logger.debug("dbseq : " + dbseq);
							prcTotalcount = rs.getRow();
						}
						*/
						//resultBuffer.append("");
						resultBuffer.append(newline);
					} else {
						resultBuffer.append(newline);
					}

					// ?ˆ˜ì§‘ëœ ?•œì¤„ì„ ?ŒŒ?¼?— ?¨?„£?Š”?‹¤.
					if (fileUtil.writeFile(resultBuffer.toString(), out) == 0) {
						res = 0; // ?ŒŒ?¼?“°ê¸°ì¤‘ ?—?Ÿ¬ê°? ë°œìƒ?œê²½ìš° 0?„ ?…‹?Œ….
					}
					;
				}
			}
			logger.debug("### rows : " + rows);
			logger.debug("### end_time : " + System.currentTimeMillis());

		} catch (IOException e) {
			e.printStackTrace();
			res = 0; // ?ŒŒ?¼?“°ê¸°ì¤‘ ?—?Ÿ¬ê°? ë°œìƒ?•œê²½ìš° 0?„ ì¶œë ¥
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}