package assignment1;  

import javax.swing.table.AbstractTableModel; 
import java.util.ArrayList;  

/**
 * Table model for displaying all boats in a flat table,
 * showing owner and boat details.
 */
public class BoatTableModel extends AbstractTableModel {
    /** Column names for the boat table */
    private static final String[] columns = {
        "Owner ID", "Owner Name", "Type", "Height", "Length", "Width",
        "Value", "Mast Height", "Sail Area", "Horse Power"
    };
    /** List of all owners (each may own multiple boats) */
    private ArrayList<Owner> owners;
    /** Flat list of (Owner, Boat) pairs, one per table row */
    private ArrayList<BoatOwnerPair> boatPairs;

    /**
     * Helper class to store a pair of Owner and Boat for each table row.
     */
    public static class BoatOwnerPair {
        /** The owner of the boat */
        public final Owner owner;
        /** The boat itself */
        public final Boat boat;

        /**
         * Constructs a BoatOwnerPair.
         * @param owner the owner of the boat
         * @param boat the boat
         */
        public BoatOwnerPair(Owner owner, Boat boat) {
            this.owner = owner;
            this.boat = boat;
        }
    }

    /**
     * Constructs the table model from a list of owners.
     * @param owners the list of owners (each with their boats)
     */
    public BoatTableModel(ArrayList<Owner> owners) {
        this.owners = owners;
        refreshBoatPairs();
    }

    /**
     * Rebuilds the flat list of (Owner, Boat) pairs for the table rows.
     */
    private void refreshBoatPairs() {
        boatPairs = new ArrayList<>();
        for (Owner owner : owners) {
            for (Boat boat : owner.getBoats()) {
                boatPairs.add(new BoatOwnerPair(owner, boat));
            }
        }
    }

    /**
     * Gets the (Owner, Boat) pair for a given table row.
     * @param row the row index
     * @return the BoatOwnerPair for the row
     */
    public BoatOwnerPair getBoatOwnerPair(int row) {
        return boatPairs.get(row);
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
        return boatPairs.size();
    }

    /**
     * Returns the value at the specified row and column.
     * @param row the row index
     * @param col the column index
     * @return the value for the cell
     */
    @Override
    public Object getValueAt(int row, int col) {
        BoatOwnerPair pair = boatPairs.get(row);
        Owner o = pair.owner;
        Boat b = pair.boat;
        switch (col) {
            case 0: return o.getIdNumber();
            case 1: return o.getName();
            case 2: return (b instanceof SailBoat)
                ? "SailBoat" : "MotorBoat";
            case 3: return b.getHeight();
            case 4: return b.getLength();
            case 5: return b.getWidth();
            case 6: return b.getBoatValue();
            case 7: return (b instanceof SailBoat)
                ? ((SailBoat) b).getMastHeight() : "-";
            case 8: return (b instanceof SailBoat)
                ? ((SailBoat) b).getSailArea() : "-";
            case 9: return (b instanceof MotorBoat)
                ? ((MotorBoat) b).getHorsePower() : "-";
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
     * Updates the owners list and rebuilds the flat pair list,
     * then refreshes the table.
     * @param owners the new list of owners
     */
    public void setOwners(ArrayList<Owner> owners) {
        this.owners = owners;
        refreshBoatPairs();
        fireTableDataChanged();
    }
}
