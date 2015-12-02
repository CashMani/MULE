package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import java.io.File;

/**
 * Created by Mani Japra and Alex Link.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Sets the stage for the user's visuals and sets details of a Player's
 * potential attributes as well as sets up the basics of the game's core
 * variable values.
 */
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
        String path = "C64 m.u.l.e. music.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
    }

    /**
     * Runs the game.
     * @param args all the game items to be run
     */
    public static void main(String[] args) {
        launch(args);
    }
}
