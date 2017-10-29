import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBHandler {

	private ClientThread clientThread;
	private DBConnection dbCon;
	private String text;
	private String sqlQuery;


	public DBHandler() {
		dbCon = new DBConnection();
		dbCon.connect();
	}

	/**
	 * Runs a query that drops the table if it exists.
	 */
	public void dropTablesIfExists() {
		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate("DROP TABLE IF EXISTS Subject;");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	/**
	 * this method uses readSqlFile(String filepath) to run a query that creates the table.
	 * @return a confirmation text
	 */
	public String createSubjectTable() {

		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate(readSqlFile("target/textfiles/createSubjectTableSql.sql"));

			text = ("Subject table created...");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return text;
	}

	/**
	 * This method reads text from a file and returns it as a string.
	 * @param filepath the absolute file path to the .txt file.
	 * @return a string of the file by using a StringBuilder, in this intance a SQL-string.
	 */
	private String readSqlFile(String filepath){

		try {
			BufferedReader sqlFileReader = new BufferedReader(new FileReader(filepath));

			StringBuilder stringBuilder = new StringBuilder();
			String line = sqlFileReader.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = sqlFileReader.readLine();
			}

			sqlQuery = stringBuilder.toString();

		} catch (IOException ioex){
			ioex.getMessage();
		}
		return sqlQuery;
	}
}
