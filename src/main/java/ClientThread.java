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

				if (clientInput.ready()) {
					String clientMsg = clientInput.readLine();
					System.out.println(clientMsg);
				}

				if (serverInput.ready()) {
					String input = serverInput.readLine();
					outputToClient.println(input);
				}
			}
		} catch (IOException ioex) {
			ioex.getMessage();
		} catch (Exception e){
			e.getMessage();
		}

	}

	public void print(String text){
		try{
			outputToClient = new PrintWriter(threadSocket.getOutputStream(), true);
			outputToClient.println(text);
		} catch (IOException ioex){
			ioex.getMessage();
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
				outputToClient.print("Kobler til databasen...");
				dbConnection = new DBConnection();
				dbConnection.setupCheck();
				outputToClient.println("Vellykket");
				serverInitMenu();
				break;
			case "0":
				outputToClient.println("Serveren avsluttes...");
				flag = false;
				break;
			default:
				outputToClient.println("Feil svar! Prøv igjen...");
				menu();
				break;
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
				dbHandler = new DBHandler();
				dbHandler.dropTablesIfExists();
				outputToClient.print("Dropper tabeller...");
				outputToClient.println("Vellykket");
				serverInitMenu();
				break;
			case "2":
				//dbHandler = new DBHandler();
				outputToClient.print("Oppretter tabellene 'Student' og 'Teacher'...");
				dbHandler.createTables();
				outputToClient.println("Vellykket!");

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
				break;
		}
	}

	private void insertDataToTables() throws IOException, Exception{
		outputToClient.println("1: Fyll inn tabellene med data fra fil \n" +
				"9: Tilbake \n" +
				"0: Avslutt serveren");

		choice = clientInput.readLine();

		switch (choice) {
			case "1":
				outputToClient.print("Oppretter 'subject' table...'");
				inputHandler = new InputHandler();
				inputHandler.addSubjectDataFromFile();
				outputToClient.println("Vellykket");

				outputToClient.print("Oppretter 'teacher' table...'");
				inputHandler = new InputHandler();
				inputHandler.addTeacherDataFromFile();
				outputToClient.println("Vellykket");
				printDataFromDatabase();
				break;
			case "9":
				outputToClient.println("Går tilbake...");
				serverInitMenu();
				break;
			case "0":
				flag = false;
				break;
			default:
				outputToClient.println("Feil svar! Prøv igjen...");
				insertDataToTables();
				break;
		}
	}

	private void printDataFromDatabase() throws IOException, Exception{
		outputToClient.println("1: Print data fra tabellen 'subject'\n" +
				"2: Print data fra tabellen 'teacher'\n" +
				"3: Print all data fra begge tabellene\n" +
				"4: Print ett subject fra emnekode\n" +
				"9: Tilbake\n" +
				"0: Avslutt serveren");

		choice = clientInput.readLine();

		switch (choice){
			case "1":
				outputToClient.println("----------------- SUBJECTS ------------------");
				inputHandler = new InputHandler();
				inputHandler.printAllSubjects();
				printDataFromDatabase();
				break;
			case "2":
				outputToClient.println("----------------- TEACHERS ------------------");
				inputHandler = new InputHandler();
				inputHandler.addTeacherDataFromFile();
				printDataFromDatabase();
				break;
			case "3":
				outputToClient.println("----------- PRINTING ONE SUBJECT ------------");
				inputHandler = new InputHandler();
				inputHandler.printAllData();
				printDataFromDatabase();
				break;
			case "4":
				outputToClient.println("------------- PRINTING ALL DATA -------------");
				inputHandler = new InputHandler();
				inputHandler.printSingeSubject();
				printDataFromDatabase();
				break;
			case "9":
				outputToClient.println("Går tilbake...");
				insertDataToTables();
				break;
			case "0":
				flag = false;
				break;
			default:
				outputToClient.println("Feil svar! Prøv igjen...");
				printDataFromDatabase();
				break;
		}
	}
}