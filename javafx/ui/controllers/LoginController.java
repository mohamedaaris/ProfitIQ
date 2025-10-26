package javafx.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.data.DataService;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Failed", "Please enter both username and password.");
            return;
        }

        // Authenticate user against database
        DataService dataService = DataService.getInstance();
        if (dataService.authenticateUser(username, password)) {
            System.out.println("Authentication successful for user: " + username);
            
            try {
                // Open main dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainDashboard_complete.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root, 1200, 800);
                scene.getStylesheets().add(getClass().getResource("/javafx/css/dashboard.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/javafx/css/charts.css").toExternalForm());
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setTitle("ProfitIQ - JavaFX Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Failed to load dashboard.");
            }
        } else {
            System.out.println("Authentication failed for user: " + username);
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
        }
    }

    @FXML
    private void handleSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signupButton.getScene().getWindow();
            Scene scene = new Scene(root, 500, 550);
            scene.getStylesheets().add(getClass().getResource("/javafx/css/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("ProfitIQ - Sign Up");
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load signup page.");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}