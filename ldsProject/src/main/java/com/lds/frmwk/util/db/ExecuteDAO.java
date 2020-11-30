package com.lds.frmwk.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lds.frmwk.util.CommUtil;
import com.lds.frmwk.util.DbUtil;

public class ExecuteDAO extends AbstractDB {

	private static final Logger logger = LoggerFactory.getLogger(ExecuteDAO.class);

	public ExecuteDAO(String driver) {
		super(driver);
	}

	public int prcTotalcount = -1;
	public int lastSeq = 0;

	public int ExcuteQueryRealTimeData(AgentDbVO adv, String currentTime, String fileName) throws Exception {

		// logger.debug("ExcuteQueryRealTimeData start");

		Connection conn = null; // ?°κ²°λ?
		PreparedStatement psmt = null; // μΏΌλ¦¬λΆ?? κ°μ μ£ΌκΈ° ??΄ PrepareStatement?¬?©
		ResultSet rs = null; // λ¦¬μλ²?
		StringBuffer queryString = new StringBuffer();
		int ret = 0;

		SqlMapClient sqlMap = MySqlMapClient.getSqlMapInstance();

		int res = 1; // ??Ό??± κ²°κ³Ό μΆλ ₯?© 0?΄λ©? ?€?¨, -1?΄λ©? ??Ό??±?΄?Έ? ??¬, 1?΄λ©? ? ?μΆλ ₯?
		String endDate = CommUtil.getCurrentTimeStamp();

		// κ²°κ³Όκ²©λ©?© ?°λͺ? ?€? λ‘κ·Έλ₯? ?Έ??Έ ?¨. (κ²°κ³Ό? N?Όλ‘? κ³ μ )
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("file_name", fileName);
		paramMap.put("daemon_type", adv.getDaemonType());
		paramMap.put("machine_type", adv.getMachineType());
		paramMap.put("source_type", adv.getSourceType());

		if ("Y".equals(adv.getIndexTypeYn())) {
			paramMap.put("index_type", adv.getIndexType());
		}
		paramMap.put("db_type", adv.getDbType());
		paramMap.put("proc_type", adv.getProcType());
		paramMap.put("write_date", endDate);
		paramMap.put("delay_minute", adv.getRunDelay());
		paramMap.put("success_yn", "N");

		try {

			DbUtil dbu = new DbUtil(adv.getDbSid(), adv.getDbUrl(), adv.getDbType(), adv.getProcDay(), adv.getDbOption());
			String nUrl = dbu.getUrl();

			// ?Ή?Όμ²λ¦¬ κΈ°λ‘?΄??μ§?λ§? μ²΄ν¬?΄? μ²λ¦¬

			//HashMap<String, String> logMap = new HashMap<String, String>();
			//logMap.put("daemon_type", adv.getDaemonType());
			//logMap.put("proc_type", adv.getProcType());
			//logMap.put("db_type", adv.getDbType());
			//logMap = (HashMap) sqlMap.queryForObject("config.getLastLog", logMap);

			paramMap = (HashMap) sqlMap.queryForObject("config.getLastLog", paramMap);
			
			String startDate = null;

			// κΈμΌ ?€? ?΄? ₯?΄ ?? κ²½μ° enddateλΆ??° ?€? ?°?΄?°λ₯? κ°?? Έ?¨?€.
			if (paramMap != null && paramMap.size() > 0) {
				startDate = String.valueOf(paramMap.get("write_date")).replace(".0", "");
				// logger.debug("startDate =========write_date==== : " + startDate);
				// yyyy-MM-dd HH:mm:ss
				String startHour = startDate.substring(11, 13);
				// logger.debug("=========startHour==== : " + startHour);
				String systemHour = CommUtil.getCurrentTimeStamp().substring(11, 13);

				if (startHour != null) {
					int startHR = Integer.parseInt(startHour);
					int systemHR = Integer.parseInt(systemHour);
					String maxHour = adv.getDaemonMaxHour();
					if (maxHour != null) {
						// db_daemon_max_hour λ³΄λ€ μ²λ¦¬ ?κ°μ΄ μ§??°?κ²½μ° 1?κ°? ?  ?°?΄?° μ²λ¦¬
						if (systemHR - startHR > adv.getDaemonMaxHourInt()) {
							startDate = CommUtil.getCurrentMinusSecond(currentTime, (adv.getRunDelayInt() + 600));
						}
					}
				}
			} else {
				paramMap = new HashMap<String, String>();
				// ?€?? ??¬?κ°μ? -?λ²κ° ?κ°? μ°¨μ΄λ₯? κ³ λ €?΄? 10λΆμ  ?°?΄?° μ‘°ν
				startDate = CommUtil.getCurrentMinusSecond(currentTime, (adv.getRunDelayInt() + 600));
			}
			logger.debug("######################   HASH : " + paramMap.toString());

			// ?°?΄?° ??± ?κ°? log ??±
			paramMap.put("write_date", endDate);

			if ("N".equals(adv.getDaemonProcedure())) {
				// Procudure ?? κ²½μ°
				// ////////////////////////////////////////////////////////////////////////

				String query_table = adv.getQueryTable();

				String query = adv.getQuery();
				String col_type = adv.getTimeColunmType();
				String col_name = adv.getTimeColunmName();
				String date_format = adv.getTimeDateformat();

				if ("Y".equals(adv.getDynamicQuery())) {

					// DynamicQuery /////////////////////////////////////////////////////////
					// logger.debug("CommUtil.getCurrentTime() : " + CommUtil.getCurrentTime());
					// logger.debug("query_table : " + adv.getQueryTable());
					// logger.debug("query_where : " + adv.getQueryWhere());

					query_table = DbUtil.getTableType(query_table, startDate);

					queryString.append("SELECT ");
					queryString.append(adv.getQueryColumn());
					queryString.append(" FROM ");
					queryString.append(" " + query_table);
					queryString.append(" WHERE 1 = 1 ");
					queryString.append(" " + adv.getQueryWhere());

					queryString.append(
							DbUtil.getColType(adv.getDbType(), col_type, col_name, date_format, startDate, endDate));

				} else {

					// DynamicQuery ?? κ²½μ° /////////////////////////////////////////////////////////
					queryString.append(query);
					queryString.append(
							DbUtil.getColType(adv.getDbType(), col_type, col_name, date_format, startDate, endDate));

				}
				logger.info(" ExcuteDAO realtime query =============" + queryString.toString());
				logger.info(" URL =============" + nUrl);

				conn = this.getConnection(nUrl, adv.getDbUser(), adv.getDbPassword());
				psmt = conn.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = psmt.executeQuery(); // μΏΌλ¦¬?€??΄? rs? κ°μ λ°μ?¨?€.

			} else if ("Y".equals(adv.getDaemonProcedure())) {
				// Procudure
				// ////////////////////////////////////////////////////////////////////////

				// String procedure = "{call IP_AUDIT_Search_EventLog_E50(20000)}";
				String procedure = adv.getQuery();
				String paramColNm = adv.getDaemonProcedureParamColumn();

				HashMap prcMap = (HashMap) sqlMap.queryForObject("config.getLastProcedureDbSeq", paramMap);
				if (prcMap != null && prcMap.get("DB_SEQ") != null && !"".equals(prcMap.get("DB_SEQ"))) {
					String dbSeq = (String) prcMap.get("DB_SEQ");
					// logger.debug("dbSeq ============= : " + dbSeq);
					procedure = procedure.replaceAll(paramColNm, dbSeq);
				} else {
					procedure = procedure.replaceAll(paramColNm, adv.getDaemonSeq());
					// logger.info("procedure 1 ----> " + paramColNm + " - " + adv.getDaemonSeq());
				}

				// logger.debug("executeStoredprocedure start : " + procedure);
				conn = this.getConnection(nUrl, adv.getDbUser(), adv.getDbPassword());

				queryString.append(procedure);
				logger.info(" ExcuteDAO query =============" + queryString.toString());
				psmt = conn.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = psmt.executeQuery(); // μΏΌλ¦¬?€??΄? rs? κ°μ λ°μ?¨?€.
			}
			paramMap.put("db_query", queryString.toString());

			// ??Ό? ???₯
			// /////////////////////////////////////////////////////////////////////////////////
			// ?΄ ??±?λ‘? ??Ό? ? ? ? ? κ²½λ‘?? ?? ?€λΈμ ?Έ(?? ? λ¦?)κ°? μ‘΄μ¬?μ§? ??Όλ©? ???Όλ‘? ??±?κ³? ?΄?Ή ?€λΈμ ?Έλ₯? ??±??€.
			res = DbUtil.dbWriteFile(rs, fileName, adv.getDataType(), adv.getDataSplt());

			paramMap.put("success_yn", "Y");
			paramMap.put("error_msg", "");
			ret = new DbUtil().update(sqlMap, "config.updateStatusAgentLog", paramMap);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException ?€?¨ -- ExcuteDAO : " + e);
			paramMap = new HashMap<String, String>();
			paramMap.put("daemon_type", adv.getDaemonType());
			paramMap.put("proc_type", adv.getProcType());
			paramMap.put("db_type", adv.getDbType());
			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", e.getLocalizedMessage());
			res = -1; // I/O ?΄?Έ? ??¬? κ²½μ° -1? μΆλ ₯
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception ?€?¨ -- ExcuteDAO : " + e);
			paramMap = new HashMap<String, String>();
			paramMap.put("daemon_type", adv.getDaemonType());
			paramMap.put("proc_type", adv.getProcType());
			paramMap.put("db_type", adv.getDbType());
			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", "Excute run error");
			res = -1; // I/O ?΄?Έ? ??¬? κ²½μ° -1? μΆλ ₯
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);
		} finally { // closeκ°? λ°λ? ?€???λ‘?
			// logger.debug("----------------------- finally -------------------------------
			// ");
			this.close(conn, psmt, rs); // λΆ?λͺ¨μ close?ΈμΆ?
			if (rs != null) { // λ©μ?? ?°?Ό ?? κ²½μ°? ??Όλ―?λ‘? null?΄ ???λ§? ?«?μ€??€.
				try {
					// logger.debug("rs ?«κΈ? ?±κ³?");
					rs.close(); // ?«?? μ΅κ·Ό? ?°κ²λ??° ?«??€.
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("rs ?«κΈ? ?€?¨");
				} finally {
					rs.close();
				}
			}
			if (psmt != null) {
				try {
					psmt.close();
					// logger.debug("psmt ?«κΈ? ?±κ³?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("psmt ?«κΈ? ?€?¨");
				}
			}
			if (conn != null) {
				try {
					conn.close();
					// logger.debug("conn ?«κΈ? ?±κ³?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("conn ?«κΈ? ?€?¨");
				}
			}
		}
		return res;
	}

	public int ExcuteQueryBeforeData(AgentDbVO adv, String fileName, String startDate, String endDate)
			throws Exception {

		logger.debug("################  ExcuteQueryBeforeData start");

		Connection conn = null; // ?°κ²°λ?
		PreparedStatement psmt = null; // μΏΌλ¦¬λΆ?, ?? κ°μ μ£ΌκΈ° ??΄ PrepareStatement?¬?©
		ResultSet rs = null; // λ¦¬μλ²?
		StringBuffer queryString = new StringBuffer();
		int ret = 0;
		
		//ResultSetMetaData rmd = null;
		int res = 1; // ??Ό??± κ²°κ³Ό μΆλ ₯?© 0?΄λ©? ?€?¨, -1?΄λ©? ??Ό??±?΄?Έ? ??¬, 1?΄λ©? ? ?μΆλ ₯?

		SqlMapClient sqlMap = MySqlMapClient.getSqlMapInstance();

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("file_name", fileName);
		paramMap.put("daemon_type", adv.getDaemonType());
		paramMap.put("db_type", adv.getDbType());
		paramMap.put("proc_type", adv.getProcType());
		paramMap.put("write_date", endDate);
		paramMap.put("delay_minute", adv.getRunDelay());
		paramMap.put("success_yn", "N");
		paramMap.put("db_query", queryString.toString());
		paramMap.put("source_type", adv.getSourceType());
		paramMap.put("machine_type", adv.getMachineType());
		
		if ("Y".equals(adv.getIndexTypeYn())) {
			paramMap.put("index_type", adv.getIndexType());
		}
		//sqlMap.insert("config.createAgentLog", paramMap);

		try {

			DbUtil dbu = new DbUtil(adv.getDbSid(), adv.getDbUrl(), adv.getDbType(), adv.getProcDay(), adv.getDbOption());
			String nUrl = dbu.getUrl();

			logger.info(" ExcuteDAO url ============================================== " + nUrl);

			String query = adv.getQuery();
			String delay_minute = adv.getTimeMinuteDelay();
			
			String col_type = adv.getTimeColunmType();
			String col_name = adv.getTimeColunmName();
			String date_format = adv.getTimeDateformat();

			// WRITE_DATE ? λ§μ?λ§? endDate , ?΄? ?Ό? 23:59:59 λ₯? μ²λ¦¬??Όλ©? ??΄? μ²λ¦¬?μ§? ???€.
			if (!CommUtil.getYesterDay().equals(startDate)) {

				// ?€?κ°λ°°μΉκ? ??κ²½μ° ? ?Ό ? μ§? 23?59λΆ?59μ΄κΉμ§?λ§? μ²λ¦¬??€.
				queryString.append(query);
				if (delay_minute != null && !"".equals(delay_minute)) {

					// ?λ‘μ? Έ? ?€?κ°λ§ μ§??
					if ("Y".equals(adv.getDaemonProcedure())) {
						throw new Exception("not support porcedure");
					} else {

						if ("Y".equals(adv.getDynamicQuery())) {

							logger.debug("CommUtil.getCurrentTime() : " + CommUtil.getCurrentTime());
							String query_table = adv.getQueryTable();
							//String query_where = adv.getQueryWhere();
							//String query_column = adv.getQueryColumn();

							query_table = DbUtil.getTableType(query_table, startDate);

							queryString.append("SELECT ");
							queryString.append(adv.getQueryColumn());
							queryString.append(" FROM ");
							queryString.append(" " + query_table);
							queryString.append(" WHERE 1 = 1 ");
							queryString.append(" " + adv.getQueryWhere());

							queryString.append(
									DbUtil.getColType(adv.getDbType(), col_type, col_name, date_format, startDate, endDate));

						} else {

							// DynamicQuery ?? κ²½μ° /////////////////////////////////////////////////////////
							queryString.append(query);
							queryString.append(
									DbUtil.getColType(adv.getDbType(), col_type, col_name, date_format, startDate, endDate));

						}

						

						logger.debug(" ExcuteDAO NOT REALTIME QUERY =============" + queryString.toString());
						logger.debug(" URL =============" + nUrl);
						
						conn = this.getConnection(nUrl, adv.getDbUser(), adv.getDbPassword());
						psmt = conn.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						rs = psmt.executeQuery(); // μΏΌλ¦¬?€??΄? rs? κ°μ λ°μ?¨?€.
					}

				} else {
					// delay_minute null?Έκ²½μ° ? ?ΌμΉ? ?°?΄?°λ₯? ?λ²μ ? μ²΄μ²λ¦? ??€.
				}
				paramMap.put("db_query", queryString.toString());
				
				// ??Ό? ???₯
				// /////////////////////////////////////////////////////////////////////////////////
				// ?΄ ??±?λ‘? ??Ό? ? ? ? ? κ²½λ‘?? ?? ?€λΈμ ?Έ(?? ? λ¦?)κ°? μ‘΄μ¬?μ§? ??Όλ©? ???Όλ‘? ??±?κ³? ?΄?Ή ?€λΈμ ?Έλ₯? ??±??€.
				res = DbUtil.dbWriteFile(rs, fileName, adv.getDataType(), adv.getDataSplt());
				
				paramMap.put("success_yn", "Y");
				paramMap.put("error_msg", "");
				ret = new DbUtil().update(sqlMap, "config.updateStatusAgentLog", paramMap);

			} else {
				logger.warn(" ExcuteDAO :proc_type?΄ notrealtiem?Έκ²½μ°  ? ?Ό 23? 59λΆ? 59μ΄? κΉμ?λ§? μ²λ¦¬??€.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException ?€?¨ -- ExcuteDAO : " + e);

			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", e.getLocalizedMessage());
			res = -1; // I/O ?΄?Έ? ??¬? κ²½μ° -1? μΆλ ₯
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception ?€?¨ -- ExcuteDAO : " + e);

			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", "Excute run error");
			res = -1; // I/O ?΄?Έ? ??¬? κ²½μ° -1? μΆλ ₯
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);

		} finally { // closeκ°? λ°λ? ?€???λ‘?
			logger.debug("----------------------- finally ------------------------------- ");
			this.close(conn, psmt, rs); // λΆ?λͺ¨μ close?ΈμΆ?
			if (rs != null) { // λ©μ?? ?°?Ό ?? κ²½μ°? ??Όλ―?λ‘? null?΄ ???λ§? ?«?μ€??€.
				try {
					// logger.debug("rs ?«κΈ? ?±κ³?");
					rs.close(); // ?«?? μ΅κ·Ό? ?°κ²λ??° ?«??€.
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("rs ?«κΈ? ?€?¨");
				}
			}

			if (psmt != null) {
				try {
					// logger.debug("psmt ?«κΈ? ?±κ³?");
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("psmt ?«κΈ? ?€?¨");
				}
			}

			if (conn != null) {
				try {
					conn.close();
					// logger.debug("conn ?«κΈ? ?±κ³?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("conn ?«κΈ? ?€?¨");
				}
			}
		}
		return res;
	}

	public int ExcuteQueryBeforeOneDayData(AgentDbVO adv, String fileName, String currentDate)
			throws Exception {

		logger.debug("ExcuteQueryBeforeOneDayData start");

		Connection conn = null; // ?°κ²°λ?
		PreparedStatement psmt = null; // μΏΌλ¦¬λΆ?, ?? κ°μ μ£ΌκΈ° ??΄ PrepareStatement?¬?©
		ResultSet rs = null; // λ¦¬μλ²?
		StringBuffer resultBuffer = new StringBuffer();
		StringBuffer queryString = new StringBuffer();
		int res = 1; // ??Ό??± κ²°κ³Ό μΆλ ₯?© 0?΄λ©? ?€?¨, -1?΄λ©? ??Ό??±?΄?Έ? ??¬, 1?΄λ©? ? ?μΆλ ₯?

		SqlMapClient sqlMap = MySqlMapClient.getSqlMapInstance();

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("file_name", fileName);
		paramMap.put("daemon_type", adv.getDaemonType());
		paramMap.put("db_type", adv.getDbType());
		paramMap.put("proc_type", adv.getProcType());
		paramMap.put("write_date", currentDate);
		paramMap.put("delay_minute", adv.getRunDelay());
		paramMap.put("success_yn", "N");
		paramMap.put("db_query", queryString.toString());
		paramMap.put("source_type", adv.getSourceType());
		paramMap.put("machine_type", adv.getMachineType());

		if ("Y".equals(adv.getIndexTypeYn())) {
			paramMap.put("index_type", adv.getIndexTypeYn());
		}

		logger.debug("######## createAgentLog : " + paramMap);
		sqlMap.insert("config.createAgentLog", paramMap);

		try {

			DbUtil dbu = new DbUtil(adv.getDbSid(), adv.getDbUrl(), adv.getDbType(), adv.getProcType(), adv.getDbOption());
			String nUrl = dbu.getUrl();

			logger.info(" ExcuteDAO url ============================================== " + nUrl);
			queryString.append(adv.getQuery());

			String currentDateYYYYMMDD = CommUtil.getDay(currentDate, "yyyyMMdd");
			logger.debug("currentDateYYYYMMDD : " + currentDateYYYYMMDD);

			// ?λ‘μ? Έ? ?€?κ°λ§ μ§??
			if ("Y".equals(adv.getDaemonProcedure())) {
				throw new Exception("not support porcedure");
			} else {

				if ("Y".equals(adv.getDynamicQuery())) {

					logger.debug("CommUtil.getCurrentTime() : " + CommUtil.getCurrentTime());

					String year = CommUtil.getDay("yyyyMMdd", adv.getProcDayInt()).substring(0, 4);
					String month = CommUtil.getDay("yyyyMMdd", adv.getProcDayInt()).substring(4, 6);
					String day = CommUtil.getDay("yyyyMMdd", adv.getProcDayInt()).substring(6, 8);

					logger.debug("year : " + year);
					logger.debug("month : " + month);
					logger.debug("day : " + day);
					logger.debug("year : " + year);
					logger.debug("query_table : " + adv.getQueryTable());
					logger.debug("query_where : " + adv.getQueryWhere());
					String nQueryTable = adv.getQueryTable();

					if (nQueryTable.toUpperCase().contains("YYYY_MM_DD")) {
						nQueryTable = nQueryTable.replaceAll("YYYY_MM_DD", year + "_" + month + "_" + day);
					} else if (nQueryTable.toUpperCase().contains("YYYY-MM-DD")) {
						nQueryTable = nQueryTable.replaceAll("YYYY-MM-DD", year + "-" + month + "-" + day);
					} else if (nQueryTable.toUpperCase().contains("YYYYMMDD")) {
						nQueryTable = nQueryTable.replaceAll("YYYYMMDD", year + month + day);
					} else if (nQueryTable.toUpperCase().contains("YYYY_MM")) {
						nQueryTable = nQueryTable.replaceAll("YYYY_MM", year + "_" + month);
					} else if (nQueryTable.toUpperCase().contains("YYYY-MM")) {
						nQueryTable = nQueryTable.replaceAll("YYYY-MM", year + "-" + month);
					} else if (nQueryTable.toUpperCase().contains("YYYYMM")) {
						nQueryTable = nQueryTable.replaceAll("YYYYMMDD", year + month);
					}

					queryString.append(adv.getQueryTable());
					queryString.append(" " + nQueryTable);
					queryString.append(" " + adv.getQueryWhere());

					if (adv.getTimeColunmType().contains("date")) {
						if ("mssql".equals(adv.getDbType())) {
							queryString.append(" and convert(varchar(8) , " + adv.getTimeColunmName()
									+ ", 112 ) = convert(varchar(8), '" + currentDateYYYYMMDD + "' , 112 ) ");
						} else if ("oracle".equals(adv.getDbType())) {
							queryString.append(" and to_char(" + adv.getTimeColunmName() + ",'"
									+ adv.getTimeDateformat() + "') = to_date('" + currentDateYYYYMMDD + "','"
									+ adv.getTimeDateformat() + "') ");
						} else if ("mysql".equals(adv.getDbType())) {
							queryString.append(" and date_format(" + adv.getTimeColunmName() + ",'"
									+ adv.getTimeDateformat() + "') = date_format('" + currentDateYYYYMMDD + "','"
									+ adv.getTimeDateformat() + "') ");
						}
					} else if (adv.getTimeColunmType().contains("string")) {

						if ("mssql".equals(adv.getDbType())) {
							queryString.append(" and  " + adv.getTimeColunmName() + " = convert(varchar(8), '"
									+ currentDateYYYYMMDD + "' , 112 ) ");
						} else if ("oracle".equals(adv.getDbType())) {
							queryString.append(" and  " + adv.getTimeColunmName() + " = to_date('" + currentDateYYYYMMDD
									+ "','" + adv.getTimeDateformat() + "')");
						} else if ("mysql".equals(adv.getDbType())) {
							queryString.append(" and  " + adv.getTimeColunmName() + " =  date_format('"
									+ currentDateYYYYMMDD + "','" + adv.getTimeDateformat() + "') ");
						}
					}

				} else {
					if (adv.getTimeColunmType().contains("date")) {
						if ("mssql".equals(adv.getDbType())) {
							queryString.append(" and convert(varchar(8) , " + adv.getTimeColunmName()
									+ ", 112 ) = convert(varchar(8), '" + currentDateYYYYMMDD + "' , 112 ) ");
						} else if ("oracle".equals(adv.getDbType())) {
							queryString.append(" and to_char(" + adv.getTimeColunmName() + ",'"
									+ adv.getTimeDateformat() + "') = to_date('" + currentDateYYYYMMDD + "','"
									+ adv.getTimeDateformat() + "') ");
						} else if ("mysql".equals(adv.getDbType())) {
							queryString.append(" and date_format(" + adv.getTimeColunmName() + ",'"
									+ adv.getTimeDateformat() + "') = date_format('" + currentDateYYYYMMDD + "','"
									+ adv.getTimeDateformat() + "') ");
						}
					} else if (adv.getTimeColunmType().contains("string")) {

						if ("mssql".equals(adv.getDbType())) {
							queryString.append(" and  " + adv.getTimeColunmName() + " = convert(varchar(8), '"
									+ currentDateYYYYMMDD + "' , 112 ) ");
						} else if ("oracle".equals(adv.getDbType())) {
							queryString.append(" and  " + adv.getTimeColunmName() + " = to_date('" + currentDateYYYYMMDD
									+ "','" + adv.getTimeDateformat() + "')");
						} else if ("mysql".equals(adv.getDbType())) {
							queryString.append(" and  " + adv.getTimeColunmName() + " =  date_format('"
									+ currentDateYYYYMMDD + "','" + adv.getTimeDateformat() + "') ");
						}
					}
				}

				logger.debug(" ## DB REQUEST url : " + nUrl);
				logger.debug(" ## DB REQUEST user/pass : " + adv.getDbUser() + "/" + adv.getDbPassword());
				conn = this.getConnection(nUrl, adv.getDbUser(), adv.getDbPassword());

				logger.debug(" ExcuteDAO not realtime query =============" + queryString.toString());
				psmt = conn.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				rs = psmt.executeQuery(); // μΏΌλ¦¬?€??΄? rs? κ°μ λ°μ?¨?€.
			}

			// ??Ό? ???₯
			// /////////////////////////////////////////////////////////////////////////////////
			// ?΄ ??±?λ‘? ??Ό? ? ? ? ? κ²½λ‘?? ?? ?€λΈμ ?Έ(?? ? λ¦?)κ°? μ‘΄μ¬?μ§? ??Όλ©? ???Όλ‘? ??±?κ³? ?΄?Ή ?€λΈμ ?Έλ₯? ??±??€.
			res = DbUtil.dbWriteFile(rs, fileName, adv.getDataType(), adv.getDataSplt());

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException ?€?¨ -- ExcuteDAO : " + e);
			paramMap = new HashMap<String, String>();
			paramMap.put("daemon_type", adv.getDaemonType());
			paramMap.put("proc_type", adv.getProcType());
			paramMap.put("db_type", adv.getDbType());
			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", e.getLocalizedMessage());
			resultBuffer.append("Error");
			res = -1;

			try {
				sqlMap.startTransaction();
				sqlMap.update("config.updateAgentLog", paramMap);
				sqlMap.commitTransaction();
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception ?€?¨ -- ExcuteDAO : " + e);
			paramMap = new HashMap<String, String>();
			paramMap.put("daemon_type", adv.getDaemonType());
			paramMap.put("proc_type", adv.getProcType());
			paramMap.put("db_type", adv.getDbType());
			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", "Excute run error");
			resultBuffer.append("Error");
			res = -1;
			try {
				sqlMap.startTransaction();
				sqlMap.update("config.updateAgentLog", paramMap);
				sqlMap.commitTransaction();
			} finally {
				sqlMap.endTransaction();
			}
		} finally { // closeκ°? λ°λ? ?€???λ‘?
			logger.debug("----------------------- finally ------------------------------- ");
			this.close(conn, psmt, rs); // λΆ?λͺ¨μ close?ΈμΆ?
			if (rs != null) { // λ©μ?? ?°?Ό ?? κ²½μ°? ??Όλ―?λ‘? null?΄ ???λ§? ?«?μ€??€.
				try {
					// logger.debug("rs ?«κΈ? ?±κ³?");
					rs.close(); // ?«?? μ΅κ·Ό? ?°κ²λ??° ?«??€.
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("rs ?«κΈ? ?€?¨");
				}
			}
			if (psmt != null) {
				try {
					// logger.debug("psmt ?«κΈ? ?±κ³?");
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("psmt ?«κΈ? ?€?¨");
				}
			}
			if (conn != null) {
				try {
					conn.close();
					// logger.debug("conn ?«κΈ? ?±κ³?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("conn ?«κΈ? ?€?¨");
				}
			}
		}
		return res;
	}

}