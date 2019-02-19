/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author Daniyal
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button login;
    
    @FXML
    private TextField uname;

    @FXML
    private PasswordField pass;

    @FXML
    private Label warning;
    
    static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static String db_uname = "dpz";
    static String db_pass  = "sequel";
    public static int clearanceLevel = 0;
    public static String loggedInAs;
    
    @FXML
    void handleLogin(ActionEvent event) throws Exception{
        
        
        boolean isValid = validate(); //check db and validate credentials
        
        if(!isValid)
            warning.setText("Invalid credentials!");
        else        
            loadScreen(event);
        
        
    }
    private boolean validate()
    {
         try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection db=DriverManager.getConnection(url,db_uname,db_pass);
            System.out.println("Connection to db successful.");
            
            String query= "select * from ims_user where uname=? and password=?";
            PreparedStatement st = db.prepareStatement(query);
            
            st.setString(1,uname.getText());
            st.setString(2,pass.getText());
            
            ResultSet rs = st.executeQuery();
            
            if(!rs.next())
                return false;
            
            clearanceLevel = rs.getInt("CLEARANCE_LEVEL");
            loggedInAs = rs.getString("UNAME") + "@" + rs.getString("SYSTEM_ID");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }   
    }
    
    private void loadScreen(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen/MainScreen.fxml")); 
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/orders/Orders.fxml")); 
//        loader.setController(new MainController("/orders/OrdersController.java"));
//        Parent root = loader.load();
        Scene scene = new Scene(root);        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("IMS Panel");
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
