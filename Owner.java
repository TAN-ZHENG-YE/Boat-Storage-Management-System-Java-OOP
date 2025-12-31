package assignment1;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Owner class represents a boat owner with attributes for the
 *  owner's details and a list of boats owned by the owner
 */
public class Owner implements Serializable{
    /** Unique identifier for the owner */
    private String idNumber;
    /** Name of the owner */
    private String name;
    /** Address of the owner */
    private String address;
    /** List of boats owned by the owner */
    private ArrayList<Boat> boats;

    /**
     * No-argument constructor.
     */
    public Owner() {
        // Call the parameterized constructor with default values
        this("", "", "", new ArrayList<>()); 
    }

    /**
     * Constructor with arguments to initialize an Owner object.
     * 
     * @param idNumber Unique identifier for the owner
     * @param name     Name of the owner
     * @param address  Address of the owner
     * @param boats    List of boats owned by the owner
     */
    public Owner(String idNumber, String name, String address,
                 ArrayList<Boat> boats) {
        setIdNumber(idNumber);
        setName(name);
        setAddress(address);
        setBoats(boats);
    }

    /**
     * Get the ID number of the owner.
     * 
     * @return ID number of the owner
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Set the ID number of the owner.
     * 
     * @param idNumber ID number of the owner
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Get the name of the owner.
     * 
     * @return Name of the owner
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the owner.
     * 
     * @param name Name of the owner
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the address of the owner.
     * 
     * @return Address of the owner
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the owner.
     * 
     * @param address Address of the owner
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set the list of boats owned by the owner.
     * 
     * @param boats List of boats owned by the owner
     */
    public void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }

    /**
     * Get the list of boats owned by the owner.
     * 
     * @return List of boats owned by the owner
     */
    public ArrayList<Boat> getBoats() {
        return boats;
    }

    /**
     * Add a boat to the owner's list of boats.
     * 
     * @param boat The boat to be added
     */
    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    /**
     * Calculate total charges for all boats owned by this owner.
     * 
     * @return Total charges for all boats
     */
    public double totalOwnerCharge() {
        return boats.stream() // Create a stream from the boats list
                    // Map each boat to its total monthly charge
                    .mapToDouble(Boat::totalMonthlyCharge) 
                    .sum(); // Sum all the charges
    }

    /**
     * Calculate total storage charges for all boats owned by this owner.
     * 
     * @return Total storage charges for all boats
     */
    public double totalStorageCharge() {
        return boats.stream() // Create a stream from the boats list
                    // Map each boat to its storage charge
                    .mapToDouble(Boat::storageCharge) 
                    .sum(); // Sum all the storage charges
    }

    /**
     * Return a string representation of the owner.
     * 
     * @return String representation of the owner
     */
    @Override
    public String toString() {
        return "Owner Details:\n" +
               "----------------------------------------------------------"+
               "----------------------------------------------------\n" +
               "ID: " + idNumber + "\n" +
               "Name: " + name + "\n" +
               "Address: " + address + "\n" +
               "Number of boats: " + boats.size() + "\n" +
               "----------------------------------------------------------"+
               "----------------------------------------------------";
    }
}