package video;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import tool.MyToggleButton;

import static video.VideoControls.SIDE_CONTROLS_HEIGHT;

public class LeftControls extends HBox {
    public static final LeftControls INSTANCE = new LeftControls();

    private LeftControls() {
        Runnable doSomething = VideoProperties.INSTANCE::invertPlaying;
        MyToggleButton pausePlay = new MyToggleButton(
                "Play",
                "Pause",
                VideoProperties.INSTANCE.getPlayingProperty(),
                doSomething
        );
        getChildren().addAll(pausePlay, Volume.INSTANCE);
        setPadding(new Insets(0, 0, (double) VideoControls.HEIGHT / 4, (double) VideoControls.PADDING / 2));
        setHeight(SIDE_CONTROLS_HEIGHT);
        setSpacing(15);
        setAlignment(Pos.BOTTOM_CENTER);
    }

}
