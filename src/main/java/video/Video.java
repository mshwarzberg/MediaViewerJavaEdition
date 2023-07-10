package video;

import core.Main;
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
    private MediaPlayer mediaPlayer = new MediaPlayer(Main.EMPTY_VIDEO);

    private Video() {
        setMediaPlayer(mediaPlayer);
        listeners();
    }

    public void updateMediaPlayer(URI source) {
        String path = source.toString();
        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        setMediaPlayer(mediaPlayer);
        if (isPlaying()) {
            mediaPlayer.play();
        }
        mediaPlayer.setMute(isMuted());
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);
        mediaPlayer.seek(new Duration(0));
        mediaPlayer.setOnReady(() -> {
            SeekSlider.INSTANCE.setMaxSeekPosition(mediaPlayer.getMedia().getDuration().toMillis());
            currentTimeListener();
        });
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

    private void currentTimeListener() {
        getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            SeekSlider.INSTANCE.setSeekPosition(newValue.toMillis());
        });
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