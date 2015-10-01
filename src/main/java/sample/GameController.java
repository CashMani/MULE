package sample;
import javafx.util.Duration;


/**
 * Created by mani on 9/24/15.
 */
public class GameController {

    //***************************** Land Selection ********************************************************************
    public static void nextTurn() {
        if (Controller.currentPlayerTurn == 0 && Controller.roundNum == 0) {
            
        } else endTurn();
        if (Controller.currentPlayerTurn == Controller.players.size() - 1) {
            Controller.currentPlayerTurn = 0;
            Controller.roundNum++;
            if (Controller.roundNum == 2) {
                Controller.landSelectionMode = false;
                System.out.println("Land Selection Mode is now over.");
                System.out.println("You will now have to purchase property to claim it!");
            }
        }
        else Controller.currentPlayerTurn++;
        startTurn();
    }

    public static boolean buyProperty() {
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        if (cur.getMoneyStash() - 300 < 0) {
            System.out.println("You do not have enough funds to purchase a property.");
            return false;
        } else {
            cur.subtractMoney(300);
            System.out.println(cur.getName() + " has $" + cur.getMoneyStash() + " leftover.");
            return true;
        }
    }

    public static void landSelectionPhase() {
        System.out.println(Controller.players.get(Controller.currentPlayerTurn).getName() + " please select a plot of land...");
    }

    //********************************** Main Game Controls *********************************************
    public static void startTurn() {
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
        int timeInSec = 50;
        if (Round.getFoodReq() <= cur.getFood()) timeInSec = 50;
        else if (Round.getFoodReq() > cur.getFood() && cur.getFood() > 0) timeInSec = 30;
        else timeInSec = 5;
        Duration turnTime = new Duration(timeInSec * 1000);

        Controller.timer.setTimeline(turnTime);
        Controller.timer.startTimer();
    }

    public static void endTurn() {
        Controller.timer.stopTimer();
        Player cur = Controller.players.get(Controller.currentPlayerTurn);
            System.out.println(cur.getName() + "'s turn is over.");
            System.out.println(cur.inventoryToString());
    }
}
