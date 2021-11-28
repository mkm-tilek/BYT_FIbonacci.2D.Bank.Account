package b_Money;
import java.math.BigDecimal;
import java.util.*;

public class Currency {
	private String name;
	private Double rate;
	
	/**
	 * New Currency
	 * The rate argument of each currency indicates that Currency's "universal" exchange rate.
	 * Imagine that we define the rate of each currency in relation to some universal currency.
	 * This means that the rate of each currency defines its value compared to this universal currency.
	 *
	 * @param name The name of this Currency
	 * @param rate The exchange rate of this Currency
	 */
	Currency (String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	/** Convert an amount of this Currency to its value in the general "universal currency"
	 * (As mentioned in the documentation of the Currency constructor)
	 * 
	 * @param amount An amount of cash of this currency.
	 * @return The value of amount in the "universal currency"
	 */
	public Double universalValue(Integer amount) {	
		// Here I assume that in "universal currency" means (amount * rate) and it should actually return Double
		if (amount != 0) {
			String amountStr = String.valueOf(amount);
			String digit = amountStr.substring(0, amountStr.length() - 2);
			String decimal = amountStr.substring(amountStr.length() - 2);
			
			return Double.valueOf(digit + "." + decimal) * rate;
		} else {
			return Double.valueOf(amount);
		}
	}

	/** Get the name of this Currency.
	 * @return name of Currency
	 */
	public String getName() {
		return this.name;
	}
	
	/** Get the rate of this Currency.
	 * 
	 * @return rate of this Currency
	 */
	public Double getRate() {
		return this.rate;
	}
	
	/** Set the rate of this currency.
	 * 
	 * @param rate New rate for this Currency
	 */
	public void setRate(Double rate) {
		if(rate != null)
			this.rate = rate;
		
	}
	
	/** Convert an amount from another Currency to an amount in this Currency
	 * 
	 * @param amount Amount of the other Currency
	 * @param othercurrency The other Currency
	*/
	public Double valueInThisCurrency(Integer amount, Currency othercurrency) {
		// Here I assume that @param amount is not in the general "universal currency", because amount parameter is type of Integer.
		if (othercurrency.getRate().equals(this.rate) && othercurrency.getName().equals(name)) {
			return othercurrency.universalValue(amount);
		} else {
			return universalValue(amount);
		}
	}
	
	
//	public Double valueInThisCurrency(Integer amount, Currency othercurrency) {
//		if (othercurrency.getRate().equals(this.getRate()) && othercurrency.getName() == this.getName()) {
//			return universalValue(amount);
//		} else {
//			String amountStr = amount.toString();
//			String digit = amountStr.substring(0, amountStr.length() - 2);
//			String decimal = amountStr.substring(amountStr.length() - 2);
//			
//			return Double.valueOf(digit + "." + decimal) * (othercurrency.getRate() / this.rate);
//		}
//	}
}
