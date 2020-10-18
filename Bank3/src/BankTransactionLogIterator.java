import java.util.ArrayList;
import java.util.Iterator;

public class BankTransactionLogIterator implements Iterator {
	
	private ArrayList<String> transactions;
	private int index;
	
	public BankTransactionLogIterator(ArrayList<String> transactions){
		this.transactions = transactions;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext() {
		return index < transactions.size();
	}
	
	@Override
	public Object next() {
		return transactions.get(index++);
	}
}
