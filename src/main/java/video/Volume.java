package video;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tool.MyToggleButton;

public class Volume extends HBox {
    public static Volume INSTANCE = new Volume();
    private final DoubleProperty volumeLevel = new SimpleDoubleProperty(1);

    private Volume() {
        MyToggleButton muteUnmute = new MyToggleButton("Unmute");
        muteUnmute.setOnAction(event -> {
            muteUnmute.setText(Video.INSTANCE.isMuted() ? "Mute" : "Unmute");
            Video.INSTANCE.invertMuted();
        });
        setPadding(new Insets((double) VideoControls.HEIGHT / 4, 0, 0, 0));
        getChildren().addAll(muteUnmute, new VolumeSlider());
        setAlignment(Pos.CENTER);
        volumeLevel.addListener((observable, n, v) -> {
            Video.INSTANCE.getMediaPlayer().setVolume((Double) v);
        });
    }

    private static class VolumeSlider extends ProgressBar {
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
            double seekTime = (mouseX / width);
            Volume.INSTANCE.setVolumeLevel(seekTime);
            setProgress(seekTime);
        }
    }

    public void setVolumeLevel(double volumeLevel) {
        this.volumeLevel.set(volumeLevel);
    }

    public double getVolumeLevel() {
        return volumeLevel.get();
    }
}
