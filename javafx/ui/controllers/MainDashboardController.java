package javafx.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.Node;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Import model classes
import javafx.model.Company;
import javafx.model.College;
import javafx.model.ResearchPaper;

// Import utility classes
import javafx.data.DataService;

/**
 * Main Dashboard Controller for ProfitIQ JavaFX Application
 * Handles all panel navigation and data visualization
 */
public class MainDashboardController implements Initializable {
    
    // Business Panel Components
    @FXML private VBox businessPanel;
    @FXML private TableView<Company> companyTable;
    @FXML private ComboBox<String> chartTypeCombo;
    @FXML private StackPane businessChartPane;
    
    // College Panel Components
    @FXML private VBox collegePanel;
    @FXML private TableView<College> collegeTable;
    @FXML private StackPane collegeChartPane;
    
    // Research Panel Components
    @FXML private VBox researchPanel;
    @FXML private TableView<ResearchPaper> researchTable;
    @FXML private StackPane researchChartPane;
    
    // Summary Panel Components
    @FXML private VBox summaryPanel;
    @FXML private VBox revenueCard;
    @FXML private VBox customersCard;
    @FXML private VBox ordersCard;
    @FXML private VBox growthCard;
    @FXML private VBox salesChartCard;
    @FXML private VBox revenueChartCard;
    @FXML private VBox distributionChartCard;
    @FXML private TextArea summaryTextArea;
    
    // Navigation buttons
    @FXML private Button businessBtn;
    @FXML private Button collegeBtn;
    @FXML private Button researchBtn;
    @FXML private Button summaryBtn;
    @FXML private Button logoutBtn;
    
    // Top navigation controls
    @FXML private TextField searchField;
    @FXML private Button themeToggleBtn;
    @FXML private Button notificationBtn;
    @FXML private Button profileBtn;
    @FXML private Button settingsBtn;
    
    // Data Models
    private ObservableList<Company> companies = FXCollections.observableArrayList();
    private ObservableList<College> colleges = FXCollections.observableArrayList();
    private ObservableList<ResearchPaper> researchPapers = FXCollections.observableArrayList();
    
    // Charts
    private BarChart<String, Number> businessChart;
    private BarChart<String, Number> collegeChart;
    private LineChart<String, Number> researchChart;
    private PieChart revenuePieChart;
    private LineChart<String, Number> salesLineChart;
    private BarChart<String, Number> distributionChart;
    
    // Data service for real-time updates
    private DataService dataService;
    
    // Theme tracking
    private boolean isDarkTheme = true;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize data service
        dataService = DataService.getInstance();
        
        setupTables();
        setupCharts();
        setupKPICards();
        setupChartCards();
        // setupDataListeners(); // Commented out for simplicity
        loadInitialData();
        
        // Set default panel
        showBusinessPanel();
    }

    private void setupTables() {
        // Setup Company Table
        setupCompanyTable();
        
        // Setup College Table
        setupCollegeTable();
        
        // Setup Research Table
        setupResearchTable();
    }
    
    private void setupCompanyTable() {
        TableColumn<Company, String> nameCol = new TableColumn<>("Company Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn<Company, Double> revenueCol = new TableColumn<>("Revenue ($)");
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        revenueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        TableColumn<Company, Double> profitCol = new TableColumn<>("Profit ($)");
        profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
        profitCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        TableColumn<Company, Double> growthCol = new TableColumn<>("Growth (%)");
        growthCol.setCellValueFactory(new PropertyValueFactory<>("growthPercent"));
        growthCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        TableColumn<Company, Double> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        companyTable.getColumns().addAll(nameCol, revenueCol, profitCol, growthCol, scoreCol);
        companyTable.setItems(companies);
        companyTable.setEditable(true);
    }
    
    private void setupCollegeTable() {
        TableColumn<College, String> nameCol = new TableColumn<>("College Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn<College, Double> placementCol = new TableColumn<>("Placement Rate (%)");
        placementCol.setCellValueFactory(new PropertyValueFactory<>("placementRate"));
        placementCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        TableColumn<College, Double> salaryCol = new TableColumn<>("Avg Salary ($)");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("avgSalary"));
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        TableColumn<College, Integer> recruitersCol = new TableColumn<>("Recruiters");
        recruitersCol.setCellValueFactory(new PropertyValueFactory<>("recruiters"));
        recruitersCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        TableColumn<College, Double> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        collegeTable.getColumns().addAll(nameCol, placementCol, salaryCol, recruitersCol, scoreCol);
        collegeTable.setItems(colleges);
        collegeTable.setEditable(true);
    }
    
    private void setupResearchTable() {
        TableColumn<ResearchPaper, String> titleCol = new TableColumn<>("Paper Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn<ResearchPaper, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn<ResearchPaper, Integer> citationsCol = new TableColumn<>("Citations");
        citationsCol.setCellValueFactory(new PropertyValueFactory<>("citations"));
        citationsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        TableColumn<ResearchPaper, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        TableColumn<ResearchPaper, Double> scoreCol = new TableColumn<>("Impact Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        researchTable.getColumns().addAll(titleCol, authorCol, citationsCol, yearCol, scoreCol);
        researchTable.setItems(researchPapers);
        researchTable.setEditable(true);
    }
    
    private void setupCharts() {
        // Setup Business Chart
        businessChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        businessChart.setTitle("Company Profit Analysis");
        businessChart.setAnimated(true);
        businessChart.setLegendVisible(false);
        
        // Setup College Chart
        collegeChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        collegeChart.setTitle("College Placement Rate Analysis");
        collegeChart.setAnimated(true);
        collegeChart.setLegendVisible(false);
        
        // Setup Research Chart
        researchChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        researchChart.setTitle("Research Citations Over Time");
        researchChart.setAnimated(true);
        researchChart.setLegendVisible(false);
        
        // Setup Summary Charts
        setupSummaryCharts();
    }
    
    private void setupSummaryCharts() {
        // Revenue Pie Chart
        revenuePieChart = new PieChart();
        revenuePieChart.setTitle("Revenue Breakdown");
        revenuePieChart.setAnimated(true);
        
        // Sales Line Chart
        salesLineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        salesLineChart.setTitle("Sales Trend");
        salesLineChart.setAnimated(true);
        salesLineChart.setLegendVisible(false);
        
        // Distribution Bar Chart
        distributionChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        distributionChart.setTitle("Customer Distribution");
        distributionChart.setAnimated(true);
        distributionChart.setLegendVisible(false);
    }
    
    private void setupKPICards() {
        // Revenue Card
        setupKPICard(revenueCard, "💰", "Total Revenue", "$1.2M", "+12.5%", true);
        
        // Customers Card
        setupKPICard(customersCard, "👥", "Total Customers", "8,429", "+7.2%", true);
        
        // Orders Card
        setupKPICard(ordersCard, "📦", "Total Orders", "1,247", "+15.3%", true);
        
        // Growth Card
        setupKPICard(growthCard, "📈", "Growth Rate", "18.7%", "+2.1%", true);
    }
    
    private void setupKPICard(VBox card, String icon, String title, String value, String change, boolean positive) {
        card.getChildren().clear();
        
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        headerBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px;");
        
        headerBox.getChildren().addAll(iconLabel, titleLabel);
        
        HBox valueBox = new HBox();
        valueBox.setSpacing(10);
        valueBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label changeLabel = new Label(change);
        String changeColor = positive ? "#22c55e" : "#ef4444";
        changeLabel.setStyle("-fx-text-fill: " + changeColor + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        valueBox.getChildren().addAll(valueLabel, changeLabel);
        
        card.getChildren().addAll(headerBox, valueBox);
        card.setSpacing(10);
    }
    
    private void setupChartCards() {
        // Sales Chart Card
        setupChartCard(salesChartCard, "📊 Sales Analysis", salesLineChart);
        
        // Revenue Chart Card
        setupChartCard(revenueChartCard, "💰 Revenue Breakdown", revenuePieChart);
        
        // Distribution Chart Card
        setupChartCard(distributionChartCard, "🗺️ Customer Distribution", distributionChart);
    }
    
    private void setupChartCard(VBox card, String title, Chart chart) {
        card.getChildren().clear();
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #f1f5f9; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        card.getChildren().addAll(titleLabel, chart);
        card.setSpacing(10);
    }
    
    private void loadInitialData() {
        loadBusinessData();
        loadCollegeData();
        loadResearchData();
        loadSummaryData();
    }
    
    private void loadBusinessData() {
        companies.clear();
        companies.addAll(dataService.getCompanies());
        updateBusinessChart();
    }
    
    private void loadCollegeData() {
        colleges.clear();
        colleges.addAll(dataService.getColleges());
        updateCollegeChart();
    }
    
    private void loadResearchData() {
        researchPapers.clear();
        researchPapers.addAll(dataService.getResearch());
        updateResearchChart();
    }
    
    private void loadSummaryData() {
        Map<String, Object> summary = dataService.getSummary();
        updateSummaryKPIs(summary);
        updateSummaryCharts();
    }
    
    private void updateSummaryKPIs(Map<String, Object> summary) {
        // Update KPI cards with real-time data
        double totalRevenue = (Double) summary.getOrDefault("totalRevenue", 0.0);
        int totalCustomers = (Integer) summary.getOrDefault("totalCustomers", 0);
        int totalOrders = (Integer) summary.getOrDefault("totalOrders", 0);
        double retentionRate = (Double) summary.getOrDefault("retentionRate", 0.0);
        
        // Update revenue card
        updateKPICard(revenueCard, "💰", "Total Revenue", 
            String.format("$%.1fM", totalRevenue / 1000000), "+12.5%", true);
        
        // Update customers card
        updateKPICard(customersCard, "👥", "Total Customers", 
            String.format("%,d", totalCustomers), "+7.2%", true);
        
        // Update orders card
        updateKPICard(ordersCard, "📦", "Total Orders", 
            String.format("%,d", totalOrders), "+15.3%", true);
        
        // Update growth card
        updateKPICard(growthCard, "📈", "Growth Rate", 
            String.format("%.1f%%", retentionRate), "+2.1%", true);
    }
    
    private void updateKPICard(VBox card, String icon, String title, String value, String change, boolean positive) {
        card.getChildren().clear();
        
        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        headerBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 24px;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px;");
        
        headerBox.getChildren().addAll(iconLabel, titleLabel);
        
        HBox valueBox = new HBox();
        valueBox.setSpacing(10);
        valueBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label changeLabel = new Label(change);
        String changeColor = positive ? "#22c55e" : "#ef4444";
        changeLabel.setStyle("-fx-text-fill: " + changeColor + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        valueBox.getChildren().addAll(valueLabel, changeLabel);
        
        card.getChildren().addAll(headerBox, valueBox);
        card.setSpacing(10);
    }
    
    private void updateBusinessChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Profit");
        
        for (Company company : companies) {
            series.getData().add(new XYChart.Data<>(company.getName(), company.getProfit()));
        }
        
        businessChart.getData().clear();
        businessChart.getData().add(series);
        businessChartPane.getChildren().clear();
        businessChartPane.getChildren().add(businessChart);
    }
    
    private void updateCollegeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Placement Rate");
        
        for (College college : colleges) {
            series.getData().add(new XYChart.Data<>(college.getName(), college.getPlacementRate()));
        }
        
        collegeChart.getData().clear();
        collegeChart.getData().add(series);
        collegeChartPane.getChildren().clear();
        collegeChartPane.getChildren().add(collegeChart);
    }
    
    private void updateResearchChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Citations");
        
        // Group by year
        Map<Integer, Integer> citationsByYear = new HashMap<>();
        for (ResearchPaper paper : researchPapers) {
            int year = paper.getYear();
            citationsByYear.put(year, citationsByYear.getOrDefault(year, 0) + paper.getCitations());
        }
        
        // Sort by year and add to series
        citationsByYear.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue())));
        
        researchChart.getData().clear();
        researchChart.getData().add(series);
        researchChartPane.getChildren().clear();
        researchChartPane.getChildren().add(researchChart);
    }
    
    private void updateSummaryCharts() {
        // Update Revenue Pie Chart
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
            new PieChart.Data("Business", 45),
            new PieChart.Data("College", 30),
            new PieChart.Data("Research", 25)
        );
        revenuePieChart.setData(pieData);
        
        // Update Sales Line Chart
        XYChart.Series<String, Number> salesSeries = new XYChart.Series<>();
        salesSeries.setName("Sales");
        salesSeries.getData().addAll(
            new XYChart.Data<>("Jan", 100),
            new XYChart.Data<>("Feb", 120),
            new XYChart.Data<>("Mar", 140),
            new XYChart.Data<>("Apr", 130),
            new XYChart.Data<>("May", 160),
            new XYChart.Data<>("Jun", 180)
        );
        salesLineChart.getData().clear();
        salesLineChart.getData().add(salesSeries);
        
        // Update Distribution Chart
        XYChart.Series<String, Number> distSeries = new XYChart.Series<>();
        distSeries.setName("Customers");
        distSeries.getData().addAll(
            new XYChart.Data<>("North", 2500),
            new XYChart.Data<>("South", 1800),
            new XYChart.Data<>("East", 2200),
            new XYChart.Data<>("West", 1900)
        );
        distributionChart.getData().clear();
        distributionChart.getData().add(distSeries);
    }
    
    
    // Navigation Methods
    @FXML
    private void showBusinessPanel() {
        hideAllPanels();
        businessPanel.setVisible(true);
        updateActiveNavButton(businessBtn);
        animatePanelTransition(businessPanel);
        System.out.println("Active button updated: 📊 Business Analytics");
    }
    
    @FXML
    private void showCollegePanel() {
        hideAllPanels();
        collegePanel.setVisible(true);
        updateActiveNavButton(collegeBtn);
        animatePanelTransition(collegePanel);
        System.out.println("Active button updated: 🎓 College Analytics");
    }
    
    @FXML
    private void showResearchPanel() {
        hideAllPanels();
        researchPanel.setVisible(true);
        updateActiveNavButton(researchBtn);
        animatePanelTransition(researchPanel);
        System.out.println("Active button updated: 📚 Research Analytics");
    }
    
    @FXML
    private void showSummaryPanel() {
        hideAllPanels();
        summaryPanel.setVisible(true);
        updateActiveNavButton(summaryBtn);
        animatePanelTransition(summaryPanel);
        System.out.println("Active button updated: 📈 Summary Dashboard");
    }
    
    private void hideAllPanels() {
        businessPanel.setVisible(false);
        collegePanel.setVisible(false);
        researchPanel.setVisible(false);
        summaryPanel.setVisible(false);
    }
    
    private void animatePanelTransition(Node panel) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), panel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
    
    // Action Methods
    @FXML
    private void refreshBusinessData() {
        loadBusinessData();
        showAlert("Refresh", "Business data refreshed successfully.");
    }
    
    @FXML
    private void refreshCollegeData() {
        loadCollegeData();
        showAlert("Refresh", "College data refreshed successfully.");
    }
    
    @FXML
    private void refreshResearchData() {
        loadResearchData();
        showAlert("Refresh", "Research data refreshed successfully.");
    }
    
    @FXML
    private void refreshSummaryData() {
        loadSummaryData();
        showAlert("Refresh", "Summary data refreshed successfully.");
    }
    
    @FXML
    private void exportBusinessData() {
        showAlert("Export", "Business data exported successfully.");
    }
    
    @FXML
    private void exportCollegeData() {
        showAlert("Export", "College data exported successfully.");
    }
    
    @FXML
    private void exportResearchData() {
        showAlert("Export", "Research data exported successfully.");
    }
    
    @FXML
    private void exportSummaryReport() {
        showAlert("Export", "Summary report exported successfully.");
    }
    
    @FXML
    private void showAddCompanyDialog() {
        Dialog<Company> dialog = new Dialog<>();
        dialog.setTitle("Add New Company");
        dialog.setHeaderText("Enter company details:");
        dialog.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Create form fields with validation
        TextField nameField = new TextField();
        nameField.setPromptText("Company Name");
        nameField.setPrefWidth(300);
        
        TextField revenueField = new TextField();
        revenueField.setPromptText("Revenue (e.g., 1000000.00)");
        revenueField.setPrefWidth(300);
        
        TextField profitField = new TextField();
        profitField.setPromptText("Profit (e.g., 250000.00)");
        profitField.setPrefWidth(300);
        
        TextField growthField = new TextField();
        growthField.setPromptText("Growth % (e.g., 15.50)");
        growthField.setPrefWidth(300);
        
        // Create grid with better styling
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Revenue:"), 0, 1);
        grid.add(revenueField, 1, 1);
        grid.add(new Label("Profit:"), 0, 2);
        grid.add(profitField, 1, 2);
        grid.add(new Label("Growth %:"), 0, 3);
        grid.add(growthField, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        // Add buttons
        ButtonType addButton = new ButtonType("Add Company", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        
        // Apply validation
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);
        
        // Enable add button when all fields are filled
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                revenueField.getText().trim().isEmpty() ||
                profitField.getText().trim().isEmpty() ||
                growthField.getText().trim().isEmpty()
            );
        });
        
        revenueField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                revenueField.getText().trim().isEmpty() ||
                profitField.getText().trim().isEmpty() ||
                growthField.getText().trim().isEmpty()
            );
        });
        
        profitField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                revenueField.getText().trim().isEmpty() ||
                profitField.getText().trim().isEmpty() ||
                growthField.getText().trim().isEmpty()
            );
        });
        
        growthField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                revenueField.getText().trim().isEmpty() ||
                profitField.getText().trim().isEmpty() ||
                growthField.getText().trim().isEmpty()
            );
        });
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    return new Company(
                        nameField.getText().trim(),
                        Double.parseDouble(revenueField.getText().trim()),
                        Double.parseDouble(profitField.getText().trim()),
                        Double.parseDouble(growthField.getText().trim())
                    );
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for numeric fields.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<Company> result = dialog.showAndWait();
        result.ifPresent(company -> {
            companies.add(company);
            dataService.addCompany(company);
            updateBusinessChart();
            showAlert("Success", "Company added successfully.");
        });
    }
    
    @FXML
    private void showAddCollegeDialog() {
        Dialog<College> dialog = new Dialog<>();
        dialog.setTitle("Add New College");
        dialog.setHeaderText("Enter college details:");
        dialog.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Create form fields with validation
        TextField nameField = new TextField();
        nameField.setPromptText("College Name");
        nameField.setPrefWidth(300);
        
        TextField placementField = new TextField();
        placementField.setPromptText("Placement Rate % (e.g., 95.50)");
        placementField.setPrefWidth(300);
        
        TextField salaryField = new TextField();
        salaryField.setPromptText("Average Salary (e.g., 120000.00)");
        salaryField.setPrefWidth(300);
        
        TextField recruitersField = new TextField();
        recruitersField.setPromptText("Number of Recruiters (e.g., 150)");
        recruitersField.setPrefWidth(300);
        
        // Create grid with better styling
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Placement Rate %:"), 0, 1);
        grid.add(placementField, 1, 1);
        grid.add(new Label("Avg Salary:"), 0, 2);
        grid.add(salaryField, 1, 2);
        grid.add(new Label("Recruiters:"), 0, 3);
        grid.add(recruitersField, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        // Add buttons
        ButtonType addButton = new ButtonType("Add College", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        
        // Apply validation
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);
        
        // Enable add button when all fields are filled
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                placementField.getText().trim().isEmpty() ||
                salaryField.getText().trim().isEmpty() ||
                recruitersField.getText().trim().isEmpty()
            );
        });
        
        placementField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                placementField.getText().trim().isEmpty() ||
                salaryField.getText().trim().isEmpty() ||
                recruitersField.getText().trim().isEmpty()
            );
        });
        
        salaryField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                placementField.getText().trim().isEmpty() ||
                salaryField.getText().trim().isEmpty() ||
                recruitersField.getText().trim().isEmpty()
            );
        });
        
        recruitersField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                nameField.getText().trim().isEmpty() ||
                placementField.getText().trim().isEmpty() ||
                salaryField.getText().trim().isEmpty() ||
                recruitersField.getText().trim().isEmpty()
            );
        });
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    return new College(
                        nameField.getText().trim(),
                        Double.parseDouble(placementField.getText().trim()),
                        Double.parseDouble(salaryField.getText().trim()),
                        Integer.parseInt(recruitersField.getText().trim())
                    );
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for numeric fields.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<College> result = dialog.showAndWait();
        result.ifPresent(college -> {
            colleges.add(college);
            dataService.addCollege(college);
            updateCollegeChart();
            showAlert("Success", "College added successfully.");
        });
    }
    
    @FXML
    private void showAddResearchDialog() {
        Dialog<ResearchPaper> dialog = new Dialog<>();
        dialog.setTitle("Add New Research Paper");
        dialog.setHeaderText("Enter research paper details:");
        dialog.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Create form fields with validation
        TextField titleField = new TextField();
        titleField.setPromptText("Paper Title");
        titleField.setPrefWidth(300);
        
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        authorField.setPrefWidth(300);
        
        TextField citationsField = new TextField();
        citationsField.setPromptText("Citations (e.g., 500)");
        citationsField.setPrefWidth(300);
        
        TextField yearField = new TextField();
        yearField.setPromptText("Publication Year (e.g., 2023)");
        yearField.setPrefWidth(300);
        
        // Create grid with better styling
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Citations:"), 0, 2);
        grid.add(citationsField, 1, 2);
        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearField, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        // Add buttons
        ButtonType addButton = new ButtonType("Add Research", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        
        // Apply validation
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);
        
        // Enable add button when all fields are filled
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                titleField.getText().trim().isEmpty() ||
                authorField.getText().trim().isEmpty() ||
                citationsField.getText().trim().isEmpty() ||
                yearField.getText().trim().isEmpty()
            );
        });
        
        authorField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                titleField.getText().trim().isEmpty() ||
                authorField.getText().trim().isEmpty() ||
                citationsField.getText().trim().isEmpty() ||
                yearField.getText().trim().isEmpty()
            );
        });
        
        citationsField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                titleField.getText().trim().isEmpty() ||
                authorField.getText().trim().isEmpty() ||
                citationsField.getText().trim().isEmpty() ||
                yearField.getText().trim().isEmpty()
            );
        });
        
        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButtonNode.setDisable(
                titleField.getText().trim().isEmpty() ||
                authorField.getText().trim().isEmpty() ||
                citationsField.getText().trim().isEmpty() ||
                yearField.getText().trim().isEmpty()
            );
        });
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    return new ResearchPaper(
                        titleField.getText().trim(),
                        authorField.getText().trim(),
                        Integer.parseInt(citationsField.getText().trim()),
                        Integer.parseInt(yearField.getText().trim())
                    );
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for citations and year.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<ResearchPaper> result = dialog.showAndWait();
        result.ifPresent(paper -> {
            researchPapers.add(paper);
            dataService.addResearch(paper);
            updateResearchChart();
            showAlert("Success", "Research paper added successfully.");
        });
    }
    
    @FXML
    private void generateSummaryReport() {
        StringBuilder summary = new StringBuilder();
        summary.append("Generated on: ").append(new java.util.Date()).append("\n\n");
        
        summary.append("🏢 BUSINESS ANALYTICS\n");
        summary.append("=====================\n");
        for (Company company : companies) {
            summary.append(company.getName()).append("\n");
            summary.append("   Revenue: $").append(String.format("%.2f", company.getRevenue()));
            summary.append(" | Profit: $").append(String.format("%.2f", company.getProfit()));
            summary.append(" | Growth: ").append(String.format("%.2f", company.getGrowthPercent())).append("%");
            summary.append(" | Score: ").append(String.format("%.2f", company.getScore())).append("\n");
        }
        
        summary.append("\n🎓 COLLEGE PLACEMENT ANALYTICS\n");
        summary.append("============================\n");
        for (College college : colleges) {
            summary.append(college.getName()).append("\n");
            summary.append("   Placement Rate: ").append(String.format("%.2f", college.getPlacementRate())).append("%");
            summary.append(" | Avg Salary: $").append(String.format("%.2f", college.getAvgSalary()));
            summary.append(" | Recruiters: ").append(college.getRecruiters());
            summary.append(" | Score: ").append(String.format("%.2f", college.getScore())).append("\n");
        }
        
        summary.append("\n📚 RESEARCH ANALYTICS\n");
        summary.append("====================\n");
        for (ResearchPaper paper : researchPapers) {
            summary.append(paper.getTitle()).append("\n");
            summary.append("   Author: ").append(paper.getAuthor());
            summary.append(" | Citations: ").append(paper.getCitations());
            summary.append(" | Year: ").append(paper.getYear());
            summary.append(" | Impact: ").append(String.format("%.2f", paper.getScore())).append("\n");
        }
        
        summaryTextArea.setText(summary.toString());
        showAlert("Success", "Summary report generated successfully.");
    }
    
    @FXML
    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        
        // Update theme toggle button icon
        themeToggleBtn.setText(isDarkTheme ? "🌙" : "☀️");
        
        // Get the main scene
        Node rootNode = businessPanel.getScene().getRoot();
        if (rootNode != null) {
            if (isDarkTheme) {
                rootNode.getStyleClass().remove("light-theme");
                // Ensure dark theme is applied
                rootNode.setStyle("-fx-background-color: #0f172a;");
            } else {
                rootNode.getStyleClass().add("light-theme");
                // Ensure light theme is applied
                rootNode.setStyle("-fx-background-color: #f8fafc;");
            }
        }
        
        System.out.println("Theme toggled. Current theme: " + (isDarkTheme ? "Dark" : "Light"));
    }
    
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("This will close the application and return to the login screen.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Stop data service
            if (dataService != null) {
                dataService.stop();
            }
            javafx.application.Platform.exit();
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void showNotifications() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifications");
        alert.setHeaderText("Recent Notifications");
        alert.setContentText("• System updated successfully\n• New data available\n• Backup completed");
        alert.showAndWait();
    }
    
    private void updateActiveNavButton(Button activeButton) {
        // Remove active class from all nav buttons
        businessBtn.getStyleClass().remove("active");
        collegeBtn.getStyleClass().remove("active");
        researchBtn.getStyleClass().remove("active");
        summaryBtn.getStyleClass().remove("active");
        
        // Add active class to the selected button
        activeButton.getStyleClass().add("active");
    }
}