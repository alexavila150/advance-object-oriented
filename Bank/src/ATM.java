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
	
	/****************************************************************************************************************
	 *                                      Methods
	 * **************************************************************************************************************/
	
	public boolean transferToAccount(double amount){
		//No negative transactions
		if(amount < 0){
			System.out.println("No negative transactions allowed");
			return false;
		}
		
		//Not enough Funds
		if(amount > currAccount.getStartingBalance()){
			System.out.print("Not enough funds to process transaction");
			return false;
		}
		
		//Make transfer
		currAccount.setStartingBalance(currAccount.getStartingBalance() - amount);
		selectedAccount.setStartingBalance(selectedAccount.getStartingBalance() + amount);
		
		return true;
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
		
		return true;
	}
	
	public void listener(){
		selectedOption = scanner.nextLine();
		switch (selectedOption){
			case "a":
				optionA();
				break;
			case "b":
				optionB();
				break;
			case "c":
				optionC();
				break;
			case "d":
				optionD();
				break;
			default:
		}
		
	}
	
	/*******************************************************************************************************************
	 *                                          Options
	 * ****************************************************************************************************************/
	
	public void optionA(){
	
	}
	
	public void optionB(){
	
	}
	
	public void optionC(){
	
	}
	
	public void optionD(){
	
	}
	
	public void optionsMenu(){
		System.out.println("\n" +
			"\ta) Deposit money\n" +
			"\tb) Withdraw money\n" +
			"\tc) Transfer to another account\n" +
			"\td) Transfer to this account from another account\n");
		
		listener();
	}
	
	@Override
	public String toString() {
		return "Current Account:\n " +
			"" + currAccount + "\n" +
			"Selected Account\n" +
			selectedAccount + "\n";
	}
}
