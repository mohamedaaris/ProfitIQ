import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Real-time Data Service for ProfitIQ Dashboard
 * Handles database connections, data fetching, and real-time updates
 */
public class DataService {
    
    private static DataService instance;
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean isRunning;
    private final Map<String, List<Consumer<Object>>> dataListeners;
    
    // Data caches
    private volatile List<Company> companiesCache;
    private volatile List<College> collegesCache;
    private volatile List<ResearchPaper> researchCache;
    private volatile Map<String, Object> summaryCache;
    
    private DataService() {
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.isRunning = new AtomicBoolean(false);
        this.dataListeners = new ConcurrentHashMap<>();
        this.summaryCache = new ConcurrentHashMap<>();
        
        // Initialize caches
        this.companiesCache = new ArrayList<>();
        this.collegesCache = new ArrayList<>();
        this.researchCache = new ArrayList<>();
    }
    
    public static synchronized DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    
    /**
     * Start the data service with real-time updates
     */
    public void start() {
        System.out.println("DataService.start() called");
        if (isRunning.compareAndSet(false, true)) {
            System.out.println("Starting DataService");
            // Load initial data
            loadAllData();
            
            // Schedule periodic updates
            scheduler.scheduleAtFixedRate(this::loadCompaniesData, 0, 30, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(this::loadCollegesData, 5, 30, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(this::loadResearchData, 10, 30, TimeUnit.SECONDS);
            scheduler.scheduleAtFixedRate(this::loadSummaryData, 15, 60, TimeUnit.SECONDS);
            
            System.out.println("DataService started with real-time updates");
        } else {
            System.out.println("DataService already running");
        }
    }
    
    /**
     * Stop the data service
     */
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("DataService stopped");
        }
    }
    
    /**
     * Load all data initially
     */
    private void loadAllData() {
        loadCompaniesData();
        loadCollegesData();
        loadResearchData();
        loadSummaryData();
    }
    
    /**
     * Load companies data from database
     */
    private void loadCompaniesData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT name, revenue, profit, growth_percent FROM companies ORDER BY name";
            
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                List<Company> companies = new ArrayList<>();
                while (rs.next()) {
                    Company company = new Company();
                    company.setName(rs.getString("name"));
                    company.setRevenue(rs.getDouble("revenue"));
                    company.setProfit(rs.getDouble("profit"));
                    company.setGrowthPercent(rs.getDouble("growth_percent"));
                    companies.add(company);
                }
                
                System.out.println("Loaded " + companies.size() + " companies from database");
                this.companiesCache = companies;
                notifyListeners("companies", companies);
                
            }
        } catch (SQLException e) {
            System.err.println("Error loading companies data: " + e.getMessage());
            // Load sample data if database fails
            loadSampleCompaniesData();
        }
    }
    
    /**
     * Load colleges data from database
     */
    private void loadCollegesData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT name, placement_rate, avg_salary, recruiters FROM colleges ORDER BY name";
            
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                List<College> colleges = new ArrayList<>();
                while (rs.next()) {
                    College college = new College();
                    college.setName(rs.getString("name"));
                    college.setPlacementRate(rs.getDouble("placement_rate"));
                    college.setAvgSalary(rs.getDouble("avg_salary"));
                    college.setRecruiters(rs.getInt("recruiters"));
                    colleges.add(college);
                }
                
                System.out.println("Loaded " + colleges.size() + " colleges from database");
                this.collegesCache = colleges;
                notifyListeners("colleges", colleges);
                
            }
        } catch (SQLException e) {
            System.err.println("Error loading colleges data: " + e.getMessage());
            // Load sample data if database fails
            loadSampleCollegesData();
        }
    }
    
    /**
     * Load research data from database
     */
    private void loadResearchData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT paper_title, author, citations, year FROM research ORDER BY year DESC, citations DESC";
            
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                List<ResearchPaper> research = new ArrayList<>();
                while (rs.next()) {
                    ResearchPaper paper = new ResearchPaper();
                    paper.setTitle(rs.getString("paper_title"));
                    paper.setAuthor(rs.getString("author"));
                    paper.setCitations(rs.getInt("citations"));
                    paper.setYear(rs.getInt("year"));
                    research.add(paper);
                }
                
                System.out.println("Loaded " + research.size() + " research papers from database");
                this.researchCache = research;
                notifyListeners("research", research);
                
            }
        } catch (SQLException e) {
            System.err.println("Error loading research data: " + e.getMessage());
            // Load sample data if database fails
            loadSampleResearchData();
        }
    }
    
    /**
     * Load summary data and calculate KPIs
     */
    private void loadSummaryData() {
        Map<String, Object> summary = new HashMap<>();
        
        // Calculate business metrics
        double totalRevenue = companiesCache.stream().mapToDouble(Company::getRevenue).sum();
        double totalProfit = companiesCache.stream().mapToDouble(Company::getProfit).sum();
        double avgGrowth = companiesCache.stream().mapToDouble(Company::getGrowthPercent).average().orElse(0.0);
        
        summary.put("totalRevenue", totalRevenue);
        summary.put("totalProfit", totalProfit);
        summary.put("avgGrowth", avgGrowth);
        summary.put("companyCount", companiesCache.size());
        
        // Calculate college metrics
        double avgPlacementRate = collegesCache.stream().mapToDouble(College::getPlacementRate).average().orElse(0.0);
        double avgSalary = collegesCache.stream().mapToDouble(College::getAvgSalary).average().orElse(0.0);
        int totalRecruiters = collegesCache.stream().mapToInt(College::getRecruiters).sum();
        
        summary.put("avgPlacementRate", avgPlacementRate);
        summary.put("avgSalary", avgSalary);
        summary.put("totalRecruiters", totalRecruiters);
        summary.put("collegeCount", collegesCache.size());
        
        // Calculate research metrics
        int totalCitations = researchCache.stream().mapToInt(ResearchPaper::getCitations).sum();
        double avgCitations = researchCache.stream().mapToInt(ResearchPaper::getCitations).average().orElse(0.0);
        int currentYear = java.time.Year.now().getValue();
        long recentPapers = researchCache.stream().filter(p -> p.getYear() >= currentYear - 2).count();
        
        summary.put("totalCitations", totalCitations);
        summary.put("avgCitations", avgCitations);
        summary.put("recentPapers", recentPapers);
        summary.put("researchCount", researchCache.size());
        
        // Calculate overall KPIs
        summary.put("totalCustomers", calculateTotalCustomers());
        summary.put("totalOrders", calculateTotalOrders());
        summary.put("retentionRate", calculateRetentionRate());
        
        this.summaryCache = summary;
        notifyListeners("summary", summary);
    }
    
    /**
     * Add a data listener for real-time updates
     */
    public void addDataListener(String dataType, Consumer<Object> listener) {
        dataListeners.computeIfAbsent(dataType, k -> new ArrayList<>()).add(listener);
    }
    
    /**
     * Remove a data listener
     */
    public void removeDataListener(String dataType, Consumer<Object> listener) {
        List<Consumer<Object>> listeners = dataListeners.get(dataType);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }
    
    /**
     * Notify all listeners of data changes
     */
    private void notifyListeners(String dataType, Object data) {
        List<Consumer<Object>> listeners = dataListeners.get(dataType);
        if (listeners != null) {
            listeners.forEach(listener -> {
                try {
                    listener.accept(data);
                } catch (Exception e) {
                    System.err.println("Error notifying listener: " + e.getMessage());
                }
            });
        }
    }
    
    // Getters for cached data
    public List<Company> getCompanies() {
        return new ArrayList<>(companiesCache);
    }
    
    public List<College> getColleges() {
        return new ArrayList<>(collegesCache);
    }
    
    public List<ResearchPaper> getResearch() {
        return new ArrayList<>(researchCache);
    }
    
    public Map<String, Object> getSummary() {
        return new HashMap<>(summaryCache);
    }
    
    // Data manipulation methods
    public boolean addCompany(Company company) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO companies (name, revenue, profit, growth_percent) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, company.getName());
                stmt.setDouble(2, company.getRevenue());
                stmt.setDouble(3, company.getProfit());
                stmt.setDouble(4, company.getGrowthPercent());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    loadCompaniesData(); // Reload data
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding company: " + e.getMessage());
        }
        return false;
    }
    
    public boolean addCollege(College college) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO colleges (name, placement_rate, avg_salary, recruiters) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, college.getName());
                stmt.setDouble(2, college.getPlacementRate());
                stmt.setDouble(3, college.getAvgSalary());
                stmt.setInt(4, college.getRecruiters());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    loadCollegesData(); // Reload data
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding college: " + e.getMessage());
        }
        return false;
    }
    
    public boolean addResearch(ResearchPaper paper) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO research (paper_title, author, citations, year) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, paper.getTitle());
                stmt.setString(2, paper.getAuthor());
                stmt.setInt(3, paper.getCitations());
                stmt.setInt(4, paper.getYear());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    loadResearchData(); // Reload data
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding research paper: " + e.getMessage());
        }
        return false;
    }
    
    // Sample data methods for when database is unavailable
    private void loadSampleCompaniesData() {
        List<Company> companies = Arrays.asList(
            new Company("TechCorp Inc", 1245000.0, 325000.0, 12.5),
            new Company("Global Solutions Ltd", 980000.0, 215000.0, 18.3),
            new Company("Innovate Systems", 750000.0, 180000.0, 22.1),
            new Company("Digital Dynamics", 2100000.0, 450000.0, 8.7),
            new Company("Future Tech", 1650000.0, 380000.0, 15.2)
        );
        this.companiesCache = companies;
        notifyListeners("companies", companies);
    }
    
    private void loadSampleCollegesData() {
        List<College> colleges = Arrays.asList(
            new College("MIT", 95.5, 120000.0, 150),
            new College("Stanford", 93.75, 115000.0, 140),
            new College("Harvard", 90.25, 110000.0, 130),
            new College("Berkeley", 88.5, 105000.0, 125),
            new College("Carnegie Mellon", 92.0, 118000.0, 135)
        );
        this.collegesCache = colleges;
        notifyListeners("colleges", colleges);
    }
    
    private void loadSampleResearchData() {
        List<ResearchPaper> research = Arrays.asList(
            new ResearchPaper("Machine Learning Applications in Finance", "Dr. Smith", 245, 2021),
            new ResearchPaper("Blockchain Technology for Supply Chain", "Prof. Johnson", 187, 2020),
            new ResearchPaper("AI-Driven Decision Making", "Dr. Williams", 203, 2022),
            new ResearchPaper("Quantum Computing Breakthrough", "Dr. Brown", 156, 2021),
            new ResearchPaper("Neural Networks in Healthcare", "Dr. Davis", 198, 2020)
        );
        this.researchCache = research;
        notifyListeners("research", research);
    }
    
    // Helper methods for KPI calculations
    private int calculateTotalCustomers() {
        // Simulate customer calculation based on companies and colleges
        return companiesCache.size() * 150 + collegesCache.size() * 200;
    }
    
    private int calculateTotalOrders() {
        // Simulate order calculation
        return (int) (companiesCache.stream().mapToDouble(Company::getRevenue).sum() / 1000);
    }
    
    private double calculateRetentionRate() {
        // Simulate retention rate calculation
        return 75.0 + (companiesCache.size() * 2.5) + (collegesCache.size() * 1.8);
    }
    
    /**
     * Test database connection
     */
    public boolean testConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return conn.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Get connection status
     */
    public String getConnectionStatus() {
        if (testConnection()) {
            return "Connected";
        } else {
            return "Disconnected (Using Sample Data)";
        }
    }
}
