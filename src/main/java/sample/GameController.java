package sample;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * Created by mani on 9/24/15.
 */

    public class GameController {

        public static Store store;

    //***************************** Land Selection ********************************************************************
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

    public static void landSelectionPhase() {
        System.out.println("\nSince this is Land Selection mode...");
        System.out.println(Controller.players.get(Controller.currentPlayerTurn).getName() + " please select a free plot of land...\n");
    }

    //********************************** Main Game Controls *********************************************

    public static void startGame() {
        store = new Store();
    }
    public static void startTurn() {
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        System.out.println(cur.getName() + ", your turn starts now!\n");
        int timeInSec;
        if (Round.getFoodReq() <= cur.getFood()) timeInSec = 50;
        else if (Round.getFoodReq() > cur.getFood() && cur.getFood() > 0) timeInSec = 30;
        else timeInSec = 5;
        System.out.println(cur.getName() + " has " + timeInSec + " seconds to play.");
        Duration turnTime = new Duration(timeInSec * 1000);

        Controller.timer.setTimeline(turnTime);
        Controller.timer.startTimer();
    }

    public static void endTurn() {
        Controller.timer.stopTimer();
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        calculateProduction(cur);
        System.out.println("\n" + cur.getName() + "'s turn is over.\n");
        System.out.println(cur.inventoryToString() + "\n");
    }
}
