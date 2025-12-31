package assignment1;

import java.io.Serializable;

/**
 * Abstract Boat class representing common attributes
 * and methods for all types of boats.
 */
public abstract class Boat implements Serializable{
    /** Height of the boat */
    private double height;
    /** Length of the boat */
    private double length;
    /** Width of the boat */
    private double width;
    /** Value of the boat */
    private double boatValue;
    /**
     * Default charge rate set here
     * (Changes made for assingment2: Moved from the console class)
     */
    private static double chargeRate = 1.0; 
    /** Static variable for levy rate */
    private static double levyRate = 0.0005;

    /**
     * No-argument constructor to initialize a Boat object
     *  with default values.
     */
    public Boat() {
        //Call parameterized constructor with default values
        this(0, 0, 0, 0); 
    }

    /**
     * Constructor with arguments to initialize a Boat object.
     * 
     * @param height    Height of the boat
     * @param length    Length of the boat
     * @param width     Width of the boat
     * @param boatValue Value of the boat
     */
    public Boat(double height, double length, double width,
                 double boatValue) {
        setHeight(height);
        setLength(length);
        setWidth(width);
        setBoatValue(boatValue);
    }

    /**
     * Get the height of the boat.
     * 
     * @return Height of the boat
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set the height of the boat.
     * 
     * @param height Height of the boat
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Get the length of the boat.
     * 
     * @return Length of the boat
     */
    public double getLength() {
        return length;
    }

    /**
     * Set the length of the boat.
     * 
     * @param length Length of the boat
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Get the width of the boat.
     * 
     * @return Width of the boat
     */
    public double getWidth() {
        return width;
    }

    /**
     * Set the width of the boat.
     * 
     * @param width Width of the boat
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Get the value of the boat.
     * 
     * @return Value of the boat
     */
    public double getBoatValue() {
        return boatValue;
    }

    /**
     * Set the value of the boat.
     * 
     * @param boatValue Value of the boat
     */
    public void setBoatValue(double boatValue) {
        this.boatValue = boatValue;
    }

    /**
     * Get the charge rate.
     * 
     * @return Charge rate
     */
    public static double getChargeRate() {
        return chargeRate;
    }

    /**
     * Set the charge rate.
     * 
     * @param chargeRate Charge rate
     */
    public static void setChargeRate(double chargeRate) {
        Boat.chargeRate = chargeRate;
    }

    /**
     * Get the levy rate.
     * 
     * @return Levy rate
     */
    public static double getLevyRate() {
        return levyRate;
    }

    /**
     * Set the levy rate.
     * 
     * @param levyRate Levy rate
     */
    public static void setLevyRate(double levyRate) {
        Boat.levyRate = levyRate;
    }

    /**
     * Abstract method to calculate total monthly charge.
     * 
     * @return Total monthly charge
     */
    public abstract double totalMonthlyCharge();

    /**
     * Calculate storage charge based on volume.
     * 
     * @return Storage charge
     */
    public double storageCharge() {
        double volume = height * length * width;
        return volume * chargeRate;
    }

    /**
     * Calculate insurance charge (0.05% of boat value).
     * 
     * @return Insurance charge
     */
    public double insuranceLevy() {
        return boatValue * levyRate;
    }
}