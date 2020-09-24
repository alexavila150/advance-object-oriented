public class Checking extends Account {
	private double interestRate;
	
	/**
	 * Default constructor
	 */
	public Checking(){}
	
	/**
	 * Constructor that uses the number and balance as parameter because they are the only information about
	 * checking accounts in the csv file
	 * @param number Account number unique for each account and starts with digit 1 when is checking
	 * @param balance the current amount of money that the account has
	 */
	public Checking(int number, double balance){
		super(number, balance);
	}
	
	/**
	 * Constructor that takes interest rate as a parameter but it is never use because the interest rate is
	 * not in the csv file.
	 * @param number Account number unique for each account and starts with digit 1 when is checking
	 * @param balance the current amount of money that the account has
	 * @param interestRate the interest rate of the account
	 */
	public Checking(int number, double balance, double interestRate){
		super(number, balance);
		this.interestRate = interestRate;
	}
	
	/******************************************************************************************************************
	 *                                          Getters
	 * ***************************************************************************************************************/
	
	public double getInterestRate() {
		return interestRate;
	}
	
	/******************************************************************************************************************
	 *                                          Setters
	 * ***************************************************************************************************************/
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}
