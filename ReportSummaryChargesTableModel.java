package assignment1;

import javax.swing.table.AbstractTableModel;

/**
 * Table model for displaying a summary of all charge 
 * and total monthly income.
 */
public class ReportSummaryChargesTableModel extends AbstractTableModel {
    /** Column names for the summary table */
    private final String[] columns = {"Charge Type", "Amount ($)"};
    /** Table data: charge type and amount */
    private final Object[][] data;

    /**
     * Constructs the summary charges table model.
     * @param storage total storage charges
     * @param sailDrying total sail drying charges
     * @param fireLevy total fire levy charges
     * @param insurance total insurance charges
     */
    public ReportSummaryChargesTableModel(double storage,
                double sailDrying, double fireLevy, double insurance) {
        double total = storage + sailDrying + fireLevy + insurance;
        data = new Object[][] {
            {"Storage Charges", String.format("%.2f", storage)},
            {"Sail Drying Charges", String.format("%.2f", sailDrying)},
            {"Fire Levy Charges", String.format("%.2f", fireLevy)},
            {"Insurance Charges", String.format("%.2f", insurance)},
            {"TOTAL MONTHLY INCOME", String.format("%.2f", total)}
        };
    }

    /**
     * Returns the number of rows in the table.
     * @return row count
     */
    @Override
    public int getRowCount() {
        return data.length;
    }

    /**
     * Returns the number of columns in the table.
     * @return column count
     */
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * Returns the value at the specified row and column.
     * @param row the row index
     * @param col the column index
     * @return the value for the cell
     */
    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /**
     * Returns the column name for the specified column index.
     * @param col the column index
     * @return the column name
     */
    @Override
    public String getColumnName(int col) {
        return columns[col];
    }
}
