import javafx.data.DataService;

public class FullAuthTest {
    public static void main(String[] args) {
        System.out.println("=== Full Authentication Flow Test ===\n");
        
        try {
            DataService dataService = DataService.getInstance();
            
            // Step 1: Register a new user
            String username = "testuser_auth";
            String email = "testuser_auth@example.com";
            String password = "securepassword123";
            
            System.out.println("Step 1: Registering new user");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            
            boolean registrationSuccess = dataService.registerUser(username, email, password);
            System.out.println("Registration result: " + (registrationSuccess ? "SUCCESS" : "FAILED"));
            
            if (registrationSuccess) {
                System.out.println("\nStep 2: Testing authentication with CORRECT credentials");
                boolean correctAuth = dataService.authenticateUser(username, password);
                System.out.println("Authentication with correct credentials: " + (correctAuth ? "GRANTED" : "DENIED"));
                
                System.out.println("\nStep 3: Testing authentication with INCORRECT credentials");
                boolean incorrectAuth = dataService.authenticateUser(username, "wrongpassword");
                System.out.println("Authentication with wrong password: " + (incorrectAuth ? "GRANTED" : "DENIED"));
                
                System.out.println("\nStep 4: Testing authentication with non-existent user");
                boolean nonExistentAuth = dataService.authenticateUser("nonexistentuser", "anypassword");
                System.out.println("Authentication with non-existent user: " + (nonExistentAuth ? "GRANTED" : "DENIED"));
            }
            
            System.out.println("\n=== Authentication Flow Test Complete ===");
            
        } catch (Exception e) {
            System.err.println("Error during authentication test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}