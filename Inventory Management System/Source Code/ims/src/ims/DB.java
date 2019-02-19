
package ims;

import java.sql.*;

public class DB {
    static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static String uname = "dpz";
    static String pass  = "sequel";
    
    static Connection db;
    static Connection connect()
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            db=DriverManager.getConnection(url,uname,pass);
            System.out.println("Connection to db successful.");
            
            return db;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }        
        
    }
    
}
