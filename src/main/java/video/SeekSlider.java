package video;

import core.Main;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SeekSlider extends ProgressBar {
    public static final SeekSlider INSTANCE = new SeekSlider();
    public static final int HEIGHT = VideoControls.HEIGHT / 18;
    private double maxSeekPosition = 0;

    private SeekSlider() {
        setId("seekSlider");
        getStyleClass().add("slider");
        initializeListeners();
        Main.getPrimaryStage().setOnShown(event -> {
            setPrefWidth(Main.getPrimaryStage().getWidth() * (1.32));
        });
        setHeight(HEIGHT);
    }

    public void initializeListeners() {
        setOnMousePressed(this::videoTimelineUpdate);
        setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                videoTimelineUpdate(event);
            }
        });
        setOnMouseReleased(event -> {
            VideoProperties.INSTANCE.setIsSeeking(false);
        });
    }

    private void videoTimelineUpdate(MouseEvent event) {
        VideoProperties.INSTANCE.setIsSeeking(true);
        VideoProperties.INSTANCE.setSeekPosition(Math.max(event.getX() / getWidth(), 0));
    }

    public double getMaxSeekPosition() {
        return maxSeekPosition;
    }

    public void setMaxSeekPosition(double videoDuration) {
        maxSeekPosition = videoDuration;
    }
}
