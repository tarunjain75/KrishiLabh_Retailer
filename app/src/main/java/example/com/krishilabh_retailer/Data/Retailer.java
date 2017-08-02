package example.com.krishilabh_retailer.Data;

/**
 * Created by tarun on 2/4/17.
 */

public class Retailer {

    String productName;
    int quantity;
    int basePrice;

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
