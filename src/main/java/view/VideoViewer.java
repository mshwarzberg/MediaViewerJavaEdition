package view;

import controls.SeekSlider;
import controls.VideoControls;
import core.Main;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URI;

public class VideoViewer extends StackPane implements ViewController {
    public static final VideoViewer INSTANCE = new VideoViewer();
    public final MediaView mediaView = new MediaView();
    private boolean isPlaying = true;
    private boolean isMuted = true;

    private VideoViewer() {
        getChildren().addAll(mediaView, VideoControls.INSTANCE);
        setAlignment(VideoControls.INSTANCE, Pos.BOTTOM_CENTER);
        Main.getPrimaryStage().showingProperty().addListener((observable -> {
            mediaView.setFitWidth(Main.getPrimaryStage().getWidth());
            mediaView.setFitHeight(Main.getPrimaryStage().getHeight() - 60);
        }));
        setVisible(false);
    }

    @Override
    public void clearSource() {
        MediaPlayer previousMediaPlayer = mediaView.getMediaPlayer();
        if (previousMediaPlayer != null) {
            previousMediaPlayer.stop();
            previousMediaPlayer.dispose();
        }
        setVisible(false);
    }

    @Override
    public void setSource(URI source) {
        String path = source.toString();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(path));
        mediaView.setMediaPlayer(mediaPlayer);
        setVisible(true);
        if (isPlaying) {
            mediaPlayer.play();
        }
        mediaPlayer.setMute(isMuted);
        mediaPlayer.setCycleCount(-1);
        mediaPlayer.seek(new Duration(0));
        SeekSlider.INSTANCE.setMediaPlayer(mediaPlayer);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void invertPlaying() {
        isPlaying = !isPlaying;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void invertMuted() {
        isMuted = !isMuted;
    }
}
