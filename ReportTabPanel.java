package assignment1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Panel for displaying various reports (summary, counts, owner charges) in a table.
 */
public class ReportTabPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /** Holds the current BoatStorage instance for reports */
    private BoatStorage boatStorage;
    /** Title label for the report */
    private JLabel reportTitle;
    /** JTable displaying the report data */
    private JTable reportTable;

    /**
     * Constructs the report tab panel, sets up the default summary report and buttons.
     * Handles switching between summary, counts, and owner charges reports.
     * @param boatStorage the main data model
     */
    public ReportTabPanel(BoatStorage boatStorage) {
        this.boatStorage = boatStorage; 
        setLayout(new BorderLayout());

        // Create and style the title label
        reportTitle = new JLabel("", SwingConstants.CENTER);
        reportTitle.setFont(new Font("Arial", Font.BOLD, 16));
        reportTitle.setBorder(
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        );

        // By default, show the summary charges report when the tab is opened
        ReportSummaryChargesTableModel summaryModel =
            new ReportSummaryChargesTableModel(
                boatStorage.getTotalStorageCharges(),
                boatStorage.getTotalSailDryingCharges(),
                boatStorage.getTotalFireLevyCharges(),
                boatStorage.getTotalInsuranceLevies()
            );
        reportTable = new JTable(summaryModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table read-only
            }
        };
        reportTable.setRowHeight(25);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportTable);

        // Style the table header
        reportTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        reportTable.getTableHeader().setBackground(
            new Color(230, 230, 230)
        );

        // Set the summary title and make it visible by default
        reportTitle.setText("Charges Summary");
        reportTitle.setVisible(true);

        // Set a custom cell renderer for coloring and font styling
        reportTable.setDefaultRenderer(
            Object.class,
            new DefaultTableCellRenderer() {
                /**
                 * Customizes the appearance of each cell in the report table.
                 * Applies background color and font style based on row/column content.
                 */
                @Override
                public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column
                ) {
                    Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                    );
                    String boatDetails = (String) table.getValueAt(row, 0);

                    if (table.getColumnCount() == 2 &&
                        !table.getColumnName(0).equals("ID")) {
                        if (row == table.getRowCount() - 1) {
                            c.setBackground(new Color(255, 255, 180));
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else {
                            c.setBackground(new Color(255, 223, 186));
                            c.setFont(c.getFont().deriveFont(Font.PLAIN));
                        }
                        return c;
                    }

                    if (boatDetails != null &&
                        boatDetails.startsWith("Boat")) {
                        int boatIdx = 0;
                        for (int i = 0; i <= row; i++) {
                            String bd = (String) table.getValueAt(i, 0);
                            if (bd != null && bd.startsWith("Boat"))
                                boatIdx++;
                        }
                        Color boatColor = (boatIdx % 2 == 1)
                            ? new Color(173, 216, 230)
                            : new Color(255, 223, 186);
                        c.setBackground(boatColor);
                        if (column == 0) {
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else {
                            c.setFont(c.getFont().deriveFont(Font.PLAIN));
                        }
                    } else if (boatDetails != null && 
                        (boatDetails.equals("TOTAL") || 
                         boatDetails.equals("TOTAL MONTHLY INCOME") || 
                         boatDetails.equals("TOTAL BOATS") ||
                         (row == table.getRowCount() - 1))) {
                        c.setBackground(new Color(255, 255, 180));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        int boatRow = row;
                        while (boatRow > 0 &&
                            !((String) table.getValueAt(boatRow, 0))
                                .startsWith("Boat")) {
                            boatRow--;
                        }
                        String bd = (String) table.getValueAt(boatRow, 0);
                        if (bd != null && bd.startsWith("Boat")) {
                            int boatIdx = 0;
                            for (int i = 0; i <= boatRow; i++) {
                                String b = (String) table.getValueAt(i, 0);
                                if (b != null && b.startsWith("Boat"))
                                    boatIdx++;
                            }
                            c.setBackground(
                                (boatIdx % 2 == 1)
                                    ? new Color(173, 216, 230)
                                    : new Color(255, 223, 186)
                            );
                            c.setFont(c.getFont().deriveFont(Font.PLAIN));
                        }
                    }
                    return c;
                }
            }
        );

        // Create the button panel for report actions
        JPanel btnPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 20, 10)
        );
        btnPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        );

        // Create report buttons
        JButton summaryBtn = new JButton("Show Charges Summary");
        JButton countsBtn = new JButton("Show Boat Counts");
        JButton ownerChargesBtn = new JButton("Show Owner Charges");

        // Button: Show Charges Summary
        summaryBtn.addActionListener(e -> {
            reportTitle.setText("Charges Summary");
            reportTitle.setVisible(true);
            // Set the table model to the summary charges report
            reportTable.setModel(
                new ReportSummaryChargesTableModel(
                    this.boatStorage.getTotalStorageCharges(),
                    this.boatStorage.getTotalSailDryingCharges(),
                    this.boatStorage.getTotalFireLevyCharges(),
                    this.boatStorage.getTotalInsuranceLevies()
                )
            );
        });

        // Button: Show Boat Counts
        countsBtn.addActionListener(e -> {
            reportTitle.setText("Boat Counts");
            reportTitle.setVisible(true);
            // Set the table model to the boat counts report
            reportTable.setModel(
                new ReportCountsTableModel(
                    this.boatStorage.countSailBoats(),
                    this.boatStorage.countMotorBoats(),
                    this.boatStorage.getTotalBoatCount()
                )
            );
        });

        // Button: Show Owner Charges
        ownerChargesBtn.addActionListener(e -> {
            String ownerId = JOptionPane.showInputDialog(
                this, "Enter Owner ID:"
            );
            if (ownerId == null) return;

            Owner owner = this.boatStorage.findOwner(ownerId.trim());
            if (owner == null) {
                JOptionPane.showMessageDialog(
                    this, "Owner not found!"
                );
                return;
            }

            reportTitle.setText(
                "Charges for owner: " + owner.getName()
            );
            reportTitle.setVisible(true);
            // Set the table model to the owner charges report
            reportTable.setModel(
                new ReportOwnerChargesTableModel(owner)
            );
        });

        // Add buttons to the button panel
        btnPanel.add(summaryBtn);
        btnPanel.add(countsBtn);
        btnPanel.add(ownerChargesBtn);

        // Layout: title label at top, table in center, buttons at bottom
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(reportTitle, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    /**
     * Refreshes the report table to show the latest summary charges.
     * Call this after any change to the boats or owners.
     */
    public void refreshTable() {
        reportTitle.setText("Charges Summary");
        reportTitle.setVisible(true);
        reportTable.setModel(
            new ReportSummaryChargesTableModel(
                this.boatStorage.getTotalStorageCharges(),
                this.boatStorage.getTotalSailDryingCharges(),
                this.boatStorage.getTotalFireLevyCharges(),
                this.boatStorage.getTotalInsuranceLevies()
            )
        );
    }

    /**
     * Updates the BoatStorage reference and refreshes the summary charges table.
     * Use this after loading a new database.
     * @param boatStorage the new BoatStorage to use
     */
    public void setBoatStorage(BoatStorage boatStorage) {
        this.boatStorage = boatStorage;
        refreshTable();
    }
}

