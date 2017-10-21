import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBHandler {

	private DBConnection dbCon;
	private String text;
	private String filepath;


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

			filepath = "files/createSubjectTableSql.txt";
			BufferedReader sqlFileReader = new BufferedReader(new FileReader(filepath));

			StringBuilder stringBuilder = new StringBuilder();

			String line = sqlFileReader.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = sqlFileReader.readLine();
			}

			String sqlQuery = stringBuilder.toString();

			stmt.executeUpdate(sqlQuery);

			text = ("Subject table created...");

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		} catch (FileNotFoundException fnfex){
			System.out.println("Could not find file on: " + filepath);
		} catch (IOException ioex){
			System.out.println(ioex.getMessage());
		}
		return text;
	}
}
