package databaseProcesses;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneralDB implements ISqlHelper {

	private String username = "gokay2027";
	private String password = "8605968605"; // Þifreyi tekrar GÝRERSÝN
	private Connection con; // DATABASE ÞEMASI BAÞTAN KURULACAK DATABASE ADI AYNI OLACAK
	private Statement stmt;
	private static GeneralDB DB = new GeneralDB();

	private GeneralDB() {
		try {

			this.con = DriverManager.getConnection("jdbc:mysql://localhost/softwaremanagementsystem", username,
					password);

			this.stmt = con.createStatement();

		} catch (Exception e) {

			System.out.println(e);

		}
	}

	@Override
	public void insertData(String insertsql) throws SQLException {
		// TODO Auto-generated method stub

		stmt.executeUpdate(insertsql);

	}

	@Override
	public void deleteData(String deleteexe) throws SQLException {
		// TODO Auto-generated method stub

		stmt.executeUpdate(deleteexe);

	}

	@SuppressWarnings("unused")
	@Override
	public ResultSet selectData(String selectExe) throws SQLException {

		ResultSet rs;

		// TODO Auto-generated method stub

		return rs = stmt.executeQuery(selectExe);

	}

	@Override
	public void updateData(String updateExe) throws SQLException {
		// TODO Auto-generated method stub

		stmt.executeUpdate(updateExe);

	}

	public static GeneralDB getObject() {
		return DB;
	}
}
