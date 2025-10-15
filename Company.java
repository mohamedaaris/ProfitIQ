public class Company {
    private String name;
    private double revenue;
    private double profit;
    private double growthPercent;

    public Company() {
    }

    public Company(String name, double revenue, double profit, double growthPercent) {
        this.name = name;
        this.revenue = revenue;
        this.profit = profit;
        this.growthPercent = growthPercent;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getRevenue() {
        return revenue;
    }

    public double getProfit() {
        return profit;
    }

    public double getGrowthPercent() {
        return growthPercent;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setGrowthPercent(double growthPercent) {
        this.growthPercent = growthPercent;
    }

    // Calculate company score: (profit / revenue) × growth_percent
    public double getScore() {
        if (revenue == 0) {
            return 0;
        }
        return (profit / revenue) * growthPercent;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", revenue=" + revenue +
                ", profit=" + profit +
                ", growthPercent=" + growthPercent +
                '}';
    }
}