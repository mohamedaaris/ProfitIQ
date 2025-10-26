package javafx.model;

public class College {
    private String name;
    private double placementRate;
    private double avgSalary;
    private int recruiters;
    private double score;

    public College(String name, double placementRate, double avgSalary, int recruiters) {
        this.name = name;
        this.placementRate = placementRate;
        this.avgSalary = avgSalary;
        this.recruiters = recruiters;
        // Calculate score based on metrics
        this.score = (placementRate / 10) + (avgSalary / 10000) + recruiters;
    }

    // Getters
    public String getName() { return name; }
    public double getPlacementRate() { return placementRate; }
    public double getAvgSalary() { return avgSalary; }
    public int getRecruiters() { return recruiters; }
    public double getScore() { return score; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPlacementRate(double placementRate) { this.placementRate = placementRate; }
    public void setAvgSalary(double avgSalary) { this.avgSalary = avgSalary; }
    public void setRecruiters(int recruiters) { this.recruiters = recruiters; }
    
    // Recalculate score when metrics change
    public void updateScore() {
        this.score = (placementRate / 10) + (avgSalary / 10000) + recruiters;
    }
}