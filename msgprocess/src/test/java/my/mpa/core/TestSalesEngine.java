package my.mpa.core;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import my.mpa.sales.msg.Message;
import my.mpa.sales.product.Product;

public class TestSalesEngine {

	private SalesEngine engine;
	@Before
	public void setUp() {
		engine = SalesEngine.getSalesEngine();
	}

	@Test
	public void testInitialize() {
		String storageFile = "testproducts.csv";
		//load file from the test resource
		URL testData = this.getClass().getClassLoader().getResource(storageFile);
		assertNotNull(testData);
		assertTrue(engine.initialize(new File(testData.getFile())));
	}

	@Test
	public void testParseStorageEntry() {
		String correctEntry = "potato,500,200,1.5";
		Product product = engine.parseStorageEntry(correctEntry);
		assertNotNull(product);
		assertEquals("potato", product.getType());
		assertEquals(500, product.getInMarket());
		assertEquals(200, product.getSoldOut());
		assertEquals(Double.valueOf(1.5), product.getUnitPrice());
		String inCorrectEntry = "potato,500,200,1.5,6.7";
		product = engine.parseStorageEntry(inCorrectEntry);
		assertNull(product);
	}

	@Test
	public void testProcessMessage() {
		//engine initialization
		testInitialize();
		//load messages
		String messageFile = "testmessages.json";
		//load file from the test resource
		URL testData = this.getClass().getClassLoader().getResource(messageFile);
		assertNotNull(testData);
		List<Message> messages = engine.parse(new File(testData.getFile()));
		assertNotNull(messages);
		assertEquals(4, messages.size());
		assertTrue(engine.process(messages));
	}

}
