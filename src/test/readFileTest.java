import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

/**
 * @author Christian on 01.11.2017.
 */
public class readFileTest {

	@Test
	public void shouldReturnOneString(){
		DBHandler dbHandler = new DBHandler();

		String filepath = "target/textfiles/testFile.csv";
		String result = dbHandler.readSqlFile(filepath);

		assertEquals("This is a test to see if this method reads the file correctly. ", result);
	}
}
