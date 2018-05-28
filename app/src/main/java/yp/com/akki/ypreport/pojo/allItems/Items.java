package yp.com.akki.ypreport.pojo.allItems;

/**
 * Created by akshaybmsa96 on 19/01/18.
 */

public class Items {
    private String id;
    private String itemName;
    private String unit;

    private String _id;
    private String purchasePrice;
    private String sellingPrice;
    private String costingPrice;
    private String tax;

    private String centreId;
    private String centreAdminId;
    private String centre;






    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getItemName ()
    {
        return itemName;
    }

    public void setItemName (String itemName)
    {
        this.itemName = itemName;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCostingPrice() {
        return costingPrice;
    }

    public void setCostingPrice(String costingPrice) {
        this.costingPrice = costingPrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCentreId() {
        return centreId;
    }

    public void setCentreId(String centreId) {
        this.centreId = centreId;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public String getCentreAdminId() {
        return centreAdminId;
    }

    public void setCentreAdminId(String centreAdminId) {
        this.centreAdminId = centreAdminId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", itemName = "+itemName+"]";
    }
}
