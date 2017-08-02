package example.com.krishilabh_retailer.Data;

/**
 * Created by User on 7/20/2017.
 */

public class DataFpiInfo {
    String productName;


    String rate;



    public DataFpiInfo(String productName, String rate) {
        this.productName = productName;
        this.rate=rate;

    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


}
