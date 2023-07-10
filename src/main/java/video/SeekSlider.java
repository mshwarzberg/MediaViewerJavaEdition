package video;

import core.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SeekSlider extends ProgressBar {
    public static final SeekSlider INSTANCE = new SeekSlider();
    private final BooleanProperty isSeeking = new SimpleBooleanProperty(false);
    private final DoubleProperty seekPosition;
    public static final int HEIGHT = VideoControls.HEIGHT / 18;
    private double maxSeekPosition = 0;
    private boolean wasPlayingBeforeSeek;

    private SeekSlider() {
        seekPosition = new SimpleDoubleProperty(0);
        setId("seekSlider");
        getStyleClass().add("slider");
        initializeListeners();
        setPrefWidth(Double.MAX_VALUE);
        setHeight(HEIGHT);
        seekPosition.set(0);
    }

    public void initializeListeners() {
        seekPosition.addListener((observable, oldValue, newValue) -> {
            MediaPlayer mediaPlayer = Video.INSTANCE.getMediaPlayer();
            double toDouble = newValue.doubleValue();
            if (toDouble < 1) {
                setProgress(toDouble);
                mediaPlayer.seek(Duration.millis(toDouble * maxSeekPosition));
            } else {
                setProgress(toDouble / maxSeekPosition);
            }
        });
        setOnMousePressed(this::videoTimelineUpdate);
        setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                videoTimelineUpdate(event);
            }
        });
        setOnMouseReleased(event -> {
            isSeeking.set(false);
        });
        isSeeking.addListener((observable, n, currentlySeeking) -> {
            if (currentlySeeking) {
                wasPlayingBeforeSeek = Video.INSTANCE.isPlaying();
                Video.INSTANCE.setPlaying(false);
            } else {
                Video.INSTANCE.setPlaying(wasPlayingBeforeSeek);
            }
        });
    }

    private void videoTimelineUpdate(MouseEvent event) {
        isSeeking.set(true);
        seekPosition.set((event.getX() / getWidth()));
    }

    private boolean isSeeking() {
        return isSeeking.get();
    }

    public void setMaxSeekPosition(double videoDuration) {
        maxSeekPosition = videoDuration;
    }

    public void setSeekPosition(double seekPosition) {
        this.seekPosition.set(seekPosition);
    }
}
