package yp.com.akki.ypreport.pojo.setItems;

import java.util.ArrayList;

/**
 * Created by akshaybmsa96 on 19/01/18.
 */

public class SetItemsPojo {


    private String id;
    private String centre;
    private String centreId;
    private String centreAdminId;
    private String date;
    private String orders;
    private String sale;
    private String materialCost;
    private ArrayList<SetItems> itemUsage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public String getCentreId() {
        return centreId;
    }

    public void setCentreId(String centreId) {
        this.centreId = centreId;
    }

    public String getCentreAdminId() {
        return centreAdminId;
    }

    public void setCentreAdminId(String adminId) {
        this.centreAdminId = adminId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public ArrayList<SetItems> getItemUsage() {
        return itemUsage;
    }

    public void setItemUsage(ArrayList<SetItems> itemUsage) {
        this.itemUsage = itemUsage;
    }
}
