package main.Resources;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;


/**
 * Created by blakejoynes on 11/4/16.
 */
public class DBConnect {
    private static String url = "jdbc:mysql://localhost:3306/calmmindsdb?useSSL=false"; //jdbc:mysql://triton.towson.edu:3360/calmmindsdb
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "admin";
    private static String password = "password";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);

            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }
}
