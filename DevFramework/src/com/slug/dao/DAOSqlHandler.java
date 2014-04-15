package com.slug.dao;
/**
 * @(#) DAOSqlHandler.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.sql.*;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.slug.config.*;
import com.slug.exception.*;


/**
 * Insert the type's description here.
 * Creation date: (2002-03-05 ���� 4:04:50)
 * @author: Administrator
 */

 public class DAOSqlHandler {

    private InitialContext ctx;

    public DAOSqlHandler(){}

	public String getDataSourceName(String ds) throws PException
	{

		 AppConfigManager conf = AppConfigManager.getInstance();
		 //String dsName = conf.getString("webApp.datasource.default.url");
         String dsName = conf.getString("webApp.datasource."+ds+".url");

		return dsName;

	}
    public Connection getConnection() throws PException
    {
        return getConnection("default");
    }

    public Connection getConnection(String dsn) throws PException
    {

        Connection conn = null;
		DataSource ds = null;
        String dataSourceName ="";

        try {

            if(ctx == null) ctx = new InitialContext();

			dataSourceName = getDataSourceName(dsn);
            ds = (javax.sql.DataSource) ctx.lookup (dataSourceName);
            conn  = ds.getConnection();

        } catch (NamingException ne) {
            throw new SysException("NamingException while getting DataSource" + ne.getMessage() +"\n"+ ne);
        } catch (SQLException se) {
            throw new SysException("SQLExcpetion while ds.getConnection() to "+ ds + se);
        }
            return conn;
    }

    public void close(Connection conn) throws PException
    {
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            throw new SysException("SQLExcpetion while closing Connection : " + se);
        }
    }

    public void close(ResultSet rs) throws PException
    {
        try {
            if (rs != null) rs.close();
        } catch (SQLException se) {
            throw new SysException("SQLExcpetion while closing ResultSet : " + se);
        }
    }

    public void close(Statement stmt) throws PException
    {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException se) {
            throw new SysException("SQLExcpetion while closing Statement : " + se);
        }
    }

    public void close(PreparedStatement pstmt) throws PException
    {
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException se) {
            throw new SysException("SQLExcpetion while closing PreparedStatement : " + se);
        }
    }

    public void close(CallableStatement cstmt) throws PException
    {
        try {
            if (cstmt != null) cstmt.close();
        } catch (SQLException se) {
            throw new SysException("SQLExcpetion while closing CallableStatement : " + se);
        }
    }
    public void close(Connection conn, ResultSet rs, Statement stmt) throws PException
    {

        if(rs   != null) close(rs)   ;
        if(stmt != null) close(stmt) ;
        if(conn != null) close(conn) ;

    }
    public void close(Connection conn, ResultSet rs, PreparedStatement pstmt) throws PException
    {
        if(rs    != null) close(rs)    ;
        if(pstmt != null) close(pstmt) ;
        if(conn  != null) close(conn)  ;
    }
    public void close(Connection conn, ResultSet rs, CallableStatement cstmt) throws PException
    {
        if(rs    != null)  close(rs)    ;
        if(cstmt != null)  close(cstmt) ;
        if(conn  != null)  close(conn)  ;
    }

}
