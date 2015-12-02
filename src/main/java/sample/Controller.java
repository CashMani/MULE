package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import sun.util.resources.cldr.sr.TimeZoneNames_sr_Latn;


import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Paint.*;
/**
*Class that controls the game functions
* @author Alex Link, Mani Japra, Justin Thornburgh, McKenzie Elliott, Joanna Parkhurst, Sara Norris
* @version 1.0
*/

public class Controller implements Initializable {
    @FXML
    public Slider difficultySlider;
    public Slider mapTypeSlider;

    //Player One
    public TextField name_player1;
    public ToggleGroup race_player1;
    public ColorPicker color_player1;
    @FXML
    public TextField playerOneTag;

    //Player Two
    public TextField name_player2;
    public ToggleGroup race_player2;
    public ColorPicker color_player2;

    //Player Three
    public TextField name_player3;
    public ToggleGroup race_player3;
    public ColorPicker color_player3;

    //Player Four
    public TextField name_player4;
    public ToggleGroup race_player4;
    public ColorPicker color_player4;

    public boolean[][] landTaken = new boolean[5][9];
    Button[][] map = new Button[5][9];

    //Map Buttons
    public Button zeroZero;
    public Button zeroOne;
    public Button zeroTwo;
    public Button zeroThree;
    public Button zeroFour;
    public Button zeroFive;
    public Button zeroSix;
    public Button zeroSeven;
    public Button zeroEight;

    public Button oneZero;
    public Button oneOne;
    public Button oneTwo;
    public Button oneThree;
    public Button oneFour;
    public Button oneFive;
    public Button oneSix;
    public Button oneSeven;
    public Button oneEight;

    public Button twoZero;
    public Button twoOne;
    public Button twoTwo;
    public Button twoThree;
    public Button twoFour;
    public Button twoFive;
    public Button twoSix;
    public Button twoSeven;
    public Button twoEight;

    public Button threeZero;
    public Button threeOne;
    public Button threeTwo;
    public Button threeThree;
    public Button threeFour;
    public Button threeFive;
    public Button threeSix;
    public Button threeSeven;
    public Button threeEight;

    public Button fourZero;
    public Button fourOne;
    public Button fourTwo;
    public Button fourThree;
    public Button fourFour;
    public Button fourFive;
    public Button fourSix;
    public Button fourSeven;
    public Button fourEight;

    //Timer
    public static Timer timer = new Timer();
//    public Label timeLeftDisp;
//    public final Timeline timeline = new Timeline(new KeyFrame(new Duration(1000) , ae -> displayTime()));

    public static LinkedList<Player> players = new LinkedList<>();
    public static int currentPlayerTurn = 0;
    public static boolean landSelectionMode = true;
    public static int roundNum = 0;

    public static boolean mulePlacementMode = false;
    public static Mule muleToAdd;

    public Main.NumPlayers howMany;

    public int playerTurn = 0; // always begins with first player
    public int turnRound = 0; // increases by one after every player goes once
    /**
    *initializes the game
    *@param location the web location
    *@param resources the resources used by the player
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

//    public final void displayTime() {
//        timeLeftDisp.setText(Controller.timer.getTimeRemaining().toString());
//    }
    /**
    *takes you to the about team display screen when clicked 
    *@param event the action of clicking the about
    *@throws IOException when unexpected event occurs
    */
    @FXML
    public void aboutTeamClicked(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setAboutTeam();
    }
    /**
    *takes you to game configuration screen
    *@param event the action of clicking config
    *@throws IOException when unexpected event occurs
    */
    @FXML
    public void gameConfigClicked(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setGameConfiguration();
    }
    /**
    *action when back to main menu button is clicked
    *@param event the event of clicking the button
    *@throws IOException in an unexpected event
    */
    @FXML
    public void backToMainMenu(Event event) throws IOException {
        ////screenController sc = screenController.getInstance();
        //sc.setStartPage();
        Main.stage.close();
    }
    /**
    *action when one exits game
    *@param event clicking the exit button
    */

    @FXML
    public void exitGame(Event event) {
        Main.stage.close();
    }
    /**
    *the second game configuration page that sets difficulties
    *@param event the on click action
    *@throws IOException when unexpected event occurs
    */
    @FXML
    public void gameConfigPage2(Event event) throws IOException {
        switch ((int) difficultySlider.getValue()) {
            case 0:
                Main.difficulty = Main.Difficulty.BEGINNER;
                break;
            case 1:
                Main.difficulty = Main.Difficulty.STANDARD;
                break;
            case 2:
                Main.difficulty = Main.Difficulty.TOURNAMENT;
                break;
            default:
                Main.difficulty = Main.Difficulty.BEGINNER;
                break;
        }

        switch ((int) mapTypeSlider.getValue()) {
            case 1:
                Main.mapType = Main.MapType.STANDARD;
                break;
            case 2:
                Main.mapType = Main.MapType.RANDOM;
                break;
            default:
                Main.mapType = Main.MapType.STANDARD;
                break;
        }

        System.out.println("Difficulty Selected: " + Main.difficulty);
        System.out.println("Map Type Selected: " + Main.mapType);

        screenController sc = screenController.getInstance();
        sc.setPlayerInfo();
    }
    /**
    *the player starts the game
    *@param event the on click event
    *@throws IOException when unexpected occurence
    */
    @FXML
    public void playGame(Event event) throws IOException {
        boolean validNames = false;

        try {
            Player player1 = new Player(Verifier.verifyName(name_player1.getText()), Verifier.verifyRace(race_player1), color_player1.getValue());
            Player player2 = new Player(Verifier.verifyName(name_player2.getText()), Verifier.verifyRace(race_player2), color_player2.getValue());
            Player player3 = null;
            Player player4 = null;

            players.add(player1);
            players.add(player2);

            System.out.println(race_player1.getSelectedToggle());

            if (!(name_player3.getText().isEmpty())) {
                player3 = new Player(Verifier.verifyName(name_player3.getText()), Verifier.verifyRace(race_player3), color_player3.getValue());
                players.add(player3);
                Main.numPlayers = Main.NumPlayers.THREE;
                if (!(name_player4.getText().isEmpty())) {
                    player4 = new Player(Verifier.verifyName(name_player4.getText()), Verifier.verifyRace(race_player4), color_player4.getValue());
                    players.add(player4);
                    Main.numPlayers = Main.NumPlayers.FOUR;
                }
            }

            if (!(name_player4.getText().isEmpty())) {
                player3 = new Player(Verifier.verifyName(name_player3.getText()), Verifier.verifyRace(race_player3), color_player3.getValue());
                player4 = new Player(Verifier.verifyName(name_player4.getText()), Verifier.verifyRace(race_player4), color_player4.getValue());
                players.add(player3);
                players.add(player4);
                Main.numPlayers = Main.NumPlayers.FOUR;
            }

            if (Main.numPlayers == Main.NumPlayers.TWO) {
                System.out.println(player1 + "\n" + player2 + "\n");
            } else if (Main.numPlayers == Main.NumPlayers.THREE) {
                System.out.println(player1 + "\n" + player2 + "\n" + player3 + "\n");
            } else if (Main.numPlayers == Main.NumPlayers.FOUR) {
                System.out.println(player1 + "\n" + player2 + "\n" + player3 + "\n" + player4 + "\n");
            }
            validNames = true;
        } catch (IllegalArgumentException e) {
            System.out.println("You did not enter a valid name for one of your players. Try again.");
        }

        if (validNames) {
            screenController sc = screenController.getInstance();
            sc.setMainMap();

            System.out.println("Welcome Players!");
            GameController.startGame();
            GameController.landSelectionPhase();
        }
    }
    /**
    *player leaves the town 
    *@param event the on click event of player leaving town 
    *@throws IOException in an unexpected occurence 
    */
    @FXML
    public void leaveTown(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setMainMap();
    }
    /**
    *player goes into the town 
    *@param event the on click event
    *@throws IOException in an unexpected occurence
    */
    @FXML
    public void goToTown(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setTown();
    }

    /**
    *first grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroZeroClicked(Event event) {
        btnHandlerHelper(event, 0, 0, zeroZero);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroTwoClicked(Event event) {
        btnHandlerHelper(event, 0, 2, zeroTwo);
    }
    /**
    *grid that can be clicked on map
    *@param event the on click action
    */
    public void zeroOneClicked(Event event) {
        btnHandlerHelper(event, 0, 1, zeroOne);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroThreeClicked(Event event) {
        btnHandlerHelper(event, 0, 3, zeroThree);
    }
        /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroFourClicked(Event event) {
        btnHandlerHelper(event, 0, 4, zeroFour);
    }
        /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroFiveClicked(Event event) {
        btnHandlerHelper(event, 0, 5, zeroFive);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroSixClicked(Event event) {
        btnHandlerHelper(event, 0, 6, zeroSix);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroEightClicked(Event event) {
        btnHandlerHelper(event, 0, 8, zeroEight);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroSevenClicked(Event event) {
        btnHandlerHelper(event, 0, 7, zeroSeven);
    }

    //Row One
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneZeroClicked(Event event) {
        btnHandlerHelper(event, 1, 0, oneZero);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneTwoClicked(Event event) {
        btnHandlerHelper(event, 1, 2, oneTwo);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneOneClicked(Event event) {
        btnHandlerHelper(event, 1, 1, oneOne);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneThreeClicked(Event event) {
        btnHandlerHelper(event, 1, 3, oneThree);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneFourClicked(Event event) {
        btnHandlerHelper(event, 1, 4, oneFour);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneFiveClicked(Event event) {
        btnHandlerHelper(event, 1, 5, oneFive);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneSixClicked(Event event) {
        btnHandlerHelper(event, 1, 6, oneSix);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneEightClicked(Event event) {
        btnHandlerHelper(event, 1, 8, oneEight);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneSevenClicked(Event event) {
        btnHandlerHelper(event, 1, 7, oneSeven);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    //Row Two
    public void twoZeroClicked(Event event) {
        btnHandlerHelper(event, 2, 0, twoZero);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoTwoClicked(Event event) {
        btnHandlerHelper(event, 2, 2, twoTwo);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoOneClicked(Event event) {
        btnHandlerHelper(event, 2, 1, twoOne);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoThreeClicked(Event event) {
        btnHandlerHelper(event, 2, 3, twoThree);
    }
    /**
     *grid space that can be clicked on map
     *@param event the on click action
     */
    public void twoFourClicked(Event event) {
        btnHandlerHelper(event, 2, 4, twoFour);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoFiveClicked(Event event) {
        btnHandlerHelper(event, 2, 5, twoFive);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoSixClicked(Event event) {
        btnHandlerHelper(event, 2, 6, twoSix);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoEightClicked(Event event) {
        btnHandlerHelper(event, 2, 8, twoEight);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoSevenClicked(Event event) {
        btnHandlerHelper(event, 2, 7, twoSeven);
    }

    //Row Three
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeZeroClicked(Event event) {
        btnHandlerHelper(event, 3, 0, threeZero);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeTwoClicked(Event event) {
        btnHandlerHelper(event, 3, 2, threeTwo);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeOneClicked(Event event) {
        btnHandlerHelper(event, 3, 1, threeOne);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeThreeClicked(Event event) {
        btnHandlerHelper(event, 3, 3, threeThree);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeFourClicked(Event event) {
        btnHandlerHelper(event, 3, 4, threeFour);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeFiveClicked(Event event) {
        btnHandlerHelper(event, 3, 5, threeFive);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeSixClicked(Event event) {
        btnHandlerHelper(event, 3, 6, threeSix);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeEightClicked(Event event) {
        btnHandlerHelper(event, 3, 8, threeEight);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeSevenClicked(Event event) {
        btnHandlerHelper(event, 3, 7, threeSeven);
    }

    //Row Four
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourZeroClicked(Event event) {
        btnHandlerHelper(event, 4, 0, fourZero);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourTwoClicked(Event event) {
        btnHandlerHelper(event, 4, 2, fourTwo);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourOneClicked(Event event) {
        btnHandlerHelper(event, 4, 1, fourOne);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourThreeClicked(Event event) {
        btnHandlerHelper(event, 4, 3, fourThree);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourFourClicked(Event event) {
        btnHandlerHelper(event, 4, 4, fourFour);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourFiveClicked(Event event) {
        btnHandlerHelper(event, 4, 5, fourFive);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourSixClicked(Event event) {
        btnHandlerHelper(event, 4, 6, fourSix);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourEightClicked(Event event) {
        btnHandlerHelper(event, 4, 8, fourEight);
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourSevenClicked(Event event) {
        btnHandlerHelper(event, 4, 7, fourSeven);
    }

    /*
    Handles button clicks on the map for all buttons
     */
    private void btnHandlerHelper(Event event, int x, int y, Button thisButton) {
        if (!landTaken[x][y] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(new Point(x, y))) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(new Point (x, y));
                    if (!land.hasMule()) {
                        land.setMule(muleToAdd);
                        muleToAdd = null;
                    } else {
                        System.out.println("There is already a MULE there.");
                        System.out.println("Your MULE ran away.");
                        muleToAdd = null;
                    }
                } else {
                    System.out.println("You do not own that land!!");
                    System.out.println("Your MULE ran away.");
                    muleToAdd = null;
                }
                mulePlacementMode = false;
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
            }
            else if (landSelectionMode || GameController.buyProperty()) {
                thisButton.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), thisButton, new Point(x, y)));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[x][y] = true;
            }
        }
    }

    /**
    *player goes to the pub in town
    *@param event the on click action
    *@throws IOException in an unexpected occurence
    */
    @FXML
    public void goToPub(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setPub();
    }
    /**
    *leaves pub to go to town 
    *@param event on click action
    *@throws IOException in an unexpected occurence
    */
    @FXML
    public void returnToTown(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setTown();
    }
    /**
    *player goes to gamble 
    *@param event 
    *@throws IOException 
    */
    @FXML
    public void gamble(Event event) throws IOException {
        Duration timeToGamble = timer.stopTimer();
        System.out.println("Timer stopped at: " + timeToGamble);
        Pub pubFun = new Pub(timeToGamble);
        int earnings = pubFun.gamble();
        System.out.println(players.get(currentPlayerTurn).getName() + " just won $" + earnings + "\n");
        //Try to get a visual to pop up later
        players.get(currentPlayerTurn).addMoney(earnings);
        returnToMap();
        GameController.nextTurn();
    }
    /**
    *returns from gambling back to the map
    *@throws IOException in an unexpected event
    */
    public void returnToMap() throws IOException {
        screenController sc = screenController.getInstance();
        sc.setMainMap();
    }
    /**
    *enters the store
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void enterStore(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setResourceStore();
    }
    /**
    *leaves the store
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void exitStore(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setTown();
    }
    /**
    *enters the mule purchase
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void toMuleSales(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setMuleStore();
    }
    /**
    *ends mule purchase
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void exitMuleStore(Event event) throws IOException {
        screenController sc = screenController.getInstance();
        sc.setResourceStore();
    }
    /**
    *purchase food
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void buyFood(Event event) throws IOException {
        GameController.store.buyFood(1);
    }
    /**
    *purchase energy
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void buyEnergy(Event event) throws IOException {
        GameController.store.buyEnergy(1);
    }
    /**
    *player purchase ore
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void buyOre(Event event) throws IOException {
        GameController.store.buyOre(1);
    }
    /**
    *player sells food
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void sellFood(Event event) throws IOException {
        GameController.store.sellFood(1);
    }
    /**
    *player sells  energy
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void sellEnergy(Event event) throws IOException {
        GameController.store.sellEnergy(1);
    }
    /**
    *player sells ore
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void sellOre(Event event) throws IOException {
        GameController.store.sellOre(1);
    }
    /**
    *player sells mule
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void sellMule(Event event) throws IOException {
        GameController.store.sellMule();
    }
    /**
    *player buy mule ore
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void buyOreMule(Event event) throws IOException {
        GameController.store.buyMule(Mule.Configuration.ORE);
    }
    /**
    *player buys mule food
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void buyFoodMule(Event event) throws IOException {
        GameController.store.buyMule(Mule.Configuration.FOOD);
    }
    /**
    *player buys mule energy
    *@param event on click event
    *@throws IOException in an unexpected event 
    */
    public void buyEnergyMule(Event event) throws IOException {
        GameController.store.buyMule(Mule.Configuration.ENERGY);
    }
    /**
    *player places mules
    *@param event on click event
    *@throws IOException in an unexpected event 
    */

    @FXML
    public void placeMule(Event event) {
        Player cur = Controller.players.get(currentPlayerTurn);
        if (cur.hasMule()) {
            muleToAdd = cur.getMule();
            mulePlacementMode = true;
            System.out.println("\nPlease select a land plot you wish to add your MULE to: ");
            System.out.println("NOTE: You must add it to one of your own land plots or else your MULE will be lost");
            System.out.println("If you attempt to add a MULE to a land plot that already has a MULE, your new MULE will be lost as well.\n");
        } else {
            System.out.println("Silly " + cur.getName() + " - You don't have a MULE in your inventory.");
        }
    }
    /**
    *saves game
    *@param event on click event
    */
    @FXML
    public void saveGame(Event event) {
        try {
            FileOutputStream saveDataOut = new FileOutputStream("savedata.ser");
            ObjectOutputStream out = new ObjectOutputStream(saveDataOut);

            // save global data
            out.writeObject(Main.difficulty);
            out.writeObject(Main.mapType);
            out.writeObject(Main.numPlayers);
            out.writeObject(GameController.store);
            out.writeObject(GameController.randEvents);
            out.writeObject(players);
            out.writeObject(currentPlayerTurn);
            out.writeObject(landSelectionMode);
            out.writeObject(mulePlacementMode);
            out.writeObject(roundNum);
            out.writeObject(muleToAdd);
            out.writeObject(howMany);
            out.writeObject(turnRound);
            out.writeObject(playerTurn);
            out.writeObject(landTaken);

            // save remaining time in current player's turn
            out.writeObject(timer.stopTimer());

            // saves players' colors
            int numPlayers = 2;
            numPlayers = (howMany == Main.NumPlayers.THREE) ? 3 : numPlayers;
            numPlayers = (howMany == Main.NumPlayers.FOUR) ? 4 : numPlayers;
            out.writeObject(numPlayers);
            for (int i = 0; i < numPlayers; i++) {
                out.writeObject(players.get(i).getColor().toString());
            }

            out.close();
            saveDataOut.close();
        } catch (IOException e) {
            System.out.println("Problem Saving Game.  Game was not saved successfully.");
            return;
        }
        System.out.println("Game saved");

        for(Player cur: players) {
            System.out.println(cur.inventoryToString() + "\n");
        }
    }
    /**
     *loads saved game
     *@param event on click event
     */
    @FXML
    public void loadGame(Event event) {
        try {
            FileInputStream fileIn = new FileInputStream("savedata.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // load global data
            Main.difficulty = (Main.Difficulty) in.readObject();
            Main.mapType = (Main.MapType) in.readObject();
            Main.numPlayers = (Main.NumPlayers) in.readObject();
            GameController.store = (Store) in.readObject();
            GameController.randEvents = (RandomEvents) in.readObject();
            players = (LinkedList) in.readObject();
            currentPlayerTurn = (Integer) in.readObject();
            landSelectionMode = (Boolean) in.readObject();
            mulePlacementMode = (Boolean) in.readObject();
            roundNum = (Integer) in.readObject();
            muleToAdd = (Mule) in.readObject();
            howMany = (Main.NumPlayers) in.readObject();
            turnRound = (Integer) in.readObject();
            playerTurn = (Integer) in.readObject();
            landTaken = (boolean[][]) in.readObject();

            //load remaining time in current player's turn
            timer = new Timer();
            timer.setTimeline((Duration) in.readObject());

            int numPlayers = (Integer) in.readObject();
            // loads each player's color
            for (int i = 0; i < numPlayers; i++) {
                players.get(i).setColor(Color.web((String) in.readObject()));
                // sets each player's owned land plots' colors to the player's color
                for (int j = 0; j < players.get(i).getLandCount(); j++) {
                    players.get(i).getLandOwned().get(j).setColor(players.get(i).getColor());
                }
            }

            Object tempObj = new Object();
            // set each land plot's button location
            for (int i = 0; i < numPlayers; i++) {
                for (int j = 0; j < players.get(i).getLandCount(); j++) {
                    LandPlot plot = players.get(i).getLandOwned().get(j);
                    Button btn = new Button();
                    btn.setBackground(
                            new Background(
                                    new BackgroundFill(valueOf(players.get(i).getColor().toString()), null, null)));
                    plot.setLocation(btn);
                }
            }

            /*
            for (int i = 0; i < numPlayers; i++) {
                for (int j = 0; j < players.get(i).getLandCount(); j++) {
                    System.out.println(players.get(i).getLandOwned().get(j).getButton());
                }
            }*/

            // re set-up game
            screenController sc = screenController.getInstance();
            sc.setMainMap();

            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("No save file found.  Save a game before trying to load.");
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("No save file found.  Save a game before trying to load.");
            return;
        }
        System.out.println("Game Loaded");

        for(Player cur: players) {
            System.out.println(cur.inventoryToString() + "\n");
        }
        timer.startTimer();
    }

    /*
    Allows access of buttons given their coordinates on the map
     */
    private Button getButtonFromMap(int i, int j) {

        if (i == 0 && j == 0) return zeroZero;
        else if (i == 0 && j == 1) return zeroOne;
        else if (i == 0 && j == 2) return zeroTwo;
        else if (i == 0 && j == 3) return zeroThree;
        else if (i == 0 && j == 4) return zeroFour;
        else if (i == 0 && j == 5) return zeroFive;
        else if (i == 0 && j == 6) return zeroSix;
        else if (i == 0 && j == 7) return zeroSeven;
        else if (i == 0 && j == 8) return zeroEight;

        else if (i == 1 && j == 0) return oneZero;
        else if (i == 1 && j == 1) return oneOne;
        else if (i == 1 && j == 2) return oneTwo;
        else if (i == 1 && j == 3) return oneThree;
        else if (i == 1 && j == 4) return oneFour;
        else if (i == 1 && j == 5) return oneFive;
        else if (i == 1 && j == 6) return oneSix;
        else if (i == 1 && j == 7) return oneSeven;
        else if (i == 1 && j == 8) return oneEight;

        else if (i == 2 && j == 0) return twoZero;
        else if (i == 2 && j == 1) return twoOne;
        else if (i == 2 && j == 2) return twoTwo;
        else if (i == 2 && j == 3) return twoThree;
        else if (i == 2 && j == 4) return twoFour;
        else if (i == 2 && j == 5) return twoFive;
        else if (i == 2 && j == 6) return twoSix;
        else if (i == 2 && j == 7) return twoSeven;
        else if (i == 2 && j == 8) return twoEight;

        else if (i == 3 && j == 0) return threeZero;
        else if (i == 3 && j == 1) return threeOne;
        else if (i == 3 && j == 2) return threeTwo;
        else if (i == 3 && j == 3) return threeThree;
        else if (i == 3 && j == 4) return threeFour;
        else if (i == 3 && j == 5) return threeFive;
        else if (i == 3 && j == 6) return threeSix;
        else if (i == 3 && j == 7) return threeSeven;
        else if (i == 3 && j == 8) return threeEight;

        else if (i == 4 && j == 0) return fourZero;
        else if (i == 4 && j == 1) return fourOne;
        else if (i == 4 && j == 2) return fourTwo;
        else if (i == 4 && j == 3) return fourThree;
        else if (i == 4 && j == 4) return fourFour;
        else if (i == 4 && j == 5) return fourFive;
        else if (i == 4 && j == 6) return fourSix;
        else if (i == 4 && j == 7) return fourSeven;
        else if (i == 4 && j == 8) return fourEight;

        else throw new IllegalArgumentException("There is not button at location (" + i + ", " + j + ")");
    }

}