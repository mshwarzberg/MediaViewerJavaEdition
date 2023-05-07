package controls;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SeekSlider extends Slider {
    public static final SeekSlider INSTANCE = new SeekSlider();
    private double totalDuration;
    private Timeline valueTimeline;
    private boolean isSeeking = false;
    private MediaPlayer mediaPlayer;

    private SeekSlider() {
        setPrefWidth(Double.MAX_VALUE);
        setPadding(new Insets(5, 5, 5, 5));
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        setup();
    }

    private void setup() {
        mediaPlayer.setOnReady(() -> {
            totalDuration = mediaPlayer.getMedia().getDuration().toSeconds();
            createValueTimeline(mediaPlayer);
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSeeking) {
                double currentTime = newValue.toSeconds();
                double position = currentTime / totalDuration;
                setValue(position * 100);
            }
        });

        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isSeeking) {
                double newPosition = totalDuration * (newValue.doubleValue() / 100);
                mediaPlayer.seek(new Duration(newPosition * 1000));
            }
        });
    }

    private void createValueTimeline(MediaPlayer mediaPlayer) {
        if (valueTimeline != null) {
            valueTimeline.stop();
        }

        valueTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!isSeeking) {
                double newValue = getValue();
                double newPosition = totalDuration * (newValue / 100);
                mediaPlayer.seek(new Duration(newPosition * 1000));
            }
        }));

        valueTimeline.setCycleCount(Timeline.INDEFINITE);
        valueTimeline.play();

        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSeeking) {
                valueTimeline.playFromStart();
            }
        });

        setOnMouseClicked(event -> isSeeking = true);
        setOnMousePressed(event -> isSeeking = true);
        setOnMouseReleased(event -> isSeeking = false);
    }
}


