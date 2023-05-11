package video;

import core.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SeekSlider extends ProgressBar {
    public static final SeekSlider INSTANCE = new SeekSlider();
    private static final BooleanProperty isSeeking = new SimpleBooleanProperty(false);
    public static final int HEIGHT = VideoControls.HEIGHT / 18;
    private boolean wasPlayingBeforeSeek;

    private SeekSlider() {
        setId("seekSlider");
    }

    public void initializeListeners() {
        MediaPlayer mediaPlayer = Video.INSTANCE.getMediaPlayer();
        setPrefWidth(Main.getPrimaryStage().getWidth() - 40);
        setPrefHeight(HEIGHT);
        if (mediaPlayer != null) {
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                    double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                    double totalTime = mediaPlayer.getMedia().getDuration().toSeconds();

                    double progress = currentTime / totalTime;
                    setProgress(progress);
                });
                setOnMousePressed(this::videoTimelineUpdate);
                setOnMouseDragged(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        videoTimelineUpdate(event);
                    }
                });
                setOnMouseReleased(event -> {
                    if (isSeeking()) {
                        isSeeking.set(false);
                    }
                });
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
    }

    private void videoTimelineUpdate(MouseEvent event) {
        isSeeking.set(true);
        MediaPlayer mediaPlayer = Video.INSTANCE.getMediaPlayer();
        double mouseX = event.getX();
        double width = getWidth();
        double seekTime = (mouseX / width) * mediaPlayer.getMedia().getDuration().toSeconds();
        mediaPlayer.seek(Duration.seconds(seekTime));
    }

    private boolean isSeeking() {
        return isSeeking.get();
    }
}
