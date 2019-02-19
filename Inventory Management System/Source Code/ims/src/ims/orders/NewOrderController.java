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
import java.sql.*;
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
public class NewOrderController implements Initializable {

    
    static int lastOrderNo; //stores number of last order
    
    @FXML
    private ChoiceBox<String> vendorList;

    @FXML
    void placeOrder(ActionEvent event) throws Exception {
        
        String selectedVendor = vendorList.getSelectionModel().getSelectedItem();
        
        int indexOfOpeningBrace = selectedVendor.indexOf("(");
        selectedVendor = selectedVendor.substring(0,indexOfOpeningBrace);
        
       
        String currentTimeStamp = new SimpleDateFormat("dd-MMM-YYYY HH:mm").format(Calendar.getInstance().getTime());
        System.out.println(currentTimeStamp);
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
            String query= "insert into orders values(?,?,?,?)";
            PreparedStatement st = db.prepareStatement(query);            
            
            Statement st2 = db.createStatement();
            String setTimeFormat = "alter session set nls_date_format = 'DD-MON-YYYY HH24:MI'";
            st2.execute(setTimeFormat);
            //set commands here
            st.setString(1,"x");
            st.setString(2,currentTimeStamp);
            st.setString(3,selectedVendor);
            st.setString(4,"Placed");
            st.execute();
            
            db.close();
            st.close();
            st2.close();
            System.out.println("CLOSING");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
            
            
         
        }
        catch(Exception x)
        {
                       
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
            
            //now generate new ORDER_ID
            query= "select order_id from orders order by order_id asc";
            st = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);            
                       
            rs= st.executeQuery(query);
            
            rs.last();
           lastOrderNo = Integer.parseInt(rs.getString(1).substring(3));
              
                
            db.close();
            st.close();
         
        }
        catch(Exception x)
        {
                       
        }   
        vendorList.getSelectionModel().selectFirst();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("STARTING");
        populateVendorList();
    }    
    
}
