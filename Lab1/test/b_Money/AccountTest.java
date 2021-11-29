package b_Money;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AccountTest {
	
	static Currency SEK, DKK;
	static Bank Nordea, DanskeBank, SweBank;
	static Account testAccount;
	
	// setting up all testing variables
	@BeforeAll
	static void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	// testing TimePayment methods - remove, add
	// adjusted tick() method
	// Creating temporary Accounts and creating delayed payments
	// And comparing accounts balances with expected amount, if delayed payment was removed or performed
	@Test
	public void testAddRemoveTimedPayment() throws AccountDoesNotExistException {
		try {
			SweBank.openAccount("Elis");
			SweBank.openAccount("Nana");
			SweBank.deposit("Nana", new Money(50000, SEK));
			
			SweBank.addTimedPayment("Nana", "1", 1, 0, new Money(10000, SEK), SweBank, "Elis"); 
			
			SweBank.tick(3);
			SweBank.removeTimedPayment("Nana", "1");
			SweBank.tick(3);
			assertEquals(200 * 0.15, SweBank.getAccount("Elis").getBalance().getAmount());
			
		} catch (AccountExistsException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	// testing timePayment() method
	// Found bug, bad implementation of counting ticks
	// Creating new account with some balance
	// Performing delayed payments for accounts
	// And initializing tick method to check if account balance was increased
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		try {
			SweBank.openAccount("Mark");
			SweBank.openAccount("Alex");
			SweBank.deposit("Alex", new Money(30000, SEK));
			
			SweBank.addTimedPayment("Alex", "1", 2, 0, new Money(1000, SEK), SweBank, "Mark");  // 3 times -> 30 SEK
			SweBank.addTimedPayment("Alex", "2", 6, 6, new Money(10000, SEK), SweBank, "Mark"); // aborted
			SweBank.addTimedPayment("Alex", "3", 8, 6, new Money(10000, SEK), SweBank, "Mark");
			
			// Results expected:
			// "Mark"   130 * 0.15 = 19.5 SEK  
			// "Alex"   300 - 130  = 170  ->  170 * 0.15 = 25.5 SEK 
						
			SweBank.tick(5);  // ticks executed only 2 times by Id "1", so 20 * 0.15 = 3 SEK
			assertEquals(3, SweBank.getAccount("Mark").getBalance().getAmount());
						
			SweBank.removeTimedPayment("Alex", "2");  // Removing this for test
			SweBank.tick(3);
			assertEquals(25.5, SweBank.getAccount("Alex").getBalance().getAmount());
			
		} catch (AccountExistsException e) {
			System.out.println(e.getMessage());
		}		
	}

	// testing deposit() and withdraw() methods
	// Adding and Withdrawing money from test Acoount
	// Comparing new amounts with this account, should return true
	@Test
	public void testAddWithdraw() {
		Double balance = 100000.00 * 0.15;
		testAccount.deposit(new Money(10000, SEK));
		assertEquals(balance + 100.00 * 0.15, testAccount.getBalance().getAmount());
		
		balance = 100100.00 * 0.15;
		testAccount.withdraw(new Money(10000000, SEK));
		assertEquals(balance - 100000.00 * 0.15, testAccount.getBalance().getAmount());
	}
	
	// testing getBalance() method
	// Creating variable of amount 50.00 in SEK Currency
	// Comparing temporary money with account "Фдшсу" balance, should return true
	@Test
	public void testGetBalance() {
		try {
			Double balance = 10000.00 * 0.15;
			assertEquals(balance, SweBank.getBalance("Alice"));
		} catch (AccountDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

}
