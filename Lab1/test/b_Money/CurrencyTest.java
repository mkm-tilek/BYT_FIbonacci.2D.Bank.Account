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

	/*
	 * Testing getName() method
	 * Comparing temporary name variable of value "SEK" with SEK Currency's name
	 * Should return true
	*/
	@Test
	public void testGetName() {
		String tempName = "SEK";
		assertEquals(tempName, SEK.getName());
	}
	
	/*
	 * Testing getRate() method
	 * Comparing temporary rate variable of value 0.15 with SEK Currency's rate
	 * Should return true
	*/
	@Test
	public void testGetRate() {
		Double tempRate = 0.15;
		assertEquals(tempRate, SEK.getRate());
	}
	
	/*
	 * Testing setRate() method
	 * Setting new rate for DKK Currency with temporary rate variable of value 0.50
	 * Comparing temporary rate variable with updated DKK Currency's rate
	*/
	@Test
	public void testSetRate() {
		Double tempRate = 0.50;
		DKK.setRate(tempRate);
		assertEquals(tempRate, DKK.getRate());
	}
	
	/*
	 * Testing universalValue() method
	 * Creating temporary universal value variable of value 12345
	 * Comparing it with custom universal value
	*/
	@Test
	public void testUniversalValue() {
		Double uVal = SEK.universalValue(12345);
		assertEquals(123.45 * SEK.getRate(), uVal);
	}
	
	/*
	 * Testing valueInThisCurrency() method
	 * Creating EURtoSEK variable of EUR Currency amount converted to SEK Currency 
	 * Creating temporary variable in SEK Currency
	 * Comparing temporary variable with converted EURtoSEK amount
	*/
	@Test
	public void testValueInThisCurrency() {
		Double EURtoSEK = SEK.valueInThisCurrency(10025, EUR);
		Double temptVal = 100.25 * SEK.getRate();
		assertEquals(temptVal, EURtoSEK);
	}
	
}


