import javafx.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewUsers {
    public static void main(String[] args) {
        System.out.println("=== ProfitIQ User Database Viewer ===");
        System.out.println();
        
        try {
            // Connect to the database
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Successfully connected to the database!");
                System.out.println();
                
                // Query to fetch all users
                String query = "SELECT id, username, email, created_at FROM users ORDER BY created_at DESC";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                
                // Display column headers
                System.out.printf("%-5s %-20s %-30s %-20s%n", "ID", "Username", "Email", "Created At");
                System.out.println("------------------------------------------------------------------------");
                
                // Display user data
                boolean hasUsers = false;
                while (rs.next()) {
                    hasUsers = true;
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String createdAt = rs.getString("created_at");
                    
                    System.out.printf("%-5d %-20s %-30s %-20s%n", id, username, email, createdAt);
                }
                
                if (!hasUsers) {
                    System.out.println("No users found in the database.");
                }
                
                // Close connections
                rs.close();
                stmt.close();
                conn.close();
                
                System.out.println();
                System.out.println("Database query completed successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
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