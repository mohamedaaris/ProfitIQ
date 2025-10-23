import javax.swing.*;
import java.awt.*;
// Temporarily comment out FlatLaf imports until we add the libraries
// import com.formdev.flatlaf.FlatDarkLaf;
// import com.formdev.flatlaf.FlatLightLaf;

public class ProfitIQ {
    // Theme constants
    public static final Color PRIMARY_COLOR = new Color(59, 130, 246);    // Blue
    public static final Color SECONDARY_COLOR = new Color(14, 165, 233);  // Sky blue
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);     // Green
    public static final Color WARNING_COLOR = new Color(234, 179, 8);     // Yellow
    public static final Color DANGER_COLOR = new Color(239, 68, 68);      // Red
    public static final Color BACKGROUND_DARK = new Color(15, 23, 42);    // Dark blue
    public static final Color CARD_DARK = new Color(30, 41, 59);          // Slightly lighter blue
    public static final Color TEXT_LIGHT = new Color(241, 245, 249);      // Light gray
    
    private static boolean isDarkMode = true;
    
    public static void main(String[] args) {
        // Start the application
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel since FlatLaf is not available
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                setupCustomTheme();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Start with login screen
            new LoginFrame().setVisible(true);
        });
    }
    
    public static void toggleTheme() {
        isDarkMode = !isDarkMode;
        try {
            // Use system look and feel since FlatLaf is not available
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setupCustomTheme();
            SwingUtilities.updateComponentTreeUI(Frame.getFrames()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isDarkMode() {
        return isDarkMode;
    }
    
    private static void setupCustomTheme() {
        // Custom UI properties for both themes
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("ProgressBar.arc", 8);
        UIManager.put("TextComponent.arc", 8);
        
        // Set custom accent color
        UIManager.put("Button.default.focusedBorderColor", PRIMARY_COLOR);
        UIManager.put("Component.focusedBorderColor", PRIMARY_COLOR);
        UIManager.put("TabbedPane.selectedBackground", PRIMARY_COLOR);
        UIManager.put("TabbedPane.underlineColor", PRIMARY_COLOR);
        UIManager.put("ProgressBar.foreground", PRIMARY_COLOR);
    }
}