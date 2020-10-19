import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Alex Avila
 * @version 1.0
 * @since 9/28/20
 * <p>
 * Main class which handles all the messages and menu UI. It reads the file with information and
 * creates the update information file as well as the transaction log file
 */
public class RunBank {
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Attributes
	 ----------------------------------------------------------------------------------------------------------------*/
	//Stores information about the customers their accounts and transactions
	private static IBankDB bankDB;
	
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
		//Database that will include all information about the bank
		bankDB = new BankDB();
		
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
				String[] columnTitles = fileScnr.nextLine().split(",");
				
				//Makes a respective index for each of the titles
				HashMap<String, Integer> titleToExpectedIndex = new HashMap<>();
				titleToExpectedIndex.put("First Name", 0);
				titleToExpectedIndex.put("Last Name", 1);
				titleToExpectedIndex.put("Date of Birth", 2);
				titleToExpectedIndex.put("Address", 3);
				titleToExpectedIndex.put("Phone Number", 4);
				titleToExpectedIndex.put("Identification Number", 5);
				titleToExpectedIndex.put("Checking Account Number", 6);
				titleToExpectedIndex.put("Checking Starting Balance", 7);
				titleToExpectedIndex.put("Savings Account Number", 8);
				titleToExpectedIndex.put("Savings Starting Balance", 9);
				titleToExpectedIndex.put("Credit Account Number", 10);
				titleToExpectedIndex.put("Credit Starting Balance", 11);
				titleToExpectedIndex.put("Credit Max", 12);
				
				int[] indexMapping = new int[13];
				for(int i = 0; i < 13; i++){
					indexMapping[titleToExpectedIndex.get(columnTitles[i])] = i;
				}
				
				//For every line make a new Customer Account instance
				while(fileScnr.hasNextLine()){
					String line = fileScnr.nextLine();
					line = line.replace(", "," ");
					String[] attributes = line.split(",");
					
					// create customer from csv information
					Customer customer = new Customer(
						attributes[indexMapping[0]],                            //firstName
						attributes[indexMapping[1]],                            //lastName
						attributes[indexMapping[2]],                            //dob
						attributes[indexMapping[3]],                            //address
						attributes[indexMapping[4]],                            //phone
						attributes[indexMapping[5]],                            //id
						Integer.parseInt(attributes[indexMapping[6]]),          //checking account number
						Double.parseDouble(attributes[indexMapping[7]]),        //checking balance
						Integer.parseInt(attributes[indexMapping[8]]),          //savings account number
						Double.parseDouble(attributes[indexMapping[9]]),        //savings balance
						Integer.parseInt(attributes[indexMapping[10]]),         //credit account number
						Double.parseDouble(attributes[indexMapping[11]]),       //credit balance
						Integer.parseInt(attributes[indexMapping[12]])          //max credit
					);
					
					//Save Customer into the database
					bankDB.addCustomer(customer);
				}
				//File was process successfully
				valid = true;
				
			} catch (FileNotFoundException e) {
				System.out.println("file not found please try again");
			}
		}while(!valid);
		
		// ask user to sign
		System.out.println("Successfully entered data");
		
		//This is the menu handler depending on the menu variable the user will be ask different things
		//and the menu will change as the user chooses specific options.
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
				case "createBankStatement":
					createBankStatement();
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
				case "createNewCustomer":
					createNewCustomer();
					break;
				case "transactionReader":
					transactionReader();
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
				"Checking Starting Balance,Savings Starting Balance,Credit Starting Balance,Max Credit\n");
			
			// Write all customers
			for(Customer customer : bankDB.getCustomers()){
				myWriter.write(customer.toCsvLine() + "\n");
			}
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred when trying to create the file.");
		}
		
		//Write log file
		try {
			FileWriter myWriter = new FileWriter("transactions.txt");
			
			//First line of the output file
			myWriter.write("Disney Bank transaction file\n");
			
			//gets transaction from iterable
			
			for(Transaction transaction : bankDB.getTransactions()){
				myWriter.write(transaction.getTransaction() + "\n");
			}
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}
	
	/**
	 * Asks the user if they want to sign in as a Customer or as a Manager
	 */
	private static void userTypeMenu(){
		System.out.println("Sign in as:\n");
		System.out.println("\tA) Manager");
		System.out.println("\tB) Customer");
		System.out.println("\tC) Transaction Reader");
		System.out.println("\tD) Create new customer");
		System.out.println("\tE) Exit\n");
		
		switch (scnr.nextLine()){
			case "A":
				System.out.println("You signed in as a manager");
				menu = "manager";
				return;
			case "B":
				menu = "customerSignIn";
				return;
			case "C":
				menu = "transactionReader";
				return;
			case "D":
				menu = "createNewCustomer";
				return;
			case "E":
				menu = "done";
				return;
			default:
				System.out.println("not an option please try again");
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Manager
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Ask user if he wants to look for a customer by either using their name or one of their account
	 * number
	 */
	private static void managerMenu(){
		// Display Menu for the the manager
		System.out.println("What would you like to do?\n");
		System.out.println("\tA. Inquire account by name");
		System.out.println("\tB. Inquire account by type/number");
		System.out.println("\tC. Generate Bank Statement");
		System.out.println("\tD. Sign in page\n");
		String input = scnr.nextLine();
		
		switch (input){
			case "A":
				menu = "askForAccountName";
				break;
			case "B":
				menu = "askForAccountType";
				break;
			case "C":
				menu = "createBankStatement";
				break;
			case "D":
				menu = "userType";
				break;
			default:
				System.out.println("not an option please try again");
		}
	}
	
	/**
	 * Ask for the name of the customer that the manager wants to inquire
	 */
	private static void askForAccountName(){
		System.out.println("Who's account would you like to inquire about?");
		String input = scnr.nextLine();
		
		//Check if name is valid
		if(!bankDB.containsCustomer(input)){
			System.out.println("Customer does not exist please try again.");
			return;
		}
		
		//find costumer by name
		Customer customer = bankDB.getCustomer(input);
		
		//Inquire customer's information
		System.out.println(customer);
		menu = "manager";
	}
	
	/**
	 * Ask the manager what the account number is if they selected a checking account to look for
	 */
	private static void askForAccountType(){
		System.out.println("What type of account?");
		String input = scnr.nextLine();
		
		//Check account type
		switch (input){
			case "Checking":
				menu = "askForChecking";
				break;
			case "Savings":
				menu = "askForSavings";
				break;
			case "Credit":
				menu = "askForCredit";
				break;
			default:
				System.out.println("not valid type please try again");
		}
	}
	
	/**
	 * Ask the manager what the account number is if they selected a savings account to look for
	 */
	private static void askForChecking(){
		System.out.println("What is the account number?");
		int input = Integer.parseInt(scnr.nextLine());
		
		//check if input is valid
		if(!bankDB.containsChecking(input)){
			System.out.println("not a valid account number please try again");
			return;
		}
		
		//Get account
		Checking checking = (Checking) bankDB.getAccount(input);
		Customer customer = checking.customer;
		
		//Inquire customer's information
		System.out.println(customer);
		menu = "manager";
	}
	
	/**
	 * Ask the manager what the account number is if they selected a savings account to look for
	 */
	private static void askForSavings(){
		System.out.println("What is the account number?");
		int input = Integer.parseInt(scnr.nextLine());
		
		//check if input is valid
		if(!bankDB.containsSavings(input)){
			System.out.println("not a valid account number please try again");
			return;
		}
		
		//get account
		Savings savings = (Savings) bankDB.getAccount(input);
		System.out.println("savings: " + savings.getBalance());
		Customer customer = savings.getCustomer();
		
		//Inquire customer's information
		System.out.println(customer);
		menu = "manager";
	}
	
	/**
	 * Ask the manager what the account number is if they selected a credit account to look for
	 */
	private static void askForCredit(){
		System.out.println("What is the account number?");
		int input = Integer.parseInt(scnr.nextLine());
		
		//check if input is valid
		if(!bankDB.containsCredit(input)){
			System.out.println("not a valid account number please try again");
			return;
		}
		
		//Get account
		Credit credit = (Credit) bankDB.getAccount(input);
		Customer customer = credit.getCustomer();
		
		//Inquire customer's information
		System.out.println(customer);
		menu = "manager";
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            BankStatement
	 ----------------------------------------------------------------------------------------------------------------*/
	
	private static void createBankStatement(){
		//ask for name
		System.out.println("Whose bank statement do you want to create?");
		String name = scnr.nextLine();
		if(!bankDB.containsCustomer(name)){
			System.out.println("Customer does not exist please try again");
			return;
		}
		
		bankDB.getBankStatement(bankDB.getCustomer(name)).createStatement();
		menu = "manager";
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Customer
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * If user want to sign in as a customer then this menu will ask the user to enter their name to
	 * sign in
	 */
	private static void customerSignIn(){
		System.out.println("What is your name?");
		String input = scnr.nextLine();
		
		//check if name is in the information
		if(!bankDB.containsCustomer(input)){
			System.out.println("Name not found. Please try again");
			return;
		}
		
		//Print customer's information
		user = bankDB.getCustomer(input);
		System.out.println("Welcome " + user.getFullName());
		System.out.println(user);
		menu = "customerMenu";
	}
	
	/**
	 * This is the main menu for the user with the main choices for him to do
	 */
	private static void customerMenu(){
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
				
				String checkingString = "";
				if(user.getChecking() != null){
					checkingString = "Checking: $" + String.format("%.2f", user.getChecking().getBalance());
				}
				
				String savingsString = "";
				if(user.getSavings() != null){
					savingsString = "Savings: $" + String.format("%.2f", user.getSavings().getBalance());
				}
				
				String creditString = "";
				if(user.getCredit() != null){
					creditString = "Credit: $" + String.format("%.2f", user.getCredit().getBalance());
				}
				
				// Log message
				bankDB.addTransaction(new Transaction(user.getFullName() + " made a balance inquire." + checkingString + " " +
						savingsString + " " + creditString, user));
				break;
			case "B":
				menu = "deposit";
				break;
			case "C":
				menu = "withdraw";
				break;
			case "D":
				menu = "transferMoney";
				break;
			case "E":
				menu = "sendMoney";
				break;
			case "F":
				menu = "userType";
				break;
			default:
				System.out.println("Not an option please try again");
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Deposit
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 *  Ask the user to choose for any of their three account to deposit money into
	 */
	private static void deposit(){
		System.out.println("Choose account to deposit your money\n");
		System.out.println("\tA. Checking");
		System.out.println("\tB. Savings");
		System.out.println("\tC. Credit\n");
		
		switch (scnr.nextLine()){
			case "A":
				selectedAccount = user.getChecking();
				menu = "askDepositMoney";
				break;
			case "B":
				selectedAccount = user.getSavings();
				menu = "askDepositMoney";
				break;
			case "C":
				selectedAccount = user.getCredit();
				menu = "askDepositMoney";
				break;
			default:
				System.out.println("not an option please try again");
		}
	}
	
	/**
	 *  Ask the user for how much money they want to deposit into the previously selected account
	 */
	private static void askDepositMoney(){
		System.out.println("How much money do you want to deposit?");
		try{
			double input = Double.parseDouble(scnr.nextLine());
			selectedAccount.deposit(input);
			menu = "customerMenu";
			
			//Message to user
			System.out.println("Successfully deposited $" + String.format("%.2f", input) +
				" to " + selectedAccount.getClass().getName());
			
			//Log message
			bankDB.addTransaction(
				new Transaction(
					user.getFullName() + " deposited $" + String.format("%.2f", input) +
					" on " + selectedAccount.getClass().getName() + "-" + selectedAccount.number + ". New balance: 4" +
					String.format("%.2f", selectedAccount.balance),
					user
				)
			);
		}catch(NumberFormatException e){
			System.out.println("Please insert a number");
		}catch (NullPointerException e){
			System.out.println("Your account does not exist please try again");
			menu = "deposit";
		}catch (RuntimeException e){
			System.out.println(e.getMessage() + " please try again.");
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Withdraw
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Asks user to select an account to withdraw from either checking or savings since user cannot
	 * withdraw from credit
	 */
	private static void withdraw(){
		System.out.println("Choose account to withdraw money from\n");
		System.out.println("\tA. Checking");
		System.out.println("\tB. Savings\n");
		
		switch (scnr.nextLine()){
			case "A":
				selectedAccount = user.getChecking();
				menu = "askWithdrawMoney";
				break;
			case "B":
				selectedAccount = user.getSavings();
				menu = "askWithdrawMoney";
				break;
			default:
				System.out.println("not a valid option please try again");
				break;
		}
	}
	
	/**
	 * Ask the user for how much money they want to withdraw
	 */
	private static void askWithdrawMoney(){
		try{
			System.out.println("How much money do you want to withdraw?");
			double input = Double.parseDouble(scnr.nextLine());
			selectedAccount.withdraw(input);
			menu = "customerMenu";
			
			//User message
			System.out.println("Successfully withdrew $" + String.format("%.2f", input) +
				" from " + selectedAccount.getClass().getName());
			
			//Log message
			bankDB.addTransaction(
				new Transaction(user.getFullName() + " withdrew $" + String.format("%.2f", input) +
					" from " + selectedAccount.getClass().getName() + "-" + selectedAccount.number +
					". New balance: 4" + String.format("%.2f", selectedAccount.balance),
					user)
			);
			
		}catch(NumberFormatException e){
			System.out.println("Please insert a number");
		}catch (NullPointerException e){
			System.out.println("Your account does not exist please try again");
			menu = "withdraw";
		}catch (RuntimeException e){
			System.out.println(e.getMessage() + " please try again");
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Transfer
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Asks the user what account the user wants to use to transfer to another account
	 */
	private static void transferMoney(){
		System.out.println("Choose account to transfer money from\n");
		System.out.println("\tA. Checking");
		System.out.println("\tB. Savings\n");
		
		//Check if input is valid
		switch (scnr.nextLine()){
			case "A":
				selectedAccount = user.getChecking();
				menu = "checkingAskDestAccount";
				break;
			case "B":
				selectedAccount = user.getSavings();
				menu = "savingsAskDestAccount";
				break;
			default:
				System.out.println("not a valid option please try again");
				break;
		}
	}
	
	/**
	 * if user chose checking as the withdrawing account it asks for either savings or credit to deposit
	 * money into
	 */
	private static void checkingAskDestAccount(){
		System.out.println("What account do you want to transfer to?\n");
		System.out.println("\t A. Savings");
		System.out.println("\t B. Credit\n");
		
		switch (scnr.nextLine()){
			case "A":
				destAccount = user.getSavings();
				menu = "askTransferAmount";
				break;
			case "B":
				destAccount = user.getCredit();
				menu = "askTransferAmount";
				break;
			default:
				System.out.println("not an option please try again");
		}
	}
	
	/**
	 * if user chose savings as the withdrawing account it ask for either checkings or credit to deposit
	 * money into
	 */
	private static void savingsAskDestAccount(){
		System.out.println("What account do you want to transfer to?\n");
		System.out.println("\tA. Checking");
		System.out.println("\tB. Credit\n");
		
		switch (scnr.nextLine()){
			case "A":
				destAccount = user.getChecking();
				menu = "askTransferAmount";
				break;
			case "B":
				destAccount = user.getCredit();
				menu = "askTransferAmount";
				break;
			default:
				System.out.println("not an option please try again");
		}
	}
	
	/**
	 * For for the amount the user wants to send from the selected account to the destination account
	 */
	private static void askTransferAmount(){
		try{
			System.out.println("How much money do you want to transfer?");
			double input = Double.parseDouble(scnr.nextLine());
			user.transfer(selectedAccount, destAccount, input);
			menu = "customerMenu";
			
			//User message
			System.out.println("Successfully transferred $" + String.format("%.2f", input) +
				" from " + selectedAccount.getClass().getName() + " to " +
				destAccount.getClass().getName());
			
			//Log message
			bankDB.addTransaction(
				new Transaction(user.getFullName() + " transferred $" + String.format("%.2f", input) +
					" from " + selectedAccount.getClass().getName() + "-" + selectedAccount.number +
					" to " + destAccount.getClass().getName() + "-" + destAccount.number +
					". " + selectedAccount.getClass().getName() + " balance: $" +
					String.format("%.2f", selectedAccount.balance) + ". " +
					destAccount.getClass().getName() + "-" + destAccount.number + " balance: $" +
					String.format("%.2f", destAccount.balance),
					user
				)
			);
		}catch(NumberFormatException e){
			System.out.println("Please insert a number");
		}catch(NullPointerException e){
			System.out.println("Your account does not exist please try again");
			menu = "customerMenu";
		}catch(RuntimeException e){
			System.out.println(e.getMessage() + " please try again");
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Send Money
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * After the user says they want to send money this menu asks the signed in customer for the
	 * person they want to send money to and what the amount of money will be
	 */
	private static void sendMoney(){
		System.out.println("Who would you like to send money to?");
		
		String name = scnr.nextLine();
		if(!bankDB.containsCustomer(name) || name.equals(user.getFullName())){
			System.out.println("name not found please try again");
			return;
		}
		
		// get customer from name
		Customer customer = bankDB.getCustomer(name);
		System.out.println("How much money do you want to send?");
		try{
			double amount = Double.parseDouble(scnr.nextLine());
			user.paySomeone(customer, amount);
			menu = "customerMenu";
			
			//User message
			System.out.println("Successfully transferred $" + String.format("%.2f", amount) +
				" to " + customer.getFullName());
			
			//Log message
			bankDB.addTransaction(
				new Transaction(user.getFullName() + " transferred $" + String.format("%.2f", amount) +
					" to " + customer.getFullName() + ". Checking-" + user.getChecking().getNumber() + " balance: $" +
					String.format("%.2f", user.getChecking().getBalance()),
					user
				)
			);
		}catch(NumberFormatException e){
			System.out.println("Please insert a valid number");
		}catch(NullPointerException e){
			System.out.println("Customer does not have a Checking account");
			menu = "customerMenu";
		}catch(RuntimeException e){
			System.out.println(e.getMessage() + " please try again");
			menu = "customerMenu";
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Create New Customer
	 ----------------------------------------------------------------------------------------------------------------*/
	
	private static void createNewCustomer(){
		//Read name
		System.out.println("Enter your information below");
		System.out.print("First Name: ");
		String firstName = scnr.nextLine();
		System.out.print("Last Name: ");
		String lastName = scnr.nextLine();
		
		
		//If name already exist then come back
		if(bankDB.containsCustomer(firstName + " " + lastName)){
			System.out.println("Name already exist please try again.");
			return;
		}
		
		System.out.print("Date of Birth: ");
		String dob = scnr.nextLine();
		
		System.out.print("Address: ");
		String address = scnr.nextLine();
		
		System.out.print("Phone Number: ");
		String phone = scnr.nextLine();
		
		//Create ID
		int id = bankDB.getCustomers().size() + 1;
		
		Savings savings = new Savings(id + 1999, 0);
		System.out.println("Congrats " + firstName + " " + lastName + " your account was successfully created\n" +
			"We have created a Savings account for you with the id " + (id + 1999));
		
		Checking checking = null;
		boolean validInput = false;
		while(!validInput){
			System.out.println("Do you want to create a Checking account?\n");
			System.out.println("\tA) yes");
			System.out.println("\tB) no\n");
			
			String input = scnr.nextLine();
			switch (input){
				case "A":
					checking = new Checking(999 + id, 0);
					validInput = true;
					break;
				case "B":
					validInput = true;
					break;
				default:
					System.out.println("not a valid option please try again.");
			}
		}
		
		Credit credit = null;
		validInput = false;
		while(!validInput){
			System.out.println("Do you want to create a Credit account?\n");
			System.out.println("\tA) yes");
			System.out.println("\tB) no\n");
			
			String input = scnr.nextLine();
			switch (input){
				case "A":
					credit = new Credit(2999 + id, 0, 5000); //TODO: make max random
					validInput = true;
					break;
				case "B":
					validInput = true;
					break;
				default:
					System.out.println("not a valid option please try again.");
			}
		}
		
		Customer customer = new Customer(
			firstName,
			lastName,
			dob,
			address,
			phone,
			Integer.toString(id),
			checking,
			savings,
			credit
		);
		
		bankDB.addCustomer(customer);
		menu = "userType";
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Transaction File
	 ----------------------------------------------------------------------------------------------------------------*/
	
	private static void transactionReader(){
		System.out.print("Enter file name: ");
		File transactionFile = new File(System.getProperty("user.dir") + "\\" + scnr.nextLine());
		Scanner fileScnr;
		try{
			fileScnr = new Scanner(transactionFile);
		}catch (FileNotFoundException e){
			System.out.println("file not found please try again");
			return;
		}
		
		fileScnr.nextLine();
		while(fileScnr.hasNextLine()){
			String[] actions = fileScnr.nextLine().split(",");
			
			switch (actions[3]){
				case "pays":
					pays(actions);
					break;
				case "transfers":
					transfers(actions);
					break;
				case "inquires":
					inquires(actions);
					break;
				case "withdraws":
					withdraws(actions);
					break;
				case "deposits":
					deposits(actions);
					break;
			}
		}
		
		menu = "userType";
	}
	
	private static void pays(String[] actions){
		
		try{
			//Find source user and account
			Customer srcCustomer = bankDB.getCustomer(actions[0] + " " + actions[1]);
			Account srcAccount = srcCustomer.getAccountByType(actions[2]);
			
			//Find dest user and account
			Customer destCustomer = bankDB.getCustomer(actions[4] + " " + actions[5]);
			Account destAccount = destCustomer.getAccountByType(actions[6]);
			
			srcAccount.transfer(destAccount, Double.parseDouble(actions[7]));
			//Log message
			bankDB.addTransaction(new Transaction(srcCustomer.getFullName() + " transferred $" +
				String.format("%.2f", Double.parseDouble(actions[7])) +
				" to " + destCustomer.getFullName() + ". Checking-" +
				srcCustomer.getChecking().getNumber() + " balance: $" +
				String.format("%.2f", srcCustomer.getChecking().getBalance()), srcCustomer)
			);
		}catch(RuntimeException e){
			String log = e.getMessage() + " in transaction: " + String.join(", ", actions);
			bankDB.addTransaction(new Transaction(log, null));
		}
	}
	
	private static void transfers(String[] actions){
		try{
			//Find source user and account
			Customer srcCustomer = bankDB.getCustomer(actions[0] + " " + actions[1]);
			Account srcAccount = srcCustomer.getAccountByType(actions[2]);
			
			//Find dest user and account
			Customer destCustomer = bankDB.getCustomer(actions[4] + " " + actions[5]);
			Account destAccount = destCustomer.getAccountByType(actions[6]);
			srcAccount.transfer(destAccount, Double.parseDouble(actions[7]));
			
			//Log message
			bankDB.addTransaction(new Transaction(srcCustomer.getFullName() + " transferred $" +
				String.format("%.2f", Double.parseDouble(actions[7])) +
				" from " + srcAccount.getClass().getName() + "-" + srcAccount.number +
				" to " + destAccount.getClass().getName() + "-" + destAccount.number +
				". " + srcAccount.getClass().getName() + " balance: $" +
				String.format("%.2f", srcAccount.balance) + ". " +
				destAccount.getClass().getName() + "-" + destAccount.number + " balance: $" +
				String.format("%.2f", destAccount.balance), srcCustomer)
			);
		}catch (NumberFormatException e){
			System.out.println("Please enter a number");
		} catch (RuntimeException e){
			String log = e.getMessage() + " in transaction: " + String.join(", ", actions);
			bankDB.addTransaction(new Transaction(log, null));
		}
		
	}
	
	private static void inquires(String[] actions){
		//Find customer
		Customer customer = bankDB.getCustomer(actions[0] + " " + actions[1]);
		
		// Log message
		bankDB.addTransaction(new Transaction(customer.getFullName() + " made a balance inquire. Checking: $" +
			String.format("%.2f", customer.getChecking().getBalance()) + " Savings: $" +
			String.format("%.2f", customer.getSavings().getBalance()) + " Credit: $" +
			String.format("%.2f", customer.getCredit().getBalance()),
			customer)
		);
		System.out.println(customer);
	}
	
	private static void withdraws(String[] actions){
		try{
			//Find account and Customer
			Customer customer = bankDB.getCustomer(actions[0] + " " + actions[1]);
			Account account = customer.getAccountByType(actions[2]);
			account.withdraw(Double.parseDouble(actions[7]));
			
			//Log message
			bankDB.addTransaction(new Transaction(customer.getFullName() + " withdrew $" + String.format("%.2f", Double.parseDouble(actions[7])) +
				" from " + account.getClass().getName() + "-" + account.number +
				". New balance: 4" + String.format("%.2f", account.balance),
				customer)
			);
		}catch (NullPointerException e){
			String log = "Failed Transaction: ";
			for(int i = 0; i < actions.length; i++){
				log += actions[i];
			}
			bankDB.addTransaction(new Transaction(log, null));
			e.printStackTrace();
		}catch (RuntimeException e){
			String log = e.getMessage() + " in transaction: " + String.join(", ", actions);
			bankDB.addTransaction(new Transaction(log, user));
		}
	}
	
	private static void deposits(String[] actions){
		try{
			//Get customer and account to deposit
			Customer customer = bankDB.getCustomer(actions[4] + " " + actions[5]);
			Account account = customer.getAccountByType(actions[6]);
			account.deposit(Double.parseDouble(actions[7]));
			
			//Log message
			bankDB.addTransaction(
				new Transaction(customer.getFullName() + " deposited $" +
					String.format("%.2f", Double.parseDouble(actions[7])) +
					" on " + account.getClass().getName() + "-" + account.getNumber() + ". New balance: 4" +
					String.format("%.2f", account.getBalance()),
					customer
				)
			);
		} catch (NullPointerException e){
			String log = e.getMessage() + " in transaction: " + String.join(", ", actions);
			bankDB.addTransaction(new Transaction(log, null));
		} catch (RuntimeException e){
			String log = e.getMessage() + " in transaction: ";
			for(int i = 0; i < actions.length; i++){
				log += actions[i];
			}
			bankDB.addTransaction(new Transaction(log, null));
		}
	}
}
