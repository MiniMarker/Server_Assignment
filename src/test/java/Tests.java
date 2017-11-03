import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Christian on 01.11.2017.
 */
public class Tests {

	@Test
	public void shouldReturnOneString(){
		DBHandler dbHandler = new DBHandler();

		String filepath = "target/textfiles/testFile.csv";
		String result = dbHandler.readSqlFile(filepath);

		System.out.println(result);
		assertEquals("This is a test to see if this method reads the file correctly. ", result);
	}
}
