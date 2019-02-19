/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.queryBox;

import static ims.Ims.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class QueryBoxController implements Initializable {
    
    @FXML
    private TextArea queryBox;
        
    @FXML
    void handleQuery(ActionEvent event) {
        try
        {
           
           Class.forName("oracle.jdbc.OracleDriver");
           Connection db=DriverManager.getConnection(url,db_uname,db_pass);
           
           Statement st = db.createStatement();
           String setTimeFormat = "alter session set nls_date_format = 'DD-MON-YYYY HH24:MI'";
           
           st.execute(setTimeFormat);
           st.execute(queryBox.getText());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
           
           
        }
        catch(Exception e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Exception occured");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
