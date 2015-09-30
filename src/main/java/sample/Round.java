package sample;

/**
 * Created by AlexandraLink on 9/30/15.
 */
public class Round {

    public static int round;
    public static int[] foodReq = {3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};
    public static int[] randEventFactor = {25, 25, 25, 50, 50, 50, 50, 75, 75, 75, 75, 100};
    public static int[] roundBonus = {50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200};

    public Round() {
        round = 0;
    }

    public static void increment() {
        round++;
        System.out.println("Game now set to round " + round);
    }

    public static int getFoodReq() {
        return foodReq[round];
    }

    public static int getRandEventFactor() {
        return randEventFactor[round];
    }

    public static int getRoundBonus() {
        return roundBonus[round];
    }

}
