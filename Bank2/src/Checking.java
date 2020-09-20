public class Checking extends Account {
	private double interestRate;
	
	public Checking(){}
	
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
