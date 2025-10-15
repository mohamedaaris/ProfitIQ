import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel headerLabel;
    private JLabel footerLabel;

    public LoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
        setupAnimations();
    }

    private void initializeComponents() {
        // Create components with enhanced styling
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(250, 35));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        loginButton = new JButton("Login");
        styleButton(loginButton, new Color(59, 130, 246)); // Blue color
        
        signupButton = new JButton("Create Account");
        styleButton(signupButton, new Color(34, 197, 94)); // Green color
        
        headerLabel = new JLabel("ProfitIQ Analytics", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        headerLabel.setForeground(new Color(96, 165, 250)); // Light blue color
        
        footerLabel = new JLabel("Unified Business, Placement, and Research Analytics", JLabel.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        footerLabel.setForeground(new Color(156, 163, 175)); // Gray color
    }

    private void styleButton(JButton button, Color baseColor) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(baseColor.darker());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }
        });
    }

    private void setupLayout() {
        setTitle("ProfitIQ - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create gradient background panel
        JPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Header
        add(headerLabel, BorderLayout.NORTH);

        // Center panel for login form
        JPanel centerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Add subtle shadow effect
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(30, 41, 59, 200)); // Semi-transparent dark color
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Username section
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        centerPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(usernameField, gbc);

        // Password section
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        centerPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        centerPanel.add(buttonPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Footer
        add(footerLabel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });

        // Allow Enter key to trigger login
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void setupAnimations() {
        // Add subtle animations to components
        animateComponent(headerLabel, 0);
        animateComponent(usernameField, 100);
        animateComponent(passwordField, 200);
        animateComponent(loginButton, 300);
        animateComponent(signupButton, 400);
    }

    private void animateComponent(Component component, int delay) {
        // Simple fade-in animation
        Timer timer = new Timer(delay, new ActionListener() {
            float opacity = 0.0f;
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.1f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    ((Timer)e.getSource()).stop();
                }
                component.repaint();
            }
        });
        timer.start();
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter both username and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (authenticateUser(username, password)) {
            showMessage("Login successful! Welcome back.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Open main dashboard
            MainDashboard dashboard = new MainDashboard(username);
            dashboard.setVisible(true);
            this.dispose();
        } else {
            showMessage("Invalid username or password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignup() {
        this.dispose();
        new SignupFrame().setVisible(true);
    }

    private void showMessage(String message, String title, int messageType) {
        // Custom styled message dialog
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT id FROM users WHERE username = ? AND password_hash = crypt(?, password_hash)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If we have a result, authentication is successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Database error occurred during authentication.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void setupWindow() {
        setSize(500, 450);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setUndecorated(false); // Keep window decorations for better UX
    }
    
    // Custom gradient panel for background
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Create gradient from dark blue to darker blue
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(15, 23, 42), 
                0, getHeight(), new Color(30, 41, 59)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}