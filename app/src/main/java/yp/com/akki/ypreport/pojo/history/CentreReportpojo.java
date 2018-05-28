package yp.com.akki.ypreport.pojo.history;

/**
 * Created by akshaybmsa96 on 21/01/18.
 */

public class CentreReportpojo {

  //  private String error;
  //  private ArrayList<ItemUsagePojo> items;
    private String sale;
    private String orders;
    private String materialCost;
/*
    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public ArrayList<ItemUsagePojo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemUsagePojo> items) {
        this.items = items;
    }
    */

    public String getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public String getSale ()
    {
        return sale;
    }

    public void setSale (String sale)
    {
        this.sale = sale;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sale = "+sale+"]";
    }
}


