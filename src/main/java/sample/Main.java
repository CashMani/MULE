package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

    public static Stage primary = null;

    public enum Difficulty { BEGINNER, STANDARD, TOURNAMENT};
    public enum MapType {STANDARD, RANDOM};
    public enum NumPlayers {ONE, TWO, THREE, FOUR};

    public static ArrayList<Player> players = new ArrayList<>();

    public static Difficulty difficulty = Difficulty.BEGINNER;
    public static MapType mapType = MapType.STANDARD;
    //public static NumPlayers numPlayers = NumPlayers.ONE; // what does this do? why define just one?
    public NumPlayers howMany;

    public int playerTurn = 1; // always begins with first player
    public int turnRound = 0; // increases by one after every player goes once

    @Override
    public void start(Stage primaryStage) throws Exception{
        init();
        primary = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primary.setTitle("Welcome to M.U.L.E.");
        primary.setScene(new Scene(root, 500, 500));
        primary.show();
    }


    //updated land selection
    public void landSelection () {
        boolean landBought  = false;
        switch (howMany) {
            case TWO;
                while (playerTurn < 3) {
                    if (turnRound < 3) {
                        //land is free!
                        //set color of land
                        //place land in inventory
                    } else {
                        //land cost $300
                        if (CLICK LAND) {
                            this.subtractMoney(300); // subtract money from user
                            landBought = true;
                        }
                    }

                    playerTurn++;
                }
                break;
            case THREE:
                while (playerTurn < 4) {
                    if (turnRound < 3) {
                        //land is free!
                    } else {
                        //land cost $300
                        if (CLICK LAND) {
                            this.subtractMoney(300); // subtract money from user
                            landBought = true;
                        }
                    }
                    playerTurn++;
                }
                break;
            case FOUR:
                while (playerTurn < 5) {
                    if (turnRound < 3) {
                        //land is free!
                    } else {
                        //land cost $300
                        if (CLICK LAND) {
                            this.subtractMoney(300); // subtract money from user
                            landBought = true;
                        }
                    }
                    playerTurn++; //go to next player's turn
                }
                break;
        }
        turnRound++; //increase turn round
        //Land selection ends when every player choose to not buy land in a round
        if (landBought == true) {
            landSelection();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
