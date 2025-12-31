package assignment1;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for displaying and managing the list of boats in a table.
 */
public class BoatTabPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /** Table model for boats */
    private BoatTableModel boatTableModel;
    /** JTable displaying boats */
    private JTable boatTable;
    /** Reference to the main BoatStorage data */
    private BoatStorage boatStorage;

    /**
     * Constructs the boat tab panel with buttons and table.
     * Sets up listeners for add/edit actions and initializes the table.
     * @param boatStorage the main data model
     * @param showAddBoatDialog callback to show add boat dialog
     * @param showEditBoatDialog callback to show edit boat dialog
     */
    public BoatTabPanel(
            BoatStorage boatStorage,
            Runnable showAddBoatDialog,
            java.util.function.BiConsumer<Owner, Boat> showEditBoatDialog
    ) {
        this.boatStorage = boatStorage;
        setLayout(new BorderLayout());

        boatTableModel = new BoatTableModel(boatStorage.getOwners());
        boatTable = new JTable(boatTableModel);
        boatTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(boatTable);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Boat");
        JButton editBtn = new JButton("Edit Boat");

        addBtn.addActionListener(e -> showAddBoatDialog.run());
        editBtn.addActionListener(e -> {
            int row = boatTable.getSelectedRow();
            if (row >= 0) {
                BoatTableModel.BoatOwnerPair pair =
                    boatTableModel.getBoatOwnerPair(row);
                if (pair != null) {
                    showEditBoatDialog.accept(pair.owner, pair.boat);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Could not find the selected boat.");
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a boat to edit.");
            }
        });

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Refreshes the boat table with the latest data from boatStorage.
     * Call this after any change to the boats or owners.
     */
    public void refreshTable() {
        boatTableModel.setOwners(boatStorage.getOwners());
    }

    /**
     * Updates the BoatStorage reference and refreshes the table model.
     * Use this after loading a new database.
     * @param boatStorage the new BoatStorage to use
     */
    public void setBoatStorage(BoatStorage boatStorage) {
        this.boatStorage = boatStorage;
        boatTableModel = new BoatTableModel(boatStorage.getOwners());
        boatTable.setModel(boatTableModel);
    }

    /**
     * Gets the boat table model.
     * @return the BoatTableModel
     */
    public BoatTableModel getBoatTableModel() {
        return boatTableModel;
    }

    /**
     * Gets the JTable displaying boats.
     * @return the JTable
     */
    public JTable getBoatTable() {
        return boatTable;
    }
}
