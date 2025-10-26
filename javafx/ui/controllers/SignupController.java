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

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signupButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleSignup() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Sign Up Failed", "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.WARNING, "Sign Up Failed", "Passwords do not match.");
            return;
        }

        // Register user in database
        DataService dataService = DataService.getInstance();
        if (dataService.registerUser(username, email, password)) {
            System.out.println("Success: Account created successfully for " + username + "! Please login.");
            showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Account created successfully! Please login.");
            handleBackToLogin();
        } else {
            System.out.println("Error: Failed to create account. Username or email may already exist.");
            showAlert(Alert.AlertType.ERROR, "Sign Up Failed", "Failed to create account. Username or email may already exist.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root, 500, 450);
            scene.getStylesheets().add(getClass().getResource("/javafx/css/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("ProfitIQ - Login");
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load login page.");
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