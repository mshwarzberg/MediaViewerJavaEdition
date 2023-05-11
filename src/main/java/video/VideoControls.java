package video;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class VideoControls extends BorderPane {
    public static final VideoControls INSTANCE = new VideoControls();
    private final BooleanProperty showControls = new SimpleBooleanProperty(true);
    private Timeline timeoutTimeline;
    public static final int HEIGHT = 90;
    public static final int PADDING = 10;

    private VideoControls() {
        setTop(SeekSlider.INSTANCE);
        setLeft(LeftControls.INSTANCE);
        setRight(RightControls.INSTANCE);
        setPrefWidth(Double.MAX_VALUE);
        setMaxHeight(HEIGHT);
        setId("videoControls");
        getStyleClass().add("slider");
        setPadding(new Insets(PADDING));
        listeners();
    }

    public void listeners() {
        showControls.addListener((observable, wasShowing, isValueShowing) -> {
            setVisible(showControls());
        });
        timeoutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3),
                        event -> {
                            if (Video.INSTANCE.isPlaying()) {
                                showControls.set(false);
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
        showControls.set(true);
    }

    public boolean showControls() {
        return showControls.get();
    }
}