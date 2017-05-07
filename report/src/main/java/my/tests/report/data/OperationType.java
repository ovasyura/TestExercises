/**
 * 
 */
package my.tests.report.data;

/**
 * Type of settlement instruction.
 * Current version support two type of operations:
 *  - buy
 *  - sell
 *
 */
public enum OperationType {

	/*
	 * Buy shares
	 */
	BUY, 
	/*
	 * Sell shares
	 */
	SELL,
	/*
	 * Undefined operation
	 */
	UNDEFINED;
	
	public static OperationType fromValue(String value) {
		switch(value) {
		case"B":
		case"b":
			return BUY;
		case"S":
		case"s":
			return SELL;
		default:
			break;
		}
		return UNDEFINED;
	}
}
