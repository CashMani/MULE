package sample;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * Created by mani on 9/24/15.
 * Edited by Joanna Parkhurst on 11/4/15
 *
 * Directs the dynamics of the player controls and turns.
 * Contains methods for each player to select land.
 */
    public class GameController {

        public static Store store;
        public static RandomEvents randEvents;

    //***************************** Land Selection ********************************************************************

    /**
     * Dictates the player and turn throughout the game.
     * Conditions for the first turn or end of loop in players.
     * Otherwise increments the currentPlayerTurn for the Controller.
     */
    public static void nextTurn() {
        Controller.mulePlacementMode = false;
        if (Controller.currentPlayerTurn == 0 && Controller.roundNum == 0) {
            Player cur = Controller.players.get(Controller.currentPlayerTurn);
            System.out.println(cur.getName() + "'s turn is over.\n");
            calculateProduction(cur);
            System.out.println(cur.inventoryToString());
        } else endTurn();
        if (Controller.currentPlayerTurn == Controller.players.size() - 1) {
            Controller.currentPlayerTurn = 0;
            Controller.roundNum++;
            System.out.println("\nRound Number: " + Controller.roundNum + "\n");
            if (Controller.roundNum == 2) {
                Controller.landSelectionMode = false;
                System.out.println("Land Selection Mode is now over.");
                System.out.println("You will now have to purchase property to claim it!\n");
            } else if (Controller.roundNum > 2) {
                calculateRoundOrder();
                System.out.println("The order this round goes as follows: ");
                for (Player p : Controller.players) {
                    System.out.println("Player: " + p.getName() + "\n" + "Score: " + p.getScore() + "\n");
                }
            }
        }
        else Controller.currentPlayerTurn++;
        startTurn();
    }

    /**
     * Calculates the player's production at the end of his/her turn.
     * @param p The player who's production will be calculated.
     */
    private static void calculateProduction(Player p) {
        System.out.println("\n" + p.getName() +"'s production at the end of the turn: ");

        int totalEnergy = 0;
        int totalFood = 0;
        int totalOre = 0;

        for (LandPlot q : p.getLandOwned()) {
            if (q.hasMule() && (p.getEnergy() > 1)) {
                Mule m = q.getMuleOnProp();
                if (m.getType() == Mule.Configuration.ENERGY) {
                    p.addEnergy(q.getEnergyProd());
                    totalEnergy += q.getEnergyProd();
                } else if (m.getType() == Mule.Configuration.FOOD) {
                    p.addFood(q.getFoodProd());
                    totalFood += q.getFoodProd();
                } else {
                    p.addOre(q.getOreProd());
                    totalOre += q.getOreProd();
                }
                p.subtractEnergy(1);
            }
        }

        System.out.println("Total Energy Produced: " + totalEnergy);
        System.out.println("Total Food Produced: " + totalFood);
        System.out.println("Total Ore Produced: " + totalOre);
    }

    /**
     * Calculates the round order based on the player's scores.
     */
    private static void calculateRoundOrder() {
        Controller.players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                if (p1.getScore() < p2.getScore()) return -1;
                else if (p1.getScore() == p2.getScore()) return 0;
                else return 1;
            }
        });
    }

    /**
     * Allows the current player to buy property.
     * Tells whether or not the player can do so.
     * @return boolean as to whether or not he/she has enough money to do so.
     */
    public static boolean buyProperty() {
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        if (cur.getMoneyStash() - 300 < 0) {
            System.out.println("\nYou do not have enough funds to purchase a property.\n");
            return false;
        } else {
            cur.subtractMoney(300);
            System.out.println(cur.getName() + " has $" + cur.getMoneyStash() + " leftover.\n");
            return true;
        }
    }

    /**
     * Controls the beginning of the land selection phase.
     */
    public static void landSelectionPhase() {
        System.out.println("\nSince this is Land Selection mode...");
        System.out.println(Controller.players.get(Controller.currentPlayerTurn).getName() + " please select a free plot of land...\n");
    }

    //********************************** Main Game Controls *********************************************

    /**
     * Controls the begining of the game.
     * Initializes a new store and random events object.
     */
    public static void startGame() {
        store = new Store();
        randEvents = new RandomEvents();
    }

    /**
     * Dictates the start of a turn.
     * Calculates the turn time and random events.
     * Keeps track of an individuals time in turn.
     */
    public static void startTurn() {
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        System.out.println(cur.getName() + ", your turn starts now!\n");

        //TURN TIME CALCULATION CODE
        int timeInSec;
        if (Round.getFoodReq() <= cur.getFood()) timeInSec = 50;
        else if (Round.getFoodReq() > cur.getFood() && cur.getFood() > 0) timeInSec = 30;
        else timeInSec = 5;
        System.out.println(cur.getName() + " has " + timeInSec + " seconds to play.");
        Duration turnTime = new Duration(timeInSec * 1000);

        //RANDOM EVENTS CODE
        if (randEvents.calcRandChance()) {
            if ((Controller.players.get(Controller.currentPlayerTurn)).equals(Controller.players.getFirst())) {
                System.out.println("Lowest score, so only postive random event should occur:\n");
                randEvents.randEventOccurs(3);
            } else {
                System.out.println("Random event could be good or bad!\n");
                randEvents.randEventOccurs(6);
            }
        } else {
            System.out.println("No random events occur for you.\n");
        }

        //TIMER SET UP CODE
        Controller.timer.setTimeline(turnTime);
        Controller.timer.startTimer();
    }

    /**
     * Dictates the end of a player's turn.
     */
    public static void endTurn() {
        Controller.timer.stopTimer();
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        calculateProduction(cur);
        System.out.println("\n" + cur.getName() + "'s turn is over.\n");
        System.out.println(cur.inventoryToString() + "\n");
    }
}
