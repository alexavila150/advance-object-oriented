import java.util.ArrayList;

public class BankStatement {
	Customer customer;
	Account account;
	double startingBalance;
	double endingBalance;
	ArrayList<String> transactions;
	String transactionDate;
	
	public BankStatement(){}
	
	public BankStatement(
		Customer customer,
		Account account,
		double startingBalance,
		double endingBalance,
		ArrayList<String> transactions,
		String transactionDate
	){
		this.customer = customer;
		this.account = account;
		this.startingBalance = startingBalance;
		this.endingBalance = endingBalance;
		this.transactions = transactions;
		this.transactionDate = transactionDate;
	}
	
	@Override
	public String toString(){
		String customerString = "Name: " + customer.getFullName() + "\n" +
			"Identification Number: " + customer.getId() + "\n" +
			"Date of Birth: " + customer.getDob() + "\n" +
			"Address: " + customer.getAddress() + "\n" +
			"Phone Number: " + customer.getAddress();
		
		return customerString;
	}
}
