package assignment1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

/**
 * Dialog for adding or editing an owner in the system.
 * Allows user to input or update owner name and address.
 */
public class OwnerDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    /** Main content panel for form fields */
    private final JPanel contentPanel = new JPanel();
    /** Field for entering the owner's name */
    private JTextField nameField;
    /** Field for entering the owner's address */
    private JTextField addressField;
    /** Flag indicating if a new owner was added */
    private boolean ownerAdded = false;
    /** Flag indicating if an owner was edited */
    private boolean ownerEdited = false;

    /**
     * Returns true if a new owner was added.
     * @return true if owner was added, false otherwise
     */
    public boolean isOwnerAdded() {
        return ownerAdded;
    }

    /**
     * Returns true if an owner was edited.
     * @return true if owner was edited, false otherwise
     */
    public boolean isOwnerEdited() {
        return ownerEdited;
    }

    /**
     * Constructs the dialog for adding or editing an owner.
     * Handles field population for edit mode and validates input on OK.
     * @param parent the parent JFrame
     * @param boatStorage the main BoatStorage data model
     * @param owner the owner to edit (null if adding)
     * @param ownerIdCounter the next available owner ID (used if adding)
     */
    public OwnerDialog(JFrame parent, BoatStorage boatStorage,
                      Owner owner, int ownerIdCounter) {
        super(parent, owner == null ? "Add Owner" : "Edit Owner", true);
        setModal(true);
        setBounds(100, 100, 300, 170);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(0, 2, 0, 0));

        // Name
        contentPanel.add(new JLabel("Owner Name:"));
        nameField = new JTextField(owner != null ? owner.getName() : "");
        contentPanel.add(nameField);

        // Address
        contentPanel.add(new JLabel("Owner Address:"));
        addressField = new JTextField(
            owner != null ? owner.getAddress() : ""
        );
        contentPanel.add(addressField);

        // Button panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            // Validate that name and address are not empty
            // If adding, create new Owner and add to storage; if editing, update fields
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Name and Address cannot be empty."
                );
                return;
            }
            if (owner == null) {
                String idNumber = String.valueOf(ownerIdCounter);
                Owner newOwner = new Owner(
                    idNumber, name, address, new ArrayList<>()
                );
                boatStorage.addOwner(newOwner);
                ownerAdded = true;
            } else {
                owner.setName(name);
                owner.setAddress(address);
                ownerEdited = true;
            }
            setVisible(false);
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
            owner == null ? "Add New Owner" : "Edit Owner"
        );
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblTitle, BorderLayout.NORTH);
    }
}
