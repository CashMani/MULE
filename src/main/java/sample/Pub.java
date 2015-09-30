package sample;
import java.util.Random;
/**
 * Created by Alexandra Link on 9/29/15.
 */
public class Pub {

    private Random rand;
    private int[] roundBonus = {50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200; }
    private int timeBonus;
    private int currentRound;


    public Pub(int round, int timeRemaining) {
        rand = new Random();
        timeBonus = timeRemaining;
        currentRound = round;
    }

    public int gamble() {
        int moneyBonus = roundBonus[currentRound - 1] * (risk());
        if (moneyBonus > 250) {
            return 250;
        } else {
            return moneyBonus;
        }
    }

    private int risk() {
        int bonusMax = 0;
        if (timeBonus >= 37) {
            bonusMax = 200;
        } else if (timeBonus >= 25 && timeBonus < 37) {
            bonusMax = 150;
        } else if(timeBonus>=12&&timeBonus<25) {
            bonusMax=100;
        } else if (timeBonus >= 0 && timeBonus < 12) {
            bonusMax = 50;
        }
        return rand.nextInt(bonusMax);
    }
}
