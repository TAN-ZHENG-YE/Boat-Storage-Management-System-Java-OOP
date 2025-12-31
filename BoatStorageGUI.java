package assignment1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Main GUI class for Bond's Boat Storage System.
 * Handles the main window, menu, tab panels, and file operations.
 */
public class BoatStorageGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    /** Main content panel for the window */
    private JPanel contentPane;

    /** Holds all owners and boats (main data model) */
    private static BoatStorage boatStorage = new BoatStorage();

    /** Counter for generating unique owner IDs */
    private static int ownerIdCounter = 1;

    /** Tracks the currently loaded or saved file */
    private File currentFile = null;

    /** Panel for displaying and managing owners */
    private OwnerTabPanel ownerTabPanel;
    /** Panel for displaying and managing boats */
    private BoatTabPanel boatTabPanel;
    /** Panel for displaying reports */
    private ReportTabPanel reportTabPanel;

    /**
     * Main entry point for the application.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BoatStorageGUI frame = new BoatStorageGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructs the main GUI window, sets up layout, menu, and tabs.
     * Initializes all tab panels and connects them to the data model.
     */
    public BoatStorageGUI() {
        setTitle("Bond's Boat Storage System - [New Database]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        setJMenuBar(createMenuBar());

        JTabbedPane tabbedPane = new JTabbedPane();

        ownerTabPanel = new OwnerTabPanel(
            boatStorage,
            () -> showOwnerDialog(null),
            owner -> showOwnerDialog(owner),
            this::showOwnerSortDialog
        );
        boatTabPanel = new BoatTabPanel(
            boatStorage,
            () -> showBoatDialog(null, null),
            (owner, boat) -> showBoatDialog(owner, boat)
        );
        reportTabPanel = new ReportTabPanel(boatStorage);

        tabbedPane.addTab("Owners", ownerTabPanel);
        tabbedPane.addTab("Boats", boatTabPanel);
        tabbedPane.addTab("Reports", reportTabPanel);

        setupTabRefresh(tabbedPane);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Updates the window title to reflect the current file or
     * indicate a new database.
     */
    private void updateTitle() {
        String title = "Bond's Boat Storage System - ";
        title += (currentFile != null)
            ? "[" + currentFile.getName() + "]"
            : "[New Database]";
        setTitle(title);
    }

    /**
     * Creates the menu bar with File menu options.
     * @return the constructed JMenuBar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem loadItem = new JMenuItem("Upload File...");
        JMenuItem exitItem = new JMenuItem("Exit");

        saveItem.addActionListener(e -> saveDatabase(false));
        saveAsItem.addActionListener(e -> saveDatabase(true));
        loadItem.addActionListener(e -> loadDatabase());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        return menuBar;
    }

    /**
     * Shows the dialog for adding or editing an owner.
     * @param owner the owner to edit, or null to add a new owner
     */
    private void showOwnerDialog(Owner owner) {
        OwnerDialog dialog = new OwnerDialog(
            this, boatStorage, owner, ownerIdCounter
        );
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        // Only increment counter for new owners
        if (owner == null && dialog.isOwnerAdded()) {
            String newOwnerId = String.valueOf(ownerIdCounter);
            ownerIdCounter++;
            ownerTabPanel.refreshTable();
            JOptionPane.showMessageDialog(
                this,
                "Owner added successfully with ID Number '" +
                newOwnerId + "' !"
            );
        } else if (owner != null && dialog.isOwnerEdited()) {  
            ownerTabPanel.refreshTable();
            JOptionPane.showMessageDialog(
                this,
                "Owner edited successfully."
            );
        }
    }

    /**
     * Shows a dialog to let the user choose how to sort owners,
     * and updates the table.
     */
    private void showOwnerSortDialog() {
        String[] options = {
            "Original Order",
            "By Name",
            "By Total Monthly Storage Charge"
        };
        int choice = JOptionPane.showOptionDialog(
            this,
            "Sort owners by:",
            "Sort Owners",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        ArrayList<Owner> sorted;
        switch (choice) {
            case 1:
                sorted = boatStorage.getOwnersSortedByName();
                break;
            case 2:
                sorted = boatStorage.getOwnersSortedByStorageCharges();
                break;
            default:
                sorted = boatStorage.getOwners();
        }
        ownerTabPanel.setTableData(sorted);
    }

    /**
     * Shows the dialog for adding or editing a boat.
     * Handles validation for owner existence and displays confirmation/error messages.
     * Refreshes all relevant tables after changes.
     * @param editOwner the owner to edit, or null to add a new boat
     * @param editBoat the boat to edit, or null to add a new boat
     */
    private void showBoatDialog(Owner editOwner, Boat editBoat) {
        System.out.println(
            "showBoatDialog called with owner: " +
            (editOwner != null ? editOwner.getName() : "null")
            + ", boat: " +
            (editBoat != null ? editBoat.toString() : "null")
        );
        if (boatStorage.getOwners().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No owners available. Please add an owner before adding a boat."
            );
            return;
        }
        try {
            BoatDialog dialog = new BoatDialog(
                this, boatStorage, editOwner, editBoat
            );
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            // Confirmation messages for add/edit boat
            if (editBoat == null && dialog.isBoatAdded()) {
                boatTabPanel.refreshTable();
                JOptionPane.showMessageDialog(
                    this,
                    "Boat added successfully."
                );
            } else if (editBoat != null && dialog.isBoatEdited()) {  
                boatTabPanel.refreshTable();
                JOptionPane.showMessageDialog(
                    this,
                    "Boat edited successfully."
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error showing BoatDialog: " + ex.getMessage()
            );
        }
    }

    /**
     * Saves the database to file using serialization.
     * @param saveAs if true, shows a Save As dialog; otherwise saves to the current file
     */
    private void saveDatabase(boolean saveAs) {
        if (!saveAs && currentFile != null) {
            try (
                FileOutputStream fos = new FileOutputStream(currentFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
            ) {
                oos.writeObject(boatStorage);
                oos.writeInt(ownerIdCounter);
                updateTitle();
                JOptionPane.showMessageDialog(
                    this,
                    "Database saved successfully."
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error saving: " + ex.getMessage()
                );
            }
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (currentFile != null) {
            fileChooser.setSelectedFile(currentFile);
        }
        if (
            fileChooser.showSaveDialog(this)
            == JFileChooser.APPROVE_OPTION
        ) {
            currentFile = fileChooser.getSelectedFile();
            try (
                FileOutputStream fos = new FileOutputStream(currentFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
            ) {
                oos.writeObject(boatStorage);
                oos.writeInt(ownerIdCounter);
                updateTitle();
                JOptionPane.showMessageDialog(
                    this,
                    "Database saved successfully."
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error saving: " + ex.getMessage()
                );
            }
        }
    }

    /**
     * Loads the database from file using deserialization.
     */
    private void loadDatabase() {
        JFileChooser fileChooser = new JFileChooser();
        if (
            fileChooser.showOpenDialog(this)
            == JFileChooser.APPROVE_OPTION
        ) {
            currentFile = fileChooser.getSelectedFile();
            try (
                FileInputStream fis = new FileInputStream(currentFile);
                ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                boatStorage = (BoatStorage) ois.readObject();
                ownerIdCounter = ois.readInt();

                ownerTabPanel.setBoatStorage(boatStorage);
                boatTabPanel.setBoatStorage(boatStorage);
                reportTabPanel.setBoatStorage(boatStorage);

                updateTitle();
                JOptionPane.showMessageDialog(
                    this,
                    "Database loaded successfully."
                );
            } catch (Exception ex) {
                currentFile = null;
                updateTitle();
                JOptionPane.showMessageDialog(
                    this,
                    "Error loading: " + ex.getMessage()
                );
            }
        }
    }

    /**
     * Sets up tab refresh logic so that tables are refreshed when switching tabs.
     * @param tabbedPane the JTabbedPane to add the listener to
     */
    private void setupTabRefresh(JTabbedPane tabbedPane) {
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 0) { // Owners tab
                ownerTabPanel.refreshTable();
            } else if (selectedIndex == 1) { // Boats tab
                boatTabPanel.refreshTable();
            } else if (selectedIndex == 2) { // Reports tab
                reportTabPanel.refreshTable();
            }
        });
    }
}
