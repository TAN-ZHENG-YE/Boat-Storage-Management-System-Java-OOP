package assignment1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for displaying and managing the list of owners in a table.
 */
public class OwnerTabPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /** Table model for owners */
    private OwnerTableModel ownerTableModel;
    /** JTable displaying owners */
    private JTable ownerTable;
    /** Reference to the main BoatStorage data */
    private BoatStorage boatStorage;

    /**
     * Constructs the owner tab panel with buttons and table.
     * Sets up listeners for add/edit/sort actions and initializes the table.
     * @param boatStorage the main data model
     * @param showAddOwnerDialog callback to show add owner dialog
     * @param showEditOwnerDialog callback to show edit owner dialog
     * @param showOwnerSortDialog callback to show sort dialog
     */
    public OwnerTabPanel(
            BoatStorage boatStorage,
            Runnable showAddOwnerDialog,
            java.util.function.Consumer<Owner> showEditOwnerDialog,
            Runnable showOwnerSortDialog
    ) {
        this.boatStorage = boatStorage;
        setLayout(new BorderLayout());

        ownerTableModel = new OwnerTableModel(boatStorage.getOwners());
        ownerTable = new JTable(ownerTableModel);
        JScrollPane scrollPane = new JScrollPane(ownerTable);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Owner");
        JButton editBtn = new JButton("Edit Owner");
        JButton sortBtn = new JButton("Sort Owners");

        addBtn.addActionListener(e -> showAddOwnerDialog.run());
        editBtn.addActionListener(e -> {
            int row = ownerTable.getSelectedRow();
            if (row >= 0) {
                Owner owner = ownerTableModel.getOwnerAt(row);
                System.out.println("Edit Owner - Selected owner: " +
                    owner.getName());
                showEditOwnerDialog.accept(owner);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select an owner to edit.");
            }
        });
        sortBtn.addActionListener(e -> showOwnerSortDialog.run());

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(sortBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Refreshes the owner table with the latest data from boatStorage.
     * Call this after any change to the owners or their boats.
     */
    public void refreshTable() {
        ownerTableModel.setOwners(boatStorage.getOwners());
    }

    /**
     * Sets the table data to a new list of owners (e.g., after sorting).
     * @param owners the new list of owners to display
     */
    public void setTableData(ArrayList<Owner> owners) {
        ownerTableModel.setOwners(owners);
    }

    /**
     * Gets the owner table model.
     * @return the OwnerTableModel
     */
    public OwnerTableModel getOwnerTableModel() {
        return ownerTableModel;
    }

    /**
     * Gets the JTable displaying owners.
     * @return the JTable
     */
    public JTable getOwnerTable() {
        return ownerTable;
    }

    /**
     * Updates the BoatStorage reference and refreshes the table model.
     * Use this after loading a new database.
     * @param boatStorage the new BoatStorage to use
     */
    public void setBoatStorage(BoatStorage boatStorage) {
        this.boatStorage = boatStorage;
        ownerTableModel = new OwnerTableModel(boatStorage.getOwners());
        ownerTable.setModel(ownerTableModel);
    }
}
