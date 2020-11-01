import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Alex Avila
 * @version 1.0
 * @since 10/10/20
 * <p>
 * Each customer has one of this classes in order to store all the transactions
 * that were made throughout the whole run of the application.
 */
public class BankStatement {
	private Customer customer;
	private double startingBalanceChecking;
	private double endingBalanceChekcing;
	private double startingBalanceSavings;
	private double endingBalanceSavings;
	private double startingBalanceCredit;
	private double endingBalanceCredit;
	private ArrayList<Transaction> transactions;
	
	public BankStatement(){}
	
	public BankStatement(
		Customer customer,
		double startingBalanceChecking,
		double startingBalanceSavings,
		double startingBalanceCredit
	){
		this.customer = customer;
		this.startingBalanceChecking = startingBalanceChecking;
		this.startingBalanceSavings = startingBalanceSavings;
		this.startingBalanceCredit = startingBalanceCredit;
		this.transactions = new ArrayList<>();
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Getters
	 ----------------------------------------------------------------------------------------------------------------*/
	
	public Customer getCustomer() {
		return customer;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public double getEndingBalanceChekcing() {
		return endingBalanceChekcing;
	}
	
	public double getEndingBalanceSavings() {
		return endingBalanceSavings;
	}
	
	public double getStartingBalanceChecking() {
		return startingBalanceChecking;
	}
	
	public double getStartingBalanceSavings() {
		return startingBalanceSavings;
	}
	
	public double getEndingBalanceCredit() {
		return endingBalanceCredit;
	}
	
	public double getStartingBalanceCredit() {
		return startingBalanceCredit;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Setters
	 ----------------------------------------------------------------------------------------------------------------*/
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void setEndingBalanceChekcing(double endingBalanceChekcing) {
		this.endingBalanceChekcing = endingBalanceChekcing;
	}
	
	public void setStartingBalanceChecking(double startingBalanceChecking) {
		this.startingBalanceChecking = startingBalanceChecking;
	}
	
	public void setStartingBalanceSavings(double startingBalanceSavings) {
		this.startingBalanceSavings = startingBalanceSavings;
	}
	
	public void setEndingBalanceSavings(double endingBalanceSavings) {
		this.endingBalanceSavings = endingBalanceSavings;
	}
	
	public void setStartingBalanceCredit(double startingBalanceCredit) {
		this.startingBalanceCredit = startingBalanceCredit;
	}
	
	public void setEndingBalanceCredit(double endingBalanceCredit) {
		this.endingBalanceCredit = endingBalanceCredit;
	}
	
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	/**
	 * Generates a file and writes all the Bank account information for the Customer here
	 */
	public void createStatement(){
		try {
			//Name file starting with last name of the user then the first name
			FileWriter myWriter = new FileWriter(
				customer.getLastName() + customer.getFirstName() + "Statement.txt");
			
			//First line of the output file
			myWriter.write("Disney Bank " + customer.getFullName() + " statement!");
			
			//Write the statement into the file
			myWriter.write(this.toString());
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred when trying to create the file.");
		}
	}
	
	@Override
	public String toString(){
		
		String checkingInfo = "";
		if(customer.getChecking() != null){
			checkingInfo = "Account: Checking-" + customer.getChecking().getNumber() + "\n" +
				"Starting Balance: $" + startingBalanceChecking + "\n" +
				"Ending Balance: $" + String.format("%.2f", customer.getChecking().getBalance());
		}
		
		String creditInfo = "";
		if(customer.getCredit() != null){
			creditInfo = "Account: Credit-" + customer.getCredit().getNumber() + "\n" +
				"Starting Balance: $" + startingBalanceCredit + "\n" +
				"Ending Balance: $" + String.format("%.2f", customer.getCredit().getBalance());
		}
		
		String savingsInfo = "";
		if(customer.getSavings() != null){
			savingsInfo = "Account: Savings-" + customer.getSavings().getNumber() + "\n" +
				"Starting Balance: $" + startingBalanceSavings + "\n" +
				"Ending Balance: $" + String.format("%.2f", customer.getChecking().getBalance());
		}
		
		
		String string = "Name: " + customer.getFullName() + "\n" +
			"Identification Number: " + customer.getId() + "\n" +
			"Date of Birth: " + customer.getDob() + "\n" +
			"Address: " + customer.getAddress() + "\n" +
			"Phone Number: " + customer.getAddress() + "\n" +
			checkingInfo + "\n" +
			savingsInfo + "\n" +
			creditInfo + "\n";
		
		for(Transaction transaction : transactions){
			string += transaction.getTransaction() + "\n";
		}
		
		return string;
	}
}
