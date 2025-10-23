import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;

/**
 * Business Panel with modern UI elements and interactive components
 */
public class BusinessPanel extends JPanel {
    private JPanel topPanel;
    private JPanel mainContentPanel;
    private JPanel businessStatsPanel;
    private JPanel businessGrowthPanel;
    private JPanel competitorAnalysisPanel;
    private JPanel marketTrendsPanel;
    private JButton refreshButton;
    private JButton exportButton;
    private Random random = new Random();

    public BusinessPanel() {
        initializeComponents();
        setupLayout();
        animateComponents();
    }

    private void initializeComponents() {
        // Initialize buttons with modern styling
        exportButton = new JButton("Export Analysis");
        styleButton(exportButton, ProfitIQ.PRIMARY_COLOR);
        
        refreshButton = new JButton("Refresh");
        styleButton(refreshButton, ProfitIQ.PRIMARY_COLOR);
        
        // Initialize panels
        businessStatsPanel = createBusinessStatsPanel();
        businessGrowthPanel = createBusinessGrowthPanel();
        competitorAnalysisPanel = createCompetitorAnalysisPanel();
        marketTrendsPanel = createMarketTrendsPanel();
        
        // Add button actions
        refreshButton.addActionListener(e -> refreshData());
        exportButton.addActionListener(e -> exportBusinessReport());
    }

    private void styleButton(JButton button, Color baseColor) {
        button.setPreferredSize(new Dimension(140, 38));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new RoundedBorder(baseColor, 8, 0));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
            
            public void mousePressed(MouseEvent evt) {
                AnimationUtils.pulse(button, 200);
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(ProfitIQ.BACKGROUND_DARK);
        
        // Top panel with title and buttons
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("Business Analysis");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Main content panel with grid layout
        mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Add business stats panel (row 0, col 0, width 2, height 1)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        mainContentPanel.add(businessStatsPanel, gbc);
        
        // Add business growth panel (row 1, col 0, width 1, height 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.4;
        mainContentPanel.add(businessGrowthPanel, gbc);
        
        // Add competitor analysis panel (row 1, col 1, width 1, height 1)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.4;
        mainContentPanel.add(competitorAnalysisPanel, gbc);
        
        // Add market trends panel (row 2, col 0, width 2, height 1)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        mainContentPanel.add(marketTrendsPanel, gbc);
        
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    private void animateComponents() {
        // Use staggered animations for a smooth appearance
        Timer initialDelay = new Timer(100, e -> {
            ((Timer)e.getSource()).stop();
            
            // Animate business stats panel
            AnimationUtils.slideIn((JComponent)businessStatsPanel, "top", 500);
            
            // Animate other panels with staggered timing
            Timer growthTimer = new Timer(200, event -> {
                ((Timer)event.getSource()).stop();
                AnimationUtils.fadeIn(businessGrowthPanel, 600);
            });
            growthTimer.setRepeats(false);
            growthTimer.start();
            
            Timer competitorTimer = new Timer(400, event -> {
                ((Timer)event.getSource()).stop();
                AnimationUtils.fadeIn(competitorAnalysisPanel, 600);
            });
            competitorTimer.setRepeats(false);
            competitorTimer.start();
            
            Timer trendsTimer = new Timer(600, event -> {
                ((Timer)event.getSource()).stop();
                AnimationUtils.slideIn(marketTrendsPanel, "bottom", 500);
            });
            trendsTimer.setRepeats(false);
            trendsTimer.start();
        });
        initialDelay.setRepeats(false);
        initialDelay.start();
    }
    
    private JPanel createBusinessStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setOpaque(false);
        
        // Create business stat cards
        panel.add(createStatCard("📈", "Market Share", "27%", "+3.5%", new Color(59, 130, 246)));
        panel.add(createStatCard("💰", "Revenue Growth", "$1.2M", "+12.8%", new Color(16, 185, 129)));
        panel.add(createStatCard("🏢", "Business Units", "14", "+2", new Color(245, 158, 11)));
        panel.add(createStatCard("👥", "Partnerships", "32", "+5", new Color(139, 92, 246)));
        
        return panel;
    }
    
    private JPanel createStatCard(String icon, String title, String value, String change, Color accentColor) {
        InteractivePanel card = new InteractivePanel(ProfitIQ.CARD_DARK, ProfitIQ.CARD_DARK.brighter(), 
                                                   new Color(51, 65, 85), accentColor, 10);
        card.setLayout(new BorderLayout(10, 5));
        card.setPreferredSize(new Dimension(220, 100));
        
        // Store original value for animation
        card.putClientProperty("originalValue", value);
        
        // Icon panel
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(50, 50));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setForeground(accentColor);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconPanel.add(iconLabel, BorderLayout.CENTER);
        
        // Content panel
        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(148, 163, 184));
        
        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(Color.WHITE);
        
        // Store the value label for animation
        card.putClientProperty("valueLabel", valueLabel);
        
        JLabel changeLabel = new JLabel(change);
        changeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        if (change.contains("+")) {
            changeLabel.setForeground(new Color(34, 197, 94));
        } else {
            changeLabel.setForeground(new Color(239, 68, 68));
        }
        
        contentPanel.add(titleLabel);
        contentPanel.add(valueLabel);
        contentPanel.add(changeLabel);
        
        card.add(iconPanel, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);
        
        // Add hover effect for value animation
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                // Animate the value when hovered
                String originalValue = (String) card.getClientProperty("originalValue");
                try {
                    // For numeric values, animate counting up
                    int targetValue = Integer.parseInt(originalValue.replaceAll("[^0-9]", ""));
                    JLabel label = (JLabel) card.getClientProperty("valueLabel");
                    String prefix = originalValue.startsWith("$") ? "$" : "";
                    String suffix = originalValue.endsWith("%") ? "%" : "";
                    
                    AnimationUtils.animateValue(0, targetValue, 800, (val) -> {
                        label.setText(prefix + (int)Math.floor(val) + suffix);
                    });
                } catch (Exception e) {
                    // For non-numeric values, just set directly
                    JLabel label = (JLabel) card.getClientProperty("valueLabel");
                    label.setText(originalValue);
                }
            }
        });
        
        return card;
    }
    
    private JPanel createBusinessGrowthPanel() {
        InteractivePanel panel = new InteractivePanel();
        panel.setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("Business Growth");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Chart panel
        ModernChartPanel chartPanel = new ModernChartPanel("Business Growth", "line");
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCompetitorAnalysisPanel() {
        InteractivePanel panel = new InteractivePanel();
        panel.setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("Competitor Analysis");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Chart panel
        ModernChartPanel chartPanel = new ModernChartPanel("Competitor Analysis", "bar");
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMarketTrendsPanel() {
        InteractivePanel panel = new InteractivePanel();
        panel.setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("Market Trends");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        // Filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);
        
        String[] periods = {"Week", "Month", "Quarter", "Year"};
        ButtonGroup filterGroup = new ButtonGroup();
        
        for (String period : periods) {
            JToggleButton filterBtn = new JToggleButton(period);
            filterBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            filterBtn.setForeground(Color.WHITE);
            filterBtn.setBackground(ProfitIQ.CARD_DARK);
            filterBtn.setBorderPainted(false);
            filterBtn.setFocusPainted(false);
            filterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            if (period.equals("Month")) {
                filterBtn.setSelected(true);
                filterBtn.setBackground(ProfitIQ.PRIMARY_COLOR);
            }
            
            filterBtn.addActionListener(e -> {
                JToggleButton source = (JToggleButton) e.getSource();
                if (source.isSelected()) {
                    source.setBackground(ProfitIQ.PRIMARY_COLOR);
                } else {
                    source.setBackground(ProfitIQ.CARD_DARK);
                }
            });
            
            filterGroup.add(filterBtn);
            filterPanel.add(filterBtn);
        }
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(filterPanel, BorderLayout.EAST);
        
        // Create trend chart
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setOpaque(false);
        chartContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        ModernChartPanel chartPanel = new ModernChartPanel("Market Trends", "line");
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(chartContainer, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void refreshData() {
        // Simulate data refresh with animations
        JOptionPane.showMessageDialog(this, 
            "Business data refreshed successfully!", 
            "Data Refreshed", 
            JOptionPane.INFORMATION_MESSAGE);
            
        // Animate the refresh
        animateComponents();
    }
    
    private void exportBusinessReport() {
        // Simulate export with a progress dialog
        JDialog progressDialog = new JDialog();
        progressDialog.setTitle("Exporting Report");
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.setLayout(new BorderLayout());
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(false);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        
        JLabel statusLabel = new JLabel("Preparing business report...");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        
        progressDialog.add(statusLabel, BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);
        
        // Use a timer to simulate progress
        Timer timer = new Timer(50, null);
        timer.addActionListener(e -> {
            int value = progressBar.getValue();
            if (value < 100) {
                progressBar.setValue(value + 1);
                
                if (value == 20) {
                    statusLabel.setText("Analyzing business data...");
                } else if (value == 50) {
                    statusLabel.setText("Generating charts...");
                } else if (value == 80) {
                    statusLabel.setText("Finalizing report...");
                }
            } else {
                timer.stop();
                progressDialog.dispose();
                JOptionPane.showMessageDialog(BusinessPanel.this, 
                    "Business report exported successfully!", 
                    "Export Complete", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        progressDialog.setVisible(true);
        timer.start();
    }
}