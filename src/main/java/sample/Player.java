package sample;

import javafx.scene.paint.Color;
/**
 * Created by mani on 9/10/15.
 */
public class Player implements Inventory {
    private String name;
    private Main.Race race;
    private Color color;
    private int moneyStash;

    public Player(String name, Main.Race race, Color color) {
        this.name = name;
        this.race = race;
        this.color = color;
        moneyStash = 0; // changes based on certain criteria?
    }

    public String toString() {
        String ret = "Name: " + this.name + "\n" + "Race: " + this.race.toString() + "\n" + "Color: " + this.color.toString();
        return ret;
    }

    public String getName() {
        return name;
    }

    public void addMoney(int valueToAdd) {
        if (valueToAdd < 0) {
            System.out.println("The value is negative.");
        } else {
            moneyStash = moneyStash + valueToAdd;
        }
    }

    public void subtractMoney(int valueToSubtract) {
        if (valueToSubtract < 0) {
            System.out.println("The value is negative.");
        } else {
            moneyStash = moneyStash - valueToSubtract;
        }
    }
}
