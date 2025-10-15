import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainDashboard extends JFrame {
    private String currentUser;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel businessPanel;
    private JPanel collegePanel;
    private JPanel researchPanel;
    private JPanel summaryPanel;
    private JPanel sidebar;
    private JLabel welcomeLabel;

    public MainDashboard(String username) {
        this.currentUser = username;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupWindow();
    }

    private void initializeComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Initialize panels for each module
        businessPanel = new BusinessAnalyticsPanel();
        collegePanel = new CollegeAnalyticsPanel();
        researchPanel = new ResearchAnalyticsPanel();
        summaryPanel = new SummaryPanel();
    }

    private void setupLayout() {
        setTitle("ProfitIQ - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create sidebar with enhanced styling
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create top panel with user info
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Add main panels to card layout with styling
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(businessPanel, "Business");
        mainPanel.add(collegePanel, "College");
        mainPanel.add(researchPanel, "Research");
        mainPanel.add(summaryPanel, "Summary");
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Show business panel by default
        cardLayout.show(mainPanel, "Business");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setBackground(new Color(15, 23, 42)); // Dark blue background
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Navigation buttons with enhanced styling
        SidebarButton businessBtn = new SidebarButton("Business Analytics", "📊");
        SidebarButton collegeBtn = new SidebarButton("College Analytics", "🎓");
        SidebarButton researchBtn = new SidebarButton("Research Analytics", "📚");
        SidebarButton summaryBtn = new SidebarButton("Summary Dashboard", "📈");
        SidebarButton logoutBtn = new SidebarButton("Logout", "🚪");

        // Add buttons to sidebar
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(businessBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(collegeBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(researchBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(summaryBtn);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // Add event handlers
        businessBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Business");
            updateActiveButton(businessBtn);
        });
        
        collegeBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "College");
            updateActiveButton(collegeBtn);
        });
        
        researchBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Research");
            updateActiveButton(researchBtn);
        });
        
        summaryBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Summary");
            updateActiveButton(summaryBtn);
        });
        
        logoutBtn.addActionListener(e -> handleLogout());

        return sidebar;
    }

    private void updateActiveButton(SidebarButton activeButton) {
        // Update all buttons to reflect the active state
        Component[] components = sidebar.getComponents();
        for (Component component : components) {
            if (component instanceof SidebarButton) {
                SidebarButton button = (SidebarButton) component;
                button.setActive(button == activeButton);
            }
        }
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 41, 59)); // Slightly lighter blue
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        welcomeLabel = new JLabel("Welcome, " + currentUser + "!", JLabel.LEFT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        
        // Add a subtle shadow effect
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(71, 85, 105)),
            topPanel.getBorder()
        ));
        
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        return topPanel;
    }

    private void setupEventHandlers() {
        // Event handlers are set up in the setupLayout method
    }

    private void handleLogout() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }

    private void setupWindow() {
        setSize(1200, 800);
        setLocationRelativeTo(null); // Center the window
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
    }
    
    // Custom sidebar button with enhanced styling
    private class SidebarButton extends JButton {
        private boolean isActive = false;
        private Color defaultBackground = new Color(30, 41, 59);
        private Color hoverBackground = new Color(51, 65, 85);
        private Color activeBackground = new Color(59, 130, 246);
        private String icon;
        
        public SidebarButton(String text, String icon) {
            super(text);
            this.icon = icon;
            initializeButton();
        }
        
        private void initializeButton() {
            setHorizontalAlignment(SwingConstants.LEFT);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setForeground(Color.WHITE);
            setBackground(defaultBackground);
            setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Add icon
            setText(icon + " " + getText());
            
            // Add hover effects
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!isActive) {
                        setBackground(hoverBackground);
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!isActive) {
                        setBackground(defaultBackground);
                    }
                }
            });
        }
        
        public void setActive(boolean active) {
            isActive = active;
            if (isActive) {
                setBackground(activeBackground);
                setFont(new Font("Segoe UI", Font.BOLD, 14));
            } else {
                setBackground(defaultBackground);
                setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Paint background
            if (getModel().isPressed()) {
                g2d.setColor(defaultBackground.darker());
            } else if (getModel().isRollover()) {
                g2d.setColor(isActive ? activeBackground : hoverBackground);
            } else {
                g2d.setColor(getBackground());
            }
            
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Paint text
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
}