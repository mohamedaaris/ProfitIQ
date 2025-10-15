public class ResearchPaper {
    private String title;
    private String author;
    private int citations;
    private int year;

    public ResearchPaper() {
    }

    public ResearchPaper(String title, String author, int citations, int year) {
        this.title = title;
        this.author = author;
        this.citations = citations;
        this.year = year;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCitations() {
        return citations;
    }

    public int getYear() {
        return year;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Calculate research score: citations / (currentYear - year + 1)
    public double getScore() {
        int currentYear = java.time.Year.now().getValue();
        int yearsSincePublication = currentYear - year + 1;
        if (yearsSincePublication <= 0) {
            return citations;
        }
        return (double) citations / yearsSincePublication;
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", citations=" + citations +
                ", year=" + year +
                '}';
    }
}