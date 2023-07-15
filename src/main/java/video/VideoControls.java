package video;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class VideoControls extends BorderPane {
    public static final VideoControls INSTANCE = new VideoControls();
    public static final int HEIGHT = 90;
    public static final int PADDING = 10;
    public static final int SIDE_CONTROLS_HEIGHT = VideoControls.HEIGHT / 6;
    private Timeline timeoutTimeline;

    private VideoControls() {
        setTop(SeekSlider.INSTANCE);
        setLeft(LeftControls.INSTANCE);
        setRight(RightControls.INSTANCE);
        setPrefWidth(Double.MAX_VALUE);
        setMaxHeight(HEIGHT);
        setId("videoControls");
        setPadding(new Insets(PADDING));
        listeners();
    }

    public void listeners() {
        timeoutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3),
                        event -> {
                            if (VideoProperties.INSTANCE.isPlaying()) {
                                VideoProperties.INSTANCE.setShowControls(false);
                            }
                        }
                )
        );
        setOnMouseEntered(event -> {
            resetTimeout(false);
        });
        setOnMouseExited(event -> {
            resetTimeout(true);
        });
        setOnMousePressed(Event::consume);
        resetTimeout(true);
    }

    protected void resetTimeout(boolean shouldRestartTimeline) {
        timeoutTimeline.stop();
        if (shouldRestartTimeline) {
            timeoutTimeline.play();
        }
        VideoProperties.INSTANCE.setShowControls(true);
    }

}