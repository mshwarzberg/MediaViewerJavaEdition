package video;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URI;

public class Video extends MediaView {
    public static Video INSTANCE = new Video();
    private final BooleanProperty isPlaying = new SimpleBooleanProperty(true);
    private final BooleanProperty isMuted = new SimpleBooleanProperty(true);

    private Video() {
        listeners();
    }

    void updateMediaPlayer(URI source) {
        String path = source.toString();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(path));
        setMediaPlayer(mediaPlayer);
        if (isPlaying()) {
            mediaPlayer.play();
        }
        mediaPlayer.setMute(isMuted());
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);
        mediaPlayer.seek(new Duration(0));
        SeekSlider.INSTANCE.initializeListeners();
    }

    public boolean isPlaying() {
        return isPlaying.get();
    }

    public void invertPlaying() {
        isPlaying.set(!isPlaying());
    }

    public void setPlaying(boolean value) {
        isPlaying.set(value);
    }

    public boolean isMuted() {
        return isMuted.get();
    }

    public void invertMuted() {
        isMuted.set(!isMuted());
    }

    private void pausePlay(boolean shouldPlay) {
        if (shouldPlay) {
            getMediaPlayer().play();
        } else {
            getMediaPlayer().pause();
        }
    }

    private void listeners() {
        isMuted.addListener((observable, wasMuted, isValueMute) -> {
            Video.INSTANCE.getMediaPlayer().setMute(isValueMute);
        });
        isPlaying.addListener((observable, wasPlaying, isValuePlaying) -> {
            Video.INSTANCE.pausePlay(isValuePlaying);
        });
        setOnMouseMoved(event -> VideoControls.INSTANCE.resetTimeout(true));
    }
}