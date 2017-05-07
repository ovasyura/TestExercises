package my.tests.report;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import my.tests.report.data.CurrencyType;
import my.tests.report.data.DataGenerator;
import my.tests.report.data.Settlement;

/**
 * 
 * Simple test class to test the common functionality.
 * Unfortunately the tests don't cover all possible cases and should be extended.
 * @author Oleh Vasyura
 *
 */

public class DataGeneratorTest {

	@Test
	public void testGenerateData() {
		List<Settlement> settlements = DataGenerator.generateData();
		assertNotNull(settlements);
		assertEquals(10, settlements.size());
	}

	@Test
	public void testBuildSettlement() {
		Settlement st = DataGenerator.buildSettlement("foo;B;0.50;SGP;01-01-2016;02-01-2016;200;100.25");
		assertNotNull(st);
	}

	@Test
	public void testGetSettlementDay() {
		LocalDate testDate = DataGenerator.getSettlementDay("01-01-2016", CurrencyType.USD);
		assertEquals(LocalDate.of(2016, 01, 01), testDate);
		testDate = DataGenerator.getSettlementDay("02-01-2016", CurrencyType.USD);
		assertEquals(LocalDate.of(2016, 01, 04), testDate);
		testDate = DataGenerator.getSettlementDay("03-01-2016", CurrencyType.USD);
		assertEquals(LocalDate.of(2016, 01, 04), testDate);
		testDate = DataGenerator.getSettlementDay("01-01-2016", CurrencyType.AED);
		assertEquals(LocalDate.of(2016, 01, 03), testDate);
		testDate = DataGenerator.getSettlementDay("02-01-2016", CurrencyType.AED);
		assertEquals(LocalDate.of(2016, 01, 03), testDate);
		testDate = DataGenerator.getSettlementDay("01-01-2016", CurrencyType.SGP);
		assertEquals(LocalDate.of(2016, 01, 03), testDate);
		testDate = DataGenerator.getSettlementDay("02-01-2016", CurrencyType.SGP);
		assertEquals(LocalDate.of(2016, 01, 03), testDate);
	}

}
