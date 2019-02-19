/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.delivery;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class AddDeliveryController implements Initializable {
    
    @FXML
    private ChoiceBox<String> list;

    private void populateList()
    {

        try
        {
          Class.forName("oracle.jdbc.OracleDriver");
          Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                                              //now get vendor_name using vendor_id
          String query = "select a.order_id, a.vendor_id from orders a where a.order_id not in ("
                  + "select order_ref_id as order_id from warehouse_delivery)"
                  + " and (select count(*) from order_items where order_id = a.order_id)>0 ";         
          Statement st = db.createStatement();
          ResultSet rs = st.executeQuery(query);
          
          while(rs.next())
          {
              list.getItems().add(rs.getString(1)+ "(" + rs.getString(2) +")");
          }
          list.getSelectionModel().selectFirst();
            System.out.println(list.getSelectionModel().getSelectedItem().substring(0, list.getSelectionModel().getSelectedItem().indexOf('(') ));                
        }
        catch(Exception e)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Exception occured");
            alert.setContentText(e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateList();
    }    
    
    
    @FXML
    void addDelivery(ActionEvent event) {
        
        try
        {
          Class.forName("oracle.jdbc.OracleDriver");
          Connection db=DriverManager.getConnection(url,db_uname,db_pass);
          
          Statement st2 = db.createStatement();
          String setTimeFormat = "alter session set nls_date_format = 'DD-MON-YYYY HH24:MI'";
          st2.execute(setTimeFormat);//now get vendor_name using vendor_id
          
          String query = "insert into warehouse_delivery values(?,?,?,?)";
               
          
          PreparedStatement st = db.prepareStatement(query);
          
          //get values for ?
          String currentTimeStamp = new SimpleDateFormat("dd-MMM-YY HH:mm").format(Calendar.getInstance().getTime());
          
          String item = list.getSelectionModel().getSelectedItem();
            
          st.setString(4,"Arrived");
          String order_id =item.substring(0, item.indexOf('(') );
          st.setString(3,order_id);

          st.setString(2,currentTimeStamp);
          
          st.setString(1, "x");
          st.execute();
          
                      
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
            
        //get new delivery_id
                query = "select delivery_id from warehouse_delivery where order_ref_id=? ";
                st = db.prepareStatement(query);
                st.setString(1, order_id);
                ResultSet res = st.executeQuery(); 
                res.next();
                String newDeliveryID = res.getString(1);
                
            query = "select * from order_items where order_id = ?";
            st = db.prepareStatement(query);
            st.setString(1, order_id);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
                String insertQuery = "insert into warehouse_delivery_items values(?,?,?,?,?,?)";
                st = db.prepareStatement(insertQuery);
                st.setString(1, rs.getString("PRODUCT_NAME"));
                st.setInt(2, rs.getInt("QUANTITY"));
                st.setString(3, rs.getString("UNITS"));
                st.setFloat(4, 0);
                st.setString(6,"x");
                st.setString(5, newDeliveryID);
                
                st.execute();
            }
            query = "update orders set status = 'Pending' where order_id = ?";
            st = db.prepareStatement(query);
            st.setString(1, order_id);
            st.execute();
            
           
                          
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }
    
}
