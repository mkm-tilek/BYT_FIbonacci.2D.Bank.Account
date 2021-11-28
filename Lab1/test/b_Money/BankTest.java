package b_Money;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BankTest {
	static Currency SEK, DKK;
	static Bank SweBank, Nordea, DanskeBank;
	
	// setting up all test variables
	@BeforeAll
	static void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	// testing getName() method
	@Test
	void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	// testing getCurrency() method
	@Test
	void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
	}

	// testing openAccount() method
	// Found bug, there was no creation statement of Account 
	@Test
	void testOpenAccount() {
		try {
			assertTrue(SweBank.openAccount("John"), "Should return true");
			assertEquals("John", SweBank.getAccount("John").getName());
		} catch(Exception e) {
			if(e instanceof AccountExistsException) {
				System.out.println(((AccountExistsException) e).getMessage());
			} else if(e instanceof AccountDoesNotExistException) {
				System.out.println(((AccountDoesNotExistException) e).getMessage());
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

	// testing deposit() method
	// Found bug in if statement
	@Test
	void testDeposit() {
		try {
			SweBank.deposit("John", new Money(10000, SEK));
			assertEquals(SEK.valueInThisCurrency(10000, SEK), SweBank.getAccount("John").getBalance().getAmount());
		} catch(AccountDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	// testing withdraw() method
	// Found bug in if statement
	@Test
	void testWithdraw() {
		try {
			SweBank.deposit("Bob", new Money(10000, SEK));
			SweBank.withdraw("Bob", new Money(5000, SEK));
			assertEquals(SEK.valueInThisCurrency(5000, SEK), SweBank.getBalance("Bob"));
		} catch(AccountDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	// testing getBalance() method
	@Test
	void testGetBalance() {
		try {
			assertEquals(SEK.valueInThisCurrency(5000, SEK), SweBank.getBalance("Bob"));
		} catch(AccountDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// testing transfer() method
	// found bug in overloading transfer() method
	@Test
	public void testTransfer() throws Exception {
		try {
			DanskeBank.openAccount("Katarzina");
			DanskeBank.deposit("Katarzina", new Money(20000, DKK));
			DanskeBank.transfer("Katarzina", "Gertrud", new Money(10000, DKK));
			assertEquals(DanskeBank.getBalance("Katarzina"), DanskeBank.getBalance("Gertrud"));
			
			Nordea.openAccount("Danilo");
			Nordea.deposit("Danilo", new Money(5000, SEK));
			DanskeBank.transfer("Katarzina", Nordea, "Bob", new Money(5000, DKK));
			assertEquals(Nordea.getBalance("Danilo"), Nordea.getBalance("Bob"));
			
		} catch(Exception e) {
			if(e instanceof AccountExistsException) {
				System.out.println(((AccountExistsException) e).getMessage());
			} else if(e instanceof AccountDoesNotExistException) {
				System.out.println(((AccountDoesNotExistException) e).getMessage());
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
	
	// testing timedPayment() method
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		try {
			SweBank.openAccount("Alex");
			SweBank.deposit("Ulrika", new Money(30000, SEK));									  // 300 * 0,15 = 45 SEK
			SweBank.addTimedPayment("Ulrika", "1", 2, 0, new Money(1000, SEK), SweBank, "Alex");  // 3 times -> 30 SEK
			SweBank.addTimedPayment("Ulrika", "2", 6, 6, new Money(10000, SEK), SweBank, "Alex"); // aborted
			SweBank.addTimedPayment("Ulrika", "3", 8, 6, new Money(10000, SEK), SweBank, "Alex"); // 1 time  -> 100 SEK
			
			// Results expected:
			// "Alex"   130 * 0.15 = 19.5 SEK  
			// "Ulrika" 300 - 130  = 170  ->  170 * 0.15 = 25.5 SEK 
			
			SweBank.tick(5);  // ticks executed only 2 times by Id "1", so 20 * 0.15 = 3 SEK
			assertEquals(3, SweBank.getAccount("Alex").getBalance().getAmount());
			
			SweBank.removeTimedPayment("Ulrika", "2");  // Removing this for test
			SweBank.tick(3);
			assertEquals(25.5, SweBank.getAccount("Ulrika").getBalance().getAmount());
		} catch (Exception e) {
			if(e instanceof AccountExistsException) {
				System.out.println(((AccountExistsException) e).getMessage());
			} else if(e instanceof AccountDoesNotExistException) {
				System.out.println(((AccountDoesNotExistException) e).getMessage());
			} else {
				System.out.println(e.getMessage());
			}
		}
	}

}



