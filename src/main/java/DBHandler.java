import java.sql.*;

public class DBHandler {

	private DBConnection dbCon;
	private String text;


	public DBHandler() {
		dbCon = new DBConnection();
		dbCon.connect();
	}

	public void dropTablesIfExists() {
		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate("DROP TABLE IF EXISTS Subject;");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	public String createSubjectTable() {

		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate("CREATE TABLE Subject(" +
					"code VARCHAR(10) NOT NULL," +
					"name VARCHAR(100) NOT NULL," +
					"duration double NOT NULL," +
					"numStudents int NOT NULL," +
					"PRIMARY KEY (code)," +
					"UNIQUE code_UNIQUE (code ASC));");
			text = ("Subject table created...");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return text;
	}

	/**
	 * Test methods
	 */

	public DBConnection getDbCon() {
		return dbCon;
	}
}
