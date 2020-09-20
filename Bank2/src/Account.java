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
}
