import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

/**
 * JavaFX Launcher with proper module handling
 */
public class JavaFXLauncher extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        // Set system properties for JavaFX
        System.setProperty("javafx.verbose", "true");
        System.setProperty("javafx.debug", "true");
        
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXLauncher.class.getResource("fxml/MainDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("javafx/css/dashboard.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("javafx/css/charts.css").toExternalForm());

        stage.setTitle("ProfitIQ - JavaFX Dashboard");
        // stage.getIcons().add(new Image(getClass().getResourceAsStream("icons/app_icon.png")));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

        System.out.println("JavaFX application started successfully!");
    }

    public static void main(String[] args) {
        System.out.println("Starting JavaFX Launcher...");
        System.out.println("JavaFX modules available: " + System.getProperty("java.version"));
        
        // Print JavaFX system properties
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));
        System.out.println("java.library.path: " + System.getProperty("java.library.path"));
        
        launch(args);
    }
}