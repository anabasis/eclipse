package com.slug.dao.sql;

import java.io.Serializable;
import java.util.HashMap;

public class StatementVO implements Serializable {
	
	private static final long serialVersionUID = -6059533977836123177L;

	private String statementKey;
	//private HashMap<String,StatementItemVO> statementVOs;
	public HashMap<String,StatementItemVO> statementVOs;

	public StatementVO() {
		this.statementVOs = new HashMap<String,StatementItemVO>();
	}

	/**
	 * 
	 * @param statementKey
	 * @param statementVOs
	 */
	public StatementVO(String statementKey, HashMap<String,StatementItemVO> statementVOs) {
		this.statementKey = statementKey;
		this.statementVOs = statementVOs;
	}

	/**
	 * 
	 * @param statementKey
	 */
	public void setStatementKey(String statementKey) {
		this.statementKey = statementKey;
	}

	/**
	 * 
	 * @param statementKey
	 * @return
	 */
	public String getStatementKey(String statementKey) {
		return this.statementKey;
	}

	public void addStatementItemVO(StatementItemVO stItemVO) {
		statementVOs.put(stItemVO.sql_id, stItemVO);
		//System.out.println("INPUT : KEY " + stItemVO.sql_id + " : " + statementVOs.containsKey(stItemVO.sql_id));
	}

	public StatementItemVO getStatementItemVO(String sql_id) {
		return (StatementItemVO) statementVOs.get(sql_id.trim());
		
	}

	@Override
	public String toString() {
		return "StatementVO [statementKey=" + statementKey + ", statementVOs="
				+ statementVOs + "]";
	}

}