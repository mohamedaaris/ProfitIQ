import javafx.data.DataService;
import javafx.model.Company;
import javafx.model.College;
import javafx.model.ResearchPaper;
import java.util.List;

public class TestDatabaseAccess {
    public static void main(String[] args) {
        System.out.println("=== Testing Database Access ===");
        
        try {
            DataService dataService = DataService.getInstance();
            
            // Test fetching companies
            List<Company> companies = dataService.getCompanies();
            System.out.println("Companies in database: " + companies.size());
            for (Company company : companies) {
                System.out.println("  - " + company.getName() + " (Revenue: " + company.getRevenue() + ")");
            }
            
            // Test fetching colleges
            List<College> colleges = dataService.getColleges();
            System.out.println("\nColleges in database: " + colleges.size());
            for (College college : colleges) {
                System.out.println("  - " + college.getName() + " (Placement Rate: " + college.getPlacementRate() + "%)");
            }
            
            // Test fetching research papers
            List<ResearchPaper> researchPapers = dataService.getResearch();
            System.out.println("\nResearch papers in database: " + researchPapers.size());
            for (ResearchPaper paper : researchPapers) {
                System.out.println("  - " + paper.getTitle() + " by " + paper.getAuthor() + " (" + paper.getCitations() + " citations)");
            }
            
            System.out.println("\n=== Database Access Test Complete ===");
            
        } catch (Exception e) {
            System.err.println("Error testing database access: " + e.getMessage());
            e.printStackTrace();
        }
    }
}