import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ResearchAnalyticsPanel extends JPanel {
    private JTable researchTable;
    private ResearchTableModel tableModel;
    private ChartPanel chartPanel;
    private JFreeChart chart;

    public ResearchAnalyticsPanel() {
        initializeComponents();
        setupLayout();
        loadData();
    }

    private void initializeComponents() {
        tableModel = new ResearchTableModel();
        researchTable = new JTable(tableModel);
        
        // Create chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(
            "Research Citations Over Time",
            "Years",
            "Citations",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        chartPanel = new ChartPanel(chart);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel with title and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Research Analytics");
        titleLabel.setFont(new Font("Orbitron", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Research");
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with table and chart
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(researchTable), BorderLayout.CENTER);
        splitPane.setLeftComponent(tablePanel);
        
        // Chart panel
        chartPanel.setPreferredSize(new Dimension(600, 400));
        splitPane.setRightComponent(chartPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Add event handlers
        addButton.addActionListener(e -> showAddResearchDialog());
        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        List<ResearchPaper> papers = new ArrayList<>();
        
        String query = "SELECT paper_title, author, citations, year FROM research ORDER BY year DESC, citations DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ResearchPaper paper = new ResearchPaper();
                paper.setTitle(rs.getString("paper_title"));
                paper.setAuthor(rs.getString("author"));
                paper.setCitations(rs.getInt("citations"));
                paper.setYear(rs.getInt("year"));
                papers.add(paper);
            }
            
            tableModel.setPapers(papers);
            updateChart(papers);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading research data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateChart(List<ResearchPaper> papers) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Group papers by year and sum citations
        java.util.Map<Integer, Integer> citationsByYear = new java.util.HashMap<>();
        for (ResearchPaper paper : papers) {
            int year = paper.getYear();
            int citations = paper.getCitations();
            citationsByYear.put(year, citationsByYear.getOrDefault(year, 0) + citations);
        }
        
        // Add data to dataset
        citationsByYear.entrySet().stream()
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(entry -> dataset.addValue(entry.getValue(), "Citations", String.valueOf(entry.getKey())));
        
        // Update chart
        chart.getCategoryPlot().setDataset(dataset);
    }

    private void showAddResearchDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Research Paper", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField citationsField = new JTextField(20);
        JTextField yearField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Paper Title:"), gbc);
        gbc.gridx = 1;
        dialog.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        dialog.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Citations:"), gbc);
        gbc.gridx = 1;
        dialog.add(citationsField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        dialog.add(yearField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        // Event handlers
        saveButton.addActionListener(e -> {
            if (saveResearch(titleField.getText(), authorField.getText(), 
                           citationsField.getText(), yearField.getText())) {
                dialog.dispose();
                loadData();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private boolean saveResearch(String title, String author, String citations, String year) {
        if (title.isEmpty() || author.isEmpty() || citations.isEmpty() || year.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int citationsVal = Integer.parseInt(citations);
            int yearVal = Integer.parseInt(year);
            
            String query = "INSERT INTO research (paper_title, author, citations, year) VALUES (?, ?, ?, ?)";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setInt(3, citationsVal);
                stmt.setInt(4, yearVal);
                
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for citations and year.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving research: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}