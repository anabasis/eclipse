package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJavaDBServlet extends HttpServlet {

	protected void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {

		httpServletResponse.setContentType("text/plain");
		PrintWriter out = httpServletResponse.getWriter();
		out.println("Hello DB Get World!!");
		doExecute(out);
		out.close();
	}

	protected void doPost(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {

		httpServletResponse.setContentType("text/plain");
		PrintWriter out = httpServletResponse.getWriter();
		out.println("Hello DB Post World!!");
		doExecute(out);
		out.close();
	}

	protected void doExecute(PrintWriter out) {
		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager
					.getConnection("jdbc:derby:/Users/junseongjo/git/eclipse_workspace/DBDIC/DB_SQL/DBDIC.dat;create=true");
			statement = connection.createStatement();
			resultSet = statement
					.executeQuery("SELECT EMPNAME FROM EMPLOYEEDETAILS");
			while (resultSet.next()) {
				out.println("EMPLOYEE NAME : " + resultSet.getString("EMPNAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
