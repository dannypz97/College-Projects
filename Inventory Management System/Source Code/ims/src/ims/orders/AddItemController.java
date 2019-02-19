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
public class AddItemController implements Initializable { 
   
    @FXML
    private ChoiceBox<String> unitsList;
    
    @FXML    
    private TextField prodName;

    @FXML
    private TextField quantity;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        unitsList.getItems().addAll("pcs","pairs");
        unitsList.getSelectionModel().selectFirst();
    } 
    
    @FXML
    void addItem(ActionEvent event) throws Exception {
       
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
            //if user tries to insert to insert product that already exist, update quantity of original
            
            String query="select * from order_items";
            Statement stat = db.createStatement();
            ResultSet rs = stat.executeQuery(query);
            
            query= "insert into order_items values(?,?,?,?)";
            
            PreparedStatement st = db.prepareStatement(query);
            st.setString(1, prodName.getText().replaceAll("( )+", " "));
            st.setInt(2, Integer.parseInt(quantity.getText()));
            st.setString(3,unitsList.getSelectionModel().getSelectedItem());
            st.setString(4, OrdersController.o_id);
            rs = st.executeQuery();
            
            db.close();
            st.close();
            
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Exception occured");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
}
