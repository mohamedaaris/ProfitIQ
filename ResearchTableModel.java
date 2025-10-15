import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ResearchTableModel extends AbstractTableModel {
    private List<ResearchPaper> papers;
    private String[] columnNames = {"Title", "Author", "Citations", "Year", "Score"};

    public ResearchTableModel() {
        this.papers = new ArrayList<>();
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return papers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ResearchPaper paper = papers.get(rowIndex);
        switch (columnIndex) {
            case 0: return paper.getTitle();
            case 1: return paper.getAuthor();
            case 2: return paper.getCitations();
            case 3: return paper.getYear();
            case 4: return String.format("%.2f", paper.getScore());
            default: return null;
        }
    }

    public ResearchPaper getPaperAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < papers.size()) {
            return papers.get(rowIndex);
        }
        return null;
    }
}