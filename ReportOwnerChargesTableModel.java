package assignment1;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Table model for displaying all charges for each boat owned by a specific owner.
 */
public class ReportOwnerChargesTableModel extends AbstractTableModel {
    /** Column names for the owner charges table */
    private final String[] columns = {
        "Boat Details", "Charge Type", "Amount ($)"
    };
    /** Table data: boat details, charge type, and amount */
    private final ArrayList<Object[]> data;

    /**
     * Constructs the owner charges table model for a specific owner.
     * @param owner the owner whose boat charges are to be displayed
     */
    public ReportOwnerChargesTableModel(Owner owner) {
        data = new ArrayList<>();
        double totalCharge = 0.0;
        int boatCount = 1;
        for (Boat boat : owner.getBoats()) {
            String boatType = (boat instanceof SailBoat)
                ? "SailBoat" : "MotorBoat";
            String boatAttributes = "";
            if (boat instanceof SailBoat) {
                SailBoat sb = (SailBoat) boat;
                boatAttributes = String.format(
                    "[height=%.1f, length=%.1f, width=%.1f, value=%.1f, " +
                    "mastHeight=%.1f, sailArea=%.1f]",
                    sb.getHeight(), sb.getLength(), sb.getWidth(),
                    sb.getBoatValue(), sb.getMastHeight(),
                    sb.getSailArea()
                );
            } else if (boat instanceof MotorBoat) {
                MotorBoat mb = (MotorBoat) boat;
                boatAttributes = String.format(
                    "[height=%.1f, length=%.1f, width=%.1f, value=%.1f, " +
                    "horsePower=%d]",
                    mb.getHeight(), mb.getLength(), mb.getWidth(),
                    mb.getBoatValue(), mb.getHorsePower()
                );
            }
            data.add(new Object[]{
                "Boat " + boatCount + ": " + boatType,
                "Storage charge",
                String.format("$%.2f", boat.storageCharge())
            });
            data.add(new Object[]{
                boatAttributes,
                "Insurance charge",
                String.format("$%.2f", boat.insuranceLevy())
            });
            if (boat instanceof SailBoat) {
                data.add(new Object[]{
                    "",
                    "Sail drying charge",
                    String.format(
                        "$%.2f", ((SailBoat) boat).sailDryingCharge()
                    )
                });
            } else if (boat instanceof MotorBoat) {
                data.add(new Object[]{
                    "",
                    "Fire levy charge",
                    String.format(
                        "$%.2f", ((MotorBoat) boat).fireLevyCharge()
                    )
                });
            }
            data.add(new Object[]{
                "",
                "Total monthly charge",
                String.format("$%.2f", boat.totalMonthlyCharge())
            });
            totalCharge += boat.totalMonthlyCharge();
            boatCount++;
        }
        data.add(new Object[]{
            "TOTAL",
            "TOTAL MONTHLY CHARGE FOR ALL BOATS",
            String.format("$%.2f", totalCharge)
        });
    }

    /**
     * Returns the number of rows in the table.
     * @return row count
     */
    @Override
    public int getRowCount() {
        return data.size();
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
        return data.get(row)[col];
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
