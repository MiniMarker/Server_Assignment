import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler  {

	private ClientThread clientThread;
	private Scanner scanner = new Scanner(System.in);
	private DBConnection dbCon;
	private DBHandler dbHan;
	private BufferedReader br;

	private ArrayList<String> list = new ArrayList<>();
	private String line;
	private String splitBy = ",";
	private String text = "";

	public InputHandler() {
		dbCon = new DBConnection();
		dbHan = new DBHandler();

		dbCon.connect();
	}

	/**
	 * Add data to Subject table form file
	 */

	public String addSubjectDataFromFile() {

		try (Connection con = dbCon.ds.getConnection()) {

			try {
				br = new BufferedReader(new FileReader("files/subjects.csv"));

				PreparedStatement prepSubjectStmt = con.prepareStatement("INSERT INTO Subject VALUES (?,?,?,?)");

				int count = 0;

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

			} catch (SQLException sqle){
				System.out.println("SQL ERROR! " + sqle.getMessage());
			} catch (IOException ioex){
				System.out.println("IO ERROR! " + ioex.getMessage());
			}
		} catch (SQLException sqle2){
			System.out.println("SQL ERROR! " + sqle2.getMessage());
		}
		return text;
	}

	/**
	 * Print one subject defined by subject.code
	 */
	public String printSingleSubject(String code) {
		try (Connection con = dbCon.ds.getConnection()) {

			PreparedStatement prepSingeSubjectStmt = con.prepareStatement("SELECT * FROM Subject WHERE code = ?");

			prepSingeSubjectStmt.setString(1, code);

			ResultSet rs = prepSingeSubjectStmt.executeQuery();

			while (rs.next()) {
				text = (rs.getString(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getInt(4));
			}

		} catch (SQLException sqle) {
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
		return text;
	}

	/**
	 * Print all subjects in database
	 */


	//TODO fix this!!!!!!!!!!!!
	public ArrayList<String> printAllSubjects() {

		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Subject");

			while (rs.next()){
				list.add("Emnekode: " + rs.getString(1) +
						" Enmenavn: " + rs.getString(2) +
						" Varighet: " + rs.getDouble(3) +
						" Antall påmeldte: " + rs.getInt(4) + "\n");
			}
		} catch (SQLException sqle) {
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}

		return list;
	}

	/**
	 * Test methods
	 */

	public DBConnection getDbCon() {
		return dbCon;
	}
}
