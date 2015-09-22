package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Slider;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Slider difficultySlider;
    public Slider mapTypeSlider;
    public TextField namePicker;
    public ColorPicker colorChooser;
    public Stage addPlayerStage = new Stage();
    public Label listPlayers = new Label();


    public Main.NumPlayers howMany;

    public int playerTurn = 1; // always begins with first player
    public int turnRound = 0; // increases by one after every player goes once

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


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
            case 0: Main.difficulty = Main.Difficulty.BEGINNER;
                break;
            case 1: Main.difficulty = Main.Difficulty.STANDARD;
                break;
            case 2: Main.difficulty = Main.Difficulty.TOURNAMENT;
                break;
            default: Main.difficulty = Main.Difficulty.BEGINNER;
                break;
        }

        switch ((int) mapTypeSlider.getValue()) {
            case 1: Main.mapType = Main.MapType.STANDARD;
                break;
            case 2: Main.mapType = Main.MapType.RANDOM;
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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("playScreen.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void helloWorld(Event event) {
        System.out.println("Hi, This is Sara's Button!");
    }


//    @FXML
//    public void addNewPlayer(Event event) throws IOException {
//        Player newPlayer = new Player(namePicker.getText(),"", colorChooser.getValue().toString());
//        Main.players.add(newPlayer);
//        listPlayers.setText(listPlayers.getText() + Main.players.get(0).getName() + "\n");
//    }


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
                            this.subtractMoney(300); // subtract money from user, "this" should be player
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
}
