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
        String path = "barbie girl lyrics.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
