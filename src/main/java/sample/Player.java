package sample;
import java.awt.Color;


/**
 * Created by mani on 9/10/15.
 */
public class Player implements Inventory {
    private String name;
    private String race;
    private String color;
    private int moneyStash;

    public Player(String name, String race, String color) {
        this.name = name;
        this.race = race;
        this.color = color;
        moneyStash = 0; // changes based on certain criteria?
    }

    public String toString() {
        String ret = "Name: " + this.name + "\n" + "Race: " + this.race + "\n" + "Color: " + this.color;
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
            moneyStash = moneyStash + valueToSubtract;
        }
    }
}
