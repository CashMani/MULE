package sample;
import javafx.animation.*;
import javafx.util.Duration;
/**
 * Created by AlexandraLink on 9/30/15.
 */
public class Timer {

    Timeline timeline;
    Duration timeRemaining;

    public Timer(Duration turnTime) {
        timeRemaining = turnTime;
        timeline = new Timeline(new KeyFrame(turnTime, ae -> GameController.endTurn()));
    }

    public void startTimer() {
        timeline.play();
    }

    public Duration stopTimer() {
        timeRemaining = timeline.getCurrentTime();
        timeline.stop();
    }

}
