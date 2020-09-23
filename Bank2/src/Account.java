public abstract class Account {
	private int number;
	private double balance;
	
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
	
	/******************************************************************************************************************
	 *                                          Setters
	 * ***************************************************************************************************************/
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
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
