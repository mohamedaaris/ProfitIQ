import javafx.application.Application;
import javafx.stage.Stage;
import javafx.data.DataService;
import javafx.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestJavaFXDB extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        System.out.println("=== JavaFX Database Connection Test ===");
        
        // Test direct database connection
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Direct database connection: SUCCESS");
                conn.close();
            } else {
                System.out.println("Direct database connection: FAILED");
            }
        } catch (SQLException e) {
            System.out.println("Direct database connection error: " + e.getMessage());
        }
        
        // Test DataService database connection
        try {
            DataService dataService = DataService.getInstance();
            System.out.println("DataService initialization: SUCCESS");
            
            // Test authentication with random credentials
            boolean randomAuth = dataService.authenticateUser("randomuser", "randompass");
            System.out.println("Random user authentication: " + (randomAuth ? "SUCCESS (UNEXPECTED)" : "FAILED (EXPECTED)"));
            
            // Test authentication with valid credentials
            boolean validAuth = dataService.authenticateUser("testuser_auth", "securepassword123");
            System.out.println("Valid user authentication: " + (validAuth ? "SUCCESS (EXPECTED)" : "FAILED (UNEXPECTED)"));
            
        } catch (Exception e) {
            System.out.println("DataService test error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== JavaFX Database Connection Test Complete ===");
        
        // Close the application
        System.exit(0);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}