/**
 * @author Christian on 02.11.2017.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {

	List<Subject> subjectList = new ArrayList<>();
	private InputHandler inputHandler = new InputHandler();
	private DBHandler dbHandler = new DBHandler();

	@BeforeClass
	public static void setUpDatabase() throws SQLException, ClassNotFoundException {

		Connection con;
		Statement stmt = null;

		try {
			Class.forName("org.h2.Driver");

			con = DriverManager.getConnection("jdbc:h2:mem://");
			stmt = con.createStatement();

			stmt.executeUpdate("DROP SCHEMA IF EXISTS TestDB");
			stmt.executeUpdate("CREATE SCHEMA TestDB");

		} catch(SQLException sqle) {
			sqle.getMessage();
		}
	}

	@Before
	public void createTable(){

	}

	@After
	public void destroy(){
		try(Connection connection = DriverManager.getConnection("jdbc:h2:mem:TestDB");
		    Statement stmt = connection.createStatement()){

			stmt.executeUpdate("DROP TABLE TestDB.Subject");


		} catch (SQLException sqle){
		    	sqle.getMessage();
		}
	}

	@Test
	public void testGetAllSubjects(){

		try(Connection connection = DriverManager.getConnection("jdbc:h2:mem:TestDB");
		    Statement stmt = connection.createStatement()){

			inputHandler.addSubjectDataFromFile();

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Subject");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				subjectList.add(new Subject(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
			}

			for (Subject subject : subjectList){
				System.out.println(subject.toString());
			}

			assertEquals(10, subjectList.size());

			System.out.println(subjectList.size());

			rs.close();
			ps.close();

		} catch (SQLException sqle){
		    	sqle.getMessage();
		}
	}

}
