package view;

import core.Main;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URI;

public class VideoPlayer extends MediaView {
    private final VideoControls videoControls = new VideoControls();
    private final VBox videoContainer = new VBox(this, videoControls);
    private MediaPlayer mediaPlayer;

    public void setVideoSource(URI source) {
        setContainerDimensions();
        Main.getStackPane().getChildren().add(videoContainer);
        videoContainer.setAlignment(Pos.CENTER);
        String path = source.toString();
        mediaPlayer = new MediaPlayer(new Media(path));
        this.setMediaPlayer(mediaPlayer);
        videoControls.setMediaPlayer(mediaPlayer);
        videoControls.toFront();
        videoControls.setAlignment(Pos.CENTER);
        mediaPlayer.play();
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(-1);
        Main.getStackPane().getChildren().get(1).toBack();
    }

    public void clearSource() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        this.setMediaPlayer(null);
        Main.getStackPane().getChildren().remove(videoContainer);
    }

    private void setContainerDimensions() {
        if (this.getFitHeight() == 0 && this.getFitWidth() == 0) {
            this.setPreserveRatio(true);
            this.fitWidthProperty().bind(Main.getPrimaryStage().widthProperty());
            this.fitHeightProperty().bind(Main.getPrimaryStage().heightProperty());
        }
        if (videoContainer.getHeight() == 0 && videoContainer.getWidth() == 0) {
            videoContainer.setPrefWidth(Main.getPrimaryStage().widthProperty().doubleValue());
            videoContainer.setPrefHeight(Main.getPrimaryStage().heightProperty().doubleValue());
        }
    }
}
