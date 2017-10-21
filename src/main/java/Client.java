import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Properties;

public class Client {

	private static String host;
	private static int portNumber;

	public static void main(String[] args) {
		new Client();
	}

	private Client() {
		readConfigFile();

		try (Socket socket = new Socket(host, portNumber);
		     PrintWriter sendToServer = new PrintWriter(socket.getOutputStream(), true);
		     BufferedReader serverOutput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in))) {

			System.out.println("Sever connected on: " + host + "/" + portNumber + " at: " + new Date());

			while(true) {

				if (clientInput.ready()){
					String input = clientInput.readLine();
					sendToServer.println(input);
				}

				if (serverOutput.ready()){
					String output = serverOutput.readLine();
					System.out.println(output);
				}
			}
		} catch (IOException ioex) {
			ioex.getMessage();
		}
	}

	private void readConfigFile(){
		Properties props = new Properties();
		InputStream input = null;

		try{
			String filePath = "serverConfig.properties";
			input = Client.class.getClassLoader().getResourceAsStream(filePath);

			if (input == null){
				System.out.println("Unable to read file at " + filePath);
				return;
			}

			props.load(input);

			host = (props.getProperty("host"));
			portNumber = Integer.parseInt(props.getProperty("portNumber"));

		} catch (IOException ioex){
			ioex.getMessage();
		}
	}
}
