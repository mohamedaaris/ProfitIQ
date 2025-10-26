import javafx.data.DataService;

public class UserTest {
    public static void main(String[] args) {
        System.out.println("Testing user registration and authentication...");
        
        try {
            DataService dataService = DataService.getInstance();
            
            // Test user registration with a new user
            String username = "newuser" + System.currentTimeMillis(); // Unique username
            String email = "newuser@example.com";
            String password = "newpassword123";
            
            System.out.println("Registering user: " + username);
            boolean registrationSuccess = dataService.registerUser(username, email, password);
            System.out.println("Registration " + (registrationSuccess ? "successful" : "failed"));
            
            if (registrationSuccess) {
                // Test user authentication
                System.out.println("Authenticating user: " + username);
                boolean authSuccess = dataService.authenticateUser(username, password);
                System.out.println("Authentication " + (authSuccess ? "successful" : "failed"));
                
                // Test with wrong password
                System.out.println("Testing with wrong password");
                boolean wrongAuth = dataService.authenticateUser(username, "wrongpassword");
                System.out.println("Wrong password authentication " + (wrongAuth ? "successful" : "failed"));
            }
            
            System.out.println("\nUser test completed!");
        } catch (Exception e) {
            System.err.println("Error testing user functionality: " + e.getMessage());
            e.printStackTrace();
        }
    }
}