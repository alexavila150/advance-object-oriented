/**
 * @author Alex Avila
 * @version 1.0
 * @since 9/27/2020
 */
public class Customer extends Person{
	private String id;
	private Checking checking;
	private Savings savings;
	private Credit credit;
	
	public Customer(){
		super();
	}
	
	/**
	 * @param firstName Customer's first name inherited from Person class
	 * @param lastName Customer's last name inherited from Person class
	 * @param dob Customer's date of birth as a String inherited from Person class
	 * @param address Customer's address inherited from Person class
	 * @param phone Customer's phone number inherited from Person class
	 * @param id Customer's identification number
	 * @param checking Customer's checkings account which is a Checking instance
	 * @param savings Customer's savings account which is a Savings instance
	 * @param credit Customer's credit account which is a Credit instance
	 */
	public Customer(
		String firstName,
		String lastName,
		String dob,
		String address,
		String phone,
		String id,
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
	
	public String getId() {
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
	 *                                          Setters
	 * ***************************************************************************************************************/
	
	public void setId(String id) {
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
	
	/******************************************************************************************************************
	 *                                          Transfers
	 * ***************************************************************************************************************/
	
	/**
	 * Transfers money determined by amount from source to dest account
	 * @param source Account where money is coming from to pay the destination account
	 * @param dest Account where money if going to from the source account
	 * @param amount the amount of money that is being transfer between both accounts
	 * @return returns true if the transaction was successful and false otherwise
	 */
	public boolean transfer(Account source, Account dest, double amount){
		//if there are not enough funds return false
		if(!source.withdraw(amount)){
			return false;
		}
		
		//if cannot deposit then deposit back to source and return false
		if(!dest.deposit(amount)){
			source.deposit(amount);
			return false;
		}
		
		return true;
	}
	
	/**
	 * transfer money from this customer's checking account and sends it to the dest customer checking
	 * account
	 * @param dest Destination account that money will be going to
	 * @param amount Amount of money that is going to be transfer
	 * @return
	 */
	public boolean paySomeone(Customer dest, double amount){
		//get checking account from customer
		return transfer(checking, dest.getChecking(), amount);
	}
	
	/**
	 * Formats the user information into csv style
	 * @return String with the Customers information formatted to be a line of the CSV file
	 */
	public String toCsvLine(){
		return firstName + "," + lastName + "," + dob + "," + id + "," +
			address + "," + phone + "," + checking.getNumber() + "," + savings.getNumber() + "," +
			credit.getNumber() + "," + checking.getBalance() + "," + savings.getBalance() + "," +
			credit.getBalance();
	}
	
	/**
	 * converts information of the Customer into a formatted String
	 * @return returns a String with all the Customer's information
	 */
	@Override
	public String toString() {
		return "Costumer Information:\n" +
			"Full Name: " + firstName + " " + lastName + "\n" +
			"Date of birth: " + dob + "\n" +
			"Address: " + address + "\n" +
			"Phone: " + phone + "\n" +
			"ID: " + id + "\n" +
			"Checking: \n" +
			"\tAccount Number: " + checking.getNumber() + "\n" +
			"\tBalance: $" + String.format("%.2f", checking.getBalance()) + "\n" +
			"Saving:\n" +
			"\tAccount Number: " + savings.getNumber() + "\n" +
			"\tBalance: $" + String.format("%.2f", savings.getBalance()) + "\n" +
			"Credits:\n" +
			"\tAccount Number: " + credit.getNumber() + "\n" +
			"\tBalance: $" + String.format("%.2f", credit.getBalance()) + "\n";
	}
}
