package view;

import core.Main;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;

public class VideoControls extends HBox {
    private MediaPlayer mediaPlayer;

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        Button playButton = new Button("Play");
        playButton.setOnAction(event -> mediaPlayer.play());

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> mediaPlayer.pause());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(event -> mediaPlayer.stop());

        getChildren().addAll(playButton, pauseButton, stopButton);
        setSpacing(10);
        setWidth(Main.getPrimaryStage().getWidth());
        setHeight(25);
    }
}
