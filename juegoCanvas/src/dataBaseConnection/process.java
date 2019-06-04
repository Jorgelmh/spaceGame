package dataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class process {

	private static String UPDATE = "UPDATE record set recordcol=? WHERE idrecord = 1";
	private static String SELECT = "select recordcol from record";

	public void update(int record) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = conection.getConection();
			ps = con.prepareStatement(UPDATE);
			int index = 1;
			ps.setInt(index, record);
			ps.executeUpdate();

		} catch (SQLException sql) {

		} finally {

			conection.close(con);
			conection.close(ps);

		}

	}

	public int select() {

		Connection con = null;
		ResultSet result = null;
		PreparedStatement ps = null;
		int resultado = 0;

		try {

			con = conection.getConection();
			ps = con.prepareStatement(SELECT);
			result = ps.executeQuery();
			result.next();

			resultado = result.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			conection.close(con);
			conection.close(ps);
			conection.close(result);

		}

		return resultado;

	}

}
