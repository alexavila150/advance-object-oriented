import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * @author Alex Avila
 * @version 1.0
 * @since 9/28/20
 * This stores the transaction information and the time where the instance of the
 * transaction was created
 */
public class Transaction {
	private String transaction;
	private Customer customer;
	
	public Transaction(){}
	
	public Transaction(String transaction, Customer customer){
		this.customer = customer;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.transaction = transaction +  "\n" + dtf.format(now);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Getters
	 ----------------------------------------------------------------------------------------------------------------*/
	
	public String getTransaction() {
		return transaction;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------
	                                            Setters
	 ----------------------------------------------------------------------------------------------------------------*/
	
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
