package my.mpa.sales.product;

/**
 * Class to define the product available on the market.
 * 
 * @author Oleh Vasyura
 *
 */
public class Product {
	
	/*
	 * Product's type define as name
	 */
    private String type;
    /*
     * Available amount on the market
     */
    private long inMarket;
    /*
     * Represent count of sold units
     */
    private long soldOut; 
    /*
     * Unit price
     */
    private Double unitPrice;

    public Product() {
    }

    public Product(String type, long inMarket, long soldOut, Double unitPrice) {
        this.type = type;
        this.inMarket = inMarket;
        this.soldOut = soldOut;
        this.unitPrice = unitPrice;
    }

    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public long getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(long soldOut) {
        this.soldOut = soldOut;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

	public long getInMarket() {
		return inMarket;
	}

	public void setInMarket(long inMarket) {
		this.inMarket = inMarket;
	}

	@Override
	public String toString() {
		return "Product [type=" + type + ", inMarket=" + inMarket + ", soldOut=" + soldOut + ", unitPrice=" + unitPrice
				+ "]";
	}
	
	
}
