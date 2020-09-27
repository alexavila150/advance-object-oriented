import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class RunBank {
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Attributes
	 ----------------------------------------------------------------------------------------------------------------*/
	
	// gets the customer instance using name as a key
	private static HashMap<String, Customer> namesToCustomers;
	
	//gets any of the accounts from their respective account number
	private static HashMap<Integer, Checking> numbersToCheckings;
	private static HashMap<Integer, Savings> numbersToSavings;
	private static HashMap<Integer, Credit> numbersToCredit;
	
	//Scanner of input
	private static Scanner scnr;
	
	//handles the menu UI that the user will go to next
	private static String menu;
	
	//the user that signs in in the sign in menu
	private static Customer user;
	
	//account that user selects to do transactions
	private static Account selectedAccount;
	
	//account that user selects to transfer money to
	private static Account destAccount;
	
	//stores the log history to print it later in the log file
	private static ArrayList<String> logMessages;
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Main
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * The main method ask the user for the name of the csv file where the information if coming from
	 * and then stores their addresses into hash tables so that the values could be access in constant
	 * time
	 * @param args arguments for the main method, it is expecting none of them
	 */
	public static void main(String[] args){
		//Initiate the Data Structures
		namesToCustomers = new HashMap<>();
		numbersToCheckings = new HashMap<>();
		numbersToSavings = new HashMap<>();
		numbersToCredit = new HashMap<>();
		logMessages = new ArrayList<>();
		
		//Welcome message
		System.out.println("Welcome to DisneyBank");
		
		boolean valid = false;
		do{
			//Ask user for file
			System.out.print("Enter file name with information: ");
			scnr = new Scanner(System.in);
			File infoFile = new File(System.getProperty("user.dir") + "\\" + scnr.nextLine());
			
			//Read file
			try {
				Scanner fileScnr = new Scanner(infoFile);
				
				//For every line make a new Custumer Account instance
				fileScnr.nextLine(); //Ignores first line
				while(fileScnr.hasNextLine()){
					String line = fileScnr.nextLine();
					line = line.replace(", "," ");
					String[] attributes = line.split(",");
					
					// create customer from csv information
					Customer customer = new Customer(
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
					);
					
					//Set customer attributes to the accounts
					customer.getSavings().setCustomer(customer);
					customer.getChecking().setCustomer(customer);
					customer.getCredit().setCustomer(customer);
					
					//Save Customer into hash map
					namesToCustomers.put(attributes[0] + " " + attributes[1], customer);
				}
				//File was process successfully
				valid = true;
				
			} catch (FileNotFoundException e) {
				System.out.println("file not found please try again");
			}
		}while(!valid);
		
		
		
		// add all the accounts to their specific hash map
		for(Customer customer : namesToCustomers.values()){
			numbersToCheckings.put(customer.getChecking().getNumber(), customer.getChecking());
			numbersToSavings.put(customer.getSavings().getNumber(), customer.getSavings());
			numbersToCredit.put(customer.getCredit().getNumber(), customer.getCredit());
		}
		
		// ask user to sign
		System.out.println("Successfully entered data");
		
		//This is the menu handler depending on the menu variable the user will be ask different things
		// and the menu will change as the user chooses specific options.
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
				case "askWithdrawMoney":
					askWithdrawMoney();
					break;
				case "transferMoney":
					transferMoney();
					break;
				case "checkingAskDestAccount":
					checkingAskDestAccount();
					break;
				case "savingsAskDestAccount":
					savingsAskDestAccount();
					break;
				case "askTransferAmount":
					askTransferAmount();
					break;
				case "sendMoney":
					sendMoney();
					break;
				case "done":
					System.out.println("Thank you for coming!");
					running = false;
				default:
			}
		}
		
		//Write to output file at the end of the program
		try {
			FileWriter myWriter = new FileWriter("BankOutput.csv");
			
			//First line of the output file
			myWriter.write("First Name,Last Name,Date of Birth,IdentificationNumber," +
				"Address,Phone Number,Checking Account Number,Savings Account Number," +
				"Checking Starting Balance,Savings Starting Balance,Credit Starting Balance\n");
			
			//gets customer sorted by account number
			for(int i = 0; i < namesToCustomers.size(); i++){
				myWriter.write(numbersToCheckings.get(1000 + i).getCustomer().toCsvLine()+ "\n");
			}
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
		}
		
		//Write log file
		for(String message : logMessages){
			System.out.println(message);
		}
	}
	
	/**
	 * Asks the user if they want to sign in as a Customer or as a Manager
	 */
	private static void userTypeMenu(){
		boolean valid = false;
		//Ask to sign in as a manager or customer until the user enters a correct input
		do{
			System.out.println("Sign in as:\n");
			System.out.println("\tA) Manager");
			System.out.println("\tB) Customer");
			System.out.println("\tC) Exit\n");
			
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
				case "C":
					valid = true;
					menu = "done";
					break;
				default:
					System.out.println("not an option please try again");
			}
		}while(!valid);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Manager
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Ask user if he wants to look for a customer by either using their name or one of their account
	 * number
	 */
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
	
	/**
	 * Ask for the name of the customer that the manager wants to inquire
	 */
	private static void askForAccountName(){
		boolean valid = false;
		do{
			System.out.println("Who's account would you like to inquire about?");
			String input = scnr.nextLine();
			
			//Check if name is valid
			if(!namesToCustomers.keySet().contains(input)){
				System.out.println("Customer does not exist please try again.");
				continue;
			}
			
			//find costumer by name
			Customer customer = namesToCustomers.get(input);
			
			//Inquire customer's information
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	/**
	 * Ask the manager what the account number is if they selected a checking account to look for
	 */
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
	
	/**
	 * Ask the manager what the account number is if they selected a savings account to look for
	 */
	private static void askForChecking(){
		boolean valid = false;
		do{
			System.out.println("What is the account number?");
			int input = Integer.parseInt(scnr.nextLine());
			
			//check if input is valid
			if(!numbersToCheckings.keySet().contains(input)){
				System.out.println("not a valid account number please try again");
				continue;
			}
			
			//Get account
			Checking checking = numbersToCheckings.get(input);
			Customer customer = checking.customer;
			
			//Inquire customer's information
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	/**
	 * Ask the manager what the account number is if they selected a savings account to look for
	 */
	private static void askForSavings(){
		boolean valid = false;
		do{
			System.out.println("What is the account number?");
			int input = Integer.parseInt(scnr.nextLine());
			
			//check if input is valid
			if(!numbersToSavings.keySet().contains(input)){
				System.out.println("not a valid account number please try again");
				continue;
			}
			
			//get account
			Savings savings = numbersToSavings.get(input);
			System.out.println("savings: " + savings.getBalance());
			Customer customer = savings.getCustomer();
			
			//Inquire customer's information
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	/**
	 * Ask the manager what the account number is if they selected a credit account to look for
	 */
	private static void askForCredit(){
		boolean valid = false;
		do{
			System.out.println("What is the account number?");
			int input = Integer.parseInt(scnr.nextLine());
			
			//check if input is valid
			if(!numbersToCredit.keySet().contains(input)){
				System.out.println("not a valid account number please try again");
				continue;
			}
			
			//Get account
			Credit credit = numbersToCredit.get(input);
			Customer customer = credit.getCustomer();
			
			//Inquire customer's information
			System.out.println(customer);
			menu = "manager";
			valid = true;
		}while(!valid);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Deposit
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * If user want to sign in as a customer then this menu will ask the user to enter their name to
	 * sign in
	 */
	private static void customerSignIn(){
		boolean valid = false;
		do{
			System.out.println("What is your name?");
			String input = scnr.nextLine();
			
			//check if name is in the information
			if(!namesToCustomers.keySet().contains(input)){
				System.out.println("Name not found. Please try again");
				continue;
			}
			
			//Print customer's information
			user = namesToCustomers.get(input);
			System.out.println("Welcome " + user.getFullName());
			System.out.println(user);
			menu = "customerMenu";
			valid = true;
		}while(!valid);
	}
	
	/**
	 * This is the main menu for the user with the main choices for him to do
	 */
	private static void customerMenu(){
		boolean valid = false;
		do{
			System.out.println("What would you like to do?\n");
			System.out.println("\tA. Inquire");
			System.out.println("\tB. Deposit");
			System.out.println("\tC. Withdraw");
			System.out.println("\tD. Transfer money");
			System.out.println("\tE. Send Money to another person");
			System.out.println("\tF. Sign in page\n");
			
			switch (scnr.nextLine()) {
				case "A":
					System.out.println(user);
					valid = true;
					
					// Log message
					logMessages.add(user.getFullName() + " made a balance inquire. Checking: $" +
						String.format("%.2f", user.getChecking().getBalance()) + " Savings: $" +
						String.format("%.2f", user.getSavings().getBalance()) + " Credit: $" +
						String.format("%.2f", user.getCredit().getBalance()));
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
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Deposit
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 *  Ask the user to choose for any of their three account to deposit money into
	 */
	private static void deposit(){
		boolean valid = false;
		do{
			System.out.println("Choose account to deposit your money\n");
			System.out.println("\tA. Checking");
			System.out.println("\tB. Savings");
			System.out.println("\tC. Credit\n");
			
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
					System.out.println("not an option please try again");
			}
		}while(!valid);
	}
	
	/**
	 *  Ask the user for how much money they want to deposit into the previously selected account
	 */
	private static void askDepositMoney(){
		boolean valid = false;
		do{
			System.out.println("How much money do you want to deposit?");
			
			double input = Double.parseDouble(scnr.nextLine());
			if(selectedAccount.deposit(input)){
				valid = true;
				menu = "customerMenu";
				
				//Message to user
				System.out.println("Successfully deposited $" + String.format("%.2f", input) +
					" to " + selectedAccount.getClass().getName());
				
				//Log message
				logMessages.add(user.getFullName() + " deposited $" + String.format("%.2f", input) +
					" on " + selectedAccount.getClass().getName() + "-" + selectedAccount.number + ". New balance: 4" +
					String.format("%.2f", selectedAccount.balance));
			}else{
				System.out.println("too many funds try again");
			}
		}while (!valid);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Withdraw
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Asks user to select an account to withdraw from either checking or savings since user cannot
	 * withdraw from credit
	 */
	private static void withdraw(){
		boolean valid = false;
		do{
			System.out.println("Choose account to withdraw money from\n");
			System.out.println("\tA. Checking");
			System.out.println("\tB. Savings\n");
			
			switch (scnr.nextLine()){
				case "A":
					selectedAccount = user.getChecking();
					menu = "askWithdrawMoney";
					valid = true;
					break;
				case "B":
					selectedAccount = user.getSavings();
					menu = "askWithdrawMoney";
					valid = true;
					break;
				default:
					System.out.println("not a valid option please try again");
					break;
			}
			
		}while(!valid);
	}
	
	/**
	 * Ask the user for how much money they want to withdraw
	 */
	private static void askWithdrawMoney(){
		boolean valid = false;
		do{
			System.out.println("How much money do you want to withdraw?");
			
			double input = Double.parseDouble(scnr.nextLine());
			if(selectedAccount.withdraw(input)){
				valid = true;
				menu = "customerMenu";
				
				//User message
				System.out.println("Successfully withdrew $" + String.format("%.2f", input) +
					" from " + selectedAccount.getClass().getName());
				
				//Log message
				logMessages.add(user.getFullName() + " withdrew $" + String.format("%.2f", input) +
					" from " + selectedAccount.getClass().getName() + "-" + selectedAccount.number +
					". New balance: 4" + String.format("%.2f", selectedAccount.balance));
				continue;
			}
			System.out.println("not enough funds to withdraw please try again");
		}while (!valid);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Transfer
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Asks the user what account the user wants to use to transfer to another account
	 */
	private static void transferMoney(){
		boolean valid;
		do{
			System.out.println("Choose account to transfer money from\n");
			System.out.println("\tA. Checking");
			System.out.println("\tB. Savings\n");
			
			//Check if input is valid
			switch (scnr.nextLine()){
				case "A":
					selectedAccount = user.getChecking();
					menu = "checkingAskDestAccount";
					valid = true;
					break;
				case "B":
					selectedAccount = user.getSavings();
					menu = "savingsAskDestAccount";
					valid = true;
					break;
				default:
					System.out.println("not a valid option please try again");
					valid = false;
					break;
			}
		}while(!valid);
	}
	
	/**
	 * if user chose checking as the withdrawing account it asks for either savings or credit to deposit
	 * money into
	 */
	private static void checkingAskDestAccount(){
		boolean valid;
		do{
			System.out.println("What account do you want to transfer to?\n");
			System.out.println("\t A. Savings");
			System.out.println("\t B. Credit\n");
			
			switch (scnr.nextLine()){
				case "A":
					destAccount = user.getSavings();
					menu = "askTransferAmount";
					valid = true;
					break;
				case "B":
					destAccount = user.getCredit();
					menu = "askTransferAmount";
					valid = true;
					break;
				default:
					System.out.println("not an option please try again");
					valid = false;
			}
		}while(!valid);
	}
	
	/**
	 * if user chose savings as the withdrawing account it ask for either checkings or credit to deposit
	 * money into
	 */
	private static void savingsAskDestAccount(){
		boolean valid;
		do{
			System.out.println("What account do you want to transfer to?\n");
			System.out.println("\tA. Checking");
			System.out.println("\tB. Credit\n");
			
			switch (scnr.nextLine()){
				case "A":
					destAccount = user.getChecking();
					menu = "askTransferAmount";
					valid = true;
					break;
				case "B":
					destAccount = user.getCredit();
					menu = "askTransferAmount";
					valid = true;
					break;
				default:
					System.out.println("not an option please try again");
					valid = false;
			}
		}while(!valid);
	}
	
	/**
	 * For for the amount the user wants to send from the selected account to the destination account
	 */
	private static void askTransferAmount(){
		boolean valid = false;
		do{
			System.out.println("How much money do you want to transfer?");
			double input = Double.parseDouble(scnr.nextLine());
			if(user.transfer(selectedAccount, destAccount, input)){
				valid = true;
				menu = "customerMenu";
				
				//User message
				System.out.println("Successfully transferred $" + String.format("%.2f", input) +
					" from " + selectedAccount.getClass().getName() + " to " +
					destAccount.getClass().getName());
				
				//Log message
				logMessages.add(user.getFullName() + " transferred $" + String.format("%.2f", input) +
					" from " + selectedAccount.getClass().getName() + "-" + selectedAccount.number +
					" to " + destAccount.getClass().getName() + "-" + destAccount.number +
					". " + selectedAccount.getClass().getName() + " balance: $" +
					String.format("%.2f", selectedAccount.balance) + ". " +
					destAccount.getClass().getName() + "-" + destAccount.number + " balance: $" +
					String.format("%.2f", destAccount.balance));
			}else{
				System.out.println("Not a valid amount please try again.");
			}
		}while(!valid);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Send Money
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * After the user says they want to send money this menu asks the signed in customer for the
	 * person they want to send money to and what the amount of money will be
	 */
	private static void sendMoney(){
		boolean valid = false;
		do{
			System.out.println("Who would you like to send money to?");
			
			String name = scnr.nextLine();
			if(!namesToCustomers.keySet().contains(name)){
				System.out.println("name not found please try again");
				continue;
			}
			
			// get customer from name
			Customer customer = namesToCustomers.get(name);
			System.out.println("How much money do you want to send?");
			double amount = Double.parseDouble(scnr.nextLine());
			if(!user.paySomeone(customer, amount)){
				continue;
			}
			
			menu = "customerMenu";
			valid = true;
			
			//User message
			System.out.println("Successfully transferred $" + String.format("%.2f", amount) +
				" to " + selectedAccount.getCustomer().getFullName());
			
			//Log message
			logMessages.add(user.getFullName() + " transferred $" + String.format("%.2f", amount) +
				" to " + customer.getFullName() + ". Checking-" + user.getChecking().getNumber() + " balance: $" +
				String.format("%.2f", user.getChecking().getBalance())
			);
			
		}while(!valid);
	}
}
