import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
	
	ATM setUpATM(){
		HashSet<Checking> checkings = new HashSet<>();
		
		//Read File
		try {
			Scanner fileScnr = new Scanner(
				new File("D:\\UTEP\\2020 Fall\\AOOP\\advance-object-oriented\\Bank\\src\\BankUsers.csv"));
			
			//For every line make a new Checking instance
			fileScnr.nextLine(); //Ignores first line
			while(fileScnr.hasNextLine()){
				String line = fileScnr.nextLine();
				String[] attributes = line.split(",");
				checkings.add(
					new Checking(
						attributes[0],
						attributes[1],
						Integer.parseInt(attributes[2]),
						Boolean.parseBoolean(attributes[3]),
						Boolean.parseBoolean(attributes[4]),
						Double.parseDouble(attributes[5]),
						Double.parseDouble(attributes[6].substring(0,attributes[6].length() - 1))
					)
				);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ATM atm = new ATM(checkings);
		
		while(atm.getRunning()){
			atm.optionsMenu();
		}
		return atm;
	}
	
	/******************************************************************************************************************
	 *                                              Deposit
	 * ***************************************************************************************************************/
	
	@org.junit.jupiter.api.Test
	void deposit() {
		//make test system
		String inputString = "5600945\n" +
			"a\n" +
			"100\n" +
			"g\n";
		System.setIn(new ByteArrayInputStream((inputString.getBytes())));
		ATM atm = setUpATM();
		
		assertEquals(293.83, atm.getCurrAccount().getStartingBalance(), 0.001);
		
	}
	
	/******************************************************************************************************************
	 *                                              Withdraw
	 * ***************************************************************************************************************/
	
	@org.junit.jupiter.api.Test
	void withdraw() {
		//make test system
		String inputString = "5600950\n" +
			"b\n" +
			"9.44\n" +
			"a\n" +
			"10\n" +
			"g\n";
		System.setIn(new ByteArrayInputStream((inputString.getBytes())));
		ATM atm = setUpATM();
		
		assertEquals(60, atm.getCurrAccount().getStartingBalance(), 0.001);
	}
	
	@org.junit.jupiter.api.Test
	void withdraw1() {
		//make test system
		String inputString = "5600947\n" +
			"b\n" +
			"200\n" +
			"100\n" +
			"g\n";
		System.setIn(new ByteArrayInputStream((inputString.getBytes())));
		ATM atm = setUpATM();
		
		assertEquals(10.19, atm.getCurrAccount().getStartingBalance(), 0.001);
	}
	
	/******************************************************************************************************************
	 *                                             Transfer to account
	 * ***************************************************************************************************************/
	
	@org.junit.jupiter.api.Test
	void transferToAccount() {
		String inputString = "5600948\n" +
			"a\n" +
			"25\n" +
			"c\n" +
			"5600949\n" +
			"50\n" +
			"g\n";
		System.setIn(new ByteArrayInputStream((inputString.getBytes())));
		ATM atm = setUpATM();
		
		assertEquals(250.54, atm.getCurrAccount().getStartingBalance(), 0.001);
		assertEquals(956.35, atm.getSelectedAccount().getStartingBalance(), 0.001);
	}
	
	/******************************************************************************************************************
	 *                                             Transfer from account
	 * ***************************************************************************************************************/
	
	@org.junit.jupiter.api.Test
	void transferFromAccount() {
		String inputString = "5600960\n" + //Woody
			"b\n" +
			"18.42\n" +
			"d\n" +
			"5600961\n" + //Buzz
			"500\n" +
			"g\n";
		System.setIn(new ByteArrayInputStream((inputString.getBytes())));
		ATM atm = setUpATM();
		
		assertEquals(1500, atm.getCurrAccount().getStartingBalance(), 0.001);
		assertEquals(1449.01, atm.getSelectedAccount().getStartingBalance(), 0.001);
	}
	
	/******************************************************************************************************************
	 *                                            Switch account
	 * ***************************************************************************************************************/
	
	@org.junit.jupiter.api.Test
	void switchAccount() {
		String inputString = "5600960\n" + //Woody
			"b\n" +
			"18.42\n" +
			"f\n" +
			"5600961\n" + //Buzz
			"a\n" +
			"50\n" +
			"g\n";
		System.setIn(new ByteArrayInputStream((inputString.getBytes())));
		ATM atm = setUpATM();
		
		assertEquals("Buzz", atm.getCurrAccount().getFirstName());
		assertEquals(1999.01, atm.getCurrAccount().getStartingBalance(), 0.001);
	}
}