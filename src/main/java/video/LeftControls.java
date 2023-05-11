package video;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import tool.MyToggleButton;

public class LeftControls extends HBox {
    public static final LeftControls INSTANCE = new LeftControls();
    public static final int HEIGHT = VideoControls.HEIGHT / 6;

    private LeftControls() {
        MyToggleButton pausePlay = new MyToggleButton("Pause");
        pausePlay.setOnAction(e -> {
            pausePlay.setText(Video.INSTANCE.isPlaying() ? "Play" : "Pause");
            Video.INSTANCE.invertPlaying();
        });
        getChildren().addAll(pausePlay, Volume.INSTANCE);
        setPadding(new Insets(0, 0, (double) VideoControls.HEIGHT / 4, (double) VideoControls.PADDING / 2));
        setHeight(HEIGHT);
        setSpacing(15);
        setAlignment(Pos.BOTTOM_CENTER);
    }

}
