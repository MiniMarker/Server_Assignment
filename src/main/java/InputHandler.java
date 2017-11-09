import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {

	private DBConnection dbCon;
	private String text = "";
	private String result;

	public InputHandler() {
		dbCon = new DBConnection();
		dbCon.connect();
	}



	/**
	 * Add data to Subject table form file
	 * @return returns numbers of rows created
	 */
	public String addSubjectDataFromFile(Connection connection) {

		try (Connection con = connection) {

			try (BufferedReader br = new BufferedReader(new FileReader("target/textfiles/subjects.csv"));
			     PreparedStatement prepSubjectStmt = con.prepareStatement("INSERT INTO Subject VALUES (?,?,?,?)")) {

				int count = 0;
				String line;
				String splitBy = ",";

				while ((line = br.readLine()) != null) {

					// Split by comma (,)
					String[] subjects = line.split(splitBy);

					prepSubjectStmt.setString(1, subjects[0]);
					prepSubjectStmt.setString(2, subjects[1]);
					prepSubjectStmt.setDouble(3, Double.parseDouble(subjects[2]));
					prepSubjectStmt.setInt(4, Integer.parseInt(subjects[3]));

					prepSubjectStmt.executeUpdate();

					count++;
				}

				text = (count + " Rows created in table 'Subject'");

			} catch (SQLException sqle) {
				System.out.println("SQL ERROR! " + sqle.getMessage());
			} catch (IOException ioex) {
				System.out.println("IO ERROR! " + ioex.getMessage());
			}
		} catch (SQLException sqle2) {
			System.out.println("SQL ERROR! " + sqle2.getMessage());
		}
		return text;
	}

	/**
	 * Print one subject defined by subject.code
	 * @return one ResultSet defined by a query based on subject.code
	 */
	public String printSingleSubject(Connection connection, String code) {
		try (Connection con = connection;
		     PreparedStatement prepSingeSubjectStmt = con.prepareStatement("SELECT * FROM Subject WHERE code = ?")) {

			prepSingeSubjectStmt.setString(1, code);

			ResultSet rs = prepSingeSubjectStmt.executeQuery();

			while (rs.next()) {
				text = (rs.getString(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getInt(4));
			}

			if (text.isEmpty()){
				text = "Found no subject with code: " + code;
			}

		} catch (SQLException sqle) {
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return text;
	}

	/**
	 * Print all subjects in database
	 * @return Strings of all rows in the db-table built by using a StringBuilder
	 */
	public String printAllSubjects(Connection connection) {

		try (Connection con = connection;
		     Statement stmt = con.createStatement()) {

			ResultSet rs = stmt.executeQuery("SELECT * FROM Subject");

			StringBuilder stringBuilder = new StringBuilder();

			while (rs.next()) {
				stringBuilder.append(
						"Emnekode: " + rs.getString(1) +
						" Enmenavn: " + rs.getString(2) +
						" Varighet: " + rs.getDouble(3) +
						" Antall påmeldte: " + rs.getInt(4) + "\n");
			}

			result = stringBuilder.toString();

		} catch (SQLException sqle) {
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return result;
	}
}
