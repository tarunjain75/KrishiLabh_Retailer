package example.com.krishilabh_retailer.Data;

/**
 * Created by User on 8/1/2017.
 */

public class PaymentDetails {




    String firm;
    String amount;

    public PaymentDetails(String firm, String amount) {
        this.firm = firm;
        this.amount = amount;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return String.format(firm);
    }

}
