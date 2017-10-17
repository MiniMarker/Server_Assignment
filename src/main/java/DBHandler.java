import java.sql.*;

public class DBHandler {

	private DBConnection dbCon;


	public DBHandler() {
		dbCon = new DBConnection();
		dbCon.connect();
	}

	public void dropTablesIfExists() {
		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate("DROP TABLE IF EXISTS Subject_Teacher;");

			stmt.executeUpdate("DROP TABLE IF EXISTS Subject;");

			stmt.executeUpdate("DROP TABLE IF EXISTS Teacher;");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	public void createTables() {

		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate("CREATE TABLE Subject(" +
					"code VARCHAR(10) NOT NULL," +
					"name VARCHAR(100) NOT NULL," +
					"duration double NOT NULL," +
					"numStudents int NOT NULL," +
					"PRIMARY KEY (code)," +
					"UNIQUE code_UNIQUE (code ASC));");
			System.out.println("Subject table created...");

			stmt.executeUpdate("CREATE TABLE Teacher(" +
					"id INT NOT NULL AUTO_INCREMENT," +
					"name VARCHAR(100) NOT NULL," +
					"notAvailable VARCHAR(10)," +
					"contact VARCHAR(100) NOT NULL," +
					"PRIMARY KEY (id)," +
					"UNIQUE code_UNIQUE (id ASC));");
			System.out.println("Teacher table created...");

			stmt.executeUpdate("CREATE TABLE Subject_Teacher(" +
					"Subject_code VARCHAR(10) NOT NULL," +
					"Teacher_id INT NOT NULL," +
					"week INT NOT NULL," +
					"classroom VARCHAR(45) NOT NULL," +
					"PRIMARY KEY (Subject_code, Teacher_id)," +
					"INDEX Teacher_id_idx (Teacher_id ASC)," +
					"CONSTRAINT Subject_code" +
					"\tFOREIGN KEY (Subject_code) REFERENCES Subject(code)" +
					"    ON DELETE CASCADE" +
					"    ON UPDATE CASCADE," +
					"CONSTRAINT Teacher_id" +
					"    FOREIGN KEY (Teacher_id) REFERENCES Teacher(id)" +
					"    ON DELETE CASCADE" +
					"    ON UPDATE CASCADE);");
			System.out.println("Subject_Teacher table created...");
		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	/**
	 * Test methods
	 */

	public DBConnection getDbCon() {
		return dbCon;
	}
}
