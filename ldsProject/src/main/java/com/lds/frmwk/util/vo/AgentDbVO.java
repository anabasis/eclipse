package com.lds.frmwk.util.vo;

import org.jdom2.Element;

public class AgentDbVO {

	public AgentDbVO(Element el) {
		// TODO Auto-generated constructor stub

	}

	String indexTypeYn = "";
	String name = "";

	// Setting
	String daemonType = "";
	String sourceType = "";
	String machineType = "";
	String indexType = "";
	String dbType = "";
	String dbClass = "";
	String dbUrl = "";
	String dbUser = "";
	String dbPassword = "";
	String dbSid = "";
	String dbOption = "";

	// Delay
	String procType = "";
	String runRightnow = "";
	String runHour = "";
	String runMin = "";
	String procDay = "";
	String runDelay = "";
	String timeMinuteDelay = "";

	// Sql
	String daemonProcedure = "";
	String daemonProcedureParamColumn = "";
	String daemonSeq = "";
	String daemonRowCnt = "";
	String daemonMaxHour = "";

	String dynamicQuery = "";
	String query = "";
	String queryColumn = "";
	String queryTable = "";

	String queryWhere = "";
	String timeColunmName = "";
	String timeColunmType = "";
	String timeDateformat = "";
	String dir = "";

	String dataType = "";
	String dataSplt = "";

	// Add Int
	int runHourInt = 0;
	int runMinInt = 0;
	int procDayInt = 0;
	int runDelayInt = 0;
	int timeMinuteDelayInt = 0;

	int daemonSeqInt = 0;
	int daemonRowCntInt = 0;
	int daemonMaxHourInt = 0;

	public String getDaemonType() {
		return daemonType;
	}

	public void setDaemonType(String daemonType) {
		this.daemonType = daemonType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbClass() {
		return dbClass;
	}

	public void setDbClass(String dbClass) {
		this.dbClass = dbClass;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbSid() {
		return dbSid;
	}

	public void setDbSid(String dbSid) {
		this.dbSid = dbSid;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getRunRightnow() {
		return runRightnow;
	}

	public void setRunRightnow(String runRightnow) {
		this.runRightnow = runRightnow;
	}

	public String getRunHour() {
		return runHour;
	}

	public void setRunHour(String runHour) {
		this.runHour = runHour;
	}

	public String getRunMin() {
		return runMin;
	}

	public void setRunMin(String runMin) {
		this.runMin = runMin;
	}

	public String getProcDay() {
		return procDay;
	}

	public void setProcDay(String procDay) {
		this.procDay = procDay;
	}

	public String getRunDelay() {
		return runDelay;
	}

	public void setRunDelay(String runDelay) {
		this.runDelay = runDelay;
	}

	public String getTimeMinuteDelay() {
		return timeMinuteDelay;
	}

	public void setTimeMinuteDelay(String timeMinuteDelay) {
		this.timeMinuteDelay = timeMinuteDelay;
	}

	public String getDaemonProcedure() {
		return daemonProcedure;
	}

	public void setDaemonProcedure(String daemonProcedure) {
		this.daemonProcedure = daemonProcedure;
	}

	public String getDaemonProcedureParamColumn() {
		return daemonProcedureParamColumn;
	}

	public void setDaemonProcedureParamColumn(String daemonProcedureParamColumn) {
		this.daemonProcedureParamColumn = daemonProcedureParamColumn;
	}

	public String getDaemonSeq() {
		return daemonSeq;
	}

	public void setDaemonSeq(String daemonSeq) {
		this.daemonSeq = daemonSeq;
	}

	public String getDaemonRowCnt() {
		return daemonRowCnt;
	}

	public void setDaemonRowCnt(String daemonRowCnt) {
		this.daemonRowCnt = daemonRowCnt;
	}

	public String getDaemonMaxHour() {
		return daemonMaxHour;
	}

	public void setDaemonMaxHour(String daemonMaxHour) {
		this.daemonMaxHour = daemonMaxHour;
	}

	public String getDynamicQuery() {
		return dynamicQuery;
	}

	public void setDynamicQuery(String dynamicQuery) {
		this.dynamicQuery = dynamicQuery;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryColumn() {
		return queryColumn;
	}

	public void setQueryColumn(String queryColumn) {
		this.queryColumn = queryColumn;
	}

	public String getQueryTable() {
		return queryTable;
	}

	public void setQueryTable(String queryTable) {
		this.queryTable = queryTable;
	}

	public String getQueryWhere() {
		return queryWhere;
	}

	public void setQueryWhere(String queryWhere) {
		this.queryWhere = queryWhere;
	}

	public String getTimeColunmName() {
		return timeColunmName;
	}

	public void setTimeColunmName(String timeColunmName) {
		this.timeColunmName = timeColunmName;
	}

	public String getTimeColunmType() {
		return timeColunmType;
	}

	public void setTimeColunmType(String timeColunmType) {
		this.timeColunmType = timeColunmType;
	}

	public String getTimeDateformat() {
		return timeDateformat;
	}

	public void setTimeDateformat(String timeDateformat) {
		this.timeDateformat = timeDateformat;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataSplt() {
		return dataSplt;
	}

	public void setDataSplt(String dataSplt) {
		this.dataSplt = dataSplt;
	}

	/* String -> Int */
	public int getRunHourInt() {
		return (runHour == null || "".equals(runHour)) ? 0 : Integer.parseInt(runHour);
	}

	public int getRunMinInt() {
		return (runMin == null || "".equals(runMin)) ? 0 : Integer.parseInt(runMin);
	}

	public int getProcDayInt() {
		return (procDay == null || "".equals(procDay)) ? 0 : Integer.parseInt(procDay);
	}

	public int getRunDelayInt() {
		return (runDelay == null || "".equals(runDelay)) ? 0 : Integer.parseInt(runDelay);
	}

	public int getTimeMinuteDelayInt() {
		return (timeMinuteDelay == null || "".equals(timeMinuteDelay)) ? 0 : Integer.parseInt(timeMinuteDelay);
	}

	public int getDaemonSeqInt() {
		return (daemonSeq == null || "".equals(daemonSeq)) ? 0 : Integer.parseInt(daemonSeq);
	}

	public int getDaemonRowCntInt() {
		return (daemonRowCnt == null || "".equals(daemonRowCnt)) ? 0 : Integer.parseInt(daemonRowCnt);
	}

	public int getDaemonMaxHourInt() {
		return (daemonMaxHour == null || "".equals(daemonMaxHour)) ? 0 : Integer.parseInt(daemonMaxHour);
	}

	public String getName() {
		return name;
	}

	public String getIndexTypeYn() {
		return indexTypeYn;
	}

	public String getDbOption() {
		return dbOption;
	}

	public void setDbOption(String dbOption) {
		this.dbOption = dbOption;
	}

}