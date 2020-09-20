import com.sun.istack.internal.NotNull;

import javax.print.DocFlavor;

public class Customer extends Person{
	private String id;
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
	 *                                          Getters
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
	
	// from savings to checking
	public boolean transfer(Savings source, Checking dest, double amount){
		if(!source.withdraw(amount)){
			return false;
		}
		
		dest.deposit(amount);
		return true;
	}
	
	// from savings to Credit
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
	
	
}
