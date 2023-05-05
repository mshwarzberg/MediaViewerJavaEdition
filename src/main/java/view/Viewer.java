package view;

import core.FileType;
import core.Main;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URI;

public class Viewer extends StackPane {
    public static final Viewer INSTANCE = new Viewer();

    private Viewer() {
        setAlignment(Pos.CENTER);
        getChildren().add(ImageViewer.INSTANCE);
        getChildren().add(VideoViewer.INSTANCE);
    }

    public void setSources(FileType fileType, URI uri) {
        ImageViewer.INSTANCE.clearSource();
        VideoViewer.INSTANCE.clearSource();
        switch (fileType) {
            case IMAGE -> ImageViewer.INSTANCE.setSource(uri);
            case VIDEO -> VideoViewer.INSTANCE.setSource(uri);
        }
    }

}