package b_Money;

import java.text.DecimalFormat;

public class Money implements Comparable {
	private int amount;
	private Currency currency;
	private static final DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * New Money
	 * @param amount	The amount of money
	 * @param currency	The currency of the money
	 */
	Money (int amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	/**
	 * Return the amount of money.
	 * @return Amount of money in Double type.
	 */
	public Double getAmount() {
		// Here I assume that getAmount() method should return amount according to it's currency rate,
		// because converting initial amount of type Integer to Double makes no sense
		return universalValue();
	}
	
	public Integer getAmountInt() {
		Double d = getAmount() / getCurrency().getRate();
		return Integer.valueOf(df.format(d).toString().replace(".", ""));
	}
	
	public Integer getAmountInt(Double dAmount) {
		Double d = dAmount / getCurrency().getRate();
		return Integer.valueOf(df.format(d).toString().replace(".", ""));
	}
	
	/**
	 * Returns the currency of this Money.
	 * @return Currency object representing the currency of this Money
	 */
	public Currency getCurrency() {
		return this.currency;
	}
	
	/**
	 * Returns the amount of the money in the string form "(amount) (currencyname)", e.g. "10.5 SEK".
	 * Recall that we represent decimal numbers with integers. This means that the "10.5 SEK" mentioned
	 * above is actually represented as the integer 1050
	 *  @return String representing the amount of Money.
	 */
	public String toString() {
		// Here I return String according to example above "(amount) (currencyname)", e.g. "10.5 SEK".
		// Initial amount is type of Integer so it doesn't correspond example, that's why I use getAmount() method to represent amount
		return getAmount() + " " + currency.getName();
	}
	
	/**
	 * Gets the universal value of the Money, according the rate of its Currency.
	 * @return The value of the Money in the "universal currency".
	 */
	public Double universalValue() {
		return currency.universalValue(amount);
	}
	
	/**
	 * Check to see if the value of this money is equal to the value of another Money of some other Currency.
	 * @param other The other Money that is being compared to this Money.
	 * @return A Boolean indicating if the two money types are equal.
	 */
	public boolean equals(Money other) {
		// validating @param for null
		if(other != null) {
			if(isSameCurrency(other.getCurrency())) {
				return getAmount().equals(other.getAmount());
			}else {
//				Double otherVal = other.getAmount() * (other.getCurrency().getRate() / this.currency.getRate());
//				return otherVal.equals(getAmount());
				return getAmountInt() == other.getAmountInt();
			}
		}
		return false;
	}
	
	/**
	 * Adds a Money to this Money, regardless of the Currency of the other Money.
	 * @param other The Money that is being added to this Money.
	 * @return A new Money with the same Currency as this Money, representing the added value of the two.
	 * (Remember to convert the other Money before adding the amounts)
	 */
	public Money add(Money other) {
		// validating @param for null
		if(other != null) {
			if(isSameCurrency(other.getCurrency())) {
				return new Money(getAmountInt(getAmount() + other.getAmount()), this.currency);
			}else {
				return new Money(getAmountInt() + other.getAmountInt(), this.currency);
			}
		}
		return null;
	}

	//Implementation from Currency:	currency.valueInThisCurrency(other.getAmountInt(), other.getCurrency());
	
	/**
	 * Subtracts a Money from this Money, regardless of the Currency of the other Money.
	 * @param other The money that is being subtracted from this Money.
	 * @return A new Money with the same Currency as this Money, representing the subtracted value.
	 * (Again, remember converting the value of the other Money to this Currency)
	 */
	public Money sub(Money other) {
		// validating @param for null
		if(other != null) {
			if(isSameCurrency(other.getCurrency())) {
				return new Money(getAmountInt(getAmount() - other.getAmount()), this.currency);
			}else {
				return new Money(getAmountInt() - other.getAmountInt(), this.currency);
			}
		}
		return null;
	}

	/**
	 * Check to see if the amount of this Money is zero or not
	 * @return True if the amount of this Money is equal to 0.0, False otherwise
	 */
	public boolean isZero() {
		Double zero = 0.0;
		if(getAmount().equals(zero))
			return true;
		
		return false;
	}
	
	/**
	 * Negate the amount of money, i.e. if the amount is 10.0 SEK the negation returns -10.0 SEK
	 * @return A new instance of the money class initialized with the new negated money amount.
	 */
	public Money negate() {
		Double negativeAmount = getAmount() * -1;
		String newAmount = negativeAmount.toString().replace(".", "");
		
		return new Money(Integer.valueOf(newAmount), currency);
	}
	
	/**
	 * Compare two Monies.
	 * compareTo is required because the class implements the Comparable interface.
	 * (Remember the universalValue method, and that Integers already implement Comparable).
	 * Also, since compareTo must take an Object, you will have to explicitly downcast it to a Money.
	 * @return 0 if the values of the monies are equal.
	 * A negative integer if this Money is less valuable than the other Money.
	 * A positive integer if this Money is more valuiable than the other Money.
	 */
	public int compareTo(Object other) {
		Money otherMoney = (Money) other;
		
		if(isSameCurrency(otherMoney.getCurrency())) {
//			Assuming that comparing initial Integer type of amounts useless, so comparing only converted to Double amounts
//			return ((Integer) amount).compareTo((Integer) otherMoney.amount);
			return getAmount().compareTo(otherMoney.getAmount());
		} else {
			return getAmountInt().compareTo(otherMoney.getAmountInt());
		}
	}
	
	
	public boolean isSameCurrency(Currency other) {
		return other.getRate().equals(currency.getRate()) && other.getName() == currency.getName();
	}
}