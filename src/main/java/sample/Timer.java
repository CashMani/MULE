package sample;
import javafx.animation.*;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;


/**
 * Created by Alexandra Link on 9/30/15.
 * Barely edited by Mani on 9/30/15
 * Edited by McKenzie Elliott on 11/4/15.
 *
 * Times each Player's turn within the MULE game.
 */
public class Timer {

    Timeline timeline;
    Duration startTime;
    Duration stopTime;

    /**
     * Timer constructor
     */
    public Timer() {

    }

    /**
     * Sets the duration of time a user will have to take a turn
     * @param turnTime the allocated duration of time for a turn
     */
    public void setTimeline(Duration turnTime) {
        startTime = turnTime;
        timeline = new Timeline(new KeyFrame(turnTime, ae -> GameController.endTurn()));

    }

    /**
     * Begins the timer.
     */
    public void startTimer() {
        timeline.play();
    }

    /**
     * Stops the timer.
     * @return the duration of time that has passed since the timer was started
     */
    public Duration stopTimer() {
        stopTime = timeline.getCurrentTime();
        timeline.stop();
        return startTime.subtract(stopTime);
    }

    /**
     * Gets the time remaining based on the start time and allotted stop time
     * @return the amount of time remaining for a turn
     */
    public Duration getTimeRemaining() {
        return startTime.subtract(stopTime);
    }
}
