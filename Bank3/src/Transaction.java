public class Transaction {
	private String transaction;
	private Customer customer;
	
	public Transaction(){}
	
	public Transaction(String transaction, Customer customer){
		this.transaction = transaction;
		this.customer = customer;
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
