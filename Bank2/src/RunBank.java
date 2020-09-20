import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.FileHandler;

/**
 *
 */
public class RunBank {
	private static HashMap<String, Customer> customerFromId;
	private static HashMap<String, Customer> customerFromName;
	private static Scanner scnr;
	private static String menu;
	
	public static void main(String[] args){
		//Welcome message
		System.out.println("Welcome to DisneyBank");
		
		//Ask user for file
		System.out.print("Enter file name with information: ");
		scnr = new Scanner(System.in);
		File infoFile = new File(System.getProperty("user.dir") + "\\" + scnr.nextLine());
		
		customerFromId = new HashMap<>();
		customerFromName = new HashMap<>();
		//Read file
		try {
			Scanner fileScnr = new Scanner(infoFile);
			
			//For every line make a new Custumer Account instance
			fileScnr.nextLine(); //Ignores first line
			while(fileScnr.hasNextLine()){
				String line = fileScnr.nextLine();
				line = line.replace(", ","#");
				String[] attributes = line.split(",");
				
				// add customers to hash map with name as the key
				customerFromName.put(attributes[0], new Customer(
					attributes[0],                          //firstName
					attributes[1],                          //lastName
					attributes[2],                          //dob
					attributes[4],                          //address
					attributes[5],                          //phone
					attributes[3],                          //id
					new Checking(
						Integer.parseInt(attributes[6]),    //checking account number
						Double.parseDouble(attributes[9])   //checking balance
					),
					new Savings(
						Integer.parseInt(attributes[7]),    //savings account number
						Double.parseDouble(attributes[10])  //savings balance
					),
					new Credit(
						Integer.parseInt(attributes[8]),    //credit account number
						Double.parseDouble(attributes[11])  //credit balance
					)
				));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found please try again");
		}
		
		// add names to the map of that gets customer from Id
		for(Customer customer: customerFromName.values()){
			customerFromId.put(customer.getId(), customer);
		}
		
		// ask user to sign
		System.out.println("Successfully entered data");
		
		menu = "userType";
		boolean running = true;
		while(running){
			switch (menu){
				case "userType":
					userType();
					break;
				case "manager":
					managerMenu();
					break;
				case "customer":
					break;
				case "done":
					running = false;
				default:
			}
		}
	}
	
	private static void userType(){
		boolean valid = false;
		do{
			System.out.println("Sign in as:");
			System.out.println("\n\tA) Manager");
			System.out.println("\tB) Customer\n");
			
			switch (scnr.nextLine()){
				case "A":
					System.out.println("You signed in as a manager");
					valid = true;
					menu = "manager";
					break;
				case "B":
					System.out.println("You signed in as a customer");
					valid = true;
					menu = "customer";
					break;
				default:
					System.out.println("not an option please try again");
			}
		}while(!valid);
	}
	
	private static boolean managerMenu(){
		boolean valid = true;
		do{
			// Display Menu for the the manager
			System.out.println("A. Inquire account by name");
			System.out.println("B. Inquire account by type/number");
			String input = scnr.nextLine();
			
			switch (input){
				case "A":
					System.out.println("Who's account would you like to inquire about");
					valid = true;
					break;
				case "B":
					System.out.println("What account type?");
					valid = true;
					break;
				default:
					valid = false;
					System.out.println("not an option please try again");
			}
		}while (!valid);
		return true;
	}
}
