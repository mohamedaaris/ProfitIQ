import javafx.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Successfully connected to the database!");
                
                // Test fetching data from companies table
                String query = "SELECT COUNT(*) as count FROM companies";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("Found " + count + " companies in the database");
                }
                
                // Test fetching data from colleges table
                query = "SELECT COUNT(*) as count FROM colleges";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("Found " + count + " colleges in the database");
                }
                
                // Test fetching data from research table
                query = "SELECT COUNT(*) as count FROM research";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("Found " + count + " research papers in the database");
                }
                
                // Test fetching data from users table
                query = "SELECT COUNT(*) as count FROM users";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("count");
                    System.out.println("Found " + count + " users in the database");
                }
                
                conn.close();
                System.out.println("Database test completed successfully!");
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}