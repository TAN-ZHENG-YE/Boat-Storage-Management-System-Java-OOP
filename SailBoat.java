package assignment1;

/**
 * Sailboat class which is the sub-class of the Boat abstract class.
 * Sailboat class representing a sailboat with additional attributes
 *  and methods.
 */
public class SailBoat extends Boat {
    /** Height of the mast */
    private double mastHeight;
    /** Area of the sail */
    private double sailArea;
    /** Rate for sail drying charge */
    private static double sailRate = 0.10;

    /**
     * No-argument constructor.
     */
    public SailBoat() {
        // Call the parameterized constructor with default values
        this(0, 0, 0, 0, 0, 0); 
    }

    /**
     * Constructor with arguments to initialize a SailBoat object.
     * 
     * @param height     Height of the boat
     * @param length     Length of the boat
     * @param width      Width of the boat
     * @param boatValue  Value of the boat
     * @param mastHeight Height of the mast
     * @param sailArea   Area of the sail
     */
    public SailBoat(double height, double length, double width,
                    double boatValue, double mastHeight, double sailArea) {
        super(height, length, width, boatValue);
        setMastHeight(mastHeight);
        setSailArea(sailArea);
    }

    /**
     * Get the height of the mast.
     * 
     * @return Height of the mast
     */
    public double getMastHeight() {
        return mastHeight;
    }

    /**
     * Set the height of the mast.
     * 
     * @param mastHeight Height of the mast
     */
    public void setMastHeight(double mastHeight) {
        this.mastHeight = mastHeight;
    }

    /**
     * Get the area of the sail.
     * 
     * @return Area of the sail
     */
    public double getSailArea() {
        return sailArea;
    }

    /**
     * Set the area of the sail.
     * 
     * @param sailArea Area of the sail
     */
    public void setSailArea(double sailArea) {
        this.sailArea = sailArea;
    }

    /**
     * Get the sail rate.
     * 
     * @return Sail rate
     */
    public static double getSailRate() {
        return sailRate;
    }

    /**
     * Set the sail rate.
     * 
     * @param sailRate Sail rate
     */
    public static void setSailRate(double sailRate) {
        SailBoat.sailRate = sailRate;
    }

    /**
     * Calculate sail drying charge (10% of sail area).
     * 
     * @return Sail drying charge
     */
    public double sailDryingCharge() {
        return sailArea * sailRate;
    }

    /**
     * Implementation of abstract method from Boat
     * Calculate total monthly charge.
     * 
     * @return Total monthly charge
     */
    @Override
    public double totalMonthlyCharge() {
        /*Total charge includes storage charge, insurance levy,
         and sail drying charge*/
        return storageCharge() + insuranceLevy() + sailDryingCharge();
    }

    /**
     * Return a string representation of the sailboat.
     * 
     * @return String representation of the sailboat
     */
    @Override
    public String toString() {
        return "SailBoat [height=" + getHeight()+ ", length=" +getLength()+ 
             ", width=" + getWidth() + ", value=" + getBoatValue() + 
             ", mastHeight=" + mastHeight + ", sailArea=" + sailArea + "]";
    }
}