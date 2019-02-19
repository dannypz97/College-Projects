
package ims.orders;

public class Order_items {
    private String product_name;
    private Integer quantity;
    private String units;
    

    public Order_items(String product_name, Integer quantity, String units) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.units = units;
        
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    
    
    
    
}
