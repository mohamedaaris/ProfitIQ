package swing.model;

public class ResearchPaper {
    private String title;
    private String author;
    private int citations;
    private int year;
    private double score;

    public ResearchPaper(String title, String author, int citations, int year) {
        this.title = title;
        this.author = author;
        this.citations = citations;
        this.year = year;
        // Calculate score based on citations and recency
        this.score = citations * (1 + (year - 2020) * 0.1);
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCitations() { return citations; }
    public int getYear() { return year; }
    public double getScore() { return score; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCitations(int citations) { this.citations = citations; }
    public void setYear(int year) { this.year = year; }
    
    // Recalculate score when metrics change
    public void updateScore() {
        this.score = citations * (1 + (year - 2020) * 0.1);
    }
}