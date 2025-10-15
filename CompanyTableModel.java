import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CompanyTableModel extends AbstractTableModel {
    private List<Company> companies;
    private String[] columnNames = {"Company Name", "Revenue ($)", "Profit ($)", "Growth (%)", "Score"};

    public CompanyTableModel() {
        this.companies = new ArrayList<>();
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return companies.size();
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
        Company company = companies.get(rowIndex);
        switch (columnIndex) {
            case 0: return company.getName();
            case 1: return String.format("%.2f", company.getRevenue());
            case 2: return String.format("%.2f", company.getProfit());
            case 3: return String.format("%.2f", company.getGrowthPercent());
            case 4: return String.format("%.2f", company.getScore());
            default: return null;
        }
    }

    public Company getCompanyAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < companies.size()) {
            return companies.get(rowIndex);
        }
        return null;
    }
}