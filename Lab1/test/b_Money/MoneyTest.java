package b_Money;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class MoneyTest {

	static Currency SEK, DKK, NOK, EUR;
	static Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	/*
	 * Setting up all testing variables
	*/
	@BeforeAll
	static void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/*
	 * Testing getAmount() method
	 * Money SEK100 is initialized with amount 10000 = 100.00
	 * So I create temporary variable of value 100.00 multiplied by rate
	 * And comparing temporary variable with actual amount in Double, they should be equal
	*/
	@Test
	public void testGetAmount() {
		Double tempAmount = 100 * 0.15;
		assertEquals(tempAmount, SEK100.getAmount());
	}

	/*
	 * Testing getCurrency() method
	 * Comparing Currency SEK with actual Currency of Money SEK100, they should be equal
	*/
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
	}

	/*
	 * Testing toString() method
	 * I create temporary variable of value 100.00 multiplied by rate
	 * Comparing temporary variable casted to String with SEK100 toString() method
	*/
	@Test
	public void testToString() {
		Double tempStr = 100.00 * SEK100.getCurrency().getRate();
		assertEquals(tempStr.toString() + " SEK", SEK100.toString());
	}

	/*
	 * Testing universalValue() method
	 * Comparing temporary variable with univerlValue
	*/
	@Test
	public void testUniversalValue() {
		Double testStr = 100.00 * SEK100.getCurrency().getRate();
		assertEquals(testStr, SEK100.universalValue());
	}

	/*
	 * Testing equalsMoney() method
	 * Creating temporary Money and Comparing it with actual SEK100 Money
	*/
	@Test
	public void testEqualsMoney() {
		Money testMoney = new Money(10000, SEK);
		assertTrue(SEK100.equals(testMoney), "Values must be equal");
		assertFalse(SEK100.equals(EUR10), "Values must be different");
	}

	/*
	 * Testing add() method
	 * Comparing temporary variable with SEK100 Money by adding additional SEK200 Money
	*/
	@Test
	public void testAdd() {
		Double testAmount = (100.0 + 200.0) * 0.15;
		assertEquals(testAmount, SEK100.add(SEK200).getAmount());
	}

	/*
	 * Testing sub() method
	 * Comparing temporary variable with SEK200 Money by subtracting additional SEK100 Money
	*/
	@Test
	public void testSub() {
		Double testAmount = (200.0 - 100.0) * 0.15;
		assertEquals(testAmount, SEK200.sub(SEK100).getAmount());
	}

	/*
	 * Testing isZero() method
	 * Checking SEK0 Money if it's amount is zero for True statement
	*/
	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
	}

	/*
	 * Testing negate() method
	 * Checking SEKn100 Money if it's amount is negative for True statement
	 * Negating SEK200 Money and checking if it's amount is negative for True statement
	*/
	@Test
	public void testNegate() {
		assertTrue(SEKn100.getAmount() < 0);
		assertTrue(SEK200.negate().getAmount() < 0);
	}

	/*
	 * Testing compareTo() method
	 * Comparing temporary Money with SEK100 Money for True statement
	 * If they are equal the result will be 0
	*/
	@Test
	public void testCompareTo() {
		Money testMoney = new Money(10000, SEK);
		assertEquals(0, SEK100.compareTo(testMoney));
		assertNotEquals(EUR10, SEK100);
	}

}

