import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class RunBank {
	public static HashMap<String, Customer> customerFromId;
	public static HashMap<String, Customer> customerFromName;
	
	public static void main(String[] args){
		//Welcome message
		System.out.println("Welcome to DisneyBank");
		
		//Ask user for file
		System.out.print("Enter file name with information: ");
		Scanner scnr = new Scanner(System.in);
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
		
		boolean valid = false;
		do{
			System.out.println("Sign in as:");
			System.out.println("\n\tA) Manager");
			System.out.println("\tB) Customer\n");
			String input = scnr.nextLine();
			
			switch (input){
				case "A":
					System.out.println("You signed in as a manager");
					valid = true;
					break;
				case "B":
					System.out.println("You signed in as a customer");
					valid = true;
					break;
				default:
					System.out.println("not an option please try again");
			}
		}while(!valid);
		
		while(running());
	}
	
	public static boolean running(){
		return false;
	}
}
