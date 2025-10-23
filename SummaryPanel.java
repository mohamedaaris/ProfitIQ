import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// import java.sql.*;  // Commented out SQL imports
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;
// import org.jfree.chart.ChartPanel;  // Commented out JFreeChart import
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SummaryPanel extends JPanel {
    private JButton exportButton;
    private JButton refreshButton;
    private JPanel topPanel;
    private JPanel statsPanel;
    private JPanel chartsPanel;
    private JPanel reviewPanel;
    private JPanel mainContentPanel;
    private JPanel salesAnalysisPanel;
    private JPanel customerMapPanel;
    private JPanel revenueChartPanel;
    private JTextArea summaryArea;
    private Random random = new Random();

    public SummaryPanel() {
        initializeComponents();
        setupLayout();
        animateComponents();
    }
    
    /**
     * Animate components with staggered timing for a smooth appearance
     */
    private void animateComponents() {
        // Use staggered animations for a professional appearance
        Timer initialDelay = new Timer(100, e -> {
            ((Timer)e.getSource()).stop();
            
            // Animate stats cards with staggered timing
            Component[] statCards = statsPanel.getComponents();
            for (int i = 0; i < statCards.length; i++) {
                final int index = i;
                Timer cardTimer = new Timer(100 * i, event -> {
                    ((Timer)event.getSource()).stop();
                    AnimationUtils.slideIn((JComponent)statCards[index], "top", 500);
                });
                cardTimer.setRepeats(false);
                cardTimer.start();
            }
            
            // Animate chart panels with a different delay
            Timer chartTimer = new Timer(300, event -> {
                ((Timer)event.getSource()).stop();
                AnimationUtils.slideIn(chartsPanel, "right", 600);
            });
            chartTimer.setRepeats(false);
            chartTimer.start();
            
            // Animate review panel last
            Timer reviewTimer = new Timer(600, event -> {
                ((Timer)event.getSource()).stop();
                AnimationUtils.slideIn(reviewPanel, "bottom", 500);
            });
            reviewTimer.setRepeats(false);
            reviewTimer.start();
        });
        initialDelay.setRepeats(false);
        initialDelay.start();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(ProfitIQ.BACKGROUND_DARK);
        
        // Create main components
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setOpaque(false);
        
        // Create stats panel with 4 stat cards
        statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        
        // Add stat cards
        statsPanel.add(createStatCard("Revenue", "$1.2M", "+12.5%", true));
        statsPanel.add(createStatCard("Customers", "8,429", "+7.2%", true));
        statsPanel.add(createStatCard("Avg. Order", "$142", "-2.3%", false));
        statsPanel.add(createStatCard("Retention", "84.6%", "+3.8%", true));
        
        // Create charts panel with 3 charts
        chartsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        chartsPanel.setOpaque(false);
        
        // Add chart panels
        salesAnalysisPanel = createChartPanel("Sales Analysis", "line");
        customerMapPanel = createChartPanel("Customer Distribution", "map");
        revenueChartPanel = createChartPanel("Revenue Breakdown", "donut");
        
        chartsPanel.add(salesAnalysisPanel);
        chartsPanel.add(customerMapPanel);
        chartsPanel.add(revenueChartPanel);
        
        // Create review panel
        reviewPanel = createReviewPanel();
        
        // Create buttons
        exportButton = new JButton("Export Report");
        styleButton(exportButton, ProfitIQ.PRIMARY_COLOR);
        exportButton.addActionListener(e -> exportSummary());
        
        refreshButton = new JButton("Refresh Data");
        styleButton(refreshButton, ProfitIQ.PRIMARY_COLOR);
        refreshButton.addActionListener(e -> refreshData());
        
        // Add components to main content panel
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        centerPanel.setOpaque(false);
        centerPanel.add(statsPanel);
        centerPanel.add(chartsPanel);
        
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);
        mainContentPanel.add(reviewPanel, BorderLayout.SOUTH);
        
        // Add padding to main content
        mainContentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }
    
    private JPanel createStatCard(String title, String value, String change, boolean positive) {
        InteractivePanel card = new InteractivePanel();
        card.setLayout(new BorderLayout());
        card.setPadding(15);
        card.setBackground(ProfitIQ.CARD_DARK);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(ProfitIQ.TEXT_LIGHT);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel changeLabel = new JLabel(change);
        changeLabel.setForeground(positive ? ProfitIQ.SUCCESS_COLOR : ProfitIQ.DANGER_COLOR);
        changeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(valueLabel, BorderLayout.WEST);
        centerPanel.add(changeLabel, BorderLayout.EAST);
        
        card.add(topPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createChartPanel(String title, String chartType) {
        InteractivePanel panel = new InteractivePanel();
        panel.setLayout(new BorderLayout());
        panel.setPadding(15);
        panel.setBackground(ProfitIQ.CARD_DARK);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(ProfitIQ.TEXT_LIGHT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JPanel chartPanel = new ModernChartPanel(chartType);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReviewPanel() {
        InteractivePanel panel = new InteractivePanel();
        panel.setLayout(new BorderLayout());
        panel.setPadding(15);
        panel.setBackground(ProfitIQ.CARD_DARK);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Business Summary");
        titleLabel.setForeground(ProfitIQ.TEXT_LIGHT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JButton generateButton = new JButton("Generate Report");
        styleButton(generateButton, ProfitIQ.PRIMARY_COLOR);
        generateButton.addActionListener(e -> generateSummaryReport());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(generateButton, BorderLayout.EAST);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        
        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        summaryArea.setForeground(ProfitIQ.TEXT_LIGHT);
        summaryArea.setBackground(new Color(30, 30, 30));
        summaryArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        summaryArea.setText("Click 'Generate Report' to create a business summary...");
        
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        scrollPane.setBorder(new RoundedBorder(new Color(40, 40, 40), 5, 1));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void styleButton(JButton button, Color color) {
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }
    
    private void setupLayout() {
        // Setup top panel with title and buttons
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Dashboard Summary");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(exportButton);
        
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(buttonsPanel, BorderLayout.EAST);
        
        topPanel.add(titlePanel, BorderLayout.CENTER);
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Add panels to main layout
        add(topPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    private void refreshData() {
        // In a real implementation, this would refresh data from the database
        JOptionPane.showMessageDialog(this, 
            "Data refresh functionality would be implemented here.\n" +
            "In a full implementation, this would fetch new data from the database.", 
            "Refresh Data", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void generateSummaryReport() {
        StringBuilder summary = new StringBuilder();
        
        // Add timestamp
        summary.append("Generated on: ").append(new java.util.Date()).append("\n\n");
        
        // Load business analytics summary
        loadBusinessSummary(summary);
        
        // Load college analytics summary
        loadCollegeSummary(summary);
        
        // Load research analytics summary
        loadResearchSummary(summary);
        
        summaryArea.setText(summary.toString());
        summaryArea.setCaretPosition(0); // Scroll to top
    }

    private void loadBusinessSummary(StringBuilder summary) {
        summary.append("🏢 BUSINESS ANALYTICS\n");
        summary.append("=====================\n");
        
        // Placeholder data since SQL is commented out
        summary.append("1. TechCorp Inc\n");
        summary.append("   Revenue: $1,245,000.00 | Profit: $325,000.00 | Growth: 12.50% | Score: 3.25\n");
        summary.append("2. Global Solutions Ltd\n");
        summary.append("   Revenue: $980,000.00 | Profit: $215,000.00 | Growth: 18.30% | Score: 4.02\n");
        summary.append("3. Innovate Systems\n");
        summary.append("   Revenue: $750,000.00 | Profit: $180,000.00 | Growth: 22.10% | Score: 5.30\n");
        
        summary.append("\n");
    }

    private void loadCollegeSummary(StringBuilder summary) {
        summary.append("🎓 COLLEGE PLACEMENT ANALYTICS\n");
        summary.append("============================\n");
        
        // Placeholder data since SQL is commented out
        summary.append("1. Tech University\n");
        summary.append("   Placement Rate: 92.5% | Avg Salary: $85,000.00 | Recruiters: 45 | Score: 78.63\n");
        summary.append("2. Business College\n");
        summary.append("   Placement Rate: 88.3% | Avg Salary: $78,500.00 | Recruiters: 38 | Score: 69.32\n");
        summary.append("3. Engineering Institute\n");
        summary.append("   Placement Rate: 95.1% | Avg Salary: $92,000.00 | Recruiters: 52 | Score: 87.49\n");
        
        summary.append("\n");
    }

    private void loadResearchSummary(StringBuilder summary) {
        summary.append("📚 RESEARCH ANALYTICS\n");
        summary.append("====================\n");
        
        // Placeholder data since SQL is commented out
        summary.append("1. Machine Learning Applications in Finance\n");
        summary.append("   Author: Dr. Smith | Citations: 245 | Year: 2021 | Impact: 122.50\n");
        summary.append("2. Blockchain Technology for Supply Chain\n");
        summary.append("   Author: Prof. Johnson | Citations: 187 | Year: 2020 | Impact: 62.33\n");
        summary.append("3. AI-Driven Decision Making\n");
        summary.append("   Author: Dr. Williams | Citations: 203 | Year: 2022 | Impact: 203.00\n");
        
        summary.append("\n");
    }

    private void exportSummary() {
        // In a real implementation, this would export the summary to CSV or PDF
        JOptionPane.showMessageDialog(this, 
            "Export functionality would be implemented here.\n" +
            "In a full implementation, this would export to CSV or PDF.", 
            "Export", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}