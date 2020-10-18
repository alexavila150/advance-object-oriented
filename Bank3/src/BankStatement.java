import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BankStatement {
	private Customer customer;
	private Account account;
	private double startingBalance;
	private double endingBalance;
	private ArrayList<String> transactions;
	
	public BankStatement(){}
	
	public BankStatement(
		Customer customer,
		Account account,
		double startingBalance,
		double endingBalance,
		ArrayList<String> transactions
	){
		this.customer = customer;
		this.account = account;
		this.startingBalance = startingBalance;
		this.endingBalance = endingBalance;
		this.transactions = transactions;
	}
	
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
		String string = "Name: " + customer.getFullName() + "\n" +
			"Identification Number: " + customer.getId() + "\n" +
			"Date of Birth: " + customer.getDob() + "\n" +
			"Address: " + customer.getAddress() + "\n" +
			"Phone Number: " + customer.getAddress() + "\n" +
			"Account ID: " + account.getNumber() + "\n" +
			"Starting Balance: " + this.startingBalance + "\n" +
			"Ending Balance: " + this.endingBalance + "\n";
		
		for(String transaction : transactions){
			string += transaction + "\n";
		}
		
		return string;
	}
}
