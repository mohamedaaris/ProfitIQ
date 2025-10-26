import javafx.data.DataService;

public class AuthTest {
    public static void main(String[] args) {
        System.out.println("Testing authentication functionality...");
        
        try {
            DataService dataService = DataService.getInstance();
            
            // Test user registration
            String username = "testuser";
            String email = "test@example.com";
            String password = "testpassword";
            
            System.out.println("Registering user: " + username);
            boolean registrationSuccess = dataService.registerUser(username, email, password);
            System.out.println("Registration " + (registrationSuccess ? "successful" : "failed"));
            
            // Test user authentication
            System.out.println("Authenticating user: " + username);
            boolean authSuccess = dataService.authenticateUser(username, password);
            System.out.println("Authentication " + (authSuccess ? "successful" : "failed"));
            
            // Test with wrong password
            System.out.println("Testing with wrong password");
            boolean wrongAuth = dataService.authenticateUser(username, "wrongpassword");
            System.out.println("Wrong password authentication " + (wrongAuth ? "successful" : "failed"));
            
            System.out.println("\nAuthentication test completed!");
        } catch (Exception e) {
            System.err.println("Error testing authentication: " + e.getMessage());
            e.printStackTrace();
        }
    }
}