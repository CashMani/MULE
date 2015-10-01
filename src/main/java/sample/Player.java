package sample;

import javafx.scene.paint.Color;
/**
 * Created by mani on 9/10/15.
 */
public class Player implements InventoryInterface {
    private String name;
    private Main.Race race;
    private Color color;
    private Inventory inventory;

    public Player(String name, Main.Race race, Color color) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.inventory = new Inventory();
    }

    public String toString() {
        String ret = "Name: " + this.name + "\n" + "Race: " + this.race.toString() + "\n" + "Color: " + this.color.toString();
        return ret;
    }


    public String getName() {return this.name; }

    public Color getColor() { return this.color; }

    public int getMoneyStash() {return this.inventory.moneyStash; }

    public int getEnergy() {return this.inventory.energy; }

    public int getFood() {return this.inventory.food; }

    public int getOre() {return this.inventory.ore; }

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

    public void addEnergy(int energyAdded) {
        if (energyAdded < 0) {
            System.out.println("Attempt to add negative energy");
        } else {
            inventory.energy += energyAdded;
        }
    }

    public void subtractEnergy(int energySubtract) {
        if (energySubtract < 0) {
            System.out.println("Attempt to subtract negative energy");
        } else {
            inventory.energy -= energySubtract;
        }
    }

    public void addFood(int foodAdded) {
        if (foodAdded < 0) {
            System.out.println("Attempt to add negative food");
        } else {
            inventory.food += foodAdded;
        }
    }

    public void subtractFood(int foodSubtract) {
        if (foodSubtract < 0) {
            System.out.println("Attempt to subtract negative food");
        } else {
            inventory.food -= foodSubtract;
        }
    }

    public void addOre(int oreAdded) {
        if (oreAdded < 0) {
            System.out.println("Attempt to add negative ore");
        } else {
            inventory.ore += oreAdded;
        }
    }

    public void subtractOre(int oreSubtract) {
        if (oreSubtract < 0) {
            System.out.println("Attempt to subtract negative ore");
        } else {
            inventory.ore -= oreSubtract;
        }
    }

    public String inventoryToString() {
        String inventoryList = (this.name + "'s Inventory: \n Money: " + inventory.moneyStash + "\n Energy: "
            + inventory.energy + "\n Food: " + inventory.food + "\n Ore: " + inventory.ore);
            // + "\n Mules: " + mules.toString();
        return inventoryList;
    }



//***************************************************************************************************************
    /**
     * Keeps track of all the player's belongings
     */
    private class Inventory {
        private int moneyStash;
        //private Mule[] mules;
        private int energy;
        private int food;
        private int ore;

        private Inventory() {
            this.moneyStash = 1000;
            //mules = new Mule();
            this.energy = 4;
            this.food = 8;
            this.ore = 0;
        }

    }
}
