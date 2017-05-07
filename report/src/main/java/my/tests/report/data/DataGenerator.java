/**
 * 
 */
package my.tests.report.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to generate the instructions to execute in the international markets
 * @author Oleh Vasyura
 *
 */
public class DataGenerator {
	
	/*
	 * Separator for settlement data presentation 
	 */
	private static final String SEPARATOR = ";";
	/*
	 * Expected count off items in string presentation of settlement data
	 */
	private static final int EXPECTED_ITEMS_COUNT = 8;
	/*
	 * Default count of instructions.
	 */
	private static final int INSTRUCTIONS_COUNT = 200;
	
	/**
	 * Generate test data from the resource
	 * @return list of settlement instructions ordered by settlement date in ascending order
	 */
	public static List<Settlement> generateData() {
		ArrayList<Settlement> instructions = new ArrayList<Settlement>(INSTRUCTIONS_COUNT);
		
		//read test data from resource file
		URL testData = DataGenerator.class.getClassLoader().getResource("settlement.txt");
		File file = new File(testData.getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Settlement settlement = buildSettlement(line);
				if (settlement != null) {
					instructions.add(settlement);
				}
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}		
		//order by settlement date
		instructions.sort(  (a, b) -> a.getSettlementDate().compareTo(b.getSettlementDate()));
		return instructions; 
	}
	
	/**
	 * Method to build one settlement from the string presentation of the object.
	 * String should present as list of properties in correct format separated by ';'.
	 * Example:
	 *   - "foo;B;0.50;SGP;01-01-2016;02-01-2016;200;100.25"
	 * @param presentation String presentation of the one settlement from the client
	 * @return  instance of {@link Settlement} instance or null if some error in the data
	 */
	public static Settlement buildSettlement(String presentation) {
		Settlement instruction = null;
		String[] data = presentation.split(SEPARATOR);
		if (data.length != EXPECTED_ITEMS_COUNT) {
			System.out.println("Incorrect test data. Items' count is not " + EXPECTED_ITEMS_COUNT);
			return null;
		}
		
		instruction = new Settlement();
		try {
		instruction.setEntity(data[0]);
		instruction.setOperation(OperationType.fromValue(data[1]));
		
		double exRate = 1.0;
		CurrencyType cType = CurrencyType.fromValue(data[3]);
		//We ignore rate for USD currency
		if (cType != CurrencyType.USD) {
			exRate = Double.valueOf(data[2]);
 		}
		instruction.setCurrency(new Currency(cType, exRate));
		instruction.setInstructionDate(LocalDate.parse(data[4], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		instruction.setSettlementDate(getSettlementDay(data[5], cType));
		instruction.setUnits(Integer.parseInt(data[6]));
		instruction.setUnitPrice(Double.parseDouble(data[7]));
		} catch(NumberFormatException  | DateTimeParseException ex) {
			System.out.println("Incorrect test data: " + ex.getMessage());
			return null;
		}
		
		return instruction;
	}

	/**
	 * Create settlement day from input string.
	 * It also change the date depending from currency if settlement date falls on a weekend.
	 * @param sDate date's string presentation in format "dd-MM-yyyy"
	 * @param cType currency type
	 * @return settlement day as instance of {@link LocalDate}instance of settlement day
	 */
	public static LocalDate getSettlementDay(String sDate, CurrencyType cType) throws DateTimeParseException{
		LocalDate date = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		//check settlement date
		DayOfWeek sDay = date.getDayOfWeek();
		long plusDays = 0;
		switch(sDay) {
		case FRIDAY:
			if(cType == CurrencyType.AED || cType == CurrencyType.SGP ) {
				plusDays = 2;
			}
			break;
		case SUNDAY:
			if (cType == CurrencyType.USD) {
				plusDays = 1;
			}
			break;
		case SATURDAY:
			if (cType == CurrencyType.AED || cType == CurrencyType.SGP ) {
				plusDays = 1;
			} else {
				plusDays = 2;
			}
			break;
		default:
			break;
		}
		return date.plusDays(plusDays);
	}
	
}
