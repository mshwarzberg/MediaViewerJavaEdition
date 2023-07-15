package video;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class VideoProperties {
    public static final VideoProperties INSTANCE = new VideoProperties();

    private final BooleanProperty showControls = new SimpleBooleanProperty(true);
    private final DoubleProperty volumeLevel = new SimpleDoubleProperty(1);
    private final BooleanProperty isPlaying = new SimpleBooleanProperty(true);
    private final BooleanProperty isMuted = new SimpleBooleanProperty(true);
    private final DoubleProperty seekPosition = new SimpleDoubleProperty(0);
    private final BooleanProperty isSeeking = new SimpleBooleanProperty(false);
    private boolean wasPlayingBeforeSeek;

    private VideoProperties() {
        volumeLevel.addListener((observable, n, v) -> {
            isMuted.set((Double) v == 0);
            Video.INSTANCE.getMediaPlayer().setVolume((Double) v);
            Volume.INSTANCE.SLIDER_INSTANCE.setProgress((Double) v);
        });
        showControls.addListener((observable, wasShowing, isValueShowing) -> {
            VideoControls.INSTANCE.setVisible(showControls.get());
        });
        isMuted.addListener((observable, wasMuted, muted) -> {
            Video.INSTANCE.getMediaPlayer().setMute(muted);
            if (wasMuted && volumeLevel.get() == 0) {
                setVolumeLevel(1);
                Volume.INSTANCE.SLIDER_INSTANCE.setProgress(1);
            }
        });
        isPlaying.addListener((observable, wasPlaying, isValuePlaying) -> {
            pausePlay(isValuePlaying);
        });
        seekPosition.addListener((observable, oldValue, newValue) -> {
            MediaPlayer mediaPlayer = Video.INSTANCE.getMediaPlayer();
            double toDouble = newValue.doubleValue();
            if (toDouble < 1) {
                SeekSlider.INSTANCE.setProgress(toDouble);
                mediaPlayer.seek(Duration.millis(toDouble * SeekSlider.INSTANCE.getMaxSeekPosition()));
            } else {
                SeekSlider.INSTANCE.setProgress(toDouble / SeekSlider.INSTANCE.getMaxSeekPosition());
            }
        });
        isSeeking.addListener((observable, n, currentlySeeking) -> {
            if (currentlySeeking) {
                wasPlayingBeforeSeek = isPlaying.get();
                VideoProperties.INSTANCE.setPlaying(false);
            } else {
                VideoProperties.INSTANCE.setPlaying(wasPlayingBeforeSeek);
            }
        });
    }

    private void pausePlay(boolean shouldPlay) {
        if (shouldPlay) {
            Video.INSTANCE.getMediaPlayer().play();
        } else {
            Video.INSTANCE.getMediaPlayer().pause();
        }
    }

    public void invertPlaying() {
        this.isPlaying.set(!isPlaying.get());
    }

    public void invertMuted() {
        this.isMuted.set(!isMuted.get());
    }

    public void setShowControls(boolean showControls) {
        this.showControls.set(showControls);
    }

    public double getVolumeLevel() {
        return volumeLevel.get();
    }

    public void setVolumeLevel(double volumeLevel) {
        this.volumeLevel.set(volumeLevel);
    }

    public void setMuted(boolean isMuted) {
        this.isMuted.set(isMuted);
    }

    public boolean isMuted() {
        return isMuted.get();
    }

    public boolean isPlaying() {
        return isPlaying.get();
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying.set(isPlaying);
    }

    public void setSeekPosition(double seekPosition) {
        this.seekPosition.set(seekPosition);
    }

    public void setIsSeeking(boolean isSeeking) {
        this.isSeeking.set(isSeeking);
    }

    public BooleanProperty getPlayingProperty() {
        return isPlaying;
    }

    public BooleanProperty getMutedProperty() {
        return isMuted;
    }
}
