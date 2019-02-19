
package ims.delivery;

public class WarehouseDeliveryItems {
   private String item_name;
   private Integer quantity;    
   private String units;
   private Double batch_price;
   private String product_type_code;

    public WarehouseDeliveryItems(String item_name, Integer quantity, String units, double batch_price, String product_type_code) {
        this.item_name = item_name;
        this.quantity = quantity;
        this.units = units;
        this.batch_price = batch_price;
        this.product_type_code = product_type_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getBatch_price() {
        return batch_price;
    }

    public void setBatch_price(double batch_price) {
        this.batch_price = batch_price;
    }

    public String getProduct_type_code() {
        return product_type_code;
    }

    public void setProduct_type_code(String product_type_code) {
        this.product_type_code = product_type_code;
    }
   
   
}
