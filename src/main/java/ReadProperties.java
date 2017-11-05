import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Christian on 05.11.2017.
 */
public class ReadProperties {

	private DBConnection dbConnection = new DBConnection();

	public ReadProperties() {
	}

	/**
	 * reads the propertyfile and setting the fields to its data for further use.
	 */
	public void readConfigFile(String filepath) {
		Properties props = new Properties();
		InputStream input;

		try {
			input = DBConnection.class.getClassLoader().getResourceAsStream(filepath);

			if (input == null) {
				System.out.println("Unable to read file at " + filepath);
				return;
			}

			props.load(input);

			dbConnection.ds.setDatabaseName(props.getProperty("dbName")); // = (props.getProperty("dbName"));
			dbConnection.ds.setServerName(props.getProperty("host")); //host = (props.getProperty("host"));
			dbConnection.ds.setUser(props.getProperty("username")); //userName = (props.getProperty("username"));
			dbConnection.ds.setPassword("password"); //password = (props.getProperty("password"));

		} catch (IOException ioex) {
			ioex.getMessage();
		}
	}



}
