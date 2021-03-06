package com.lds.frmwk.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDB {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDB.class);

	public AbstractDB(String driver) {// Construct
		logger.debug("AbstractDB driver : " + driver);
		init(driver);
	}

	private void init(String driver) {
		try {
			Class.forName(driver);
			logger.debug("1/6 ??Ό?΄λ²? λ‘λ© ?±κ³?");
		} catch (ClassNotFoundException e) {
			logger.debug(e.getLocalizedMessage());
			logger.debug("1/6 ??Ό?΄λ²? λ‘λ© ?€?¨");
		}
	} // init

	public Connection getConnection(String url, String user, String pass) { // DB?°κ²?
		Connection conn = null;
		try {
			// logger.debug("URL"+url);
			// logger.debug("ID"+user);
			// logger.debug("PWD"+pass);
			conn = DriverManager.getConnection(url, user, pass);
			logger.debug("2/6 ?°κ²? ?±κ³? url: " + url);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(e.getLocalizedMessage());
			logger.debug("2/6 ?°κ²? ?€?¨");
		}
		return conn;
	} // getConnection

	public void close(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) { // λ©μ?? ?°?Ό ?? κ²½μ°? ??Όλ―?λ‘? null?΄ ???λ§? ?«?μ€??€.
			try {
				rs.close(); // ?«?? μ΅κ·Ό? ?°κ²λ??° ?«??€.
			} catch (SQLException e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}

		if (conn != null) {
			try {
				conn.close();
				logger.debug("6/6 ?«κΈ? ?±κ³?");
			} catch (SQLException e) {
				logger.debug("6/6 ?«κΈ? ?€?¨");
			}
		}
	}

}
