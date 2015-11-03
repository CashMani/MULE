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

public class Controller implements Initializable {
    @FXML
    public Slider difficultySlider;
    public Slider mapTypeSlider;

    //Player One
    public TextField name_player1;
    public ToggleGroup race_player1;
    public ColorPicker color_player1;
    public Label playerOneTag;

    //Player Two
    public TextField name_player2;
    public ToggleGroup race_player2;
    public ColorPicker color_player2;
    public Label playerTwoTag;

    //Player Three
    public TextField name_player3;
    public ToggleGroup race_player3;
    public ColorPicker color_player3;
    public Label playerThreeTag;

    //Player Four
    public TextField name_player4;
    public ToggleGroup race_player4;
    public ColorPicker color_player4;
    public Label playerFourTag;

    public boolean[][] landTaken = new boolean[5][9];
    public Paint[][] coloredMap = new Paint[5][9];

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

    public Button[][] map = new Button[5][9];

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

    public Stage addPlayerStage = new Stage();
    public Label listPlayers = new Label();


    public Main.NumPlayers howMany;

    public int playerTurn = 0; // always begins with first player
    public int turnRound = 0; // increases by one after every player goes once


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        timeline.play();
    }

//    public final void displayTime() {
//        timeLeftDisp.setText(Controller.timer.getTimeRemaining().toString());
//    }

    @FXML
    public void aboutTeamClicked(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("aboutTeam.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void gameConfigClicked(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gameConfig.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void backToMainMenu(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void exitGame(Event event) {
        Main.primary.close();
    }

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

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addPlayers.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

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
            playerOneTag = new Label(players.get(0).getName());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("playScreen.fxml"));
            Scene scene = new Scene(root);

            Main.primary.setScene(scene);

            System.out.println("Welcome Players!");
            GameController.startGame();
            GameController.landSelectionPhase();
        }
    }

    @FXML
    public void leaveTown(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("playScreen.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
        repaintMap();
    }

    @FXML
    public void goToTown(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("townScreen.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    public void repaintMap() {
        System.out.println("Made it to repaint");
        int i = 0;
        for(Button b : map[0]) {
            if (coloredMap[0][i] != null) {
                System.out.println(coloredMap[0][i].toString());
                b.setBackground(
                        new Background(
                                new BackgroundFill(coloredMap[0][i],
                                        null,
                                        null)));
            }
            i++;
        }
        i = 0;
        for(Button b : map[1]) {
            if (coloredMap[1][i] != null) {
                b.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(coloredMap[1][i].toString()),
                                        null,
                                        null)));
            }
            i++;
        }
        i = 0;
        for(Button b : map[2]) {
            if (coloredMap[2][i] != null) {
                b.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(coloredMap[2][i].toString()),
                                        null,
                                        null)));
            }
            i++;
        }
        i = 0;
        for(Button b : map[3]) {
            if (coloredMap[3][i] != null) {
                b.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(coloredMap[3][i].toString()),
                                        null,
                                        null)));
            }
            i++;
        }
        i = 0;
        for(Button b : map[4]) {
            if (coloredMap[4][i] != null) {
                b.setBackground(
                        new Background(
                                new BackgroundFill(valueOf(coloredMap[4][i].toString()),
                                        null,
                                        null)));
            }
            i++;
        }
    }

    // Row Zero
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
                coloredMap[0][0] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][0] = true;
            }
        }
    }

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
                coloredMap[0][2] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][2] = true;

            }
        }
    }

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
                coloredMap[0][1] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][1] = true;
            }
        }
    }

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
                coloredMap[0][3] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][3] = true;
            }
        }
    }

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
                coloredMap[0][4] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][4] = true;
            }
        }
    }

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
                coloredMap[0][5] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][5] = true;

            }
        }
    }

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
                coloredMap[0][6] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][6] = true;
            }
        }
    }

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
                coloredMap[0][8] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][8] = true;
            }
        }
    }

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
                coloredMap[0][7] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[0][7] = true;

            }
        }
    }

    //Row One

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
                coloredMap[1][0] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][0] = true;
            }
        }
    }

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
                coloredMap[1][2] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][2] = true;
            }
        }
    }

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
                coloredMap[1][1] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][1] = true;
            }
        }
    }

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
                coloredMap[1][3] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][3] = true;
            }
        }
    }

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
                coloredMap[1][4] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][4] = true;
            }
        }
    }

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

                coloredMap[1][5] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][5] = true;
            }
        }
    }

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

                coloredMap[1][6] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][6] = true;
            }
        }
    }

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
                coloredMap[1][8] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][8] = true;
            }
        }
    }

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
                coloredMap[1][7] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[1][7] = true;
            }
        }
    }

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
                coloredMap[2][0] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][0] = true;
            }
        }
    }

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
                coloredMap[2][2] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][2] = true;
            }
        }
    }

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
                coloredMap[2][1] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][1] = true;
            }
        }
    }

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

                coloredMap[2][3] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][3] = true;
            }
        }
    }

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
                coloredMap[2][5] = valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][5] = true;
            }
        }
    }

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
                coloredMap[2][6] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][6] = true;
            }
        }
    }

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
                coloredMap[2][8] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][8] = true;
            }
        }
    }

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
                coloredMap[2][7] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[2][7] = true;
            }
        }
    }

    //Row Three

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
                coloredMap[3][0] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][0] = true;
            }
        }
    }

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
                coloredMap[3][2] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][2] = true;
            }
        }
    }

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
                coloredMap[3][1] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][1] = true;
            }
        }
    }

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
                coloredMap[3][3] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][3] = true;
            }
        }
    }

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
                coloredMap[3][4] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][4] = true;
            }
        }
    }

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
                coloredMap[3][5] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][5] = true;
            }
        }
    }

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
                coloredMap[3][6] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][6] = true;
            }
        }
    }

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
                coloredMap[3][8] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][8] = true;
            }
        }
    }

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
                coloredMap[3][7] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[3][7] = true;
            }
        }
    }

    //Row Four

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
                coloredMap[4][0] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][0] = true;
            }
        }
    }

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
                coloredMap[4][2] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][2] = true;
            }
        }
    }

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
                coloredMap[4][1] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][1] = true;
            }
        }
    }

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
                coloredMap[4][3] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][3] = true;
            }
        }
    }

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
                coloredMap[4][4] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][4] = true;
            }
        }
    }

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
                coloredMap[4][5] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][5] = true;
            }
        }
    }

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
                coloredMap[4][6] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][6] = true;
            }
        }
    }

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
                coloredMap[4][8] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][8] = true;
            }
        }
    }

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
                coloredMap[4][7] = Paint.valueOf(players.get(currentPlayerTurn).getColor().toString());
                GameController.nextTurn();
                if (landSelectionMode) GameController.landSelectionPhase();
                landTaken[4][7] = true;
            }
        }
    }

    @FXML
    public void goToPub(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Pub.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void returnToTown(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("townScreen.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

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

    public void returnToMap() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("playScreen.fxml"));
        Scene scene = new Scene(root);
        repaintMap();
        Main.primary.setScene(scene);
    }

    public void enterStore(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("store1.fxml"));
        Scene scene = new Scene(root);
        System.out.println(GameController.store.toString());
        Main.primary.setScene(scene);
    }

    public void exitStore(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("townScreen.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    public void toMuleSales(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("muleStore.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    public void exitMuleStore(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("store1.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    public void buyFood(Event event) throws IOException {
        GameController.store.buyFood(1);
    }

    public void buyEnergy(Event event) throws IOException {
        GameController.store.buyEnergy(1);
    }

    public void buyOre(Event event) throws IOException {
        GameController.store.buyOre(1);
    }

    public void sellFood(Event event) throws IOException {
        GameController.store.sellFood(1);
    }

    public void sellEnergy(Event event) throws IOException {
        GameController.store.sellEnergy(1);
    }

    public void sellOre(Event event) throws IOException {
        GameController.store.sellOre(1);
    }

    public void sellMule(Event event) throws IOException {
        GameController.store.sellMule();
    }

    public void buyOreMule(Event event) throws IOException {
        GameController.store.buyMule(Mule.Configuration.ORE);
    }

    public void buyFoodMule(Event event) throws IOException {
        GameController.store.buyMule(Mule.Configuration.FOOD);
    }

    public void buyEnergyMule(Event event) throws IOException {
        GameController.store.buyMule(Mule.Configuration.ENERGY);
    }

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
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Game saved");
    }

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
            setMapButtons();
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


            // re set-up game
            returnToMap();

            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Game Loaded");
        timer.startTimer();
    }

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