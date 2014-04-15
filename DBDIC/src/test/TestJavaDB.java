package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJavaDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
				System.out.println("EMPLOYEE NAME : "
						+ resultSet.getString("EMPNAME"));
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
