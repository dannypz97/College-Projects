package ims.orders;


public class Orders {
    private String order_id;
    private String time_of_order;
    private String vendor_id;
    private String status;
    
    public Orders(String order_id,String time_of_order, String vendor_id, String status)
    {
        this.order_id = order_id;
        this.time_of_order = time_of_order;
        this.vendor_id = vendor_id;
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime_of_order() {
        return time_of_order;
    }

    public void setTime_of_order(String time_of_order) {
        this.time_of_order = time_of_order;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
