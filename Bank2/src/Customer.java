public class Customer extends Person{
	Checking cheking;
	Savings savings;
	Credit credit;
	
	public Customer(Checking checking, Savings savings, Credit credit){
		this.cheking = checking;
		this.savings = savings;
		this.credit = credit;
	}
	
	/******************************************************************************************************************
	 *                                          Getters
	 * ***************************************************************************************************************/
	
	public Checking getCheking() {
		return cheking;
	}
	
	public Savings getSavings() {
		return savings;
	}
	
	public Credit getCredit() {
		return credit;
	}
	
	/******************************************************************************************************************
	 *                                          Getters
	 * ***************************************************************************************************************/
	
	public void setCheking(Checking cheking) {
		this.cheking = cheking;
	}
	
	public void setSavings(Savings savings) {
		this.savings = savings;
	}
	
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
}
