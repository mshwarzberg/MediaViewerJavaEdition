package view;

import java.net.URI;
import java.util.List;

import core.Main;
import core.ResizeToScreen;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import tool.MyButton;
import tool.MyToggleButton;

public class VideoViewer extends BorderPane implements ViewController {
    public static final VideoViewer INSTANCE = new VideoViewer();
    private final MediaView mediaView = new MediaView();

    private VideoViewer() {
        setCenter(mediaView);
        setBottom(Controls.INSTANCE);
        Controls.INSTANCE.setVisible(false);
    }

    @Override
    public void clearSource() {
        setVisible(false);
        Controls.INSTANCE.setVisible(false);
    }

    @Override
    public void setSource(URI source) {
        String path = source.toString();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(path));
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setFitWidth(Main.getPrimaryStage().getWidth());
        mediaView.setFitHeight(Main.getPrimaryStage().getHeight() - (Main.BAR_HEIGHT * 2));
        setVisible(true);
        mediaPlayer.play();
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(-1);
        Controls.INSTANCE.setVisible(true);
    }

    private static class Controls extends HBox {

        public static final Controls INSTANCE = new Controls();
        private Controls() {
            MyToggleButton pausePlay = new MyToggleButton("Pause");
            pausePlay.setOnAction(e -> {
                MediaPlayer player = VideoViewer.INSTANCE.mediaView.getMediaPlayer();
                if (player != null) {
                    if (pausePlay.isSelected()) {
                        player.pause();
                        pausePlay.setText("Play");
                    } else {
                        player.play();
                        pausePlay.setText("Pause");
                    }
                }
            });

            MyButton stop = new MyButton("Stop");
            stop.setOnAction(e -> {
                if (VideoViewer.INSTANCE.mediaView.getMediaPlayer() != null) {
                    VideoViewer.INSTANCE.mediaView.getMediaPlayer().stop();
                }
            });

            MyToggleButton muteUnmute = new MyToggleButton("Unmute");
            muteUnmute.setOnAction(event -> {
                MediaPlayer player = VideoViewer.INSTANCE.mediaView.getMediaPlayer();
                if (player != null) {
                    boolean isCurrentlyMuted = player.muteProperty().get();
                    player.setMute(!isCurrentlyMuted);
                    muteUnmute.setText(isCurrentlyMuted ? "Mute" : "Unmute");
                }
            });
            setSpacing(10);
            getChildren().addAll(pausePlay, stop, muteUnmute);
            setBackground(Background.fill(Paint.valueOf("blue")));
            setPrefWidth(Double.MAX_VALUE);
            setAlignment(Pos.CENTER);
            setHeight(Main.BAR_HEIGHT * 3);
        }
    }
}
