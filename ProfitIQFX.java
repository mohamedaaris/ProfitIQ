import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ProfitIQFX extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the login FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        
        // Create the scene with the login form
        Scene scene = new Scene(root, 500, 450);
        
        // Add CSS styling
        scene.getStylesheets().add(getClass().getResource("/javafx/css/dashboard.css").toExternalForm());
        
        // Configure the stage
        primaryStage.setTitle("ProfitIQ - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}