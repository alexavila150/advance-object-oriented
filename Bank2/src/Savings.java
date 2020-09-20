public class Savings extends Account {
	private double interestRate;
	
	public Savings(){}
	
	public Savings(int number, double balance){
		super(number, balance);
	}
	
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
