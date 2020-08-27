public class Checking {
	private String firstName;
	private String lastName;
	private int accountNumber;
	private boolean checking;
	private boolean savings;
	private double startingBalance;
	private double interestRate;
	
	/****************************************************************************************************************
	 *                                      Constructors
	 * **************************************************************************************************************/
	
	public Checking(){}
	
	public Checking(
		String firstName,
		String lastName,
		int accountNumber,
		boolean checking,
		boolean savings,
		double startingBalance,
		double interestRate
	){
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountNumber = accountNumber;
		this.checking = checking;
		this.savings = savings;
		this.startingBalance = startingBalance;
		this.interestRate = interestRate;
	}
	
	/****************************************************************************************************************
	 *                                      Getters
	 * **************************************************************************************************************/
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public int getAccountNumber(){
		return accountNumber;
	}
	
	public boolean getChecking(){
		return checking;
	}
	
	public boolean getSavings(){
		return savings;
	}
	
	public double getStartingBalance(){
		return startingBalance;
	}
	
	public double getInterestRate(){
		return interestRate;
	}
	
	/****************************************************************************************************************
	 *                                      Setters
	 * **************************************************************************************************************/
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setAccountNumber(int accountNumber){
		this.accountNumber = accountNumber;
	}
	
	public void setChecking(boolean checking){
		this.checking = checking;
	}
	
	public void setSavings(boolean savings){
		this.savings = savings;
	}
	
	public void setStartingBalance(double startingBalance){
		this.startingBalance = startingBalance;
	}
	
	public void setInterestRate(double interestRate){
		this.interestRate = interestRate;
	}
}
