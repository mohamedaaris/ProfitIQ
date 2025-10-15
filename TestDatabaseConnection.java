import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Successfully connected to the database!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}