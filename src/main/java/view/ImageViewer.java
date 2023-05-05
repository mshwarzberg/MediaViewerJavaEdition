package view;

import core.Main;
import core.ResizeToScreen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URI;

public class ImageViewer extends ImageView implements ViewController {
    public static final ImageViewer INSTANCE = new ImageViewer();

    private ImageViewer() {

    }

    @Override
    public void clearSource() {
        setImage(null);
    }

    @Override
    public void setSource(URI source) {
        ResizeToScreen.setDimensions(this);
        String path = source.toString();
        setImage(new Image(path));
        setVisible(true);
    }
}
