/**
 * 
 */
package my.mpa.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import my.mpa.sales.msg.AdjustmentMessage;
import my.mpa.sales.msg.DetailMessage;
import my.mpa.sales.msg.Message;
import my.mpa.sales.product.Product;

/**
 * Class to present the storage of the all operations made on the product according the sales messages
 * 
 * @author Oleh Vasyura
 *
 */
public class Storage {

	/*
	 * Map of all activities after message handling
	 */
    Map<Product, List<Activity>> records;

    public Storage() {
        this.records = new HashMap<>();
    }

    public Storage(Map<Product, List<Activity>> records) {
        this.records = records;
    }

    public Map<Product, List<Activity>> getRecords() {
        return records;
    }
	
    /**
     * Add product to the market.
     * Product can be added only one time
     * @param product
     * @return false if incorrect product or already added otherwise true 
     */
    public boolean addProduct(Product product) {
        if(product == null || records.containsKey(product)) {
            return false;
        }

        records.put(product, new ArrayList<>());
        return true;
    }
    
    /*
     * Update the storage according sales message
     */
    public boolean updateRecords(Message message) {
        if(message == null) {
            System.out.println("Message cannot be null.");
            return false;
        }

        Product product = findProduct(message.getType());
        if(product == null) {
            System.out.println("Invalid sales record. Product is not in the storage.");
            return false;
        }

        List<Activity> activities = records.get(product);

        if(message instanceof AdjustmentMessage) {
        	final AdjustmentMessage msg = (AdjustmentMessage)message;
        	activities.forEach(a -> a.adjustActivity(msg));
        } else {
        	activities = addNewActivities(activities, message);
        }

        records.put(product, activities);
        return true;
    }

	private List<Activity> addNewActivities(List<Activity> activities, Message message) {
		long count = 1;
		if (message instanceof DetailMessage) {
			count = ((DetailMessage)message).getSalesCount();
		}
		for (long i = 0; i < count; i ++) {
			activities.add(new Activity(message.getSalePrice()));			
		}
		return activities;
	}

	private Product findProduct(String productType) {
        Optional<Product> product = records.keySet().stream().filter( pt -> productType.equals(pt.getType())).findFirst();

        if (product.isPresent()) {
        	return product.get();
        }

        return null;
    }

    public void printSalesReport() {
    	records.forEach((p,a) ->
        System.out.println("Product type: " + p.getType() +
                ", Total units sold: " + a.size() +
                ", Revenue generated: " + getRevenueForProduct(a)));
    }

	private double getRevenueForProduct(List<Activity> pActivities) {
		double revenue = pActivities.stream().mapToDouble(a -> a.getValue()).sum();
		return revenue;
	}
	
    
}
