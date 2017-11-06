import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Christian on 05.11.2017.
 */
public class DatabaseTests {

	private TestDBConnection testCon;

	@Before
	public void setup(){
		testCon = new TestDBConnection();
		assertTrue(testCon.setupCheck());

		DBHandler dbHandler = new DBHandler();
		dbHandler.dropTablesIfExists(testCon.connect());
		dbHandler.createSubjectTable(testCon.connect());
	}

	@Test
	public void setUpCheckTest() throws SQLException{
		Statement stmt = testCon.connect().createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM TestTimetableWesterdals.Subject");
		ResultSetMetaData rsmd = rs.getMetaData();

		String[] attributteNames = {"code", "name", "duration", "numStudents"};

		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			assertEquals(attributteNames[i], rsmd.getColumnName(i+1));
		}
	}

	@Test
	public void getPrimaryKeyTest() throws SQLException{
		DatabaseMetaData metaData = testCon.connect().getMetaData();
		ResultSet rs = metaData.getPrimaryKeys(null, "TestTimetableWesterdals", "Subject");

		while (rs.next()){
			assertEquals("code", rs.getString(4));
		}
	}

	@Test
	public void tableIsFilledTest() throws SQLException {
		InputHandler inputHandler = new InputHandler();
		inputHandler.addSubjectDataFromFile(testCon.connect());

		Statement stmt = testCon.connect().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM TestTimetableWesterdals.Subject");

		int rowCount = rs.last() ? rs.getRow() : 0;

		assertEquals(10, rowCount);
	}

	@Test
	public void readSqlFileTest(){
		DBHandler dbHandler = new DBHandler();

		String filepath = "target/textfiles/testFile.csv";
		String result = dbHandler.readSqlFile(filepath);

		assertEquals("This is a test to see if this method reads the file correctly. ", result);
	}

}
