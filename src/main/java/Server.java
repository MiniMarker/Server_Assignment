import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Server {

	public static void main(String[] args) {
		new Server();
	}

	private static int portNumber;
	private List<ClientThread> clients = new ArrayList<>();

	public Server() {
		readConfigFile();

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

			System.out.println("Server started on port: " + portNumber + " on: " + new Date());

			while (true){
				Socket socket = serverSocket.accept();

				ClientThread clientThread = new ClientThread(socket);
				clients.add(clientThread);

				new Thread(clientThread).start();
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
			input = Server.class.getClassLoader().getResourceAsStream(filePath);

			if (input == null){
				System.out.println("Unable to read file at " + filePath);
				return;
			}

			props.load(input);

			portNumber = Integer.parseInt(props.getProperty("portNumber"));

		} catch (IOException ioex){
			ioex.getMessage();
		}
	}

	public List<ClientThread> getClients() {
		return clients;
	}
}