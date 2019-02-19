/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.mainScreen;

import static ims.Ims.*;
import static ims.LoginController.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniyal
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button vendorButton;

    @FXML
    private Button orderButton;
    
    
    @FXML
    private TextArea status;

    @FXML
    private Button queryButton;
    
    
    @FXML
    private Label loginInfo;
        
    @FXML
    void loadOrdersScreen(ActionEvent event) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/ims/orders/Orders.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Manage Orders");
        stage.show();
    }

    @FXML
    void loadVendorsScreen(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ims/vendors/Vendor.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Vendors");
        stage.show();
    }
    
    @FXML
    void loadDeliveryScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ims/delivery/Warehouse_deliveryPage.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Manage Deliveries");
        stage.show();
    }
    
    @FXML
    void loadStockScreen(ActionEvent event) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("/ims/stock/Stock_Items.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Manage Stock");
        stage.show();
    }
    
    
    @FXML
    void loadDepartmentsScreen(ActionEvent event) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("/ims/departments/Department.fxml")); 

        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Departments");
        stage.show();
    }
    
    private void checkStock()
    {
            int fl = 0; //flag to check if while loops been entered, i.e, to check if stocks not empty?
            try
             {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection db=DriverManager.getConnection(url,db_uname,db_pass);
                Statement st = db.createStatement();
                
                String query= "select sku,quantity from stock_items";
                ResultSet rs = st.executeQuery(query);
                
                status.clear();
                
                
                while(rs.next())
                {
                    fl=1;
                    String sku = rs.getString(1);
                    int currentQty = rs.getInt(2);
                    
                    String[] tokens = sku.split("/");
                    
                        
                  if(currentQty==0)
                      status.appendText(sku +  " stock level DEPLETED.\n\n");
                  else if(currentQty <= (int) (0.20 * Integer.parseInt(tokens[4])))
                        status.appendText(sku +  " stock level is critical.\n\n");
                  else if(currentQty <= (int) (0.45 * Integer.parseInt(tokens[4])))
                            status.appendText(sku +  " stock level is low.\n\n");

                }
             }
             catch(Exception e)
             {
                 
             }
             if(fl==0)
                 status.setText("Stock is empty.");
             if(status.getText().equals(""))
                 status.appendText("Stock is maintained.");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(loggedInAs!=null)
        {
            System.out.println("s");
        loginInfo.setText(loggedInAs);

        }
                if(clearanceLevel!=1)
            queryButton.setDisable(true);
        checkStock(); //check if stock is low or depleted and update text-area to reflect
    }    
    
        @FXML
    void openQueryBox(ActionEvent event) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/ims/queryBox/QueryBox.fxml")); 

        Scene scene = new Scene(root);        
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SQL Query Box");
        stage.show();
    }
    
}
