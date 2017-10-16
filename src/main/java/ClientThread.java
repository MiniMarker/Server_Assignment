import Database.DBConnection;
import Database.DBHandler;
import Database.InputHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {

	private Socket threadSocket;
	private Scanner scanner = new Scanner(System.in);
	private String choice;
	private boolean flag = true;

	private PrintWriter outputToClient;
	private BufferedReader clientInput;

	private DBConnection dbConnection;
	private DBHandler dbHandler;
	private InputHandler inputHandler;


	public ClientThread(Socket socket) {
		threadSocket = socket;
	}

	public void run() {
		try(BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in))) {

			outputToClient = new PrintWriter(threadSocket.getOutputStream(), true);
			clientInput = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));

			while (flag) {

				menu();

				/*
				if (clientInput.ready()) {
					String clientMsg = clientInput.readLine();
					System.out.println(clientMsg);
				}

				if (serverInput.ready()) {
					String input = serverInput.readLine();
					outputToClient.println(input);
				}
				*/
			}
		} catch (IOException ioex) {
			ioex.getMessage();
		} catch (Exception e){
			e.getMessage();
		}

	}

	public void menu() throws Exception {
		//String menuMsg =
		outputToClient.println("Velkommen til serveren! \n" +
				"Skriv inn ønsket valg og avslutt med enter\n\n" +
				"1: Koble til og opprett databasen\n" +
				"0: Avslutt serveren");

		choice = clientInput.readLine();
		//choice = scanner.nextLine();

		switch (choice){
			case "1":
				outputToClient.println("Kobler til databasen");
				serverInitMenu();
				dbConnection.connect();
				dbConnection.setupCheck();
				break;
			case "0":
				outputToClient.println("Serveren avsluttes...");
				flag = false;
				break;
			default:
				outputToClient.println("Feil svar! Prøv igjen...");
				menu();
		}
	}

	private void serverInitMenu() throws IOException, Exception {
		outputToClient.println("1: Drop tabeller hvis de eksisterer \n" +
				"2: Opprett tabeller \n" +
				"9: Tilbake \n" +
				"0: Avslutt serveren");

		choice = clientInput.readLine();

		switch (choice){
			case "1":
				dbHandler.dropTablesIfExists();
				outputToClient.println("Dropper tabeller...");
				serverInitMenu();
				break;
			case "2":
				outputToClient.println("Oppretter tabellene 'Student' og 'Teacher'");
				dbHandler.createTables();
				outputToClient.println("Oppretting vellykket!");
				insertDataToTables();
				break;
			case "9":
				outputToClient.println("Går tilbake...");
				menu();
			case "0":
				outputToClient.println("Serveren avsluttes...");
				flag = false;
				break;
			default:
				outputToClient.println("Feil svar! Prøv igjen...");
				serverInitMenu();

		}
	}

	private void insertDataToTables() throws IOException{
		outputToClient.println("1: Fyll inn tabellene med data fra fil \n" +
				"9: Tilbake \n" +
				"0: Avslutt serveren");

		choice = clientInput.readLine();

		switch (choice) {
			case "1":
				inputHandler.addSubjectDataFromFile();
				inputHandler.addTeacherDataFromFile();
				break;
			case "0":
				flag = false;
				break;
		}
	}
}