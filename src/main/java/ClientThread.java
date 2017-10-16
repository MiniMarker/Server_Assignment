import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private Server server;
	private Socket threadSocket;
	private String receiveMessage;
	private String sendMessage;

	private Scanner scanner = new Scanner(System.in);
	private String choice;

	public ClientThread(Socket socket) {
		threadSocket = socket;
	}

	public void run() {
		try {
			// reading server-input
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

			//sending to client
			PrintWriter printWriter = new PrintWriter(threadSocket.getOutputStream(), true);

			// receiving from client
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));

			while (true){
				if((receiveMessage = receiveRead.readLine()) != null) {
					System.out.println(receiveMessage);
				}

				sendMessage = serverInput.readLine();
				printWriter.println(sendMessage);
				printWriter.flush();
				}

		} catch (IOException ioex) {
			ioex.getMessage();
		}
	}
}