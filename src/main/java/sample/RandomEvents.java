package sample;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by Alexandra Link on 10/21/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Dictates the Random Events that may occur during MULE game play.
 */
public class RandomEvents implements Serializable {
    Random rand;

    /**
     * Random events constructor.
     */
    public RandomEvents() {
        rand = new Random();
    }

    /**
     * Determines whether or not a Random event will occur.
     * Executed by utilization of a random number generator and number comparison
     * in order to creat probablity. There exists a 26% chance that a random
     * event will occur.
     * @return true if a random event is to occur, false if not.
     */
    public boolean calcRandChance() {

        int chance = rand.nextInt(99);
        if (chance <= 26) {
            System.out.println("Random event occurs:\n");
            return true;
        }
        return false;
    }

    /**
     * The random event that is to occur when calcRandChance returns true.
     * Uses a switch-case  and random number generator to toggle through
     * a number of viable event options. Random events may add to or subtract
     * from a Player's inventory.
     * @param option the random event that is to occur in the MULE game round.
     */
    public void randEventOccurs(int option) {
        int event = rand.nextInt(option) + 1;
        int m = Round.getRandEventFactor();

        switch (event) {

            case 1:
                System.out.println("YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD " +
                        "AND 2 ENERGY UNITS.\n");
                (Controller.players.get(Controller.currentPlayerTurn)).addFood(3);
                (Controller.players.get(Controller.currentPlayerTurn)).addEnergy(2);
                break;
            case 2:
                System.out.println("A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.\n");
                (Controller.players.get(Controller.currentPlayerTurn)).addOre(2);
                break;
            case 3:
                int sold = 8 * m;
                System.out.println("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $" + sold + ".\n");
                (Controller.players.get(Controller.currentPlayerTurn)).addMoney(sold);
                break;
            case 4:
                int rat = 2 * m;
                System.out.println("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $" + rat + ".\n");
                (Controller.players.get(Controller.currentPlayerTurn)).addMoney(rat);
                break;
            case 5:
                int repairs = 4 * m;
                System.out.println("FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $" + repairs + ".\n");
                (Controller.players.get(Controller.currentPlayerTurn)).subtractMoney(repairs);
                break;
            case 6:
                System.out.println("MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD.\n");
                int foodSupply = (Controller.players.get(Controller.currentPlayerTurn)).getFood();
                System.out.println("You had " + foodSupply + " units of food.\n");
                foodSupply = foodSupply / 2;
                (Controller.players.get(Controller.currentPlayerTurn)).subtractFood(foodSupply);
                System.out.println("You lost " + foodSupply + " units of food.\n");
                break;
            case 7:
                int inlaws = 6 * m;
                System.out.println("YOUR SPACE GYPSY INLAWS MADE A MESS OF THE TOWN. IT COST YOU $" + inlaws +
                        " TO CLEAN IT UP.\n");
                (Controller.players.get(Controller.currentPlayerTurn)).subtractMoney(inlaws);
                break;
        }
    }
}
