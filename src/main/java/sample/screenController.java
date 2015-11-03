package sample;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Created by Alexandra Link on 11/3/15.
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

    public static screenController getInstance() { return instance;}

    public void setStage(Stage stage) {
        theStage = stage;
    }

    public void setStartPage() {
        theStage.setScene(startScreen);
    }

    public void setAboutTeam() {
        theStage.setScene(aboutTeam);
    }

    public void setGameConfiguration() {
        theStage.setScene(gameConfiguration);
    }

    public void setPlayerInfo() {
        theStage.setScene(playerInfo);
    }

    public void setMainMap() {
        theStage.setScene(mainMap);

    }

    public void setTown() {
        theStage.setScene(town);
    }

    public void setPub() {
        theStage.setScene(pub);
    }

    public void setResourceStore() {
        theStage.setScene(resourceStore);
    }

    public void setMuleStore() {
        theStage.setScene(muleStore);
    }
}

