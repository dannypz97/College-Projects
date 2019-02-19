/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.orders;

import static ims.Ims.db_pass;
import static ims.Ims.db_uname;
import static ims.Ims.url;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ModifyOrderController implements Initializable {

    static int n; //stores number of orders already in database
    
    
    @FXML
    private ChoiceBox<String> vendorList;

    @FXML
    void modifyOrder(ActionEvent event) throws Exception {
        
        
        String selectedVendor = vendorList.getSelectionModel().getSelectedItem();
        
        int indexOfOpeningBrace = selectedVendor.indexOf("(");
        selectedVendor = selectedVendor.substring(0,indexOfOpeningBrace);
        
        
        
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
            String query= "update orders set vendor_id=? where vendor_id=?";
            PreparedStatement st = db.prepareStatement(query);            
            
            
            
            //set commands here
            st.setString(1,selectedVendor);
            
            
            st.setString(2,OrdersController.v_id);
           
            st.execute();
            
            db.close();
            st.close();
            
            System.out.println("CLOSING");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
            
            
         
        }
        catch(Exception x)
        {
           x.printStackTrace();
        } 
        
    }
    
     private void populateVendorList()
    {
        try
        {
            
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
            String query= "select * from vendor order by vendor_id asc";
            Statement st = db.createStatement();            
                       
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                 String text = rs.getString(2) + "(" + rs.getString(1) + ")" ;
                vendorList.getItems().add(text);
                
            }   
         
            db.close();
            st.close();
         
        }
        catch(Exception x)
        {
                       
        }   
        vendorList.getSelectionModel().selectFirst();
    }
        
    private String generateOrderId()
    {
       Integer orderNumber = n+1; 
       String newOrderNoAsString = orderNumber.toString();
       newOrderNoAsString = "#OR000" + newOrderNoAsString;
       return newOrderNoAsString;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populateVendorList();
    }   
    
}
