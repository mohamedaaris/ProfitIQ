import javafx.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryUsers {
    public static void main(String[] args) {
        System.out.println("Querying users from the database...");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                String query = "SELECT username, email, created_at FROM users ORDER BY created_at DESC LIMIT 10";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                
                System.out.println("Recent users in the database:");
                System.out.println("Username\t\tEmail\t\t\tCreated At");
                System.out.println("--------\t\t-----\t\t\t----------");
                
                while (rs.next()) {
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String createdAt = rs.getString("created_at");
                    System.out.println(username + "\t\t" + email + "\t\t" + createdAt);
                }
                
                conn.close();
            } else {
                System.out.println("Failed to connect to database.");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}