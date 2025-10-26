package javafx.data;

import javafx.model.Company;
import javafx.model.College;
import javafx.model.ResearchPaper;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DataService {
    private static DataService instance;
    private ScheduledExecutorService scheduler;
    private Map<String, List<Consumer<Object>>> listeners;
    
    // Data lists
    private List<Company> companies;
    private List<College> colleges;
    private List<ResearchPaper> researchPapers;
    private Map<String, Object> summaryData;
    
    private DataService() {
        listeners = new HashMap<>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        loadDataFromDatabase();
    }
    
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    
    private void loadDataFromDatabase() {
        companies = new ArrayList<>();
        colleges = new ArrayList<>();
        researchPapers = new ArrayList<>();
        
        loadCompaniesFromDatabase();
        loadCollegesFromDatabase();
        loadResearchPapersFromDatabase();
        calculateSummaryData();
    }
    
    private void loadCompaniesFromDatabase() {
        String query = "SELECT name, revenue, profit, growth_percent FROM companies ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            companies.clear();
            while (rs.next()) {
                String name = rs.getString("name");
                double revenue = rs.getDouble("revenue");
                double profit = rs.getDouble("profit");
                double growthPercent = rs.getDouble("growth_percent");
                
                companies.add(new Company(name, revenue, profit, growthPercent));
            }
        } catch (SQLException e) {
            System.err.println("Error loading companies from database: " + e.getMessage());
            e.printStackTrace();
            // Fallback to sample data if database connection fails
            initializeSampleData();
        }
    }
    
    private void loadCollegesFromDatabase() {
        String query = "SELECT name, placement_rate, avg_salary, recruiters FROM colleges ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            colleges.clear();
            while (rs.next()) {
                String name = rs.getString("name");
                double placementRate = rs.getDouble("placement_rate");
                double avgSalary = rs.getDouble("avg_salary");
                int recruiters = rs.getInt("recruiters");
                
                colleges.add(new College(name, placementRate, avgSalary, recruiters));
            }
        } catch (SQLException e) {
            System.err.println("Error loading colleges from database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadResearchPapersFromDatabase() {
        String query = "SELECT paper_title, author, citations, year FROM research ORDER BY year DESC, citations DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            researchPapers.clear();
            while (rs.next()) {
                String title = rs.getString("paper_title");
                String author = rs.getString("author");
                int citations = rs.getInt("citations");
                int year = rs.getInt("year");
                
                researchPapers.add(new ResearchPaper(title, author, citations, year));
            }
        } catch (SQLException e) {
            System.err.println("Error loading research papers from database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void calculateSummaryData() {
        summaryData = new HashMap<>();
        
        double totalRevenue = companies.stream().mapToDouble(Company::getRevenue).sum();
        summaryData.put("totalRevenue", totalRevenue);
        
        // Other summary data would be calculated here or fetched from database
        summaryData.put("totalCustomers", 8429);
        summaryData.put("totalOrders", 1247);
        summaryData.put("retentionRate", 18.7);
    }
    
    private void initializeSampleData() {
        // Initialize companies
        companies = new ArrayList<>();
        companies.add(new Company("TechCorp", 1500000, 300000, 12.5));
        companies.add(new Company("Innovate Inc", 2200000, 450000, 18.2));
        companies.add(new Company("Global Solutions", 1800000, 350000, 15.7));
        companies.add(new Company("Future Systems", 1200000, 250000, 10.3));
        companies.add(new Company("Digital Dynamics", 2000000, 400000, 20.1));
        
        // Initialize colleges
        colleges = new ArrayList<>();
        colleges.add(new College("MIT", 95.2, 125000, 350));
        colleges.add(new College("Stanford", 93.8, 132000, 320));
        colleges.add(new College("Harvard", 91.5, 118000, 280));
        colleges.add(new College("Berkeley", 89.7, 115000, 250));
        colleges.add(new College("Caltech", 96.1, 140000, 200));
        
        // Initialize research papers
        researchPapers = new ArrayList<>();
        researchPapers.add(new ResearchPaper("Machine Learning Advances", "Dr. Smith", 1250, 2023));
        researchPapers.add(new ResearchPaper("Quantum Computing Breakthrough", "Dr. Johnson", 890, 2022));
        researchPapers.add(new ResearchPaper("Neural Network Optimization", "Dr. Williams", 650, 2023));
        researchPapers.add(new ResearchPaper("Data Science Applications", "Dr. Brown", 1100, 2021));
        researchPapers.add(new ResearchPaper("Artificial Intelligence Ethics", "Dr. Davis", 780, 2022));
        
        // Initialize summary data
        summaryData = new HashMap<>();
        summaryData.put("totalRevenue", 8700000.0);
        summaryData.put("totalCustomers", 8429);
        summaryData.put("totalOrders", 1247);
        summaryData.put("retentionRate", 18.7);
    }
    
    public List<Company> getCompanies() {
        return new ArrayList<>(companies);
    }
    
    public List<College> getColleges() {
        return new ArrayList<>(colleges);
    }
    
    public List<ResearchPaper> getResearch() {
        return new ArrayList<>(researchPapers);
    }
    
    public Map<String, Object> getSummary() {
        return new HashMap<>(summaryData);
    }
    
    public void addDataListener(String dataType, Consumer<Object> listener) {
        listeners.computeIfAbsent(dataType, k -> new ArrayList<>()).add(listener);
    }
    
    public void addCompany(Company company) {
        companies.add(company);
        // Also insert into database
        insertCompanyIntoDatabase(company);
        notifyListeners("companies", companies);
    }
    
    public void addCollege(College college) {
        colleges.add(college);
        // Also insert into database
        insertCollegeIntoDatabase(college);
        notifyListeners("colleges", colleges);
    }
    
    public void addResearch(ResearchPaper paper) {
        researchPapers.add(paper);
        // Also insert into database
        insertResearchPaperIntoDatabase(paper);
        notifyListeners("research", researchPapers);
    }
    
    private void insertCompanyIntoDatabase(Company company) {
        String query = "INSERT INTO companies (name, revenue, profit, growth_percent) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, company.getName());
            stmt.setDouble(2, company.getRevenue());
            stmt.setDouble(3, company.getProfit());
            stmt.setDouble(4, company.getGrowthPercent());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting company into database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void insertCollegeIntoDatabase(College college) {
        String query = "INSERT INTO colleges (name, placement_rate, avg_salary, recruiters) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, college.getName());
            stmt.setDouble(2, college.getPlacementRate());
            stmt.setDouble(3, college.getAvgSalary());
            stmt.setInt(4, college.getRecruiters());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting college into database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void insertResearchPaperIntoDatabase(ResearchPaper paper) {
        String query = "INSERT INTO research (paper_title, author, citations, year) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, paper.getTitle());
            stmt.setString(2, paper.getAuthor());
            stmt.setInt(3, paper.getCitations());
            stmt.setInt(4, paper.getYear());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting research paper into database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void notifyListeners(String dataType, Object data) {
        List<Consumer<Object>> typeListeners = listeners.get(dataType);
        if (typeListeners != null) {
            for (Consumer<Object> listener : typeListeners) {
                listener.accept(data);
            }
        }
    }
    
    public void start() {
        // Simulate real-time data updates
        scheduler.scheduleAtFixedRate(this::simulateDataUpdates, 5, 5, TimeUnit.SECONDS);
    }
    
    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    
    private void simulateDataUpdates() {
        // Randomly update some data to simulate real-time changes
        Random random = new Random();
        
        // Update a random company
        if (!companies.isEmpty()) {
            Company company = companies.get(random.nextInt(companies.size()));
            company.setRevenue(company.getRevenue() * (0.95 + random.nextFloat() * 0.1));
            company.setProfit(company.getProfit() * (0.95 + random.nextFloat() * 0.1));
            company.updateScore();
            notifyListeners("companies", companies);
        }
        
        // Update summary data
        summaryData.put("totalRevenue", ((Double) summaryData.get("totalRevenue")) * (0.995 + random.nextFloat() * 0.01));
        summaryData.put("totalCustomers", (Integer) summaryData.get("totalCustomers") + random.nextInt(10));
        notifyListeners("summary", summaryData);
    }
    
    // Authentication methods
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT id FROM users WHERE username = ? AND password_hash = crypt(?, password_hash)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If we have a result, authentication is successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registerUser(String username, String email, String password) {
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
            return false;
        }
    }
}