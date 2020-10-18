import java.util.ArrayList;
import java.util.Iterator;

public class BankTransactionLog implements Iterable {
	
	private ArrayList<String> transactions = new ArrayList<>();
	
	@Override
	public Iterator iterator() {
		return new BankTransactionLogIterator(transactions);
	}
	
	public void add(String string){
		transactions.add(string);
	}
}
