package example.com.krishilabh_retailer.Data;

/**
 * Created by User on 7/29/2017.
 */

public class Notify {




    String status;
    String cost;
    String fpi;
    String retail;


    public Notify(String status, String cost, String fpi,String retail) {
        this.status = status;
        this.cost = cost;
        this.fpi = fpi;
        this.retail=retail;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRetail() {
        return retail;
    }

    public void setRetail(String retail) {
        this.retail = retail;
    }

    public String getFpi() {
        return fpi;
    }

    public void setFpi(String fpi) {
        this.fpi = fpi;
    }



}
