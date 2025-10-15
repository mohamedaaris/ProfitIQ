import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CollegeTableModel extends AbstractTableModel {
    private List<College> colleges;
    private String[] columnNames = {"College Name", "Placement Rate (%)", "Average Salary ($)", "Recruiters", "Score"};

    public CollegeTableModel() {
        this.colleges = new ArrayList<>();
    }

    public void setColleges(List<College> colleges) {
        this.colleges = colleges;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return colleges.size();
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
        College college = colleges.get(rowIndex);
        switch (columnIndex) {
            case 0: return college.getName();
            case 1: return String.format("%.2f", college.getPlacementRate());
            case 2: return String.format("%.2f", college.getAvgSalary());
            case 3: return college.getRecruiters();
            case 4: return String.format("%.2f", college.getScore());
            default: return null;
        }
    }

    public College getCollegeAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < colleges.size()) {
            return colleges.get(rowIndex);
        }
        return null;
    }
}