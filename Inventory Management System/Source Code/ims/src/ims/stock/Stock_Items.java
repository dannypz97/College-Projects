/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.stock;


public class Stock_Items {
    private String sku;
    private String delivery_ref_id;
    private Integer quantity;
    private String dept_code;

    public Stock_Items(String sku, String delivery_ref_id, Integer quantity, String dept_code) {
        this.sku = sku;
        this.delivery_ref_id = delivery_ref_id;
        this.quantity = quantity;
        this.dept_code = dept_code;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDelivery_ref_id() {
        return delivery_ref_id;
    }

    public void setDelivery_ref_id(String delivery_ref_id) {
        this.delivery_ref_id = delivery_ref_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }
    
    
    
}
