package sample;

/**
 * Created by mani on 9/10/15.
 */
public class Player {
    private String name;
    private String race;
    private String color;

    public Player(String name, String race, String color) {
        this.name = name;
        this.race = race;
        this.color = color;
    }

    public String toString() {
        String ret = "Name: " + this.name + "\n" + "Race: " + this.race + "\n" + "Color: " + this.color;
        return ret;
    }

    public String getName() {
        return name;
    }
}
