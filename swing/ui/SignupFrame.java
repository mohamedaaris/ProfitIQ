package swing.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFrame extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;
    private JButton backButton;
    private JLabel headerLabel;
    private JLabel footerLabel;

    public SignupFrame() {
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
        
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(250, 35));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setPreferredSize(new Dimension(250, 35));
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        signupButton = new JButton("Create Account");
        styleButton(signupButton, new Color(34, 197, 94)); // Green color
        
        backButton = new JButton("Back to Login");
        styleButton(backButton, new Color(156, 163, 175)); // Gray color
        
        headerLabel = new JLabel("Create Account", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        headerLabel.setForeground(new Color(96, 165, 250)); // Light blue color
        
        footerLabel = new JLabel("Join ProfitIQ to unlock powerful analytics", JLabel.CENTER);
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
        setTitle("ProfitIQ - Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create gradient background panel
        JPanel backgroundPanel = new GradientPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Header
        add(headerLabel, BorderLayout.NORTH);

        // Center panel for signup form
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

        // Email section
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(Color.WHITE);
        centerPanel.add(emailLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(emailField, gbc);

        // Password section
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        centerPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(passwordField, gbc);

        // Confirm Password section
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPasswordLabel.setForeground(Color.WHITE);
        centerPanel.add(confirmPasswordLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(confirmPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        centerPanel.add(buttonPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Footer
        add(footerLabel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackToLogin();
            }
        });

        // Allow Enter key to trigger signup
        confirmPasswordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });
    }

    private void setupAnimations() {
        // Add subtle animations to components
        animateComponent(headerLabel, 0);
        animateComponent(usernameField, 100);
        animateComponent(emailField, 200);
        animateComponent(passwordField, 300);
        animateComponent(confirmPasswordField, 400);
        animateComponent(signupButton, 500);
        animateComponent(backButton, 600);
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

    private void handleSignup() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields.", "Signup Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match.", "Signup Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (registerUser(username, email, password)) {
            showMessage("Account created successfully! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            handleBackToLogin();
        } else {
            showMessage("Failed to create account. Username or email may already exist.", "Signup Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBackToLogin() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }

    private void showMessage(String message, String title, int messageType) {
        // Custom styled message dialog
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, crypt(?, gen_salt('bf')))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Database error occurred during registration.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void setupWindow() {
        setSize(500, 550);
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