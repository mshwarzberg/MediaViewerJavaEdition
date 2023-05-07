package controls;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import tool.MyToggleButton;
import view.VideoViewer;

public class LeftControls extends HBox {
    public static final LeftControls INSTANCE = new LeftControls();

    private LeftControls() {
        MyToggleButton pausePlay = new MyToggleButton("Pause");
        pausePlay.setOnAction(e -> {
            MediaPlayer player = VideoViewer.INSTANCE.mediaView.getMediaPlayer();
            if (VideoViewer.INSTANCE.isPlaying()) {
                player.pause();
                pausePlay.setText("Play");
            } else {
                player.play();
                pausePlay.setText("Pause");
            }
            VideoViewer.INSTANCE.invertPlaying();
        });

        MyToggleButton muteUnmute = new MyToggleButton("Unmute");
        muteUnmute.setOnAction(event -> {
            MediaPlayer player = VideoViewer.INSTANCE.mediaView.getMediaPlayer();
            if (player != null) {
                player.setMute(VideoViewer.INSTANCE.isMuted());
                muteUnmute.setText(VideoViewer.INSTANCE.isMuted() ? "Unmute" : "Mute");
                VideoViewer.INSTANCE.invertMuted();
            }
        });
        getChildren().addAll(pausePlay, muteUnmute);
        setPadding(new Insets(15, 0, 0, 15));
        setSpacing(15);
    }

}
