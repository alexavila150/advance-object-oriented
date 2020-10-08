/**
 * @author Alex Avila
 * @version 1.0
 * @since 9/28/20
 * <p>
 * Abstract class that contains the attributes and methods of an account. Planned to use
 * when inherited from Checking Savings and Credit.
 */
public abstract class Account {
	protected int number;
	protected double balance;
	protected Customer customer;
	
	/**
	 * Default constructor
	 */
	public Account(){ }
	
	/**
	 * Abstract constructor use to initiate the attributes that the account types will inherit
	 * @param number unique account number that varies depending on the type of account
	 * @param balance the current amount of money in this account
	 */
	public Account(int number, double balance){
		this.number = number;
		this.balance = balance;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Getters
	 ----------------------------------------------------------------------------------------------------------------*/
	
	public int getNumber() {
		return number;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Setters
	 ----------------------------------------------------------------------------------------------------------------*/
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Actuators
	 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Puts money into the account and it could be as much as the user wants
	 * @param amount the amount of money the user wants to deposit into the account
	 * @return returns true if transaction was successful, returns false otherwise
	 */
	public boolean deposit(double amount){
		// no negative deposits
		if(amount < 0){
			return false;
		}
		balance += amount;
		return true;
	}
	
	/**
	 * Takes out money from this account if amount is not bigger than current balance
	 * @param amount the amount of money the user wants to withdraw cannot be more than balance
	 * @return returns true if transaction was successful, returns false otherwise
	 */
	public boolean withdraw(double amount){
		// no negative deposits
		if(amount < 0){
			return false;
		}
		
		//not enough balance to withdraw
		if(amount > balance){
			return false;
		}
		
		balance -= amount;
		return true;
	}
	
}
