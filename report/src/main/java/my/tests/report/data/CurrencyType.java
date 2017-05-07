package my.tests.report.data;

/**
 * Enum to present type of currency
 */
public enum CurrencyType {
	AED,
	SGP,
	USD;
	
	public static CurrencyType fromValue(String value) {
		switch(value) {
		case"AED":
			return AED;
		case"SGP":
			return SGP;
		default:
			break;
		}
		return USD;
	}
	
}
