package sample;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Created by Alexandra Link on 11/3/15.
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * A class to control what the user views on their screen while playing the MULE game.
 */
public class screenController {

    private static screenController instance = new screenController();

    private Scene startScreen;
    private Scene aboutTeam;
    private Scene gameConfiguration;
    private Scene playerInfo;
    private Scene mainMap;
    private Scene town;
    private Scene pub;
    private Scene resourceStore;
    private Scene muleStore;
    //private Scene assay;
    private Stage theStage;
    private Controller mainController;

    /**
     * The Constructor for a screenController object.
     * Defines what should appear on a user's screen via loading fxml resource images and data.
     */
    private screenController() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
            startScreen = new Scene(root);
            root = FXMLLoader.load(getClass().getClassLoader().getResource("gameConfig.fxml"));
            gameConfiguration = new Scene(root);
            //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameConfig.fxml"));
            //gameConfiguration = new Scene(loader.load());
            //Controller c = new Controller();
            //loader.setController(c);

            root = FXMLLoader.load(getClass().getClassLoader().getResource("addPlayers.fxml"));
            playerInfo = new Scene(root);

            root = FXMLLoader.load(getClass().getClassLoader().getResource("playScreen.fxml"));
            mainMap = new Scene(root);
            //FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("playScreen.fxml"));
            //mainMap = new Scene(loader2.load());
            //loader2.setController(c);
            //mainController = loader.getController();

            root = FXMLLoader.load(getClass().getClassLoader().getResource("townScreen.fxml"));
            town = new Scene(root);
            root = FXMLLoader.load(getClass().getClassLoader().getResource("Pub.fxml"));
            pub = new Scene(root);
            root = FXMLLoader.load(getClass().getClassLoader().getResource("store1.fxml"));
            resourceStore = new Scene(root);
            root = FXMLLoader.load(getClass().getClassLoader().getResource("muleStore.fxml"));
            muleStore = new Scene(root);
            root = FXMLLoader.load(getClass().getClassLoader().getResource("aboutTeam.fxml"));
            aboutTeam = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets an instance of the screen for a user to view
     * @return the screen to appear
     */
    public static screenController getInstance() { return instance;}

    public static screenController getNewInstance() {
        instance = new screenController();
        return instance;
    }

    /**
     * A setter for the screen's stage.
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        theStage = stage;
    }

    /**
     * A setter for the game's start page.
     */
    public void setStartPage() {
        theStage.setScene(startScreen);
    }

    /**
     * A setter for the game's "About Team" page.
     * Allows this page to be accessed and viewed on screen.
     */
    public void setAboutTeam() {
        theStage.setScene(aboutTeam);
    }

    /**
     * A setter for the game's configuration.
     * Uses the game's stage to retrieve the particular game config.
     */
    public void setGameConfiguration() {
        theStage.setScene(gameConfiguration);
    }

    /**
     * A setter for a Player object's info.
     */
    public void setPlayerInfo() {
        theStage.setScene(playerInfo);
    }

    /**
     * A setter for the main map's screen to appear.
     */
    public void setMainMap() {
        theStage.setScene(mainMap);

    }

    /**
     * A setter for the image of the town to appear.
     */
    public void setTown() {
        theStage.setScene(town);
    }

    /**
     * A setter for the image of the pub to appear.
     */
    public void setPub() {
        theStage.setScene(pub);
    }

    /**
     * A setter for the image of the Store resources to appear.
     */
    public void setResourceStore() {
        theStage.setScene(resourceStore);
    }

    /**
     * A setter for the image of the MULE store to appear.
     */
    public void setMuleStore() {
        theStage.setScene(muleStore);
    }
}

