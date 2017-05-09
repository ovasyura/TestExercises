package my.mpa.sales.msg;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class to present general sale message of type 1 according specification.
 * 
 * Message will be presented in Json format.
 * Json mapper {@link ObjectMapper} can easily convert the message to correct object.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DetailMessage.class),
        @JsonSubTypes.Type(value = AdjustmentMessage.class)
})
public class Message {
	
	/*
	 * Name of the product. 
	 * Engine will identify the product using this property
	 */
    private String type;
    /*
     * Price of the market operation
     */
    private double salePrice;

    public Message() {
    }

    public Message(String type, Double salePrice) {
        this.type = type;
        this.salePrice = salePrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

	@Override
	public String toString() {
		return "Message [type=" + type + ", salePrice=" + salePrice + "]";
	}
    
}
