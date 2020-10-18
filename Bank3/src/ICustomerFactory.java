import java.util.Collection;

public interface ICustomerFactory {
	public Collection<Customer> getCustomers();
	
	public Customer getCustomer(String fullName);
	
	public Account getAccountByType(String type);
	
	public void addCustomer(Customer customer);
}
