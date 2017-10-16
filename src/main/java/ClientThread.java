import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private Socket threadSocket;

	public ClientThread(Socket socket) {
		threadSocket = socket;
	}

	public void run() {
		try {
			//working
			// reading server-input
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
			OutputStream outputStream = threadSocket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, true);

			// receiving from client
			InputStream inputStream = threadSocket.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(inputStream));

			String receiveMessage;
			String sendMessage;


			while (true){
				//working
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
/*
			//working
			out = new PrintWriter(threadSocket.getOutputStream(), true);
			serverIn = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));
			userIn = new BufferedReader(new InputStreamReader(System.in));

			while (!threadSocket.isClosed()){

				if (serverIn.ready()){
					String input = serverIn.readLine();

					if (input != null) {
						System.out.println(input);
					}
				}
				if (userIn.ready()) {
					out.println(name + " > " + userIn.readLine());
				}
			} */

			/*

			for (ClientThread thatClient : server.getClients()) {

				*/