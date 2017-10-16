import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	public static void main(String[] args) {
		Server server = new Server();
	}

	private static final int portNumber = 4444;

	private int serverPort;
	//private List<ClientThread> clients = new ArrayList<ClientThread>();

	public Server() {
		try {

			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("Server started on port: " + portNumber);

			while (true){
				Socket socket = serverSocket.accept();

				ClientThread clientThread = new ClientThread(socket);

				new Thread(clientThread).start();
			}
		} catch (IOException ioex) {
			ioex.getMessage();
		}
	}

	/*public List<ClientThread> getClients() {
		return clients;
	} */
}