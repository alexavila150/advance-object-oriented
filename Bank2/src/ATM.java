import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class ATM {
	private HashSet<Checking> checkings;
	private Checking currAccount;
	private Checking selectedAccount;
	private String selectedOption;
	private Scanner scanner;
	private FileWriter transactionsFile;
	private boolean running = true;
	
	/****************************************************************************************************************
	 *                                      Constructors
	 * **************************************************************************************************************/
	
	public ATM(){}
	
	public ATM(HashSet<Checking> checkings){
		this.checkings = checkings;
		
		//Prepare file
		try {
			transactionsFile = new FileWriter("transactions.txt");
			transactionsFile.write("Disney bank transaction logs!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Main page Disneybank
		System.out.println("Welcome to Disneybank!\n" +
			"Bank in which if you can save it, we can protect it!");
		
		//Ask for account number until it is valid
		boolean validAccount = false;
		int accountNumber = 0;
		scanner = new Scanner(System.in);
		do{
			System.out.print("\n\tEnter your account number to sign in: ");
			try{
				accountNumber = Integer.parseInt(scanner.nextLine());
			}catch (NumberFormatException e){
				System.out.println("\nOnly integers accepted, please try again");
				continue;
			}
			
			System.out.println();
			validAccount = setCurrAccount(accountNumber);
		}while(!validAccount);
		
	}
	
	public ATM(HashSet<Checking> checkings, Checking currAccount, Checking selectedAccount){
		this.checkings = checkings;
		this.currAccount = currAccount;
		this.selectedAccount = selectedAccount;
	}
	
	/****************************************************************************************************************
	 *                                      Getters
	 * **************************************************************************************************************/
	
	public HashSet<Checking> getCheckings(){
		return checkings;
	}
	
	public Checking getCurrAccount(){
		return currAccount;
	}
	
	public Checking getSelectedAccount() {
		return selectedAccount;
	}
	
	public String getSelectedOption() {
		return selectedOption;
	}
	
	public boolean getRunning() {
		return running;
	}
	
	/****************************************************************************************************************
	 *                                      Setters
	 * **************************************************************************************************************/
	
	public void setCheckings(HashSet<Checking> checkings){
		this.checkings = checkings;
	}
	
	public void setCurrAccount(Checking currAccount){
		this.currAccount = currAccount;
	}
	
	public boolean setCurrAccount(int accountNumber){
		for(Checking checking: checkings){
			if(checking.getAccountNumber() == accountNumber){
				this.currAccount = checking;
				System.out.println("Welcome " + currAccount.getFirstName() + "\n" +
					"How can we help you today?");
				optionsMenu();
				return true;
			}
		}
		System.out.println("We are sorry this account does not exist!");
		return false;
	}
	
	public void setSelectedAccount(Checking selectedAccount) {
		this.selectedAccount = selectedAccount;
	}
	
	public boolean setSelectedAccount(int accountNumber){
		for(Checking checking: checkings){
			if(checking.getAccountNumber() == accountNumber){
				this.selectedAccount = checking;
				System.out.println("You selected " + checking.getFirstName() + " " + checking.getLastName());
				return true;
			}
		}
		System.out.println("We are sorry this account does not exist!");
		return false;
	}
	
	/****************************************************************************************************************
	 *                                          Deposit
	 * **************************************************************************************************************/
	
	public void deposit(){
		boolean validAmount = false;
		do{
			System.out.print("How much money do you want to deposit?: ");
			double amount;
			try{
				amount = Double.parseDouble(scanner.nextLine());
			}catch (NumberFormatException e){
				System.out.println("Only numbers, accepted, try again");
				continue;
			}
			
			if(amount < 0){
				System.out.println("No negative transactions are allowed, try again");
				continue;
			}
			
			currAccount.setStartingBalance(currAccount.getStartingBalance() + amount);
			System.out.format("Deposited successfully!\n" +
				"Your current balance in now: $%.2f", currAccount.getStartingBalance());
			validAmount = true;
			
			//File log
			try {
				transactionsFile.write(currAccount.getFirstName() + " " +
					currAccount.getLastName() + " deposited " +
					amount + "$\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}while(!validAmount);
	}
	
	/****************************************************************************************************************
	 *                                          Withdraw
	 * **************************************************************************************************************/
	
	public void withdraw(){
		boolean validAmount = false;
		do{
			//Ask for amount and correct format
			System.out.print("How much money do you want to withdraw?: ");
			double amount;
			try{
				amount = Double.parseDouble(scanner.nextLine());
			}catch(NumberFormatException e){
				System.out.println("Only numbers accepted, try again");
				continue;
			}
			
			//No negative transactions
			if(amount < 0){
				System.out.println("No negative transactions are allowed, try again");
				continue;
			}
			
			//Cannot withdraw more the current balance
			if(amount > currAccount.getStartingBalance()){
				System.out.println("Not enough funds to withdraw this amount, try again");
				continue;
			}
			
			//Message to user
			currAccount.setStartingBalance(currAccount.getStartingBalance() - amount);
			System.out.format("Withdrew successfully!\n" +
				"Your current balance in now: $%.2f", currAccount.getStartingBalance());
			validAmount = true;
			
			//File log
			try {
				transactionsFile.write(currAccount.getFirstName() + " " +
					currAccount.getLastName() + " withdrew " +
					amount + "$\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}while(!validAmount);
	}
	
	/******************************************************************************************************************
	 *                                      Transfer to account
	 * ***************************************************************************************************************/
	
	public void transferToAccount(){
		//Ask for destination account
		boolean validAccount = false;
		do{
			System.out.print("Enter account number to send money to: ");
			
			int accountNumber;
			try{
				accountNumber = Integer.parseInt(scanner.nextLine());
			}catch (NumberFormatException e){
				System.out.println("Only numbers accepted, try again");
				continue;
			}
			
			//Check if account was successful
			validAccount = setSelectedAccount(accountNumber);
			
		}while(!validAccount);
		
		//Ask for amount;
		boolean validAmount = false;
		do{
			//Get amount of money from user and check if it is valid
			System.out.print("How much money do you want to send?: ");
			double amount;
			try{
				amount = Double.parseDouble(scanner.nextLine());
			}catch(NumberFormatException e){
				System.out.println("Only numbers accepted, try again");
				continue;
			}
			
			// checks if transaction was process successfully
			validAmount = transferToAccount(amount);
		}while(!validAmount);
	}
	
	public boolean transferToAccount(double amount){
		//No negative transactions
		if(amount < 0){
			System.out.println("No negative transactions allowed");
			return false;
		}
		
		//Not enough Funds
		if(amount > currAccount.getStartingBalance()){
			System.out.println("Not enough funds to process transaction");
			return false;
		}
		
		//Make transfer
		currAccount.setStartingBalance(currAccount.getStartingBalance() - amount);
		selectedAccount.setStartingBalance(selectedAccount.getStartingBalance() + amount);
		System.out.format("You transferred " + amount + "$ to " + selectedAccount.getFirstName());
		
		//File log
		try {
			transactionsFile.write(currAccount.getFirstName() + " " +
				currAccount.getLastName() + " transferred " + amount + "$ to " +
				selectedAccount.getFirstName() + " " + selectedAccount.getLastName() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/****************************************************************************************************************
	 *                                         Transfer from account
	 * **************************************************************************************************************/
	
	public void transferFromAccount(){
		//Ask for incoming account
		boolean validAccount = false;
		do{
			System.out.print("Enter account number to take money from: ");
			
			int accountNumber;
			try{
				accountNumber = Integer.parseInt(scanner.nextLine());
			}catch (NumberFormatException e){
				System.out.println("Only numbers accepted, try again");
				continue;
			}
			
			//Check if account was successfull
			validAccount = setSelectedAccount(accountNumber);
			
		}while(!validAccount);
		
		//Ask for amount;
		boolean validAmount = false;
		do{
			//Get amount of money from user and check if it is valid
			System.out.print("How much money do you want to take?: ");
			double amount;
			try{
				amount = Double.parseDouble(scanner.nextLine());
			}catch(NumberFormatException e){
				System.out.println("Only numbers accepted, try again");
				continue;
			}
			
			// checks if transaction was process successfully
			validAmount = transferFromAccount(amount);
		}while(!validAmount);
	}
	
	public boolean transferFromAccount(double amount){
		//No negative transactions
		if(amount < 0){
			System.out.println("No negative transactions allowed");
			return false;
		}
		
		//Not enough funds
		if(amount > selectedAccount.getStartingBalance()){
			System.out.println("Not enough funds to process transaction");
			return false;
		}
		
		//Make transfer
		currAccount.setStartingBalance(currAccount.getStartingBalance() + amount);
		selectedAccount.setStartingBalance(selectedAccount.getStartingBalance() - amount);
		
		System.out.println(selectedAccount.getFirstName() + " transferred " + amount + "$ to you");
		
		//File log
		try {
			transactionsFile.write(selectedAccount.getFirstName() + " " +
				selectedAccount.getLastName() + " transferred " + amount + "$ to " +
				currAccount.getFirstName() + " " + currAccount.getLastName() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/****************************************************************************************************************
	 *                                          Other Options
	 * **************************************************************************************************************/
	
	public void showFunds(){
		System.out.format("You currently have %.2f$ in your account", currAccount.getStartingBalance());
	}
	
	public void switchAccount(){
		boolean validAccount = false;
		do{
			System.out.print("Enter account number to sign in: ");
			int accountNumber;
			try {
				accountNumber = Integer.parseInt(scanner.nextLine());
			}catch (NumberFormatException e){
				System.out.println("Only integers allowed, please try again");
				continue;
			}
			
			//Look for account in checkings
			for(Checking checking: checkings){
				if(checking.getAccountNumber() == accountNumber){
					currAccount = checking;
					validAccount = true;
				}
			}
			
		}while(!validAccount);
		
		System.out.println("\nWelcome " + currAccount.getFirstName() + "\n" +
			"How can we help you today?");
	}
	
	public void exit(){
		System.out.println("Thank you for coming!!");
		try {
			transactionsFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	/****************************************************************************************************************
	 *                                      Menu and listener
	 * **************************************************************************************************************/
	
	public void optionsMenu(){
		System.out.println("\n" +
			"\ta) Deposit money\n" +
			"\tb) Withdraw money\n" +
			"\tc) Transfer to another account\n" +
			"\td) Transfer to this account from another account\n" +
			"\te) Show funds\n" +
			"\tf) Switch account\n" +
			"\tg) Exit\n");
		
		listener();
	}
	
	public void listener(){
		selectedOption = scanner.nextLine();
		switch (selectedOption) {
			case "a":
				deposit();
				break;
			case "b":
				withdraw();
				break;
			case "c":
				transferToAccount();
				break;
			case "d":
				transferFromAccount();
				break;
			case "e":
				showFunds();
				break;
			case "f":
				switchAccount();
				break;
			case "g":
				exit();
				break;
			default:
				System.out.println("Not an option please try again");
		}
	}
	
	/****************************************************************************************************************
	 *                                          Override
	 * **************************************************************************************************************/
	
	@Override
	public String toString() {
		return "Current Account:\n " +
			"" + currAccount + "\n" +
			"Selected Account\n" +
			selectedAccount + "\n";
	}
}