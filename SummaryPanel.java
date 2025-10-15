import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SummaryPanel extends JPanel {
    private JTextArea summaryArea;
    private JButton exportButton;
    private JButton refreshButton;
    private JPanel topPanel;

    public SummaryPanel() {
        initializeComponents();
        setupLayout();
        loadSummary();
    }

    private void initializeComponents() {
        summaryArea = new JTextArea(20, 50);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        summaryArea.setBackground(new Color(30, 41, 59));
        summaryArea.setForeground(new Color(226, 232, 240));
        summaryArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        exportButton = new JButton("Export to CSV");
        styleButton(exportButton, new Color(16, 185, 129)); // Green
        
        refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(59, 130, 246)); // Blue
    }

    private void styleButton(JButton button, Color baseColor) {
        button.setPreferredSize(new Dimension(120, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(15, 23, 42)); // Dark background
        
        // Top panel with title and buttons
        topPanel = new JPanel(new BorderLayout()); // Fixed syntax error
        topPanel.setBackground(new Color(30, 41, 59));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Analytics Summary");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with summary text
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.getViewport().setBackground(new Color(30, 41, 59));
        add(scrollPane, BorderLayout.CENTER);
        
        // Add event handlers
        refreshButton.addActionListener(e -> loadSummary());
        exportButton.addActionListener(e -> exportSummary());
    }

    private void loadSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== PROFITIQ ANALYTICS SUMMARY ===\n\n");
        
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
        
        String query = "SELECT name, revenue, profit, growth_percent FROM companies ORDER BY (profit / revenue) * growth_percent DESC LIMIT 5";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (!rs.isBeforeFirst()) {
                summary.append("No company data available.\n\n");
                return;
            }
            
            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                double revenue = rs.getDouble("revenue");
                double profit = rs.getDouble("profit");
                double growth = rs.getDouble("growth_percent");
                double score = (revenue != 0) ? (profit / revenue) * growth : 0;
                
                summary.append(String.format("%d. %s\n", rank++, name));
                summary.append(String.format("   Revenue: $%.2f | Profit: $%.2f | Growth: %.2f%% | Score: %.2f\n", 
                                           revenue, profit, growth, score));
            }
            summary.append("\n");
        } catch (SQLException e) {
            e.printStackTrace();
            summary.append("Error loading business data: ").append(e.getMessage()).append("\n\n");
        }
    }

    private void loadCollegeSummary(StringBuilder summary) {
        summary.append("🎓 COLLEGE PLACEMENT ANALYTICS\n");
        summary.append("============================\n");
        
        String query = "SELECT name, placement_rate, avg_salary, recruiters FROM colleges ORDER BY (placement_rate * avg_salary) / 100 DESC LIMIT 5";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (!rs.isBeforeFirst()) {
                summary.append("No college data available.\n\n");
                return;
            }
            
            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                double placementRate = rs.getDouble("placement_rate");
                double avgSalary = rs.getDouble("avg_salary");
                int recruiters = rs.getInt("recruiters");
                double score = (placementRate * avgSalary) / 100;
                
                summary.append(String.format("%d. %s\n", rank++, name));
                summary.append(String.format("   Placement Rate: %.2f%% | Avg Salary: $%.2f | Recruiters: %d | Score: %.2f\n", 
                                           placementRate, avgSalary, recruiters, score));
            }
            summary.append("\n");
        } catch (SQLException e) {
            e.printStackTrace();
            summary.append("Error loading college data: ").append(e.getMessage()).append("\n\n");
        }
    }

    private void loadResearchSummary(StringBuilder summary) {
        summary.append("📚 RESEARCH ANALYTICS\n");
        summary.append("====================\n");
        
        String query = "SELECT paper_title, author, citations, year FROM research ORDER BY citations / (? - year + 1) DESC LIMIT 5";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            int currentYear = java.time.Year.now().getValue();
            stmt.setInt(1, currentYear);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    summary.append("No research data available.\n\n");
                    return;
                }
                
                int rank = 1;
                while (rs.next()) {
                    String title = rs.getString("paper_title");
                    String author = rs.getString("author");
                    int citations = rs.getInt("citations");
                    int year = rs.getInt("year");
                    double score = (double) citations / (currentYear - year + 1);
                    
                    summary.append(String.format("%d. %s\n", rank++, title));
                    summary.append(String.format("   Author: %s | Citations: %d | Year: %d | Score: %.2f\n", 
                                               author, citations, year, score));
                }
                summary.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            summary.append("Error loading research data: ").append(e.getMessage()).append("\n\n");
        }
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