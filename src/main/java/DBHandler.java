import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBHandler {

	private DBConnection dbCon;
	private String text;
	private String sqlQuery;


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

			String readSqlFileString = readSqlFile("files/createSubjectTableSql.txt");

			stmt.executeUpdate(readSqlFileString);

			text = ("Subject table created...");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return text;
	}

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
