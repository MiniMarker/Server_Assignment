package Database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

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
		fillDbCredentials();
	}

	public void fillDbCredentials(){

		try{
			br = new BufferedReader(new FileReader("dbConnectionCredentials.csv"));

			while ((line = br.readLine()) != null) {
				String[] strings = line.split(splitBy);

				dbName = strings[0];
				host = strings[1];
				userName = strings[2];
				password = strings[3];

				linesRead++;
				break;
			}
		} catch (IOException ioex){
			System.out.println("ERROR! feil ved lesing av fil: " + ioex.getMessage());
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

