package sample;

/**
 * Created by AlexandraLink on 9/30/15.
 */
public class Round {

    public static int[] foodReq = {3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};
    public static int[] randEventFactor = {25, 25, 25, 50, 50, 50, 50, 75, 75, 75, 75, 100};
    public static int[] roundBonus = {50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200};

    public Round() {

    }

    public static int getFoodReq() {
        return foodReq[Controller.roundNum];
    }

    public static int getRandEventFactor() {
        return randEventFactor[Controller.roundNum];
    }

    public static int getRoundBonus() {
        return roundBonus[Controller.roundNum];
    }

}
