import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

	MysqlDataSource ds;
	private String dbName;
	private String host;
	private String userName;
	private String password;

	public DBConnection() {
		readConfigFile();
	}

	/**
	 * reads the propertyfile and setting the fields to its data for further use.
	 */
	private void readConfigFile(){
		Properties props = new Properties();
		InputStream input;

		try{
			String filePath = "dbConfig.properties";
			input = DBConnection.class.getClassLoader().getResourceAsStream(filePath);

			if (input == null){
				System.out.println("Unable to read file at " + filePath);
				return;
			}

			props.load(input);

			dbName = (props.getProperty("dbName"));
			host = (props.getProperty("host"));
			userName = (props.getProperty("username"));
			password = (props.getProperty("password"));

		} catch (IOException ioex){
			ioex.getMessage();
		}
	}

	/**
	 * establishes a new MySqlDataSource bu using the properties of the fields
	 */
	public void connect(){
		ds = new MysqlDataSource();
		ds.setDatabaseName(dbName);
		ds.setServerName(host);
		ds.setUser(userName);
		ds.setPassword(password);
	}

	/**
	 * does a setup check that connects to the mySQL server and creates the schema
	 */
	public void setupCheck() {
		Connection con;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://" + host + "/?user=" + userName + "&password=" + password);
			stmt = con.createStatement();

			stmt.executeUpdate("DROP SCHEMA IF EXISTS " + dbName);
			stmt.executeUpdate("CREATE SCHEMA " + dbName);

		} catch (SQLException sqle){
			System.out.println("SQL ERROR! " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe){
			System.out.println("ClassNotFoundError! " + cnfe.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle2) {
				//empty on purpose
			}
		}
	}
}

