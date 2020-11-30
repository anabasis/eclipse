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
			logger.debug("1/6 ?��?��?���? 로딩 ?���?");
		} catch (ClassNotFoundException e) {
			logger.debug(e.getLocalizedMessage());
			logger.debug("1/6 ?��?��?���? 로딩 ?��?��");
		}
	} // init

	public Connection getConnection(String url, String user, String pass) { // DB?���?
		Connection conn = null;
		try {
			// logger.debug("URL"+url);
			// logger.debug("ID"+user);
			// logger.debug("PWD"+pass);
			conn = DriverManager.getConnection(url, user, pass);
			logger.debug("2/6 ?���? ?���? url: " + url);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(e.getLocalizedMessage());
			logger.debug("2/6 ?���? ?��?��");
		}
		return conn;
	} // getConnection

	public void close(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) { // 메서?��?�� ?��?�� ?��?�� 경우?�� ?��?���?�? null?�� ?��?��?���? ?��?���??��.
			try {
				rs.close(); // ?��?��?�� 최근?�� ?��것�??�� ?��?��?��.
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
				logger.debug("6/6 ?���? ?���?");
			} catch (SQLException e) {
				logger.debug("6/6 ?���? ?��?��");
			}
		}
	}

}
