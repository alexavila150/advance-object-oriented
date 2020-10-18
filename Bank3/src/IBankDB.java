import java.util.Collection;

public interface IBankDB {
	
	public void addCustomer(Customer customer);
	
	public Collection<Customer> getCustomers(Customer customer);
	
	public Customer getCustomer(String fullName);
	
	public void addAccount(Account account);
}
