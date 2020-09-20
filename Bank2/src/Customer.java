public class Customer extends Person{
	private int id;
	private Checking checking;
	private Savings savings;
	private Credit credit;
	
	public Customer(){
		super();
	}
	
	public Customer(
		String firstName,
		String lastName,
		String dob,
		String address,
		String phone,
		int id,
		Checking checking,
		Savings savings,
		Credit credit)
	{
		super(firstName, lastName, dob, address, phone);
		this.id = id;
		this.checking = checking;
		this.savings = savings;
		this.credit = credit;
	}
	
	
	
	/******************************************************************************************************************
	 *                                          Getters
	 * ***************************************************************************************************************/
	
	public int getId() {
		return id;
	}
	
	public Checking getChecking() {
		return checking;
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
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setChecking(Checking cheking) {
		this.checking = cheking;
	}
	
	public void setSavings(Savings savings) {
		this.savings = savings;
	}
	
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
}
