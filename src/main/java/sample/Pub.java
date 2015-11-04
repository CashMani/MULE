package sample;
import java.util.Random;
import javafx.util.Duration;
/**
 * Created by Alexandra Link on 9/29/15.
 */
public class Pub {

    private Random rand;
    private Duration time;
    private double timeBonus;


    public Pub(Duration timeRemaining) {
        rand = new Random();
        time = timeRemaining;
        timeBonus = time.toSeconds();
    }

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
