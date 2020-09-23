import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class RunBank {
	private static HashMap<String, Customer> customerFromName;
	private static HashMap<Checking, Customer> customerFromChecking;
	private static HashMap<Savings, Customer> customerFromSavings;
	private static HashMap<Credit, Customer> customerFromCredit;
	private static HashMap<Integer, Checking> checkingFromNumber;
	private static HashMap<Integer, Savings> savingsFromNumber;
	private static HashMap<Integer, Credit> creditFromNumber;
	private static Scanner scnr;
	private static String menu = "customerMenu";
	private static Customer user;
	private static Account selectedAccount;
	
	public static void main(String[] args){
		//Welcome message
		System.out.println("Welcome to DisneyBank");
		
		//Ask user for file
		System.out.print("Enter file name with information: ");
		scnr = new Scanner(System.in);
		File infoFile = new File(System.getProperty("user.dir") + "\\" + scnr.nextLine());
		
		
		customerFromName = new HashMap<>();
		customerFromChecking = new HashMap<>();
		customerFromSavings = new HashMap<>();
		customerFromCredit = new HashMap<>();
		checkingFromNumber = new HashMap<>();
		savingsFromNumber = new HashMap<>();
		creditFromNumber = new HashMap<>();
		//Read file
		try {
			Scanner fileScnr = new Scanner(infoFile);
			
			//For every line make a new Custumer Account instance
			fileScnr.nextLine(); //Ignores first line
			while(fileScnr.hasNextLine()){
				String line = fileScnr.nextLine();
				line = line.replace(", "," ");
				String[] attributes = line.split(",");
				
				// add customers to hash map with name as the key
				customerFromName.put(attributes[0] + " " + attributes[1], new Customer(
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
		
		// add all the accounts to their specific hash map
		for(Customer customer : customerFromName.values()){
			customerFromChecking.put(customer.getChecking(), customer);
			customerFromSavings.put(customer.getSavings(), customer);
			customerFromCredit.put(customer.getCredit(), customer);
			checkingFromNumber.put(customer.getChecking().getNumber(), customer.getChecking());
			savingsFromNumber.put(customer.getSavings().getNumber(), customer.getSavings());
			creditFromNumber.put(customer.getCredit().getNumber(), customer.getCredit());
		}
		
		// ask user to sign
		System.out.println("Successfully entered data");
		
		menu = "userType";
		boolean running = true;
		while(running){
			switch (menu){
				case "userType":
					userTypeMenu();
					break;
				case "manager":
					managerMenu();
					break;
				case "askForAccountName":
					askForAccountName();
					break;
				case "askForAccountType":
					askForAccountType();
					break;
				case "askForChecking":
					askForChecking();
					break;
				case "askForSavings":
					askForSavings();
					break;
				case "askForCredit":
					askForCredit();
					break;
				case "customerSignIn":
					customerSignIn();
					break;
				case "customerMenu":
					customerMenu();
					break;
				case "deposit":
					deposit();
					break;
				case "askDepositMoney":
					askDepositMoney();
					break;
				case "withdraw":
					withdraw();
					break;
				case "transferMoney":
					transferMoney();
					break;
				case "sendMoney":
					sendMoney();
					break;
				case "done":
					running = false;
				default:
			}
		}
	}
	
	private static void userTypeMenu(){
		boolean valid = false;
		//Ask to sign in as a manager or customer until the user enters a correct input
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
					valid = true;
					menu = "customerSignIn";
					break;
				default:
					System.out.println("not an option please try again");
			}
		}while(!valid);
	}
	
	/******************************************************************************************************************
	 *                                          Manager
	 * ***************************************************************************************************************/
	
	private static void managerMenu(){
		boolean valid;
		do{
			// Display Menu for the the manager
			System.out.println("What would you like to do?\n");
			System.out.println("\tA. Inquire account by name");
			System.out.println("\tB. Inquire account by type/number");
			System.out.println("\tC. Sign in page\n");
			String input = scnr.nextLine();
			
			switch (input){
				case "A":
					menu = "askForAccountName";
					valid = true;
					break;
				case "B":
					menu = "askForAccountType";
					valid = true;
					break;
				case "C":
					menu = "userType";
					valid = true;
					break;
				default:
					valid = false;
					System.out.println("not an option please try again");
			}
		}while (!valid);
	}
	
	private static void askForAccountName(){
		boolean valid = false;
		do{
			System.out.println("Who's account would you like to inquire about");
			String input = scnr.nextLine();
			
			//Check if name is valid
			if(!customerFromName.keySet().contains(input)){
				System.out.println("Customer does not exist please try again.");
				continue;
			}
			
			//find costumer by name
			Customer customer = customerFromName.get(input);
			
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	private static void askForAccountType(){
		boolean valid;
		do{
			System.out.println("What type of account?");
			String input = scnr.nextLine();
			
			//Check account type
			switch (input){
				case "Checking":
					menu = "askForChecking";
					valid = true;
					break;
				case "Savings":
					menu = "askForSavings";
					valid = true;
					break;
				case "Credit":
					menu = "askForCredit";
					valid = true;
					break;
				default:
					System.out.println("not valid type please try again");
					valid = false;
			}
			
		}while(!valid);
	}
	
	private static void askForChecking(){
		boolean valid = false;
		do{
			System.out.println("What is the account number?");
			int input = Integer.parseInt(scnr.nextLine());
			
			//check if input is valid
			if(!checkingFromNumber.keySet().contains(input)){
				System.out.println("not a valid account number please try again");
				continue;
			}
			
			//Get account
			Checking checking = checkingFromNumber.get(input);
			Customer customer = customerFromChecking.get(checking);
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	private static void askForSavings(){
		boolean valid = false;
		do{
			System.out.println("What is the account number?");
			int input = Integer.parseInt(scnr.nextLine());
			
			//check if input is valid
			if(!savingsFromNumber.keySet().contains(input)){
				System.out.println("not a valid account number please try again");
				continue;
			}
			
			//get account
			Savings savings = savingsFromNumber.get(input);
			System.out.println("savings: " + savings.getBalance());
			Customer customer = customerFromSavings.get(savings);
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	private static void askForCredit(){
		boolean valid = false;
		do{
			System.out.println("What is the account number?");
			int input = Integer.parseInt(scnr.nextLine());
			
			//check if input is valid
			if(!creditFromNumber.keySet().contains(input)){
				System.out.println("not a valid account number please try again");
				continue;
			}
			
			//Get account
			Credit credit = creditFromNumber.get(input);
			Customer customer = customerFromCredit.get(credit);
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	/******************************************************************************************************************
	 *                                          Customer
	 * ***************************************************************************************************************/
	
	private static void customerSignIn(){
		boolean valid = false;
		do{
			System.out.println("What is your name?");
			String input = scnr.nextLine();
			
			//check if name is in the information
			if(!customerFromName.keySet().contains(input)){
				System.out.println("Name not found. Please try again");
				continue;
			}
			
			//Print customer
			user = customerFromName.get(input);
			System.out.println(user);
			menu = "customerMenu";
			valid = true;
		}while(!valid);
	}
	
	private static void customerMenu(){
		boolean valid = false;
		do{
			System.out.println("Welcome " + user.firstName + " " + user.lastName);
			System.out.println("\n\tA. Inquire");
			System.out.println("\tB. Deposit");
			System.out.println("\tC. Withdraw");
			System.out.println("\tD. Transfer money");
			System.out.println("\tE. Send Money");
			System.out.println("\tF. Sign in page");
			
			switch (scnr.nextLine()) {
				case "A":
					System.out.println(user);
					valid = true;
					break;
				case "B":
					menu = "deposit";
					valid = true;
					break;
				case "C":
					menu = "withdraw";
					valid = true;
					break;
				case "D":
					menu = "transferMoney";
					valid = true;
					break;
				case "E":
					menu = "sendMoney";
					valid = true;
					break;
				case "F":
					menu = "userType";
					valid = true;
					break;
				default:
					System.out.println("Not an option please try again");
			}
		}while(!valid);
	}
	
	private static void deposit(){
		boolean valid = false;
		do{
			System.out.println("Choose account to deposit your money");
			System.out.println("\n\tA. Checking");
			System.out.println("\tB. Savings");
			System.out.println("\tC. Credit");
			
			switch (scnr.nextLine()){
				case "A":
					selectedAccount = user.getChecking();
					menu = "askDepositMoney";
					valid = true;
					break;
				case "B":
					selectedAccount = user.getSavings();
					menu = "askDepositMoney";
					valid = true;
					break;
				case "C":
					selectedAccount = user.getCredit();
					menu = "askDepositMoney";
					valid = true;
					break;
				default:
					System.out.println("not an option pleas try again");
			}
		}while(!valid);
	}
	
	private static void askDepositMoney(){
		boolean valid = false;
		do{
			System.out.println("How much money do you want to deposit?");
			
			int input = Integer.parseInt(scnr.nextLine());
			if(selectedAccount.deposit(input)){
				valid = true;
				menu = "customerMenu";
				continue;
			}
			System.out.println("too many funds try again");
		}while (!valid);
	}
	
	private static void withdraw(){
	
	}
	
	private static void transferMoney(){
	
	}
	
	private static void sendMoney(){
	
	}
}
