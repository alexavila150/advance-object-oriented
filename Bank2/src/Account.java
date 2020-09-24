public abstract class Account {
	protected int number;
	protected double balance;
	protected Customer customer;
	
	public Account(){ }
	
	public Account(int number, double balance){
		this.number = number;
		this.balance = balance;
	}
	
	/******************************************************************************************************************
	 *                                          Getters
	 * ***************************************************************************************************************/
	
	public int getNumber() {
		return number;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	/******************************************************************************************************************
	 *                                          Setters
	 * ***************************************************************************************************************/
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/******************************************************************************************************************
	 *                                          Actuator
	 * ***************************************************************************************************************/
	
	public boolean deposit(double amount){
		// no negative deposits
		if(amount < 0){
			return false;
		}
		balance += amount;
		return true;
	}
	
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
