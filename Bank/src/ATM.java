import java.util.HashSet;
import java.util.Scanner;

public class ATM {
	private HashSet<Checking> checkings;
	private Checking currAccount;
	private Checking selectedAccount;
	private Scanner scanner;
	private String selectedOption;
	
	/****************************************************************************************************************
	 *                                      Constructors
	 * **************************************************************************************************************/
	
	public ATM(){}
	
	public ATM(HashSet<Checking> checkings){
		this.checkings = checkings;
		
		//Main page Disneybank
		System.out.print("Welcome to Disneybank!\n" +
			"Bank in which if you can save it, we can protect it! \n\n" +
			"\tPlease enter your account number to sign in: ");
		
		//Scan account number and then set account as currAccount
		scanner = new Scanner(System.in);
		int accountNumber = Integer.parseInt(scanner.nextLine());
		System.out.println();
		this.setCurrAccount(accountNumber);
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
	
	/****************************************************************************************************************
	 *                                      Setters
	 * **************************************************************************************************************/
	
	public void setCheckings(HashSet<Checking> checkings){
		this.checkings = checkings;
	}
	
	public void setCurrAccount(Checking currAccount){
		this.currAccount = currAccount;
	}
	
	public void setCurrAccount(int accountNumber){
		for(Checking checking: checkings){
			if(checking.getAccountNumber() == accountNumber){
				this.currAccount = checking;
				System.out.println("Welcome " + currAccount.getFirstName() + "\n" +
					"How can we help you today?");
				optionsMenu();
				return;
			}
		}
		System.out.println("We are sorry your account does not exist!");
		System.exit(1);
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
			System.out.println("Deposited successfully!\n" +
				"Your current balance in now: " + currAccount.getStartingBalance());
			validAmount = true;
			
		}while(!validAmount);
	}
	
	/****************************************************************************************************************
	 *                                          Withdraw
	 * **************************************************************************************************************/
	
	public void withdraw(){
		boolean validAmount = false;
		do{
			System.out.print("How much money do you want to withdraw?: ");
			double amount;
			try{
				amount = Double.parseDouble(scanner.nextLine());
			}catch(NumberFormatException e){
				System.out.println("Only numbers accepted, try again");
				continue;
			}
			
			if(amount < 0){
				System.out.println("No negative transactions are allowed, try again");
				continue;
			}
			
			currAccount.setStartingBalance(currAccount.getStartingBalance() - amount);
			System.out.println("Withdrew successful!\n" +
				"Your current balance in now: " + currAccount.getStartingBalance());
			validAmount = true;
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
			
			//Check if account was successfull
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
		
		System.out.println(currAccount.getFirstName() + " pay " + amount + "$ to " + selectedAccount.getFirstName());
		
		return true;
	}
	
	/****************************************************************************************************************
	 *                                         Transfer from account
	 * **************************************************************************************************************/
	
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
		
		System.out.println(selectedAccount.getFirstName() + " pay " + amount + "$ to " + currAccount.getFirstName());
		
		return true;
	}
	
	/****************************************************************************************************************
	 *                                          Other Options
	 * **************************************************************************************************************/
	
	public void showFunds(){
		System.out.println("You currently have " + currAccount.getStartingBalance() + "$ in your account");
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
			
			System.out.println("\nWelcome " + currAccount.getFirstName() + "\n" +
				"How can we help you today?");
			
		}while(!validAccount);
	}
	
	public void exit(){
		System.out.println("Thank you for coming!!");
		System.exit(0);
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
				optionD();
				break;
			case "e":
				showFunds();
				break;
			case "f":
				switchAccount();
				break;
			case "g":
				exit();
			default:
				System.out.println("Not an option please try again");
		}
	}
	
	/*******************************************************************************************************************
	 *                                          Options
	 * ****************************************************************************************************************/
	
	public void optionD(){
		System.out.println("optionD");
	}
	
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
