package my.tests.report;

import java.time.LocalDate;
import java.util.List;

import my.tests.report.data.DataGenerator;
import my.tests.report.data.Settlement;

public class Application {

	/*
	 * List of clients' instructions come from the customers to execute in the market.
	 * 
	 */
	public static List<Settlement> marketInstructions;

	public static void main(String[] args) {
		
		//create test data
		marketInstructions = DataGenerator.generateData();
		
		//generate the report
		ReportEngine.generateReport(LocalDate.of(2016, 01, 01), LocalDate.of(2016, 01, 10));
	}

}
