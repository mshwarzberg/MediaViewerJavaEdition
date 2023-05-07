package controls;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;

public class VideoControls extends BorderPane {
    public static final VideoControls INSTANCE = new VideoControls();

    private VideoControls() {
        setTop(SeekSlider.INSTANCE);
        setLeft(LeftControls.INSTANCE);
        setRight(RightControls.INSTANCE);
        setBackground(Background.fill(Paint.valueOf("#728D72BA")));
        setPrefWidth(Double.MAX_VALUE);
        setMaxHeight(120);
        setPadding(new Insets(10, 10, 10, 10));
    }
}