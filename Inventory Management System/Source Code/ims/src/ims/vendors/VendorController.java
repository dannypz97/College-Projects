/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.vendors;

import ims.Ims.*;
import static ims.Ims.db_pass;
import static ims.Ims.db_uname;
import static ims.Ims.url;
import ims.orders.Orders;
import ims.stock.Stock_Items;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class VendorController implements Initializable {

   

    
    @FXML
    private ListView<String> list;

    @FXML
    private TextArea details;

    @FXML
    void backToPanel(ActionEvent event) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("/ims/mainScreen/MainScreen.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void populateList()
    {
          try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
       
            
            String query= "select vendor_name from vendor order by vendor_id asc";
            Statement st = db.createStatement();
            
                      
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                list.getItems().add(rs.getString(1));
                
            }
            
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                
            public void changed(ObservableValue<? extends String> ov,String old_val, String new_val){
                System.out.println(new_val);
                try
                {
                String query= "select * from vendor where vendor_name=?";
                PreparedStatement st = db.prepareStatement(query);
                
                st.setString(1, new_val);
                ResultSet rs = st.executeQuery();
                rs.next();
                
                details.setText("Vendor ID:  " + rs.getString("VENDOR_ID"));
                details.appendText("\n\n\nAddress:  "+rs.getString("ADDRESS"));
                details.appendText("\n\n\nContact Number:  "+rs.getString("CONTACT_NUMBER"));
                }
                catch(Exception e)
                {
                  e.printStackTrace();
                }
                
            }});
            list.getSelectionModel().selectFirst();
            
           
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
           
        }          
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populateList();
    }    
    
}
