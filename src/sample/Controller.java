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
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;


import java.awt.event.ActionEvent;
import java.io.IOException;

public class Controller {
    @FXML
    public Slider difficultySlider;
    public Slider mapTypeSlider;
    public TextField namePicker;
    public ColorPicker colorChooser;
    public Stage addPlayerStage = new Stage();
    public Label listPlayers = new Label();

    @FXML
    public void aboutTeamClicked(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("aboutTeam.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void gameConfigClicked(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gameConfig.fxml"));
        Scene scene = new Scene(root);

        Main.primary.setScene(scene);
    }

    @FXML
    public void backToMainMenu(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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

    }

    @FXML
    public void addAPlayer(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gameConfigAdvanced.fxml"));
        Scene scene = new Scene(root);

        addPlayerStage.setScene(scene);

        addPlayerStage.show();
    }

    @FXML
    public void addNewPlayer(Event event) throws IOException {
        Player newPlayer = new Player(namePicker.getText(),"", colorChooser.getValue().toString());
        Main.players.add(newPlayer);
        listPlayers.setText(listPlayers.getText() + Main.players.get(0).getName() + "\n");
    }
}
