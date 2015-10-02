package sample;
import javafx.animation.*;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
/**
 * Created by Alexandra Link on 9/30/15.
 * Barely edited by Mani on 9/30/15
 */
public class Timer {

    Timeline timeline;
    Duration startTime;
    Duration stopTime;

    public Timer() {

    }

    public void setTimeline(Duration turnTime) {
        startTime = turnTime;
        timeline = new Timeline(new KeyFrame(turnTime, ae -> GameController.endTurn()));

    }

    public void startTimer() {
        timeline.play();
    }

    public Duration stopTimer() {
        stopTime = timeline.getCurrentTime();
        timeline.stop();
        System.out.println("Timer stopped at " + startTime.subtract(stopTime).toSeconds() + "\n");
        return startTime.subtract(stopTime);
    }

    public Duration getTimeRemaining() {
        return startTime.subtract(stopTime);
    }
}
