public class Credit{
	private int number;
	private double balance;
	
	public Credit(){
	}
	
	public Credit(int number, double balance){
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
	
	public boolean pay(double amount){
		// not negative amount allowed
		if(amount < 0){
			return false;
		}
		
		// cannot pay more than debt
		if(balance + amount > 0){
			return false;
		}
		
		balance += amount;
		return true;
	}
	
	public boolean use(double amount){
		// not negative amount allowed
		if(amount < 0){
			return false;
		}
		
		balance -= amount;
		return true;
	}
}
