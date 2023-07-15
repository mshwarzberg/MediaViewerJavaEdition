package video;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tool.MyToggleButton;

public class Volume extends HBox {
    public static Volume INSTANCE = new Volume();
    public final VolumeSlider SLIDER_INSTANCE = new VolumeSlider();

    private Volume() {
        Runnable doSomething = VideoProperties.INSTANCE::invertMuted;
        MyToggleButton muteUnmute = new MyToggleButton("Mute", "Unmute", VideoProperties.INSTANCE.getMutedProperty(), doSomething);
        setPadding(new Insets((double) VideoControls.HEIGHT / 4, 0, 0, 0));
        getChildren().addAll(muteUnmute, SLIDER_INSTANCE);
        setAlignment(Pos.CENTER);
    }

    public static class VolumeSlider extends ProgressBar {
        private static final int WIDTH = 50;

        private VolumeSlider() {
            setWidth(WIDTH);
            setHeight(SeekSlider.HEIGHT);
            setProgress(100);
            getStyleClass().add("slider");
            setId("volumeSlider");
            setOnMousePressed(this::updateSliderProperty);
            setOnMouseDragged(this::updateSliderProperty);
        }

        private void updateSliderProperty(MouseEvent event) {
            double mouseX = event.getX();
            double width = getWidth();
            double seekTime = Math.max((mouseX / width), 0);
            VideoProperties.INSTANCE.setVolumeLevel(seekTime);
        }
    }
}
