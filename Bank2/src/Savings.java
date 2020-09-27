public class Savings extends Account {
	private double interestRate;
	
	/**
	 * Default Constructor
	 */
	public Savings(){}
	
	/**
	 * This constructor is the does not take interest rate but it is the one used in RunBank because
	 * there is no interest rate in the csv file
	 * @param number Account number unique for savings always starts with digit 2
	 * @param balance Current balance at the Savings account
	 */
	public Savings(int number, double balance){
		super(number, balance);
	}
	
	/**
	 * This constructor includes all the attributes in the super and the interest rate however this method
	 * is not use that much because there is not interest rate in the csv file
	 * @param number Account number unique for savings always starts with digit 2
	 * @param balance Current balance at the Savings account
	 * @param interestRate the interest rate that is not given in the csv file
	 */
	public Savings(int number, double balance, double interestRate){
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
