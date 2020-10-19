import java.util.Collection;

public interface IBankDB {
	
	void addCustomer(Customer customer);
	
	Collection<Customer> getCustomers();
	
	boolean containsCustomer(String fullName);
	
	Customer getCustomer(String fullName);
	
	boolean containsChecking(int accountNumber);
	
	boolean containsSavings(int accountNumber);
	
	boolean containsCredit(int accountNumber);
	
	Account getAccount(int accountNumber);
	
	void addTransaction(Transaction transaction);
	
	Collection<Transaction> getTransactions();
	
	Collection<Transaction> getTransactions(Customer customer);
}
