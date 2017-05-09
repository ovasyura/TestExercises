package my.mpa.sales.msg;

import my.mpa.core.OperationType;

/**
 * Class to present general sale message of type 3
 * @author Oleh Vasyura
 *
 */
public class AdjustmentMessage extends Message {

	/*
	 * Type of operation for adjusment message
	 */
    OperationType operationType;

    public AdjustmentMessage() {}

    public AdjustmentMessage(OperationType operationType) {
        this.operationType = operationType;
    }

    public AdjustmentMessage(String type, Double salePrice, OperationType operationType) {
        super(type, salePrice);
        this.operationType = operationType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

}
