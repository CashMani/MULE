package sample;
import java.util.Random;
import javafx.util.Duration;
/**
 * Created by Alexandra Link on 9/29/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Creates a Pub class object that allows for a player to gamble
 * and make risks regarding their current inventory.
 */
public class Pub {

    private Random rand;
    private Duration time;
    private double timeBonus;

    /**
     * Class constructor that generates the time a Player will
     * have to take action in the Pub.
     * @param timeRemaining the amount of time left for a Player
     *                      to interact with the Pub.
     */
    public Pub(Duration timeRemaining) {
        rand = new Random();
        time = timeRemaining;
        timeBonus = time.toSeconds();
    }

    /**
     * Allows a player to gamble their current inventory.
     * Reveals if a Player will make money or lose money based on
     * the risk() method.
     * @return the amount of money gained or lost by the Player.
     */
    public int gamble() {
        System.out.println("Pub Math Breakdown:");
        int moneyBonus = (Round.getRoundBonus() * (risk())) / 100;
        System.out.println("Round money bonus comes out to $" + moneyBonus + ".\n");
        if (moneyBonus > 250) {
            return 250;
        } else {
            return moneyBonus;
        }
    }

    /**
     * Helper method for gamble to calculate how much money a Player will win or lose
     * based on the remaining time left.
     * @return the amount won from the risk to contribute to the time bonus value
     */
    private int risk() {
        int bonusMax = 0;
        System.out.println("\nPub used " + timeBonus + " seconds as the time bonus calc.");
        if (timeBonus >= 37) {
            bonusMax = 200;
        } else if (timeBonus >= 25 && timeBonus < 37) {
            bonusMax = 150;
        } else if(timeBonus>=12 && timeBonus<25) {
            bonusMax=100;
        } else if (timeBonus >= 0 && timeBonus < 12) {
            bonusMax = 50;
        }
        int risk = rand.nextInt(bonusMax);
        System.out.println("Bonus multpilier: " + risk);
        return risk;
    }
}
