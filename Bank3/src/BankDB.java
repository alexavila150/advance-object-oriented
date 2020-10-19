import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BankDB implements IBankDB{
	private HashMap<String, Customer> namesToCustomers;
	private HashMap<Integer, Checking> numbersToCheckings;
	private HashMap<Integer, Savings> numbersToSavings;
	private HashMap<Integer, Credit> numbersToCredit;
	private ArrayList<Transaction> transactions;
	private HashMap<Customer, BankStatement> customerToStatement;
	
	
	public BankDB(){
		this.namesToCustomers = new HashMap<>();
		this.numbersToCheckings = new HashMap<>();
		this.numbersToSavings = new HashMap<>();
		this.numbersToCredit = new HashMap<>();
		this.transactions = new ArrayList<>();
		this.customerToStatement = new HashMap<>();
	}
	
	@Override
	public void addCustomer(Customer customer) {
		//store instance of customer in hash table with name as their key
		namesToCustomers.put(customer.getFullName(), customer);
		
		//store the customer accounts with the account number as their key
		if(customer.getChecking() != null){
			numbersToCheckings.put(customer.getChecking().getNumber(), customer.getChecking());
		}
		if(customer.getSavings() != null){
			numbersToSavings.put(customer.getSavings().getNumber(), customer.getSavings());
		}
		if(customer.getCredit() != null){
			numbersToCredit.put(customer.getCredit().getNumber(), customer.getCredit());
		}
		
		customerToStatement.put(customer, new BankStatement(
			customer,
			customer.getChecking().getBalance(),
			customer.getSavings().getBalance(),
			customer.getCredit().getBalance()
		));
	}
	
	@Override
	public Collection<Customer> getCustomers() {
		return namesToCustomers.values();
	}
	
	@Override
	public boolean containsCustomer(String fullName) {
		return namesToCustomers.containsKey(fullName);
	}
	
	@Override
	public Customer getCustomer(String fullName) {
		return namesToCustomers.get(fullName);
	}
	
	@Override
	public boolean containsChecking(int accountNumber) {
		return numbersToCheckings.containsKey(accountNumber);
	}
	
	@Override
	public boolean containsSavings(int accountNumber) {
		return numbersToSavings.containsKey(accountNumber);
	}
	
	@Override
	public boolean containsCredit(int accountNumber) {
		return numbersToCredit.containsKey(accountNumber);
	}
	
	@Override
	public Account getAccount(int accountNumber) {
		if(numbersToSavings.containsKey(accountNumber)){
			return numbersToSavings.get(accountNumber);
		}
		if(numbersToCredit.containsKey(accountNumber)){
			return numbersToCredit.get(accountNumber);
		}
		if(numbersToCheckings.containsKey(accountNumber)){
			return numbersToCredit.get(accountNumber);
		}
		return null;
	}
	
	@Override
	public void addTransaction(Transaction transaction){
		//add transaction to list
		transactions.add(transaction);
		
		//add transaction to customer's bank statement
		Customer customer = transaction.getCustomer();
		if(customer != null) {
			customerToStatement.get(customer).getTransactions().add(transaction);
		}
	}
	
	@Override
	public Collection<Transaction> getTransactions(){
		return transactions;
	}
	
	@Override
	public Collection<Transaction> getTransactions(Customer customer){
		return customerToStatement.get(customer).getTransactions();
	}
	
	@Override
	public BankStatement getBankStatement(Customer customer){
		return customerToStatement.get(customer);
	}
}
