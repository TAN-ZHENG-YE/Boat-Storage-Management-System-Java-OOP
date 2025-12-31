package assignment1;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Table model for displaying a list of owners and their summary
 * information in a JTable.
 */
public class OwnerTableModel extends AbstractTableModel {
    /** Column names for the owner table */
    private static final String[] columns = {
        "ID", "Name", "Address", "Number of Boats",
        "Total Monthly Storage Charge"
    };
    /** List of owners to display in the table */
    private ArrayList<Owner> owners;

    /**
     * Constructs the table model from a list of owners.
     * @param owners the list of owners to display
     */
    public OwnerTableModel(ArrayList<Owner> owners) {
        this.owners = owners;
    }

    /**
     * Returns the Owner object at the specified row.
     * @param row the row index
     * @return the Owner at the given row
     */
    public Owner getOwnerAt(int row) {
        return owners.get(row);
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
     * Returns the number of rows in the table.
     * @return row count
     */
    @Override
    public int getRowCount() {
        return owners.size();
    }

    /**
     * Returns the value at the specified row and column.
     * @param row the row index
     * @param col the column index
     * @return the value for the cell
     */
    @Override
    public Object getValueAt(int row, int col) {
        Owner o = owners.get(row);
        switch (col) {
            case 0: return o.getIdNumber();
            case 1: return o.getName();
            case 2: return o.getAddress();
            case 3: return o.getBoats().size();
            case 4: return String.format(
                "%.2f", o.totalStorageCharge()
            );
            default: return "";
        }
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

    /**
     * Updates the owners list and refreshes the table.
     * @param owners the new list of owners
     */
    public void setOwners(ArrayList<Owner> owners) {
        this.owners = owners;
        fireTableDataChanged();
    }
}
