import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Alex Avila
 * @version 1.0
 * @since 10/10/20
 * <p>
 * This class implements an interface IBank which is nesesary to store most of the information about the bank
 * and then update this information using the require methods. This version of IBankDB will use an interface to
 * store the customers accounts and transactions.
 * <p/>
 */
public class BankDB implements IBankDB{
	private HashMap<String, Customer> namesToCustomers;
	private HashMap<Integer, Checking> numbersToCheckings;
	private HashMap<Integer, Savings> numbersToSavings;
	private HashMap<Integer, Credit> numbersToCredit;
	private ArrayList<Transaction> transactions;
	private HashMap<Customer, BankStatement> customerToStatement;
	
	/**
	 * Inizializes instance that inizialazies all the collections with empty hash maps or lists
	 */
	public BankDB(){
		this.namesToCustomers = new HashMap<>();
		this.numbersToCheckings = new HashMap<>();
		this.numbersToSavings = new HashMap<>();
		this.numbersToCredit = new HashMap<>();
		this.transactions = new ArrayList<>();
		this.customerToStatement = new HashMap<>();
	}
	
	/**
	 * add a customer to the customer hash table which key should be their full name
	 * @param customer
	 */
	@Override
	public void addCustomer(Customer customer) {
		//store instance of customer in hash table with name as their key
		namesToCustomers.put(customer.getFullName(), customer);
		
		//store the customer accounts with the account number as their key
		double checkingBalance = 0;
		double savingsBalance = 0;
		double creditBalance = 0;
		
		if(customer.getChecking() != null){
			checkingBalance = customer.getChecking().getBalance();
			numbersToCheckings.put(customer.getChecking().getNumber(), customer.getChecking());
		}
		if(customer.getSavings() != null){
			savingsBalance = customer.getSavings().getBalance();
			numbersToSavings.put(customer.getSavings().getNumber(), customer.getSavings());
		}
		if(customer.getCredit() != null){
			creditBalance = customer.getCredit().getBalance();
			numbersToCredit.put(customer.getCredit().getNumber(), customer.getCredit());
		}
		
		customerToStatement.put(customer, new BankStatement(
			customer,
			checkingBalance,
			savingsBalance,
			creditBalance
		));
	}
	
	/**
	 * gets the collection of customers by taking tha values of the customer hash tables.
	 * @return returns a list of customers
	 */
	@Override
	public Collection<Customer> getCustomers() {
		return namesToCustomers.values();
	}
	
	/**
	 * Checks if the bank contains a customer of a certain name
	 * @param fullName name of the seachee customer
	 * @return returns whether if the customer is in the bank or not
	 */
	@Override
	public boolean containsCustomer(String fullName) {
		return namesToCustomers.containsKey(fullName);
	}
	
	/**
	 * get a customer using their respective full name if the name is in the bank
	 * @param fullName the name of the Customer
	 * @return returns the Customer of the name fullName
	 */
	@Override
	public Customer getCustomer(String fullName) {
		return namesToCustomers.get(fullName);
	}
	
	/**
	 * checks if the bank contains a Checkings account
	 * @param accountNumber uses account number to query account
	 * @return returns the account of certain account number
	 */
	@Override
	public boolean containsChecking(int accountNumber) {
		return numbersToCheckings.containsKey(accountNumber);
	}
	
	/**
	 * checks if the bank contains a Saving account
	 * @param accountNumber uses account number to query account
	 * @return returns the account of certain account number
	 */
	@Override
	public boolean containsSavings(int accountNumber) {
		return numbersToSavings.containsKey(accountNumber);
	}
	
	/**
	 * checks if the bank contains a Credit account
	 * @param accountNumber uses account number to query account
	 * @return returns the account of certain account number
	 */
	@Override
	public boolean containsCredit(int accountNumber) {
		return numbersToCredit.containsKey(accountNumber);
	}
	
	/**
	 * gets account using its account number
	 * @param accountNumber queries account using account number
	 * @return returns the respective account of the account number
	 */
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
	
	/**
	 * adds a transactions that was made when the users does a transaction
	 * @param transaction the transaction information message of the Customer to be added
	 */
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
	
	/**
	 * get all the transactions made by all the Customers in the bank
	 * @return return a list of all the transactions
	 */
	@Override
	public Collection<Transaction> getTransactions(){
		return transactions;
	}
	
	/**
	 * get all the transactions of a certain customer
	 * @param customer the customer to get the transactions from
	 * @return return a list of all customer transactions
	 */
	@Override
	public Collection<Transaction> getTransactions(Customer customer){
		return customerToStatement.get(customer).getTransactions();
	}
	
	/**
	 * get the bank statement of an specific customer
	 * @param customer the bankStatemet's customer
	 * @return return the bank statement of the user.
	 */
	@Override
	public BankStatement getBankStatement(Customer customer){
		return customerToStatement.get(customer);
	}
}
