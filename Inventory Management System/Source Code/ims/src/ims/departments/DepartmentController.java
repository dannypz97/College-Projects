/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.departments;

import static ims.Ims.db_pass;
import static ims.Ims.db_uname;
import static ims.Ims.url;
import ims.stock.Stock_Items;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class DepartmentController implements Initializable {
    

    @FXML
    private TableView<Department> table;

    @FXML
    private TableColumn<Department, String> c1;

    @FXML
    private TableColumn<Department, String> c2;

    @FXML
    private TableColumn<Department, String> c3;
    
    @FXML
    private TableColumn<Department, Integer> c4;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNumberOfItems(); //call stored procedure to find number of items in each department
        populateTable();
    }    
    
    private void populateTable()
    {
        c1.setCellValueFactory(new PropertyValueFactory<>("department_name"));
        c2.setCellValueFactory(new PropertyValueFactory<>("department_code"));
        c3.setCellValueFactory(new PropertyValueFactory<>("location"));
        c4.setCellValueFactory(new PropertyValueFactory<>("number_of_items"));
        table.setItems(getDetails());
    }
    
    private ObservableList<Department> getDetails()
    {
        ObservableList<Department> list = FXCollections.observableArrayList();
                        
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
       
            
            String query= "select * from department order by department_name asc";
            Statement st = db.createStatement();
            
                      
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                list.add(new Department(rs.getString(1), rs.getString(2),  rs.getString(3),rs.getInt(4)));
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    @FXML
    void backToPanel(ActionEvent event) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/ims/mainScreen/MainScreen.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    private void setNumberOfItems()
    {
         try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            
       
            
            String query= "CALL numofitems()";
            CallableStatement st = db.prepareCall(query);
            
            st.execute();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } 
    }

}
