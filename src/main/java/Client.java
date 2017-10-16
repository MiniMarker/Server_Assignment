import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Properties;

public class Client {

	//Socket
	private static String host; // = "localhost";
	private static int portNumber; // = 4444;

	public static void main(String[] args) {
		new Client();
	}

	private Client() {
		readConfigFile();

		try {
			Socket socket = new Socket(host, portNumber);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Sever connected on: " + host + "/" + portNumber + " at: " + new Date());

			// sending to server
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

			// receiving from server
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String receiveMessage;
			String sendMessage;

			while(true) {

				sendMessage = bufferedReader.readLine();  // keyboard reading
				printWriter.println(sendMessage);       // sending to server
				printWriter.flush();                    // flush the data
				if((receiveMessage = receiveRead.readLine()) != null) //receive from server
				{
					System.out.println(receiveMessage); // displaying at DOS prompt
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
			String filePath = "config.properties";
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
