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
	public void pay1() {
		assertFalse(credit1.pay(905.32));
		assertEquals(-900.50, credit1.getBalance(), 0.01);
	}
	
	@Test
	public void pay2() {
		assertTrue(credit1.pay(100.50));
		assertEquals(-800, credit1.getBalance(), 0.01);
	}
	
	@Test
	public void use() {
		assertTrue(credit1.use(100.50));
		assertEquals(-1001, credit1.getBalance(), 0.01);
	}
}