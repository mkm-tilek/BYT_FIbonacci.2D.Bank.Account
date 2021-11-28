package b_Money;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class MoneyTest {

	static Currency SEK, DKK, NOK, EUR;
	static Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
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

	@Test
	public void testGetAmount() {
		Double testAmount = 100 * 0.15;
		assertEquals(testAmount, SEK100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
	}

	@Test
	public void testToString() {
		Double testStr = 100.00 * SEK100.getCurrency().getRate();
		assertEquals(testStr.toString() + " SEK", SEK100.toString());
	}

	@Test
	public void testUniversalValue() {
		Double testStr = 100.00 * SEK100.getCurrency().getRate();
		assertEquals(testStr, SEK100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		Money testMoney = new Money(10000, SEK);
		assertTrue(SEK100.equals(testMoney), "Values must be equal");
		assertFalse(SEK100.equals(EUR10), "Values must be different");
	}

	@Test
	public void testAdd() {
		Double testAmount = (100.0 + 200.0) * 0.15;
		assertEquals(testAmount, SEK100.add(SEK200).getAmount());
	}

	@Test
	public void testSub() {
		Double testAmount = (200.0 - 100.0) * 0.15;
		assertEquals(testAmount, SEK200.sub(SEK100).getAmount());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
	}

	@Test
	public void testNegate() {
		assertTrue(SEKn100.getAmount() < 0);
	}

	@Test
	public void testCompareTo() {
		Money testMoney = new Money(10000, SEK);
		assertEquals(0, SEK100.compareTo(testMoney));
		assertNotEquals(EUR10, SEK100);
	}

}

