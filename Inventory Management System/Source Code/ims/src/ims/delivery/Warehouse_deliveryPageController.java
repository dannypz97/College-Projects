/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.delivery;

import static ims.Ims.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class Warehouse_deliveryPageController implements Initializable {
    
    public static String d_id; //to store delivery_id of row selected.
    public static boolean alreadyExists=false; //is order already 'Stocked'?
    
    @FXML
    private TableView<Warehouse_Delivery> table;

    @FXML
    private TableColumn<Warehouse_Delivery, String> c1;

    @FXML
    private TableColumn<Warehouse_Delivery, String> c3;
    
    @FXML
    private TableColumn<Warehouse_Delivery, String> c4;
    
    @FXML
    private TableColumn<Warehouse_Delivery, String> c5;
    

    @FXML
    void backToPanel(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ims/mainScreen/MainScreen.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void viewDetails(ActionEvent event) throws Exception {
        if(table.getSelectionModel().getSelectedItem()!=null)
        {
        d_id=table.getSelectionModel().getSelectedItem().getDelivery_id();
        if(table.getSelectionModel().getSelectedItem().getStatus().equals("Stocked"))
            alreadyExists = true;
        else alreadyExists = false;
        System.out.println(d_id);
        Parent root = FXMLLoader.load(getClass().getResource("/ims/delivery/Items_Page.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Delivery Items");
        stage.show();
        }
    }
    
    private void populateTable()
    {
        c1.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));
        c3.setCellValueFactory(new PropertyValueFactory<>("time_of_delivery"));
        c4.setCellValueFactory(new PropertyValueFactory<>("order_ref_id"));
        c5.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(getData());
    }
    
    private ObservableList<Warehouse_Delivery> getData()
    {
        ObservableList<Warehouse_Delivery> list = FXCollections.observableArrayList();
         try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                        
            String query= "select * from warehouse_delivery order by delivery_id asc";
            Statement st = db.createStatement();
            
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next())
            {
                list.add(new Warehouse_Delivery(rs.getString("delivery_id"), rs.getString("time_of_delivery"),rs.getString("order_ref_id"),rs.getString("status")));
                                
            }      
          
           db.close();
           st.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        }  
        
        return list;
    }
    
    
    @FXML
    void moveToStock(ActionEvent event) throws Exception {
            
            if(table.getSelectionModel().getSelectedItem()!=null && table.getSelectionModel().getSelectedItem().getStatus().toLowerCase().equals("arrived"))
            {
            
            d_id=table.getSelectionModel().getSelectedItem().getDelivery_id();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Move items under " + d_id + " to stock?");
            alert.setContentText("Once items have been moved to stock, status will be set to 'Stocked'. This"
                    + " can't be undone directly."+
                    " Contact DBA is case of problems.");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
            {
            try
            {
                   

            
                   Class.forName("oracle.jdbc.OracleDriver");
                    Connection db=DriverManager.getConnection(url,db_uname,db_pass);

                    String query;

                    Statement st;
                    
                    ResultSet rs;
                    
                    //check for invalid entries in table
                    query = "select * from warehouse_delivery_items where delivery_id=? and ((quantity>0"
                            + " and batch_price=0)"
                            + " or product_type_code not in (select department_code from department)) ";
                       PreparedStatement st2 = db.prepareStatement(query);
                       st2.setString(1,d_id);
                       rs=st2.executeQuery();
                    if(rs.next())
                    {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("WARNING");
                        alert.setHeaderText("Are you sure all your item fields are accurate?");
                        alert.setContentText("We found some probelms with some of your item entries. "
                                + "Please check your entries and try again. Aborting transaction...");
                        alert.showAndWait();
                        return;
                    }
                       query ="select * from warehouse_delivery_items where delivery_id = ? and quantity>0 and delivery_id not in (select delivery_ref_id from stock_items)";
                        st2 = db.prepareStatement(query);
                       st2.setString(1,d_id);
                       rs=st2.executeQuery();                       
                       
                                             
                       while(rs.next())
                       {
                          
                        String itemName = rs.getString("ITEM_NAME");
                        if(itemName.charAt(0)!='!') //item not defective and can be moved into stock.
                        {
                          //first get vendor name
                          String vendorName = getVendorName(table.getSelectionModel().getSelectedItem().getOrder_ref_id());
                            System.out.println(vendorName);
                          String orderID = table.getSelectionModel().getSelectedItem().getOrder_ref_id();
                            System.out.println(orderID);
                          String productCode = getProductTypeCode(itemName);
                         
                         
                         String quantity = getQuantity(itemName);
                         String currentTimeStamp = new SimpleDateFormat("dd-MMM-YY HH:mm").format(Calendar.getInstance().getTime());
                         String sku = generateSKU(vendorName, orderID, itemName, quantity, currentTimeStamp); 
                         
                         //now move item to stock
                         query = "insert into stock_items values(?,?,?,?)";
                         st2 = db.prepareStatement(query);
                         st2.setString(1,sku);
                         st2.setString(2,d_id);
                         st2.setString(3,quantity);
                         st2.setString(4,productCode);
                         
                         st2.execute();
                         
                         query = "update warehouse_delivery set status = ? where delivery_id=?";
                         st2 = db.prepareStatement(query);
                         st2.setString(1,"Stocked");
                         st2.setString(2,d_id);
                         st2.execute();
                         
                                                  
                         query = "update orders set status = ? where order_id=?";
                         st2 = db.prepareStatement(query);
                         st2.setString(1, "Completed");
                         st2.setString(2, table.getSelectionModel().getSelectedItem().getOrder_ref_id());
                         st2.execute();
                        }
                       
                      
                      }
                   

            }
            catch(Exception e)
            {
                e.printStackTrace();

            } 
            }
            }
            
            
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTable();
    }    
    
    private String generateSKU(String vendorName, String orderID, String itemName,String qty, String  x)
    {
     return  vendorName + "/" + orderID + "/"  + itemName + "/" + qty + "/\'"+ x + "\'";
    }
    
    private String getVendorName(String order_id)
    {
        String query;
        Statement st;
        PreparedStatement st2;
        ResultSet rs;
        try
        {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                          
                        //first get vendor_id associated with order_id
                          query = "select vendor_id from orders where order_id=?";
                          st2 = db.prepareStatement(query);
                          st2.setString(1, order_id);
                          
                          rs = st2.executeQuery();
                          rs.next();
                          
                          //now get vendor name matching vendor_id
                          query = "select vendor_name from vendor where vendor_id=?";
                          st2 = db.prepareStatement(query);
                          st2.setString(1, rs.getString(1));
                          
                          rs = st2.executeQuery();
                          rs.next();
                          return rs.getString(1);
        }
        catch(Exception e)
        {
            
        }
        return "...";
    }
    
    private String getProductTypeCode(String itemName)
    {
        String query;
        Statement st;
        PreparedStatement st2;
        ResultSet rs;
        try
        {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                                              //now get vendor_name using vendor_id
                          query = "select product_type_code from warehouse_delivery_items where item_name=?";
                          st2 = db.prepareStatement(query);
                          st2.setString(1,itemName);
                          
                          rs = st2.executeQuery();
                          rs.next();
                          return rs.getString(1);
        }
        catch(Exception e)
        {
            
        }
        return "...";   
    }
    
    private String getQuantity ( String itemName)
    {
        String query;
        Statement st;
        PreparedStatement st2;
        ResultSet rs;
        try
        {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                                              //now get vendor_name using vendor_id
                          query = "select quantity from warehouse_delivery_items where item_name=?";
                          st2 = db.prepareStatement(query);
                          st2.setString(1,itemName);
                          
                          rs = st2.executeQuery();
                          rs.next();
                          return rs.getString(1);
        }
        catch(Exception e)
        {
            
        }
        return "...";   
    
    }
    
     @FXML
    void addDelivery(ActionEvent event) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/ims/delivery/AddDelivery.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New Delivery");
        stage.show();
    }
    
    @FXML
    
    void deleteDelivery(ActionEvent event) throws Exception
    {
             if(table.getSelectionModel().getSelectedItem().getStatus().equals("Arrived"))
             {
                 
                 
            try
            {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                    
                    String query = "select status from orders where order_id=?";
                    PreparedStatement st = db.prepareStatement(query);
                    st.setString(1, table.getSelectionModel().getSelectedItem().getOrder_ref_id());
                    ResultSet rs = st.executeQuery();
                    rs.next();
                    if(rs.getString(1).equals("Pending") || rs.getString(1).equals("Placed"))
                    {
                    
                    query= "delete from warehouse_delivery_items where delivery_id=? ";

                    st = db.prepareStatement(query);
                    st.setString(1, table.getSelectionModel().getSelectedItem().getDelivery_id());
                    st.execute();
                    
                    query= "delete from warehouse_delivery where delivery_id=?";

                    st = db.prepareStatement(query);
                    st.setString(1, table.getSelectionModel().getSelectedItem().getDelivery_id());
                    st.execute();
                    
                    st.close();
                    db.close();
                    populateTable();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setHeaderText("Can't delete this entry. Contact DBA for more help.");


                        alert.showAndWait();
                    }

            }
            catch(Exception e)
            {
                e.printStackTrace();

            } 
             }
    }
    
    
    @FXML
    void refresh(ActionEvent event) {
        populateTable();
    }

    
}
