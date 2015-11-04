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
        screenController sc = screenController.getInstance();
        sc.setStartPage();
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
            setMapButtons();

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
        if (!landTaken[0][0] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroZero)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroZero);
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
                zeroZero.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][0] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroTwoClicked(Event event) {
        if (!landTaken[0][2] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroTwo)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroTwo);
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
                zeroTwo.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("1 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroTwo));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][2] = true;

            }
        }
    }
    /**
    *grid that can be clicked on map
    *@param event the on click action
    */
    public void zeroOneClicked(Event event) {
        if (!landTaken[0][1] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroOne)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroOne);
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
                zeroOne.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroOne));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][1] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroThreeClicked(Event event) {
        if (!landTaken[0][3] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroThree)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroThree);
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
                zeroThree.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroThree));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][3] = true;
            }
        }
    }
        /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroFourClicked(Event event) {
        if (!landTaken[0][4] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroFour)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroFour);
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
                zeroFour.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("River", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroFour));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][4] = true;
            }
        }
    }
        /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroFiveClicked(Event event) {
        if (!landTaken[0][5] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroFive)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroFive);
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
                zeroFive.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroFive));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][5] = true;

            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroSixClicked(Event event) {
        if (!landTaken[0][6] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroSix)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroSix);
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
                zeroSix.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("3 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][6] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroEightClicked(Event event) {
        if (!landTaken[0][8] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroEight)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroEight);
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
                zeroEight.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroEight));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][8] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void zeroSevenClicked(Event event) {
        if (!landTaken[0][7] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(zeroSeven)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(zeroSeven);
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
                zeroSeven.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroSeven));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][7] = true;

            }
        }
    }

    //Row One
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneZeroClicked(Event event) {
        if (!landTaken[1][0] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneZero)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneZero);
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
                oneZero.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][0] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneTwoClicked(Event event) {
        if (!landTaken[1][2] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneTwo)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneTwo);
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
                oneTwo.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneTwo));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][2] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneOneClicked(Event event) {
        if (!landTaken[1][1] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneOne)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneOne);
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
                oneOne.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("1 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][1] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneThreeClicked(Event event) {
        if (!landTaken[1][3] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneThree)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneThree);
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
                oneThree.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneThree));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][3] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneFourClicked(Event event) {
        if (!landTaken[1][4] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneFour)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneFour);
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
                oneFour.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("River", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneFour));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][4] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneFiveClicked(Event event) {
        if (!landTaken[1][5] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneFive)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneFive);
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
                oneFive.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneFive));

                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][5] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneSixClicked(Event event) {
        if (!landTaken[1][6] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneSix)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneSix);
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
                oneSix.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneSix));

                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][6] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneEightClicked(Event event) {
        if (!landTaken[1][8] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneEight)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneEight);
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
                oneEight.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("3 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneEight));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][8] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void oneSevenClicked(Event event) {
        if (!landTaken[1][7] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(oneSeven)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(oneSeven);
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
                oneSeven.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), oneSeven));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][7] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    //Row Two
    public void twoZeroClicked(Event event) {
        if (!landTaken[2][0] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoZero)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoZero);
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
                twoZero.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("3 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][0] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoTwoClicked(Event event) {
        if (!landTaken[2][2] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoTwo)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoTwo);
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
                twoTwo.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoTwo));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][2] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoOneClicked(Event event) {
        if (!landTaken[2][1] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoOne)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoOne);
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
                twoOne.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoOne));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][1] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoThreeClicked(Event event) {
        if (!landTaken[2][3] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoThree)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoThree);
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
                twoThree.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoThree));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][3] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoFiveClicked(Event event) {
        if (!landTaken[2][5] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoFive)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoFive);
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
                twoFive.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoFive));

                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][5] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoSixClicked(Event event) {
        if (!landTaken[2][6] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoSix)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoSix);
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
                twoSix.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoSix));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][6] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoEightClicked(Event event) {
        if (!landTaken[2][8] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoEight)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoEight);
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
                twoEight.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("1 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoEight));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][8] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void twoSevenClicked(Event event) {
        if (!landTaken[2][7] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(twoSeven)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(twoSeven);
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
                twoSeven.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), twoSeven));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][7] = true;
            }
        }
    }

    //Row Three
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeZeroClicked(Event event) {
        if (!landTaken[3][0] || mulePlacementMode) {
            if (landSelectionMode || GameController.buyProperty()) {
                if (mulePlacementMode) {
                    if (players.get(currentPlayerTurn).ownsLand(threeZero)) {
                        LandPlot land = players.get(currentPlayerTurn).getLand(threeZero);
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
                else threeZero.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][0] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeTwoClicked(Event event) {
        if (!landTaken[3][2] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeTwo)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeTwo);
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
                threeTwo.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plains", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeTwo));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][2] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeOneClicked(Event event) {
        if (!landTaken[3][1] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeOne)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeOne);
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
                threeOne.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("2 Mountains", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), zeroZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][1] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeThreeClicked(Event event) {
        if (!landTaken[3][3] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeThree)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeThree);
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
                threeThree.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeThree));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][3] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeFourClicked(Event event) {
        if (!landTaken[3][4] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeFour)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeFour);
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
                threeFour.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("River", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeFour));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][4] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeFiveClicked(Event event) {
        if (!landTaken[3][5] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeFive)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeFive);
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
                threeFive.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeFive));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][5] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeSixClicked(Event event) {
        if (!landTaken[3][6] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeSix)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeSix);
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
                threeSix.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("2 Mountains", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeSix));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][6] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeEightClicked(Event event) {
        if (!landTaken[3][8] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeEight)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeEight);
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
                threeEight.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeEight));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][8] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void threeSevenClicked(Event event) {
        if (!landTaken[3][7] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(threeSeven)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(threeSeven);
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
                threeSeven.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), threeSeven));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][7] = true;
            }
        }
    }

    //Row Four
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourZeroClicked(Event event) {
        if (!landTaken[4][0] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourZero)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourZero);
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
                fourZero.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourZero));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][0] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourTwoClicked(Event event) {
        if (!landTaken[4][2] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourTwo)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourTwo);
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
                fourTwo.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("2 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourTwo));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][2] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourOneClicked(Event event) {
        if (!landTaken[4][1] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourOne)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourOne);
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
                fourOne.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourOne));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][1] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourThreeClicked(Event event) {
        if (!landTaken[4][3] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourThree)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourThree);
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
                fourThree.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourThree));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][3] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourFourClicked(Event event) {
        if (!landTaken[4][4] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourFour)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourFour);
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
                fourFour.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("River", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourFour));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][4] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourFiveClicked(Event event) {
        if (!landTaken[4][5] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourFive)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourFive);
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
                fourFive.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourFive));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][5] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourSixClicked(Event event) {
        if (!landTaken[4][6] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourSix)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourSix);
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
                fourSix.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourSix));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][6] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourEightClicked(Event event) {
        if (!landTaken[4][8] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourEight)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourEight);
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
                fourEight.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("2 Mountain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourEight));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][8] = true;
            }
        }
    }
    /**
    *grid space that can be clicked on map
    *@param event the on click action
    */
    public void fourSevenClicked(Event event) {
        if (!landTaken[4][7] || mulePlacementMode) {
            if (mulePlacementMode) {
                if (players.get(currentPlayerTurn).ownsLand(fourSeven)) {
                    LandPlot land = players.get(currentPlayerTurn).getLand(fourSeven);
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
                fourSeven.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(players.get(currentPlayerTurn).getColor().toString()),
                                        null,
                                        null)));
                //Adding land plot to person's land plot array list
                players.get(currentPlayerTurn).addLand(new LandPlot("Plain", players.get(currentPlayerTurn),
                        players.get(currentPlayerTurn).getColor(), fourSeven));
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][7] = true;
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

            // save each land plot's button location
            for (int i = 0; i < numPlayers; i++) {
                for (int j = 0; j < players.get(i).getLandCount(); j++) {
                    for (int y = 0; y < 5; y++) {
                        for (int x = 0; x < 9; x++) {
                            Button btnLandPlot = players.get(i).getLandOwned().get(j).getButton();
                            if (btnLandPlot.equals(map[y][x])) {
                                out.writeObject(new Point(x, y));
                            }
                        }
                    }
                }
            }

            // saves a String of the player's color for each land plot if the player owns that plot
            String[][] coloredMapSaver = new String[5][9];
            for (int y = 0; y < 5; y++)
                for (int x = 0; x < 9; x++)
                    for (int i = 0; i < numPlayers; i++)
                        if (players.get(i).ownsLand(map[y][x]))
                            coloredMapSaver[y][x] = players.get(i).getColor().toString();
            out.writeObject(coloredMapSaver);

            out.close();
            saveDataOut.close();
        } catch (IOException e) {
            //e.printStackTrace();
            return;
        } catch (Exception e) {
            //e.printStackTrace();
            return;
        }
        System.out.println("Game saved");
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
      /*      setMapButtons();
            // load each land plot's button location
            try {
                for (int i = 0; i < numPlayers; i++) {
                    for (int j = 0; j < players.get(i).getLandCount(); j++) {
                        for (int y = 0; y < 5; y++) {
                            for (int x = 0; x < 9; x++) {
                                tempObj = in.readObject();
                                if (tempObj instanceof Point) {
                                    Point coord = (Point) tempObj;
                                    players.get(i).getLandOwned().get(j).setLocation(map[coord.y][coord.x]);
                                }
                            }
                        }
                    }
                }
            } catch (EOFException e) {
                // expected
            }

            // load coloredMap array
            String[][] coloredMapSaver = (String[][]) tempObj;
            for (int y = 0; y < 5; y++)
                for (int x = 0; x < 9; x++)
                    coloredMap[y][x] = (null != coloredMapSaver[y][x]) ? Paint.valueOf(coloredMapSaver[y][x]) : null;

*/
            // re set-up game
            returnToMap();

            in.close();
            fileIn.close();
        } catch (IOException i) {
            //i.printStackTrace();
            System.out.println("No game saved. \n");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Game Loaded");
        timer.startTimer();
    }
    /**
    *initializes map buttons
    */
    private void setMapButtons() {
        map[0][0] = zeroZero;
        map[0][1] = zeroOne;
        map[0][2] = zeroTwo;
        map[0][3] = zeroThree;
        map[0][4] = zeroFour;
        map[0][5] = zeroFive;
        map[0][6] = zeroSix;
        map[0][7] = zeroSeven;
        map[0][8] = zeroEight;

        map[1][0] = oneZero;
        map[1][1] = oneOne;
        map[1][2] = oneTwo;
        map[1][3] = oneThree;
        map[1][4] = oneFour;
        map[1][5] = oneFive;
        map[1][6] = oneSix;
        map[1][7] = oneSeven;
        map[1][8] = oneEight;

        map[2][0] = twoZero;
        map[2][1] = twoOne;
        map[2][2] = twoTwo;
        map[2][3] = twoThree;
        map[2][5] = twoFive;
        map[2][6] = twoSix;
        map[2][7] = twoSeven;
        map[2][8] = twoEight;

        map[3][0] = threeZero;
        map[3][1] = threeOne;
        map[3][2] = threeTwo;
        map[3][3] = threeThree;
        map[3][4] = threeFour;
        map[3][5] = threeFive;
        map[3][6] = threeSix;
        map[3][7] = threeSeven;
        map[3][8] = threeEight;

        map[4][0] = fourZero;
        map[4][1] = fourOne;
        map[4][2] = fourTwo;
        map[4][3] = fourThree;
        map[4][4] = fourFour;
        map[4][5] = fourFive;
        map[4][6] = fourSix;
        map[4][7] = fourSeven;
        map[4][8] = fourEight;
    }


}