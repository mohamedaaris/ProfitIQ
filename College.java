public class College {
    private String name;
    private double placementRate;
    private double avgSalary;
    private int recruiters;

    public College() {
    }

    public College(String name, double placementRate, double avgSalary, int recruiters) {
        this.name = name;
        this.placementRate = placementRate;
        this.avgSalary = avgSalary;
        this.recruiters = recruiters;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPlacementRate() {
        return placementRate;
    }

    public double getAvgSalary() {
        return avgSalary;
    }

    public int getRecruiters() {
        return recruiters;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPlacementRate(double placementRate) {
        this.placementRate = placementRate;
    }

    public void setAvgSalary(double avgSalary) {
        this.avgSalary = avgSalary;
    }

    public void setRecruiters(int recruiters) {
        this.recruiters = recruiters;
    }

    // Calculate placement score: (placement_rate × avg_salary) / 100
    public double getScore() {
        return (placementRate * avgSalary) / 100;
    }

    @Override
    public String toString() {
        return "College{" +
                "name='" + name + '\'' +
                ", placementRate=" + placementRate +
                ", avgSalary=" + avgSalary +
                ", recruiters=" + recruiters +
                '}';
    }
}