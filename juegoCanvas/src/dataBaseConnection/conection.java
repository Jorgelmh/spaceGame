package dataBaseConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class conection {

	private static String driverCode = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/spaceshipgame?useSSL=false";
	private static String user = "root";
	private static String password = "admin";

	private static Driver driver;

	public static synchronized Connection getConection() throws SQLException {

		if (driver == null) {

			try {

				Class driverClass = Class.forName(driverCode);
				driver = (Driver) driverClass.newInstance();
				DriverManager.registerDriver(driver);

			} catch (Exception sql) {

			}

		}

		return (Connection) DriverManager.getConnection(url, user, password);

	}

	public static void close(PreparedStatement ps) {

		if (ps != null) {

			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void close(ResultSet result) {

		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void close(Connection con) {

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
