import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditTest {
	Credit credit1;
	Credit credit2;
	
	@Before
	public void setUp() throws Exception {
		credit1 = new Credit(
			3001,
			-900.50
		);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void deposit1() {
		assertFalse(credit1.deposit(905.32));
		assertEquals(-900.50, credit1.getBalance(), 0.001);
	}
	
	@Test
	public void deposit2() {
		assertTrue(credit1.deposit(100.50));
		assertEquals(-800, credit1.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw() {
		assertFalse(credit1.withdraw(100.50));
	}
}