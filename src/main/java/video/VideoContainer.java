package video;

import core.Main;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import view.ViewController;

import java.net.URI;

public class VideoContainer extends StackPane implements ViewController {
    public static final VideoContainer INSTANCE = new VideoContainer();

    private VideoContainer() {
        getChildren().addAll(Video.INSTANCE, VideoControls.INSTANCE);
        setAlignment(VideoControls.INSTANCE, Pos.BOTTOM_CENTER);
        Main.getPrimaryStage().showingProperty().addListener((observable -> {
            Video.INSTANCE.setFitWidth(Main.getPrimaryStage().getWidth());
            Video.INSTANCE.setFitHeight(Main.getPrimaryStage().getHeight() - 40);
        }));
        setVisible(false);
        listeners();
    }

    @Override
    public void clearSource() {
        MediaPlayer previousMediaPlayer = Video.INSTANCE.getMediaPlayer();
        if (previousMediaPlayer != null && previousMediaPlayer.getMedia() != null) {
            previousMediaPlayer.dispose();
            Video.INSTANCE.setMediaPlayer(null);
        }
        setVisible(false);
    }

    @Override
    public void setSource(URI source) {
        setVisible(true);
        Video.INSTANCE.updateMediaPlayer(source);
    }

    private void listeners() {
        setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                Video.INSTANCE.invertPlaying();
            }
            if (event.getClickCount() == 2) {
                Main.isFullscreen.set(!Main.isFullscreen.get());
             }
        });
    }
}
