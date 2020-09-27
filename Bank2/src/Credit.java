public class Credit extends Account{
	/**
	 * Default constructor
	 */
	public Credit(){
		super();
	}
	
	/**
	 * Credit constructor with Account parameters
	 * @param number Account number of the credit card. Always starts with digit 3
	 * @param balance current balance of the credit card which is negative because it is dept
	 */
	public Credit(int number, double balance){
		super(number, balance);
	}
	
	/**
	 * Does deposit to credit if it is successful then return false
	 * @param amount the amount of money the user wants to deposit into the account
	 * @return returns true is deposit was successful and false otherwise
	 */
	@Override
	public boolean deposit(double amount){
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
	
	/**
	 * Cannot withdraw from credit card
	 * @param amount the amount of money the user wants to withdraw cannot be more than balance
	 * @return returns false because cannot withdraw from credit card
	 */
	@Override
	public boolean withdraw(double amount){
		return false;
	}
}
