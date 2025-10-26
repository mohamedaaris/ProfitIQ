import javafx.data.DataService;
import javafx.model.Company;
import javafx.model.College;
import javafx.model.ResearchPaper;
import java.util.List;

public class DataServiceTest {
    public static void main(String[] args) {
        System.out.println("Testing DataService with database connection...");
        
        try {
            DataService dataService = DataService.getInstance();
            
            // Test fetching companies
            List<Company> companies = dataService.getCompanies();
            System.out.println("Loaded " + companies.size() + " companies from database:");
            for (Company company : companies) {
                System.out.println("  - " + company.getName() + ": Revenue=" + company.getRevenue() + 
                                 ", Profit=" + company.getProfit() + ", Growth=" + company.getGrowthPercent() + "%");
            }
            
            // Test fetching colleges
            List<College> colleges = dataService.getColleges();
            System.out.println("\nLoaded " + colleges.size() + " colleges from database:");
            for (College college : colleges) {
                System.out.println("  - " + college.getName() + ": Placement Rate=" + college.getPlacementRate() + 
                                 "%, Avg Salary=" + college.getAvgSalary() + ", Recruiters=" + college.getRecruiters());
            }
            
            // Test fetching research papers
            List<ResearchPaper> researchPapers = dataService.getResearch();
            System.out.println("\nLoaded " + researchPapers.size() + " research papers from database:");
            for (ResearchPaper paper : researchPapers) {
                System.out.println("  - \"" + paper.getTitle() + "\" by " + paper.getAuthor() + 
                                 ": " + paper.getCitations() + " citations (" + paper.getYear() + ")");
            }
            
            System.out.println("\nDataService test completed successfully!");
        } catch (Exception e) {
            System.err.println("Error testing DataService: " + e.getMessage());
            e.printStackTrace();
        }
    }
}