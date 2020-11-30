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

		Connection conn = null; // ?��결�?
		PreparedStatement psmt = null; // 쿼리�??�� 값을 주기 ?��?�� PrepareStatement?��?��
		ResultSet rs = null; // 리시�?
		StringBuffer queryString = new StringBuffer();
		int ret = 0;

		SqlMapClient sqlMap = MySqlMapClient.getSqlMapInstance();

		int res = 1; // ?��?��?��?�� 결과 출력?�� 0?���? ?��?��, -1?���? ?��?��?��?��?��?��?�� ?��?��, 1?���? ?��?��출력?��
		String endDate = CommUtil.getCurrentTimeStamp();

		// 결과격납?�� ?���? ?��?�� 로그�? ?��?��?�� ?��. (결과?�� N?���? 고정)
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

			// ?��?��처리 기록?��?��?���?�? 체크?��?�� 처리

			//HashMap<String, String> logMap = new HashMap<String, String>();
			//logMap.put("daemon_type", adv.getDaemonType());
			//logMap.put("proc_type", adv.getProcType());
			//logMap.put("db_type", adv.getDbType());
			//logMap = (HashMap) sqlMap.queryForObject("config.getLastLog", logMap);

			paramMap = (HashMap) sqlMap.queryForObject("config.getLastLog", paramMap);
			
			String startDate = null;

			// 금일 ?��?�� ?��?��?�� ?��?�� 경우 enddate�??�� ?��?�� ?��?��?���? �??��?��?��.
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
						// db_daemon_max_hour 보다 처리 ?��간이 �??��?��경우 1?���? ?�� ?��?��?�� 처리
						if (systemHR - startHR > adv.getDaemonMaxHourInt()) {
							startDate = CommUtil.getCurrentMinusSecond(currentTime, (adv.getRunDelayInt() + 600));
						}
					}
				}
			} else {
				paramMap = new HashMap<String, String>();
				// ?��?��?�� ?��?��?��간에?�� -?��버간 ?���? 차이�? 고려?��?�� 10분전 ?��?��?�� 조회
				startDate = CommUtil.getCurrentMinusSecond(currentTime, (adv.getRunDelayInt() + 600));
			}
			logger.debug("######################   HASH : " + paramMap.toString());

			// ?��?��?�� ?��?�� ?���? log ?��?��
			paramMap.put("write_date", endDate);

			if ("N".equals(adv.getDaemonProcedure())) {
				// Procudure ?��?�� 경우
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

					// DynamicQuery ?��?�� 경우 /////////////////////////////////////////////////////////
					queryString.append(query);
					queryString.append(
							DbUtil.getColType(adv.getDbType(), col_type, col_name, date_format, startDate, endDate));

				}
				logger.info(" ExcuteDAO realtime query =============" + queryString.toString());
				logger.info(" URL =============" + nUrl);

				conn = this.getConnection(nUrl, adv.getDbUser(), adv.getDbPassword());
				psmt = conn.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = psmt.executeQuery(); // 쿼리?��?��?��?�� rs?�� 값을 받아?��?��.

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
				rs = psmt.executeQuery(); // 쿼리?��?��?��?�� rs?�� 값을 받아?��?��.
			}
			paramMap.put("db_query", queryString.toString());

			// ?��?��?�� ???��
			// /////////////////////////////////////////////////////////////////////////////////
			// ?�� ?��?��?���? ?��?��?�� ?��?�� ?��?�� 경로?��?�� ?��?�� ?��브젝?��(?��?��?���?)�? 존재?���? ?��?���? ?��?��?���? ?��?��?���? ?��?�� ?��브젝?���? ?��?��?��?��.
			res = DbUtil.dbWriteFile(rs, fileName, adv.getDataType(), adv.getDataSplt());

			paramMap.put("success_yn", "Y");
			paramMap.put("error_msg", "");
			ret = new DbUtil().update(sqlMap, "config.updateStatusAgentLog", paramMap);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException ?��?�� -- ExcuteDAO : " + e);
			paramMap = new HashMap<String, String>();
			paramMap.put("daemon_type", adv.getDaemonType());
			paramMap.put("proc_type", adv.getProcType());
			paramMap.put("db_type", adv.getDbType());
			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", e.getLocalizedMessage());
			res = -1; // I/O ?��?��?�� ?��?��?�� 경우 -1?�� 출력
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception ?��?�� -- ExcuteDAO : " + e);
			paramMap = new HashMap<String, String>();
			paramMap.put("daemon_type", adv.getDaemonType());
			paramMap.put("proc_type", adv.getProcType());
			paramMap.put("db_type", adv.getDbType());
			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", "Excute run error");
			res = -1; // I/O ?��?��?�� ?��?��?�� 경우 -1?�� 출력
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);
		} finally { // close�? 반드?�� ?��?��?��?���?
			// logger.debug("----------------------- finally -------------------------------
			// ");
			this.close(conn, psmt, rs); // �?모의 close?���?
			if (rs != null) { // 메서?��?�� ?��?�� ?��?�� 경우?�� ?��?���?�? null?�� ?��?��?���? ?��?���??��.
				try {
					// logger.debug("rs ?���? ?���?");
					rs.close(); // ?��?��?�� 최근?�� ?��것�??�� ?��?��?��.
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("rs ?���? ?��?��");
				} finally {
					rs.close();
				}
			}
			if (psmt != null) {
				try {
					psmt.close();
					// logger.debug("psmt ?���? ?���?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("psmt ?���? ?��?��");
				}
			}
			if (conn != null) {
				try {
					conn.close();
					// logger.debug("conn ?���? ?���?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("conn ?���? ?��?��");
				}
			}
		}
		return res;
	}

	public int ExcuteQueryBeforeData(AgentDbVO adv, String fileName, String startDate, String endDate)
			throws Exception {

		logger.debug("################  ExcuteQueryBeforeData start");

		Connection conn = null; // ?��결�?
		PreparedStatement psmt = null; // 쿼리�?, ??�� 값을 주기 ?��?�� PrepareStatement?��?��
		ResultSet rs = null; // 리시�?
		StringBuffer queryString = new StringBuffer();
		int ret = 0;
		
		//ResultSetMetaData rmd = null;
		int res = 1; // ?��?��?��?�� 결과 출력?�� 0?���? ?��?��, -1?���? ?��?��?��?��?��?��?�� ?��?��, 1?���? ?��?��출력?��

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

			// WRITE_DATE ?�� 마�?�? endDate , ?��?��?��?�� 23:59:59 �? 처리?��?���? ?��?��?�� 처리?���? ?��?��?��.
			if (!CommUtil.getYesterDay().equals(startDate)) {

				// ?��?��간배치�? ?��?��경우 ?��?�� ?���? 23?��59�?59초까�?�? 처리?��?��.
				queryString.append(query);
				if (delay_minute != null && !"".equals(delay_minute)) {

					// ?��로시?��?�� ?��?��간만 �??��
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

							// DynamicQuery ?��?�� 경우 /////////////////////////////////////////////////////////
							queryString.append(query);
							queryString.append(
									DbUtil.getColType(adv.getDbType(), col_type, col_name, date_format, startDate, endDate));

						}

						

						logger.debug(" ExcuteDAO NOT REALTIME QUERY =============" + queryString.toString());
						logger.debug(" URL =============" + nUrl);
						
						conn = this.getConnection(nUrl, adv.getDbUser(), adv.getDbPassword());
						psmt = conn.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						rs = psmt.executeQuery(); // 쿼리?��?��?��?�� rs?�� 값을 받아?��?��.
					}

				} else {
					// delay_minute null?��경우 ?��?���? ?��?��?���? ?��번에 ?��체처�? ?��?��.
				}
				paramMap.put("db_query", queryString.toString());
				
				// ?��?��?�� ???��
				// /////////////////////////////////////////////////////////////////////////////////
				// ?�� ?��?��?���? ?��?��?�� ?��?�� ?��?�� 경로?��?�� ?��?�� ?��브젝?��(?��?��?���?)�? 존재?���? ?��?���? ?��?��?���? ?��?��?���? ?��?�� ?��브젝?���? ?��?��?��?��.
				res = DbUtil.dbWriteFile(rs, fileName, adv.getDataType(), adv.getDataSplt());
				
				paramMap.put("success_yn", "Y");
				paramMap.put("error_msg", "");
				ret = new DbUtil().update(sqlMap, "config.updateStatusAgentLog", paramMap);

			} else {
				logger.warn(" ExcuteDAO :proc_type?�� notrealtiem?��경우  ?��?�� 23?�� 59�? 59�? 까�?�? 처리?��?��.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException ?��?�� -- ExcuteDAO : " + e);

			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", e.getLocalizedMessage());
			res = -1; // I/O ?��?��?�� ?��?��?�� 경우 -1?�� 출력
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception ?��?�� -- ExcuteDAO : " + e);

			paramMap.put("file_name", fileName);
			paramMap.put("success_yn", "N");
			paramMap.put("db_query", queryString.toString());
			paramMap.put("error_msg", "Excute run error");
			res = -1; // I/O ?��?��?�� ?��?��?�� 경우 -1?�� 출력
			ret = new DbUtil().update(sqlMap, "config.updateAgentLog", paramMap);

		} finally { // close�? 반드?�� ?��?��?��?���?
			logger.debug("----------------------- finally ------------------------------- ");
			this.close(conn, psmt, rs); // �?모의 close?���?
			if (rs != null) { // 메서?��?�� ?��?�� ?��?�� 경우?�� ?��?���?�? null?�� ?��?��?���? ?��?���??��.
				try {
					// logger.debug("rs ?���? ?���?");
					rs.close(); // ?��?��?�� 최근?�� ?��것�??�� ?��?��?��.
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("rs ?���? ?��?��");
				}
			}

			if (psmt != null) {
				try {
					// logger.debug("psmt ?���? ?���?");
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("psmt ?���? ?��?��");
				}
			}

			if (conn != null) {
				try {
					conn.close();
					// logger.debug("conn ?���? ?���?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("conn ?���? ?��?��");
				}
			}
		}
		return res;
	}

	public int ExcuteQueryBeforeOneDayData(AgentDbVO adv, String fileName, String currentDate)
			throws Exception {

		logger.debug("ExcuteQueryBeforeOneDayData start");

		Connection conn = null; // ?��결�?
		PreparedStatement psmt = null; // 쿼리�?, ??�� 값을 주기 ?��?�� PrepareStatement?��?��
		ResultSet rs = null; // 리시�?
		StringBuffer resultBuffer = new StringBuffer();
		StringBuffer queryString = new StringBuffer();
		int res = 1; // ?��?��?��?�� 결과 출력?�� 0?���? ?��?��, -1?���? ?��?��?��?��?��?��?�� ?��?��, 1?���? ?��?��출력?��

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

			// ?��로시?��?�� ?��?��간만 �??��
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

				rs = psmt.executeQuery(); // 쿼리?��?��?��?�� rs?�� 값을 받아?��?��.
			}

			// ?��?��?�� ???��
			// /////////////////////////////////////////////////////////////////////////////////
			// ?�� ?��?��?���? ?��?��?�� ?��?�� ?��?�� 경로?��?�� ?��?�� ?��브젝?��(?��?��?���?)�? 존재?���? ?��?���? ?��?��?���? ?��?��?���? ?��?�� ?��브젝?���? ?��?��?��?��.
			res = DbUtil.dbWriteFile(rs, fileName, adv.getDataType(), adv.getDataSplt());

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException ?��?�� -- ExcuteDAO : " + e);
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
			logger.error("Exception ?��?�� -- ExcuteDAO : " + e);
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
		} finally { // close�? 반드?�� ?��?��?��?���?
			logger.debug("----------------------- finally ------------------------------- ");
			this.close(conn, psmt, rs); // �?모의 close?���?
			if (rs != null) { // 메서?��?�� ?��?�� ?��?�� 경우?�� ?��?���?�? null?�� ?��?��?���? ?��?���??��.
				try {
					// logger.debug("rs ?���? ?���?");
					rs.close(); // ?��?��?�� 최근?�� ?��것�??�� ?��?��?��.
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("rs ?���? ?��?��");
				}
			}
			if (psmt != null) {
				try {
					// logger.debug("psmt ?���? ?���?");
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("psmt ?���? ?��?��");
				}
			}
			if (conn != null) {
				try {
					conn.close();
					// logger.debug("conn ?���? ?���?");
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("conn ?���? ?��?��");
				}
			}
		}
		return res;
	}

}