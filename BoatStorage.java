package assignment1;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.*;

/**
 * BoatStorage class to manage the collection of boats and owners.
 * It provides methods to add owners, add boats, find owners, sort owners,
 * count boats and calculate various charges.
 */
public class BoatStorage implements Serializable{
    /** List of owners */
    private ArrayList<Owner> owners = new ArrayList<>();
    /** List of boats */
    private ArrayList<Boat> boats = new ArrayList<>();

    /**
     * Add a new owner to the owner's list.
     * 
     * @param owner The owner to be added
     */
    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    /**
     * Add a new boat for a specific owner.
     * 
     * @param boat  The boat to be added
     * @param owner The owner of the boat
     */
    public void addBoat(Boat boat, Owner owner) {
        owner.addBoat(boat); // Add the boat to the owner's list of boats
        boats.add(boat);//Add the boat to overall list of boats in the storage
    }

    /**
     * Find an owner by their ID number.
     * 
     * @param idNumber The ID number of the owner
     * @return The owner if found, null otherwise
     */
    public Owner findOwner(String idNumber) {
        return owners.stream()
                     .filter(owner -> owner.getIdNumber().equals(idNumber))
                     .findFirst()
                     .orElse(null);
    }

    /**
     * Display total monthly charges for all boats.
     */
    public void displayCharges() {
        for (Boat boat : boats) {
            System.out.println("Total Monthly Charge for Boat: "
                                + boat.totalMonthlyCharge());
        }
    }

    /**
     * Get the total number of boats.
     * 
     * @return Total number of boats
     */
    public int getTotalBoatCount() {
        return boats.size();
    }

    /**
     * Count the number of sail boats.
     * 
     * @return Number of sail boats
     */
    public int countSailBoats() {
        return (int) boats.stream()
                          .filter(boat -> boat instanceof SailBoat)
                          .count();
    }

    /**
     * Count the number of motor boats.
     * 
     * @return Number of motor boats
     */
    public int countMotorBoats() {
        return (int) boats.stream()
                          .filter(boat -> boat instanceof MotorBoat)
                          .count();
    }

    /**
     * Calculate total storage charges for all boats.
     * 
     * @return Total storage charges
     */
    public double getTotalStorageCharges() {
        return boats.stream()
                    .mapToDouble(Boat::storageCharge)
                    .sum();
    }

    /**
     * Calculate total insurance levies for all boats.
     * 
     * @return Total insurance levies
     */
    public double getTotalInsuranceLevies() {
        return boats.stream()
                    .mapToDouble(Boat::insuranceLevy)
                    .sum();
    }

    /**
     * Calculate total sail drying charges.
     * 
     * @return Total sail drying charges
     */
    public double getTotalSailDryingCharges() {
        return boats.stream()
                .filter(boat -> boat instanceof SailBoat)
                .mapToDouble(boat -> ((SailBoat) boat).sailDryingCharge())
                .sum();
    }

    /**
     * Calculate total fire levy charges.
     * 
     * @return Total fire levy charges
     */
    public double getTotalFireLevyCharges() {
        return boats.stream()
                .filter(boat -> boat instanceof MotorBoat)
                .mapToDouble(boat -> ((MotorBoat) boat).fireLevyCharge())
                .sum();
    }

    /**
     * Get all owners in original order.
     * 
     * @return List of owners in original order
     */
    public ArrayList<Owner> getOwners() {
        return new ArrayList<>(owners);
    }

    /**
     * Get all owners sorted by name in ascending order.
     * 
     * @return List of owners sorted by name
     */
    public ArrayList<Owner> getOwnersSortedByName() {
        return (ArrayList<Owner>) owners.stream()
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Get all owners sorted by total monthly storage charges in descending order.
     * 
     * @return List of owners sorted by total monthly storage charges
     */
    public ArrayList<Owner> getOwnersSortedByStorageCharges() {
        return (ArrayList<Owner>) owners.stream()
                                        .sorted((o1, o2) -> Double.compare(o2.totalStorageCharge(), o1.totalStorageCharge()))
                                        .collect(Collectors.toList());
    }

    /**
     * Display summary of all charges.
     */
    public void displayChargeSummary() {
        System.out.println("Charge Summary:");
        System.out.printf("Total Storage Charges: $%.2f%n",
                          getTotalStorageCharges());
        System.out.printf("Total Insurance Levies: $%.2f%n",
                          getTotalInsuranceLevies());
        System.out.printf("Total Sail Drying Charges: $%.2f%n", 
                          getTotalSailDryingCharges());
        System.out.printf("Total Fire Levy Charges: $%.2f%n", 
                          getTotalFireLevyCharges());
    }
}