/**
 * 
 */
package my.tests.report.data;

/**
 * Class to present currency in the system.
 * @author Oleh vasyura
 *
 */

public class Currency {

	/*
	 * Currency type
	 */
	private CurrencyType type = CurrencyType.USD;
	
	/*
	 * Exchange rate with respect to USD
	 */
	private double exRate;
	
	public Currency() {
		
	}
	
	public Currency(CurrencyType type, double exRate) {
		this.type = type;
		this.exRate = exRate;
	}
	
	public CurrencyType getType() {
		return type;
	}

	public void setType(CurrencyType type) {
		this.type = type;
	}

	public double getExRate() {
		return exRate;
	}

	public void setExRate(double exRate) {
		this.exRate = exRate;
	}

	@Override
	public String toString() {
		return "Currency [type=" + type + ", exRate=" + exRate + "]";
	}
	
	
}


