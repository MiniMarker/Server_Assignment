import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UserMenu {
	private Scanner scanner = new Scanner(System.in);
	private String choice;

	DBConnection dbConnection;
	DBHandler dbHandler;

	public static void main(String[] args) throws InterruptedException {
		UserMenu userMenu = new UserMenu();
		userMenu.menu();
	}

	/*public int userInput(){
		return;
	}*/

	public String menu() {
		System.out.println("Velkommen til serveren! \n" +
				"Skriv inn ønsket valg og avslutt med enter\n\n" +
				"1: Koble til og opprett databasen\n" +
				"0: Avslutt serveren");

		choice = scanner.nextLine();
		makeCodeWait();

		 switch (choice){
			case "1":
				System.out.println("Kobler til databasen");
				serverInitMenu();
				//dbConnection.connect();
				//dbConnection.setupCheck();

			break;
			case "0":
				System.out.println("Serveren avsluttes...");
				break;
			default:
				System.out.println("Feil svar! Prøv igjen...");
				menu();
		}
		return null;
	}
	private void serverInitMenu(){
		System.out.println("1: Drop tabeller hvis de eksisterer \n" +
				"2: Opprett tabeller \n" +
				"9: Tilbake \n" +
				"0: Avslutt serveren");

		choice = scanner.nextLine();
		makeCodeWait();

		switch (choice){
			case "1":
				//dbHandler.dropTablesIfExists();
				System.out.println("Dropper tabeller...");
				//dbHandler.dropTablesIfExists();
				serverInitMenu();
				break;
			case "2":
				System.out.println("Oppretter tabellene 'Student' og 'Teacher'");
				//dbHandler.createTables();
				System.out.println("Oppretting vellykket!");
				//insertDataToTables();
				break;
			case "9":
				System.out.println("Går tilbake...");
				menu();
			case "0":
				System.out.println("Serveren avsluttes...");
				System.out.println();
				break;
			default:
				System.out.println("Feil svar! Prøv igjen...");
				serverInitMenu();

		}
	}

	private void insertDataToTables(){
		System.out.println("1: Fyll inn tabellene med data fra fil \n" +
				"9: Tilbake \n" +
				"0: Avslutt serveren");

		choice = scanner.nextLine();
		makeCodeWait();

		switch (choice) {
			case "1":
				break;
		}
	}

	private void makeCodeWait(){
		try{
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException ie){
			ie.getMessage();
		}


	}

}
