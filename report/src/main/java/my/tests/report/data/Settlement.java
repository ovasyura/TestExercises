/**
 * 
 */
package my.tests.report.data;

import java.time.LocalDate;

/**
 * Class to present the client's instruction for buying or selling shares in the international market.
 * In real environment it is coming from some DB. 
 * Current tests version of the report environment assumes that list of the instructions 
 * are filled during startup of applications and stored in shared memory.
 * @author Oleh Vasyura
 *
 */
public class Settlement {

	/*
	 * String presentation of financial entity.
	 * It is simple name of share 
	 */
	private String entity;
	
	/*
	 * Operation for the instruction
	 */
	private OperationType operation;

	/*
	 * Currency for the operation.
	 * By default it is USD
	 */
	private Currency currency = new Currency(CurrencyType.USD, 1.0);

	/*
	 * Date when instruction comes to the system 
	 */
	private LocalDate instructionDate;
	
	/*
	 * Date when instruction to be settled
	 */
	private LocalDate settlementDate;
	
	/*
	 * Number of shares to be bought or sold
	 */
	private int units;
	
	/*
	 * Price per unit
	 */
	private double unitPrice;
	
	public Settlement() {
		
	}
	
	public Settlement(String entity, OperationType operation, int units, double unitPrice) {
		this.entity = entity;
		this.operation = operation;
		this.units = units;
		this.unitPrice = unitPrice;
		//instruction date is set to the current date
		instructionDate = LocalDate.now();
		settlementDate = LocalDate.now();
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public OperationType getOperation() {
		return operation;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * Calculate price of settlement as: Price per unit * Units * AgreedFx
	 * @return common settlement price
	 */
	public double getPrice() {
		if (currency != null) {
			return units * unitPrice * currency.getExRate();
		}
		return units * unitPrice;
	}
	
	@Override
	public String toString() {
		if (currency != null) {
			return " [entity=" + entity + ", price=" + (units * unitPrice * currency.getExRate()) + "]";
		}
		return " [entity=" + entity + ", price=" + (units * unitPrice ) + "]";
	}
}
