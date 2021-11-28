package b_Money;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CurrencyTest {

	static Currency SEK, DKK, NOK, EUR;
	
	@BeforeAll
	static void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate());
	}
	
	@Test
	public void testSetRate() {
		DKK.setRate(0.50);
		assertEquals(0.50, DKK.getRate());
	}
	
	@Test
	public void testUniversalValue() {
		Double uVal = SEK.universalValue(12345);
		assertEquals(123.45 * SEK.getRate(), uVal);
	}
	
	@Test
	public void testValueInThisCurrency() {
		Double EURtoSEK = SEK.valueInThisCurrency(10025, EUR);
		Double testVal = 100.25 * SEK.getRate();
		assertEquals(testVal, EURtoSEK);
	}
	
}


