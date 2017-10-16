import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

public class Client {

	private static final String host = "localhost";
	private static final int portNumber = 4444;

	private String userName;
	private String serverHost;
	private int serverPort;
	private Scanner userInputScanner;

	public static void main(String[] args) {
		Client client = new Client();
	}

	private Client() {
		try {
			Socket socket = new Socket(host, portNumber);

			//Thread.sleep(1000); //wait for network com

			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			// sending to client (pwrite object)
			OutputStream ostream = socket.getOutputStream();
			PrintWriter pwrite = new PrintWriter(ostream, true);

			// receiving from server ( receiveRead  object)
			InputStream istream = socket.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

			System.out.println("Start the chitchat, type and press Enter key");

			String receiveMessage, sendMessage;
			while(true)
			{
				sendMessage = keyRead.readLine();  // keyboard reading
				pwrite.println(sendMessage);       // sending to server
				pwrite.flush();                    // flush the data
				if((receiveMessage = receiveRead.readLine()) != null) //receive from server
				{
					System.out.println(receiveMessage); // displaying at DOS prompt
				}
			}
		} catch (IOException ioex) {
			ioex.getMessage();
		}
	}

	private void startClient(Scanner scanner) {

	}

	/*
		try {

			Socket socket = new Socket("localhost", 4444);

			// working reading client-input
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, true);

			// receiving from server-input
			InputStream inputStream = socket.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(inputStream));

			String receiveMessage;
			String sendMessage;

			System.out.println("You have connected the server on: " + new Date());

			while(true)

	{
				//working
				sendMessage = serverInput.readLine();
				printWriter.println(sendMessage);
				printWriter.flush();

				if((receiveMessage = receiveRead.readLine()) != null) {
					System.out.println(receiveMessage);
				}

			}
		} catch (IOException exception) {
			System.out.println("Error: " + exception);
		}
	}
	*/


}
