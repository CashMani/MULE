package sample;

/**
 * Created by AlexandraLink on 9/30/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Defines and regulates a "round" of game play.
 */
public class Round {

    public static int[] foodReq = {3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};
    public static int[] randEventFactor = {25, 25, 25, 50, 50, 50, 50, 75, 75, 75, 75, 100};
    public static int[] roundBonus = {50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200};

    /**
     * Game round constructor.
     */
    public Round() {

    }

    /**
     * A getter to notify a Player of a food request.
     * @return the food request
     */
    public static int getFoodReq() {
        return foodReq[Controller.roundNum];
    }

    /**
     * Gets the random event to occur within a round of game play.
     * @return the random event to occur
     */
    public static int getRandEventFactor() {
        return randEventFactor[Controller.roundNum];
    }

    /**
     * Gets a bonus that will be received by a Player at the end of a round.
     * @return the value of the round bonus
     */
    public static int getRoundBonus() {
        return roundBonus[Controller.roundNum];
    }

}
