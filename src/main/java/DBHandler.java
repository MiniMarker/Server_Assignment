import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBHandler {

	private String text;
	private String result;


	public DBHandler() {

	}

	/**
	 * Runs a query that drops the table if it exists.
	 * @param connection a MySQLDataSource
	 * @return a boolean to check if the deletion was successful.
	 */
	public boolean dropTablesIfExists(Connection connection) {
		try (Connection con = connection;
		     Statement stmt = con.createStatement()) {

			stmt.executeUpdate("DROP TABLE IF EXISTS Subject;");
			return true;

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return false;
	}

	/**
	 * this method uses readSqlFile(String filepath) to run a query that creates the table.
	 * @param connection a MySQLDataSource
	 * @return a confirmation text
	 */
	public String createSubjectTable(Connection connection) {

		try (Connection con = connection;
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
	public String readSqlFile(String filepath){

		try(BufferedReader sqlFileReader = new BufferedReader(new FileReader(filepath))) {

			StringBuilder stringBuilder = new StringBuilder();
			String line = sqlFileReader.readLine();

			while (line != null) {
				stringBuilder.append(line + " ");
				line = sqlFileReader.readLine();
			}

			result = stringBuilder.toString();

		} catch (IOException ioex){
			ioex.getMessage();
		}
		return result;
	}
}
