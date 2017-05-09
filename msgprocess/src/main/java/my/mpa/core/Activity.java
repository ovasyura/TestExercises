/**
 * 
 */
package my.mpa.core;

import my.mpa.sales.msg.AdjustmentMessage;

/**
 * Class to present activity operation for the product according sale message
 * @author Oleh Vasyura
 *
 */
public class Activity {

	/*
	 * 
	 */
	private double value;
	private ActivityStatus activityStatus;

	public Activity() {
		this.activityStatus = ActivityStatus.NOT_ADJUSTED;
	}

	public Activity(double value) {
		this.activityStatus = ActivityStatus.NOT_ADJUSTED;
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public ActivityStatus getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(ActivityStatus activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void adjustActivity(AdjustmentMessage msg) {
		switch(msg.getOperationType()) {
		case ADD:
			this.value += msg.getSalePrice();
			this.activityStatus = ActivityStatus.ADJUSTED;
			break;
		case MULTIPLY:
			this.value *= msg.getSalePrice();
			this.activityStatus = ActivityStatus.ADJUSTED;
			break;
		case SUBTRACT:
			if (this.value < msg.getSalePrice()) {
				System.out.println("Potential lost detected [Product: " + msg.getType() +
						" Existing value: " + this.value + 
						" Sale price: " + msg.getSalePrice() + 
						", Adjustment operation: SUBSTRACT]");
			}
			break;
		default:
			System.out.println("Unsupported operation: " + msg.getOperationType());
			break;
		}

	}

	@Override
	public String toString() {
		return "Activity [value=" + value + ", activityStatus=" + activityStatus + "]";
	}
	
}
