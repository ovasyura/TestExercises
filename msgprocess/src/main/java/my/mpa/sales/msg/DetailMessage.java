package my.mpa.sales.msg;

/**
 * Class to present general sale message of type 2
 * @author Oleh Vasyura
 *
 */
public class DetailMessage extends Message {

	/*
	 * Number of occurrences of the sale for special products.
	 * Current version assumes that sales cannot be less than 0.
	 * In general we need use javax.validation.constraints annotations 
	 */
    private long salesCount;

    public DetailMessage() {}

    public DetailMessage(long salesCount) {
        this.salesCount = salesCount;
    }

    public DetailMessage(String type, Double salePrice, long salesCount) {
        super(type, salePrice);
        this.salesCount = salesCount;
    }

    public long getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(long salesCount) {
        this.salesCount = salesCount;
    }

}
