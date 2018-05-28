package yp.com.akki.ypreport.pojo.reportDetailPojo;

/**
 * Created by akshaybmsa96 on 21/01/18.
 */

public class ReportDetailItems {

        private String itemname;

        private String qty;

        private String cost_per_unit;

        private String totalcost;

        private String unit;



        public String getItemname ()
        {
            return itemname;
        }

        public void setItemname (String itemname)
        {
            this.itemname = itemname;
        }

        public String getQty ()
        {
            return qty;
        }

        public void setQty (String qty)
        {
            this.qty = qty;
        }

    public String getCost_per_unit() {
        return cost_per_unit;
    }

    public void setCost_per_unit(String cost_per_unit) {
        this.cost_per_unit = cost_per_unit;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
        public String toString()
        {
            return "ClassPojo [itemname = "+itemname+", qty = "+qty+", total = "+totalcost+"]";
        }
    }
