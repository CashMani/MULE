package sample;

import java.io.Serializable;

/**
 * Created by AlexandraLink on 10/5/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Creates a MULE object that a Player may interact with.
 */
public class Mule implements Serializable {

    public enum Configuration {ENERGY, FOOD, ORE};

    private Configuration type = Configuration.ENERGY;

    /**
     * Mule object constructor
     * @param type the type of Mule associated with the Mule object.
     */
    public Mule(Configuration type) {
        this.type = type;
    }

    /**
     * Mule constructor that defaults to Type ENERGY
     */
    public Mule() { this.type = Configuration.ENERGY; }

    /**
     * Sets the type of a Mule
     * @param type the type of Mule desired
     */
    public void setType(Configuration type) {
        this.type = type;
    }

    /**
     * A getter for the Mule's type
     * @return the type of this Mule object
     */
    public Configuration getType() {
        return type;
    }
}
