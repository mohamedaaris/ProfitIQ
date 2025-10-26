package javafx.model;

public class Company {
    private String name;
    private double revenue;
    private double profit;
    private double growthPercent;
    private double score;

    public Company(String name, double revenue, double profit, double growthPercent) {
        this.name = name;
        this.revenue = revenue;
        this.profit = profit;
        this.growthPercent = growthPercent;
        // Calculate score based on metrics
        this.score = (revenue / 1000000) + (profit / 100000) + growthPercent;
    }

    // Getters
    public String getName() { return name; }
    public double getRevenue() { return revenue; }
    public double getProfit() { return profit; }
    public double getGrowthPercent() { return growthPercent; }
    public double getScore() { return score; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRevenue(double revenue) { this.revenue = revenue; }
    public void setProfit(double profit) { this.profit = profit; }
    public void setGrowthPercent(double growthPercent) { this.growthPercent = growthPercent; }
    
    // Recalculate score when metrics change
    public void updateScore() {
        this.score = (revenue / 1000000) + (profit / 100000) + growthPercent;
    }
}