package my.mpa.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.mpa.sales.msg.AdjustmentMessage;
import my.mpa.sales.msg.Message;
import my.mpa.sales.product.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Engine to handle the sales message
 * 
 * @author Oleh Vasyura
 *
 */
public class SalesEngine {
	private static SalesEngine salesEngine = new SalesEngine();
	private Storage storage;

	private static final long MESSAGE_PROCESSING_CAPACITY = 50;

	private SalesEngine() {
		this.storage = new Storage();
	}

	public static SalesEngine getSalesEngine() {
		return salesEngine;
	}

	/**
	 * Initialize the SalesEngine with products from the file in csv format.
	 * Every string is the list of {@link Product} properties separated by ','.
	 * @param storageFile file that contains the storage entry
	 * @return true if initialization is success otherwise false
	 */
	public boolean initialize(File storageFile) {
		try {
			Scanner scanner = new Scanner(storageFile);
			while(scanner.hasNextLine()) {
				String storageEntry = scanner.nextLine();
				boolean productAdded = storage.addProduct(parseStorageEntry(storageEntry));

				if(!productAdded) {
					System.out.println("Incorrect storage entry.Please check the data:" + 
							storageEntry);
				}
			}
			scanner.close();
		} catch(IOException exception) {
			exception.printStackTrace();
		}

		return true;
	}

	/**
	 * Create product from string presentation.
	 * Package visibility for testing
	 * @param storageEntry product text presentation
	 * @return instance of {@link Product} or null
	 */
	Product parseStorageEntry(String storageEntry) {
		if(storageEntry == null) {
			return null;
		}

		String[] productData = storageEntry.split(",");

		if(productData.length != Product.class.getDeclaredFields().length) {
			System.out.println("Incorrect length of product data (entry). Please check data.");
			return null;
		}

		Product product = null;

		try {
			product = new Product(productData[0], //product type, string value
					Long.valueOf(productData[1]), //in stock units, long value
					Long.valueOf(productData[2]), //sold out units, long value
					Double.valueOf(productData[3])); //unit price, double value
		} catch (NumberFormatException exception) {
			System.out.println("Product count and/or pricing is incorrect. Please revise product data (entry).");
		}

		return product;
	}

	/**
	 * Create list of sales messages from input file.
	 * @param messagesFile file to present list of messages in Json format
	 * @return list of sales messages
	 */
	public List<Message> parse(File messagesFile) {
		List<Message> messages = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			messages = mapper.readValue(messagesFile, new TypeReference<List<Message>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messages;
	}

	/**
	 * Process list of messages
	 * @param messages list of message for the precessing
	 * @return true if success
	 */
	public boolean process(List<Message> messages) {
		int processedMessages = 0;
		StringBuilder adjustmentsLog = new StringBuilder();

		for(Message message : messages) {
			boolean recordsUpdated = storage.updateRecords(message);
			if(!recordsUpdated) {
				return false;
			}

			processedMessages++;

			if(message instanceof AdjustmentMessage) {
				adjustmentsLog.append("Product (");
				adjustmentsLog.append(message.getType());
				adjustmentsLog.append(") was adjusted (operation: ");
				adjustmentsLog.append(((AdjustmentMessage) message).getOperationType());
				adjustmentsLog.append(") by a value of ");
				adjustmentsLog.append(message.getSalePrice());
				adjustmentsLog.append(".\n");
			}

			if(processedMessages % 10 == 0) {
				System.out.println("\n---Intermediate Processed Sales' Record ---");
				storage.printSalesReport();
			}

			if(processedMessages == MESSAGE_PROCESSING_CAPACITY) {
				System.out.println("\nOnly limitedt count "
						+ MESSAGE_PROCESSING_CAPACITY + " were processed. Process was stopped.");
				break;
			}
		}

		/* It is necessary to print additionally the final sales report. 
		 * Otherwise notifications % 10 = 4 will not be printed.
		 */
		System.out.println("\n--- Final Processed Sales' Record ---");
		storage.printSalesReport();

		if(adjustmentsLog.length() != 0) {
			System.out.println("\n--- Adjustment Log ---");
			System.out.println(adjustmentsLog.toString());
		}

		return true;
	}
}