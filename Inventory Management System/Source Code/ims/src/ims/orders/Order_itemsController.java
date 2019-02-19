/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.orders;

import static ims.Ims.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class Order_itemsController implements Initializable {

    
    
    @FXML
    private TableView<Order_items> table;

    @FXML
    private TableColumn<Order_items, String> c1;

    @FXML
    private TableColumn<Order_items, Integer> c2;

    @FXML
    private TableColumn<Order_items, String> c3;

    private void populateItems()
    {
        c1.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        c2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        c3.setCellValueFactory(new PropertyValueFactory<>("units"));
        
        table.setItems(getItems());
    }
    
    private ObservableList<Order_items> getItems()
    {
       ObservableList<Order_items> items = FXCollections.observableArrayList();
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            System.out.println("Connection to db successful.");
            
            String query= "select * from order_items where order_id=?";
            
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, OrdersController.o_id.toString());
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                items.add(new Order_items(rs.getString(1),rs.getInt(2),rs.getString(3)));                
            }
            st.close();
            db.close();
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        }   
        
        return items;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateItems();
    }    
    
        @FXML
    void addItem(ActionEvent event) throws Exception {
        if(OrdersController.isOrderEditable==true)
        {
            Parent root = FXMLLoader.load(getClass().getResource("/ims/orders/AddItem.fxml")); 

            Scene scene = new Scene(root);        
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("New item");
            stage.show();
        }
    }

    @FXML
    void refresh(ActionEvent event) throws Exception{
        populateItems();
    }

    @FXML
    void removeItem(ActionEvent event) throws Exception{
         if(OrdersController.isOrderEditable==true)
         {
            try
            {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection db=DriverManager.getConnection(url,db_uname,db_pass);

                    String query= "delete from order_items where product_name=?";

                    PreparedStatement st = db.prepareStatement(query);
                    st.setString(1, table.getSelectionModel().getSelectedItem().getProduct_name());
                    ResultSet rs = st.executeQuery();

                    st.close();
                    db.close();
                    populateItems();

            }
            catch(Exception e)
            {
                e.printStackTrace();

            }  
    }
    }
    
}
