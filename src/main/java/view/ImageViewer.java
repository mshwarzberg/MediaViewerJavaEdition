package view;

import core.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URI;

public class ImageViewer extends ImageView {

    public void setImageSource(URI source) {
        setContainerDimensions();
        Main.getStackPane().getChildren().add(this);
        String path = source.toString();
        this.setImage(new Image(path));
        Main.getNavbar().toFront();
    }

    public void clearSource() {
        Main.getStackPane().getChildren().remove(this);
    }

    private void setContainerDimensions() {
        if (this.getFitHeight() == 0 && this.getFitWidth() == 0) {
            this.setPreserveRatio(true);
            this.fitWidthProperty().bind(Main.getPrimaryStage().widthProperty());
            this.fitHeightProperty().bind(Main.getPrimaryStage().heightProperty());
        }
    }
}
