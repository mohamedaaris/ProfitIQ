import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class BusinessAnalyticsPanel extends JPanel {
    private JTable companyTable;
    private CompanyTableModel tableModel;
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private JButton addButton;
    private JButton refreshButton;
    private JButton exportButton;
    private JComboBox<String> chartTypeComboBox;

    public BusinessAnalyticsPanel() {
        initializeComponents();
        setupLayout();
        loadData();
    }

    private void initializeComponents() {
        tableModel = new CompanyTableModel();
        companyTable = new JTable(tableModel);
        styleTable(companyTable);
        
        // Create chart with enhanced styling
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
            "Company Profit Analysis",
            "Companies",
            "Profit ($)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        // Style the chart
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Style the bars
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(59, 130, 246)); // Blue bars
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(false);
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create buttons with enhanced styling
        addButton = new JButton("➕ Add Company");
        styleButton(addButton, new Color(59, 130, 246)); // Blue
        
        refreshButton = new JButton("🔄 Refresh");
        styleButton(refreshButton, new Color(16, 185, 129)); // Green
        
        exportButton = new JButton("📤 Export Data");
        styleButton(exportButton, new Color(139, 92, 246)); // Purple
        
        // Chart type selector
        chartTypeComboBox = new JComboBox<>(new String[]{"Bar Chart", "Line Chart"});
        chartTypeComboBox.setPreferredSize(new Dimension(120, 35));
        chartTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chartTypeComboBox.setBackground(new Color(30, 41, 59));
        chartTypeComboBox.setForeground(Color.WHITE);
        chartTypeComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(30, 41, 59));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(59, 130, 246, 100));
        table.setSelectionForeground(Color.WHITE);
        
        // Add hover effect to table rows
        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    table.setRowSelectionInterval(row, row);
                }
            }
        });
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
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(15, 23, 42)); // Dark background
        
        // Top panel with title and buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 41, 59));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Business Analytics");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(new JLabel("Chart Type:"));
        buttonPanel.add(chartTypeComboBox);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(addButton);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with table and chart
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setOpaque(false);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        // Table panel with enhanced styling
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(30, 41, 59));
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105)), 
            "Company Data", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14), 
            Color.WHITE
        ));
        
        JScrollPane tableScrollPane = new JScrollPane(companyTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tableScrollPane.getViewport().setBackground(new Color(30, 41, 59));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(tablePanel);
        
        // Chart panel with enhanced styling
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(new Color(30, 41, 59));
        chartContainer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105)), 
            "Profit Visualization", 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14), 
            Color.WHITE
        ));
        
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        splitPane.setRightComponent(chartContainer);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Add event handlers
        addButton.addActionListener(e -> showAddCompanyDialog());
        refreshButton.addActionListener(e -> loadData());
        exportButton.addActionListener(e -> exportData());
        chartTypeComboBox.addActionListener(e -> changeChartType());
    }

    private void loadData() {
        List<Company> companies = new ArrayList<>();
        
        String query = "SELECT name, revenue, profit, growth_percent FROM companies ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Company company = new Company();
                company.setName(rs.getString("name"));
                company.setRevenue(rs.getDouble("revenue"));
                company.setProfit(rs.getDouble("profit"));
                company.setGrowthPercent(rs.getDouble("growth_percent"));
                companies.add(company);
            }
            
            tableModel.setCompanies(companies);
            updateChart(companies);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading company data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateChart(List<Company> companies) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Company company : companies) {
            dataset.addValue(company.getProfit(), "Profit", company.getName());
        }
        
        // Update chart
        chart.getCategoryPlot().setDataset(dataset);
    }

    private void changeChartType() {
        String selectedType = (String) chartTypeComboBox.getSelectedItem();
        DefaultCategoryDataset dataset = (DefaultCategoryDataset) chart.getCategoryPlot().getDataset();
        
        if ("Bar Chart".equals(selectedType)) {
            chart = ChartFactory.createBarChart(
                "Company Profit Analysis",
                "Companies",
                "Profit ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
        } else if ("Line Chart".equals(selectedType)) {
            chart = ChartFactory.createLineChart(
                "Company Profit Analysis",
                "Companies",
                "Profit ($)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
        }
        
        // Apply styling to the new chart
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        chartPanel.setChart(chart);
    }

    private void showAddCompanyDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Company", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setBackground(new Color(15, 23, 42));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Style input fields
        JTextField nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(200, 30));
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JTextField revenueField = new JTextField(20);
        revenueField.setPreferredSize(new Dimension(200, 30));
        revenueField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JTextField profitField = new JTextField(20);
        profitField.setPreferredSize(new Dimension(200, 30));
        profitField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JTextField growthField = new JTextField(20);
        growthField.setPreferredSize(new Dimension(200, 30));
        growthField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Company Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Revenue ($):"), gbc);
        gbc.gridx = 1;
        dialog.add(revenueField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Profit ($):"), gbc);
        gbc.gridx = 1;
        dialog.add(profitField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Growth (%):"), gbc);
        gbc.gridx = 1;
        dialog.add(growthField, gbc);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        JButton saveButton = new JButton("Save");
        styleButton(saveButton, new Color(34, 197, 94)); // Green
        
        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton, new Color(239, 68, 68)); // Red
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        // Event handlers
        saveButton.addActionListener(e -> {
            if (saveCompany(nameField.getText(), revenueField.getText(), 
                          profitField.getText(), growthField.getText())) {
                dialog.dispose();
                loadData();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void exportData() {
        JOptionPane.showMessageDialog(this, 
            "Export functionality would be implemented here.\n" +
            "In a full implementation, this would export data to CSV or Excel.", 
            "Export Data", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean saveCompany(String name, String revenue, String profit, String growth) {
        if (name.isEmpty() || revenue.isEmpty() || profit.isEmpty() || growth.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double revenueVal = Double.parseDouble(revenue);
            double profitVal = Double.parseDouble(profit);
            double growthVal = Double.parseDouble(growth);
            
            String query = "INSERT INTO companies (name, revenue, profit, growth_percent) VALUES (?, ?, ?, ?)";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, name);
                stmt.setDouble(2, revenueVal);
                stmt.setDouble(3, profitVal);
                stmt.setDouble(4, growthVal);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Company added successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add company.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for revenue, profit, and growth.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving company: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}