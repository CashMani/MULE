package sample;

import javafx.scene.paint.Color;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Button;
/**
 * Created by mani on 9/10/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Creates a Player object to interact with the MULE game and to
 * maintain an inventory of items necessary to survive in the game.
 */
public class Player implements InventoryInterface, Serializable {
    private String name;
    private Main.Race race;
    private transient Color color; // transient means it is not serializable
    private Inventory inventory;
    private ArrayList<LandPlot> landOwned;
    private int landCount;
    private int score;

    /**
     * Constructor for a Player object.
     * @param name the player's name
     * @param race the player's race
     * @param color the player's color
     */
    public Player(String name, Main.Race race, Color color) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.inventory = new Inventory();
        landOwned = new ArrayList<>();
        landCount = 0;
    }

    /**
     * Turns a Player's attributes into a string value.
     *
     * No parameter
     * @return Player's attributes in String form
     */
    public String toString() {
        String ret = "Name: " + this.name + "\n" + "Race: " + this.race.toString() + "\n" + "Color: " + this.color.toString();
        return ret;
    }

    /**
     * Calculates and gets a Player's score.
     *
     * @return this Player's score
     */
    public int getScore() {
        calculateScore();
        return score;
    }

    /**
     * Calculates a Player's score with a particular formula based on
     * money, food, energy, and ore inventory.
     *
     * No parameters
     * No return
     */
    public void calculateScore() {
        score = 1 * getMoneyStash() + 500 * getLandCount() + 1 * (getFood() + getEnergy() + getOre());
    }

    /**
     * Player name getter
     *
     * @return Player's name
     */
    public String getName() {return this.name; }

    /**
     * Player color getter
     * @return Player color
     */
    public Color getColor() { return this.color; }

    /**
     * Player money inventory getter
     * @return amount of money this Player possesses
     */
    public int getMoneyStash() {return this.inventory.moneyStash; }

    /**
     * Player energy getter
     * @return amount of energy this Player possesses
     */
    public int getEnergy() {return this.inventory.energy; }

    /**
     * Player food getter
     * @return amount of food this Player possesses
     */
    public int getFood() {return this.inventory.food; }

    /**
     * Player ore getter
     * @return amount of ore a Player possesses
     */
    public int getOre() {return this.inventory.ore; }

    /**
     * Player land owned amount getter
     * @return amount of land a player possesses
     */
    public int getLandCount() { return this.landCount; }

    /**
     * Player land owned list getter
     * @return list of land a player possesses
     */
    public ArrayList<LandPlot> getLandOwned() {
        return this.landOwned;
    }

    /**
     * Allows player to view whether or
     * not they own a plot of land
     * @param p the user clicking on a plot of land
     * @return true if Player owns land, false if not
     */
    public boolean ownsLand(Point p) {
        boolean isOwner = false;
        for (LandPlot l : landOwned) {
            if (l.getCoord().equals(p)) isOwner = true;
        }
        return isOwner;
    }

    /**
     * Land getter at user request
     * @param p button action of user
     *          requesting land ownership
     * @return land plot owned
     */
    public LandPlot getLand(Point p) {
        LandPlot land = null;
        for (LandPlot l : landOwned) {
            if (l.getCoord().equals(p)) land = l;
        }
        return land;
    }

    /**
     * Mule getter
     * @return returns this Mule
     */
    public Mule getMule() {
        Mule temp = this.inventory.muleOnPerson;
        this.inventory.muleOnPerson = null;
        return temp;
    }

    /**
     * Displays whether or not a Player has a Mule
     * @return true if Player has Mule, false if not
     */
    public boolean hasMule() { return this.inventory.muleOnPerson != null; }

    /**
     * Adds land to Player inventory
     * @param land the land added to the player inventory
     */
    public void addLand(LandPlot land) {
        landOwned.add(land);
        landCount++;
    }

    /**
     * Removes land from a Player's inventory
     * @param land the land removed from the inventory
     */
    public void removeLand(LandPlot land) {
        landOwned.remove(land);
        landCount--;
    }


    /**
     * Adds money to a player's money stash in their inventory
     * @param valueToAdd How much money to add to player's money stash
     */
    public void addMoney(int valueToAdd) {
        if (valueToAdd < 0) {
            System.out.println("The value is negative.");
        } else {
            inventory.moneyStash += valueToAdd;
        }
    }

    /**
     * Removes money from a player's money stash in their inventory
     * @param valueToSubtract The amount of money being taken out of inventory
     */
    public void subtractMoney(int valueToSubtract) {
        if (valueToSubtract < 0) {
            System.out.println("The value is negative.");
        } else {
            inventory.moneyStash -= valueToSubtract;
        }
    }

    /**
     * Adds energy to a player's inventory
     * @param energyAdded the amount of energy to be added
     *                    to a Player's inventory
     */
    public void addEnergy(int energyAdded) {
        if (energyAdded < 0) {
            System.out.println("Attempt to add negative energy");
        } else {
            inventory.energy += energyAdded;
        }
    }

    /**
     * Subtracts energy from a Player's inventory
     * @param energySubtract the amount of energy
     *                       to be subtracted
     */
    public void subtractEnergy(int energySubtract) {
        if (energySubtract < 0) {
            System.out.println("Attempt to subtract negative energy");
        } else {
            inventory.energy -= energySubtract;
        }
    }

    /**
     * The amount of food to be added to a Player's inventory
     * @param foodAdded the amount of food to be added
     */
    public void addFood(int foodAdded) {
        if (foodAdded < 0) {
            System.out.println("Attempt to add negative food");
        } else {
            inventory.food += foodAdded;
        }
    }

    /**
     * Subtracts food from a Player's inventory
     * @param foodSubtract the amount of food to be subtracted
     */
    public void subtractFood(int foodSubtract) {
        if (foodSubtract < 0) {
            System.out.println("Attempt to subtract negative food");
        } else {
            inventory.food -= foodSubtract;
        }
    }

    /**
     * Adds ore to a Player's inventory
     * @param oreAdded the amount of ore to be added
     */
    public void addOre(int oreAdded) {
        if (oreAdded < 0) {
            System.out.println("Attempt to add negative ore.");
        } else {
            inventory.ore += oreAdded;
        }
    }

    /**
     * Subtracts ore from a Player's inventory
     * @param oreSubtract the amount of ore to be
     *                    subtracted
     */
    public void subtractOre(int oreSubtract) {
        if (oreSubtract < 0) {
            System.out.println("Attempt to subtract negative ore.");
        } else {
            inventory.ore -= oreSubtract;
        }
    }

    /**
     * Adds crystite to a player's inventory
     * @param crystiteAdded the amount of crystite to be added
     */
    public void addCrystite(int crystiteAdded) {
        if (crystiteAdded < 0) {
            System.out.println("Attempt to add negative crystite.");
        } else {
            inventory.crystite += crystiteAdded;
        }
    }

    /**
     * Adds a new Mule to a Player's inventory
     * @param newMule the Mule type to be added
     */
    public void addMule(Mule newMule) {
        if (newMule == null || inventory.muleOnPerson != null) {
            System.out.println("Attempted to add null mule");
        } else {
            inventory.muleOnPerson = newMule;
        }
    }

    /**
     * Removes Mule from a Player's inventory
     * If no Mule in inventory, prints error statement to
     * indicate so.
     */
    public void sellMule() {
        if (inventory.muleOnPerson != null) {
            inventory.muleOnPerson = null;
        } else {
            System.out.println("Error- No mule to sell.");
        }
    }

    /**
     * Sets color of player
     * @param color desired color of player
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Converts the inventory to a String value
     * @return the String rendition of the contents of
     * a Player's inventory
     */
    public String inventoryToString() {
        String inventoryList;
        if (inventory.muleOnPerson == null) {
            inventoryList = (this.name + "'s Inventory: \n Money: " + inventory.moneyStash + "\n Energy: "
                + inventory.energy + "\n Food: " + inventory.food + "\n Ore: " + inventory.ore + "\n Crystite: " +
                    inventory.crystite + "\n No mule on person.");
        } else {
            inventoryList = (this.name + "'s Inventory: \n Money: " + inventory.moneyStash + "\n Energy: "
                    + inventory.energy + "\n Food: " + inventory.food + "\n Ore: " + inventory.ore + "\n Crystite: " +
                    inventory.crystite + "\n Mule on person.");
        }
        return inventoryList;
    }



//***************************************************************************************************************
    /**
     * Keeps track of all the player's belongings
     */
    private class Inventory implements Serializable {
        private int moneyStash;
        private int energy;
        private int food;
        private int ore;
        private int crystite;
        private Mule muleOnPerson;

        /**
         * A player's inventory.
         */
        private Inventory() {
            this.moneyStash = 1000;
            this.energy = 4;
            this.food = 8;
            this.ore = 0;
            this.crystite = 0;
            muleOnPerson = null;
        }

    }
}
