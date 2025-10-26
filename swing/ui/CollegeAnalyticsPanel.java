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

public class CollegeAnalyticsPanel extends JPanel {
    private JTable collegeTable;
    private CollegeTableModel tableModel;
    private ChartPanel chartPanel;
    private JFreeChart chart;

    public CollegeAnalyticsPanel() {
        initializeComponents();
        setupLayout();
        loadData();
    }

    private void initializeComponents() {
        tableModel = new CollegeTableModel();
        collegeTable = new JTable(tableModel);
        
        // Create chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
            "College Placement Rate Analysis",
            "Colleges",
            "Placement Rate (%)",
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
        JLabel titleLabel = new JLabel("College Placement Analytics");
        titleLabel.setFont(new Font("Orbitron", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add College");
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
        tablePanel.add(new JScrollPane(collegeTable), BorderLayout.CENTER);
        splitPane.setLeftComponent(tablePanel);
        
        // Chart panel
        chartPanel.setPreferredSize(new Dimension(600, 400));
        splitPane.setRightComponent(chartPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Add event handlers
        addButton.addActionListener(e -> showAddCollegeDialog());
        refreshButton.addActionListener(e -> loadData());
    }

    private void loadData() {
        List<College> colleges = new ArrayList<>();
        
        String query = "SELECT name, placement_rate, avg_salary, recruiters FROM colleges ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                College college = new College();
                college.setName(rs.getString("name"));
                college.setPlacementRate(rs.getDouble("placement_rate"));
                college.setAvgSalary(rs.getDouble("avg_salary"));
                college.setRecruiters(rs.getInt("recruiters"));
                colleges.add(college);
            }
            
            tableModel.setColleges(colleges);
            updateChart(colleges);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading college data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateChart(List<College> colleges) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (College college : colleges) {
            dataset.addValue(college.getPlacementRate(), "Placement Rate", college.getName());
        }
        
        // Update chart
        chart.getCategoryPlot().setDataset(dataset);
    }

    private void showAddCollegeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add College", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField nameField = new JTextField(20);
        JTextField placementField = new JTextField(20);
        JTextField salaryField = new JTextField(20);
        JTextField recruitersField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("College Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Placement Rate (%):"), gbc);
        gbc.gridx = 1;
        dialog.add(placementField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Average Salary ($):"), gbc);
        gbc.gridx = 1;
        dialog.add(salaryField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Recruiters Count:"), gbc);
        gbc.gridx = 1;
        dialog.add(recruitersField, gbc);
        
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
            if (saveCollege(nameField.getText(), placementField.getText(), 
                          salaryField.getText(), recruitersField.getText())) {
                dialog.dispose();
                loadData();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private boolean saveCollege(String name, String placementRate, String avgSalary, String recruiters) {
        if (name.isEmpty() || placementRate.isEmpty() || avgSalary.isEmpty() || recruiters.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double placementRateVal = Double.parseDouble(placementRate);
            double avgSalaryVal = Double.parseDouble(avgSalary);
            int recruitersVal = Integer.parseInt(recruiters);
            
            String query = "INSERT INTO colleges (name, placement_rate, avg_salary, recruiters) VALUES (?, ?, ?, ?)";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, name);
                stmt.setDouble(2, placementRateVal);
                stmt.setDouble(3, avgSalaryVal);
                stmt.setInt(4, recruitersVal);
                
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for placement rate, salary, and recruiters.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving college: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}