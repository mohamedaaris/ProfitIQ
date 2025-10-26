import javafx.data.DataService;

public class DebugAuthTest {
    public static void main(String[] args) {
        System.out.println("=== Debug Authentication Test ===\n");
        
        try {
            DataService dataService = DataService.getInstance();
            
            // Test with random/non-existent user
            String randomUsername = "randomuser123";
            String randomPassword = "randompassword123";
            
            System.out.println("Testing authentication with random credentials:");
            System.out.println("Username: " + randomUsername);
            System.out.println("Password: " + randomPassword);
            
            boolean authResult = dataService.authenticateUser(randomUsername, randomPassword);
            System.out.println("Authentication result: " + (authResult ? "SUCCESS (ACCESS GRANTED)" : "FAILED (ACCESS DENIED)"));
            
            // Test with a valid user we know exists
            System.out.println("\nTesting authentication with valid user:");
            System.out.println("Username: testuser_auth");
            System.out.println("Password: securepassword123");
            
            boolean validAuthResult = dataService.authenticateUser("testuser_auth", "securepassword123");
            System.out.println("Authentication result: " + (validAuthResult ? "SUCCESS (ACCESS GRANTED)" : "FAILED (ACCESS DENIED)"));
            
            System.out.println("\n=== Debug Authentication Test Complete ===");
            
        } catch (Exception e) {
            System.err.println("Error during debug authentication test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}