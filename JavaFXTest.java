import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple JavaFX Test Application
 * This is a minimal JavaFX app to test if JavaFX is working
 */
public class JavaFXTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create a simple scene
        VBox root = new VBox();
        root.getChildren().add(new Label("JavaFX is working!"));
        root.getChildren().add(new Label("ProfitIQ JavaFX Dashboard is ready to run."));
        
        Scene scene = new Scene(root, 400, 200);
        
        primaryStage.setTitle("JavaFX Test - ProfitIQ");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        System.out.println("JavaFX Test Application started successfully!");
    }
    
    public static void main(String[] args) {
        System.out.println("Starting JavaFX Test Application...");
        launch(args);
    }
}


