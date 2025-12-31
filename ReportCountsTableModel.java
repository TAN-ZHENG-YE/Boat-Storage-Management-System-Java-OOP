package assignment1;

import javax.swing.table.AbstractTableModel;

/**
 * Table model for displaying the count of sail boats, motor boats, and total boats.
 */
public class ReportCountsTableModel extends AbstractTableModel {
    /** Column names for the counts table */
    private final String[] columns = {"Boat Type", "Count"};
    /** Table data: boat type and count */
    private final Object[][] data;

    /**
     * Constructs the counts table model.
     * @param sail number of sail boats
     * @param motor number of motor boats
     * @param total total number of boats
     */
    public ReportCountsTableModel(int sail, int motor, int total) {
        data = new Object[][] {
            {"Sail Boats", sail},
            {"Motor Boats", motor},
            {"TOTAL BOATS", total}
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
