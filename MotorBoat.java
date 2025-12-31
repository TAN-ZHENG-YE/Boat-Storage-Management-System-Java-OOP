package assignment1;

/**
 * Motorboat class which is the sub-class of the Boat abstract class.
 * Motorboat class representing a motorboat with additional attributes
 *  and methods.
 */
public class MotorBoat extends Boat {
    /** Horsepower of the motorboat */
    private int horsePower;
    /** Rate for fire levy charge */
    private static double fireRate = 0.10;

    /**
     * No-argument constructor.
     */
    public MotorBoat() {
        // Call the parameterized constructor with default values
        this(0, 0, 0, 0, 0); 
    }

    /**
     * Constructor with arguments to initialize a MotorBoat object.
     * 
     * @param height     Height of the boat
     * @param length     Length of the boat
     * @param width      Width of the boat
     * @param boatValue  Value of the boat
     * @param horsePower Horsepower of the motorboat
     */
    public MotorBoat(double height, double length, double width,
                     double boatValue, int horsePower) {
        super(height, length, width, boatValue);
        setHorsePower(horsePower);
    }

    /**
     * Get the horsepower of the motorboat.
     * 
     * @return Horsepower of the motorboat
     */
    public int getHorsePower() {
        return horsePower;
    }

    /**
     * Set the horsepower of the motorboat.
     * 
     * @param horsePower Horsepower of the motorboat
     */
    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    /**
     * Get the fire rate.
     * 
     * @return Fire rate
     */
    public static double getFireRate() {
        return fireRate;
    }

    /**
     * Set the fire rate.
     * 
     * @param fireRate Fire rate
     */
    public static void setFireRate(double fireRate) {
        MotorBoat.fireRate = fireRate;
    }

    /**
     * Calculate fire levy charge (10% of horse power).
     * 
     * @return Fire levy charge
     */
    public double fireLevyCharge() {
        return horsePower * fireRate;
    }

    /**
     * Implementation of abstract method from Boat.
     * Calculate total monthly charge.
     * 
     * @return Total monthly charge
     */
    @Override
    public double totalMonthlyCharge() {
        /*Total charge includes storage charge, insurance charge,
        and fire levy charge*/
        return storageCharge() + insuranceLevy() + fireLevyCharge();
    }

    /**
     * Return a string representation of the motorboat.
     * 
     * @return String representation of the motorboat
     */
    @Override
    public String toString() {
        return "MotorBoat [height="+ getHeight()+ ", length="+ getLength()+
               ", width=" + getWidth() + ", value=" + getBoatValue() + 
               ", horsePower=" + horsePower + "]";
    }
}