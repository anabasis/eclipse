package com.slug.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.vo.box.Box;

public class DAOStatementExecutor extends DAOSqlHandler {

	String trxResultCode = "Y";
	String trxResultMsg = "Success To Request!";

	public DAOStatementExecutor() {
		super();
	}

	/**
	 * 
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @param ubox
	 * @param input_value
	 * @return
	 */
	public ResultSet doExecuteSelectLoggable(Connection conn,
			PreparedStatement pstmt, ResultSet rs, Box ubox,
			String input_value[]) {

		String query = ubox.getString("query");
		String db = ubox.getString("db");

		try {

			pstmt = conn.prepareStatement(query);
			pstmt = new LoggableStatement(conn, query);

			for (int input = 0; input < input_value.length; input++) {
				pstmt.setString(input + 1, input_value[input]);
			}

			rs = pstmt.executeQuery();

		} catch (SQLException se) {

			Logging.err.println(this.getClass().getName() + "."
					+ "doExecuteSelect()" + "=>\n" + se + "\n" + query);
			for (int input = 0; input < input_value.length; input++) {
				Logging.err.println("Query Parameter [" + input + "]>>"
						+ input_value[input]);
			}
			Logging.err.println("Query: " + query);

		} catch (Exception e) {
			Logging.err.println(this.getClass().getName() + "."
					+ "doExecuteSelect()" + "=>\n" + e + "\n");
		}

		return rs;

	}

	/**
	 * 
	 * @param ubox
	 * @param input_value
	 * @return
	 * @throws PException
	 */
	public String[] updateObject(Connection pconn, Box ubox,
			String input_value[]) throws PException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String rtn_rst[] = new String[2];
		String db = ubox.getString("db");

		int txRs = 0;

		String query = ubox.getString("query");

		try {
			if (pconn == null) {
				conn = getConnection(db);
				pstmt = new LoggableStatement(conn, query);// CLAB 처占쏙옙占쏙옙
															// 占쏙옙占쏙옙
															// LaggableStatement
															// 占쏙옙占�
			} else {
				pstmt = new LoggableStatement(pconn, query);// CLAB 처占쏙옙占쏙옙
															// 占쏙옙占쏙옙
															// LaggableStatement
															// 占쏙옙占�
			}

			for (int input = 0; input < input_value.length; input++) {
				pstmt.setString(input + 1, input_value[input]);
			}

			txRs = pstmt.executeUpdate();

		} catch (SQLException se) {

			Logging.err.println(this.getClass().getName() + "."
					+ "updateObject() SQLException" + "=>" + se);

			Logging.err.println("Query: " + query);
			for (int input = 0; input < input_value.length; input++) {
				Logging.err.println("Query Parameter [" + input + "]>>"
						+ input_value[input]);
			}

			trxResultCode = "N";
			trxResultMsg = se.getErrorCode() + ", " + this.getClass().getName()
					+ "." + "updateObject() SQLException" + "=>" + se;
			throw new PException(se);

		} catch (Exception e) {

			Logging.err.println(this.getClass().getName() + "."
					+ "updateObject() Exception" + "=>" + e);
			trxResultCode = "N";
			trxResultMsg = this.getClass().getName() + "."
					+ "updateObject() Exception" + "=>" + e;
			throw new PException(e);

		} finally {
			if (pconn == null) {
				try {
					close(conn);
				} catch (Exception e) {
				}
				try {
					close(pstmt);
				} catch (Exception e) {
				}
			} else {
				try {
					close(pstmt);
				} catch (Exception e) {
				}
			}

			rtn_rst[0] = trxResultCode;
			rtn_rst[1] = trxResultMsg;
		}

		return rtn_rst;
	}

}
