package placement;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static String url = "jdbc:mysql://localhost:3306/campus_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static String user = "root";
    static String password = "Vijai1612"; // <-- must be YOUR actual MySQL root password
    
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}