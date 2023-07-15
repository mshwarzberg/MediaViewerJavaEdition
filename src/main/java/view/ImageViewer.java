package view;

import core.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        Image image = new Image(path);
        if (path.endsWith("webp")) {
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(new File(source));
                image = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setImage(image);
        setVisible(true);
    }
}
