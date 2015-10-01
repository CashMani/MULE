package sample;
import javafx.animation.*;
import javafx.util.Duration;
/**
 * Created by AlexandraLink on 9/30/15.
 * Perfected by Mani on 9/30/15
 */
public class Timer {

    Timeline timeline;
    Duration timeBegan;
    Duration timeRemaining;

    public Timer() {

    }

    public void setTimeline(Duration turnTime) {
        timeRemaining = turnTime;
        timeline = new Timeline(new KeyFrame(turnTime, ae -> GameController.endTurn()));
    }


    public void startTimer() {
        timeline.play();
        while (timeline.getCurrentTime().greaterThan(new Duration(0))) {
            System.out.println("Time Remaining: " + timeRemaining.subtract(timeline.getCurrentTime()).toString());
        }
        timeBegan = timeline.getCurrentTime();
    }

    public Duration stopTimer() {
        timeRemaining = timeline.getCurrentTime();
        timeline.stop();
        return timeRemaining.subtract(timeBegan);
    }
}
