package sample;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import java.awt.Point;
import java.io.Serializable;

/**
 * Created by AlexandraLink on 10/1/15.
 * Edited by Joanna Parkhurst on 11/4/15
 *
 * Stores and sets information on the property.
 */
public class LandPlot implements Serializable {

    private final String type;
    private Player owner;
    private transient Color color; // transient means not serializable
    private transient Button location;
    private Point coord;
    private Mule muleOnProp;
    private int energyProd;
    private int foodProd;
    private int oreProd;

    /**
     * LandPlot constructor
      * @param type type of land
     * @param owner player that owns this land
     * @param color color of land on map
     * @param location where the land button is located
     */
    public LandPlot(String type, Player owner, Color color, Button location, Point coord) {
        this.type = type;
        this.owner = owner;
        this.color = color;
        this.location = location;
        this.coord = coord;
        setProductionRates();
        muleOnProp = null;
        System.out.println("Landplot successfully assigned to " + owner.getName());
    }

    /**
     * Changes the player that owns the land
     * @param newOwner The new owner's name
     */
    public void changeOwner(Player newOwner) {
        owner = newOwner;
        color = newOwner.getColor();
    }

    /**
     * Places a specific mule on this land
     * @param mule the mule to be placed
     */
    public void placeMule(Mule mule) {
        if (muleOnProp == null) {
            muleOnProp = mule;
        } else {
            System.out.println("Mule already on property, so your new mule ran away!!");
        }
    }

    /**
     * Answers whether the land plot has a mule on it
     * @return boolean true for yes, false for no
     */
    public boolean hasMule() {
        return muleOnProp != null;
    }

    /**
     * Gives the mule who is on the property
     * @return mule object
     */
    public Mule getMuleOnProp() {
        return this.muleOnProp;
    }

    /**
     * Gives the Energy Production level
     * @return energyProd as an int of this level
     */
    public int getEnergyProd() {
        return energyProd;
    }

    /**
     * Returns the level of Ore Production
     * @return oreProd as an int of this level
     */
    public int getOreProd() {
        return oreProd;
    }

    /**
     * Returns the level of Food Production
     * @return foodProd as an int
     */
    public int getFoodProd() {
        return foodProd;
    }

    /**
     * Sets a specific mule on this land
     * @param m the mule to be set
     */
    public void setMule(Mule m) {
        this.muleOnProp = m;
        System.out.println("Mule of type \"" + m.getType() + "\" successfully planted on property of type \"" + this.type + "\" by " + owner.getName() + ".");
    }

    /**
     * Returns the button associated with this land plot
     * @return button as a location
     */
    public Button getButton() {
        return this.location;
    }

    /**
     * Sets the Production Rates based on type of land
     */
    public void setProductionRates() {
        if (type.equalsIgnoreCase("River")) {
            energyProd = 2;
            foodProd = 4;
            oreProd = 0;
        } else if (type.equalsIgnoreCase("Plain")) {
            energyProd = 3;
            foodProd = 2;
            oreProd = 1;
        } else if (type.equalsIgnoreCase("1 Mountain")) {
            energyProd = 1;
            foodProd = 1;
            oreProd = 2;
        } else if (type.equalsIgnoreCase("2 Mountain")) {
            energyProd = 1;
            foodProd = 1;
            oreProd = 3;
        } else if (type.equalsIgnoreCase("3 Mountain")) {
            energyProd = 1;
            foodProd = 1;
            oreProd = 4;
        } else {
            energyProd = 0;
            foodProd = 0;
            oreProd = 0;
            System.out.println("Error in decided land type for production rates.");
        }
    }

    /**
     * Sets the color of the land plot
     * @param color of land on the map
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the location of the button
     * Also used to help load the game
     * @param location of the land plot (new)
     */
    public void setLocation(Button location) {
        this.location = location;
    }

    public void setCoord(Point coord) { this.coord = coord; }

    public Point getCoord() { return this.coord; }

}
