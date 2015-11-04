package sample;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by Alexandra Link on 10/1/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Creates the Store object that a Player may interact with
 * in order to make purchaes or sell their own inventory
 */
public class Store implements Serializable {

    //Stock indexing:
    //Food(0); Energy(1); Smithore(2); Crystite(3); Mule(4)
    public static int[] stock = {16, 16, 0, 0, 25};
    // Standard/Tournament stock = {8, 8, 8, 0, 14};

    public static int[] prices = {30, 25, 50, 100, 100};

    /**
     * Store object constructor
     */
    public Store() {

    }

    //********************************************** BUY FROM STORE ****************************************************

    /**
     * toString value of store stock
     * @return the toString version of a Store's inventory
     */
    public String toString() {
        String stockString = "Store Stock: \n Food: " + stock[0] + "\n Energy: " + stock[1] + "\n Ore: " + stock[2]
                + "\n Mules: " + stock[4] + "\n";
        return stockString;
    }

    /**
     * Allows a Player to buy food from the store
     * Adds food and subtracts money.
     * @param numWanted the amount of food a Player wishes to purchase
     */
    public void buyFood(int numWanted) {
        if (verifyAvailability(0, numWanted) && verifyFunds(0, numWanted)) {
            (Controller.players.get(Controller.currentPlayerTurn)).addFood(numWanted);
            (Controller.players.get(Controller.currentPlayerTurn)).subtractMoney(30 * numWanted);
            stock[0] = stock[0] - numWanted;
            System.out.println("Successful purchase of food.");
        } else {
            System.out.println("Insufficient funds or out of stock");
        }
    }

    /**
     * Allows a player to buy energy from the Store
     * Adds energy and subtracts money.
     * @param numWanted the amount of energy a Player wishes to purchase
     */
    public void buyEnergy(int numWanted) {
        if (verifyAvailability(1, numWanted) && verifyFunds(1, numWanted)) {
            (Controller.players.get(Controller.currentPlayerTurn)).addEnergy(numWanted);
            (Controller.players.get(Controller.currentPlayerTurn)).subtractMoney(25 * numWanted);
            stock[1] = stock[1] - numWanted;
            System.out.println("Successful purchase of energy.");
        } else {
            System.out.println("Insufficient funds or out of stock.");
        }
    }

    /**
     * Allows a Player to buy Ore from the Store
     * Adds Ore and subtracts money.
     * @param numWanted the amount of ore a Player wishes to purchase
     */
    public void buyOre(int numWanted) {
        if (verifyAvailability(2, numWanted) && verifyFunds(2, numWanted)) {
            (Controller.players.get(Controller.currentPlayerTurn)).addOre(numWanted);
            (Controller.players.get(Controller.currentPlayerTurn)).subtractMoney(50 * numWanted);
            stock[2] = stock[2] - numWanted;
            System.out.println("Successful purchase of ore.");
        } else {
            System.out.println("Insufficient funds or out of stock.");
        }
    }

    /**
     * Allows a Player to purchase Crystite from the Store
     * Adds Crystite and subtracts money.
     * @param numWanted amount of Crystite a Player wishes to purchase
     */
    public void buyCrystite(int numWanted) {
        if (verifyAvailability(3, numWanted) && verifyFunds(3, numWanted)) {
            (Controller.players.get(Controller.currentPlayerTurn)).addCrystite(numWanted);
            (Controller.players.get(Controller.currentPlayerTurn)).subtractMoney(50 * numWanted);
            stock[3] = stock[3] - numWanted;
            System.out.println("Successful purchase of crystite.");
        } else {
            System.out.println("Insufficient funds or out of stock.");
        }
    }

    /**
     * Allows a Player to purchase a Mule from the store.
     * Adds a Mule and subtracts money.
     * @param type the type of Mule a Player wishes to purchase
     */
    public  void buyMule(Mule.Configuration type) {
        if (verifyAvailability(4, 1)) {
            int price;
            if (type == Mule.Configuration.FOOD) {
                price = 125;
            } else if (type == Mule.Configuration.ENERGY) {
                price = 150;
            } else if (type == Mule.Configuration.ORE) {
                price = 175;
            } else {
                price = 200;
            }
            if ((Controller.players.get(Controller.currentPlayerTurn)).getMoneyStash() >= price &&
                    (Controller.players.get(Controller.currentPlayerTurn).getMule() == null)) {
                Controller.players.get(Controller.currentPlayerTurn).addMule(new Mule(type));
                Controller.players.get(Controller.currentPlayerTurn).subtractMoney(price);
                stock[4]--;
                System.out.println("Mule purchase was successfull!");
            } else {
                System.out.println("Not enough money to buy this mule");
            }
        } else {
            System.out.println("Out of stock :(");
        }

    }

    //********************************************* SELL BACK TO STORE *************************************************

    /**
     * Allows a Player to sell food to the Store.
     * Subtracts food and adds money to a Player's inventory.
     * @param numSold the amount of food sold by the Player to the Store
     */
    public void sellFood(int numSold) {
        if (Controller.players.get(Controller.currentPlayerTurn).getFood() >= numSold) {
            stock[0] = stock[0] + numSold;
            Controller.players.get(Controller.currentPlayerTurn).addMoney(30 * numSold);
            Controller.players.get(Controller.currentPlayerTurn).subtractFood(numSold);
            System.out.println("Successful sale of food. \n $30 credited to your account.");
        } else {
            System.out.println("You do not have that much food to sell.");
        }
    }

    /**
     * Allows a Player to sell energy to the Store.
     * Subtracts energy and adds money to a Player's inventory.
     * @param numSold the amount of Energy a Player wishes to sell
     */
    public void sellEnergy(int numSold) {
        if (Controller.players.get(Controller.currentPlayerTurn).getEnergy() >= numSold) {
            stock[1] = stock[1] + numSold;
            Controller.players.get(Controller.currentPlayerTurn).addMoney(25 * numSold);
            Controller.players.get(Controller.currentPlayerTurn).subtractEnergy(numSold);
            System.out.println("Successful sale of energy. \n $25 credited to your account.");
        } else {
            System.out.println("You do not have that much energy to sell.");
        }
    }

    /**
     * Allows a Player to sell Ore to the Store.
     * Subtracts Ore and adds money to a Player's inventory.
     * @param numSold the amount of Ore a Player wishes to sell.
     */
    public void sellOre(int numSold) {
        if (Controller.players.get(Controller.currentPlayerTurn).getOre() >= numSold) {
            stock[2] = stock[2] + numSold;
            Controller.players.get(Controller.currentPlayerTurn).addMoney(50 * numSold);
            Controller.players.get(Controller.currentPlayerTurn).subtractOre(numSold);
            System.out.println("Successful sale of ore. \n $50 credited to your account.");
        } else {
            System.out.println("You do not have that much ore to sell.");
        }
    }

    /**
     * Allows a Player to sell Crystite to the Store.
     * Subtracts Cyrstite and adds money to a Player's inventory.
     * @param numSold the amount of Crystite a Player chooses to sell to the Store.
     */
    public void sellCrystite(int numSold) {
        if (Controller.players.get(Controller.currentPlayerTurn).getOre() >= numSold) {
            stock[3] = stock[3] + numSold;
            Controller.players.get(Controller.currentPlayerTurn).addMoney(100 * numSold);
            Controller.players.get(Controller.currentPlayerTurn).subtractOre(numSold);
            System.out.println("Successful sale of crystite. \n $100 credited to your account.");
        } else {
            System.out.println("You do not have that much crystite to sell.");
        }
    }

    /**
     * Allows a Player to sell a Mule to the Store.
     * Subtracts a Mule and adds money to a Player's inventory.
     */
    public void sellMule() {
        Player curr = Controller.players.get(Controller.currentPlayerTurn);
        if (curr.getMule() != null) {
            Mule currMule = curr.getMule();
            int sellback;
            if (currMule.getType() == Mule.Configuration.ENERGY) {
                sellback = 150;
            } else if (currMule.getType() == Mule.Configuration.FOOD) {
                sellback = 125;
            } else if (currMule.getType() == Mule.Configuration.ORE) {
                sellback = 150;
            } else {
                sellback = 200;
            }
            curr.addMoney(sellback);
            curr.sellMule();
            System.out.println("Successful sale of mule. \n $" + sellback + " credited to your account.");
            stock[4]++;
        } else {
            System.out.println("You don't have a mule to sell.");
        }
    }

    //***************************************** Private Helper Methods ************************************************

    /**
     * Helper method to verify that a requested item is in stock for purchase
     * @param stockIndex the particular index of the stock item requested
     * @param numPurchased the amount of stock of that item in inventory in the Store
     * @return true if a stocked item is available, false if not
     */
    private boolean verifyAvailability(int stockIndex, int numPurchased) {
        return (stock[stockIndex] >= numPurchased);
    }

    /**
     * Helper method to verify if a Player has enough money to make a desired purchase.
     * @param moneyIndex the amount of money necessary to purchase the desired item.
     * @param numPurchased the number of items to purchase
     * @return true if a Player can afford the desired Store items, false if not
     */
    private boolean verifyFunds(int moneyIndex, int numPurchased) {
        int fundsNeeded = prices[moneyIndex] * numPurchased;
        Player curr = Controller.players.get(Controller.currentPlayerTurn);
        return (curr.getMoneyStash()) >= fundsNeeded;
    }
}
