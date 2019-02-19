/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.delivery;

import static ims.Ims.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class Items_PageController implements Initializable {
    

    @FXML
    private TableView<WarehouseDeliveryItems> table;

    @FXML
    private TableColumn<WarehouseDeliveryItems, String> c1;

    @FXML
    private TableColumn<WarehouseDeliveryItems, Integer> c2;

    @FXML
    private TableColumn<WarehouseDeliveryItems, String> c3;

    @FXML
    private TableColumn<WarehouseDeliveryItems, Double> c4;

    @FXML
    private TableColumn<WarehouseDeliveryItems, String> c5;
    


    @FXML
    void markBatch(ActionEvent event) throws Exception {
        try
        {
            if(table.getSelectionModel().getSelectedItem()!=null && Warehouse_deliveryPageController.alreadyExists==false)
            {
                String oldItemName = table.getSelectionModel().getSelectedItem().getItem_name();
                System.out.println(oldItemName);
                String newItemName;
                //add or remove '!' from beginning of name.
                if(oldItemName.charAt(0) == '!')
                    newItemName = oldItemName.substring(2);
                else
                    newItemName = "! " + oldItemName;
                Class.forName("oracle.jdbc.OracleDriver");
                Connection db=DriverManager.getConnection(url,db_uname,db_pass);

                String query= "update warehouse_delivery_items set item_name = ? where item_name=?";
                
                PreparedStatement st = db.prepareStatement(query);
                st.setString(1, newItemName);
                st.setString(2, oldItemName);
                st.execute();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        populateTable();
    }
    
    private void populateTable()
    {
        c1.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        c2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        c3.setCellValueFactory(new PropertyValueFactory<>("units"));
        c4.setCellValueFactory(new PropertyValueFactory<>("batch_price"));
        c5.setCellValueFactory(new PropertyValueFactory<>("product_type_code"));
        
        table.setItems(getData());
        if(Warehouse_deliveryPageController.alreadyExists == true)
            
        table.setEditable(false);
        else
        table.setEditable(true);
        
        //if item_name edited
      
        
        c1.setCellFactory(TextFieldTableCell.forTableColumn());
        c1.setOnEditCommit(event -> {
            
                updateDB("item_name",table.getSelectionModel().getSelectedItem().getItem_name(), String.valueOf(event.getNewValue()));
        });
        
        c2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        c2.setOnEditCommit(event -> {
            
                updateDB("quantity",table.getSelectionModel().getSelectedItem().getItem_name(), String.valueOf(event.getNewValue()));
        });
        
        c4.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        c4.setOnEditCommit(event -> {
            
                updateDB("batch_price",table.getSelectionModel().getSelectedItem().getItem_name(), String.valueOf(event.getNewValue()));
        });
        
        c5.setCellFactory(TextFieldTableCell.forTableColumn());
        c5.setOnEditCommit(event -> {
            
                updateDB("product_type_code",table.getSelectionModel().getSelectedItem().getItem_name(), String.valueOf(event.getNewValue()));
        });
    }
    
    private ObservableList<WarehouseDeliveryItems> getData()
    {
        ObservableList<WarehouseDeliveryItems> list = FXCollections.observableArrayList();
        
         try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                        
            String query= "select * from warehouse_delivery_items where delivery_id = ?";
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1,Warehouse_deliveryPageController.d_id);
            
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
                list.add(new WarehouseDeliveryItems(rs.getString(1), rs.getInt(2), rs.getString(3),  rs.getDouble(4),  rs.getString(6)));
                                
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
    
    private void updateDB(String field, String itemName, String newValue)
    {
        //update 'field' with newValue
                 try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                        
            String query= "update warehouse_delivery_items set " + field + "=? where item_name = ?";
            PreparedStatement st = db.prepareStatement(query);
            st.setString(2,itemName);
            
            if(!field.equals("quantity") || !field.equals("batch_price"))
                st.setString(1, newValue);
            else if(field.equals("quantity"))
                st.setInt(1, Integer.parseInt(newValue));
            else if(field.equals("batch_price"))
                st.setFloat(1, Float.parseFloat(newValue));
            st.execute();
            
               
          
           db.close();
           st.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        } 
                 populateTable();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTable();
        System.out.println(Warehouse_deliveryPageController.d_id);
    }
  
    
}
