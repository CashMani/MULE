package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

    public enum Difficulty { BEGINNER, STANDARD, TOURNAMENT};
    public enum MapType {STANDARD, RANDOM};
    public enum NumPlayers {ONE, TWO, THREE, FOUR};
    public enum Race {PACKER, SPHEROID, HUMANOID, LEGGITE, FLAPPER, BONZOID, MECHTRON, GOLLUMER};

    public static ArrayList<Player> players = new ArrayList<>();

    public static Difficulty difficulty = Difficulty.BEGINNER;
    public static MapType mapType = MapType.STANDARD;
    public static NumPlayers numPlayers = NumPlayers.TWO;

    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        screenController primary = screenController.getInstance();
        primary.setStage(stage);
        primary.setStartPage();
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
