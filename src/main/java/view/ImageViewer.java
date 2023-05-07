package view;

import core.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URI;

public class ImageViewer extends ImageView implements ViewController {
    public static final ImageViewer INSTANCE = new ImageViewer();

    private ImageViewer() {
        Main.getPrimaryStage().showingProperty().addListener(observable -> {
            setPreserveRatio(true);
            fitWidthProperty().bind(Main.getPrimaryStage().widthProperty());
            fitHeightProperty().bind(Main.getPrimaryStage().heightProperty().subtract(Main.BAR_HEIGHT / 2));
        });
    }

    @Override
    public void clearSource() {
        setVisible(false);
    }

    @Override
    public void setSource(URI source) {
        String path = source.toString();
        setImage(new Image(path));
        setVisible(true);
    }
}
