package javafx.util;

import javafx.model.Company;
import javafx.model.College;
import javafx.model.ResearchPaper;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DataService {
    private static DataService instance;
    private ScheduledExecutorService scheduler;
    private Map<String, List<Consumer<Object>>> listeners;
    
    // Sample data
    private List<Company> companies;
    private List<College> colleges;
    private List<ResearchPaper> researchPapers;
    private Map<String, Object> summaryData;
    
    private DataService() {
        listeners = new HashMap<>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        initializeSampleData();
    }
    
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
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
        notifyListeners("companies", companies);
    }
    
    public void addCollege(College college) {
        colleges.add(college);
        notifyListeners("colleges", colleges);
    }
    
    public void addResearch(ResearchPaper paper) {
        researchPapers.add(paper);
        notifyListeners("research", researchPapers);
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
}