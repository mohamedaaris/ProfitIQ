import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatDarkLaf;

public class ProfitIQ {
    public static void main(String[] args) {
        // Set up FlatLaf with modern enhancements
        setupFlatLaf();
        
        // Start the application
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
    
    private static void setupFlatLaf() {
        try {
            // Use the dark theme
            UIManager.setLookAndFeel(new FlatDarkLaf());
            
            // Customize the theme properties for a more modern look
            customizeTheme();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf, using default look and feel");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void customizeTheme() {
        // Customize colors and properties for a more modern appearance
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("ProgressBar.arc", 12);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("ScrollBar.width", 16);
        UIManager.put("TabbedPane.tabHeight", 32);
        UIManager.put("TabbedPane.tabSelectionHeight", 3);
        
        // Enhanced colors for a more vibrant UI
        UIManager.put("Button.background", new Color(75, 85, 99));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focusedBackground", new Color(100, 116, 139));
        UIManager.put("Button.hoverBackground", new Color(100, 116, 139));
        UIManager.put("Button.pressedBackground", new Color(100, 116, 139));
        
        // Text field enhancements
        UIManager.put("TextField.arc", 12);
        UIManager.put("PasswordField.arc", 12);
        
        // Scrollbar improvements
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
    }
}