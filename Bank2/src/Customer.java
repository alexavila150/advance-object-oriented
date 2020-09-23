import com.sun.istack.internal.NotNull;

import javax.print.DocFlavor;

/**
 * @author Alex Avila
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
	
	// from checking to savings
	public boolean transfer(Checking source, Savings dest, double amount){
		if(!source.withdraw(amount)){
			return false;
		}
		
		dest.deposit(amount);
		return true;
	}
	
	// from checking to credit
	public boolean transfer(Checking source, Credit dest, double amount){
		if(dest.getBalance() > -(amount)){
			return false;
		}
		
		if(!source.withdraw(amount)){
			return false;
		}
		
		dest.pay(amount);
		return true;
	}
	
	
	/**
	 * @param source Savings account instance that the money will transfer to dest account
	 * @param dest Checking account that will received money from the source account
	 * @param amount the amount of money transferred from one account to the other
	 * @return returns true is transaction was a success and false is transaction failed
	 */
	public boolean transfer(Savings source, Checking dest, double amount){
		if(!source.withdraw(amount)){
			return false;
		}
		
		dest.deposit(amount);
		return true;
	}
	
	/**
	 * @param source Savings account instance that the money will transfer to dest
	 * @param dest Credit account that will be paid using the source account
	 * @param amount the amount of money transferred from one account to the other
	 * @return returns true is transaction was a success and false is transaction failed
	 */
	public boolean transfer(Savings source, Credit dest, double amount){
		if(dest.getBalance() > -(amount)){
			return false;
		}
		
		if(!source.withdraw(amount)){
			return false;
		}
		
		dest.pay(amount);
		return true;
	}
	
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
			"\tBalance: $" + checking.getBalance() + "\n" +
			"Saving:\n" +
			"\tAccount Number: " + savings.getNumber() + "\n" +
			"\tBalance: $" + savings.getBalance() + "\n" +
			"Credits:\n" +
			"\tAccount Number: " + credit.getNumber() + "\n" +
			"\tBalance: $" + credit.getBalance() + "\n";
	}
}
