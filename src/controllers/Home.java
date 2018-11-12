package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import model.Attempt;
import model.AttemptKind;


public class Home {

    private final AudioClip mApplauseSound;

    @FXML
    private VBox container;

    @FXML
    private Label title;

    @FXML
    private TextArea message;

    private Attempt mCurrentAttempt;

    private StringProperty mTimerText;

    private Timeline mTimeline;

    public Home(){
        mApplauseSound = new AudioClip(getClass().getResource("/raw/applause.wav").toExternalForm());
        mTimerText = new SimpleStringProperty();
        setTimerText(0);
    }

    public String getTimerText() {
//        System.out.printf("   " + mTimerText.get());
        return mTimerText.get();
    }

    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(String timerText) {
        mTimerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds) {
        int minites = (int) remainingSeconds / 60;
        int seconds = (int) remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d", minites, seconds));
    }

    private void prepareAttempt(AttemptKind attemptKind){
        reset();
        mCurrentAttempt = new Attempt(attemptKind, "");
        addAttemptStyle(attemptKind);
        title.setText(attemptKind.getAttemptName());
        setTimerText(mCurrentAttempt.getRemainingSeconds());
        mTimeline = new Timeline();
        mTimeline.setCycleCount(attemptKind.getTotalSeconds());
        mTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            mCurrentAttempt.tick();
            setTimerText(mCurrentAttempt.getRemainingSeconds());
//            System.out.printf(mTimeline.getStatus().toString());
//            System.out.printf(mCurrentAttempt.getRemainingSeconds() + "");
        }));
        mTimeline.setOnFinished( e -> {
            saveCurrentAttempt();
            mApplauseSound.play();
            prepareAttempt(mCurrentAttempt.getAttemptKind() == AttemptKind.FOCUS ?
                    AttemptKind.BREAK : AttemptKind.FOCUS);
        });
    }

    private void saveCurrentAttempt() {
        mCurrentAttempt.setMessage(message.getText());
        mCurrentAttempt.save();
    }

    private void reset() {
        clearAttemptStyles();
        if (mTimeline != null && mTimeline.getStatus() == Timeline.Status.RUNNING){
            mTimeline.stop();
        }
    }

    private void addAttemptStyle(AttemptKind attemptKind) {
        container.getStyleClass().add(attemptKind.toString().toLowerCase());
    }

    private void clearAttemptStyles() {
        container.getStyleClass().remove("playing");
        for (AttemptKind attemptKind : AttemptKind.values()) {
            container.getStyleClass().remove(attemptKind.toString().toLowerCase());
        }
    }

    public void playTimer(){
        container.getStyleClass().add("playing");
        mTimeline.play();
    }

    public void pauseTimer(){
        container.getStyleClass().remove("playing");
        mTimeline.pause();
    }

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handlePlay(ActionEvent event) {
        if (mCurrentAttempt == null) {
            handleRestart(event);
        }else {
            playTimer();
        }
    }

    public void handlePause(ActionEvent event) {
        pauseTimer();
    }
}
