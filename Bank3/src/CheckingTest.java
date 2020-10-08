import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckingTest {
	private Checking checking;
	
	@Before
	public void setUp() throws Exception {
		checking = new Checking(1000, 360.24);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void deposit1() {
		assertTrue(checking.deposit(100));
		assertEquals(460.24, checking.getBalance(), 0.001);
	}
	
	@Test
	public void deposit2() {
		assertTrue(checking.deposit(10.50));
		assertEquals(370.74, checking.getBalance(), 0.001);
	}
	
	@Test
	public void deposit3() {
		assertFalse(checking.deposit(-50));
		assertEquals(360.24, checking.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw1() {
		assertTrue(checking.withdraw(60));
		assertEquals(300.24, checking.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw2() {
		assertFalse(checking.withdraw(-100));
		assertEquals(360.24, checking.getBalance(), 0.001);
	}
	
	@Test
	public void withdraw3() {
		assertFalse(checking.withdraw(500));
		assertEquals(360.24, checking.getBalance(), 0.001);
	}
}