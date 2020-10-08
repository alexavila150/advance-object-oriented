import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SavingsTest {
	private Savings savings;
	
	@Before
	public void setUp() throws Exception {
		savings = new Savings(2000, 430.64);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void deposit1() {
		assertTrue(savings.deposit(20));
		assertEquals(450.64, savings.getBalance(), 0.001);
	}
	
	@Test
	public void deposit2() {
		assertTrue(savings.deposit(20.50));
		assertEquals(451.14, savings.getBalance(), 0.001);
	}
	
	@Test
	public void deposit3() {
		assertFalse(savings.deposit(-50));
		assertEquals(430.64, savings.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw1() {
		assertTrue(savings.withdraw(100));
		assertEquals(330.64, savings.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw2() {
		assertFalse(savings.withdraw(-100));
		assertEquals(430.64, savings.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw3() {
		assertFalse(savings.withdraw(500));
		assertEquals(430.64, savings.getBalance(), 0.001);
	}
}