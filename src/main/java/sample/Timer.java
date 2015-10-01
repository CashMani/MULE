package sample;
import javafx.animation.*;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
/**
 * Created by AlexandraLink on 9/30/15.
 * Perfected by Mani on 9/30/15
 */
public class Timer {

    Timeline timeline;
    Duration timeBegan;
    Duration timeRemaining;
    Duration turnTime;

    public Timer() {

    }

    public void setTimeline(Duration turnTime) {
        timeRemaining = turnTime;
        timeline = new Timeline(new KeyFrame(turnTime, ae -> GameController.endTurn()));

    }


    public void startTimer() {
        timeline.play();
        timeBegan = timeline.getCurrentTime();
    }

    public Duration stopTimer() {
        turnTime = timeline.getCurrentTime();
        timeline.stop();
        System.out.println(timeRemaining.subtract(turnTime));
        return timeRemaining.subtract(timeBegan);
    }

    public Duration getTimeRemaining() {
        return timeRemaining.subtract(turnTime);
    }
}
