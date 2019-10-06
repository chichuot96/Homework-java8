package vn.topica.itlab4.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDb {
	private final static String name = "root";
	private final static String password = "";
	private final static String port = "3306";
	private final static String dbName = "school";

	public static Statement connectDB() {

		Statement stmt = null;
		Connection con;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?serverTimezone=UTC",name, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
}
