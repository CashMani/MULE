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
    public static NumPlayers numPlayers = NumPlayers.ONE;

    @Override
    public void start(Stage primaryStage) throws Exception{
        init();
        primary = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primary.setTitle("Welcome to M.U.L.E.");
        primary.setScene(new Scene(root, 500, 500));
        primary.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
