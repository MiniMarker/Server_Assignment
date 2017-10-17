import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

	MysqlDataSource ds;
	BufferedReader br;

	private String line;
	private String splitBy = ",";

	private String dbName;
	private String host;
	private String userName;
	private String password;

	private boolean connectTest;
	int linesRead;

	public DBConnection() {
		readConfigFile();
	}

	private void readConfigFile(){
		Properties props = new Properties();
		InputStream input = null;

		try{
			String filePath = "dbconfig.properties";
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

	public void connect(){
		ds = new MysqlDataSource();
		ds.setDatabaseName(dbName);
		ds.setServerName(host);
		ds.setUser(userName); //root
		ds.setPassword(password); //pass
	}

	public void setupCheck() {
		Connection con;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Connecting to " + host + " database using username = " + userName + "...");
			con = DriverManager.getConnection("jdbc:mysql://" + host + "/?user=" + userName + "&password=" + password);
			System.out.println("Connected!");

			stmt = con.createStatement();

			stmt.executeUpdate("DROP SCHEMA IF EXISTS " + dbName);
			stmt.executeUpdate("CREATE SCHEMA " + dbName);
			System.out.println("Database " + dbName + " created!");

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
				sqle2.printStackTrace();
			}
		}
	}


	/**
	 * Test methods
	 */
	public String getDbName() {
		return dbName;
	}

}

