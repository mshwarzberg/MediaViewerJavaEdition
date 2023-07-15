package video;

import core.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import tool.MyButton;
import tool.MyToggleButton;

import static video.VideoControls.SIDE_CONTROLS_HEIGHT;

public class RightControls extends HBox {

    public static final RightControls INSTANCE = new RightControls();
    private final Runnable doSomething = Main::invertFullscreen;
    private final MyToggleButton fullscreenButton = new MyToggleButton(
            "Enter Fullscreen",
            "Exit Fullscreen",
            Main.isFullscreen,
            doSomething
    );
    private final MyButton loopingButton = new MyButton("Stop Loop");

    private RightControls() {
        getChildren().addAll(loopingButton, fullscreenButton);
        setPadding(new Insets(0, VideoControls.PADDING * 2, (double) VideoControls.HEIGHT / 4, 0));
        setHeight(SIDE_CONTROLS_HEIGHT);
        setSpacing(15);
        setAlignment(Pos.BOTTOM_CENTER);
    }
}
