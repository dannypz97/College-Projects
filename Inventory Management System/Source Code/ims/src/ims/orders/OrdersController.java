/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.orders;

import static ims.Ims.*;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Daniyal
 */
public class OrdersController implements Initializable {
    
    public static String o_id; //to store order_id of order user wishes to view items for.
    public static String v_id; ////to store vendor_id of selected row.
    public static boolean isOrderEditable = false; //'Completed' or 'Arrived' orders shaould not be modifiable.
    
    @FXML
    private Button detailsButton;

    @FXML
    private TableView<Orders> table;
    @FXML
    private TableColumn<Orders, String> order_id;
    
    @FXML
    private TableColumn<Orders, String> time_of_order;
    
    @FXML
    private TableColumn<Orders, String> vendor_id;
    
    @FXML
    private TableColumn<Orders, String> status;
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populateTable(); //fill table with values
    }    
    
    public void populateTable()
    {
       
        
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        time_of_order.setCellValueFactory(new PropertyValueFactory<>("time_of_order"));
        vendor_id.setCellValueFactory(new PropertyValueFactory<>("vendor_id"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        
        table.setItems(getOrders());
        
                
    }
    
    
    
    public ObservableList<Orders> getOrders()
    {
        ObservableList<Orders> orders = FXCollections.observableArrayList();
        
         try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
            
            String setTimeFormat = "alter session set nls_date_format = 'DD-MON-YYYY HH24:MI'";
            
            String query= "select * from orders order by order_id asc";
            Statement st = db.createStatement();
            
            st.execute(setTimeFormat);
            
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                orders.add(new Orders(rs.getString(1), rs.getString(2),  rs.getString(3),  rs.getString(4)));
                
            }
            
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        }   
        
        return orders;
    }
    
    @FXML
    void showOrderDetails(ActionEvent event) throws Exception {
       
       
        if(table.getSelectionModel().getSelectedItem()!=null)
       {
        
        if(table.getSelectionModel().getSelectedItem().getStatus().equals("Placed"))
            isOrderEditable = true;
        else isOrderEditable = false;
        o_id = table.getSelectionModel().getSelectedItem().getOrder_id();
        Parent root = FXMLLoader.load(getClass().getResource("/ims/orders/Order_items.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Items in Order");
        stage.show();
       }
    }
    

    
    @FXML
    void createOrder(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ims/orders/NewOrder.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New Order");
        stage.show();
    }

    @FXML
    void deleteOrder(ActionEvent event) throws Exception {
        String order_id = table.getSelectionModel().getSelectedItem().getOrder_id();
        if(table.getSelectionModel().getSelectedItem().getStatus().equals("Placed"))
                
        {
            try
            {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                String selectedOrder = table.getSelectionModel().getSelectedItem().getOrder_id();


                String query = "delete from order_items where order_id=?";
                PreparedStatement st = db.prepareStatement(query);
                st.setString(1,selectedOrder);
                
                query = "delete from orders where order_id=?";
                st = db.prepareStatement(query);
                st.setString(1,selectedOrder);
                st.execute();
                st.close();
                db.close();


            }
            catch(Exception e)
            {
                e.printStackTrace();

            } 
        }
        else
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Can't delete this order. Contact DBA for more help.");


            alert.showAndWait();
        }
        
    }

    
    @FXML
    void updateOrder(ActionEvent event) throws Exception {
        
        v_id = table.getSelectionModel().getSelectedItem().getVendor_id();
        if(table.getSelectionModel().getSelectedItem().getStatus().equals("Placed"))
        {
            Parent root = FXMLLoader.load(getClass().getResource("/ims/orders/ModifyOrder.fxml")); 

            Scene scene = new Scene(root);        
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Order");
            stage.show();
        }
    }
    
    @FXML
    void refresh(ActionEvent event) throws Exception { //refresh the page
        table.setItems(getOrders());
    }
    
    @FXML
    void backToPanel(ActionEvent event) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("/ims/mainScreen/MainScreen.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void markAsCompleted(ActionEvent event) {
        
       if(!table.getSelectionModel().getSelectedItem().getStatus().equals("Completed"))
       {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Update Order Status to 'Completed'?");
            alert.setContentText("Once Order Status has been set to 'Completed', it can't be changed directly."+
                    " Contact DBA is case of problems.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
          
                try
            {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                String selectedOrder = table.getSelectionModel().getSelectedItem().getOrder_id();


                String query = "update orders set status=? where order_id = ?";
                PreparedStatement st = db.prepareStatement(query);
                st.setString(1, "Completed");
                st.setString(2,selectedOrder);
                st.execute();
                st.close();
                db.close();


            }
            catch(Exception e)
            {
                e.printStackTrace();

            } 
                
            }
            if (result.get() == ButtonType.CANCEL){
                
            }
       }
        
        }
    
}
