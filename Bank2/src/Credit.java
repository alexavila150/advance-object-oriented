public class Credit extends Account{
	public Credit(){
		super();
	}
	
	public Credit(int number, double balance){
		super(number, balance);
	}
	
	@Override
	public boolean deposit(double amount){
		// not negative amount allowed
		if(amount < 0){
			return false;
		}
		
		// cannot pay more than debt
		if(balance + amount > 0){
			return false;
		}
		
		balance += amount;
		return true;
	}
	
	@Override
	public boolean withdraw(double amount){
		// not negative amount allowed
		if(amount < 0){
			return false;
		}
		
		balance -= amount;
		return true;
	}
}
