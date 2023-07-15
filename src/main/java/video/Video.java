package video;

import core.Main;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URI;

public class Video extends MediaView {
    public static Video INSTANCE = new Video();
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
        if (VideoProperties.INSTANCE.isPlaying()) {
            mediaPlayer.play();
        }
        VideoProperties.INSTANCE.setSeekPosition(0);
        mediaPlayer.setMute(VideoProperties.INSTANCE.isMuted());
        mediaPlayer.setVolume(VideoProperties.INSTANCE.getVolumeLevel());
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);
        mediaPlayer.seek(new Duration(0));
        mediaPlayer.setOnReady(() -> {
            SeekSlider.INSTANCE.setMaxSeekPosition(mediaPlayer.getMedia().getDuration().toMillis());
            currentTimeListener();
        });
    }

    private void currentTimeListener() {
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            VideoProperties.INSTANCE.setSeekPosition(Math.max(newValue.toMillis(), 0));
        });
    }

    private void listeners() {
        setOnMouseMoved(event -> VideoControls.INSTANCE.resetTimeout(true));
    }
}