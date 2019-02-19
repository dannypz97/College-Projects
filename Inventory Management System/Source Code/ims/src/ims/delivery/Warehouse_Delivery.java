
package ims.delivery;


public class Warehouse_Delivery {
    private String delivery_id;
    private String time_of_delivery;
    private String order_ref_id;
    private String status;

    public Warehouse_Delivery(String delivery_id, String time_of_delivery, String order_ref_id, String status) {
        this.delivery_id = delivery_id;
        this.time_of_delivery = time_of_delivery;
        this.order_ref_id = order_ref_id;
        this.status = status;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getTime_of_delivery() {
        return time_of_delivery;
    }

    public void setTime_of_delivery(String time_of_delivery) {
        this.time_of_delivery = time_of_delivery;
    }

    public String getOrder_ref_id() {
        return order_ref_id;
    }

    public void setOrder_ref_id(String order_ref_id) {
        this.order_ref_id = order_ref_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
