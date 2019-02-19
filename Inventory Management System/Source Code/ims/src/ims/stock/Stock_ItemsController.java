/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.stock;

import static ims.Ims.db_pass;
import static ims.Ims.db_uname;
import static ims.Ims.url;
import ims.orders.Orders;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class Stock_ItemsController implements Initializable {

    @FXML
    private TableView<Stock_Items> table;

    @FXML
    private TableColumn<Stock_Items, String> c1;

    @FXML
    private TableColumn<Stock_Items, String> c2;

    @FXML
    private TableColumn<Stock_Items, Integer> c3;

    @FXML
    private TableColumn<Stock_Items, String> c4;
    
    @FXML
    private TextField qtyBox;

    @FXML
    void backToPanel(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ims/mainScreen/MainScreen.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTable();
    }    
    
    private void populateTable()
    {
        c1.setCellValueFactory(new PropertyValueFactory<>("sku"));
        c2.setCellValueFactory(new PropertyValueFactory<>("delivery_ref_id"));
        c3.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        c4.setCellValueFactory(new PropertyValueFactory<>("dept_code"));
        
        table.setItems(getItems());
        
    }
    
    private ObservableList<Stock_Items> getItems()
    {
        ObservableList<Stock_Items> list = FXCollections.observableArrayList();
         try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
       
            
            String query= "select * from stock_items order by delivery_ref_id asc";
            Statement st = db.createStatement();
            
                      
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                list.add(new Stock_Items(rs.getString(1), rs.getString(2),  rs.getInt(3),  rs.getString(4)));
                
            }
            
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        }   
        
        return list;
    }
    
        @FXML
    void initiateTransfer(ActionEvent event) {
        if(qtyBox.getText()!=null && table.getSelectionModel().getSelectedItem()!=null)
        {
            int qtyToBeTransferred = Integer.parseInt(qtyBox.getText());
            int qty = table.getSelectionModel().getSelectedItem().getQuantity();
            if(qtyToBeTransferred>0 && qtyToBeTransferred <= qty)
            {
             String sku = table.getSelectionModel().getSelectedItem().getSku();
             
             String[] tokens = sku.split("/");
             System.out.println(tokens[3]);
             try
             {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection db=DriverManager.getConnection(url,db_uname,db_pass);



                String query= "update stock_items set quantity = ? where sku = ?";
                PreparedStatement st = db.prepareStatement(query);
                st.setInt(1, qty-qtyToBeTransferred);                        
                st.setString(2, sku);
                st.execute();                 
             }
             catch(Exception e)
             {
                 
             }
            }
            populateTable();
        }
    }
    
    @FXML
    void clearDepleted(ActionEvent event) throws Exception {
                     try
             {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection db=DriverManager.getConnection(url,db_uname,db_pass);



                String query= "delete from stock_items where quantity = 0";
                Statement st = db.createStatement();
                st.execute(query);
                populateTable();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
    }
}
