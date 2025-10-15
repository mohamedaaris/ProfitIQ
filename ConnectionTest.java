import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/profitiq";
        String user = "postgres";
        String password = "profitiq123"; // Updated password
        
        System.out.println("Attempting to connect to database...");
        System.out.println("URL: " + url);
        System.out.println("User: " + user);
        
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully");
            
            // Attempt to connect
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully!");
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}