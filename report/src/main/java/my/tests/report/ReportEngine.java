/**
 * 
 */
package my.tests.report;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import my.tests.report.data.OperationType;
import my.tests.report.data.Settlement;

/**
 * Class to generate operations report in the international market.
 * The report is printed to standard output in the following format:
 *	Day: 2016-01-07
 *		Income:196602.00
 *			Entity:bar3 rank:1.00
 *			Entity:bar2 rank:0.59
 *			Entity:bar rank:0.13
 *		Outcome:113977.50
 *			Entity:bar3 rank:1.00
 * @author Oleh Vasyura
 *
 */
public class ReportEngine {

	/**
	 * Generate report of the incoming and outgoing settlement for period of date.
	 * This method use {@link Application#marketInstructions} as a data and assumes that it cannot be null.
	 * It assumes also that all properties are set correctly in settlement.
	 * @param startDay
	 * @param endDay
	 */
	public static void generateReport(LocalDate startDay, LocalDate endDay) {
		if (endDay.compareTo(startDay) < -1) {
			System.out.println("Incorrect end day. It cannot be less than start day");
			return;
		}

		LocalDate day = startDay;
		while(endDay.compareTo(day) >= 0) {
			final LocalDate curDay = day;
			System.out.println("Day: " + day);
			//Calculate and output income for the current day
			dayInstructionsReport("\tIncome:", Application.marketInstructions.stream().
					filter(st -> st.getSettlementDate().compareTo(curDay) == 0 && st.getOperation() == OperationType.SELL).
					collect(Collectors.toList()));

			dayInstructionsReport("\tOutcome:", Application.marketInstructions.stream().
					filter(st -> st.getSettlementDate().compareTo(curDay) == 0 && st.getOperation() == OperationType.BUY).
					collect(Collectors.toList()));
			day = day.plusDays(1);
		}

	}

	/**
	 * Generate report for income or outcome instructions for one day.
	 * @param description escription of the instruction:
	 * Possible cases:
	 * 	- Outcome
	 *  - Income
	 * @param settlements list of instructions of one of special types:
	 *  - {@link OperationType#BUY}
	 *  - {@link OperationType#SELL}
	 */
	private static void dayInstructionsReport(String description, List<Settlement> settlements) {
		if (!settlements.isEmpty()) {
			System.out.println(String.format("%s%.02f", description,
					settlements.stream().mapToDouble(st -> st.getPrice()).sum()));
			//filter all items by rank and display
			settlements.sort((a, b) -> (
					Double.compare(b.getPrice(),
							a.getPrice())));
			final double maxValue = settlements.get(0).getPrice();
			settlements.forEach(st -> settlementOutput(st, maxValue));
		} else {
			System.out.println(String.format("%s%.2f", description,0.0));
		}
	}

	/**
	 * Generate output for one instruction
	 * @param st settlement
	 * @param maxValue max value of the entity's price for one day
	 */
	private static void settlementOutput(Settlement st, double maxValue) {
		System.out.println(String.format("\t\tEntity:%s rank:%.02f", st.getEntity(), st.getPrice()/maxValue));
	}


}
