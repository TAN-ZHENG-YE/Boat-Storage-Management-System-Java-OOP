package assignment1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

/**
 * Dialog for adding or editing a boat (SailBoat or MotorBoat) in the system.
 * Allows user to input all relevant boat details and select the owner.
 */
public class BoatDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    /** Main content panel for form fields */
    private final JPanel contentPanel = new JPanel();
    /** Combo box for selecting the owner */
    private JComboBox<String> ownerCombo;
    /** Combo box for selecting the boat type */
    private JComboBox<String> typeCombo;
    /** Field for entering boat height */
    private JTextField heightField;
    /** Field for entering boat length */
    private JTextField lengthField;
    /** Field for entering boat width */
    private JTextField widthField;
    /** Field for entering boat value */
    private JTextField valueField;
    /** Field for entering mast height (SailBoat only) */
    private JTextField mastHeightField;
    /** Field for entering sail area (SailBoat only) */
    private JTextField sailAreaField;
    /** Field for entering horse power (MotorBoat only) */
    private JTextField horsePowerField;
    /** Flag indicating if a new boat was added */
    private boolean boatAdded = false;
    /** Flag indicating if a boat was edited */
    private boolean boatEdited = false;

    /**
     * Constructs the dialog for adding or editing a boat.
     * Handles dynamic field enabling/disabling and populates fields for edit mode.
     * Validates user input and updates or creates the boat on OK.
     * @param parent the parent JFrame
     * @param boatStorage the main BoatStorage data model
     * @param editOwner the owner to edit (null if adding)
     * @param editBoat the boat to edit (null if adding)
     */
    public BoatDialog(JFrame parent, BoatStorage boatStorage,
                     Owner editOwner, Boat editBoat) {
        super(parent, editBoat == null ? "Add Boat" : "Edit Boat", true);
        setModal(true);
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(0, 2, 0, 0));

        // Owner Combo
        contentPanel.add(new JLabel("Owner:"));
        ownerCombo = new JComboBox<>();
        ArrayList<Owner> owners = boatStorage.getOwners();
        int selectedOwnerIdx = 0;
        for (int i = 0; i < owners.size(); i++) {
            Owner o = owners.get(i);
            ownerCombo.addItem(o.getIdNumber() + " - " + o.getName());
            if (editOwner != null &&
                o.getIdNumber().equals(editOwner.getIdNumber())) {
                selectedOwnerIdx = i;
            }
        }
        ownerCombo.setSelectedIndex(selectedOwnerIdx);
        ownerCombo.setEnabled(editBoat == null);
        contentPanel.add(ownerCombo);

        // Type Combo
        contentPanel.add(new JLabel("Boat Type:"));
        String[] types = {"SailBoat", "MotorBoat"};
        typeCombo = new JComboBox<>(types);
        if (editBoat instanceof SailBoat) {
            typeCombo.setSelectedIndex(0);
        } else if (editBoat instanceof MotorBoat) {
            typeCombo.setSelectedIndex(1);
        }
        typeCombo.setEnabled(editBoat == null);
        contentPanel.add(typeCombo);

        // Height
        contentPanel.add(new JLabel("Height:"));
        heightField = new JTextField(
            editBoat != null ? String.valueOf(editBoat.getHeight()) : ""
        );
        contentPanel.add(heightField);

        // Length
        contentPanel.add(new JLabel("Length:"));
        lengthField = new JTextField(
            editBoat != null ? String.valueOf(editBoat.getLength()) : ""
        );
        contentPanel.add(lengthField);

        // Width
        contentPanel.add(new JLabel("Width:"));
        widthField = new JTextField(
            editBoat != null ? String.valueOf(editBoat.getWidth()) : ""
        );
        contentPanel.add(widthField);

        // Value
        contentPanel.add(new JLabel("Value:"));
        valueField = new JTextField(
            editBoat != null ? String.valueOf(editBoat.getBoatValue()) : ""
        );
        contentPanel.add(valueField);

        // Mast Height (SailBoat)
        contentPanel.add(new JLabel("Mast Height (SailBoat):"));
        mastHeightField = new JTextField();
        contentPanel.add(mastHeightField);

        // Sail Area (SailBoat)
        contentPanel.add(new JLabel("Sail Area (SailBoat):"));
        sailAreaField = new JTextField();
        contentPanel.add(sailAreaField);

        // Horse Power (MotorBoat)
        contentPanel.add(new JLabel("Horse Power (MotorBoat):"));
        horsePowerField = new JTextField();
        contentPanel.add(horsePowerField);

        if (editBoat instanceof SailBoat) {
            mastHeightField.setText(
                String.valueOf(((SailBoat) editBoat).getMastHeight())
            );
            sailAreaField.setText(
                String.valueOf(((SailBoat) editBoat).getSailArea())
            );
        } else if (editBoat instanceof MotorBoat) {
            horsePowerField.setText(
                String.valueOf(((MotorBoat) editBoat).getHorsePower())
            );
        }

        // Enable/disable fields based on type selection
        typeCombo.addActionListener(e -> {
            boolean isSail = typeCombo.getSelectedIndex() == 0;
            mastHeightField.setEnabled(isSail);
            sailAreaField.setEnabled(isSail);
            horsePowerField.setEnabled(!isSail);
        });
        typeCombo.setSelectedIndex(typeCombo.getSelectedIndex());

        // Button panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                // Validate required fields for SailBoat or MotorBoat
                // If any required field is missing, show an error dialog and return
                if (typeCombo.getSelectedIndex() == 0) { // SailBoat
                    StringBuilder missing = new StringBuilder();
                    if (heightField.getText().trim().isEmpty())
                        missing.append("Height, ");
                    if (lengthField.getText().trim().isEmpty())
                        missing.append("Length, ");
                    if (widthField.getText().trim().isEmpty())
                        missing.append("Width, ");
                    if (valueField.getText().trim().isEmpty())
                        missing.append("Value, ");
                    if (mastHeightField.getText().trim().isEmpty())
                        missing.append("Mast Height, ");
                    if (sailAreaField.getText().trim().isEmpty())
                        missing.append("Sail Area, ");
                    if (missing.length() > 0) {
                        // Remove last comma and space
                        missing.setLength(missing.length() - 2);
                        JOptionPane.showMessageDialog(
                            this,
                            missing.toString() + " field(s) cannot be empty!"
                        );
                        return;
                    }
                } else { // MotorBoat
                    StringBuilder missing = new StringBuilder();
                    if (heightField.getText().trim().isEmpty())
                        missing.append("Height, ");
                    if (lengthField.getText().trim().isEmpty())
                        missing.append("Length, ");
                    if (widthField.getText().trim().isEmpty())
                        missing.append("Width, ");
                    if (valueField.getText().trim().isEmpty())
                        missing.append("Value, ");
                    if (horsePowerField.getText().trim().isEmpty())
                        missing.append("Horse Power, ");
                    if (missing.length() > 0) {
                        // Remove last comma and space
                        missing.setLength(missing.length() - 2);
                        JOptionPane.showMessageDialog(
                            this,
                            missing.toString() + " field(s) cannot be empty!"
                        );
                        return;
                    }
                }
                int ownerIdx = ownerCombo.getSelectedIndex();
                Owner owner = owners.get(ownerIdx);
                double height = Double.parseDouble(
                    heightField.getText().trim()
                );
                double length = Double.parseDouble(
                    lengthField.getText().trim()
                );
                double width = Double.parseDouble(
                    widthField.getText().trim()
                );
                double value = Double.parseDouble(
                    valueField.getText().trim()
                );

                if (typeCombo.getSelectedIndex() == 0) { // SailBoat
                    // Parse and validate SailBoat-specific fields
                    // If editing, update; if adding, create new SailBoat
                    double mastHeight = Double.parseDouble(
                        mastHeightField.getText().trim()
                    );
                    double sailArea = Double.parseDouble(
                        sailAreaField.getText().trim()
                    );
                    if (height <= 0 || length <= 0 || width <= 0 ||
                        value <= 0 || mastHeight <= 0 || sailArea <= 0) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Height, Length, Width, Value, Mast Height, and " +
                            "Sail Area must all be positive numbers."
                        );
                        return;
                    }
                    if (editBoat == null) {
                        Boat newBoat = new SailBoat(
                            height, length, width, value, mastHeight, sailArea
                        );
                        boatStorage.addBoat(newBoat, owner);
                        boatAdded = true;
                    } else if (editBoat instanceof SailBoat) {
                        editBoat.setHeight(height);
                        editBoat.setLength(length);
                        editBoat.setWidth(width);
                        editBoat.setBoatValue(value);
                        ((SailBoat) editBoat).setMastHeight(mastHeight);
                        ((SailBoat) editBoat).setSailArea(sailArea);
                        boatEdited = true;
                    }
                } else { // MotorBoat
                    // Parse and validate MotorBoat-specific fields
                    // If editing, update; if adding, create new MotorBoat
                    int horsePower = Integer.parseInt(
                        horsePowerField.getText().trim()
                    );
                    if (height <= 0 || length <= 0 || width <= 0 ||
                        value <= 0 || horsePower <= 0) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Height, Length, Width, Value, and Horse Power " +
                            "must all be positive numbers."
                        );
                        return;
                    }
                    if (editBoat == null) {
                        Boat newBoat = new MotorBoat(
                            height, length, width, value, horsePower
                        );
                        boatStorage.addBoat(newBoat, owner);
                        boatAdded = true;
                    } else if (editBoat instanceof MotorBoat) {
                        editBoat.setHeight(height);
                        editBoat.setLength(length);
                        editBoat.setWidth(width);
                        editBoat.setBoatValue(value);
                        ((MotorBoat) editBoat).setHorsePower(horsePower);
                        boatEdited = true;
                    }
                }
                setVisible(false);
            } catch (NumberFormatException ex) {
                // Show error if any number field is invalid
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid input: Please enter valid numbers."
                );
            } catch (Exception ex) {
                // Show error for any other exception
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid input: " + ex.getMessage()
                );
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));
        buttonPane.add(cancelButton);

        // Title label at the top
        JLabel lblTitle = new JLabel(
            editBoat == null ? "Add New Boat" : "Edit Boat"
        );
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblTitle, BorderLayout.NORTH);
    }

    /**
     * Returns true if a new boat was added.
     * @return true if boat was added, false otherwise
     */
    public boolean isBoatAdded() {
        return boatAdded;
    }

    /**
     * Returns true if a boat was edited.
     * @return true if boat was edited, false otherwise
     */
    public boolean isBoatEdited() {
        return boatEdited;
    }
}
