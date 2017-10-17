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

	ArrayList<String> list = new ArrayList<>();
	private String line;
	private String splitBy = ",";

	public InputHandler() {
		dbCon = new DBConnection();
		dbHan = new DBHandler();

		dbCon.connect();
	}

	/**
	 * Add data to Subject table form file
	 */

	public void addSubjectDataFromFile() {

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

				System.out.println(count + " Rows created in table 'Subject'");

			} catch (SQLException sqle){
				System.out.println("SQL ERROR! " + sqle.getMessage());
			} catch (IOException ioex){
				System.out.println("IO ERROR! " + ioex.getMessage());
			}
		} catch (SQLException sqle2){
			System.out.println("SQL ERROR! " + sqle2.getMessage());
		}
	}

	/**
	 * Add data to Teacher table form file
	 */

	public void addTeacherDataFromFile(){

		try (Connection con = dbCon.ds.getConnection()){

			try {
				br = new BufferedReader(new FileReader("files/teachers.csv"));

				PreparedStatement prepTeacherStmt = con.prepareStatement("INSERT INTO Teacher VALUES (?,?,?,?)");

				int count = 0;

				while ((line = br.readLine()) != null){

					// Split by comma (,)
					String[] teachers = line.split(splitBy);

					prepTeacherStmt.setInt(1, Integer.parseInt(teachers[0]));
					prepTeacherStmt.setString(2, teachers[1]);
					prepTeacherStmt.setString(3, teachers[2]);
					prepTeacherStmt.setString(4, teachers[3]);

					prepTeacherStmt.executeUpdate();

					count++;
				}

				System.out.println(count + " Rows created in table 'Teacher'");

			} catch (SQLException sqle){
				System.out.println("SQL ERROR! " + sqle.getMessage());
			} catch (IOException ioex){
				System.out.println("IO ERROR! " + ioex.getMessage());
			}
		} catch (SQLException sqle2){
			System.out.println("SQL ERROR! " + sqle2.getMessage());
		}
	}

	/**
	 * Print one subject defined by subject.code
	 */
	public void printSingeSubject() {
		try (Connection con = dbCon.ds.getConnection()) {

			PreparedStatement prepSingeSubjectStmt = con.prepareStatement("SELECT * FROM Subject WHERE code = ?");

			System.out.print("Enter subject code: ");
			String code = scanner.nextLine();

			prepSingeSubjectStmt.setString(1, code);

			ResultSet rs = prepSingeSubjectStmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getInt(4));
			}
		} catch (SQLException sqle) {
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	/**
	 * Print all subjects in database
	 */

	public void printAllSubjects() {

		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Subject");

			while (rs.next()){
				System.out.println("Emnekode: " + rs.getString(1) +
						" Enmenavn: " + rs.getString(2) +
						" Varighet: " + rs.getDouble(3) +
						" Antall påmeldte: " + rs.getInt(4));
			}
		} catch (SQLException sqle) {
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	/**
	 * Print all teachers in database
	 */

	public void printAllTeachers() {

		try (Connection con = dbCon.ds.getConnection();
		     Statement stmt = con.createStatement()) {

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Teacher");

			while (rs.next()){
				clientThread.print("Lærerid: " + rs.getInt(1) + " Navn: " + rs.getString(2) + " Ikke ledig: " + rs.getString(3) + " Kontaktinfo: " + rs.getString(4));
			}
		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		}
	}

	/**
	 * Print all data from tables
	 */

	public void printAllData(){
		printAllSubjects();
		System.out.println("");
		printAllTeachers();
	}

	/*
	public void pupulateSubjectTeacherTable() throws SQLException{
		try (Connection con = dbCon.ds.getConnection();
			Statement stmt = con.createStatement()) {

			PreparedStatement prepSubjectTeacherStmt = con.prepareStatement("INSERT INTO Subject_Teacher VALUES (?,?,?,?)");


			prepSubjectTeacherStmt.executeQuery();
		}
	}
	*/

	/**
	 * Test methods
	 */

	public DBConnection getDbCon() {
		return dbCon;
	}
}
